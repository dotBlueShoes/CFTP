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

import java.util.Objects;

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

        final Difficulty difficulty = this.getWorld().getDifficulty();

        // Ghost Creepers won't spawn ghost creepers when not in HARD difficulty.
        // TODO. Getting 7 creepers in a row seems a bit bad design...
        if (Objects.requireNonNull(difficulty) == Difficulty.HARD) {
            super.onDeath(damageSource);
        } else {
            super.onSuperDeath(damageSource);
        }

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

}
