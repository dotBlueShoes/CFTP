package cftp.particles;

import cftp.registries.CFTPParticles;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.util.math.random.Random;
import org.jetbrains.annotations.NotNull;

public class ElectricParticle extends SpriteBillboardParticle {

    protected final Random random = Random.create();

    public final static float COLOR_R = 1.0000f;
    public final static float COLOR_G = 0.9725f;
    public final static float COLOR_B = 0.7196f;

    private final SpriteProvider spriteProvider;

    protected ElectricParticle(
            ClientWorld clientWorld,
            double x, double y, double z,
            double velocityX, double velocityY, double velocityZ,
            SpriteProvider spriteProvider
    ) {
        super(clientWorld, x, y, z, velocityX, velocityY, velocityZ);
        this.spriteProvider = spriteProvider;
        this.setSpriteForAge(spriteProvider);

        this.age = this.random.nextInt(30);
        this.maxAge = 20 * 3;
        this.velocityY = 0;
    }

    @Override
    public int getBrightness(float tint) {
        return 240;
    }

    //ParticleTypes

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_OPAQUE;
    }

    @Override
    public void tick() {
        super.tick();

        //int frame = (this.age / 2) % 5;
        //this.setSprite(sprites.get(frame));

        this.setSpriteForAge(this.spriteProvider);
    }

    // Actual creation / assignment logic.
    public record Factory(SpriteProvider spriteProvider) implements ParticleFactory<SimpleParticleType> {
        @Override
        public @NotNull Particle createParticle(
                SimpleParticleType parameters,
                ClientWorld clientWorld,
                double x, double y, double z,
                double velocityX, double velocityY, double velocityZ
        ) {
            ElectricParticle particle = new ElectricParticle(
                    clientWorld,
                    x, y, z,
                    velocityX, velocityY, velocityZ,
                    spriteProvider
            );

            //particle.setColor(COLOR_R, COLOR_G, COLOR_B);
            return particle;
        }
    }

}
