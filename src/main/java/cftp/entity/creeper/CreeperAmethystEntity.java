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

public class CreeperAmethystEntity extends CreeperElementalEntity {

    protected int ExplosionDiameter = 7;

    public CreeperAmethystEntity(
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
        return CreeperMath.CREEPER_TYPE.AMETHYST.getType();
    }

    @Override // TODO
    protected void explode() {
        if (this.getWorld() instanceof ServerWorld serverWorld) {
            this.dead = true;

            final Difficulty difficulty = this.getWorld().getDifficulty();
            final float chargedPower = this.isCharged() ? 2.0F : 1.0F;
            float diameter = this.ExplosionDiameter;

            int dropExplosionItemChance;
            int ghostCreeperChance;

            switch (difficulty) {
                case PEACEFUL:
                case EASY: {
                    dropExplosionItemChance = (int)(100 * DROP_EXPLOSION_ITEM_CHANCE_EASY);
                    ghostCreeperChance = (int)(255 * GHOST_CREEPER_EXPLODE_CHANCE_EASY);
                    diameter *= chargedPower;
                } break;
                case NORMAL: {
                    dropExplosionItemChance = (int)(100 * DROP_EXPLOSION_ITEM_CHANCE_NORMAL);
                    ghostCreeperChance = (int)(255 * GHOST_CREEPER_EXPLODE_CHANCE_NORMAL);
                    diameter *= 1.25f * chargedPower;
                } break;
                case HARD:
                default: {
                    dropExplosionItemChance = (int)(100 * DROP_EXPLOSION_ITEM_CHANCE_HARD);
                    ghostCreeperChance = (int)(255 * GHOST_CREEPER_EXPLODE_CHANCE_HARD);
                    diameter *= 1.5f * chargedPower;
                } break;
            }

            {
                final int iDiameter = (int) diameter;
                final int radius = iDiameter / 2;

                // We're creating a pseudo explosion just to verify the behaviour of blocks when destroyed.
                final ExplosionImpl dummyExplosion = new ExplosionImpl(
                        serverWorld, null, null,
                        null, null, 1, false,
                        Explosion.DestructionType.DESTROY
                );

                for (int y = 0; y < iDiameter; ++y) { // gen
                    for (int x = 0; x < iDiameter; ++x) {
                        for (int z = 0; z < iDiameter; ++z) {

                            int distance = (int) Shapes.sphereDistance(x, y, z, radius);
                            BlockPos blockPos = BlockPos.ofFloored(
                                    this.getX() + x - radius,
                                    this.getY() + y - radius,
                                    this.getZ() + z - radius
                            );

                            BlockState state = serverWorld.getBlockState(blockPos);
                            float resistance = state.getBlock().getBlastResistance();

                            if (distance < radius - 3 && resistance < 100) {

                                CreeperMath.onGeneralReplace(serverWorld, dummyExplosion, state, Blocks.WATER.getDefaultState(), blockPos, (itemStack, pos) -> {
                                    if (this.random.nextInt(100) < dropExplosionItemChance) {
                                        Block.dropStack(serverWorld, blockPos, itemStack);
                                    }
                                });

                            } else if (distance < (radius - 2) && resistance < 100) {
                                if (this.random.nextInt(10) < 7) {

                                    CreeperMath.onGeneralReplace(serverWorld, dummyExplosion, state, Blocks.AMETHYST_BLOCK.getDefaultState(), blockPos, (itemStack, pos) -> {
                                        if (this.random.nextInt(100) < dropExplosionItemChance) {
                                            Block.dropStack(serverWorld, blockPos, itemStack);
                                        }
                                    });

                                } else {

                                    CreeperMath.onGeneralReplace(serverWorld, dummyExplosion, state, Blocks.WATER.getDefaultState(), blockPos, (itemStack, pos) -> {
                                        if (this.random.nextInt(100) < dropExplosionItemChance) {
                                            Block.dropStack(serverWorld, blockPos, itemStack);
                                        }
                                    });

                                }
                            } else if (distance < (radius - 1) && resistance < 100) {
                                if (this.random.nextInt(10) < 7) {

                                    CreeperMath.onGeneralReplace(serverWorld, dummyExplosion, state, Blocks.CALCITE.getDefaultState(), blockPos, (itemStack, pos) -> {
                                        if (this.random.nextInt(100) < dropExplosionItemChance) {
                                            Block.dropStack(serverWorld, blockPos, itemStack);
                                        }
                                    });

                                } else {

                                    CreeperMath.onGeneralReplace(serverWorld, dummyExplosion, state, Blocks.WATER.getDefaultState(), blockPos, (itemStack, pos) -> {
                                        if (this.random.nextInt(100) < dropExplosionItemChance) {
                                            Block.dropStack(serverWorld, blockPos, itemStack);
                                        }
                                    });

                                }
                            } else if (distance < radius && resistance < 100) {
                                if (this.random.nextInt(10) < 8) {

                                    CreeperMath.onGeneralReplace(serverWorld, dummyExplosion, state, Blocks.SMOOTH_BASALT.getDefaultState(), blockPos, (itemStack, pos) -> {
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

            this.playExplosionSound(serverWorld);
            this.spawnEffectsCloud();
            this.onRemoval(serverWorld, RemovalReason.KILLED);
            this.discard();

            SpawnGhostCreeper(serverWorld, ghostCreeperChance);
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
                        1.3f, 0.45f
                );
            }
        }

        super.tickMovement();
    }

}
