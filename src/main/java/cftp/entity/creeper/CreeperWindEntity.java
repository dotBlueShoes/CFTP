package cftp.entity.creeper;

import cftp.config.CFTPData;
import cftp.entity.base.CreeperElementalEntity;
import cftp.goals.CreeperElementalIgniteGoal;

import cftp.utility.CreeperMath;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.impl.networking.server.ServerNetworkingImpl;
import net.fabricmc.fabric.mixin.registry.sync.RegistriesAccessor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.passive.OcelotEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.s2c.common.CustomPayloadS2CPacket;
import net.minecraft.network.packet.s2c.play.ExplosionS2CPacket;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Difficulty;
import net.minecraft.world.GameMode;
import net.minecraft.world.World;
import net.minecraft.world.explosion.AdvancedExplosionBehavior;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.world.explosion.ExplosionBehavior;
import net.minecraft.world.explosion.ExplosionImpl;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class CreeperWindEntity extends CreeperElementalEntity {

    protected float power = CFTPData.creeperWind.power;

    public CreeperWindEntity(
            EntityType<? extends CreeperElementalEntity> entityType,
            World world
    ) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.MAX_HEALTH, CFTPData.creeperWind.health)
                .add(EntityAttributes.MOVEMENT_SPEED, CFTPData.creeperWind.movementSpeed)
                .add(EntityAttributes.ATTACK_DAMAGE, 1)
                .add(EntityAttributes.FOLLOW_RANGE, CFTPData.creeperWind.followRange);
    }

    @Override
    protected int getElementalCreeperType() {
        return CreeperMath.CREEPER_TYPE.WIND.getType();
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(2, new CreeperElementalIgniteGoal(this, 4.0F)); // closer
        this.goalSelector.add(3, new FleeEntityGoal<>(this, OcelotEntity.class, 6.0F, 1.0, 1.2));
        this.goalSelector.add(3, new FleeEntityGoal<>(this, CatEntity.class, 6.0F, 1.0, 1.2));
        this.goalSelector.add(4, new MeleeAttackGoal(this, 1.0, false));
        this.goalSelector.add(5, new WanderAroundFarGoal(this, 0.8));
        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(6, new LookAroundGoal(this));
        this.targetSelector.add(1, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.add(2, new RevengeGoal(this));
    }

    @Override
    protected void explode() {
        if (this.getWorld() instanceof ServerWorld serverWorld) {
            this.dead = true;

            if (!isDefused()) {
                final Difficulty difficulty = this.getWorld().getDifficulty();

                float chargedPower = this.isCharged() ? power * 1.5f : power;
                float radius;

                int ghostCreeperChance;

                switch (difficulty) {
                    case PEACEFUL:
                    case EASY: {
                        ghostCreeperChance = (int) (255 * GHOST_CREEPER_EXPLODE_CHANCE_EASY);
                        chargedPower *= 0.68f;
                        radius = 4;
                    }
                    case NORMAL: {
                        ghostCreeperChance = (int) (255 * GHOST_CREEPER_EXPLODE_CHANCE_NORMAL);
                        chargedPower *= 0.75f;
                        radius = 5;
                    }
                    break;
                    case HARD:
                    default: {
                        ghostCreeperChance = (int) (255 * GHOST_CREEPER_EXPLODE_CHANCE_HARD);
                        radius = 6;
                    }
                    break;
                }

                { // air-explosion
                    Box radiusBox = new Box(
                            this.getX() - radius, this.getY() - radius, this.getZ() - radius,
                            this.getX() + radius, this.getY() + radius, this.getZ() + radius
                    );

                    List<Entity> entities = serverWorld.getOtherEntities(
                            this,
                            radiusBox,
                            Entity::isAlive
                    );

                    for (Entity entity : entities) {

                        if (entity instanceof ServerPlayerEntity player) {
                            if (player.interactionManager.getGameMode() == GameMode.CREATIVE) {
                                break;
                            }
                        }

                        entity.addVelocity(0.0, chargedPower, 0.0);
                        entity.velocityModified = true;
                    }
                }

                this.createExplosionParticles(serverWorld);
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

    public void createExplosionParticles(ServerWorld serverWorld) {
        serverWorld.spawnParticles(
                ParticleTypes.END_ROD,
                this.getX(),
                this.getY() + 2,
                this.getZ(),
                30,
                3, 3, 3,
                0.02
        );
    }

    @Override
    protected void playExplosionSound(World world) {
        world.playSound(
                null, this.getX(), this.getY(), this.getZ(),
                SoundEvents.ENTITY_WIND_CHARGE_WIND_BURST, SoundCategory.HOSTILE,
                1.1F, this.random.nextFloat() * 0.4F + 0.8F
        );
    }

    public void createWalkingParticle() {


        // Wind Explode
        //for (int i = 0; i < 1; i++) {
        //    this.getWorld().addParticle(
        //            ParticleTypes.FLASH,
        //            this.getParticleX(0.5),
        //            this.getRandomBodyY() + 0.25,
        //            this.getParticleZ(0.5),
        //            (this.random.nextDouble() - 0.5) * 0.2,
        //            -this.random.nextDouble() * 0.1,
        //            (this.random.nextDouble() - 0.5) * 0.2
        //    );
        //}

        //for (int i = 0; i < 1; i++) {
        //    this.getWorld().addParticle(
        //            ParticleTypes.INFESTED,
        //            this.getParticleX(0.5),
        //            this.getRandomBodyY() + 0.25,
        //            this.getParticleZ(0.5),
        //            (this.random.nextDouble() - 0.5) * 0.2,
        //            -this.random.nextDouble() * 0.1,
        //            (this.random.nextDouble() - 0.5) * 0.2
        //    );
        //}

        // interesting
        //for (int i = 0; i < 1; i++) {
        //    this.getWorld().addParticle(
        //            ParticleTypes.SNEEZE,
        //            this.getParticleX(0.5),
        //            this.getRandomBodyY() + 0.25,
        //            this.getParticleZ(0.5),
        //            (this.random.nextDouble() - 0.5) * 0.2,
        //            -this.random.nextDouble() * 0.1,
        //            (this.random.nextDouble() - 0.5) * 0.2
        //    );
        //}

        // rotation does not matter for some reason...
        if (this.random.nextInt(100) > 75) {
            this.getWorld().addParticle(
                    ParticleTypes.WHITE_ASH,
                    this.getParticleX(0.5),
                    this.getRandomBodyY() + 0.25,
                    this.getParticleZ(0.5),
                    -(this.random.nextDouble() - 0.5) * 0.001,
                    -this.random.nextDouble() * 0.1,
                    -(this.random.nextDouble() - 0.5) * 0.001
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
