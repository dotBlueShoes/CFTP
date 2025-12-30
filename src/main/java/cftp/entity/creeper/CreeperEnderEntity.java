package cftp.entity.creeper;

import cftp.CFTP;
import cftp.entity.base.CreeperElementalEntity;
import cftp.utility.CreeperMath;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.mob.EndermiteEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.s2c.play.PositionFlag;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Difficulty;
import net.minecraft.world.GameRules;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;

public class CreeperEnderEntity extends CreeperElementalEntity {

    public CreeperEnderEntity(
            EntityType<? extends CreeperEnderEntity> entityType,
            World world
    ) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        //return HostileEntity.createHostileAttributes().add(EntityAttributes.MOVEMENT_SPEED, 0.25);
        return HostileEntity.createHostileAttributes()
                .add(EntityAttributes.MAX_HEALTH, 20)
                .add(EntityAttributes.MOVEMENT_SPEED, 0.25f)
                .add(EntityAttributes.ATTACK_DAMAGE, 0)
                .add(EntityAttributes.FOLLOW_RANGE, 20);

    }

    @Override
    protected int getElementalCreeperType() {
        return CreeperMath.CREEPER_TYPE.ENDER.getType();
    }

    //private void teleportEntity(LivingEntity entity, double x, double y, double z) {
    //    BlockPos.Mutable mutable = new BlockPos.Mutable(x, y, z);
    //
    //    while (mutable.getY() > entity.getWorld().getBottomY() && !entity.getWorld().getBlockState(mutable).blocksMovement()) {
    //        mutable.move(Direction.DOWN);
    //    }
    //
    //    BlockState blockState = entity.getWorld().getBlockState(mutable);
    //
    //    boolean isInFluid = blockState.getFluidState().isIn(FluidTags.WATER);
    //    boolean isValidBlock = blockState.blocksMovement();
    //
    //    if (isValidBlock && !isInFluid) {
    //
    //        Vec3d vec3d = entity.getPos();
    //
    //        boolean teleportSuccess = entity.teleport(x, y, z, true);
    //
    //        if (teleportSuccess) {
    //
    //            entity.getWorld().emitGameEvent(
    //                    GameEvent.TELEPORT,
    //                    vec3d,
    //                    GameEvent.Emitter.of(entity)
    //            );
    //
    //            if (!entity.isSilent()) {
    //
    //                entity.getWorld().playSound(
    //                        null,
    //                        entity.prevX, entity.prevY, entity.prevZ,
    //                        SoundEvents.ENTITY_ENDERMAN_TELEPORT,
    //                        entity.getSoundCategory(),
    //                        1.0F, 1.0F
    //                );
    //
    //                entity.playSound(
    //                        SoundEvents.ENTITY_ENDERMAN_TELEPORT,
    //                        1.0F,
    //                        1.0F
    //                );
    //
    //                CFTP.LOGGER.warn("FINE!");
    //
    //            } else {
    //                CFTP.LOGGER.warn("1. Failed To Teleport with explosion of creeper_ender.");
    //            }
    //
    //        } else {
    //            CFTP.LOGGER.warn("2. Failed To Teleport with explosion of creeper_ender.");
    //        }
    //
    //    } else {
    //        CFTP.LOGGER.warn("3. Failed To Teleport with explosion of creeper_ender.");
    //    }
    //}

    //protected boolean teleportRandomly() {
    //    if (!this.getWorld().isClient() && this.isAlive()) {
    //        double d = this.getX() + (this.random.nextDouble() - 0.5) * 64.0;
    //        double e = this.getY() + (this.random.nextInt(64) - 32);
    //        double f = this.getZ() + (this.random.nextDouble() - 0.5) * 64.0;
    //        return this.teleportTo(d, e, f);
    //    } else {
    //        return false;
    //    }
    //}

    //protected void onCollision(HitResult hitResult) {
    //		super.onCollision(hitResult);
    //
    //		for (int i = 0; i < 32; i++) {
    //			this.getWorld()
    //				.addParticle(
    //					ParticleTypes.PORTAL, this.getX(), this.getY() + this.random.nextDouble() * 2.0, this.getZ(), this.random.nextGaussian(), 0.0, this.random.nextGaussian()
    //				);
    //		}
    //
    //		if (this.getWorld() instanceof ServerWorld serverWorld && !this.isRemoved()) {
    //			Entity entity = this.getOwner();
    //			if (entity != null && canTeleportEntityTo(entity, serverWorld)) {
    //				if (entity.hasVehicle()) {
    //					entity.detach();
    //				}
    //
    //				Vec3d vec3d = this.getLastRenderPos();
    //				if (entity instanceof ServerPlayerEntity serverPlayerEntity) {
    //					if (serverPlayerEntity.networkHandler.isConnectionOpen()) {
    //						if (this.random.nextFloat() < 0.05F && serverWorld.getGameRules().getBoolean(GameRules.DO_MOB_SPAWNING)) {
    //							EndermiteEntity endermiteEntity = EntityType.ENDERMITE.create(serverWorld, SpawnReason.TRIGGERED);
    //							if (endermiteEntity != null) {
    //								endermiteEntity.refreshPositionAndAngles(entity.getX(), entity.getY(), entity.getZ(), entity.getYaw(), entity.getPitch());
    //								serverWorld.spawnEntity(endermiteEntity);
    //							}
    //						}
    //
    //						if (this.hasPortalCooldown()) {
    //							entity.resetPortalCooldown();
    //						}
    //
    //						ServerPlayerEntity serverPlayerEntity2 = serverPlayerEntity.teleportTo(
    //							new TeleportTarget(serverWorld, vec3d, Vec3d.ZERO, 0.0F, 0.0F, PositionFlag.combine(PositionFlag.ROT, PositionFlag.DELTA), TeleportTarget.NO_OP)
    //						);
    //						if (serverPlayerEntity2 != null) {
    //							serverPlayerEntity2.onLanding();
    //							serverPlayerEntity2.clearCurrentExplosion();
    //							serverPlayerEntity2.damage(serverPlayerEntity.getServerWorld(), this.getDamageSources().enderPearl(), 5.0F);
    //						}
    //
    //						this.playTeleportSound(serverWorld, vec3d);
    //					}
    //				} else {
    //					Entity entity2 = entity.teleportTo(new TeleportTarget(serverWorld, vec3d, entity.getVelocity(), entity.getYaw(), entity.getPitch(), TeleportTarget.NO_OP));
    //					if (entity2 != null) {
    //						entity2.onLanding();
    //					}
    //
    //					this.playTeleportSound(serverWorld, vec3d);
    //				}
    //
    //				this.discard();
    //			} else {
    //				this.discard();
    //			}
    //		}
    //	}

    private static boolean isTeleportEntityPossible(Entity entity, World world) {
        if (entity.getWorld().getRegistryKey() == world.getRegistryKey()) {
            return !(entity instanceof LivingEntity livingEntity) ?
                    entity.isAlive() :
                    livingEntity.isAlive() && !livingEntity.isSleeping();
        } else {
            return entity.canUsePortals(true);
        }
    }

    // Check if entity is null before.
    public void teleportEntity(ServerWorld serverWorld, ServerPlayerEntity entity, double x, double y, double z) {
        if (isTeleportEntityPossible(entity, serverWorld)) {

            //CFTP.LOGGER.warn("Teleporting Entity...");

            if (entity.hasVehicle()) {
                entity.detach();
            }

            Vec3d newPosition = new Vec3d(x, y, z);

            //if (entity instanceof ServerPlayerEntity serverPlayerEntity) {
                if (entity.networkHandler.isConnectionOpen()) {

                    // Create Endermite.
                    if (this.random.nextFloat() < 0.05F && serverWorld.getGameRules().getBoolean(GameRules.DO_MOB_SPAWNING)) {
                        EndermiteEntity endermiteEntity = EntityType.ENDERMITE.create(serverWorld, SpawnReason.TRIGGERED);
                        if (endermiteEntity != null) {
                            endermiteEntity.refreshPositionAndAngles(entity.getX(), entity.getY(), entity.getZ(), entity.getYaw(), entity.getPitch());
                            serverWorld.spawnEntity(endermiteEntity);
                        }
                    }

                    //if (this.hasPortalCooldown()) {
                    //    entity.resetPortalCooldown();
                    //}

                    ServerPlayerEntity teleportedEntity = entity.teleportTo(
                            new TeleportTarget(serverWorld, newPosition, Vec3d.ZERO, 0.0F, 0.0F, PositionFlag.combine(PositionFlag.ROT, PositionFlag.DELTA), TeleportTarget.NO_OP)
                    );

                    if (teleportedEntity != null) {
                        teleportedEntity.onLanding();
                        teleportedEntity.clearCurrentExplosion();
                        teleportedEntity.damage(entity.getServerWorld(), this.getDamageSources().enderPearl(), 5.0F);
                    }

                    //this.playTeleportSound(serverWorld, newPosition);
                }
            //} else {
            //
            //    Entity entity2 = entity.teleportTo(new TeleportTarget(serverWorld, newPosition, entity.getVelocity(), entity.getYaw(), entity.getPitch(), TeleportTarget.NO_OP));
            //    if (entity2 != null) {
            //        entity2.onLanding();
            //    }
            //
            //    //this.playTeleportSound(serverWorld, newPosition);
            //}

        }
    }

    // On HARD difficulty u can be moved into LAVA and be teleported into mid-air.
    public void getHardDifficultyTeleport(
            ServerWorld serverWorld,
            ServerPlayerEntity serverPlayerEntity,
            double chargedValue
    ) {

        double x = this.getX() + (this.random.nextDouble() - 0.5) * 64.0 * chargedValue;
        double y = this.getY() + (this.random.nextInt(64) - 32) * chargedValue;
        double z = this.getZ() + (this.random.nextDouble() - 0.5) * 64.0 * chargedValue;

        // Don't fall into void.
        if (y < serverWorld.getBottomY()) {
            y = serverWorld.getBottomY();
        }

        while (true) { // Be sure it's not in block. MID-AIR is a valid position!
            BlockPos position = BlockPos.ofFloored(x, y, z);

            BlockState blockState = serverWorld.getBlockState(position);

            if (!blockState.isSolidBlock(serverWorld, position)) {
                break;
            }

            ++y;
        }

        this.teleportEntity(serverWorld, serverPlayerEntity, x, y, z);

        // Create particles and sound at the position player teleported to.
        createTeleportParticles(serverWorld, x, y, z);
        createTeleportSound(serverWorld, x, y, z);
    }

    public void getNonHardDifficultyTeleport(
            ServerWorld serverWorld,
            ServerPlayerEntity serverPlayerEntity,
            double chargedValue
    ) {

        double x = this.getX() + (this.random.nextDouble() - 0.5) * 64.0 * chargedValue;
        double y = this.getY() + (this.random.nextInt(64) - 32) * chargedValue;
        double z = this.getZ() + (this.random.nextDouble() - 0.5) * 64.0 * chargedValue;

        // Don't fall into void.
        if (y < serverWorld.getBottomY()) {
            y = serverWorld.getBottomY();
        }

        while (true) { // Be sure it's not in block. MID-AIR is a valid position!
            BlockPos position = BlockPos.ofFloored(x, y, z);

            BlockState blockState = serverWorld.getBlockState(position);
            boolean isInLava = blockState.getFluidState().isIn(FluidTags.LAVA);

            if (!blockState.isSolidBlock(serverWorld, position) && !isInLava) {
                break;
            }

            ++y;
        }

        while (true) { // Do not allow midair teleports.
            BlockPos blockPos = BlockPos.ofFloored(x, y - 1, z);

            if (serverWorld.getBlockState(blockPos).getBlock() != Blocks.AIR) {
                break;
            }

            --y;

            if (y < serverWorld.getBottomY()) {
                CFTP.LOGGER.info("Could not find a valid teleport position.");

                serverWorld.playSound(
                        null, x, y, z,
                        SoundEvents.ENTITY_PLAYER_TELEPORT,
                        SoundCategory.PLAYERS
                );

                return;
            }
        }

        this.teleportEntity(serverWorld, serverPlayerEntity, x, y, z);

        // Create particles and sound at the position player teleported to.
        createTeleportParticles(serverWorld, x, y, z);
        createTeleportSound(serverWorld, x, y, z);
    }

    private void createTeleportSound (ServerWorld world, double x, double y, double z) {
        world.playSound(
                null, x, y, z,
                SoundEvents.ENTITY_PLAYER_TELEPORT,
                SoundCategory.PLAYERS
        );
    }

    private void createTeleportParticles(ServerWorld world, double x, double y, double z) {
        world.spawnParticles(ParticleTypes.PORTAL,
                x + 0.5, y + 0.5, z + 0.5,
                24,
                0.5f, 0.5f, 0.5f,
                0.25f
        );
    }

    @Override
    protected void explode() {
        final World world = this.getWorld();

        if (world instanceof ServerWorld serverWorld) {
            this.dead = true;

            final Difficulty difficulty = serverWorld.getDifficulty();

            int chargedAmount = this.isCharged() ? 3 : 1;
            int ghostCreeperChance = (int)(255 * GHOST_CREEPER_EXPLODE_CHANCE_EASY);

            // Create teleport particles at creeper position.
            createTeleportParticles(serverWorld, this.getX(), this.getY(), this.getZ());

            switch (difficulty) {
                case PEACEFUL:
                case EASY: {

                    for (ServerPlayerEntity serverPlayerEntity : serverWorld.getPlayers()) {
                        getNonHardDifficultyTeleport(serverWorld, serverPlayerEntity, chargedAmount);
                    }

                }

                case NORMAL: {

                    for (ServerPlayerEntity serverPlayerEntity : serverWorld.getPlayers()) {
                        getNonHardDifficultyTeleport(serverWorld, serverPlayerEntity, chargedAmount);
                    }

                    ghostCreeperChance = (int)(255 * GHOST_CREEPER_EXPLODE_CHANCE_NORMAL);

                } break;

                case HARD:
                default:{

                    for (ServerPlayerEntity serverPlayerEntity : serverWorld.getPlayers()) {
                        getHardDifficultyTeleport(serverWorld, serverPlayerEntity, chargedAmount);
                    }

                    ghostCreeperChance = (int)(255 * GHOST_CREEPER_EXPLODE_CHANCE_HARD);

                } break;
            }

            this.spawnEffectsCloud();
            this.onRemoval(serverWorld, RemovalReason.KILLED);
            this.discard();

            SpawnGhostCreeper(serverWorld, ghostCreeperChance);
        }
    }

    @Override
    public boolean hurtByWater() {
        return true;
    }

    public void createWalkingParticle() {
        for (int i = 0; i < 1; i++) {
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
    }

    @Override
    public void tickMovement() {
        if (this.getWorld().isClient) {
            createWalkingParticle();
        }

        //this.jumping = false;
        //if (!this.getWorld().isClient) {
        //    this.tickAngerLogic((ServerWorld)this.getWorld(), true);
        //}

        super.tickMovement();
    }

}
