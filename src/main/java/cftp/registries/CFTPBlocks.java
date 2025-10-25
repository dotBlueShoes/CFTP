package cftp.registries;

import cftp.CFTP;
import cftp.blocks.*;
import cftp.blocks.FieryBlock;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
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
import net.minecraft.util.ColorCode;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.gen.feature.TreeConfiguredFeatures;

import java.util.function.Function;

public class CFTPBlocks {


    // + Elemental Lamp


    // 1. add SAW_DUST_WIRE (once burnt it is destroyed, it does not have an on/off state instead a particle emitter travels through)
    //Blocks
    //public static final Block REDSTONE_WIRE = register(
    //        "redstone_wire", RedstoneWireBlock::new, AbstractBlock.Settings.create().noCollision().breakInstantly().pistonBehavior(PistonBehavior.DESTROY)
    //);
    // 2. Find Flame particle. It will travel through as signal.
    // 3. A Lock has to be defined. MAX_SAWDUST_SIGNALS -> 20. Which would mean that at the same time there can be at max
    //  20 signals burning inside a world or server and not more. a 4 connection makes 1 signal split into 4...

    public static final Block SAW_DUST_BLOCK = registerBlock(
            settings -> new SparkBlock(new ColorCode(-8356741), settings),
            "saw_dust_block",
            AbstractBlock.Settings.create()
                    .mapColor(MapColor.DIRT_BROWN)
                    .strength(0.5F)
                    .sounds(BlockSoundGroup.GRAVEL)
                    .burnable()
                    //.nonOpaque()
                    //.noCollision()
                    //.allowsSpawning(Blocks::never)
                    //.solidBlock(Blocks::never)
                    //.suffocates(Blocks::always)
                    //.blockVision(Blocks::always)
    );

    public static final Block YELLOW_MUSHROOM_BLOCK = registerBlock(
            MushroomBlock::new,
            "yellow_mushroom_block",
            AbstractBlock.Settings.create()
                    .mapColor(MapColor.YELLOW)
                    .instrument(NoteBlockInstrument.BASS)
                    .sounds(BlockSoundGroup.COBWEB)
                    .strength(0.2F)
                    .sounds(BlockSoundGroup.WOOD)
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
                    .sounds(BlockSoundGroup.WOOD)
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

    public static final Block SULPHUR_ORE = registerBlock(
            settings -> new SulphurBlock(UniformIntProvider.create(0, 3), settings),
            "sulphur_ore",
            AbstractBlock.Settings.create()
                    .mapColor(MapColor.STONE_GRAY)
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .sounds(BlockSoundGroup.STONE)
                    .requiresTool()
                    .strength(3.0F)
    );

    public static final Block DEEPSLATE_SULPHUR_ORE = registerBlock(
            settings -> new SulphurBlock(ConstantIntProvider.create(0), settings),
            "deepslate_sulphur_ore",
            AbstractBlock.Settings.create()
                    .mapColor(MapColor.DEEPSLATE_GRAY)
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .sounds(BlockSoundGroup.DEEPSLATE)
                    .requiresTool()
                    .strength(3.0F)
    );

    public static final Block NETHER_SULPHUR_ORE = registerBlock(
            settings -> new SulphurBlock(UniformIntProvider.create(0, 2), settings),
            "nether_sulphur_ore",
            AbstractBlock.Settings.create()
                    .mapColor(MapColor.DARK_RED)
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .sounds(BlockSoundGroup.NETHER_ORE)
                    .requiresTool()
                    .strength(3.0F)
    );

    public static final Block FIERY_BLOCK = registerBlock(
            FieryBlock::new,
            "fiery_block",
            AbstractBlock.Settings.create()
                    .mapColor(MapColor.RED)
                    .sounds(BlockSoundGroup.SUSPICIOUS_SAND)
                    .strength(0.5F)
                    .sounds(BlockSoundGroup.GRAVEL)
                    .burnable()
    );

    // 1. NON-AIR VERSION
    public static final Block SULPHUR_CLOUD = registerBlock(
            SulphurCloud::new,
            "sulphur_cloud",
            AbstractBlock.Settings.create()
                    .mapColor(MapColor.CLEAR)
                    .sounds(BlockSoundGroup.SUSPICIOUS_SAND)
                    .requiresTool()
                    .nonOpaque()
                    .noCollision()
                    .strength(-1.0f, 1.0f)
                    .dropsNothing()
                    .allowsSpawning(Blocks::never)  // not sure
                    .burnable()
                    //.solidBlock(Blocks::never)      // not sure
                    .replaceable()                  // maybe it shouldn't be
    );

    // 2. AIR VERSION (it seems the .air blocks the possibility of being destroyed via tnt)
    //public static final Block SULPHUR_CLOUD = registerBlock(
    //        AirSulphurCloud::new,
    //        //SulphurCloud::new,
    //        "sulphur_cloud",
    //        AbstractBlock.Settings.create()
    //                //.replaceable()
    //                .noCollision()
    //                //.nonOpaque()
    //                .dropsNothing()
    //                .air()
    //);

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

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(
                entries -> entries.add(YELLOW_COBWEB)
        );

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(
                entries -> entries.add(BLUE_COBWEB)
        );

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(
                entries -> entries.add(ELEMENTAL_BLOCK)
        );

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(
                entries -> entries.add(SULPHUR_ORE)
        );

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(
                entries -> entries.add(DEEPSLATE_SULPHUR_ORE)
        );

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(
                entries -> entries.add(NETHER_SULPHUR_ORE)
        );

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(
                entries -> entries.add(FIERY_BLOCK)
        );

        //ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(
        //        entries -> entries.add(SULPHUR_CLOUD)
        //);

        // Hay block values.
        FlammableBlockRegistry.getDefaultInstance().add(SAW_DUST_BLOCK, 60, 20);
        // TNT block values.
        FlammableBlockRegistry.getDefaultInstance().add(SULPHUR_CLOUD, 15, 100);

        // Weird. Webs and mushrooms do not burn in this game.
        //FlammableBlockRegistry.getDefaultInstance().add(YELLOW_COBWEB,          5, 5);
        //FlammableBlockRegistry.getDefaultInstance().add(BLUE_COBWEB,            5, 5);
        //FlammableBlockRegistry.getDefaultInstance().add(YELLOW_MUSHROOM_BLOCK,  5, 5);
        //FlammableBlockRegistry.getDefaultInstance().add(BLUE_MUSHROOM_BLOCK,    5, 5);

    }

}
