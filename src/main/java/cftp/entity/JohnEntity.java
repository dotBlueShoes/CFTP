package cftp.entity;

import cftp.config.CFTPData;
import cftp.registries.CFTPSoundEvents;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.*;


public class JohnEntity extends HostileEntity {

    public JohnEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 8.0f));
        this.goalSelector.add(8, new LookAroundGoal(this));
        this.initCustomGoals();
    }

    protected void initCustomGoals() {
        this.goalSelector.add(2, new AttackGoal(this));
        this.goalSelector.add(7, new WanderAroundFarGoal(this, 1.0));
        this.targetSelector.add(2, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
    }

    @Override
    protected int getExperienceToDrop(ServerWorld world) {
        if (this.isBaby()) {
            this.experiencePoints = (int)((double)this.experiencePoints * 2.5);
        }
        return super.getExperienceToDrop(world);
    }

    @Override
    public void tickMovement() {
        if (this.isAlive()) {
            boolean bl;
            boolean bl2 = bl = this.burnsInDaylight() && this.isAffectedByDaylight();
            if (bl) {
                ItemStack itemStack = this.getEquippedStack(EquipmentSlot.HEAD);
                if (!itemStack.isEmpty()) {
                    if (itemStack.isDamageable()) {
                        Item item = itemStack.getItem();
                        itemStack.setDamage(itemStack.getDamage() + this.random.nextInt(2));
                        if (itemStack.getDamage() >= itemStack.getMaxDamage()) {
                            this.sendEquipmentBreakStatus(item, EquipmentSlot.HEAD);
                            this.equipStack(EquipmentSlot.HEAD, ItemStack.EMPTY);
                        }
                    }
                    bl = false;
                }
                if (bl) {
                    this.setOnFireFor(8.0f);
                }
            }
        }
        super.tickMovement();
    }

    protected boolean burnsInDaylight() {
        return false;
    }

    @Override
    public boolean damage(ServerWorld world, DamageSource source, float amount) {
        if (!super.damage(world, source, amount)) {
            return false;
        }

        LivingEntity livingEntity = this.getTarget();

        if (livingEntity == null && source.getAttacker() instanceof LivingEntity) {
            livingEntity = (LivingEntity)source.getAttacker();
        }

        if (livingEntity != null
                && world.getDifficulty() == Difficulty.HARD
                && world.getGameRules().getBoolean(GameRules.DO_MOB_SPAWNING)
        ) {

            int floorX = MathHelper.floor(this.getX());
            int floorY = MathHelper.floor(this.getY());
            int floorZ = MathHelper.floor(this.getZ());

            //EntityType<? extends ZombieEntity> entityType = this.getType();
            //ZombieEntity zombieEntity = entityType.create(world, SpawnReason.REINFORCEMENT);
            //if (zombieEntity == null) {
            //    return true;
            //}
            //for (int l = 0; l < 50; ++l) {
            //    int o;
            //    int n;
            //    int m = i + MathHelper.nextInt(this.random, 7, 40) * MathHelper.nextInt(this.random, -1, 1);
            //    BlockPos blockPos = new BlockPos(m, n = j + MathHelper.nextInt(this.random, 7, 40) * MathHelper.nextInt(this.random, -1, 1), o = k + MathHelper.nextInt(this.random, 7, 40) * MathHelper.nextInt(this.random, -1, 1));
            //    if (!SpawnRestriction.isSpawnPosAllowed(entityType, world, blockPos) || !SpawnRestriction.canSpawn(entityType, world, SpawnReason.REINFORCEMENT, blockPos, world.random)) continue;
            //    zombieEntity.setPosition(m, n, o);
            //    if (world.isPlayerInRange(m, n, o, 7.0) || !world.doesNotIntersectEntities(zombieEntity) || !world.isSpaceEmpty(zombieEntity) || !zombieEntity.canSpawnAsReinforcementInFluid() && world.containsFluid(zombieEntity.getBoundingBox())) continue;
            //    zombieEntity.setTarget(livingEntity);
            //    zombieEntity.initialize(world, world.getLocalDifficulty(zombieEntity.getBlockPos()), SpawnReason.REINFORCEMENT, null);
            //    world.spawnEntityAndPassengers(zombieEntity);
            //    break;
            //}
        }
        return true;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        //return SoundEvents.ENTITY_ZOMBIE_AMBIENT;
        return CFTPSoundEvents.ENTITY_JOHN_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return CFTPSoundEvents.ENTITY_JOHN_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return CFTPSoundEvents.ENTITY_JOHN_DEATH;
    }

    // TODO!
    //protected SoundEvent getStepSound() {
    //    return SoundEvents.ENTITY_ZOMBIE_STEP;
    //}
    //
    //@Override
    //protected void playStepSound(BlockPos pos, BlockState state) {
    //    this.playSound(this.getStepSound(), 0.15f, 1.0f);
    //}

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.FOLLOW_RANGE, CFTPData.john.followRange)
                .add(EntityAttributes.MOVEMENT_SPEED, CFTPData.john.movementSpeed)
                .add(EntityAttributes.ATTACK_DAMAGE, CFTPData.john.attackDamage)
                .add(EntityAttributes.ARMOR, CFTPData.john.armor);
    }

}
