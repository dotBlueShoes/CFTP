package cftp.goals;

import cftp.entity.base.CreeperElementalEntity;
import cftp.entity.creeper.CreeperBallisticEntity;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import java.util.EnumSet;

public class CreeperFireAttackGoal extends Goal {

    protected final PathAwareEntity mob;
    private final double speed;

    private Path path;
    private double targetX;
    private double targetY;
    private double targetZ;
    private int updateCountdownTicks;
    private int cooldown;
    private long lastUpdateTime;

    private BlockPos lastBlockPos = new BlockPos(0, 0, 0);

    public CreeperFireAttackGoal(PathAwareEntity mob, double speed) {
        this.mob = mob;
        this.speed = speed;
        this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
    }

    @Override
    public boolean canStart() {
        long time = this.mob.getWorld().getTime();

        if (time - this.lastUpdateTime < 20L) return false;
        this.lastUpdateTime = time;

        LivingEntity livingEntity = this.mob.getTarget();

        if (livingEntity == null) return false;
        if (!livingEntity.isAlive()) return false;

        this.path = this.mob.getNavigation().findPathTo(livingEntity, 0);
        return this.path != null || this.mob.isInAttackRange(livingEntity);
    }

    private boolean isSamePosition(BlockPos blockPos) {
        return lastBlockPos.getX() == blockPos.getX() &&
            lastBlockPos.getY() == blockPos.getY() &&
            lastBlockPos.getZ() == blockPos.getZ();
    }

    @Override
    public boolean shouldContinue() {
        CreeperElementalEntity creeper = (CreeperElementalEntity)this.mob;
        LivingEntity livingEntity = this.mob.getTarget();

        if (livingEntity == null) return false;
        if (!livingEntity.isAlive()) return false;

        // TODO
        // 1. Make it so that the fire is only being placed on hard mode setting.

        if (!creeper.isDefused()){ // Making the ground on fire.
            BlockPos blockPos = BlockPos.ofFloored(
                    this.mob.getX(),
                    this.mob.getY(),
                    this.mob.getZ()
            );

            if (!isSamePosition(blockPos)) {
                var world = this.mob.getWorld();
                lastBlockPos = blockPos;

                if (AbstractFireBlock.canPlaceAt(world, blockPos, Direction.UP)) {
                    BlockState blockState = AbstractFireBlock.getState(world, blockPos);
                    world.setBlockState(blockPos, blockState, Block.NOTIFY_ALL_AND_REDRAW);
                }
            }
        }

        return !this.mob.getNavigation().isIdle();
    }

    @Override
    public void start() {
        this.mob.getNavigation().startMovingAlong(this.path, this.speed);
        this.mob.setAttacking(true);
        this.updateCountdownTicks = 0;
        this.cooldown = 0;
    }

    @Override
    public void stop() {
        LivingEntity livingEntity = this.mob.getTarget();
        if (!EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR.test(livingEntity)) {
            this.mob.setTarget(null);
        }

        this.mob.setAttacking(false);
        this.mob.getNavigation().stop();
    }

    @Override
    public boolean shouldRunEveryTick() {
        return true;
    }

    @Override
    public void tick() {
        LivingEntity livingEntity = this.mob.getTarget();

        if (livingEntity != null) {
            this.mob.getLookControl().lookAt(livingEntity, 30.0F, 30.0F);
            this.updateCountdownTicks = Math.max(this.updateCountdownTicks - 1, 0);
            if ((this.mob.getVisibilityCache().canSee(livingEntity))
                    && this.updateCountdownTicks <= 0
                    && (
                    this.targetX == 0.0 && this.targetY == 0.0 && this.targetZ == 0.0
                            || livingEntity.squaredDistanceTo(this.targetX, this.targetY, this.targetZ) >= 1.0
                            || this.mob.getRandom().nextFloat() < 0.05F
            )) {
                this.targetX = livingEntity.getX();
                this.targetY = livingEntity.getY();
                this.targetZ = livingEntity.getZ();
                this.updateCountdownTicks = 4 + this.mob.getRandom().nextInt(7);
                double d = this.mob.squaredDistanceTo(livingEntity);
                if (d > 1024.0) {
                    this.updateCountdownTicks += 10;
                } else if (d > 256.0) {
                    this.updateCountdownTicks += 5;
                }

                if (!this.mob.getNavigation().startMovingTo(livingEntity, this.speed)) {
                    this.updateCountdownTicks += 15;
                }

                this.updateCountdownTicks = this.getTickCount(this.updateCountdownTicks);
            }

            this.cooldown = Math.max(this.cooldown - 1, 0);
            this.attack(livingEntity);
        }
    }

    protected void attack(LivingEntity target) {
        if (this.canAttack(target)) {
            this.resetCooldown();
            this.mob.swingHand(Hand.MAIN_HAND);
            this.mob.tryAttack(getServerWorld(this.mob), target);
        }
    }

    protected void resetCooldown() {
        this.cooldown = this.getTickCount(20);
    }

    protected boolean isCooledDown() {
        return this.cooldown <= 0;
    }

    protected boolean canAttack(LivingEntity target) {
        return this.isCooledDown() && this.mob.isInAttackRange(target) && this.mob.getVisibilityCache().canSee(target);
    }

}
