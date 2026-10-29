package cftp.utility;

import cftp.registries.CFTPStatusEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.world.Difficulty;

public class JohnHelper {

    public static final int EFFECT_DURATION_EASY = 20 *  60; // 1min
    public static final int EFFECT_DURATION_NORM = 20 * 300; // 5min
    public static final int EFFECT_DURATION_HARD = 20 * 600; // 10min

    public static void applyJohnBasedOnDifficulty(LivingEntity target, LivingEntity source) {
        final Difficulty difficulty = target.getWorld().getDifficulty();
        int duration;

        switch (difficulty) {
            case PEACEFUL: return;
            case EASY: {
                duration = EFFECT_DURATION_EASY;
            } break;
            case NORMAL: {
                duration = EFFECT_DURATION_NORM;
            } break;
            case HARD:
            default: {
                duration = EFFECT_DURATION_HARD;
            }
        }

        target.addStatusEffect(
                new StatusEffectInstance(
                        CFTPStatusEffects.JOHN_STATUS_EFFECT,
                        duration,
                        0,
                        true,
                        true,
                        true
                ),
                source
        );
    }

}
