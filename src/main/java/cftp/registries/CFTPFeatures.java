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

    public static final RegistryKey<PlacedFeature> DISK_YELLOW_COBWEB_PLACED =
            RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of("cftp", "disk_yellow_cobweb_placed"));

    public static final RegistryKey<PlacedFeature> DISK_BLUE_COBWEB_PLACED =
            RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of("cftp", "disk_blue_cobweb_placed"));

    public static void register() {
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
                DISK_YELLOW_COBWEB_PLACED
        );

        BiomeModifications.addFeature(
                BiomeSelectors.includeByKey(BiomeKeys.JUNGLE),
                GenerationStep.Feature.UNDERGROUND_ORES,
                DISK_BLUE_COBWEB_PLACED
        );
    }

}
