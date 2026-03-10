package cftp.entity.creeper;

import cftp.config.CFTPData;
import cftp.entity.base.CreeperElementalEntity;
import cftp.utility.CreeperMath;
import cftp.utility.Shapes;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.world.explosion.ExplosionImpl;

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
        return CreeperMath.CREEPER_TYPE.GIANT.getType();
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
