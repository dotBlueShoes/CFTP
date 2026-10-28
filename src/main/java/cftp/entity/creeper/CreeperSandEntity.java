package cftp.entity.creeper;

import cftp.config.CFTPData;
import cftp.entity.base.CreeperElementalEntity;
import cftp.utility.CreeperHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.world.World;

public class CreeperSandEntity extends CreeperElementalEntity {

    protected float power = CFTPData.creeperSand.power;

    public CreeperSandEntity(
            EntityType<? extends CreeperElementalEntity> entityType,
            World world
    ) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.MAX_HEALTH, CFTPData.creeperSand.health)
                .add(EntityAttributes.MOVEMENT_SPEED, CFTPData.creeperSand.movementSpeed)
                .add(EntityAttributes.ATTACK_DAMAGE, 1)
                .add(EntityAttributes.FOLLOW_RANGE, CFTPData.creeperSand.followRange);
    }

    @Override
    protected int getElementalCreeperType() {
        return CreeperHelper.CREEPER_TYPE.SAND.getType();
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

}
