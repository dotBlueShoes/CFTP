package cftp.entity.creeper;

import cftp.entity.base.CreeperElementalEntity;
import cftp.goals.CreeperElementalIgniteGoal;
import cftp.utility.CreeperMath;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.passive.OcelotEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;

public class CreeperCookieEntity extends CreeperElementalEntity {

    public CreeperCookieEntity(
            EntityType<? extends CreeperCookieEntity> entityType,
            World world
    ) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.MAX_HEALTH, 20)
                .add(EntityAttributes.MOVEMENT_SPEED, 0.25f)
                .add(EntityAttributes.ATTACK_DAMAGE, 0)
                .add(EntityAttributes.FOLLOW_RANGE, 20);
    }

    @Override
    protected int getElementalCreeperType() {
        return CreeperMath.CREEPER_TYPE.COOKIE.getType();
    }

    /// Called whenever an entity spawns.
    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(2, new CreeperElementalIgniteGoal(this, 1.0F));
        this.goalSelector.add(3, new FleeEntityGoal<>(this, OcelotEntity.class, 6.0F, 1.0, 1.2));
        this.goalSelector.add(3, new FleeEntityGoal<>(this, CatEntity.class, 6.0F, 1.0, 1.2));
        this.goalSelector.add(3, new FleeEntityGoal<>(this, PlayerEntity.class, 6.0F, 1.0, 1.2));
        //this.goalSelector.add(4, new MeleeAttackGoal(this, 1.0)); // Maybe make it attack other creepers?
        this.goalSelector.add(5, new WanderAroundFarGoal(this, 0.8));
        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(6, new LookAroundGoal(this));
        this.targetSelector.add(1, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.add(2, new RevengeGoal(this));
    }


    @Override
    protected void explode() {
        if (this.getWorld() instanceof ServerWorld serverWorld) {
            int chargedAmount = this.isCharged() ? 2 : 1;
            this.dead = true;

            final Difficulty difficulty = this.getWorld().getDifficulty();

            int ghostCreeperChance;

            switch (difficulty) {
                case PEACEFUL:
                case EASY: {
                    ghostCreeperChance = (int)(255 * GHOST_CREEPER_EXPLODE_CHANCE_EASY);
                }
                case NORMAL: {
                    ghostCreeperChance = (int) (255 * GHOST_CREEPER_EXPLODE_CHANCE_NORMAL);
                } break;
                case HARD:
                default: {
                    ghostCreeperChance = (int) (255 * GHOST_CREEPER_EXPLODE_CHANCE_HARD);
                } break;
            }

            { // Generate cookies as the explosion.
                ItemEntity itemEntity = new ItemEntity(
                        serverWorld, this.getX(), this.getY(), this.getZ(),
                        new ItemStack(Items.COOKIE, 5 * chargedAmount)
                );

                itemEntity.setVelocity(
                        itemEntity.getVelocity().multiply(2.0, 2.0, 2.0)
                );

                itemEntity.setToDefaultPickupDelay();
                serverWorld.spawnEntity(itemEntity);
            }

            this.playExplosionSound(serverWorld);
            this.spawnEffectsCloud();
            this.onRemoval(serverWorld, Entity.RemovalReason.KILLED);
            this.discard();

            SpawnGhostCreeper(serverWorld, ghostCreeperChance);
        }
    }

}
