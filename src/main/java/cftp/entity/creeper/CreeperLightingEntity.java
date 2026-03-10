package cftp.entity.creeper;

import cftp.config.CFTPData;
import cftp.entity.base.CreeperElementalEntity;
import cftp.goals.CreeperElementalIgniteGoal;
import cftp.utility.CreeperMath;
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
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;

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
        return CreeperMath.CREEPER_TYPE.LIGHTING.getType();
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

                switch (difficulty) {
                    case PEACEFUL:
                    case EASY: {
                        ghostCreeperChance = (int) (255 * GHOST_CREEPER_EXPLODE_CHANCE_EASY);
                    }
                    case NORMAL: {
                        ghostCreeperChance = (int) (255 * GHOST_CREEPER_EXPLODE_CHANCE_NORMAL);
                    }
                    break;
                    case HARD:
                    default: {
                        ghostCreeperChance = (int) (255 * GHOST_CREEPER_EXPLODE_CHANCE_HARD);
                    }
                    break;
                }

                { // Lighting Bolt

                    LightningEntity lightningEntity = EntityType.LIGHTNING_BOLT.create(serverWorld, SpawnReason.EVENT);
                    if (lightningEntity != null) {
                        lightningEntity.refreshPositionAfterTeleport(this.getX(), this.getY(), this.getZ());
                        lightningEntity.setCosmetic(true);
                        serverWorld.spawnEntity(lightningEntity);
                    }

                }

                serverWorld.createExplosion(
                        this, this.getX(), this.getY(), this.getZ(),
                        power * chargedPower, World.ExplosionSourceType.MOB
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
                        ParticleTypes.INFESTED,
                        this.getParticleX(0.5),
                        this.getRandomBodyY() + 0.25,
                        this.getParticleZ(0.5),
                        (this.random.nextDouble() - 0.5) * 0.01,
                        -this.random.nextDouble() * 0.01,
                        (this.random.nextDouble() - 0.5) * 0.01
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
