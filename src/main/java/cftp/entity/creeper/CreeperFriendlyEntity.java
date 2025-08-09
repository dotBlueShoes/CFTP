package cftp.entity.creeper;

import cftp.CFTP;
import cftp.entity.base.CreeperElementalEntity;
import cftp.goals.CreeperElementalIgniteGoal;
import cftp.utility.CreeperMath;
import cftp.utility.PseudoRandom;
import cftp.utility.Shapes;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageSources;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.passive.OcelotEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.world.explosion.ExplosionBehavior;
import net.minecraft.world.explosion.ExplosionImpl;

public class CreeperFriendlyEntity extends CreeperElementalEntity {

    protected int ExplosionDiameter = 3;

    public CreeperFriendlyEntity(EntityType<? extends CreeperElementalEntity> entityType, World world) {
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
        return CreeperMath.CREEPER_TYPE.FRIENDLY.getType();
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(2, new CreeperElementalIgniteGoal(this, 1.0F));
        this.goalSelector.add(3, new FleeEntityGoal<>(this, OcelotEntity.class, 6.0F, 1.0, 1.2));
        this.goalSelector.add(3, new FleeEntityGoal<>(this, CatEntity.class, 6.0F, 1.0, 1.2));
        // Run away from other creepers.
        //this.goalSelector.add(3, new FleeEntityGoal<>(this, CreeperElementalEntity.class, 6.0F, 1.0, 1.2));
        //this.goalSelector.add(3, new FleeEntityGoal<>(this, CreeperEntity.class, 6.0F, 1.0, 1.2));
        // // Maybe instead make it attack other creepers?
        // this.goalSelector.add(4, new MeleeAttackGoal(this, 1.0));
        //
        this.goalSelector.add(5, new WanderAroundFarGoal(this, 0.8));
        //
        this.goalSelector.add(6, new LookAtEntityGoal(this, CreeperElementalEntity.class, 8.0F));
        this.goalSelector.add(6, new LookAtEntityGoal(this, CreeperEntity.class, 8.0F));
        //
        this.goalSelector.add(6, new LookAroundGoal(this));
        //
        this.targetSelector.add(1, new ActiveTargetGoal<>(this, CreeperElementalEntity.class, true));
        this.targetSelector.add(1, new ActiveTargetGoal<>(this, CreeperEntity.class, true));
        //
        this.targetSelector.add(2, new RevengeGoal(this));
    }

    @Override
    protected void explode() {
        if (this.getWorld() instanceof ServerWorld serverWorld) {
            this.dead = true;

            final Difficulty difficulty = this.getWorld().getDifficulty();

            final float chargedPower = this.isCharged() ? 2.0F : 1.0F;
            float diameter = this.ExplosionDiameter;

            switch (difficulty) {
                case PEACEFUL:
                case EASY: {
                    diameter *= chargedPower;
                } break;
                case NORMAL: {
                    diameter *= 1.25f * chargedPower;
                } break;
                case HARD:
                default: {
                    diameter *= 1.50f * chargedPower;
                } break;
            }

            var explosionBehaviour = new ExplosionBehavior() {
                @Override
                public boolean canDestroyBlock(Explosion explosion, BlockView world, BlockPos pos, BlockState state, float power) {
                    return false; // No blocks destroyed
                }
            };

            serverWorld.createExplosion(
                    this,
                    Explosion.createDamageSource(serverWorld, this),
                    explosionBehaviour,
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

            // Deal 7 damage to itself.
            this.damage(serverWorld, Explosion.createDamageSource(serverWorld, this), 6);

            this.playExplosionSound(serverWorld);
            this.spawnEffectsCloud();
        }
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        World world = this.getWorld();

        if (itemStack.getItem() == Items.GUNPOWDER && this.getHealth() < this.getMaxHealth()) {
            if (world instanceof ServerWorld serverWorld) {
                itemStack.decrement(1);

                // Heal the entity with 5 hearts
                this.heal(5);

                serverWorld.playSound(null, this.getX(), this.getY(), this.getZ(),
                        SoundEvents.ENTITY_PLAYER_BURP, SoundCategory.HOSTILE
                );

            } else {
                world.addParticle(
                        ParticleTypes.HEART,
                        this.getX(), this.getY() + 1.5f, this.getZ(),
                        0.0, 0.0, 0.0
                );
            }
        }

        return ActionResult.SUCCESS;
    }

}
