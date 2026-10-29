package cftp.registries;

import cftp.CFTP;
import cftp.statuseffects.*;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

public class CFTPStatusEffects {

    public static final RegistryEntry<StatusEffect> CONDUCTIVE_STATUS_EFFECT = register(
            "conductive", new ConductiveStatusEffect(StatusEffectCategory.NEUTRAL, 0x36ebeb)
                    .addAttributeModifier(
                            EntityAttributes.MOVEMENT_SPEED,
                            Identifier.of(CFTP.MOD_ID, "conductive"),
                            +0.10f,
                            EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
                    )
    );


    public static final RegistryEntry<StatusEffect> BURYING_STATUS_EFFECT = register(
            "burying", new BuryingStatusEffect(StatusEffectCategory.NEUTRAL, 0x36ebeb)
    );


    public static final RegistryEntry<StatusEffect> JOHN_STATUS_EFFECT = register(
            "john", new JohnStatusEffect(StatusEffectCategory.NEUTRAL, 0x36ebeb)
                    // livingEntity with it moves a bit slower.
                    .addAttributeModifier(
                            EntityAttributes.MOVEMENT_SPEED,
                            Identifier.of(CFTP.MOD_ID, "john"),
                            -0.10f,
                            EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
                    )
                    // livingEntity with it has increased follow range.
                    .addAttributeModifier(
                            EntityAttributes.FOLLOW_RANGE,
                            Identifier.of(CFTP.MOD_ID, "john"),
                            +0.25f,
                            EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
                    )
                    // livingEntity with it deals a bit more damage with normal attacks.
                    .addAttributeModifier(
                            EntityAttributes.ATTACK_DAMAGE,
                            Identifier.of(CFTP.MOD_ID, "john"),
                            +0.10f,
                            EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
                    )
    );

    private static RegistryEntry<StatusEffect> register(String name, StatusEffect statusEffect) {
        return Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(CFTP.MOD_ID, name), statusEffect);
    }

    public static void register () {

    }

}
