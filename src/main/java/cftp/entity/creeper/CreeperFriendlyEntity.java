package cftp.entity.creeper;

import cftp.CFTP;
import cftp.entity.base.CreeperElementalEntity;
import cftp.goals.CreeperElementalIgniteGoal;
import cftp.utility.CreeperMath;
import cftp.utility.PseudoRandom;
import cftp.utility.Shapes;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.Tameable;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageSources;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.ServerConfigHandler;
import net.minecraft.server.network.ServerPlayerEntity;
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
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.UUID;

public class CreeperFriendlyEntity extends CreeperElementalEntity implements Tameable {

    // NOT NEEDED - just deal dmg to itself.
    //private static final TrackedData<Integer> EXPLODES = DataTracker.registerData(CreeperFriendlyEntity.class, TrackedDataHandlerRegistry.INTEGER);
    protected static final TrackedData<Optional<UUID>> OWNER_UUID = DataTracker.registerData(CreeperFriendlyEntity.class, TrackedDataHandlerRegistry.OPTIONAL_UUID);
    private static final TrackedData<Byte> FRIENDLY_FLAGS = DataTracker.registerData(CreeperFriendlyEntity.class, TrackedDataHandlerRegistry.BYTE);
    private static final int TAMED_FLAG     = 2;
    private static final int RESERVED_0     = 4;
    private static final int BRED_FLAG      = 8;
    private static final int RESERVED_1     = 16;
    private static final int ANGRY_FLAG     = 32;
    private static final int EATING_FLAG    = 64;

    protected int ExplosionDiameter = 3;

    @Nullable
    private UUID ownerUuid;

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
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(FRIENDLY_FLAGS, (byte)0);
        builder.add(OWNER_UUID, Optional.empty());
    }

    protected boolean getFriendlyFlag(int bitmask) {
        return (this.dataTracker.get(FRIENDLY_FLAGS) & bitmask) != 0;
    }

    protected void setFriendlyFlag(int bitmask, boolean flag) {
        byte b = this.dataTracker.get(FRIENDLY_FLAGS);
        if (flag) {
            this.dataTracker.set(FRIENDLY_FLAGS, (byte)(b | bitmask));
        } else {
            this.dataTracker.set(FRIENDLY_FLAGS, (byte)(b & ~bitmask));
        }
    }

    public boolean isTame() {
        return this.getFriendlyFlag(TAMED_FLAG);
    }

    public void setTamed(boolean tame) {
        this.setFriendlyFlag(TAMED_FLAG, tame);
    }

    @Nullable
    @Override
    public UUID getOwnerUuid() {
        return (UUID)this.dataTracker.get(OWNER_UUID).orElse(null);
    }

    public void setOwnerUuid(@Nullable UUID uuid) {
        this.dataTracker.set(OWNER_UUID, Optional.ofNullable(uuid));
    }


    //@Override
    //protected void initDataTracker(DataTracker.Builder builder) {
    //    super.initDataTracker(builder);
    //    builder.add(EXPLODES, 3);
    //}
    //
    //public int getExplodes() {
    //    return this.dataTracker.get(EXPLODES);
    //}
    //
    //public void setExplodes(int value) {
    //    this.dataTracker.set(EXPLODES, value);
    //}
    //
    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        //nbt.putShort("explodes", (short)getExplodes());
        if (this.getOwnerUuid() != null) nbt.putUuid("Owner", this.getOwnerUuid());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        //setExplodes(nbt.getShort("explodes"));

        UUID uUID;
        if (nbt.containsUuid("Owner")) {
            uUID = nbt.getUuid("Owner");
        } else {
            String string = nbt.getString("Owner");
            uUID = ServerConfigHandler.getPlayerUuidByName(this.getServer(), string);
        }

        if (uUID != null) {
            try {
                this.setOwnerUuid(uUID);
                this.setTamed(true);
            } catch (Throwable var4) {
                this.setTamed(false);
            }
        } else {
            this.setOwnerUuid(null);
            this.setTamed(false);
        }
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

    // discard?? , SpawnGhostCreeper(serverWorld, ghostCreeperChance); ???

    @Override
    protected void explode() {
        if (this.getWorld() instanceof ServerWorld serverWorld) {
            this.dead = true;

            //if (getExplodes() > 0) {
            if (!isDefused()) {
                final Difficulty difficulty = this.getWorld().getDifficulty();

                final float chargedPower = this.isCharged() ? 2.0F : 1.0F;
                float diameter = this.ExplosionDiameter;

                switch (difficulty) {
                    case PEACEFUL:
                    case EASY: {
                        diameter *= chargedPower;
                    }
                    break;
                    case NORMAL: {
                        diameter *= 1.25f * chargedPower;
                    }
                    break;
                    case HARD:
                    default: {
                        diameter *= 1.50f * chargedPower;
                    }
                    break;
                }

                serverWorld.createExplosion(
                        this,
                        Explosion.createDamageSource(serverWorld, this),
                        CreeperMath.noDestroyExplosionBehaviour,
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

                // Deal 8 damage to itself.
                this.damage(serverWorld, Explosion.createDamageSource(serverWorld, this), 8);
                this.playExplosionSound(serverWorld);
                this.spawnEffectsCloud();
            } else {
                this.playDefusedExplosionSound(serverWorld);
                this.spawnEffectsCloud();
                this.onRemoval(serverWorld, RemovalReason.KILLED);
                this.discard();
            }


        }
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        World world = this.getWorld();

        if (isTame()) {
            CFTP.LOGGER.info("tamed!");
            if (itemStack.getItem() == Items.GUNPOWDER && this.getHealth() < this.getMaxHealth()) {
                if (world instanceof ServerWorld serverWorld) {
                    itemStack.decrementUnlessCreative(1, player);

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
        } else {
            if (!this.getWorld().isClient && itemStack.getItem() == Items.GUNPOWDER) {
                itemStack.decrementUnlessCreative(1, player);
                this.tryTame(player);
                return ActionResult.SUCCESS_SERVER;
            }
        }

        return ActionResult.SUCCESS;
    }

    private void tryTame(PlayerEntity player) {
        if (this.random.nextInt(4) == 0) {
            this.setOwnerUuid(player.getUuid());
            this.setTamed(true);

            //if (player instanceof ServerPlayerEntity serverPlayerEntity) {
            //    Criteria.TAME_ANIMAL.trigger(serverPlayerEntity, this);
            //}

            this.navigation.stop();
            this.setTarget(null);

            this.getWorld().sendEntityStatus(this, EntityStatuses.ADD_POSITIVE_PLAYER_REACTION_PARTICLES);
        } else {
            this.getWorld().sendEntityStatus(this, EntityStatuses.ADD_NEGATIVE_PLAYER_REACTION_PARTICLES);
        }
    }

    //public boolean bondWithPlayer(PlayerEntity player) {
    //    this.setOwnerUuid(player.getUuid());
    //    this.setTame(true);
    //    //if (player instanceof ServerPlayerEntity) {
    //    //    Criteria.TAME_ANIMAL.trigger((ServerPlayerEntity)player, this);
    //    //}
    //
    //    this.getWorld().sendEntityStatus(this, EntityStatuses.ADD_POSITIVE_PLAYER_REACTION_PARTICLES);
    //    return true;
    //}

}
