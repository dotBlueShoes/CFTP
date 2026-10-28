package cftp.registries;

import cftp.CFTP;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class CFTPParticles {

    public static final SimpleParticleType DRIPPING_BLOOD_PARTICLE = register(
            "dripping_blood_particle",
            FabricParticleTypes.simple(true)
    );

    public static final SimpleParticleType FALLING_BLOOD_PARTICLE = register(
            "falling_blood_particle",
            FabricParticleTypes.simple(true)
    );

    public static final SimpleParticleType LANDING_BLOOD_PARTICLE = register(
            "landing_blood_particle",
            FabricParticleTypes.simple(true)
    );

    private static SimpleParticleType register(String name, SimpleParticleType particleType) {
        return Registry.register(Registries.PARTICLE_TYPE, Identifier.of(CFTP.MOD_ID, name), particleType);
    }

    public static void register() {

    }

}
