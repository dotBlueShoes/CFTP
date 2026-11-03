package cftp.particles;

import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.util.math.random.Random;
import org.jetbrains.annotations.NotNull;

public class ElectricParticle extends SpriteBillboardParticle {

    protected final Random random = Random.create();

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
    }

    @Override
    public int getBrightness(float tint) {
        return 240;
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_OPAQUE;
    }

    @Override
    public void tick() {
        super.tick();
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

            {
                particle.age = particle.random.nextInt(30);
                particle.maxAge = 20 * 3;
                particle.velocityY = 0;
            }

            return particle;
        }
    }

}
