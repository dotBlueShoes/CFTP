package cftp.entity.creeper;

import cftp.config.CFTPData;
import cftp.entity.base.CreeperElementalEntity;
import cftp.utility.CreeperHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.EndermiteEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.network.packet.s2c.play.PositionFlag;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Difficulty;
import net.minecraft.world.GameRules;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;

import java.util.List;

public class CreeperEnderEntity extends CreeperElementalEntity {

    // todo. do something with that value actually.
    protected float power = CFTPData.creeperEnder.power;

    public CreeperEnderEntity(
            EntityType<? extends CreeperEnderEntity> entityType,
            World world
    ) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return HostileEntity.createHostileAttributes()
                .add(EntityAttributes.MAX_HEALTH, CFTPData.creeperEnder.health)
                .add(EntityAttributes.MOVEMENT_SPEED, CFTPData.creeperEnder.movementSpeed)
                .add(EntityAttributes.ATTACK_DAMAGE, 0)
                .add(EntityAttributes.FOLLOW_RANGE, CFTPData.creeperEnder.followRange);

    }

    @Override
    protected int getElementalCreeperType() {
        return CreeperHelper.CREEPER_TYPE.ENDER.getType();
    }

    @Override
    protected void explode() {
        final World world = this.getWorld();

        if (world instanceof ServerWorld serverWorld) {
            this.dead = true;

            if (!this.isTouchingWater() && !isDefused()) {
                final Difficulty difficulty = serverWorld.getDifficulty();

                int chargedAmount = this.isCharged() ? 2 : 1;
                int ghostCreeperChance;
                float sumPower;

                Box box = new Box(
                        this.getX() - this.power * chargedAmount,
                        this.getY() - this.power * chargedAmount,
                        this.getZ() - this.power * chargedAmount,
                        this.getX() + this.power * chargedAmount,
                        this.getY() + this.power * chargedAmount,
                        this.getZ() + this.power * chargedAmount
                );

                // Secure teleport for players
                List<ServerPlayerEntity> players = serverWorld.getPlayers(
                        p -> p.getBoundingBox().intersects(box)
                );

                // Non-secure tp for entities. (to last player teleport)
                List<Entity> nonPlayers = serverWorld.getOtherEntities(
                        this,
                        box,
                        entity -> !(entity instanceof ServerPlayerEntity)
                );

                // Create teleport particles at creeper's position.
                createAfterTeleportParticles(serverWorld, this.getX(), this.getY(), this.getZ());

                switch (difficulty) {
                    case PEACEFUL:
                    case EASY: {
                        sumPower = (this.power * 0.33f) * chargedAmount * 0.50f;

                        double[] np = CreeperHelper.getNonHardDifficultyTeleportPos(
                                serverWorld, this.random, this.getPos(), sumPower
                        );

                        // Secure teleport for players
                        for (ServerPlayerEntity serverPlayerEntity : players) {
                            np = CreeperHelper.getNonHardDifficultyTeleportPos(
                                    serverWorld, this.random, this.getPos(), sumPower
                            );
                            final double x = np[0]; final double y = np[1]; final double z = np[2];
                            this.teleportServerPlayer(serverWorld, serverPlayerEntity, x, y, z);
                            this.createAfterTeleportParticles(serverWorld, x, y, z);
                            this.createAfterTeleportSound(serverWorld, x, y, z);
                        }

                        // Non-secure tp for entities. (to last player teleport)
                        for (Entity entity : nonPlayers) {
                            final double x = np[0]; final double y = np[1]; final double z = np[2];
                            this.teleportEntity(serverWorld, entity, x, y, z);
                        }

                        ghostCreeperChance = (int) (255 * GHOST_CREEPER_EXPLODE_CHANCE_EASY);
                    } break;
                    case NORMAL: {
                        sumPower = (this.power * 0.45f) * chargedAmount * 0.66f;

                        double[] np = CreeperHelper.getNonHardDifficultyTeleportPos(
                                serverWorld, this.random, this.getPos(), sumPower
                        );

                        // Secure teleport for players
                        for (ServerPlayerEntity serverPlayerEntity : players) {
                            np = CreeperHelper.getNonHardDifficultyTeleportPos(
                                    serverWorld, this.random, this.getPos(), sumPower
                            );
                            final double x = np[0]; final double y = np[1]; final double z = np[2];
                            this.teleportServerPlayer(serverWorld, serverPlayerEntity, x, y, z);
                            this.createAfterTeleportParticles(serverWorld, x, y, z);
                            this.createAfterTeleportSound(serverWorld, x, y, z);
                        }

                        // Non-secure tp for entities. (to last player teleport)
                        for (Entity entity : nonPlayers) {
                            final double x = np[0]; final double y = np[1]; final double z = np[2];
                            this.teleportEntity(serverWorld, entity, x, y, z);
                        }

                        ghostCreeperChance = (int) (255 * GHOST_CREEPER_EXPLODE_CHANCE_NORMAL);
                    } break;
                    case HARD:
                    default: {
                        sumPower = (this.power * 0.66f) * chargedAmount * 0.75f;

                        double[] np = CreeperHelper.getNonHardDifficultyTeleportPos(
                                serverWorld, this.random, this.getPos(), sumPower
                        );

                        for (ServerPlayerEntity serverPlayerEntity : players) {
                            np = CreeperHelper.getHardDifficultyTeleportPos(
                                    serverWorld, this.random, this.getPos(), sumPower
                            );
                            final double x = np[0]; final double y = np[1]; final double z = np[2];
                            this.teleportServerPlayer(serverWorld, serverPlayerEntity, x, y, z);
                            this.createAfterTeleportParticles(serverWorld, x, y, z);
                            this.createAfterTeleportSound(serverWorld, x, y, z);
                        }

                        for (Entity entity : nonPlayers) {
                            final double x = np[0]; final double y = np[1]; final double z = np[2];
                            this.teleportEntity(serverWorld, entity, x, y, z);
                        }

                        ghostCreeperChance = (int) (255 * GHOST_CREEPER_EXPLODE_CHANCE_HARD);
                    }
                }

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
    public void tickMovement() {
        if (this.getWorld().isClient) {
            createWalkingParticle();
        }
        super.tickMovement();
    }

    public void createWalkingParticle() {
        this.getWorld().addParticle(
                ParticleTypes.PORTAL,
                this.getParticleX(0.5),
                this.getRandomBodyY() + 0.25,
                this.getParticleZ(0.5),
                (this.random.nextDouble() - 0.5) * 2.0,
                -this.random.nextDouble(),
                (this.random.nextDouble() - 0.5) * 2.0
        );
    }

    private static boolean isTeleportEntityPossible(Entity entity, World world) {
        if (entity.getWorld().getRegistryKey() == world.getRegistryKey()) {
            return !(entity instanceof LivingEntity livingEntity) ?
                    entity.isAlive() :
                    livingEntity.isAlive() && !livingEntity.isSleeping();
        } else {
            return entity.canUsePortals(true);
        }
    }

    // Check if serverPlayer is null before.
    public void teleportServerPlayer(ServerWorld serverWorld, ServerPlayerEntity serverPlayer, double x, double y, double z) {
        if (isTeleportEntityPossible(serverPlayer, serverWorld)) {
            if (serverPlayer.hasVehicle()) {
                serverPlayer.detach();
            }

            if (serverPlayer.networkHandler.isConnectionOpen()) {
                // Create Endermite.
                if (this.random.nextFloat() < 0.05F && serverWorld.getGameRules().getBoolean(GameRules.DO_MOB_SPAWNING)) {
                    EndermiteEntity endermiteEntity = EntityType.ENDERMITE.create(serverWorld, SpawnReason.TRIGGERED);
                    if (endermiteEntity != null) {
                        endermiteEntity.refreshPositionAndAngles(serverPlayer.getX(), serverPlayer.getY(), serverPlayer.getZ(), serverPlayer.getYaw(), serverPlayer.getPitch());
                        serverWorld.spawnEntity(endermiteEntity);
                    }
                }

                this.teleportEntity(serverWorld, serverPlayer, x, y, z);
            }
        }
    }

    public void teleportEntity(ServerWorld serverWorld, Entity entity, double x, double y, double z) {
        Vec3d newPosition = new Vec3d(x, y, z);

        Entity teleportedEntity = entity.teleportTo(
                new TeleportTarget(
                        serverWorld,
                        newPosition,
                        Vec3d.ZERO,
                        0.0F, 0.0F, PositionFlag.combine(PositionFlag.ROT, PositionFlag.DELTA),
                        TeleportTarget.NO_OP
                )
        );

        if (teleportedEntity != null) {
            teleportedEntity.onLanding();
            teleportedEntity.damage(serverWorld, this.getDamageSources().enderPearl(), 5.0F);
        }
    }

    private void createAfterTeleportSound(ServerWorld world, double x, double y, double z) {
        world.playSound(
                null, x, y, z,
                SoundEvents.ENTITY_PLAYER_TELEPORT,
                SoundCategory.PLAYERS
        );
    }

    private void createAfterTeleportParticles(ServerWorld world, double x, double y, double z) {
        world.spawnParticles(ParticleTypes.PORTAL,
                x + 0.5, y + 0.5, z + 0.5,
                24,
                0.5f, 0.5f, 0.5f,
                0.25f
        );
    }

}
