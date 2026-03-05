package cftp.entity.creeper;

import cftp.entity.base.CreeperElementalEntity;
import cftp.utility.CreeperMath;
import cftp.utility.Shapes;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.world.explosion.ExplosionImpl;

public class CreeperWaterEntity extends CreeperElementalEntity {

    protected int ExplosionDiameter = 5;

    public CreeperWaterEntity(
            EntityType<? extends CreeperElementalEntity> entityType,
            World world
    ) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.MAX_HEALTH, 20)
                .add(EntityAttributes.MOVEMENT_SPEED, 0.25f)
                .add(EntityAttributes.ATTACK_DAMAGE, 1)
                .add(EntityAttributes.FOLLOW_RANGE, 20);
    }

    @Override
    protected int getElementalCreeperType() {
        return CreeperMath.CREEPER_TYPE.WATER.getType();
    }

    @Override
    protected void explode() {
        if (this.getWorld() instanceof ServerWorld serverWorld) {
            this.dead = true;

            if (!isDefused()) {
                final Difficulty difficulty = this.getWorld().getDifficulty();
                float chargedPower;

                chargedPower = this.isCharged() ? 1.75F : 1.00F;
                chargedPower *= this.isTouchingWater() ? 1.50F : 1.00F;

                // For the case of extending the difficulty enum. We provide a default.
                float diameter = this.ExplosionDiameter;
                int dropExplosionItemChance;
                int ghostCreeperChance;

                switch (difficulty) {
                    case PEACEFUL:
                    case EASY: {
                        dropExplosionItemChance = (int) (100 * DROP_EXPLOSION_ITEM_CHANCE_EASY);
                        ghostCreeperChance = (int) (255 * GHOST_CREEPER_EXPLODE_CHANCE_EASY);
                        diameter *= chargedPower;
                    }
                    break;
                    case NORMAL: {
                        dropExplosionItemChance = (int) (100 * DROP_EXPLOSION_ITEM_CHANCE_NORMAL);
                        ghostCreeperChance = (int) (255 * GHOST_CREEPER_EXPLODE_CHANCE_NORMAL);
                        diameter *= 1.25f * chargedPower;
                    }
                    break;
                    case HARD:
                    default: {
                        dropExplosionItemChance = (int) (100 * DROP_EXPLOSION_ITEM_CHANCE_HARD);
                        ghostCreeperChance = (int) (255 * GHOST_CREEPER_EXPLODE_CHANCE_HARD);
                        diameter *= 1.50f * chargedPower;
                    }
                    break;
                }

                final int iDiameter = (int) diameter;
                final int radius = iDiameter / 2;

                // We're creating a pseudo explosion just to verify the behaviour of blocks when destroyed.
                final ExplosionImpl dummyExplosion = new ExplosionImpl(
                        serverWorld, null, null,
                        null, null, 1, false,
                        Explosion.DestructionType.DESTROY
                );

                for (int y = 0; y < iDiameter; ++y) {
                    for (int x = 0; x < iDiameter; ++x) {
                        for (int z = 0; z < iDiameter; ++z) {
                            if (Shapes.isSphere(x, y, z, radius)) {

                                BlockPos blockPos = BlockPos.ofFloored(
                                        this.getX() + x - radius,
                                        this.getY() + y - radius,
                                        this.getZ() + z - radius
                                );

                                BlockState state = serverWorld.getBlockState(blockPos);
                                float resistance = state.getBlock().getBlastResistance();
                                Block block = state.getBlock();

                                if (state.contains(Properties.WATERLOGGED)) {
                                    BlockState waterloggedState = state.with(Properties.WATERLOGGED, true);

                                    if (resistance < 100) {
                                        CreeperMath.onGeneralReplace(serverWorld, dummyExplosion, state, waterloggedState, blockPos, (itemStack, pos) -> {
                                            if (this.random.nextInt(100) < dropExplosionItemChance) {
                                                Block.dropStack(serverWorld, blockPos, itemStack);
                                            }
                                        });
                                    }

                                    // Also schedule water fluid tick for proper fluid behavior
                                    //serverWorld.getFluidTickScheduler().schedule(blockPos, Fluids.WATER, Fluids.WATER.getTickRate(serverWorld));
                                } else if (block == Blocks.LAVA) {
                                    if (resistance < 100) {
                                        CreeperMath.onGeneralReplace(serverWorld, dummyExplosion, state, Blocks.OBSIDIAN.getDefaultState(), blockPos, (itemStack, pos) -> {
                                            if (this.random.nextInt(100) < dropExplosionItemChance) {
                                                Block.dropStack(serverWorld, blockPos, itemStack);
                                            }
                                        });
                                    }
                                } else {
                                    if (resistance < 100) {
                                        CreeperMath.onGeneralReplace(serverWorld, dummyExplosion, state, Blocks.WATER.getDefaultState(), blockPos, (itemStack, pos) -> {
                                            if (this.random.nextInt(100) < dropExplosionItemChance) {
                                                Block.dropStack(serverWorld, blockPos, itemStack);
                                            }
                                        });
                                    }
                                }
                            }
                        }
                    }
                }

                //1 // This can be optimized simply calculate 1/8 of the sphere then generate blocks on all sides of the sphere.
                //1 {
                //1     // 1st. calculate the points
                //1     for (int y = 0; y < half; ++y) {
                //1         for (int x = 0; x < half; ++x) {
                //1             for (int z = 0; z < half; ++z)  {
                //1
                //1                 if (Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2) <= Math.pow(3,  2)) {
                //1
                //1                     BlockPos blockPos = BlockPos.ofFloored(
                //1                             this.getX() + x,
                //1                             this.getY() + y,
                //1                             this.getZ() + z
                //1                     );
                //1
                //1                     serverWorld.setBlockState(blockPos, Blocks.ACACIA_PLANKS.getDefaultState(), Block.NOTIFY_ALL);
                //1                 }
                //1
                //1             }
                //1         }
                //1     }
                //1
                //1     // 2nd. Generate them on all sides.
                //1 }

                this.playExplosionSound(serverWorld);
                SpawnGhostCreeper(serverWorld, ghostCreeperChance);
            } else {
                this.playDefusedExplosionSound(serverWorld);
            }

            this.spawnEffectsCloud();
            this.onRemoval(serverWorld, RemovalReason.KILLED);
            this.discard();
        }
    }

    @Override
    public void tickMovement() {
        if (this.getWorld().isClient) {
            CreeperMath.createWaterWalkingParticle(this);
        }

        if (this.random.nextInt(256) > 252) {
            if (this.getWorld() instanceof ServerWorld serverWorld) {
                serverWorld.playSound(
                        null, this.getX(), this.getY(), this.getZ(),
                        SoundEvents.BLOCK_POINTED_DRIPSTONE_DRIP_WATER,
                        SoundCategory.HOSTILE,
                        1.2f, 0.4f
                );
            }
        }

        super.tickMovement();
    }


}
