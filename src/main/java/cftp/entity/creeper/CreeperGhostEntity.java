package cftp.entity.creeper;

import cftp.CFTP;
import cftp.entity.base.CreeperElementalEntity;
import cftp.goals.CreeperElementalIgniteGoal;
import cftp.utility.CreeperMath;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.passive.OcelotEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;

import static cftp.utility.CreeperMath.CREEPER_EGGS;

public class CreeperGhostEntity extends CreeperElementalEntity {

    public int parentType = 0;

    public CreeperGhostEntity(
            EntityType<? extends CreeperGhostEntity> entityType,
            World world
    ) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.MAX_HEALTH, 14)
                .add(EntityAttributes.MOVEMENT_SPEED, 0.30f)
                .add(EntityAttributes.ATTACK_DAMAGE, 1)
                .add(EntityAttributes.FOLLOW_RANGE, 20);
    }

    @Override
    protected int getElementalCreeperType() {
        return CreeperMath.CREEPER_TYPE.GHOST.getType();
    }

    @Override
    public void onDeath(DamageSource damageSource) {
        super.onDeath(damageSource);

        if (this.getWorld() instanceof ServerWorld serverWorld) {
            ItemStack getItem = new ItemStack (CREEPER_EGGS[parentType], 1);

            ItemEntity itemEntity = new ItemEntity(
                    serverWorld,
                    this.getX() + 0.5f,
                    this.getY() + 1,
                    this.getZ() + 0.5f,
                    getItem
            );

            itemEntity.setToDefaultPickupDelay();
            serverWorld.spawnEntity(itemEntity);
        }
    }

    //@Override
    //protected void explode() {
    //    if (this.getWorld() instanceof ServerWorld serverWorld) {
    //        float chargedPower = this.isCharged() ? 2.0F : 1.0F;
    //        this.dead = true;
    //
    //        serverWorld.createExplosion(
    //                this, this.getX(), this.getY(), this.getZ(),
    //                (float)this.explosionDiameter * chargedPower,
    //                World.ExplosionSourceType.MOB
    //        );
    //
    //        this.spawnEffectsCloud();
    //        this.onRemoval(serverWorld, Entity.RemovalReason.KILLED);
    //        this.discard();
    //    }
    //}

}
