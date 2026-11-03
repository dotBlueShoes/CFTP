package cftp.registries;

import cftp.particles.*;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;

public class CFTPParticlesClient {

    public static void register() {
        ParticleFactoryRegistry.getInstance().register(
                CFTPParticles.DRIPPING_BLOOD_PARTICLE,
                BloodLeakParticle.Dripping.Factory::new
        );

        ParticleFactoryRegistry.getInstance().register(
                CFTPParticles.FALLING_BLOOD_PARTICLE,
                BloodLeakParticle.Falling.Factory::new
        );

        ParticleFactoryRegistry.getInstance().register(
                CFTPParticles.LANDING_BLOOD_PARTICLE,
                BloodLeakParticle.Landing.Factory::new
        );

        ParticleFactoryRegistry.getInstance().register(
                CFTPParticles.ELECTRIC_PARTICLE,
                ElectricParticle.Factory::new
        );

        ParticleFactoryRegistry.getInstance().register(
                CFTPParticles.GOLDENING_PARTICLE,
                BrickParticle.FactoryGoldening::new
        );

        ParticleFactoryRegistry.getInstance().register(
                CFTPParticles.BURYING_PARTICLE,
                BrickParticle.FactoryBurying::new
        );
    }

}
