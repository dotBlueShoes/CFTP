package cftp.entity.creeper;

import cftp.entity.base.CreeperElementalEntity;
import cftp.utility.CreeperMath;
import cftp.utility.PseudoRandom;
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
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.world.explosion.ExplosionImpl;

public class CreeperLavaEntity extends CreeperElementalEntity {

    protected int ExplosionDiameter = 5;

    public CreeperLavaEntity(
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
        return CreeperMath.CREEPER_TYPE.LAVA.getType();
    }

    @Override
    protected void explode() {
        if (this.getWorld() instanceof ServerWorld serverWorld) {
            this.dead = true;

            if (!this.isTouchingWater() && !isDefused()) {
                final Difficulty difficulty = this.getWorld().getDifficulty();

                final float chargedPower = this.isCharged() ? 2.0F : 1.0F;

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

                var seed = (int) this.getX() + (int) this.getY() + (int) this.getZ();

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

                                var pseudoRandom = (Math.abs(seed + (x * iDiameter * iDiameter) + (y * iDiameter) + z)) % 256;
                                var index = PseudoRandom.UNIFORM_PERMUTATION[pseudoRandom] % CreeperMath.LAVA_BLOCKS.length;

                                if (block == Blocks.WATER) {
                                    if (resistance < 100) {
                                        CreeperMath.onGeneralReplace(serverWorld, dummyExplosion, state, Blocks.OBSIDIAN.getDefaultState(), blockPos, (itemStack, pos) -> {
                                            if (this.random.nextInt(100) < dropExplosionItemChance) {
                                                Block.dropStack(serverWorld, blockPos, itemStack);
                                            }
                                        });
                                    }
                                } else {
                                    if (resistance < 100) {
                                        CreeperMath.onGeneralReplace(serverWorld, dummyExplosion, state, CreeperMath.LAVA_BLOCKS[index], blockPos, (itemStack, pos) -> {
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
    public boolean hurtByWater() {
        return true;
    }

    @Override
    public boolean isOnFire() {
        return true;
    }

    //public void createWalkingParticle() {
    //
    //    Random random = this.random;
    //
    //    if (random.nextInt(10) > 6) {
    //        this.getWorld().addParticle(
    //                ParticleTypes.FALLING_LAVA,
    //                this.getParticleX(0.50),
    //                this.getRandomBodyY(),
    //                this.getParticleZ(0.50),
    //                (random.nextDouble() - 0.5),
    //                -random.nextDouble() / 2.0,
    //                (random.nextDouble() - 0.5)
    //        );
    //    }
    //}

    @Override
    public void tickMovement() {

        if (this.getWorld() instanceof ServerWorld serverWorld) {
            if (this.random.nextInt(256) > 252) {
                serverWorld.playSound(
                        null, this.getX(), this.getY(), this.getZ(),
                        SoundEvents.BLOCK_FIRE_AMBIENT,
                        SoundCategory.HOSTILE,
                        0.9f, 1.3f
                );
            }
        } else {
            if (this.random.nextInt(256) > 128) {
                this.getWorld().addParticle(
                        ParticleTypes.FALLING_LAVA,
                        this.getParticleX(0.7),
                        this.getRandomBodyY(),
                        this.getParticleZ(0.7),
                        (random.nextDouble() - 0.5) * 2.0,
                        -random.nextDouble(),
                        (random.nextDouble() - 0.5) * 2.0
                );
            }
        }

        super.tickMovement();
    }

}
