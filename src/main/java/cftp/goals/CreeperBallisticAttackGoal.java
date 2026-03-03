package cftp.goals;

import java.util.EnumSet;

import cftp.CFTP;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.TntEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;

public class CreeperBallisticAttackGoal extends Goal {

    protected final PathAwareEntity mob;
    private final double speed;
    private final boolean pauseWhenMobIdle;

    private Path path;

    private double targetX;
    private double targetY;
    private double targetZ;

    private int updateCountdownTicks;
    private int cooldown;

    private long lastUpdateTime;

    private final int throwIntervalTicks = 160; // 8s
    private int throwCooldown = throwIntervalTicks;

    public CreeperBallisticAttackGoal(PathAwareEntity mob, double speed, boolean pauseWhenMobIdle) {
        this.mob = mob;
        this.speed = speed;
        this.pauseWhenMobIdle = pauseWhenMobIdle;
        this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
    }

    @Override
    public boolean canStart() {
        long time = this.mob.getWorld().getTime();

        if (time - this.lastUpdateTime >= 20L) {
            this.lastUpdateTime = time;

            LivingEntity livingEntity = this.mob.getTarget();

            if (livingEntity == null) {
                return false;
            } else if (!livingEntity.isAlive()) {
                return false;
            } else {
                // Calculate initial path.
                this.path = this.mob.getNavigation().findPathTo(livingEntity, 0);

                return this.path != null || this.mob.isInAttackRange(livingEntity);
            }
        }

        return false;
    }

    @Override
    public boolean shouldContinue() {
        LivingEntity livingEntity = this.mob.getTarget();

        if (livingEntity == null) {
            return false;
        } else if (!livingEntity.isAlive()) {
            return false;
        } else if (!this.pauseWhenMobIdle) {
            return !this.mob.getNavigation().isIdle();
        } else {
            return this.mob.isInWalkTargetRange(livingEntity.getBlockPos()) && (!(livingEntity instanceof PlayerEntity) || !livingEntity.isSpectator() && !((PlayerEntity) livingEntity).isCreative());
        }
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

    private void throwTntWithVelocity(ServerWorld world, double x, double y, double z) {
        // Create primed TNT
        TntEntity tnt = new TntEntity(EntityType.TNT, world);
        tnt.refreshPositionAndAngles(this.mob.getX(), this.mob.getY(), this.mob.getZ(), 0.0f, 0.0f);

        // Set fuse duration (default is 80 ticks = 4 seconds)
        tnt.setFuse(60);

        final float boost = 2.4f;

        // get player position;
        Vec3d velocity = new Vec3d(x * boost, y, z * boost);

        // Apply velocity
        tnt.setVelocity(velocity);

        // Add to world
        world.spawnEntity(tnt);
    }

    private void throwTntAtPosition(double targetPosX, double targetPosY, double targetPosZ) {
        final double g = 0.04;
        final double throwTime = 30; // ticks: 20ticks - 1s.

        double x = (targetPosX - this.mob.getX()) / throwTime;
        double z = (targetPosZ - this.mob.getZ()) / throwTime;
        double y = (targetPosY - this.mob.getY() + 0.5 * g * throwTime * throwTime) / throwTime;

        throwTntWithVelocity(getServerWorld(this.mob), x, y, z);
    }

    @Override
    public void tick() {
        LivingEntity livingEntity = this.mob.getTarget();

        if (livingEntity != null) {

            this.mob.getLookControl().lookAt(livingEntity, 30.0F, 30.0F);
            this.updateCountdownTicks = Math.max(this.updateCountdownTicks - 1, 0);

            if ((this.pauseWhenMobIdle || this.mob.getVisibilityCache().canSee(livingEntity))
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

            //CFTP.LOGGER.info("tick!");

            ++throwCooldown;
            if (throwCooldown > throwIntervalTicks) {
                throwCooldown = 1;

                throwTntAtPosition(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ());
                //CFTP.LOGGER.info("throw!");
            }

            //this.attack(livingEntity);
        }
    }

    //protected void attack(LivingEntity target) {
    //    if (this.canAttack(target)) {
    //
    //        CFTP.LOGGER.info("attack!");
    //
    //        this.resetCooldown();
    //        this.mob.swingHand(Hand.MAIN_HAND);
    //        this.mob.tryAttack(getServerWorld(this.mob), target);
    //    }
    //}

    protected void resetCooldown() {
        this.cooldown = this.getTickCount(20);
    }

    protected boolean isCooledDown() {
        return this.cooldown <= 0;
    }

    protected boolean canAttack(LivingEntity target) {
        return this.isCooledDown() && this.mob.isInAttackRange(target) && this.mob.getVisibilityCache().canSee(target);
    }

    //protected int getCooldown() {
    //    return this.cooldown;
    //}
    //protected int getMaxCooldown() {
    //    return this.getTickCount(20);
    //}
}
