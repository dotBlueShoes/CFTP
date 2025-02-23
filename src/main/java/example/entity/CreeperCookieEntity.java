package example.entity;

import example.entity.base.CreeperElementalEntity;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

public class CreeperCookieEntity extends CreeperElementalEntity {

    // TODO
    // 4. Make Cookie drop random.

    public CreeperCookieEntity(
            EntityType<? extends CreeperCookieEntity> entityType,
            World world
    ) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.MAX_HEALTH, 18)
                .add(EntityAttributes.MOVEMENT_SPEED, 0.25f)
                .add(EntityAttributes.ATTACK_DAMAGE, 0)
                .add(EntityAttributes.FOLLOW_RANGE, 20);
    }

    @Override
    protected void explode() {
        if (this.getWorld() instanceof ServerWorld serverWorld) {
            int chargedAmount = this.isCharged() ? 2 : 1;
            this.dead = true;

            { // Generate cookies as the explosion.
                ItemEntity itemEntity = new ItemEntity(
                        serverWorld, this.getX(), this.getY(), this.getZ(),
                        new ItemStack(Items.COOKIE, 5 * chargedAmount)
                );
                itemEntity.setToDefaultPickupDelay();
                serverWorld.spawnEntity(itemEntity);
            }

            this.spawnEffectsCloud();
            this.onRemoval(serverWorld, Entity.RemovalReason.KILLED);
            this.discard();

        }
    }

}
