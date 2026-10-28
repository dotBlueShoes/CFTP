package cftp.entity.creeper;

import cftp.config.CFTPData;
import cftp.entity.base.CreeperElementalEntity;
import cftp.registries.CFTPParticles;
import cftp.utility.CreeperHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;

public class CreeperSpectreEntity extends CreeperElementalEntity {

    protected float power = CFTPData.creeperSpectre.power;

    public CreeperSpectreEntity(
            EntityType<? extends CreeperElementalEntity> entityType,
            World world
    ) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.MAX_HEALTH, CFTPData.creeperSpectre.health)
                .add(EntityAttributes.MOVEMENT_SPEED, CFTPData.creeperSpectre.movementSpeed)
                .add(EntityAttributes.ATTACK_DAMAGE, 1)
                .add(EntityAttributes.FOLLOW_RANGE, CFTPData.creeperSpectre.followRange);
    }

    @Override
    protected int getElementalCreeperType() {
        return CreeperHelper.CREEPER_TYPE.SPECTRE.getType();
    }

    //@Override // TODO
    //protected void explode() {
    //    if (this.getWorld() instanceof ServerWorld serverWorld) {
    //        this.dead = true;
    //        this.playExplosionSound(serverWorld);
    //        this.spawnEffectsCloud();
    //        this.onRemoval(serverWorld, RemovalReason.KILLED);
    //        this.discard();
    //    }
    //}

    //@Override
    //public void tickMovement() {
//
    //    if (this.random.nextInt(256) > 252) {
    //        if (this.getWorld() instanceof ServerWorld serverWorld) {
    //            serverWorld.spawnParticles(
    //                    ParticleTypes.SPIT,
    //                    this.getParticleX(0.5),
    //                    this.getRandomBodyY() + 0.25,
    //                    this.getParticleZ(0.5),
    //                    1,
    //                    0.5, 0.5, 0.5,
    //                    0.02
    //            );
//
    //            serverWorld.playSound(
    //                    null, this.getX(), this.getY(), this.getZ(),
    //                    SoundEvents.ENTITY_VILLAGER_NO,
    //                    SoundCategory.HOSTILE,
    //                    0.8f, 0.4f
    //            );
    //        }
    //    }
//
    //    super.tickMovement();
    //}


    @Override
    public void tickMovement() {

        if (this.getWorld() instanceof ServerWorld serverWorld) {
            if (this.random.nextInt(256) > 252) {
                serverWorld.playSound(
                        null, this.getX(), this.getY(), this.getZ(),
                        SoundEvents.BLOCK_FIRE_AMBIENT,
                        SoundCategory.HOSTILE,
                        0.9f, 1.3f
                );
            }
        } else {
            if (this.random.nextInt(256) > 128) {
                this.getWorld().addParticle(
                        CFTPParticles.DRIPPING_BLOOD_PARTICLE,
                        this.getParticleX(0.7),
                        this.getRandomBodyY(),
                        this.getParticleZ(0.7),
                        (random.nextDouble() - 0.5) * 2.0,
                        -random.nextDouble(),
                        (random.nextDouble() - 0.5) * 2.0
                );
            }
        }

        super.tickMovement();
    }

}
