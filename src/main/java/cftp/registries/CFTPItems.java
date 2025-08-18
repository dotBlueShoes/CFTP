package cftp.registries;

import cftp.CFTP;
import cftp.entity.CFTPEntities;
import cftp.items.EarthChargeItem;
import cftp.items.ElementalPowderItem;
import cftp.items.Wand;
import cftp.items.WaterChargeItem;
import net.fabricmc.fabric.api.registry.FuelRegistryEvents;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import java.util.function.Function;

import static net.minecraft.item.Items.BOWL;

public class CFTPItems {

    public static final TagKey<Item> CHARGES = TagKey.of(RegistryKeys.ITEM, Identifier.of(CFTP.MOD_ID, "charges"));

    public static final Item ELEMENTAL_POWDER = register("elemental_powder", ElementalPowderItem::new,
            new ElementalPowderItem.Settings()
    );

    public static final Item SAW_DUST = register("saw_dust", Item::new,
            new Item.Settings()
    );

    public static final Item EARTH_CHARGE = register("earth_charge", EarthChargeItem::new,
            new Item.Settings()
                    .useCooldown(0.5F)
    );

    public static final Item WATER_CHARGE = register("water_charge", WaterChargeItem::new,
            new Item.Settings()
                    .useCooldown(0.5F)
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

    public static final Item ENRICHED_MUSHROOM_STEW = register("enriched_mushroom_stew", Item::new,
            new Item.Settings()
                    .maxCount(1)
                    .food(CFTPFoods.ENRICHED_MUSHROOM_STEW) // 10 hearts
                    .useRemainder(BOWL)
    );

    public static final Item COPPER_ROD = register("copper_rod", Wand::new,
            new Wand.Settings()
    );

    public static final Item ELEMENTAL_INGOT = register("elemental_ingot", Item::new,
            new Item.Settings()
    );

    public static final Item CREEPER_COOKIE_SPAWN_EGG = register(
            "creeper_cookie_spawn_egg",
            settings -> new SpawnEggItem(CFTPEntities.CREEPER_COOKIE, settings),
            new Item.Settings()
    );

    public static final Item CREEPER_DIRT_SPAWN_EGG = register(
            "creeper_dirt_spawn_egg",
            settings -> new SpawnEggItem(CFTPEntities.CREEPER_DIRT, settings),
            new Item.Settings()
    );

    public static final Item CREEPER_EARTH_SPAWN_EGG = register(
            "creeper_earth_spawn_egg",
            settings -> new SpawnEggItem(CFTPEntities.CREEPER_EARTH, settings),
            new Item.Settings()
    );

    public static final Item CREEPER_ENDER_SPAWN_EGG = register(
            "creeper_ender_spawn_egg",
            settings -> new SpawnEggItem(CFTPEntities.CREEPER_ENDER, settings),
            new Item.Settings()
    );

    public static final Item CREEPER_FIRE_SPAWN_EGG = register(
            "creeper_fire_spawn_egg",
            settings -> new SpawnEggItem(CFTPEntities.CREEPER_FIRE, settings),
            new Item.Settings()
    );

    public static final Item CREEPER_GHOST_SPAWN_EGG = register(
            "creeper_ghost_spawn_egg",
            settings -> new SpawnEggItem(CFTPEntities.CREEPER_GHOST, settings),
            new Item.Settings()
    );

    public static final Item CREEPER_LAVA_SPAWN_EGG = register(
            "creeper_lava_spawn_egg",
            settings -> new SpawnEggItem(CFTPEntities.CREEPER_LAVA, settings),
            new Item.Settings()
    );

    public static final Item CREEPER_LIGHTING_SPAWN_EGG = register(
            "creeper_lighting_spawn_egg",
            settings -> new SpawnEggItem(CFTPEntities.CREEPER_LIGHTING, settings),
            new Item.Settings()
    );

    public static final Item CREEPER_NETHER_SPAWN_EGG = register(
            "creeper_nether_spawn_egg",
            settings -> new SpawnEggItem(CFTPEntities.CREEPER_NETHER, settings),
            new Item.Settings()
    );

    public static final Item CREEPER_WATER_SPAWN_EGG = register(
            "creeper_water_spawn_egg",
            settings -> new SpawnEggItem(CFTPEntities.CREEPER_WATER, settings),
            new Item.Settings()
    );

    public static final Item CREEPER_WIND_SPAWN_EGG = register(
            "creeper_wind_spawn_egg",
            settings -> new SpawnEggItem(CFTPEntities.CREEPER_WIND, settings),
            new Item.Settings()
    );

    public static final Item CREEPER_FLIP_SPAWN_EGG = register(
            "creeper_flip_spawn_egg",
            settings -> new SpawnEggItem(CFTPEntities.CREEPER_FLIP, settings),
            new Item.Settings()
    );

    public static final Item CREEPER_FRIENDLY_SPAWN_EGG = register(
            "creeper_friendly_spawn_egg",
            settings -> new SpawnEggItem(CFTPEntities.CREEPER_FRIENDLY, settings),
            new Item.Settings()
    );

    public static final Item CREEPER_SNOW_SPAWN_EGG = register(
            "creeper_snow_spawn_egg",
            settings -> new SpawnEggItem(CFTPEntities.CREEPER_SNOW, settings),
            new Item.Settings()
    );

    public static final Item CREEPER_SWAMP_SPAWN_EGG = register(
            "creeper_swamp_spawn_egg",
            settings -> new SpawnEggItem(CFTPEntities.CREEPER_SWAMP, settings),
            new Item.Settings()
    );

    public static final Item CREEPER_DARK_SPAWN_EGG = register(
            "creeper_dark_spawn_egg",
            settings -> new SpawnEggItem(CFTPEntities.CREEPER_DARK, settings),
            new Item.Settings()
    );

    public static final Item CREEPER_BALLISTIC_SPAWN_EGG = register(
            "creeper_ballistic_spawn_egg",
            settings -> new SpawnEggItem(CFTPEntities.CREEPER_BALLISTIC, settings),
            new Item.Settings()
    );

    public static final Item SPIDER_YELLOW_SPAWN_EGG = register(
            "spider_yellow_spawn_egg",
            settings -> new SpawnEggItem(CFTPEntities.SPIDER_YELLOW, settings),
            new Item.Settings()
    );

    public static final Item SPIDER_BLUE_SPAWN_EGG = register(
            "spider_blue_spawn_egg",
            settings -> new SpawnEggItem(CFTPEntities.SPIDER_BLUE, settings),
            new Item.Settings()
    );

    //public static final Item CREEPER_COOKIE_SPAWN_EGG = Registry.register(
    //        Registries.ITEM,
    //        new Identifier("cftp", "creeper_cookie_spawn_egg"),
    //        new SpawnEggItem(
    //                CFTPEntities.CREEPER_COOKIE, // Your EntityType
    //                0x00FF00, // Primary egg color
    //                0xAA0000, // Secondary egg color
    //                new Item.Settings()
    //        )
    //);

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
