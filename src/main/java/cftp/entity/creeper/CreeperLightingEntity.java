package cftp.entity.creeper;

import cftp.config.CFTPData;
import cftp.entity.base.CreeperElementalEntity;
import cftp.goals.CreeperElementalIgniteGoal;
import cftp.registries.CFTPParticles;
import cftp.registries.CFTPStatusEffects;
import cftp.statuseffects.ConductiveStatusEffect;
import cftp.utility.CreeperHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.passive.OcelotEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.command.WeatherCommand;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Box;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;

import java.util.List;

public class CreeperLightingEntity extends CreeperElementalEntity {

    protected float power = CFTPData.creeperLighting.power;

    // TODO
    // 1. For lighting creeper implement own "ExplosionImpl" class.
    // 2. Make the lighting always hit up world
    // 3. Mimic serverWorld.createExplosion function as the lighting should:
    //  a) deal dmg
    //  b) create a fire
    //  c) make a little hole

    public CreeperLightingEntity(
            EntityType<? extends CreeperElementalEntity> entityType,
            World world
    ) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.MAX_HEALTH, CFTPData.creeperLighting.health)
                .add(EntityAttributes.MOVEMENT_SPEED, CFTPData.creeperLighting.movementSpeed)
                .add(EntityAttributes.ATTACK_DAMAGE, 10)
                .add(EntityAttributes.FOLLOW_RANGE, CFTPData.creeperLighting.followRange);
    }

    @Override
    protected int getElementalCreeperType() {
        return CreeperHelper.CREEPER_TYPE.LIGHTING.getType();
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(2, new CreeperElementalIgniteGoal(this, 1.0F));
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
                final float chargedPower = this.isCharged() ? 2.0F : 1.0F;

                int ghostCreeperChance;
                int conductiveLevel;
                float sumPower;

                switch (difficulty) {
                    case PEACEFUL:
                    case EASY: {
                        ghostCreeperChance = (int) (255 * GHOST_CREEPER_EXPLODE_CHANCE_EASY);
                        conductiveLevel = 0;

                        serverWorld.setWeather(0, 20 * 15, true, false);
                    } break;
                    case NORMAL: {
                        ghostCreeperChance = (int) (255 * GHOST_CREEPER_EXPLODE_CHANCE_NORMAL);
                        conductiveLevel = 1;

                        serverWorld.setWeather(0, 20 * 23, true, false);
                    } break;
                    case HARD:
                    default: {
                        ghostCreeperChance = (int) (255 * GHOST_CREEPER_EXPLODE_CHANCE_HARD);
                        conductiveLevel = 2;

                        serverWorld.setWeather(0, 20 * 31, true, true);
                    }
                }

                sumPower = power * chargedPower;

                if (serverWorld.isSkyVisible(this.getBlockPos())) { // Lighting Bolt
                    LightningEntity lightningEntity = EntityType.LIGHTNING_BOLT.create(serverWorld, SpawnReason.EVENT);
                    if (lightningEntity != null) {
                        lightningEntity.refreshPositionAfterTeleport(this.getX(), this.getY(), this.getZ());
                        lightningEntity.setCosmetic(true);
                        serverWorld.spawnEntity(lightningEntity);
                    }
                } else {
                    sumPower *= 0.5f; // No lighting so the power also weakens greatly.
                }

                if (conductiveLevel != 0) { // apply Conductive Status Effect to all entities around.
                    Box box = new Box(
                            this.getX() - sumPower,
                            this.getY() - sumPower,
                            this.getZ() - sumPower,
                            this.getX() + sumPower,
                            this.getY() + sumPower,
                            this.getZ() + sumPower
                    );

                    List<Entity> entities = serverWorld.getOtherEntities(
                            this,
                            box,
                            entity -> entity instanceof LivingEntity living && living.isAlive()
                    );

                    for (Entity entity : entities) {
                        int strikeTick = (this.random.nextInt(ConductiveStatusEffect.EFFECT_DURATION) + 1);
                        LivingEntity target = (LivingEntity) entity;

                        target.addStatusEffect(
                                new StatusEffectInstance(
                                        CFTPStatusEffects.CONDUCTIVE_STATUS_EFFECT,
                                        ConductiveStatusEffect.EFFECT_DURATION + strikeTick,
                                        conductiveLevel - 1,
                                        true,
                                        true,
                                        true
                                )
                        );
                    }
                }

                serverWorld.createExplosion(
                        this, this.getX(), this.getY(), this.getZ(),
                        sumPower, World.ExplosionSourceType.MOB
                );

                SpawnGhostCreeper(serverWorld, ghostCreeperChance);
            } else {
                this.playDefusedExplosionSound(serverWorld);
            }

            this.spawnEffectsCloud();
            this.onRemoval(serverWorld, Entity.RemovalReason.KILLED);
            this.discard();
        }
    }

    public void createWalkingParticle() {
        if (this.random.nextInt(100) > 90) {
            for (int i = 0; i < 1; i++) {
                this.getWorld().addParticle(
                        //ParticleTypes.INFESTED,
                        CFTPParticles.ELECTRIC_PARTICLE,
                        this.getParticleX(0.5),
                        this.getRandomBodyY() + 0.25,
                        this.getParticleZ(0.5),
                        0.0f,//(this.random.nextDouble() - 0.5) * 0.01,
                        0.0f,//-this.random.nextDouble() * 0.001,
                        0.0f//(this.random.nextDouble() - 0.5) * 0.01
                );
            }
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
