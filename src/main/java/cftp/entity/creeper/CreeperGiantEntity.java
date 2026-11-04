package cftp.entity.creeper;

import cftp.config.CFTPData;
import cftp.entity.base.CreeperElementalEntity;
import cftp.utility.CreeperHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

public class CreeperGiantEntity extends CreeperElementalEntity {

    protected float power = CFTPData.creeperGiant.power;

    public CreeperGiantEntity(
            EntityType<? extends CreeperElementalEntity> entityType,
            World world
    ) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.MAX_HEALTH, CFTPData.creeperGiant.health)
                .add(EntityAttributes.MOVEMENT_SPEED, CFTPData.creeperGiant.movementSpeed)
                .add(EntityAttributes.ATTACK_DAMAGE, 1)
                .add(EntityAttributes.FOLLOW_RANGE, CFTPData.creeperGiant.followRange);
    }

    @Override
    protected int getElementalCreeperType() {
        return CreeperHelper.CREEPER_TYPE.GIANT.getType();
    }

    @Override
    protected void explode() {
        if (this.getWorld() instanceof ServerWorld serverWorld) {

            // explode animation uses this for scaling of the entity not sure why it does not reset
            //public float getClientFuseTime(float timeDelta) {
            //    return MathHelper.lerp(timeDelta, (float)this.lastFuseTime, (float)this.currentFuseTime) / (float)(this.fuseTime - 2);
            //}
            //creeperEntityRenderState.fuseTime

            { // Reset ALL so it can re-explode properly with time.
                this.ignite(false);
                this.fuseTime = FUSE_TIME_MAX;
                this.currentFuseTime = 0;
                this.lastFuseTime = 0;
                this.setFuseSpeed(-1);
            }

            if (!this.isTouchingWater() && !isDefused()) {
                final Difficulty difficulty = this.getWorld().getDifficulty();
                float chargedPower = this.isCharged() ? 1.5F : 1.0F;
                float diameter = this.power;
                int ghostCreeperChance;

                switch (difficulty) {
                    case PEACEFUL:
                    case EASY: {
                        ghostCreeperChance = (int) (255 * GHOST_CREEPER_EXPLODE_CHANCE_EASY);
                        diameter *= chargedPower;
                    } break;
                    case NORMAL: {
                        ghostCreeperChance = (int) (255 * GHOST_CREEPER_EXPLODE_CHANCE_EASY);
                        diameter *= 1.33f * chargedPower;
                    } break;
                    case HARD:
                    default: {
                        ghostCreeperChance = (int) (255 * GHOST_CREEPER_EXPLODE_CHANCE_HARD);
                        diameter *= 1.66f * chargedPower;
                    }
                }

                //serverWorld.createExplosion(
                //        this, this.getX(), this.getY(), this.getZ(),
                //        diameter, World.ExplosionSourceType.MOB
                //);

                // Non destructive explosion
                serverWorld.createExplosion(
                        this,
                        Explosion.createDamageSource(serverWorld, this),
                        CreeperHelper.noDestroyExplosionBehaviour,
                        this.getX(),
                        this.getY(),
                        this.getZ(),
                        diameter,
                        false,
                        World.ExplosionSourceType.MOB,
                        ParticleTypes.EXPLOSION,
                        ParticleTypes.EXPLOSION_EMITTER,
                        SoundEvents.ENTITY_GENERIC_EXPLODE
                );

                SpawnGhostCreeper(serverWorld, ghostCreeperChance);
            } else {
                this.playDefusedExplosionSound(serverWorld);
            }

            this.spawnEffectsCloud();

            //this.dead = true;
            //this.onRemoval(serverWorld, Entity.RemovalReason.KILLED);
            //this.discard();
        }
    }

    @Override
    public void tickMovement() {

        if (this.random.nextInt(256) > 248) {
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
                        0.9f, 0.4f
                );
            }
        }

        super.tickMovement();
    }

}
