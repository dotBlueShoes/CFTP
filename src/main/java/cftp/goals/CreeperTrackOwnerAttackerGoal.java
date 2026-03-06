package cftp.goals;

import cftp.entity.creeper.CreeperFriendlyEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.TrackTargetGoal;
import net.minecraft.entity.passive.TameableEntity;

import java.util.EnumSet;

public class CreeperTrackOwnerAttackerGoal extends TrackTargetGoal {
    private final CreeperFriendlyEntity tameable;
    private LivingEntity attacker;
    private int lastAttackedTime;

    public CreeperTrackOwnerAttackerGoal(CreeperFriendlyEntity tameable) {
        super(tameable, false);
        this.tameable = tameable;
        this.setControls(EnumSet.of(Goal.Control.TARGET));
    }

    @Override
    public boolean canStart() {
        if (this.tameable.isTame() /*&& !this.tameable.isSitting()*/) {
            LivingEntity livingEntity = this.tameable.getOwner();
            if (livingEntity == null) {
                return false;
            } else {
                this.attacker = livingEntity.getAttacker();
                int i = livingEntity.getLastAttackedTime();
                return i != this.lastAttackedTime && this.canTrack(this.attacker, TargetPredicate.DEFAULT) && this.tameable.canAttackWithOwner(this.attacker, livingEntity);
            }
        } else {
            return false;
        }
    }

    @Override
    public void start() {
        this.mob.setTarget(this.attacker);
        LivingEntity livingEntity = this.tameable.getOwner();
        if (livingEntity != null) {
            this.lastAttackedTime = livingEntity.getLastAttackedTime();
        }

        super.start();
    }
}
