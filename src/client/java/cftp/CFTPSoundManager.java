package cftp;

import cftp.registries.CFTPSoundEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;

public class CFTPSoundManager {

    // There's always just one instance of burying sound.
    public static LoopingSoundInstance buryingSound;

    public static void playBurying(Vec3d pos) {
        // In case another burying sound got called to be played without previous stopping.
        if (buryingSound != null && !buryingSound.isDone()) {
            stopBuryingUnsafe();
        }

        buryingSound = new LoopingSoundInstance(
                CFTPSoundEvents.STATUS_EFFECT_BURYING_LOOPING,
                SoundCategory.HOSTILE,
                Random.create(),
                pos
        );

        MinecraftClient.getInstance()
                .getSoundManager()
                .play(buryingSound);
    }

    private static void stopBuryingUnsafe() {
        buryingSound.stop();

        MinecraftClient.getInstance()
                .getSoundManager()
                .stop(buryingSound);

        buryingSound = null;
    }

    public static void stopBurying() {
        if (buryingSound != null) stopBuryingUnsafe();
    }

}
