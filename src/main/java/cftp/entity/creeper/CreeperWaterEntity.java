package cftp.entity.creeper;

import cftp.config.CFTPData;
import cftp.entity.base.CreeperElementalEntity;
import cftp.utility.CreeperMath;
import cftp.utility.Shapes;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.Difficulty;
import net.minecraft.world.GameMode;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.world.explosion.ExplosionImpl;

import java.util.List;

public class CreeperWaterEntity extends CreeperElementalEntity {

    protected float power = CFTPData.creeperWater.power;

    protected int WATER_BREATHING_EFFECT_TICKS_EASY = 20 * 10;  // = 0m 10s
    protected int WATER_BREATHING_EFFECT_TICKS_NORM = 20 * 05;  // = 0m 05s
    protected int WATER_BREATHING_EFFECT_TICKS_HARD = 20 * 00;  // = 0m 00s

    public CreeperWaterEntity(
            EntityType<? extends CreeperElementalEntity> entityType,
            World world
    ) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.MAX_HEALTH, CFTPData.creeperWater.health)
                .add(EntityAttributes.MOVEMENT_SPEED, CFTPData.creeperWater.movementSpeed)
                .add(EntityAttributes.ATTACK_DAMAGE, 1)
                .add(EntityAttributes.FOLLOW_RANGE, CFTPData.creeperWater.followRange);
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
                float diameter = this.power;
                int dropExplosionItemChance;
                int ghostCreeperChance;
                int effectTicks;

                switch (difficulty) {
                    case PEACEFUL:
                    case EASY: {
                        dropExplosionItemChance = (int) (100 * DROP_EXPLOSION_ITEM_CHANCE_EASY);
                        ghostCreeperChance = (int) (255 * GHOST_CREEPER_EXPLODE_CHANCE_EASY);
                        effectTicks = WATER_BREATHING_EFFECT_TICKS_EASY;
                        diameter *= chargedPower;
                    }
                    break;
                    case NORMAL: {
                        dropExplosionItemChance = (int) (100 * DROP_EXPLOSION_ITEM_CHANCE_NORMAL);
                        ghostCreeperChance = (int) (255 * GHOST_CREEPER_EXPLODE_CHANCE_NORMAL);
                        effectTicks = WATER_BREATHING_EFFECT_TICKS_NORM;
                        diameter *= 1.25f * chargedPower;
                    }
                    break;
                    case HARD:
                    default: {
                        dropExplosionItemChance = (int) (100 * DROP_EXPLOSION_ITEM_CHANCE_HARD);
                        ghostCreeperChance = (int) (255 * GHOST_CREEPER_EXPLODE_CHANCE_HARD);
                        effectTicks = WATER_BREATHING_EFFECT_TICKS_HARD;
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

                { // Destroy some items on the ground.
                    Box box = new Box(
                            this.getX() - radius,
                            this.getY() - radius,
                            this.getZ() - radius,
                            this.getX() + radius,
                            this.getY() + radius,
                            this.getZ() + radius
                    );

                    List<ServerPlayerEntity> players = serverWorld.getPlayers(p -> p.getBoundingBox().intersects(box));

                    for (ServerPlayerEntity player : players) {
                        if (player.interactionManager.getGameMode() != GameMode.CREATIVE) {
                            player.addStatusEffect(new StatusEffectInstance(
                                    StatusEffects.WATER_BREATHING,
                                    effectTicks,
                                    0,   // amplifier (0 == level I)
                                    true,        // ambient (optional)
                                    true,        // showParticles (set false to hide)
                                    true         // showIcon
                            ));
                        }
                    }

                    ///for (ItemEntity item : items) {
                    ///    item.damage(serverWorld, damageSource, ITEM_EXPLOSION_DAMAGE);
                    ///}
                }

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
    protected void playExplosionSound(World world) {
        world.playSound(
                null, this.getX(), this.getY(), this.getZ(),
                SoundEvents.ENTITY_PLAYER_SPLASH_HIGH_SPEED, SoundCategory.HOSTILE,
                2.0F, 0.4F
        );
    }

    @Override
    public void tickMovement() {
        if (this.getWorld() instanceof ServerWorld serverWorld) {
            if (this.random.nextInt(256) > 252) {
                Block block = serverWorld.getBlockState(this.getBlockPos()).getBlock();

                if (block == Blocks.AIR) {
                    BlockState state = Blocks.WATER.getDefaultState().with(FluidBlock.LEVEL, 6);
                    serverWorld.setBlockState(this.getBlockPos(), state, Block.NOTIFY_ALL_AND_REDRAW);
                } else if (block == Blocks.CAULDRON) {
                    BlockState state = Blocks.WATER_CAULDRON.getDefaultState().with(LeveledCauldronBlock.LEVEL, 1);
                    serverWorld.setBlockState(this.getBlockPos(), state, Block.NOTIFY_ALL_AND_REDRAW);
                } else if (block == Blocks.WATER_CAULDRON) {
                    BlockState state = serverWorld.getBlockState(this.getBlockPos());
                    int currentLevel = state.get(LeveledCauldronBlock.LEVEL);

                    if (currentLevel < 3) { // max level is 3
                        serverWorld.setBlockState(
                                this.getBlockPos(),
                                state.with(LeveledCauldronBlock.LEVEL, ++currentLevel),
                                Block.NOTIFY_ALL_AND_REDRAW
                        );
                    }
                }

                serverWorld.playSound(
                        null, this.getX(), this.getY(), this.getZ(),
                        SoundEvents.BLOCK_POINTED_DRIPSTONE_DRIP_WATER,
                        SoundCategory.HOSTILE,
                        1.2f, 0.4f
                );
            }
        } else {
            CreeperMath.createWaterWalkingParticle(this);
        }

        super.tickMovement();
    }


}
