package cftp.particles;

import cftp.registries.CFTPParticles;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.SimpleParticleType;
import org.jetbrains.annotations.NotNull;

public class BloodLeakParticle extends SpriteBillboardParticle {

    public final static float COLOR_R = 0.9220f;
    public final static float COLOR_G = 0.0625f;
    public final static float COLOR_B = 0.0625f;

    protected BloodLeakParticle(
            ClientWorld clientWorld,
            double x, double y, double z
    ) {
        super(clientWorld, x, y, z);
        this.setBoundingBoxSpacing(0.01f, 0.01f);
        this.gravityStrength = 0.06f;
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_OPAQUE;
    }

    protected void atDeath() {}
    protected void updateVelocity() {}

    @Override
    public void tick() {
        this.prevPosX = this.x;
        this.prevPosY = this.y;
        this.prevPosZ = this.z;

        // update age
        if (--this.maxAge <= 0) {
            this.markDead();
            this.atDeath();
            return;
        }

        // apply gravity.
        this.velocityY -= this.gravityStrength;
        this.move(this.velocityX, this.velocityY, this.velocityZ);

        // apply custom velocity.
        this.updateVelocity();
    }

    public static class Dripping extends BloodLeakParticle {
        private final ParticleEffect nextParticle;

        Dripping(
                ClientWorld world,
                double x, double y, double z,
                SpriteProvider spriteProvider,
                ParticleEffect nextParticle
        ) {
            super(world, x, y, z);
            this.setSpriteForAge(spriteProvider);
            this.nextParticle = nextParticle;
            this.gravityStrength *= 0.02f;
            this.maxAge = 40;
        }

        @Override
        protected void atDeath() {
            // Create next particle in place of old one.
            this.world.addParticle(
                    this.nextParticle,
                    this.x, this.y, this.z,
                    this.velocityX, this.velocityY, this.velocityZ
            );
        }

        @Override
        protected void updateVelocity() {
            this.velocityX *= 0.02;
            this.velocityY *= 0.02;
            this.velocityZ *= 0.02;

            this.velocityX *= 0.98f;
            this.velocityY *= 0.98f;
            this.velocityZ *= 0.98f;
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
                Dripping bloodParticle = new Dripping(
                        clientWorld,
                        x, y, z,
                        spriteProvider,
                        CFTPParticles.FALLING_BLOOD_PARTICLE
                );

                bloodParticle.setColor(COLOR_R, COLOR_G, COLOR_B);
                return bloodParticle;
            }
        }
    }

    public static class Falling extends BloodLeakParticle {
        protected final ParticleEffect nextParticle;

        Falling(
                ClientWorld world,
                double x, double y, double z,
                SpriteProvider spriteProvider,
                ParticleEffect nextParticle,
                int maxAge
        ) {
            super(world, x, y, z);
            this.setSpriteForAge(spriteProvider);
            this.nextParticle = nextParticle;
            this.maxAge = maxAge;
        }

        @Override
        protected void updateVelocity() {
            if (this.onGround) {
                this.markDead();
                this.world.addParticle(
                        this.nextParticle,
                        this.x, this.y, this.z,
                        0.0, 0.0, 0.0
                );
            }
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
                Falling bloodParticle = new Falling(
                        clientWorld,
                        x, y, z,
                        spriteProvider,
                        CFTPParticles.LANDING_BLOOD_PARTICLE,
                        (int) (64.0 / (Math.random() * 0.8 + 0.2))
                );

                bloodParticle.setColor(COLOR_R, COLOR_G, COLOR_B);
                return bloodParticle;
            }
        }
    }

    public static class Landing extends BloodLeakParticle {
        Landing(
                ClientWorld clientWorld,
                double x, double y, double z,
                SpriteProvider spriteProvider
        ) {
            super(clientWorld, x, y, z);
            this.setSpriteForAge(spriteProvider);
            this.maxAge = (int)(16.0 / (Math.random() * 0.8 + 0.2));
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
                Landing bloodParticle = new Landing(
                        clientWorld,
                        x, y, z,
                        spriteProvider
                );

                bloodParticle.setColor(COLOR_R, COLOR_G, COLOR_B);
                return bloodParticle;
            }
        }
    }

}
