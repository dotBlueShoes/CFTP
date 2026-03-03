package cftp.entity.creeper;

import cftp.CFTP;
import cftp.entity.base.CreeperElementalEntity;
import cftp.registries.CFTPEntities;
import cftp.utility.CreeperMath;
import cftp.utility.Shapes;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.world.explosion.ExplosionImpl;

public class CreeperAmalgamEntity extends CreeperElementalEntity {

    protected int ExplosionDiameter = 5;

    public CreeperAmalgamEntity(
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
        return CreeperMath.CREEPER_TYPE.AMALGAM.getType();
    }

    void SpawnCreeper (ServerWorld world, RegistryKey<World> dimension, double x, double y, double z) {

        int iCreeper = this.random.nextInt(CreeperMath.CREEPERS.length);
        var entity = CreeperMath.CREEPERS[iCreeper].create(world, SpawnReason.MOB_SUMMONED);

        EntityData entityData = null;

        if (entity == null) {
            CFTP.LOGGER.warn("Error when spawning a creeper from amalgam.");
            return;
        }

        entity.refreshPositionAndAngles(x, y, z, world.random.nextFloat() * 360.0F, 0.0F);
        entityData = entity.initialize(world, world.getLocalDifficulty(entity.getBlockPos()), SpawnReason.NATURAL, entityData);
        world.spawnEntityAndPassengers(entity);

        world.spawnParticles(ParticleTypes.ENCHANT,
                x, y, z,
                10,
                0.5f, 0.5f, 0.5f,
                0.05f
        );

        // for (int i = 0; i < 1; i++) {
        //            this.getWorld().addParticle(
        //                    ParticleTypes.ENCHANT,
        //                    this.getParticleX(0.5),
        //                    this.getRandomBodyY() + 0.25,
        //                    this.getParticleZ(0.5),
        //                    (this.random.nextDouble() - 0.5) * 2.0,
        //                    -this.random.nextDouble(),
        //                    (this.random.nextDouble() - 0.5) * 2.0
        //            );
        //        }

    }

    @Override // TODO
    protected void explode() {
        if (this.getWorld() instanceof ServerWorld serverWorld) {
            this.dead = true;

            final RegistryKey<World> dimension = serverWorld.getRegistryKey();
            final Difficulty difficulty = serverWorld.getDifficulty();

            final float chargedPower = this.isCharged() ? 2.0F : 1.0F;
            float diameter = this.ExplosionDiameter ;

            int ghostCreeperChance;
            int count;

            switch (difficulty) {
                case PEACEFUL:
                case EASY: {
                    ghostCreeperChance = (int) (255 * GHOST_CREEPER_EXPLODE_CHANCE_EASY);
                    count = 1 + random.nextInt(2); // 1-2
                    diameter *= chargedPower;
                }
                break;
                case NORMAL: {
                    ghostCreeperChance = (int) (255 * GHOST_CREEPER_EXPLODE_CHANCE_NORMAL);
                    count = 2 + random.nextInt(3); // 2-4
                    diameter *= 1.25f * chargedPower;
                }
                break;
                case HARD:
                default: {
                    ghostCreeperChance = (int) (255 * GHOST_CREEPER_EXPLODE_CHANCE_HARD);
                    count = 3 + random.nextInt(3); // 3-5
                    diameter *= 1.50f * chargedPower;
                }
                break;
            }

            // TODO
            //  1. Instead x, y, z should be random so each entity spawns in a random position.

            final int iDiameter = (int) diameter;
            final int randomCallsCount = 10;

            // Try `randomCallsCount` times to summon the creeper at random positions.
            for (int i = randomCallsCount; i > 0 && count > 0; --i) {
                int y = random.nextInt(3);
                int x = random.nextInt(iDiameter);
                int z = random.nextInt(iDiameter);

                BlockPos blockPos = BlockPos.ofFloored(
                        this.getX() + x - 4,
                        this.getY() + y - 1,
                        this.getZ() + z - 4
                );

                BlockState state = serverWorld.getBlockState(blockPos);
                boolean isValid = state.getCollisionShape(serverWorld, blockPos).isEmpty();

                if (isValid) {
                    --count;
                    SpawnCreeper(serverWorld, dimension, blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5);
                }

            }

            // If the above failed to summon all count instead summon them in the position of the creeper.
            for (int i = count; i > 0; --i) {

                // Ensure they do not spawn in wall (because their collision shape is different from a creeper)
                BlockPos blockPosition = BlockPos.ofFloored(
                        this.getX(),
                        this.getY(),
                        this.getZ()
                );

                SpawnCreeper(serverWorld, dimension, blockPosition.getX() + 0.5f, blockPosition.getY() , blockPosition.getZ() + 0.5f);
            }

            this.playExplosionSound(serverWorld);
            this.spawnEffectsCloud();
            this.onRemoval(serverWorld, RemovalReason.KILLED);
            this.discard();

            SpawnGhostCreeper(serverWorld, ghostCreeperChance);
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

    public void createWalkingParticle() {
        for (int i = 0; i < 1; i++) {
            this.getWorld().addParticle(
                    ParticleTypes.ENCHANT,
                    this.getParticleX(0.5),
                    this.getRandomBodyY() + 0.25,
                    this.getParticleZ(0.5),
                    (this.random.nextDouble() - 0.5) * 2.0,
                    -this.random.nextDouble(),
                    (this.random.nextDouble() - 0.5) * 2.0
            );
        }
    }

    @Override
    public void tickMovement() {
        if (this.getWorld().isClient) {
            createWalkingParticle();
        }

        super.tickMovement();
    }

}
