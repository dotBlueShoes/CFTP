package cftp.entity;

import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.entity.passive.StriderEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public class HorseFieryEntity extends AbstractHorseEntity {

    public HorseFieryEntity(EntityType<? extends AbstractHorseEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.MAX_HEALTH,       20)
                .add(EntityAttributes.MOVEMENT_SPEED,   0.25f)
                .add(EntityAttributes.ATTACK_DAMAGE,    0)
                .add(EntityAttributes.FOLLOW_RANGE,     20)
                .add(EntityAttributes.TEMPT_RANGE,      16.0f)
        ;
    }

    //@Override
    //public boolean canSpawn(ServerWorldAccess world, SpawnReason spawnReason) {
    //    return true;
    //}

    @Override
    public boolean canSpawn(WorldAccess world, SpawnReason spawnReason) {
        return true;
    }

    @Override
    public boolean canSpawn(WorldView world) {
        return true;
    }

    public static boolean canSpawn(EntityType<HorseFieryEntity> entityType, ServerWorldAccess world, SpawnReason spawnReason, BlockPos blockPos, Random random) {
        BlockPos.Mutable mutable = blockPos.mutableCopy();
        return world.getBlockState(mutable.down()).isSolidBlock(world, mutable.down());
    }

    //@Override
    //protected void initGoals() {
    //    this.goalSelector.add(1, new EscapeDangerGoal(this, 1.2));
    //    this.goalSelector.add(1, new HorseBondWithPlayerGoal(this, 1.2));
    //    this.goalSelector.add(2, new AnimalMateGoal(this, 1.0, AbstractHorseEntity.class));
    //    this.goalSelector.add(4, new FollowParentGoal(this, 1.0));
    //    this.goalSelector.add(6, new WanderAroundFarGoal(this, 0.7));
    //    this.goalSelector.add(7, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
    //    this.goalSelector.add(8, new LookAroundGoal(this));
    //
    //    if (this.shouldAmbientStand()) {
    //        this.goalSelector.add(9, new AmbientStandGoal(this));
    //    }
    //
    //    this.initCustomGoals();
    //}

    @Override
    public void tickMovement() {
        super.tickMovement();

        World world = this.getWorld();

        // Place fire underneath the horse sometimes.
        //
        if (!world.isClient) {
            BlockPos pos = this.getBlockPos();
            if (world.isAir(pos) && world.getBlockState(pos.down()).isSolidBlock(world, pos.down())) {
                if (this.random.nextFloat() < 0.3f) {
                    world.setBlockState(pos, Blocks.FIRE.getDefaultState());
                }
            }
        }
    }

    public void tick() {
        super.tick();

        World world = this.getWorld();

        // Make the rider immune to fire damage when saddled.
        //
        if (!world.isClient) {
            if (this.isSaddled() && this.hasPassengers()) {
                this.getPassengerList().forEach(passenger -> {
                    if (passenger instanceof LivingEntity living) {
                        living.addStatusEffect(new StatusEffectInstance(
                                StatusEffects.FIRE_RESISTANCE,
                                5,  // duration in ticks
                                0,          // amplifier (level 0 = Fire Resistance I)
                                true,       // ambient
                                false,      // show particles
                                false       // show icon
                        ));
                    }
                });
            }
        }

    }

    @Override
    public boolean hurtByWater() {
        return true;
    }

    @Override
    public boolean isOnFire() {
        return true;
    }
}
