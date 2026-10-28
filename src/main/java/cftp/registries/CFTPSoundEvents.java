package cftp.registries;

import cftp.CFTP;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class CFTPSoundEvents {

    public static final SoundEvent ENTITY_JOHN_AMBIENT = register("entity.john.ambient");
    public static final SoundEvent ENTITY_JOHN_HURT = register("entity.john.hurt");
    public static final SoundEvent ENTITY_JOHN_DEATH = register("entity.john.death");

    private static SoundEvent register(String name) {
        Identifier id = Identifier.of(CFTP.MOD_ID, name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    public static void register() {

    }

}
