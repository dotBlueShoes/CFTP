package cftp;

import cftp.entity.CFTPEntities;
import cftp.registries.CFTPBlocks;
import cftp.registries.CFTPItemGroups;
import cftp.registries.CFTPItems;
import cftp.world.gen.CFTPEntitySpawns;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.block.Block;
import net.minecraft.registry.*;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.PlacedFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CFTP implements ModInitializer {

	public static final String MOD_ID = "cftp";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static final RegistryKey<PlacedFeature> PATCH_YELLOW_MUSHROOM_PLACED =
            RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of("cftp", "patch_yellow_mushroom_placed"));

    public static final RegistryKey<PlacedFeature> PATCH_BLUE_MUSHROOM_PLACED =
            RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of("cftp", "patch_blue_mushroom_placed"));

    public static final RegistryKey<PlacedFeature> DISK_MUD_PLACED =
            RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of("cftp", "disk_mud_placed"));

    public static final RegistryKey<PlacedFeature> DISK_COBWEB_PLACED =
            RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of("cftp", "disk_cobweb_placed"));

    public static final RegistryKey<PlacedFeature> DISK_YELLOW_COBWEB_PLACED =
            RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of("cftp", "disk_yellow_cobweb_placed"));

    public static final RegistryKey<PlacedFeature> DISK_BLUE_COBWEB_PLACED =
            RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of("cftp", "disk_blue_cobweb_placed"));

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Hello Fabric Server!");

		CFTPItems.register();
		CFTPBlocks.register();
		CFTPItemGroups.register();
		CFTPEntities.register();
		CFTPEntitySpawns.register();

        {
            //Feature<DefaultFeatureConfig> MY_FEATURE = new PillarFeature(DefaultFeatureConfig.CODEC);
            //ConfiguredFeature<?, ?> MY_CONFIGURED = new ConfiguredFeature<>(MY_FEATURE, DefaultFeatureConfig.INSTANCE);
            //
            //final String feature_id = "pillar_feature";
            //Registry.register(Registries.FEATURE, Identifier.of("cftp", feature_id), MY_FEATURE);

            //// happens in json
            ////Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, Identifier.of("cftp", feature_id), MY_CONFIGURED);
            ////PlacedFeature MY_PLACED = new PlacedFeature(
            ////        RegistryEntry.of(MY_CONFIGURED), List.of(SquarePlacementModifier.of())
            ////);

            BiomeModifications.addFeature(
                    BiomeSelectors.includeByKey(BiomeKeys.SWAMP, BiomeKeys.MANGROVE_SWAMP),
                    GenerationStep.Feature.VEGETAL_DECORATION,
                    PATCH_YELLOW_MUSHROOM_PLACED
            );

            BiomeModifications.addFeature(
                    BiomeSelectors.includeByKey(BiomeKeys.JUNGLE, BiomeKeys.BAMBOO_JUNGLE, BiomeKeys.SPARSE_JUNGLE),
                    GenerationStep.Feature.VEGETAL_DECORATION,
                    PATCH_BLUE_MUSHROOM_PLACED
            );

            BiomeModifications.addFeature(
                    BiomeSelectors.includeByKey(BiomeKeys.SWAMP),
                    GenerationStep.Feature.UNDERGROUND_ORES,
                    DISK_MUD_PLACED
            );

            BiomeModifications.addFeature(
                    BiomeSelectors.includeByKey(BiomeKeys.SWAMP, BiomeKeys.JUNGLE),
                    //BiomeSelectors.foundInOverworld(),
                    GenerationStep.Feature.UNDERGROUND_ORES,
                    DISK_COBWEB_PLACED
            );

            BiomeModifications.addFeature(
                    BiomeSelectors.includeByKey(BiomeKeys.SWAMP),
                    GenerationStep.Feature.UNDERGROUND_ORES,
                    DISK_YELLOW_COBWEB_PLACED
            );

            BiomeModifications.addFeature(
                    BiomeSelectors.includeByKey(BiomeKeys.JUNGLE),
                    GenerationStep.Feature.UNDERGROUND_ORES,
                    DISK_BLUE_COBWEB_PLACED
            );

        }
	}
}