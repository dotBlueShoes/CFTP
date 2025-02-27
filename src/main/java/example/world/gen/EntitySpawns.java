package example.world.gen;

import example.entity.Entities;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnLocationTypes;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.BiomeKeys;

public class EntitySpawns {

    public static void addSpawns() {

        // BIOMES
        // PLAINS, SUNFLOWER_PLAINS, SNOWY_PLAINS, ICE_SPIKES, DESERT, SWAMP, MANGROVE_SWAMP, FOREST,
        // FLOWER_FOREST, BIRCH_FOREST, DARK_FOREST, PALE_GARDEN, OLD_GROWTH_BIRCH_FOREST,
        // TAIGA, SNOWY_TAIGA, SAVANNA, SAVANNA_PLATEAU, WINDSWEPT_HILLS, WINDSWEPT_GRAVELLY_HILLS,
        // WINDSWEPT_FOREST, WINDSWEPT_SAVANNA, JUNGLE, SPARSE_JUNGLE, BAMBOO_JUNGLE, BADLANDS,
        // ERODED_BADLANDS, WOODED_BADLANDS, MEADOW, CHERRY_GROVE, GROVE, SNOWY_SLOPES, FROZEN_PEAKS,
        // JAGGED_PEAKS, STONY_PEAKS, RIVER, FROZEN_RIVER, BEACH, SNOWY_BEACH, WARM_OCEAN, LUKEWARM_OCEAN,
        // DEEP_LUKEWARM_OCEAN, OCEAN, DEEP_OCEAN, COLD_OCEAN, DEEP_COLD_OCEAN, FROZEN_OCEAN, DEEP_FROZEN_OCEAN,
        // MUSHROOM_FIELDS, DRIPSTONE_CAVES, LUSH_CAVES, DEEP_DARK, NETHER_WASTES, WARPED_FOREST, CRIMSON_FOREST,
        // SOUL_SAND_VALLEY, BASALT_DELTAS, THE_END, END_HIGHLANDS, END_MIDLANDS, SMALL_END_ISLANDS, END_BARRENS

        BiomeModifications.addSpawn(
            BiomeSelectors.includeByKey(
                BiomeKeys.PLAINS, BiomeKeys.SUNFLOWER_PLAINS, BiomeKeys.DESERT, BiomeKeys.SWAMP,
                BiomeKeys.SWAMP, BiomeKeys.MANGROVE_SWAMP, BiomeKeys.FOREST, BiomeKeys.MEADOW
            ), SpawnGroup.MONSTER, Entities.CREEPER_COOKIE,
            30, 1, 1
        );

        BiomeModifications.addSpawn(
                BiomeSelectors.includeByKey(
                    BiomeKeys.PLAINS, BiomeKeys.DESERT, BiomeKeys.SWAMP,
                    BiomeKeys.MANGROVE_SWAMP, BiomeKeys.MEADOW,
                    BiomeKeys.BEACH
                ), SpawnGroup.MONSTER, Entities.CREEPER_FIRE,
                30, 1, 1
        );

        BiomeModifications.addSpawn(
                BiomeSelectors.includeByKey(
                    BiomeKeys.PLAINS, BiomeKeys.SUNFLOWER_PLAINS, BiomeKeys.DESERT, BiomeKeys.SWAMP,
                    BiomeKeys.SWAMP, BiomeKeys.MANGROVE_SWAMP, BiomeKeys.FOREST, BiomeKeys.MEADOW
                ), SpawnGroup.MONSTER, Entities.CREEPER_LIGHTING,
                30, 1, 1
        );

        BiomeModifications.addSpawn(
                BiomeSelectors.includeByKey(
                    BiomeKeys.PLAINS, BiomeKeys.SUNFLOWER_PLAINS, BiomeKeys.DESERT, BiomeKeys.SWAMP,
                    BiomeKeys.SWAMP, BiomeKeys.MANGROVE_SWAMP, BiomeKeys.FOREST, BiomeKeys.MEADOW
                ), SpawnGroup.MONSTER, Entities.CREEPER_WATER,
                30, 1, 1
        );

        SpawnRestriction.register(
                Entities.CREEPER_FIRE,
                SpawnLocationTypes.ON_GROUND,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                HostileEntity::canSpawnInDark
        );

    }

}
