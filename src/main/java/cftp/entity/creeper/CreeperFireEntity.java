package cftp.entity.creeper;

import cftp.entity.base.CreeperElementalEntity;
import cftp.goals.CreeperElementalIgniteGoal;
import cftp.goals.CreeperFireAttackGoal;
import cftp.registries.CFTPTags;
import cftp.utility.CreeperMath;
import cftp.utility.Shapes;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.passive.OcelotEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.world.explosion.ExplosionImpl;

public class CreeperFireEntity extends CreeperElementalEntity {

    protected int ExplosionDiameter = 9;

    public CreeperFireEntity(
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
        return CreeperMath.CREEPER_TYPE.FIRE.getType();
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(2, new CreeperElementalIgniteGoal(this, 1.0F));
        this.goalSelector.add(3, new FleeEntityGoal<>(this, OcelotEntity.class, 6.0F, 1.0, 1.2));
        this.goalSelector.add(3, new FleeEntityGoal<>(this, CatEntity.class, 6.0F, 1.0, 1.2));
        this.goalSelector.add(4, new CreeperFireAttackGoal(this, 1.0));
        this.goalSelector.add(5, new WanderAroundFarGoal(this, 0.8));
        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(6, new LookAroundGoal(this));
        this.targetSelector.add(1, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.add(2, new RevengeGoal(this));
    }


    private void createExplosionParticlesA(ServerWorld world, BlockPos pos) {

        // 2-4 flame, 3-5 smoke, none
        // 0, 1, 2 -> FLAME, 3, 4, 5 - SMOKE, 6, 7, 8, 9 -> NONE
        int chance = (pos.getX() + pos.getY() + pos.getZ()) % 10;

        if (chance > 5) {
            return;
        } else if (chance > 2) {
            world.spawnParticles(ParticleTypes.SMOKE,
                    pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                    chance, // 3, 4, 5
                    0.5f, 0.5f, 0.5f,
                    0.01f
            );
        } else {
            world.spawnParticles(ParticleTypes.FLAME,
                    pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                    chance + 2, // 2, 3, 4
                    0.5f, 0.5f, 0.5f,
                    0.25f
            );
        }

    }

    private void createExplosionParticlesB(ServerWorld world, BlockPos pos) {
        world.spawnParticles(ParticleTypes.CLOUD,
                pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                2,
                0.5f, 0.5f, 0.5f,
                0.01f
        );
    }

    @Override
    protected void explode() {
        if (this.getWorld() instanceof ServerWorld serverWorld) {
            this.dead = true;

            if (!this.isTouchingWater()) {

                final Difficulty difficulty = this.getWorld().getDifficulty();
                final float chargedPower = this.isCharged() ? 2.0F : 1.0F;

                float diameter = this.ExplosionDiameter;
                int dropExplosionItemChance;
                int ghostCreeperChance;

                switch (difficulty) {
                    case PEACEFUL:
                    case EASY: {
                        dropExplosionItemChance = (int)(100 * DROP_EXPLOSION_ITEM_CHANCE_EASY);
                        ghostCreeperChance = (int) (255 * GHOST_CREEPER_EXPLODE_CHANCE_EASY);
                        diameter *= chargedPower;
                    }
                    break;
                    case NORMAL: {
                        dropExplosionItemChance = (int)(100 * DROP_EXPLOSION_ITEM_CHANCE_NORMAL);
                        ghostCreeperChance = (int) (255 * GHOST_CREEPER_EXPLODE_CHANCE_NORMAL);
                        diameter *= 1.5f * chargedPower;
                    }
                    break;
                    case HARD:
                    default: {
                        dropExplosionItemChance = (int)(100 * DROP_EXPLOSION_ITEM_CHANCE_HARD);
                        ghostCreeperChance = (int) (255 * GHOST_CREEPER_EXPLODE_CHANCE_HARD);
                        diameter *= 2.0f * chargedPower;
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

                for (int y = 0; y < diameter; ++y) {
                    for (int x = 0; x < diameter; ++x) {
                        for (int z = 0; z < diameter; ++z) {
                            if (Shapes.isSphere(x, y, z, radius)) {

                                BlockPos blockPos = BlockPos.ofFloored(
                                        this.getX() + x - radius,
                                        this.getY() + y - radius,
                                        this.getZ() + z - radius
                                );

                                // So only the faces that are in front of the explosion will be affected.
                                Direction direction = Direction.getFacing(
                                        blockPos.getX(),
                                        blockPos.getY(),
                                        blockPos.getZ()
                                );

                                BlockState state = serverWorld.getBlockState(blockPos);

                                int condition = 0;
                                // todo config
                                //condition += (state.isIn(BlockTags.SNOW) ? 1 : 0);
                                condition += (state.isIn(CFTPTags.VAPORIZABLES) ? 1 : 0);
                                condition += (state.isIn(BlockTags.ICE) ? 1 : 0) << 1;
                                condition += (state.isIn(BlockTags.LEAVES) ? 1 : 0) << 2;
                                condition += (state.isIn(BlockTags.FLOWERS) ? 1 : 0) << 3;
                                condition += (state.isIn(BlockTags.SAND) ? 1 : 0) << 4;
                                condition += (state.isIn(BlockTags.SAPLINGS) ? 1 : 0) << 5;
                                condition += (state.isIn(BlockTags.CROPS) ? 1 : 0) << 6;
                                // todo config
                                //condition += (state.isIn(BlockTags.DIRT) ? 1 : 0) << 7;
                                condition += (state.isIn(CFTPTags.FERTILIZED_DIRTS) ? 1 : 0) << 7;


                                switch (condition) {
                                    case 1:         // Change SNOW-like to AIR.
                                    case 1 << 2:    // Change LEAVES-like to AIR.
                                    case 1 << 3:    // Change FLOWERS-like to AIR.
                                    case 1 << 5:    // Change SAPLINGS-like to AIR.
                                    case 1 << 6: {  // Change CROPS-like to AIR.
                                        CreeperMath.onGeneralReplace(serverWorld, dummyExplosion, state, Blocks.AIR.getDefaultState(), blockPos, (itemStack, pos) -> {
                                            if (this.random.nextInt(100) < dropExplosionItemChance) {
                                                Block.dropStack(serverWorld, blockPos, itemStack);
                                            }
                                        });
                                        createExplosionParticlesA(serverWorld, blockPos);
                                    }
                                    break;

                                    case 1 << 1: { // Change ICE-like to WATER.
                                        CreeperMath.onGeneralReplace(serverWorld, dummyExplosion, state, Blocks.WATER.getDefaultState(), blockPos, (itemStack, pos) -> {
                                        });
                                        createExplosionParticlesB(serverWorld, blockPos);
                                    }
                                    break;

                                    case 1 << 4: { // Change SAND-like to GLASS
                                        CreeperMath.onGeneralReplace(serverWorld, dummyExplosion, state, Blocks.GLASS.getDefaultState(), blockPos, (itemStack, pos) -> {
                                        });
                                        createExplosionParticlesA(serverWorld, blockPos);
                                    }
                                    break;

                                    case 1 << 7: { // Change DIRT(GRASS)-like to DIRT
                                        CreeperMath.onGeneralReplace(serverWorld, dummyExplosion, state, Blocks.DIRT.getDefaultState(), blockPos, (itemStack, pos) -> {
                                        });
                                        createExplosionParticlesA(serverWorld, blockPos);
                                    }

                                    default: // Lit the block if possible otherwise.
                                        //  ABOUT
                                        // Prob. required for server’s sake that everything is ok and
                                        //  prob. that check is not required.
                                        if (AbstractFireBlock.canPlaceAt(serverWorld, blockPos, direction)) {
                                            BlockState blockState = AbstractFireBlock.getState(serverWorld, blockPos);
                                            serverWorld.setBlockState(blockPos, blockState, Block.NOTIFY_ALL_AND_REDRAW);
                                        }
                                }
                            }
                        }
                    }
                }

                this.playExplosionSound(serverWorld);
                SpawnGhostCreeper(serverWorld, ghostCreeperChance);
            }

            this.onRemoval(serverWorld, RemovalReason.KILLED);
            this.discard();
        }
    }

    @Override
    public boolean hurtByWater() {
        return true;
    }

    @Override
    public boolean isOnFire() {
        return true;
    }

    @Override
    public void tickMovement() {

        if (this.random.nextInt(256) > 252) {
            if (this.getWorld().isClient) {
                this.getWorld().addParticle(
                        ParticleTypes.DRIPPING_LAVA,
                        this.getParticleX(0.5),
                        this.getRandomBodyY(),
                        this.getParticleZ(0.5),
                        (random.nextDouble() - 0.5) * 2.0,
                        -random.nextDouble(),
                        (random.nextDouble() - 0.5) * 2.0
                );
            }

            if (this.getWorld() instanceof ServerWorld serverWorld) {
                serverWorld.playSound(
                        null, this.getX(), this.getY(), this.getZ(),
                        SoundEvents.BLOCK_FIRE_AMBIENT,
                        SoundCategory.HOSTILE,
                        0.8f, 1.2f
                );
            }
        }

        super.tickMovement();
    }

}
