package cftp.entity.creeper;

import cftp.CFTP;
import cftp.entity.base.CreeperElementalEntity;
import cftp.utility.CreeperMath;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;

public class CreeperPiggyEntity extends CreeperElementalEntity {

    protected int ExplosionDiameter = 5;

    public CreeperPiggyEntity(EntityType<? extends CreeperElementalEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.MAX_HEALTH, 20)
                .add(EntityAttributes.MOVEMENT_SPEED, 0.25f)
                .add(EntityAttributes.ATTACK_DAMAGE, 0)
                .add(EntityAttributes.FOLLOW_RANGE, 20);
    }

    @Override
    protected int getElementalCreeperType() {
        return CreeperMath.CREEPER_TYPE.PIGGY.getType();
    }

    void SpawnPig (ServerWorld world, RegistryKey<World> dimension, double x, double y, double z) {

        var entity = (this.isCharged() || dimension == World.NETHER) ?
                EntityType.PIGLIN.create(world, SpawnReason.NATURAL) :
                EntityType.PIG.create(world, SpawnReason.NATURAL);

        EntityData entityData = null;

        if (entity == null) {
            CFTP.LOGGER.warn("Error when creating a Pig.");
            return;
        }

        entity.refreshPositionAndAngles(x, y, z, world.random.nextFloat() * 360.0F, 0.0F);
        entityData = entity.initialize(world, world.getLocalDifficulty(entity.getBlockPos()), SpawnReason.NATURAL, entityData);
        world.spawnEntityAndPassengers(entity);

        world.spawnParticles(ParticleTypes.CLOUD,
                x, y, z,
                5,
                0.5f, 0.5f, 0.5f,
                0.05f
        );

    }


    @Override
    protected void explode() {
        if (this.getWorld() instanceof ServerWorld serverWorld) {
            this.dead = true;

            if (!isDefused()) {
                final RegistryKey<World> dimension = serverWorld.getRegistryKey();
                final Difficulty difficulty = serverWorld.getDifficulty();

                final float chargedPower = this.isCharged() ? 2.0F : 1.0F;
                float diameter = this.ExplosionDiameter;

                int ghostCreeperChance;
                int pigsCount;

                switch (difficulty) {
                    case PEACEFUL:
                    case EASY: {
                        ghostCreeperChance = (int) (255 * GHOST_CREEPER_EXPLODE_CHANCE_EASY);
                        pigsCount = 1 + random.nextInt(2); // 1-2
                        diameter *= chargedPower;
                    }
                    break;
                    case NORMAL: {
                        ghostCreeperChance = (int) (255 * GHOST_CREEPER_EXPLODE_CHANCE_NORMAL);
                        pigsCount = 1 + random.nextInt(3); // 1-3
                        diameter *= 1.25f * chargedPower;
                    }
                    break;
                    case HARD:
                    default: {
                        ghostCreeperChance = (int) (255 * GHOST_CREEPER_EXPLODE_CHANCE_HARD);
                        pigsCount = 2 + random.nextInt(3); // 2-4
                        diameter *= 1.50f * chargedPower;
                    }
                    break;
                }

                // TODO
                //  1. Instead x, y, z should be random so each entity spawns in a random position.
                //  2. If it's nether it could spawn zombie_pig_men ?

                final int iDiameter = (int) diameter;
                final int randomCallsCount = 10;

                // Try `randomCallsCount` times to summon the pig at random positions.
                for (int i = randomCallsCount; i > 0 && pigsCount > 0; --i) {
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
                        --pigsCount;
                        SpawnPig(serverWorld, dimension, blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5);
                    }

                }

                // If the above failed to summon all pigsCount instead summon them in the position of the creeper.
                for (int i = pigsCount; i > 0; --i) {

                    // CFTP.LOGGER.info("call");

                    // Ensure they do not spawn in wall (because their collision shape is different from a creeper)
                    BlockPos blockPosition = BlockPos.ofFloored(
                            this.getX(),
                            this.getY(),
                            this.getZ()
                    );

                    SpawnPig(serverWorld, dimension, blockPosition.getX() + 0.5f, blockPosition.getY(), blockPosition.getZ() + 0.5f);
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
    protected void playExplosionSound(World world) {
        world.playSound(
                null, this.getX(), this.getY(), this.getZ(),
                SoundEvents.ENTITY_BREEZE_WIND_BURST, SoundCategory.HOSTILE,
                0.9F, 0.5f
        );
    }

}
