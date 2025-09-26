package cftp.entity.creeper;

import cftp.CFTP;
import cftp.entity.CFTPEntities;
import cftp.entity.base.CreeperElementalEntity;
import cftp.goals.CreeperBallisticAttackGoal;
import cftp.goals.CreeperElementalIgniteGoal;
import cftp.utility.CreeperMath;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.passive.OcelotEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
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

    void SpawnPig (ServerWorld world, double x, double y, double z) {
        var entity = EntityType.PIG.create(world, SpawnReason.NATURAL);
        EntityData entityData = null;

        if (entity == null) {
            CFTP.LOGGER.warn("Error when creating a Pig.");
            return;
        }

        entity.refreshPositionAndAngles(x, y, z, world.random.nextFloat() * 360.0F, 0.0F);
        entityData = entity.initialize(world, world.getLocalDifficulty(entity.getBlockPos()), SpawnReason.NATURAL, entityData);
        world.spawnEntityAndPassengers(entity);
    }


    @Override
    protected void explode() {
        if (this.getWorld() instanceof ServerWorld serverWorld) {
            this.dead = true;

            final Difficulty difficulty = this.getWorld().getDifficulty();

            final float chargedPower = this.isCharged() ? 2.0F : 1.0F;
            float diameter = this.ExplosionDiameter ;

            int ghostCreeperChance;
            int pigsCount;

            switch (difficulty) {
                case PEACEFUL:
                case EASY: {
                    ghostCreeperChance = (int) (255 * GHOST_CREEPER_EXPLODE_CHANCE_EASY);
                    pigsCount = 1 + random.nextInt(1);
                    diameter *= chargedPower;
                }
                break;
                case NORMAL: {
                    ghostCreeperChance = (int) (255 * GHOST_CREEPER_EXPLODE_CHANCE_NORMAL);
                    pigsCount = 1 + random.nextInt(2);
                    diameter *= 1.25f * chargedPower;
                }
                break;
                case HARD:
                default: {
                    ghostCreeperChance = (int) (255 * GHOST_CREEPER_EXPLODE_CHANCE_HARD);
                    pigsCount = 2 + random.nextInt(2);
                    diameter *= 1.50f * chargedPower;
                }
                break;
            }

            // TODO
            //  1. Instead x, y, z should be random so each entity spawns in a random position.
            //  2. If it's nether it could spawn zombie_pig_men ?

            outer: for (int y = 0; y < 3; ++y) {
                for (int x = 0; x < 9; ++x) {
                    for (int z = 0; z < 9; ++z) {

                        if (pigsCount <= 0) break outer;

                        BlockPos blockPos = BlockPos.ofFloored(
                                this.getX() + x - 4,
                                this.getY() + y - 1,
                                this.getZ() + z - 4
                        );

                        BlockState state = serverWorld.getBlockState(blockPos);

                        ///  Spawn in air is allowed (even preferred) !
                        ///boolean isValidGround = state.getCollisionShape(serverWorld, blockPos).isEmpty()
                        ///        && serverWorld.getBlockState(blockPos.down())
                        ///            .isSolidBlock(serverWorld, blockPos.down());

                        boolean isValid = state.getCollisionShape(serverWorld, blockPos).isEmpty();

                        if (isValid) {
                            --pigsCount;
                            SpawnPig(serverWorld, blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5);
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

}
