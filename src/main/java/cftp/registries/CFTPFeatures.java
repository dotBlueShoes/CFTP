package cftp.registries;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.PlacedFeature;

public class CFTPFeatures {

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

    public static final RegistryKey<PlacedFeature> ORE_SULPHUR_PLACED =
            RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of("cftp", "ore_sulphur"));

    public static final RegistryKey<PlacedFeature> ORE_DEEPSLATE_SULPHUR_PLACED =
            RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of("cftp", "ore_deepslate_sulphur"));

    public static final RegistryKey<PlacedFeature> ORE_NETHER_SULPHUR_PLACED =
            RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of("cftp", "ore_nether_sulphur"));

    public static final RegistryKey<PlacedFeature> CLOUD_SULPHUR_PLACED =
            RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of("cftp", "cloud_sulphur"));

    public static void register() {
        BiomeModifications.addFeature(
                BiomeSelectors.all(),
                GenerationStep.Feature.UNDERGROUND_ORES,
                CLOUD_SULPHUR_PLACED
        );

        BiomeModifications.addFeature(
                BiomeSelectors.foundInOverworld(),
                GenerationStep.Feature.UNDERGROUND_ORES,
                ORE_SULPHUR_PLACED
        );

        BiomeModifications.addFeature(
                BiomeSelectors.foundInOverworld(),
                GenerationStep.Feature.UNDERGROUND_ORES,
                ORE_DEEPSLATE_SULPHUR_PLACED
        );

        BiomeModifications.addFeature(
                BiomeSelectors.foundInTheNether(),
                GenerationStep.Feature.UNDERGROUND_ORES,
                ORE_NETHER_SULPHUR_PLACED
        );

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
