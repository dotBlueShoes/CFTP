package cftp.particles;

import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.util.math.random.Random;
import org.jetbrains.annotations.NotNull;

public class BrickParticle extends SpriteBillboardParticle {

    protected final Random random = Random.create();

    public final static float GOLDENING_COLOR_R = 1.0000f;
    public final static float GOLDENING_COLOR_G = 0.8313f;
    public final static float GOLDENING_COLOR_B = 0.2500f;

    public final static float BURYING_COLOR_R = 0.5000f;
    public final static float BURYING_COLOR_G = 0.4392f;
    public final static float BURYING_COLOR_B = 0.2500f;

    private final SpriteProvider spriteProvider;

    protected BrickParticle(
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
    public record FactoryGoldening(SpriteProvider spriteProvider) implements ParticleFactory<SimpleParticleType> {
        @Override
        public @NotNull Particle createParticle(
                SimpleParticleType parameters,
                ClientWorld clientWorld,
                double x, double y, double z,
                double velocityX, double velocityY, double velocityZ
        ) {
            BrickParticle particle = new BrickParticle(
                    clientWorld,
                    x, y, z,
                    velocityX, velocityY, velocityZ,
                    spriteProvider
            );

            {
                particle.setColor(GOLDENING_COLOR_R, GOLDENING_COLOR_G, GOLDENING_COLOR_B);

                particle.age = particle.random.nextInt(40);
                particle.maxAge = 20 * 7;

                particle.velocityX = 0.00f;
                particle.velocityY = -0.0625 - (particle.random.nextFloat() * 0.0625);
                particle.velocityZ = 0.00f;
            }

            return particle;
        }
    }


    // Actual creation / assignment logic.
    public record FactoryBurying(SpriteProvider spriteProvider) implements ParticleFactory<SimpleParticleType> {
        @Override
        public @NotNull Particle createParticle(
                SimpleParticleType parameters,
                ClientWorld clientWorld,
                double x, double y, double z,
                double velocityX, double velocityY, double velocityZ
        ) {
            BrickParticle particle = new BrickParticle(
                    clientWorld,
                    x, y, z,
                    velocityX, velocityY, velocityZ,
                    spriteProvider
            );

            {
                particle.setColor(BURYING_COLOR_R, BURYING_COLOR_G, BURYING_COLOR_B);

                particle.age = particle.random.nextInt(40);
                particle.maxAge = 20 * 7;

                particle.velocityX = particle.random.nextFloat() * 0.01;
                particle.velocityY = particle.random.nextFloat() * 0.0625;
                particle.velocityZ = particle.random.nextFloat() * 0.01;
            }

            return particle;
        }
    }

}
