package cftp.statuseffects;

import cftp.registries.CFTPParticles;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class JohnStatusEffect extends StatusEffect {

    public JohnStatusEffect(StatusEffectCategory category, int color) {
        super(category, color, CFTPParticles.DRIPPING_BLOOD_PARTICLE);
    }

    // happens every tick
    //@Override
    //public boolean applyUpdateEffect(ServerWorld world, LivingEntity entity, int amplifier) {
    //    return super.applyUpdateEffect(world, entity, amplifier);
    //}

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }
}
