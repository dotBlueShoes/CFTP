package cftp.registries;

import cftp.CFTP;
import cftp.blocks.SparkBlock;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.minecraft.block.*;
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
import net.minecraft.world.gen.feature.TreeConfiguredFeatures;

public class CFTPBlocks {

    //public static final Block SAW_DUST_BLOCK = registerDefaultBlock(
    //        "saw_dust_block",
    //        AbstractBlock.Settings.create()
    //                .mapColor(MapColor.DIRT_BROWN)
    //                .strength(0.5F)
    //                .sounds(BlockSoundGroup.GRAVEL)
    //                .burnable()
    //);

    //  world.addParticle(ParticleTypes.HEART, playerEntity.getX(), playerEntity.getY() + 2.0, playerEntity.getZ(), 0.0, 0.0, 0.0);

    public static final Block SAW_DUST_BLOCK = registerFallingBlock(
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

    public static final Block YELLOW_MUSHROOM = registerMushroomBlock(
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

    public static final Block BLUE_MUSHROOM = registerMushroomBlock(
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



    //Block GLASS = register(
    //        "glass",
    //        TransparentBlock::new,
    //        AbstractBlock.Settings.create()
    //                .instrument(NoteBlockInstrument.HAT)
    //                .strength(0.3F)
    //                .sounds(BlockSoundGroup.GLASS)
    //                .nonOpaque()
    //                .allowsSpawning(Blocks::never)
    //                .solidBlock(Blocks::never)
    //                .suffocates(Blocks::never)
    //                .blockVision(Blocks::never)

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

        Block block = new SparkBlock(new ColorCode(-8356741), blockSettings.registryKey(key));
        registerBlockItem(name, block);

        return Registry.register(Registries.BLOCK, key, block);
    }

    private static Block registerMushroomBlock(String name, AbstractBlock.Settings blockSettings) {
        RegistryKey<Block> key = RegistryKey.of(
                RegistryKeys.BLOCK,
                Identifier.of(CFTP.MOD_ID, name)
        );

        Block block = new MushroomPlantBlock(TreeConfiguredFeatures.HUGE_BROWN_MUSHROOM, blockSettings.registryKey(key));
        registerBlockItem(name, block);

        return Registry.register(Registries.BLOCK, key, block);
    }

    //private static Block registerCauldron(String name, AbstractBlock.Settings blockSettings) {
    //    RegistryKey<Block> key = RegistryKey.of(
    //            RegistryKeys.BLOCK,
    //            Identifier.of(CFTP.MOD_ID, name)
    //    );
    //
    //    LeveledCauldronBlock block = new LeveledCauldronBlock(blockSettings.registryKey(key));
    //    registerBlockItem(name, block);
    //
    //    return Registry.register(Registries.BLOCK, key, block);
    //}

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

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(
                entries -> entries.add(YELLOW_MUSHROOM)
        );

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(
                entries -> entries.add(BLUE_MUSHROOM)
        );

        // Hay block values.
        FlammableBlockRegistry.getDefaultInstance().add(SAW_DUST_BLOCK, 60, 20);

    }

}
