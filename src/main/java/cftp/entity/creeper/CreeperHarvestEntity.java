package cftp.entity.creeper;

import cftp.config.CFTPData;
import cftp.entity.base.CreeperElementalEntity;
import cftp.utility.CreeperHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.world.World;

public class CreeperHarvestEntity extends CreeperElementalEntity {

    protected float power = CFTPData.creeperHarvest.power;

    public CreeperHarvestEntity(
            EntityType<? extends CreeperElementalEntity> entityType,
            World world
    ) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.MAX_HEALTH, CFTPData.creeperHarvest.health)
                .add(EntityAttributes.MOVEMENT_SPEED, CFTPData.creeperHarvest.movementSpeed)
                .add(EntityAttributes.ATTACK_DAMAGE, 1)
                .add(EntityAttributes.FOLLOW_RANGE, CFTPData.creeperHarvest.followRange);
    }

    @Override
    protected int getElementalCreeperType() {
        return CreeperHelper.CREEPER_TYPE.HARVEST.getType();
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
