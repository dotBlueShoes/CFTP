package cftp.item;

import cftp.CFTP;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistryEvents;
import net.fabricmc.fabric.impl.content.registry.FlammableBlockRegistryImpl;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.ColoredFallingBlock;
import net.minecraft.block.MapColor;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.ColorCode;
import net.minecraft.util.Identifier;

public class CFTPBlocks {

    //public static final Block SAW_DUST_BLOCK = registerDefaultBlock(
    //        "saw_dust_block",
    //        AbstractBlock.Settings.create()
    //                .mapColor(MapColor.DIRT_BROWN)
    //                .strength(0.5F)
    //                .sounds(BlockSoundGroup.GRAVEL)
    //                .burnable()
    //);

    public static final Block SAW_DUST_BLOCK = registerFallingBlock(
            "saw_dust_block",
            AbstractBlock.Settings.create()
                    .mapColor(MapColor.DIRT_BROWN)
                    .strength(0.5F)
                    .sounds(BlockSoundGroup.GRAVEL)
                    .burnable()
    );

    //public static final Block SAW_DUST_BLOCK = register(
    //        "saw_dust_block",
    //        settings -> new ColoredFallingBlock(new ColorCode(-8356741), settings),
    //        AbstractBlock.Settings.create().mapColor(MapColor.STONE_GRAY).instrument(NoteBlockInstrument.SNARE).strength(0.6F).sounds(BlockSoundGroup.GRAVEL)
    //);

    private static Block registerFallingBlock(String name, AbstractBlock.Settings blockSettings) {
        RegistryKey<Block> key = RegistryKey.of(
                RegistryKeys.BLOCK,
                Identifier.of(CFTP.MOD_ID, name)
        );

        Block block = new ColoredFallingBlock(new ColorCode(-8356741), blockSettings.registryKey(key));
        registerBlockItem(name, block);

        return Registry.register(Registries.BLOCK, key, block);
    }

    private static Block registerDefaultBlock(String name, AbstractBlock.Settings blockSettings) {
        RegistryKey<Block> key = RegistryKey.of(
                RegistryKeys.BLOCK,
                Identifier.of(CFTP.MOD_ID, name)
        );

        Block block = new Block(blockSettings.registryKey(key));
        registerBlockItem(name, block);

        return Registry.register(Registries.BLOCK, key, block);
    }


    private static void registerBlockItem(String name, Block block) {
        RegistryKey<Item> key = RegistryKey.of(
                RegistryKeys.ITEM,
                Identifier.of(CFTP.MOD_ID, name)
        );

        BlockItem item = new BlockItem(block, new Item.Settings().registryKey(key));
        Registry.register(Registries.ITEM, key, item);
    }


    public static void register() {

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(
                entries -> entries.add(SAW_DUST_BLOCK)
        );

        // Hay block values.
        FlammableBlockRegistry.getDefaultInstance().add(SAW_DUST_BLOCK, 60, 20);

    }

}
