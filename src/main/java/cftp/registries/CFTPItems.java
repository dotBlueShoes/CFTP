package cftp.registries;

import cftp.CFTP;
import cftp.item.items.*;
import net.fabricmc.fabric.api.registry.FuelRegistryEvents;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public class CFTPItems {

    //public static final TagKey<Item> GUNS_TAG = TagKey.of(RegistryKeys.ITEM,  Identifier.of("my_mod", "guns"));
    //public static final TagKey<Item> CHARGES = TagKey.of(RegistryKeys.ITEM,  Identifier.of("cftp", "charges"));
    public static final TagKey<Item> CHARGES = TagKey.of(RegistryKeys.ITEM, Identifier.of(CFTP.MOD_ID, "charges"));

    public static final Item ELEMENTAL_POWDER = register("elemental_powder", ElementalPowderItem::new,
            new ElementalPowderItem.Settings()
    );

    public static final Item SAW_DUST = register("saw_dust", Item::new,
            new Item.Settings()
    );

    public static final Item EARTH_CHARGE = register("earth_charge", EarthChargeItem::new,
            new Item.Settings().useCooldown(0.5F)
    );

    public static final Item WATER_CHARGE = register("water_charge", WaterChargeItem::new,
            new Item.Settings().useCooldown(0.5F)
    );

    public static final Item COIN_COPPER = register("coin_copper", Item::new,
            new Item.Settings()
    );

    public static final Item COIN_IRON = register("coin_iron", Item::new,
            new Item.Settings()
    );

    public static final Item COIN_GOLD = register("coin_gold", Item::new,
            new Item.Settings()
    );

    //public static final Item COPPER_ROD = register("copper_rod", Item::new,
    //        new Item.Settings()
    //);

    public static final Item COPPER_ROD = register("copper_rod", Wand::new,
            new Wand.Settings()
    );

    //public static final Item FIRE_CHARGE = register("fire_charge", FireChargeItem::new);
    //public static final Item WIND_CHARGE = register("wind_charge", WindChargeItem::new, new Item.Settings().useCooldown(0.5F));

    public static Item register(String path, Function<Item.Settings, Item> factory, Item.Settings settings) {
        final RegistryKey<Item> registryKey = RegistryKey.of(
                RegistryKeys.ITEM,
                Identifier.of(CFTP.MOD_ID, path)
        );

        return net.minecraft.item.Items.register(registryKey, factory, settings);
    }

    // HACK. Java is weird. We need to call a method even if empty. To make the unreferenced variables
    //  we made compile from this file-class.
    public static void register() {
        FuelRegistryEvents.BUILD.register((builder, context) -> {
            // We can add multiple items at once in this lambda.
            builder.add(SAW_DUST, 100); // 0.5 item
        });
    }

    //public static final Item BOW = register("bow", BowItem::new, new Item.Settings().maxDamage(384).enchantable(1));
    //public static final Item ARROW = register("arrow", ArrowItem::new);

}
