package cftp.entity.base;

import cftp.CFTP;
import cftp.entity.CFTPEntities;
import cftp.goals.CreeperElementalIgniteGoal;
import cftp.utility.CreeperMath;
import cftp.utility.PseudoRandom;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.CaveSpiderEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.passive.GoatEntity;
import net.minecraft.entity.passive.OcelotEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public class CreeperElementalEntity extends HostileEntity {

    //SpiderEntity;
    //CaveSpiderEntity;

    protected static final TrackedData<Integer> FUSE_SPEED = DataTracker.registerData(CreeperElementalEntity.class, TrackedDataHandlerRegistry.INTEGER);
    protected static final TrackedData<Boolean> CHARGED = DataTracker.registerData(CreeperElementalEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    protected static final TrackedData<Boolean> IGNITED = DataTracker.registerData(CreeperElementalEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    protected int lastFuseTime;
    protected int currentFuseTime;
    protected int headsDropped;

    protected int fuseTime = 10;
    protected int explosionDiameter = 3;


    public static final float DROP_EXPLOSION_ITEM_CHANCE_EASY   = 1.00f;
    public static final float DROP_EXPLOSION_ITEM_CHANCE_NORMAL = 0.75f;
    public static final float DROP_EXPLOSION_ITEM_CHANCE_HARD   = 0.50f;

    public static final float GHOST_CREEPER_KILL_CHANCE_EASY   = 0.00f;
    public static final float GHOST_CREEPER_KILL_CHANCE_NORMAL = 0.25f;
    public static final float GHOST_CREEPER_KILL_CHANCE_HARD   = 0.50f;

    public static final float GHOST_CREEPER_EXPLODE_CHANCE_EASY   = 0.000f;
    public static final float GHOST_CREEPER_EXPLODE_CHANCE_NORMAL = 0.125f;
    public static final float GHOST_CREEPER_EXPLODE_CHANCE_HARD   = 0.250f;

    public CreeperElementalEntity(EntityType<? extends CreeperElementalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(2, new CreeperElementalIgniteGoal(this, 7.0F));
        this.goalSelector.add(3, new FleeEntityGoal<>(this, OcelotEntity.class, 6.0F, 1.0, 1.2));
        this.goalSelector.add(3, new FleeEntityGoal<>(this, CatEntity.class, 6.0F, 1.0, 1.2));
        this.goalSelector.add(4, new MeleeAttackGoal(this, 1.0, false));
        this.goalSelector.add(5, new WanderAroundFarGoal(this, 0.8));
        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(6, new LookAroundGoal(this));
        this.targetSelector.add(1, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.add(2, new RevengeGoal(this));
    }

    @Override
    public int getSafeFallDistance() {
        return this.getTarget() == null ? this.getSafeFallDistance(0.0F) : this.getSafeFallDistance(this.getHealth() - 1.0F);
    }

    @Override
    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        boolean bl = super.handleFallDamage(fallDistance, damageMultiplier, damageSource);
        this.currentFuseTime += (int)(fallDistance * 1.5F);
        if (this.currentFuseTime > this.fuseTime - 5) {
            this.currentFuseTime = this.fuseTime - 5;
        }

        return bl;
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(FUSE_SPEED, -1);
        builder.add(CHARGED, false);
        builder.add(IGNITED, false);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        if (this.dataTracker.get(CHARGED)) {
            nbt.putBoolean("powered", true);
        }

        nbt.putShort("Fuse", (short)this.fuseTime);
        nbt.putByte("ExplosionDiameter", (byte)this.explosionDiameter);
        nbt.putBoolean("ignited", this.isIgnited());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.dataTracker.set(CHARGED, nbt.getBoolean("powered"));
        if (nbt.contains("Fuse", NbtElement.NUMBER_TYPE)) {
            this.fuseTime = nbt.getShort("Fuse");
        }

        if (nbt.contains("ExplosionDiameter", NbtElement.NUMBER_TYPE)) {
            this.explosionDiameter = nbt.getByte("ExplosionDiameter");
        }

        if (nbt.getBoolean("ignited")) {
            this.ignite();
        }
    }

    @Override
    public void tick() {
        if (this.isAlive()) {
            this.lastFuseTime = this.currentFuseTime;
            if (this.isIgnited()) {
                this.setFuseSpeed(1);
            }

            int i = this.getFuseSpeed();
            if (i > 0 && this.currentFuseTime == 0) {
                this.playSound(SoundEvents.ENTITY_CREEPER_PRIMED, 1.0F, 0.5F);
                this.emitGameEvent(GameEvent.PRIME_FUSE);
            }

            this.currentFuseTime += i;
            if (this.currentFuseTime < 0) {
                this.currentFuseTime = 0;
            }

            if (this.currentFuseTime >= this.fuseTime) {
                this.currentFuseTime = this.fuseTime;
                this.explode();
            }
        }

        super.tick();
    }

    @Override
    public void setTarget(@Nullable LivingEntity target) {
        if (!(target instanceof GoatEntity)) {
            super.setTarget(target);
        }
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_CREEPER_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_CREEPER_DEATH;
    }

    @Override
    protected void dropEquipment(ServerWorld world, DamageSource source, boolean causedByPlayer) {
        super.dropEquipment(world, source, causedByPlayer);
        Entity entity = source.getAttacker();
        if (entity != this && entity instanceof CreeperElementalEntity creeperEntity && creeperEntity.shouldDropHead()) {
            creeperEntity.onHeadDropped();
            this.dropItem(world, Items.CREEPER_HEAD);
        }
    }

    @Override
    public boolean tryAttack(ServerWorld world, Entity target) {
        return true;
    }

    public boolean isCharged() {
        return this.dataTracker.get(CHARGED);
    }

    public float getClientFuseTime(float timeDelta) {
        return MathHelper.lerp(timeDelta, (float)this.lastFuseTime, (float)this.currentFuseTime) / (float)(this.fuseTime - 2);
    }

    public int getFuseSpeed() {
        return this.dataTracker.get(FUSE_SPEED);
    }

    public void setFuseSpeed(int fuseSpeed) {
        this.dataTracker.set(FUSE_SPEED, fuseSpeed);
    }

    @Override
    public void onStruckByLightning(ServerWorld world, LightningEntity lightning) {
        super.onStruckByLightning(world, lightning);
        this.dataTracker.set(CHARGED, true);
    }

    @Override
    protected ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (itemStack.isIn(ItemTags.CREEPER_IGNITERS)) {
            SoundEvent soundEvent = itemStack.isOf(Items.FIRE_CHARGE) ? SoundEvents.ITEM_FIRECHARGE_USE : SoundEvents.ITEM_FLINTANDSTEEL_USE;
            this.getWorld().playSound(player, this.getX(), this.getY(), this.getZ(), soundEvent, this.getSoundCategory(), 1.0F, this.random.nextFloat() * 0.4F + 0.8F);
            if (!this.getWorld().isClient) {
                this.ignite();
                if (!itemStack.isDamageable()) {
                    itemStack.decrement(1);
                } else {
                    itemStack.damage(1, player, getSlotForHand(hand));
                }
            }

            return ActionResult.SUCCESS;
        } else {
            return super.interactMob(player, hand);
        }
    }

    protected void explode() {
        if (this.getWorld() instanceof ServerWorld serverWorld) {
            float chargedPower = this.isCharged() ? 2.0F : 1.0F;
            this.dead = true;

            serverWorld.createExplosion(
                    this, this.getX(), this.getY(), this.getZ(),
                    (float)this.explosionDiameter * chargedPower,
                    World.ExplosionSourceType.MOB
            );

            this.spawnEffectsCloud();
            this.onRemoval(serverWorld, Entity.RemovalReason.KILLED);
            this.discard();
        }
    }

    protected void spawnEffectsCloud() {
        Collection<StatusEffectInstance> collection = this.getStatusEffects();
        if (!collection.isEmpty()) {

            AreaEffectCloudEntity areaEffectCloudEntity = getAreaEffectCloudEntity();

            for (StatusEffectInstance statusEffectInstance : collection) {
                areaEffectCloudEntity.addEffect(new StatusEffectInstance(statusEffectInstance));
            }

            this.getWorld().spawnEntity(areaEffectCloudEntity);
        }
    }

    protected AreaEffectCloudEntity getAreaEffectCloudEntity() {
        AreaEffectCloudEntity areaEffectCloudEntity = new AreaEffectCloudEntity(
                this.getWorld(), this.getX(), this.getY(), this.getZ()
        );

        areaEffectCloudEntity.setRadius(2.5F);
        areaEffectCloudEntity.setRadiusOnUse(-0.5F);
        areaEffectCloudEntity.setWaitTime(10);

        areaEffectCloudEntity.setDuration(
                areaEffectCloudEntity.getDuration() / 2
        );

        areaEffectCloudEntity.setRadiusGrowth(
                -areaEffectCloudEntity.getRadius() / (float)areaEffectCloudEntity.getDuration()
        );

        return areaEffectCloudEntity;
    }

    protected void playExplosionSound(World world) {
        world.playSound(
            null, this.getX(), this.getY(), this.getZ(),
            SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.HOSTILE, 1.0F,
            world.getRandom().nextFloat() * 0.4F + 0.8F
        );
    }

    public boolean isIgnited() {
        return this.dataTracker.get(IGNITED);
    }

    public void ignite() {
        this.dataTracker.set(IGNITED, true);
    }

    public boolean shouldDropHead() {
        return this.isCharged() && this.headsDropped < 1;
    }

    public void onHeadDropped() {
        this.headsDropped++;
    }

    @Override
    public int getLimitPerChunk() {
        return 15;
    }

    protected int getElementalCreeperType() {
        return CreeperMath.CREEPER_TYPE.VANILLA.getType();
    }

    protected void SpawnGhostCreeper (ServerWorld world, int ghostCreeperChance) {
        var seed = Math.abs((int) (world.getTimeOfDay()) + (int) (this.getX()) + (int) (this.getY()) + (int) (this.getZ()) + ++PseudoRandom.helperCounter) % 256;

        if (PseudoRandom.UNIFORM_PERMUTATION[seed] <= ghostCreeperChance) {
            var entity = CFTPEntities.CREEPER_GHOST.create(world, SpawnReason.NATURAL);
            EntityData entityData = null;

            if (entity == null) {
                CFTP.LOGGER.warn("Error when creating a Ghost Creeper.");
                return;
            }

            entity.parentType = getElementalCreeperType();
            entity.refreshPositionAndAngles(getX(), getY(), getZ(), world.random.nextFloat() * 360.0F, 0.0F);
            entityData = entity.initialize(world, world.getLocalDifficulty(entity.getBlockPos()), SpawnReason.NATURAL, entityData);
            world.spawnEntityAndPassengers(entity);
        }
    }

    protected void onSuperDeath(DamageSource damageSource) {
        super.onDeath(damageSource);
    }

    @Override
    public void onDeath(DamageSource damageSource) {
        super.onDeath(damageSource);

        World world = getWorld();
        if (world instanceof ServerWorld serverWorld) {

            final Difficulty difficulty = this.getWorld().getDifficulty();

            int ghostCreeperChance = (int)(255 * GHOST_CREEPER_KILL_CHANCE_EASY);
            switch (difficulty) {
                case NORMAL: {
                    ghostCreeperChance = (int)(255 * GHOST_CREEPER_KILL_CHANCE_NORMAL);
                } break;
                case HARD: {
                    ghostCreeperChance = (int)(255 * GHOST_CREEPER_KILL_CHANCE_HARD);
                } break;
            }

            SpawnGhostCreeper(serverWorld, ghostCreeperChance);
        }

    }
}
