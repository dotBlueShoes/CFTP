package cftp.statuseffects;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.math.Vec3d;

public class BuryingStatusEffect extends StatusEffect {

    public static final Vec3d SLOW = new Vec3d(0.05, 0.05, 0.05); // I -> 5%
    public static float SLOW_PER_LEVEL = 0.03f;                            // n -> 5%

    public static final int DURATION     = 20 * 7;                         // I -> 7 seconds
    public static int DURATION_PER_LEVEL = 20 * 3;                         // n -> 3 seconds

    public BuryingStatusEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }
}
