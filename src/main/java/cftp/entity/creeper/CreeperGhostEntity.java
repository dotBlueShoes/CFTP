package cftp.entity.creeper;

import cftp.CFTP;
import cftp.config.CFTPData;
import cftp.entity.base.CreeperElementalEntity;
import cftp.goals.CreeperElementalIgniteGoal;
import cftp.registries.CFTPItems;
import cftp.utility.CreeperMath;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.passive.OcelotEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;

import java.util.Objects;

import static cftp.utility.CreeperMath.CREEPER_EGGS;

public class CreeperGhostEntity extends CreeperElementalEntity {

    protected float power = CFTPData.creeperGhost.power;

    private static final TrackedData<Integer> INVISIBLE_AGE_PROPERTY = DataTracker.registerData(CreeperGhostEntity.class, TrackedDataHandlerRegistry.INTEGER);

    public static final int MAX_INVISIBLE_AGE = 35;

    public int invisibleAge = 0;
    public int parentType = 0;

    public static final int EASY_INV_CHANCE = 2;    // never
    public static final int NORMAL_INV_CHANCE = 1;  // 1/2
    public static final int HARD_INV_CHANCE = 0;    // always

    public CreeperGhostEntity(
            EntityType<? extends CreeperGhostEntity> entityType,
            World world
    ) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.MAX_HEALTH, CFTPData.creeperGhost.health)
                .add(EntityAttributes.MOVEMENT_SPEED, CFTPData.creeperGhost.movementSpeed)
                .add(EntityAttributes.ATTACK_DAMAGE, 1)
                .add(EntityAttributes.FOLLOW_RANGE, CFTPData.creeperGhost.followRange);
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(INVISIBLE_AGE_PROPERTY, 0);
    }

    @Override
    protected float getIgniteDistance() {
        return 5.0F;

        // This makes the ghost creeper not scary at all.
        //  should be a config setting. Make Ghost Creepers Easy.
        //return 3.0F;
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

    @Override
    protected ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);

        if (stack.isIn(ItemTags.CREEPER_IGNITERS) && !isDefused()) {
            SoundEvent sound = stack.isOf(Items.FIRE_CHARGE) ? SoundEvents.ITEM_FIRECHARGE_USE : SoundEvents.ITEM_FLINTANDSTEEL_USE;

            this.getWorld().playSound(player, this.getX(), this.getY(), this.getZ(),
                    sound, this.getSoundCategory(),
                    1.0F, this.random.nextFloat() * 0.4F + 0.8F
            );

            if (this.getWorld() instanceof ServerWorld) {
                this.ignite(true);

                if (!stack.isDamageable()) {
                    stack.decrement(1);
                } else {
                    stack.damage(1, player, getSlotForHand(hand));
                }
            }

            return ActionResult.SUCCESS;
        }

        //return ActionResult.FAIL;
        return ActionResult.PASS;
    }

    @Override
    protected void explode() {
        if (this.getWorld() instanceof ServerWorld serverWorld) {
            this.dead = true;

            if (!this.isTouchingWater() && !isDefused()) {
                final Difficulty difficulty = this.getWorld().getDifficulty();
                float chargedPower = this.isCharged() ? 2.0F : 1.0F;
                float diameter = this.power;
                int ghostCreeperChance;

                switch (difficulty) {
                    case PEACEFUL:
                    case EASY: {
                        ghostCreeperChance = (int) (255 * GHOST_CREEPER_EXPLODE_CHANCE_EASY);
                        diameter *= chargedPower;
                    }
                    break;
                    case NORMAL: {
                        ghostCreeperChance = (int) (255 * GHOST_CREEPER_EXPLODE_CHANCE_EASY);
                        diameter *= 1.5f * chargedPower;
                    }
                    break;
                    case HARD:
                    default: {
                        ghostCreeperChance = (int) (255 * GHOST_CREEPER_EXPLODE_CHANCE_HARD);
                        diameter *= 2.0f * chargedPower;
                    }
                    break;
                }

                serverWorld.createExplosion(
                        this, this.getX(), this.getY(), this.getZ(),
                        diameter, World.ExplosionSourceType.MOB
                );

                SpawnGhostCreeper(serverWorld, ghostCreeperChance);
            } else {
                this.playDefusedExplosionSound(serverWorld);
            }

            this.spawnEffectsCloud();
            this.onRemoval(serverWorld, Entity.RemovalReason.KILLED);
            this.discard();
        }
    }

    public void createInvisibleParticles(ServerWorld serverWorld) {
        serverWorld.spawnParticles(
                ParticleTypes.POOF,
                this.getX(),
                this.getY() + 1,
                this.getZ(),
                15,
                1, 1, 1,
                0.02
        );
    }

    public int getInvisibleAge() {
        return this.dataTracker.get(INVISIBLE_AGE_PROPERTY);
    }

    public void setInvisibleAge(int value) {
        this.dataTracker.set(INVISIBLE_AGE_PROPERTY, value);
    }

    public int getInvChance () {
        final Difficulty difficulty = this.getWorld().getDifficulty();
        int chance;

        switch (difficulty) {
            case PEACEFUL:
            case EASY: {
                chance = EASY_INV_CHANCE;
            } break;
            case NORMAL: {
                chance = NORMAL_INV_CHANCE;
            } break;
            case HARD:
            default: {
                chance = HARD_INV_CHANCE;
            } break;
        }

        return chance;
    }

    private void createPoofSound(ServerWorld serverWorld) {
        serverWorld.playSound(
                null, this.getX(), this.getY(), this.getZ(),
                SoundEvents.ENTITY_BREEZE_WIND_BURST,
                SoundCategory.HOSTILE,
                0.2f, 0.4f
        );
    }

    @Override
    public void tickMovement() {

        if (this.getWorld() instanceof ServerWorld serverWorld) {
            if (this.random.nextInt(256) > 254) {
                if (this.random.nextInt(2) >= getInvChance()) {
                    createInvisibleParticles(serverWorld);
                    this.setInvisibleAge(MAX_INVISIBLE_AGE);
                    invisibleAge = MAX_INVISIBLE_AGE;
                    createPoofSound(serverWorld);
                }
            } else {
                --invisibleAge;

                // We want to only once sync with dataTracker
                //  not every tick. I believe that would be heavy for server.

                if (invisibleAge == 0) {
                    createInvisibleParticles(serverWorld);
                    this.setInvisibleAge(0);
                    createPoofSound(serverWorld);
                } else if (invisibleAge < 0) {
                    invisibleAge = -1;
                }
            }
        }

        super.tickMovement();
    }

}
