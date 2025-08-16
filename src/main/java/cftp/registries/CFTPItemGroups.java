package cftp.registries;

import cftp.CFTP;
import cftp.entity.CFTPEntities;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class CFTPItemGroups {

    private static final String ITEM_GROUP_ITEMS = "itemgroup.cftp.cftp_items";

    public static final ItemGroup CFTP_ITEMS = Registry.register(
            Registries.ITEM_GROUP,
            Identifier.of(CFTP.MOD_ID, "coin_copper"),
            FabricItemGroup.builder()
                    .icon(() -> new ItemStack(CFTPItems.COIN_COPPER))
                    .displayName(Text.translatable(ITEM_GROUP_ITEMS))
                    .entries(
                            (displayContext, entries) -> {
                                entries.add(CFTPItems.ELEMENTAL_POWDER);
                                entries.add(CFTPItems.EARTH_CHARGE);
                                entries.add(CFTPItems.WATER_CHARGE);
                                entries.add(CFTPItems.SAW_DUST);
                                entries.add(CFTPItems.COIN_COPPER);
                                entries.add(CFTPItems.COIN_IRON);
                                entries.add(CFTPItems.COIN_GOLD);
                                entries.add(CFTPItems.COPPER_ROD);
                                entries.add(CFTPItems.ENRICHED_MUSHROOM_STEW);
                                //
                                entries.add(CFTPBlocks.SAW_DUST_BLOCK);
                                entries.add(CFTPBlocks.YELLOW_MUSHROOM);
                                entries.add(CFTPBlocks.BLUE_MUSHROOM);
                                entries.add(CFTPBlocks.YELLOW_COBWEB);
                                entries.add(CFTPBlocks.BLUE_COBWEB);
                                //
                                entries.add(CFTPItems.CREEPER_COOKIE_SPAWN_EGG);
                                entries.add(CFTPItems.CREEPER_DIRT_SPAWN_EGG);
                                entries.add(CFTPItems.CREEPER_EARTH_SPAWN_EGG);
                                entries.add(CFTPItems.CREEPER_ENDER_SPAWN_EGG);
                                entries.add(CFTPItems.CREEPER_FIRE_SPAWN_EGG);
                                entries.add(CFTPItems.CREEPER_GHOST_SPAWN_EGG);
                                entries.add(CFTPItems.CREEPER_LAVA_SPAWN_EGG);
                                entries.add(CFTPItems.CREEPER_LIGHTING_SPAWN_EGG);
                                entries.add(CFTPItems.CREEPER_NETHER_SPAWN_EGG);
                                entries.add(CFTPItems.CREEPER_WATER_SPAWN_EGG);
                                entries.add(CFTPItems.CREEPER_WIND_SPAWN_EGG);
                                entries.add(CFTPItems.CREEPER_FLIP_SPAWN_EGG);
                                entries.add(CFTPItems.CREEPER_FRIENDLY_SPAWN_EGG);
                                entries.add(CFTPItems.CREEPER_SNOW_SPAWN_EGG);
                                entries.add(CFTPItems.CREEPER_SWAMP_SPAWN_EGG);
                                entries.add(CFTPItems.CREEPER_DARK_SPAWN_EGG);
                                entries.add(CFTPItems.SPIDER_YELLOW_SPAWN_EGG);
                                entries.add(CFTPItems.SPIDER_BLUE_SPAWN_EGG);
                            }
                    )
                    .build()
    );

    //ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS).register(entries -> {
    //    entries.add(CREEPER_COOKIE_SPAWN_EGG);
    //});

    // HACK. Java is weird. We need to call a method even if empty. To make the unreferenced variables
    //  we made compile from this file-class.
    public static void register() {

    }

}
