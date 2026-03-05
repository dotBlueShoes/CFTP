package cftp.entity.creeper;

import cftp.entity.base.CreeperElementalEntity;
import cftp.utility.CreeperMath;
import cftp.utility.Shapes;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.world.explosion.ExplosionImpl;

import java.util.List;

public class CreeperDirtEntity extends CreeperElementalEntity {

    protected int ExplosionDiameter = 5;

    public CreeperDirtEntity(
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
        return CreeperMath.CREEPER_TYPE.DIRT.getType();
    }

    private void fillDirt(ServerWorld serverWorld, int diameter, int radius, float x, float y, float z) {
        for (int iy = 0; iy < diameter; ++iy) {
            for (int ix = 0; ix < diameter; ++ix) {
                for (int iz = 0; iz < diameter; ++iz) {
                    if (Shapes.isSphere(ix, iy, iz, radius)) {
                        BlockPos blockPos = BlockPos.ofFloored(
                                x + ix - radius,
                                y + iy - radius,
                                z + iz - radius
                        );

                        BlockState state = serverWorld.getBlockState(blockPos);
                        Block block = state.getBlock();

                        if (block == Blocks.AIR) {
                            serverWorld.setBlockState(blockPos, Blocks.DIRT.getDefaultState(), Block.NOTIFY_ALL);
                        }
                    }
                }
            }
        }
    }

    @Override
    protected void explode() {
        if (this.getWorld() instanceof ServerWorld serverWorld) {
            this.dead = true;

            if (!isDefused()) {
                final Difficulty difficulty = this.getWorld().getDifficulty();
                final float chargedPower = this.isCharged() ? 2.0F : 1.0F;

                // For the case of extending the difficulty enum. We provide a default.
                float diameter = this.ExplosionDiameter;
                int ghostCreeperChance;
                int bury;

                switch (difficulty) {
                    case PEACEFUL:
                    case EASY: {
                        ghostCreeperChance = (int) (255 * GHOST_CREEPER_EXPLODE_CHANCE_EASY);
                        diameter *= chargedPower;
                        bury = 0;
                    }
                    break;
                    case NORMAL: {
                        ghostCreeperChance = (int) (255 * GHOST_CREEPER_EXPLODE_CHANCE_NORMAL);
                        diameter *= 1.25f * chargedPower;
                        bury = 1;
                    }
                    break;
                    case HARD:
                    default: {
                        ghostCreeperChance = (int) (255 * GHOST_CREEPER_EXPLODE_CHANCE_HARD);
                        diameter *= 1.75f * chargedPower;
                        bury = 2;
                    }
                    break;
                }

                final int iDiameter = (int) diameter;
                final int radius = iDiameter / 2;

                {
                    Box box = new Box(
                            this.getX() - radius,
                            this.getY() - radius,
                            this.getZ() - radius,
                            this.getX() + radius,
                            this.getY() + radius,
                            this.getZ() + radius
                    );

                    List<Entity> entities = serverWorld.getOtherEntities(
                            this,
                            box,
                            entity -> entity instanceof LivingEntity living && living.isAlive()
                    );

                    int maxIterations = Math.min(entities.size(), 3);

                    for (int i = 0; i < maxIterations; ++i) {
                        LivingEntity entity = (LivingEntity) entities.get(i);

                        { // Bury
                            BlockPos a = new BlockPos((int) entity.getX(), (int) entity.getY() - 1, (int) entity.getZ());
                            BlockPos b = new BlockPos((int) entity.getX(), (int) entity.getY() - 2, (int) entity.getZ());

                            if (serverWorld.getBlockState(a).getBlock() != Blocks.BEDROCK && serverWorld.getBlockState(b).getBlock() != Blocks.BEDROCK) {
                                entity.refreshPositionAndAngles(entity.getX(), entity.getY() - bury, entity.getZ(), this.getYaw(), this.getPitch());
                            }
                        }

                        fillDirt(serverWorld, iDiameter, radius, (int) entity.getX(), (int) entity.getY() + 1, (int) entity.getZ());

                        serverWorld.playSound(
                                null, this.getX(), this.getY(), this.getZ(),
                                SoundEvents.BLOCK_MUDDY_MANGROVE_ROOTS_BREAK, SoundCategory.HOSTILE,
                                2.2F, 0.3f
                        );
                    }
                }

                this.spawnExplosionParticles(serverWorld);
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
                SoundEvents.ENTITY_BREEZE_WIND_BURST, SoundCategory.HOSTILE,
                0.9F, 0.5f
        );
    }

    private void spawnExplosionParticles (ServerWorld serverWorld) {
        serverWorld.spawnParticles(
                ParticleTypes.POOF,
                this.getX(),
                this.getY() + 1,
                this.getZ(),
                10,
                0, 1, 0,
                0.02
        );
    }

    @Override
    public void tickMovement() {

        if (this.random.nextInt(256) > 240) {
            if (this.getWorld().isClient) {
                this.getWorld().addParticle(
                        ParticleTypes.SCRAPE,
                        this.getParticleX(0.5),
                        this.getRandomBodyY(),
                        this.getParticleZ(0.5),
                        (random.nextDouble() - 0.5) * 2.0,
                        -random.nextDouble(),
                        (random.nextDouble() - 0.5) * 2.0
                );
            }
        }

        super.tickMovement();
    }

}
