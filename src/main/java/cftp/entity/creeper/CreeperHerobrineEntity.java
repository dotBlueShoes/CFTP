package cftp.entity.creeper;

import cftp.config.CFTPData;
import cftp.entity.base.CreeperElementalEntity;
import cftp.utility.CreeperMath;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Portal;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Difficulty;
import net.minecraft.world.GameMode;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;
import net.minecraft.world.dimension.PortalManager;

import java.util.List;

public class CreeperHerobrineEntity extends CreeperElementalEntity {

    protected float power = CFTPData.creeperHerobrine.power;

    public CreeperHerobrineEntity(
            EntityType<? extends CreeperElementalEntity> entityType,
            World world
    ) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.MAX_HEALTH, CFTPData.creeperHerobrine.health)
                .add(EntityAttributes.MOVEMENT_SPEED, CFTPData.creeperHerobrine.movementSpeed)
                .add(EntityAttributes.ATTACK_DAMAGE, 1)
                .add(EntityAttributes.FOLLOW_RANGE, CFTPData.creeperHerobrine.followRange);
    }

    @Override
    protected int getElementalCreeperType() {
        return CreeperMath.CREEPER_TYPE.HEROBRINE.getType();
    }

    @Override // TODO
    protected void explode() {
        if (this.getWorld() instanceof ServerWorld serverWorld) {
            this.dead = true;

            final Difficulty difficulty = this.getWorld().getDifficulty();

            float chargedPower = this.isCharged() ? power * 1.5f : power;
            float radius;

            switch (difficulty) {
                case PEACEFUL:
                case EASY: {
                    chargedPower *= 0.68f;
                    radius = 3;
                }
                case NORMAL: {
                    chargedPower *= 0.75f;
                    radius = 4;
                }
                break;
                case HARD:
                default: {
                    radius = 5;
                }
                break;
            }

            // todo -> theres an offset teleport !!
            { // teleport-explosion
                Box radiusBox = new Box(
                        this.getX() - radius, this.getY() - radius, this.getZ() - radius,
                        this.getX() + radius, this.getY() + radius, this.getZ() + radius
                );

                List<Entity> entities = serverWorld.getOtherEntities(
                        this,
                        radiusBox,
                        Entity::isAlive
                );

                MinecraftServer server = this.getServer();
                ServerWorld targetWorld = server.getWorld(World.NETHER);

                int validY = 33;

                { // todo -> for now only checks for lava and bedrock.
                    for (; validY < 256; ++validY) {
                        BlockPos posLegs = new BlockPos(
                                ((int) this.getX()) / 8,
                                validY,
                                ((int) this.getZ()) / 8
                        );

                        Block target = targetWorld.getBlockState(posLegs).getBlock();

                        // todo-> not perfect, theres a big chance for a column of no air meaning the entities will land on ceiling.
                        if (target == Blocks.AIR) {
                            break;
                        }

                        //if (target != Blocks.LAVA && target != Blocks.BEDROCK) {
                        //    break;
                        //}
                    }
                }

                Vec3d teleportLocation = new Vec3d(
                        (double)((int) this.getX()) / 8,
                        validY,
                        (double)((int) this.getZ()) / 8
                );

                { // prep location

                    BlockPos posGround = new BlockPos(
                            (int) teleportLocation.x,
                            (int) teleportLocation.y - 1,
                            (int) teleportLocation.z
                    );

                    BlockPos posHead = new BlockPos(
                            (int) teleportLocation.x,
                            (int) teleportLocation.y + 1,
                            (int) teleportLocation.z
                    );

                    BlockPos posLegs = new BlockPos(
                            (int) teleportLocation.x,
                            (int) teleportLocation.y,
                            (int) teleportLocation.z
                    );

                    /// TODO -> ensure that this does not remove some important blocks !
                    ///Block target0 = targetWorld.getBlockState(pos0).getBlock();
                    ///Block target1 = targetWorld.getBlockState(pos1).getBlock();
                    ///Block target2 = targetWorld.getBlockState(pos2).getBlock();
                     ///
                    ///if (target0 != Blocks.BEDROCK) {
                    ///
                    ///}

                    targetWorld.setBlockState(posHead, Blocks.AIR.getDefaultState());
                    targetWorld.setBlockState(posLegs, Blocks.AIR.getDefaultState());
                    targetWorld.setBlockState(posGround, Blocks.CHEST.getDefaultState());
                }

                for (Entity entity : entities) {

                    if (entity instanceof ServerPlayerEntity player) {
                        if (player.interactionManager.getGameMode() == GameMode.CREATIVE) {
                            break;
                        }
                    }

                    { // Teleport for all entities
                        TeleportTarget target = new TeleportTarget(
                                targetWorld,
                                teleportLocation,  // position
                                Vec3d.ZERO,
                                0f,
                                0f,
                                TeleportTarget.NO_OP
                        );

                        entity.teleportTo(target);
                    }
                }
            }

            this.playExplosionSound(serverWorld);
            this.spawnEffectsCloud();
            this.onRemoval(serverWorld, RemovalReason.KILLED);
            this.discard();
        }
    }

    @Override
    public void tickMovement() {

        if (this.random.nextInt(256) > 252) {
            if (this.getWorld() instanceof ServerWorld serverWorld) {
                serverWorld.spawnParticles(
                        ParticleTypes.SPIT,
                        this.getParticleX(0.5),
                        this.getRandomBodyY() + 0.25,
                        this.getParticleZ(0.5),
                        1,
                        0.5, 0.5, 0.5,
                        0.02
                );

                serverWorld.playSound(
                        null, this.getX(), this.getY(), this.getZ(),
                        SoundEvents.ENTITY_VILLAGER_NO,
                        SoundCategory.HOSTILE,
                        0.8f, 0.4f
                );
            }
        }

        super.tickMovement();
    }

}
