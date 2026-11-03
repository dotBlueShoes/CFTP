package cftp.statuseffects;

import cftp.registries.CFTPParticles;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class MadnessStatusEffect extends StatusEffect {

    public MadnessStatusEffect(StatusEffectCategory category, int color) {
        super(category, color, CFTPParticles.DRIPPING_BLOOD_PARTICLE);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }
}
