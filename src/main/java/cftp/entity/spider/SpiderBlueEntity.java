package cftp.entity.spider;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;

public class SpiderBlueEntity extends SpiderEntity {
    
    public SpiderBlueEntity(EntityType<? extends SpiderEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return HostileEntity.createHostileAttributes()
                .add(EntityAttributes.MAX_HEALTH, 16.0)
                .add(EntityAttributes.MOVEMENT_SPEED, 0.3F);
    }

    @Override
    public boolean tryAttack(ServerWorld world, Entity target) {
        if (super.tryAttack(world, target)) {

            // TODO.

            //if (target instanceof LivingEntity) {
            //
            //    int i = 0;
            //    if (this.getWorld().getDifficulty() == Difficulty.NORMAL) {
            //        i = 7;
            //    } else if (this.getWorld().getDifficulty() == Difficulty.HARD) {
            //        i = 15;
            //    }
            //
            //    if (i > 0) {
            //        ((LivingEntity)target).addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, i * 20, 0), this);
            //    }
            //
            //}

            return true;
        }
        return false;
    }

}
