package cftp;

import net.minecraft.client.sound.*;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;

import java.util.concurrent.CompletableFuture;

public class LoopingSoundInstance extends MovingSoundInstance {

    protected LoopingSoundInstance(
            SoundEvent soundEvent,
            SoundCategory soundCategory,
            Random random,
            Vec3d pos
    ) {
        super(soundEvent, soundCategory, random);

        this.x = pos.getX();
        this.y = pos.getY();
        this.z = pos.getZ();

        this.volume = 1.0f;
        this.pitch = 1.0f;
        this.repeat = true;
    }

    public void stop() {
        this.repeat = false;
    }

    @Override
    public void tick() {
        if (!repeat) {
            this.setDone();
        }
    }

    @Override
    public boolean shouldAlwaysPlay() {
        return super.shouldAlwaysPlay();
    }

    @Override
    public boolean canPlay() {
        return super.canPlay();
    }

    @Override
    public CompletableFuture<AudioStream> getAudioStream(SoundLoader loader, Identifier id, boolean repeatInstantly) {
        return super.getAudioStream(loader, id, repeatInstantly);
    }
}
