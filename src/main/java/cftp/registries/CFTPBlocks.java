package cftp.registries;

import cftp.CFTP;
import cftp.blocks.*;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.*;
import net.minecraft.block.MushroomBlock;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.TreeConfiguredFeatures;

import java.util.function.BiFunction;
import java.util.function.Function;

public class CFTPBlocks {

    public static final Block YELLOW_MUSHROOM_BLOCK = registerBlock(
            MushroomBlock::new,
            "yellow_mushroom_block",
            AbstractBlock.Settings.create()
                    .mapColor(MapColor.YELLOW)
                    .instrument(NoteBlockInstrument.BASS)
                    .sounds(BlockSoundGroup.COBWEB)
                    .strength(0.2F)
                    .burnable()
    );

    public static final Block BLUE_MUSHROOM_BLOCK = registerBlock(
            MushroomBlock::new,
            "blue_mushroom_block",
            AbstractBlock.Settings.create()
                    .mapColor(MapColor.BLUE)
                    .instrument(NoteBlockInstrument.BASS)
                    .sounds(BlockSoundGroup.COBWEB)
                    .strength(0.2F)
                    .burnable()
    );

    public static final Block YELLOW_MUSHROOM = registerBlock(
            settings -> new MushroomPlantBlock(TreeConfiguredFeatures.HUGE_BROWN_MUSHROOM, settings),
            "yellow_mushroom",
            AbstractBlock.Settings.create()
                    .mapColor(MapColor.BROWN)
                    .noCollision()
                    .ticksRandomly()
                    .breakInstantly()
                    .sounds(BlockSoundGroup.GRASS)
                    .luminance(state -> 1)
                    .postProcess(Blocks::always)
                    .pistonBehavior(PistonBehavior.DESTROY)
    );

    public static final Block BLUE_MUSHROOM = registerBlock(
            settings -> new MushroomPlantBlock(TreeConfiguredFeatures.HUGE_BROWN_MUSHROOM, settings),
            "blue_mushroom",
            AbstractBlock.Settings.create()
                    .mapColor(MapColor.BROWN)
                    .noCollision()
                    .ticksRandomly()
                    .breakInstantly()
                    .sounds(BlockSoundGroup.GRASS)
                    .luminance(state -> 1)
                    .postProcess(Blocks::always)
                    .pistonBehavior(PistonBehavior.DESTROY)
    );

    public static final Block YELLOW_COBWEB = registerBlock(
            YellowCobwebBlock::new,
            "yellow_cobweb",
            AbstractBlock.Settings.create()
                    .mapColor(MapColor.WHITE)
                    .sounds(BlockSoundGroup.COBWEB)
                    .solid()
                    .noCollision()
                    .requiresTool()
                    .strength(4.0F)
    );

    public static final Block BLUE_COBWEB = registerBlock(
            BlueCobwebBlock::new,
            "blue_cobweb",
            AbstractBlock.Settings.create()
                    .mapColor(MapColor.WHITE)
                    .sounds(BlockSoundGroup.COBWEB)
                    .solid()
                    .noCollision()
                    .requiresTool()
                    .strength(4.0F)
    );

    //public static final Block OBSIDIAN = overrideBlock(
    //        ObsidianBlock::new,
    //        "obsidian",
    //        AbstractBlock.Settings.create()
    //                .mapColor(MapColor.PURPLE)
    //                .sounds(BlockSoundGroup.STONE)
    //                .solid()
    //                .noCollision()
    //                .requiresTool()
    //                .strength(4.0F)
    //);

    public static final Block ELEMENTAL_BLOCK = registerBlock(
            Block::new,
            "elemental_block",
            AbstractBlock.Settings.create()
                    .mapColor(MapColor.PURPLE)
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .sounds(BlockSoundGroup.STONE)
                    .requiresTool()
                    .strength(3.0F)
    );

    public static <T extends Block> T overrideBlock(
            Function<AbstractBlock.Settings, T> blockFactory,
            String name,
            AbstractBlock.Settings blockSettings
    ) {
        RegistryKey<Block> key = RegistryKey.of(RegistryKeys.BLOCK, Identifier.ofVanilla(name));

        T block = blockFactory.apply(blockSettings.registryKey(key));

        return Registry.register(Registries.BLOCK, key, block);
    }

    private static <T extends Block> T registerBlock(
            Function<AbstractBlock.Settings, T> blockFactory,
            String name,
            AbstractBlock.Settings blockSettings
    ) {
        RegistryKey<Block> key = RegistryKey.of(
                RegistryKeys.BLOCK,
                Identifier.of(CFTP.MOD_ID, name)
        );

        T block = blockFactory.apply(blockSettings.registryKey(key));
        registerBlockItem(name, block);

        return Registry.register(Registries.BLOCK, key, block);
    }

    public static Item overrideBlockItem(String name, Block block) {
        RegistryKey<Item> key = RegistryKey.of(
                RegistryKeys.ITEM,
                Identifier.ofVanilla(name)
        );
        CFTP.LOGGER.info("call2!");
        BlockItem item = new BlockItem(block, new Item.Settings().registryKey(key));

        //BiFunction<Block, Item.Settings, Item> factory = BlockItem::new;
        //
        //var settings = new Item.Settings().registryKey(key).useBlockPrefixedTranslationKey();
        //BlockItem item = (BlockItem)factory.apply(block, settings);
        //
         ////
        //////(Function)((itemSettings) -> (Item)factory.apply(block, itemSettings)), settings.useBlockPrefixedTranslationKey()
         ////
        //////Item item = (Item)factory.apply(settings.registryKey(key));
        ////if (item instanceof BlockItem blockItem) {
        //    item.appendBlocks(Item.BLOCK_ITEMS, item);
        ////}

        return Registry.register(Registries.ITEM, key, item);
    }

    private static void registerBlockItem(String name, Block block) {
        RegistryKey<Item> key = RegistryKey.of(
                RegistryKeys.ITEM,
                Identifier.of(CFTP.MOD_ID, name)
        );

        BlockItem item = new BlockItem(block, new Item.Settings().registryKey(key));
        Registry.register(Registries.ITEM, key, item);

        //BiFunction<Block, Item.Settings, Item> factory = BlockItem::new;
        //var settings = new Item.Settings();
//
        //Item item = factory.apply(block, settings);
        //settings.useBlockPrefixedTranslationKey();
//
        ////(Function)((itemSettings) -> (Item)factory.apply(block, itemSettings)), settings.useBlockPrefixedTranslationKey()
//
        ////Item item = (Item)factory.apply(settings.registryKey(key));
        //if (item instanceof BlockItem blockItem) {
        //    blockItem.appendBlocks(Item.BLOCK_ITEMS, item);
        //}

        //Registry.register(Registries.ITEM, key, item);
    }

    public static void register() {

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(
                entries -> entries.add(YELLOW_MUSHROOM)
        );

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(
                entries -> entries.add(BLUE_MUSHROOM)
        );

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(
                entries -> entries.add(YELLOW_MUSHROOM_BLOCK)
        );

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(
                entries -> entries.add(BLUE_MUSHROOM_BLOCK)
        );

        //ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(
        //        entries -> entries.add(YELLOW_COBWEB)
        //);
        //
        //ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(
        //        entries -> entries.add(BLUE_COBWEB)
         //);
        //
        ////
        //
        //ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(
        //        entries -> entries.add(ELEMENTAL_BLOCK)
        //);

    }

}
