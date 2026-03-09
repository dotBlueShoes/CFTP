package cftp.registries;

import cftp.CFTP;
import cftp.items.EarthChargeItem;
import cftp.items.ElementalPowderItem;
import cftp.items.Wand;
import cftp.items.WaterChargeItem;
import net.fabricmc.fabric.api.registry.FuelRegistryEvents;
import net.minecraft.item.*;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;

import java.util.function.Function;

import static net.minecraft.item.Items.BOWL;

public class CFTPItems {

    public static final TagKey<Item> CHARGES = TagKey.of(RegistryKeys.ITEM, Identifier.of(CFTP.MOD_ID, "charges"));

    public static final Item ELEMENTAL_POWDER = register("elemental_powder", ElementalPowderItem::new,
            new ElementalPowderItem.Settings()
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

    public static final Item CREEPER_FUSE = register("creeper_fuse", Item::new,
            new Item.Settings()
    );

    public static final Item CREEPER_FISH = register("creeper_fish", Item::new,
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

    public static final Item CREEPER_GOLDEN_SPAWN_EGG = register(
            "creeper_golden_spawn_egg",
            settings -> new SpawnEggItem(CFTPEntities.CREEPER_GOLDEN, settings),
            new Item.Settings()
    );

    public static final Item CREEPER_BRIDGER_SPAWN_EGG = register(
            "creeper_bridger_spawn_egg",
            settings -> new SpawnEggItem(CFTPEntities.CREEPER_BRIDGER, settings),
            new Item.Settings()
    );

    public static final Item CREEPER_PIGGY_SPAWN_EGG = register(
            "creeper_piggy_spawn_egg",
            settings -> new SpawnEggItem(CFTPEntities.CREEPER_PIGGY, settings),
            new Item.Settings()
    );

    public static final Item CREEPER_AMALGAM_SPAWN_EGG = register(
            "creeper_amalgam_spawn_egg",
            settings -> new SpawnEggItem(CFTPEntities.CREEPER_AMALGAM, settings),
            new Item.Settings()
    );

    public static final Item CREEPER_AMETHYST_SPAWN_EGG = register(
            "creeper_amethyst_spawn_egg",
            settings -> new SpawnEggItem(CFTPEntities.CREEPER_AMETHYST, settings),
            new Item.Settings()
    );

    public static final Item CREEPER_BREWER_SPAWN_EGG = register(
            "creeper_brewer_spawn_egg",
            settings -> new SpawnEggItem(CFTPEntities.CREEPER_BREWER, settings),
            new Item.Settings()
    );

    public static final Item CREEPER_GIANT_SPAWN_EGG = register(
            "creeper_giant_spawn_egg",
            settings -> new SpawnEggItem(CFTPEntities.CREEPER_GIANT, settings),
            new Item.Settings()
    );

    public static final Item CREEPER_HARVEST_SPAWN_EGG = register(
            "creeper_harvest_spawn_egg",
            settings -> new SpawnEggItem(CFTPEntities.CREEPER_HARVEST, settings),
            new Item.Settings()
    );

    public static final Item CREEPER_OCEANID_SPAWN_EGG = register(
            "creeper_oceanid_spawn_egg",
            settings -> new SpawnEggItem(CFTPEntities.CREEPER_OCEANID, settings),
            new Item.Settings()
    );

    public static final Item CREEPER_SAND_SPAWN_EGG = register(
            "creeper_sand_spawn_egg",
            settings -> new SpawnEggItem(CFTPEntities.CREEPER_SAND, settings),
            new Item.Settings()
    );

    public static final Item CREEPER_SCULK_SPAWN_EGG = register(
            "creeper_sculk_spawn_egg",
            settings -> new SpawnEggItem(CFTPEntities.CREEPER_SCULK, settings),
            new Item.Settings()
    );

    public static final Item CREEPER_SPECTRE_SPAWN_EGG = register(
            "creeper_spectre_spawn_egg",
            settings -> new SpawnEggItem(CFTPEntities.CREEPER_SPECTRE, settings),
            new Item.Settings()
    );

    public static final Item CREEPER_HEROBRINE_SPAWN_EGG = register(
            "creeper_herobrine_spawn_egg",
            settings -> new SpawnEggItem(CFTPEntities.CREEPER_HEROBRINE, settings),
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

    public static final Item SLENDERMAN_SPAWN_EGG = register(
            "slenderman_spawn_egg",
            settings -> new SpawnEggItem(CFTPEntities.SLENDERMAN, settings),
            new Item.Settings()
    );

    public static final Item HORSE_FIERY_SPAWN_EGG = register(
            "horse_fiery_spawn_egg",
            settings -> new SpawnEggItem(CFTPEntities.HORSE_FIERY, settings),
            new Item.Settings()
    );

    public static Item register(String path, Function<Item.Settings, Item> factory, Item.Settings settings) {
        final RegistryKey<Item> registryKey = RegistryKey.of(
                RegistryKeys.ITEM,
                Identifier.of(CFTP.MOD_ID, path)
        );

        return net.minecraft.item.Items.register(registryKey, factory, settings);
    }

    // HACK! Java is weird. We need to call a method even if empty.
    //  To make the unreferenced variables we made compile from this file-class.
    public static void register() {
    }

}
