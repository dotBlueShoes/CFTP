package cftp.world.gen;

import cftp.entity.CFTPEntities;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnLocationTypes;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.registry.RegistryKey;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;

public class CFTPEntitySpawns {

    @SuppressWarnings("unchecked")
    public static void register() {

        final RegistryKey<?>[] ALL_BIOMES = {
                BiomeKeys.PLAINS,                   BiomeKeys.SUNFLOWER_PLAINS,
                BiomeKeys.SNOWY_PLAINS,             BiomeKeys.ICE_SPIKES,
                BiomeKeys.DESERT,                   BiomeKeys.SWAMP,
                BiomeKeys.MANGROVE_SWAMP,           BiomeKeys.FOREST,
                BiomeKeys.FLOWER_FOREST,            BiomeKeys.BIRCH_FOREST,
                BiomeKeys.DARK_FOREST,              BiomeKeys.PALE_GARDEN,
                BiomeKeys.OLD_GROWTH_BIRCH_FOREST,  BiomeKeys.TAIGA,
                BiomeKeys.SNOWY_TAIGA,              BiomeKeys.SAVANNA,
                BiomeKeys.SAVANNA_PLATEAU,          BiomeKeys.WINDSWEPT_HILLS,
                BiomeKeys.WINDSWEPT_GRAVELLY_HILLS, BiomeKeys.WINDSWEPT_FOREST,
                BiomeKeys.WINDSWEPT_SAVANNA,        BiomeKeys.JUNGLE,
                BiomeKeys.SPARSE_JUNGLE,            BiomeKeys.BAMBOO_JUNGLE,
                BiomeKeys.BADLANDS,                 BiomeKeys.ERODED_BADLANDS,
                BiomeKeys.WOODED_BADLANDS,          BiomeKeys.MEADOW,
                BiomeKeys.CHERRY_GROVE,             BiomeKeys.GROVE,
                BiomeKeys.SNOWY_SLOPES,             BiomeKeys.FROZEN_PEAKS,
                BiomeKeys.JAGGED_PEAKS,             BiomeKeys.STONY_PEAKS,
                BiomeKeys.RIVER,                    BiomeKeys.FROZEN_RIVER,
                BiomeKeys.BEACH,                    BiomeKeys.SNOWY_BEACH,
                BiomeKeys.WARM_OCEAN,               BiomeKeys.LUKEWARM_OCEAN,
                BiomeKeys.DEEP_LUKEWARM_OCEAN,      BiomeKeys.OCEAN,
                BiomeKeys.DEEP_OCEAN,               BiomeKeys.COLD_OCEAN,
                BiomeKeys.DEEP_COLD_OCEAN,          BiomeKeys.FROZEN_OCEAN,
                BiomeKeys.DEEP_FROZEN_OCEAN,        BiomeKeys.MUSHROOM_FIELDS,
                BiomeKeys.DRIPSTONE_CAVES,          BiomeKeys.LUSH_CAVES,
                BiomeKeys.DEEP_DARK,                BiomeKeys.NETHER_WASTES,
                BiomeKeys.WARPED_FOREST,            BiomeKeys.CRIMSON_FOREST,
                BiomeKeys.SOUL_SAND_VALLEY,         BiomeKeys.BASALT_DELTAS,
                BiomeKeys.THE_END,                  BiomeKeys.END_HIGHLANDS,
                BiomeKeys.END_MIDLANDS,             BiomeKeys.SMALL_END_ISLANDS,
                BiomeKeys.END_BARRENS
        };

        /// RegistryKey<?>[] SELECTED_BIOMES = {
        ///         BiomeKeys.PLAINS, BiomeKeys.SUNFLOWER_PLAINS, BiomeKeys.DESERT, BiomeKeys.SWAMP,
        ///         BiomeKeys.SWAMP, BiomeKeys.MANGROVE_SWAMP, BiomeKeys.FOREST, BiomeKeys.MEADOW
        /// };


        BiomeModifications.addSpawn(
            BiomeSelectors.includeByKey( (RegistryKey<Biome>[]) ALL_BIOMES),
            SpawnGroup.MONSTER, CFTPEntities.CREEPER_COOKIE,
            30, 1, 1
        );

        BiomeModifications.addSpawn(
            BiomeSelectors.includeByKey( (RegistryKey<Biome>[]) ALL_BIOMES),
            SpawnGroup.MONSTER, CFTPEntities.CREEPER_FIRE,
            30, 1, 1
        );

        BiomeModifications.addSpawn(
            BiomeSelectors.includeByKey( (RegistryKey<Biome>[]) ALL_BIOMES),
            SpawnGroup.MONSTER, CFTPEntities.CREEPER_LIGHTING,
            30, 1, 1
        );

        BiomeModifications.addSpawn(
            BiomeSelectors.includeByKey( (RegistryKey<Biome>[]) ALL_BIOMES),
            SpawnGroup.MONSTER, CFTPEntities.CREEPER_WATER,
            30, 1, 1
        );

        BiomeModifications.addSpawn(
            BiomeSelectors.includeByKey( (RegistryKey<Biome>[]) ALL_BIOMES),
            SpawnGroup.MONSTER, CFTPEntities.CREEPER_DIRT,
            30, 1, 1
        );

        BiomeModifications.addSpawn(
            BiomeSelectors.includeByKey( (RegistryKey<Biome>[]) ALL_BIOMES),
            SpawnGroup.MONSTER, CFTPEntities.CREEPER_LAVA,
            30, 1, 1
        );

        // ---

        SpawnRestriction.register(
            CFTPEntities.CREEPER_FIRE,
            SpawnLocationTypes.ON_GROUND,
            Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
            HostileEntity::canSpawnInDark
        );

        SpawnRestriction.register(
            CFTPEntities.CREEPER_LAVA,
            SpawnLocationTypes.ON_GROUND,
            Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
            HostileEntity::canSpawnInDark
        );

    }

}
