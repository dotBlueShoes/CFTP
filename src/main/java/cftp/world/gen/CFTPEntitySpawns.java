package cftp.world.gen;

import cftp.registries.CFTPEntities;
import cftp.entity.HorseFieryEntity;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.entity.*;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Difficulty;
import net.minecraft.world.Heightmap;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;

public class CFTPEntitySpawns {

    // The Void	                the_void	0

    // Plains	                plains	1
    // Sunflower Plains	        sunflower_plains	2
    // Snowy Plains	            snowy_plains	3
    // Ice Spikes	            ice_spikes	4

    // Forest	                forest	8
    // Flower Forest	        flower_forest	9
    // Birch Forest	            birch_forest	10
    // Dark Forest	            dark_forest	11             // ?
    // Old Growth Birch Forest	old_growth_birch_forest	12 // ?
    // Old Growth Pine Taiga	old_growth_pine_taiga	13 // ?
    // Old Growth Spruce Taiga	old_growth_spruce_taiga	14 // ?
    // Taiga	                taiga	15
    // Snowy Taiga	            snowy_taiga	16

    // Desert	                desert	5
    // Savanna	                savanna	17
    // Savanna Plateau	        savanna_plateau	18
    // Windswept Savanna	    windswept_savanna	22
    // Badlands	                badlands	26
    // Eroded Badlands	        eroded_badlands	27
    // Wooded Badlands	        wooded_badlands	28

    // Windswept Hills	        windswept_hills	19
    // Windswept Gravelly Hills	windswept_gravelly_hills	20
    // Windswept Forest	        windswept_forest	21
    // Windswept Savanna	    windswept_savanna	22

    // Jungle	                jungle	23
    // Sparse Jungle	        sparse_jungle	24
    // Bamboo Jungle	        bamboo_jungle	25

    // Meadow	                meadow	29
    // Cherry Grove	            cherry_grove	30
    // Grove	                grove	31
    // Snowy Slopes	            snowy_slopes	32
    // Frozen Peaks	            frozen_peaks	33
    // Jagged Peaks	            jagged_peaks	34
    // Stony Peaks	            stony_peaks	35

    // River	                river	36
    // Frozen River	            frozen_river	37

    // Beach	                beach	38
    // Snowy Beach	            snowy_beach	39
    // Stony Shore	            stony_shore	40

    // Dripstone Caves	        dripstone_caves	51
    // Lush Caves	            lush_caves	52

    // 28
    // Lighting Creeper     // windswept_hills,	windswept_gravelly_hills, windswept_forest, windswept_savanna
    // Cookie Creeper       // jungle, sparse_jungle, bamboo_jungle
    // Ender Creeper        // jungle, sparse_jungle, bamboo_jungle, the_end, end_highlands, end_midlands, small_end_islands, end_barrens, warped_forest, crimson_forest
    // Water Creeper        // plains, sunflower_plains, snowy_plains
    // Earth Creeper        // plains, sunflower_plains, snowy_plains
    // Fire Creeper         // plains, sunflower_plains, snowy_plains, desert, savanna, savanna_plateau, windswept_savanna, badlands, eroded_badlands, wooded_badlands
    // Dirt Creeper         // dripstone_caves
    // Lava Creeper         // nether_wastes, basalt_deltas
    // Wind Creeper         // plains, sunflower_plains, snowy_plains, desert, savanna, savanna_plateau, windswept_savanna, badlands, eroded_badlands, wooded_badlands
    // Ghost Creeper        // swamp, soul_sand_valley
    // Nether Creeper       // nether_wastes, basalt_deltas, warped_forest, crimson_forest
    // Flip Creeper         // warped_forest, crimson_forest
    // Friendly Creeper     // mushroom_fields
    // Snow Creeper         // snowy_plains, snowy_taiga, ice_spikes, frozen_ocean, deep_frozen_ocean
    // Swamp Creeper        // swamp, mangrove_swamp, snowy_slopes, frozen_peaks, jagged_peaks, frozen_river
    // Dark Creeper         // desert, savanna, savanna_plateau, windswept_savanna, badlands, eroded_badlands, wooded_badlands
    // Ballistic Creeper    // jagged_peaks, stony_peaks
    // Amethyst Creeper     // lush_caves
    // Sculk Creeper        // deep_dark

    // Golden Creeper       // -
    // Piggy Creeper        // -
    // Amalgam Creeper      // -

    // Bridger Creeper      //
    // Brewer Creeper       // swamp, jungle, sparse_jungle, bamboo_jungle
    // Giant Creeper        //
    // Harvest Creeper      //
    // Oceanid Creeper      // warm_ocean, lukewarm_ocean, deep_lukewarm_ocean, ocean, deep_ocean, cold_ocean, deep_cold_ocean, frozen_ocean, deep_frozen_ocean
    // Sand Creeper         // desert, savanna, savanna_plateau, windswept_savanna, badlands, eroded_badlands, wooded_badlands


    //public static boolean canSpawnIgnoreLightLevel(
    //        EntityType<? extends LivingEntity> type,
    //        WorldAccess world,
    //        SpawnReason spawnReason,
    //        BlockPos pos, Random random
    //) {
    //    if (world.getDifficulty() != Difficulty.PEACEFUL) {
    //        BlockPos blockPos = pos.down();
    //        return SpawnReason.isAnySpawner(spawnReason) || world.getBlockState(blockPos).allowsSpawning(world, blockPos, type);
    //    } else {
    //        return false;
    //    }
    //}

    @SuppressWarnings("unchecked")
    public static void register() {

        final RegistryKey<?>[] BIOMES_LIGHTING = {BiomeKeys.WINDSWEPT_HILLS, BiomeKeys.WINDSWEPT_GRAVELLY_HILLS, BiomeKeys.WINDSWEPT_FOREST, BiomeKeys.WINDSWEPT_SAVANNA};
        // Lighting Creeper     // windswept_hills,	windswept_gravelly_hills, windswept_forest, windswept_savanna
        final RegistryKey<?>[] BIOMES_COOKIE = {BiomeKeys.JUNGLE, BiomeKeys.SPARSE_JUNGLE, BiomeKeys.BAMBOO_JUNGLE};
        // Cookie Creeper       // jungle, sparse_jungle, bamboo_jungle
        final RegistryKey<?>[] BIOMES_ENDER = {BiomeKeys.JUNGLE, BiomeKeys.SPARSE_JUNGLE, BiomeKeys.BAMBOO_JUNGLE, BiomeKeys.THE_END, BiomeKeys.END_HIGHLANDS, BiomeKeys.END_MIDLANDS, BiomeKeys.SMALL_END_ISLANDS, BiomeKeys.END_BARRENS, BiomeKeys.WARPED_FOREST, BiomeKeys.CRIMSON_FOREST};
        // Ender Creeper        // jungle, sparse_jungle, bamboo_jungle, the_end, end_highlands, end_midlands, small_end_islands, end_barrens, warped_forest, crimson_forest
        final RegistryKey<?>[] BIOMES_WATER = {BiomeKeys.PLAINS, BiomeKeys.SUNFLOWER_PLAINS, BiomeKeys.SNOWY_PLAINS};
        // Water Creeper        // plains, sunflower_plains, snowy_plains
        final RegistryKey<?>[] BIOMES_EARTH = {BiomeKeys.PLAINS, BiomeKeys.SUNFLOWER_PLAINS, BiomeKeys.SNOWY_PLAINS};
        // Earth Creeper        // plains, sunflower_plains, snowy_plains
        final RegistryKey<?>[] BIOMES_FIRE = {BiomeKeys.PLAINS, BiomeKeys.SUNFLOWER_PLAINS, BiomeKeys.DESERT, BiomeKeys.SAVANNA, BiomeKeys.SAVANNA_PLATEAU, BiomeKeys.WINDSWEPT_SAVANNA, BiomeKeys.BADLANDS, BiomeKeys.ERODED_BADLANDS, BiomeKeys.WOODED_BADLANDS};
        // Fire Creeper         // plains, sunflower_plains, desert, savanna, savanna_plateau, windswept_savanna, badlands, eroded_badlands, wooded_badlands
        final RegistryKey<?>[] BIOMES_DIRT = {BiomeKeys.DRIPSTONE_CAVES};
        // Dirt Creeper         // dripstone_caves
        final RegistryKey<?>[] BIOMES_LAVA = {BiomeKeys.NETHER_WASTES, BiomeKeys.BASALT_DELTAS};
        // Lava Creeper         // nether_wastes, basalt_deltas
        final RegistryKey<?>[] BIOMES_WIND = {BiomeKeys.PLAINS, BiomeKeys.SUNFLOWER_PLAINS, BiomeKeys.SNOWY_PLAINS, BiomeKeys.DESERT, BiomeKeys.SAVANNA, BiomeKeys.SAVANNA_PLATEAU, BiomeKeys.WINDSWEPT_SAVANNA, BiomeKeys.BADLANDS, BiomeKeys.ERODED_BADLANDS, BiomeKeys.WOODED_BADLANDS};
        // Wind Creeper         // plains, sunflower_plains, snowy_plains, desert, savanna, savanna_plateau, windswept_savanna, badlands, eroded_badlands, wooded_badlands
        final RegistryKey<?>[] BIOMES_GHOST = {BiomeKeys.SWAMP, BiomeKeys.SOUL_SAND_VALLEY};
        // Ghost Creeper        // swamp, soul_sand_valley
        final RegistryKey<?>[] BIOMES_NETHER = {BiomeKeys.NETHER_WASTES, BiomeKeys.BASALT_DELTAS, BiomeKeys.WARPED_FOREST, BiomeKeys.CRIMSON_FOREST};
        // Nether Creeper       // nether_wastes, basalt_deltas, warped_forest, crimson_forest
        final RegistryKey<?>[] BIOMES_FLIP = {BiomeKeys.WARPED_FOREST, BiomeKeys.CRIMSON_FOREST};
        // Flip Creeper         // warped_forest, crimson_forest
        final RegistryKey<?>[] BIOMES_FRIENDLY = {BiomeKeys.MUSHROOM_FIELDS};
        // Friendly Creeper     // mushroom_fields
        final RegistryKey<?>[] BIOMES_SNOW = {BiomeKeys.SNOWY_PLAINS, BiomeKeys.SNOWY_TAIGA, BiomeKeys.ICE_SPIKES, BiomeKeys.FROZEN_OCEAN, BiomeKeys.DEEP_FROZEN_OCEAN, BiomeKeys.SNOWY_SLOPES, BiomeKeys.FROZEN_PEAKS, BiomeKeys.FROZEN_RIVER};
        // Snow Creeper         // snowy_plains, snowy_taiga, ice_spikes, frozen_ocean, deep_frozen_ocean, snowy_slopes, frozen_peaks, jagged_peaks, frozen_river
        final RegistryKey<?>[] BIOMES_SWAMP = {BiomeKeys.SWAMP, BiomeKeys.MANGROVE_SWAMP};
        // Swamp Creeper        // swamp, mangrove_swamp
        final RegistryKey<?>[] BIOMES_DARK = {BiomeKeys.DESERT, BiomeKeys.SAVANNA, BiomeKeys.SAVANNA_PLATEAU, BiomeKeys.WINDSWEPT_SAVANNA, BiomeKeys.BADLANDS, BiomeKeys.ERODED_BADLANDS, BiomeKeys.WOODED_BADLANDS};
        // Dark Creeper         // desert, savanna, savanna_plateau, windswept_savanna, badlands, eroded_badlands, wooded_badlands
        final RegistryKey<?>[] BIOMES_BALLISTIC = {BiomeKeys.JAGGED_PEAKS, BiomeKeys.STONY_PEAKS, BiomeKeys.DARK_FOREST};
        // Ballistic Creeper    // jagged_peaks, stony_peaks
        final RegistryKey<?>[] BIOMES_AMETHYST = {BiomeKeys.LUSH_CAVES};
        // Amethyst Creeper     // lush_caves
        final RegistryKey<?>[] BIOMES_SCULK = {BiomeKeys.DEEP_DARK};
        // Sculk Creeper        // deep_dark
        final RegistryKey<?>[] BIOMES_AMALGAM = {BiomeKeys.MEADOW};

        // OVERWORLD_DRY
        // BiomeKeys.DESERT, BiomeKeys.SAVANNA, BiomeKeys.SAVANNA_PLATEAU,

        // OVERWORLD_JUNGLE
        // BiomeKeys.JUNGLE, BiomeKeys.SPARSE_JUNGLE, BiomeKeys.BAMBOO_JUNGLE,

        // OVERWORLD_WATER
        // BiomeKeys.WARM_OCEAN, BiomeKeys.LUKEWARM_OCEAN,
        // BiomeKeys.DEEP_LUKEWARM_OCEAN, BiomeKeys.OCEAN,
        // BiomeKeys.DEEP_OCEAN, BiomeKeys.COLD_OCEAN,
        // BiomeKeys.DEEP_COLD_OCEAN,

        //final RegistryKey<?>[] OVERWORLD = {
        //        BiomeKeys.PLAINS, BiomeKeys.SUNFLOWER_PLAINS,
        //        BiomeKeys.FOREST, BiomeKeys.FLOWER_FOREST,
        //        BiomeKeys.BIRCH_FOREST, BiomeKeys.DARK_FOREST,
        //        BiomeKeys.OLD_GROWTH_BIRCH_FOREST, BiomeKeys.TAIGA,
        //        BiomeKeys.WINDSWEPT_HILLS, BiomeKeys.WINDSWEPT_GRAVELLY_HILLS,
        //        BiomeKeys.WINDSWEPT_FOREST, BiomeKeys.WINDSWEPT_SAVANNA,
        //        BiomeKeys.BADLANDS, BiomeKeys.ERODED_BADLANDS,
        //        BiomeKeys.WOODED_BADLANDS, BiomeKeys.MEADOW,
        //        BiomeKeys.CHERRY_GROVE, BiomeKeys.GROVE,
        //        BiomeKeys.JAGGED_PEAKS, BiomeKeys.STONY_PEAKS,
        //        BiomeKeys.RIVER, BiomeKeys.BEACH,
          //        //
        //        BiomeKeys.DRIPSTONE_CAVES, BiomeKeys.LUSH_CAVES,
        //        BiomeKeys.DEEP_DARK, BiomeKeys.PALE_GARDEN,
          //        //
        //        BiomeKeys.SNOWY_PLAINS, BiomeKeys.ICE_SPIKES,
        //        BiomeKeys.SNOWY_TAIGA, BiomeKeys.SNOWY_SLOPES,
        //        BiomeKeys.FROZEN_PEAKS, BiomeKeys.FROZEN_RIVER,
        //        BiomeKeys.SNOWY_BEACH, BiomeKeys.FROZEN_OCEAN,
        //        BiomeKeys.DEEP_FROZEN_OCE AN,
        //        //
        //        BiomeKeys.SWAMP, BiomeKeys.MANGROVE_SWAMP,
        //};
//
        //final RegistryKey<?>[] OVERWORLD_TEMPERATE = {
        //        BiomeKeys.PLAINS, BiomeKeys.SUNFLOWER_PLAINS,
        //        BiomeKeys.FOREST, BiomeKeys.FLOWER_FOREST,
        //        BiomeKeys.BIRCH_FOREST, BiomeKeys.DARK_FOREST,
        //        BiomeKeys.OLD_GROWTH_BIRCH_FOREST, BiomeKeys.TAIGA,
        //        BiomeKeys.WINDSWEPT_HILLS, BiomeKeys.WINDSWEPT_GRAVELLY_HILLS,
        //        BiomeKeys.WINDSWEPT_FOREST, BiomeKeys.WINDSWEPT_SAVANNA,
        //        BiomeKeys.BADLANDS, BiomeKeys.ERODED_BADLANDS,
        //        BiomeKeys.WOODED_BADLANDS, BiomeKeys.MEADOW,
        //        BiomeKeys.CHERRY_GROVE, BiomeKeys.GROVE,
        //        BiomeKeys.JAGGED_PEAKS, BiomeKeys.STONY_PEAKS,
        //        BiomeKeys.RIVER, BiomeKeys.BEACH,
        //        //
        //        BiomeKeys.DRIPSTONE_CAVES, BiomeKeys.LUSH_CAVES,
        //        BiomeKeys.DEEP_DARK, BiomeKeys.PALE_GARDEN,
        //};
//
        //final RegistryKey<?>[] OVERWORLD_SNOW = {
        //        BiomeKeys.SNOWY_PLAINS, BiomeKeys.ICE_SPIKES,
        //        BiomeKeys.SNOWY_TAIGA, BiomeKeys.SNOWY_SLOPES,
        //        BiomeKeys.FROZEN_PEAKS, BiomeKeys.FROZEN_RIVER,
        //        BiomeKeys.SNOWY_BEACH, BiomeKeys.FROZEN_OCEAN,
        //        BiomeKeys.DEEP_FROZEN_OCEAN,
        //};
//
        //final RegistryKey<?>[] OVERWORLD_SWAMP = {
        //        BiomeKeys.SWAMP, BiomeKeys.MANGROVE_SWAMP,
        //};
//
        //final RegistryKey<?>[] OVERWORLD_JUNGLE = {
        //        BiomeKeys.JUNGLE, BiomeKeys.BAMBOO_JUNGLE,
        //};
//
        //final RegistryKey<?>[] NETHER = {
        //        BiomeKeys.NETHER_WASTES, BiomeKeys.WARPED_FOREST,
        //        BiomeKeys.CRIMSON_FOREST, BiomeKeys.SOUL_SAND_VALLEY,
        //        BiomeKeys.BASALT_DELTAS,
        //};
//
        //final RegistryKey<?>[] OVERWORLD_NETHER = {
        //        BiomeKeys.PLAINS, BiomeKeys.SUNFLOWER_PLAINS,
        //        BiomeKeys.SNOWY_PLAINS, BiomeKeys.ICE_SPIKES,
        //        BiomeKeys.DESERT, BiomeKeys.SWAMP,
        //        BiomeKeys.MANGROVE_SWAMP, BiomeKeys.FOREST,
        //        BiomeKeys.FLOWER_FOREST, BiomeKeys.BIRCH_FOREST,
        //        BiomeKeys.DARK_FOREST, BiomeKeys.PALE_GARDEN,
        //        BiomeKeys.OLD_GROWTH_BIRCH_FOREST, BiomeKeys.TAIGA,
        //        BiomeKeys.SNOWY_TAIGA, BiomeKeys.SAVANNA,
        //        BiomeKeys.SAVANNA_PLATEAU, BiomeKeys.WINDSWEPT_HILLS,
        //        BiomeKeys.WINDSWEPT_GRAVELLY_HILLS, BiomeKeys.WINDSWEPT_FOREST,
        //        BiomeKeys.WINDSWEPT_SAVANNA, BiomeKeys.JUNGLE,
        //        BiomeKeys.SPARSE_JUNGLE, BiomeKeys.BAMBOO_JUNGLE,
        //        BiomeKeys.BADLANDS, BiomeKeys.ERODED_BADLANDS,
        //        BiomeKeys.WOODED_BADLANDS, BiomeKeys.MEADOW,
        //        BiomeKeys.CHERRY_GROVE, BiomeKeys.GROVE,
        //        BiomeKeys.SNOWY_SLOPES, BiomeKeys.FROZEN_PEAKS,
        //        BiomeKeys.JAGGED_PEAKS, BiomeKeys.STONY_PEAKS,
        //        BiomeKeys.RIVER, BiomeKeys.FROZEN_RIVER,
        //        BiomeKeys.BEACH, BiomeKeys.SNOWY_BEACH,
        //        BiomeKeys.WARM_OCEAN, BiomeKeys.LUKEWARM_OCEAN,
        //        BiomeKeys.DEEP_LUKEWARM_OCEAN, BiomeKeys.OCEAN,
        //        BiomeKeys.DEEP_OCEAN, BiomeKeys.COLD_OCEAN,
        //        BiomeKeys.DEEP_COLD_OCEAN, BiomeKeys.FROZEN_OCEAN,
        //        BiomeKeys.DEEP_FROZEN_OCEAN, BiomeKeys.MUSHROOM_FIELDS,
        //        BiomeKeys.DRIPSTONE_CAVES, BiomeKeys.LUSH_CAVES,
        //        BiomeKeys.DEEP_DARK, BiomeKeys.NETHER_WASTES,
        //        BiomeKeys.WARPED_FOREST, BiomeKeys.CRIMSON_FOREST,
        //        BiomeKeys.SOUL_SAND_VALLEY, BiomeKeys.BASALT_DELTAS,
        //};
//
        //final RegistryKey<?>[] END = {
        //        BiomeKeys.THE_END, BiomeKeys.END_HIGHLANDS,
        //        BiomeKeys.END_MIDLANDS, BiomeKeys.SMALL_END_ISLANDS,
        //        BiomeKeys.END_BARRENS,
        //};
//
        //final RegistryKey<?>[] ALL_BIOMES = {
        //        BiomeKeys.PLAINS, BiomeKeys.SUNFLOWER_PLAINS,
        //        BiomeKeys.SNOWY_PLAINS, BiomeKeys.ICE_SPIKES,
        //        BiomeKeys.DESERT, BiomeKeys.SWAMP,
        //        BiomeKeys.MANGROVE_SWAMP, BiomeKeys.FOREST,
        //        BiomeKeys.FLOWER_FOREST, BiomeKeys.BIRCH_FOREST,
        //        BiomeKeys.DARK_FOREST, BiomeKeys.PALE_GARDEN,
        //        BiomeKeys.OLD_GROWTH_BIRCH_FOREST, BiomeKeys.TAIGA,
        //        BiomeKeys.SNOWY_TAIGA, BiomeKeys.SAVANNA,
        //        BiomeKeys.SAVANNA_PLATEAU, BiomeKeys.WINDSWEPT_HILLS,
        //        BiomeKeys.WINDSWEPT_GRAVELLY_HILLS, BiomeKeys.WINDSWEPT_FOREST,
        //        BiomeKeys.WINDSWEPT_SAVANNA, BiomeKeys.JUNGLE,
        //        BiomeKeys.SPARSE_JUNGLE, BiomeKeys.BAMBOO_JUNGLE,
        //        BiomeKeys.BADLANDS, BiomeKeys.ERODED_BADLANDS,
        //        BiomeKeys.WOODED_BADLANDS, BiomeKeys.MEADOW,
        //        BiomeKeys.CHERRY_GROVE, BiomeKeys.GROVE,
        //        BiomeKeys.SNOWY_SLOPES, BiomeKeys.FROZEN_PEAKS,
        //        BiomeKeys.JAGGED_PEAKS, BiomeKeys.STONY_PEAKS,
        //        BiomeKeys.RIVER, BiomeKeys.FROZEN_RIVER,
        //        BiomeKeys.BEACH, BiomeKeys.SNOWY_BEACH,
        //        BiomeKeys.WARM_OCEAN, BiomeKeys.LUKEWARM_OCEAN,
        //        BiomeKeys.DEEP_LUKEWARM_OCEAN, BiomeKeys.OCEAN,
        //        BiomeKeys.DEEP_OCEAN, BiomeKeys.COLD_OCEAN,
        //        BiomeKeys.DEEP_COLD_OCEAN, BiomeKeys.FROZEN_OCEAN,
        //        BiomeKeys.DEEP_FROZEN_OCEAN, BiomeKeys.MUSHROOM_FIELDS,
        //        BiomeKeys.DRIPSTONE_CAVES, BiomeKeys.LUSH_CAVES,
        //        BiomeKeys.DEEP_DARK, BiomeKeys.NETHER_WASTES,
        //        BiomeKeys.WARPED_FOREST, BiomeKeys.CRIMSON_FOREST,
        //        BiomeKeys.SOUL_SAND_VALLEY, BiomeKeys.BASALT_DELTAS,
        //        BiomeKeys.THE_END, BiomeKeys.END_HIGHLANDS,
        //        BiomeKeys.END_MIDLANDS, BiomeKeys.SMALL_END_ISLANDS,
        //        BiomeKeys.END_BARRENS
        //};

        /// RegistryKey<?>[] SELECTED_BIOMES = {
        ///         BiomeKeys.PLAINS, BiomeKeys.SUNFLOWER_PLAINS, BiomeKeys.DESERT, BiomeKeys.SWAMP,
        ///         BiomeKeys.SWAMP, BiomeKeys.MANGROVE_SWAMP, BiomeKeys.FOREST, BiomeKeys.MEADOW
        /// };


        final int CREEPER_GROUP_WEIGHT = 14;
        final int CREEPER_GROUP_MIN = 1;
        final int CREEPER_GROUP_MAX = 3;

        //final int ZOMBIE_GROUP_WEIGHT = 10;
        //final int ZOMBIE_GROUP_MIN = 5;
        //final int ZOMBIE_GROUP_MAX = 12;

        // Solving the issue
        // mod -> https://www.curseforge.com/minecraft/mc-mods/custom-spawns/files/all?page=1&pageSize=20
        // mod's git -> https://github.com/b3spectacled/custom-spawns/blob/1.19.x/src/main/java/mod/bespectacled/customspawn/mixin/MixinServerChunkManager.java
        // other know settings -> https://www.reddit.com/r/admincraft/comments/o1cvi1/too_many_mob_spawns_paper_1165/
        // how it works 1 -> https://www.reddit.com/r/technicalminecraft/comments/kgkbkx/can_anyone_explain_the_mob_cap_to_me/
        // how it works 2 -> https://minecraft.wiki/w/Mob_spawning

        // ISSUE
        // 4 is max for some reason
        // also it might be impossible to have ZOMBIE again.
        //BiomeModifications.addSpawn(
        //        BiomeSelectors.includeByKey( (RegistryKey<Biome>[]) ALL_BIOMES),
        //        SpawnGroup.MONSTER, EntityType.ZOMBIE,
        //        ZOMBIE_GROUP_WEIGHT, ZOMBIE_GROUP_MIN, ZOMBIE_GROUP_MAX
        //);

        { // COOKIE
            BiomeModifications.addSpawn(
                    BiomeSelectors.includeByKey((RegistryKey<Biome>[]) BIOMES_COOKIE),
                    SpawnGroup.MONSTER, CFTPEntities.CREEPER_COOKIE,
                    CREEPER_GROUP_WEIGHT, CREEPER_GROUP_MIN, CREEPER_GROUP_MAX
            );

            SpawnRestriction.register(
                    CFTPEntities.CREEPER_COOKIE,
                    SpawnLocationTypes.ON_GROUND,
                    Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                    HostileEntity::canSpawnIgnoreLightLevel
            );
        }

        { // ENDER
            BiomeModifications.addSpawn(
                    BiomeSelectors.includeByKey((RegistryKey<Biome>[]) BIOMES_ENDER),
                    SpawnGroup.MONSTER, CFTPEntities.CREEPER_ENDER,
                    CREEPER_GROUP_WEIGHT, CREEPER_GROUP_MIN, CREEPER_GROUP_MAX
            );

            SpawnRestriction.register(
                    CFTPEntities.CREEPER_ENDER,
                    SpawnLocationTypes.ON_GROUND,
                    Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                    HostileEntity::canSpawnInDark
            );
        }

        { // FIRE
            BiomeModifications.addSpawn(
                    BiomeSelectors.includeByKey((RegistryKey<Biome>[]) BIOMES_FIRE),
                    SpawnGroup.MONSTER, CFTPEntities.CREEPER_FIRE,
                    CREEPER_GROUP_WEIGHT, CREEPER_GROUP_MIN, CREEPER_GROUP_MAX
            );

            SpawnRestriction.register(
                    CFTPEntities.CREEPER_FIRE,
                    SpawnLocationTypes.ON_GROUND,
                    Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                    HostileEntity::canSpawnInDark
            );
        }

        { // WIND
            BiomeModifications.addSpawn(
                    BiomeSelectors.includeByKey((RegistryKey<Biome>[]) BIOMES_WIND),
                    SpawnGroup.MONSTER, CFTPEntities.CREEPER_WIND,
                    CREEPER_GROUP_WEIGHT, CREEPER_GROUP_MIN, CREEPER_GROUP_MAX
            );

            SpawnRestriction.register(
                    CFTPEntities.CREEPER_WIND,
                    SpawnLocationTypes.ON_GROUND,
                    Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                    HostileEntity::canSpawnInDark
            );
        }

        { // LIGHTING
            BiomeModifications.addSpawn(
                    BiomeSelectors.includeByKey((RegistryKey<Biome>[]) BIOMES_LIGHTING),
                    SpawnGroup.MONSTER, CFTPEntities.CREEPER_LIGHTING,
                    CREEPER_GROUP_WEIGHT, CREEPER_GROUP_MIN, CREEPER_GROUP_MAX
            );

            SpawnRestriction.register(
                    CFTPEntities.CREEPER_LIGHTING,
                    SpawnLocationTypes.ON_GROUND,
                    Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                    HostileEntity::canSpawnInDark
            );
        }

        { // WATER
            BiomeModifications.addSpawn(
                    BiomeSelectors.includeByKey((RegistryKey<Biome>[]) BIOMES_WATER),
                    SpawnGroup.MONSTER, CFTPEntities.CREEPER_WATER,
                    CREEPER_GROUP_WEIGHT, CREEPER_GROUP_MIN, CREEPER_GROUP_MAX
            );

            SpawnRestriction.register(
                    CFTPEntities.CREEPER_WATER,
                    SpawnLocationTypes.ON_GROUND,
                    Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                    HostileEntity::canSpawnInDark
            );
        }

        { // EARTH
            BiomeModifications.addSpawn(
                    BiomeSelectors.includeByKey((RegistryKey<Biome>[]) BIOMES_EARTH),
                    SpawnGroup.MONSTER, CFTPEntities.CREEPER_EARTH,
                    CREEPER_GROUP_WEIGHT, CREEPER_GROUP_MIN, CREEPER_GROUP_MAX
            );

            SpawnRestriction.register(
                    CFTPEntities.CREEPER_EARTH,
                    SpawnLocationTypes.ON_GROUND,
                    Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                    HostileEntity::canSpawnInDark
            );
        }

        { // EARTH
            BiomeModifications.addSpawn(
                    BiomeSelectors.includeByKey((RegistryKey<Biome>[]) BIOMES_DIRT),
                    SpawnGroup.MONSTER, CFTPEntities.CREEPER_DIRT,
                    CREEPER_GROUP_WEIGHT, CREEPER_GROUP_MIN, CREEPER_GROUP_MAX
            );

            SpawnRestriction.register(
                    CFTPEntities.CREEPER_DIRT,
                    SpawnLocationTypes.ON_GROUND,
                    Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                    HostileEntity::canSpawnInDark
            );
        }

        { // LAVA
            BiomeModifications.addSpawn(
                    BiomeSelectors.includeByKey((RegistryKey<Biome>[]) BIOMES_LAVA),
                    SpawnGroup.MONSTER, CFTPEntities.CREEPER_LAVA,
                    CREEPER_GROUP_WEIGHT, CREEPER_GROUP_MIN, CREEPER_GROUP_MAX
            );

            SpawnRestriction.register(
                    CFTPEntities.CREEPER_LAVA,
                    SpawnLocationTypes.ON_GROUND,
                    Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                    HostileEntity::canSpawnInDark
            );
        }

        { // GHOST
            BiomeModifications.addSpawn(
                    BiomeSelectors.includeByKey((RegistryKey<Biome>[]) BIOMES_GHOST),
                    SpawnGroup.MONSTER, CFTPEntities.CREEPER_GHOST,
                    CREEPER_GROUP_WEIGHT, CREEPER_GROUP_MIN, CREEPER_GROUP_MAX
            );

            SpawnRestriction.register(
                    CFTPEntities.CREEPER_GHOST,
                    SpawnLocationTypes.ON_GROUND,
                    Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                    HostileEntity::canSpawnInDark
            );
        }

        { // NETHER
            BiomeModifications.addSpawn(
                    BiomeSelectors.includeByKey((RegistryKey<Biome>[]) BIOMES_NETHER),
                    SpawnGroup.MONSTER, CFTPEntities.CREEPER_NETHER,
                    CREEPER_GROUP_WEIGHT, CREEPER_GROUP_MIN, CREEPER_GROUP_MAX
            );

            SpawnRestriction.register(
                    CFTPEntities.CREEPER_NETHER,
                    SpawnLocationTypes.ON_GROUND,
                    Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                    HostileEntity::canSpawnInDark
            );
        }

        { // FLIP
            BiomeModifications.addSpawn(
                    BiomeSelectors.includeByKey((RegistryKey<Biome>[]) BIOMES_FLIP),
                    SpawnGroup.MONSTER, CFTPEntities.CREEPER_FLIP,
                    CREEPER_GROUP_WEIGHT, CREEPER_GROUP_MIN, CREEPER_GROUP_MAX
            );

            SpawnRestriction.register(
                    CFTPEntities.CREEPER_FLIP,
                    SpawnLocationTypes.ON_GROUND,
                    Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                    HostileEntity::canSpawnInDark
            );
        }

        { // FRIENDLY
            BiomeModifications.addSpawn(
                    BiomeSelectors.includeByKey((RegistryKey<Biome>[]) BIOMES_FRIENDLY),
                    SpawnGroup.MONSTER, CFTPEntities.CREEPER_FRIENDLY,
                    CREEPER_GROUP_WEIGHT, CREEPER_GROUP_MIN, CREEPER_GROUP_MAX
            );

            SpawnRestriction.register(
                    CFTPEntities.CREEPER_FRIENDLY,
                    SpawnLocationTypes.ON_GROUND,
                    Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                    HostileEntity::canSpawnInDark
            );
        }

        { // SNOW
            BiomeModifications.addSpawn(
                    BiomeSelectors.includeByKey((RegistryKey<Biome>[]) BIOMES_SNOW),
                    SpawnGroup.MONSTER, CFTPEntities.CREEPER_SNOW,
                    CREEPER_GROUP_WEIGHT, CREEPER_GROUP_MIN, CREEPER_GROUP_MAX
            );

            SpawnRestriction.register(
                    CFTPEntities.CREEPER_SNOW,
                    SpawnLocationTypes.ON_GROUND,
                    Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                    HostileEntity::canSpawnInDark
            );
        }

        { // SWAMP
            BiomeModifications.addSpawn(
                    BiomeSelectors.includeByKey((RegistryKey<Biome>[]) BIOMES_SWAMP),
                    SpawnGroup.MONSTER, CFTPEntities.CREEPER_SWAMP,
                    CREEPER_GROUP_WEIGHT, CREEPER_GROUP_MIN, CREEPER_GROUP_MAX
            );

            SpawnRestriction.register(
                    CFTPEntities.CREEPER_SWAMP,
                    SpawnLocationTypes.ON_GROUND,
                    Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                    HostileEntity::canSpawnInDark
            );
        }

        { // DARK
            BiomeModifications.addSpawn(
                    BiomeSelectors.includeByKey((RegistryKey<Biome>[]) BIOMES_DARK),
                    SpawnGroup.MONSTER, CFTPEntities.CREEPER_DARK,
                    CREEPER_GROUP_WEIGHT, CREEPER_GROUP_MIN, CREEPER_GROUP_MAX
            );

            SpawnRestriction.register(
                    CFTPEntities.CREEPER_DARK,
                    SpawnLocationTypes.ON_GROUND,
                    Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                    HostileEntity::canSpawnInDark
            );
        }

        { // BALLISTIC
            BiomeModifications.addSpawn(
                    BiomeSelectors.includeByKey((RegistryKey<Biome>[]) BIOMES_BALLISTIC),
                    SpawnGroup.MONSTER, CFTPEntities.CREEPER_BALLISTIC,
                    CREEPER_GROUP_WEIGHT, CREEPER_GROUP_MIN, CREEPER_GROUP_MAX
            );

            SpawnRestriction.register(
                    CFTPEntities.CREEPER_BALLISTIC,
                    SpawnLocationTypes.ON_GROUND,
                    Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                    HostileEntity::canSpawnInDark
            );
        }

        //{ // GOLDEN
        //    BiomeModifications.addSpawn(
        //            BiomeSelectors.includeByKey((RegistryKey<Biome>[]) ALL_BIOMES),
        //            SpawnGroup.MONSTER, CFTPEntities.CREEPER_GOLDEN,
        //            CREEPER_GROUP_WEIGHT, CREEPER_GROUP_MIN, CREEPER_GROUP_MAX
        //    );
        //
        //    SpawnRestriction.register(
        //            CFTPEntities.CREEPER_GOLDEN,
        //            SpawnLocationTypes.ON_GROUND,
        //            Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
        //            HostileEntity::canSpawnInDark
        //    );
        //}

        //{ // BRIDGER
        //    BiomeModifications.addSpawn(
        //            BiomeSelectors.includeByKey((RegistryKey<Biome>[]) ALL_BIOMES),
        //            SpawnGroup.MONSTER, CFTPEntities.CREEPER_BRIDGER,
        //            CREEPER_GROUP_WEIGHT, CREEPER_GROUP_MIN, CREEPER_GROUP_MAX
        //    );
        //
        //    SpawnRestriction.register(
        //            CFTPEntities.CREEPER_BRIDGER,
        //            SpawnLocationTypes.ON_GROUND,
        //            Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
        //            HostileEntity::canSpawnInDark
        //    );
        //}

        //{ // PIGGY
        //    BiomeModifications.addSpawn(
        //            BiomeSelectors.includeByKey((RegistryKey<Biome>[]) ALL_BIOMES),
        //            SpawnGroup.MONSTER, CFTPEntities.CREEPER_PIGGY,
        //            CREEPER_GROUP_WEIGHT, CREEPER_GROUP_MIN, CREEPER_GROUP_MAX
        //    );
        //
        //    SpawnRestriction.register(
        //            CFTPEntities.CREEPER_PIGGY,
        //            SpawnLocationTypes.ON_GROUND,
        //            Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
        //            HostileEntity::canSpawnIgnoreLightLevel
        //    );
        //}

        { // AMALGAM
            BiomeModifications.addSpawn(
                    BiomeSelectors.includeByKey((RegistryKey<Biome>[]) BIOMES_AMALGAM),
                    SpawnGroup.MONSTER, CFTPEntities.CREEPER_AMALGAM,
                    CREEPER_GROUP_WEIGHT, CREEPER_GROUP_MIN, CREEPER_GROUP_MAX
            );

            SpawnRestriction.register(
                    CFTPEntities.CREEPER_AMALGAM,
                    SpawnLocationTypes.ON_GROUND,
                    Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                    HostileEntity::canSpawnInDark
            );
        }

        { // AMETHYST
            BiomeModifications.addSpawn(
                    BiomeSelectors.includeByKey((RegistryKey<Biome>[]) BIOMES_AMETHYST),
                    SpawnGroup.MONSTER, CFTPEntities.CREEPER_AMETHYST,
                    CREEPER_GROUP_WEIGHT, CREEPER_GROUP_MIN, CREEPER_GROUP_MAX
            );

            SpawnRestriction.register(
                    CFTPEntities.CREEPER_AMETHYST,
                    SpawnLocationTypes.ON_GROUND,
                    Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                    HostileEntity::canSpawnInDark
            );
        }

        //{ // BREWER
        //    BiomeModifications.addSpawn(
        //            BiomeSelectors.includeByKey((RegistryKey<Biome>[]) ALL_BIOMES),
        //            SpawnGroup.MONSTER, CFTPEntities.CREEPER_BREWER,
        //            CREEPER_GROUP_WEIGHT, CREEPER_GROUP_MIN, CREEPER_GROUP_MAX
        //    );
        //
        //    SpawnRestriction.register(
        //            CFTPEntities.CREEPER_BREWER,
        //            SpawnLocationTypes.ON_GROUND,
        //            Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
        //            HostileEntity::canSpawnInDark
        //    );
        //}

        //{ // GIANT
        //    BiomeModifications.addSpawn(
        //            BiomeSelectors.includeByKey((RegistryKey<Biome>[]) ALL_BIOMES),
        //            SpawnGroup.MONSTER, CFTPEntities.CREEPER_GIANT,
        //            CREEPER_GROUP_WEIGHT, CREEPER_GROUP_MIN, CREEPER_GROUP_MAX
        //    );
        //
        //    SpawnRestriction.register(
        //            CFTPEntities.CREEPER_GIANT,
        //            SpawnLocationTypes.ON_GROUND,
        //            Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
        //            HostileEntity::canSpawnInDark
        //    );
        //}

        //{ // HARVEST
        //    BiomeModifications.addSpawn(
        //            BiomeSelectors.includeByKey((RegistryKey<Biome>[]) ALL_BIOMES),
        //            SpawnGroup.MONSTER, CFTPEntities.CREEPER_HARVEST,
        //            CREEPER_GROUP_WEIGHT, CREEPER_GROUP_MIN, CREEPER_GROUP_MAX
        //    );
        //
        //    SpawnRestriction.register(
        //            CFTPEntities.CREEPER_HARVEST,
        //            SpawnLocationTypes.ON_GROUND,
        //            Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
        //            HostileEntity::canSpawnInDark
        //    );
        //}

        //{ // OCEANID
        //    BiomeModifications.addSpawn(
        //            BiomeSelectors.includeByKey((RegistryKey<Biome>[]) ALL_BIOMES),
        //            SpawnGroup.MONSTER, CFTPEntities.CREEPER_OCEANID,
        //            CREEPER_GROUP_WEIGHT, CREEPER_GROUP_MIN, CREEPER_GROUP_MAX
        //    );
        //
        //    SpawnRestriction.register(
        //            CFTPEntities.CREEPER_OCEANID,
        //            SpawnLocationTypes.ON_GROUND,
        //            Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
        //            HostileEntity::canSpawnInDark
        //    );
        //}

        //{ // SAND
        //    BiomeModifications.addSpawn(
        //            BiomeSelectors.includeByKey((RegistryKey<Biome>[]) ALL_BIOMES),
        //            SpawnGroup.MONSTER, CFTPEntities.CREEPER_SAND,
        //            CREEPER_GROUP_WEIGHT, CREEPER_GROUP_MIN, CREEPER_GROUP_MAX
        //    );
        //
        //    SpawnRestriction.register(
        //            CFTPEntities.CREEPER_SAND,
        //            SpawnLocationTypes.ON_GROUND,
        //            Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
        //            HostileEntity::canSpawnIgnoreLightLevel
        //    );
        //}

        { // SCULK
            BiomeModifications.addSpawn(
                    BiomeSelectors.includeByKey((RegistryKey<Biome>[]) BIOMES_SCULK),
                    SpawnGroup.MONSTER, CFTPEntities.CREEPER_SCULK,
                    CREEPER_GROUP_WEIGHT, CREEPER_GROUP_MIN, CREEPER_GROUP_MAX
            );

            SpawnRestriction.register(
                    CFTPEntities.CREEPER_SCULK,
                    SpawnLocationTypes.ON_GROUND,
                    Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                    HostileEntity::canSpawnInDark
            );
        }

        //{ // SPIDER YELLOW
        //    BiomeModifications.addSpawn(
        //            BiomeSelectors.includeByKey((RegistryKey<Biome>[]) OVERWORLD_SWAMP),
        //            SpawnGroup.MONSTER, CFTPEntities.SPIDER_YELLOW,
        //            CREEPER_GROUP_WEIGHT, CREEPER_GROUP_MIN, CREEPER_GROUP_MAX
        //    );
        //
        //    SpawnRestriction.register(
        //            CFTPEntities.SPIDER_YELLOW,
        //            SpawnLocationTypes.ON_GROUND,
        //            Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
        //            HostileEntity::canSpawnIgnoreLightLevel
        //    );
        //}
        //
        //{ // SPIDER BLUE
        //    BiomeModifications.addSpawn(
        //            BiomeSelectors.includeByKey((RegistryKey<Biome>[]) OVERWORLD_JUNGLE),
        //            SpawnGroup.MONSTER, CFTPEntities.SPIDER_BLUE,
        //            CREEPER_GROUP_WEIGHT, CREEPER_GROUP_MIN, CREEPER_GROUP_MAX
        //    );
        //
        //    SpawnRestriction.register(
        //            CFTPEntities.SPIDER_BLUE,
        //            SpawnLocationTypes.ON_GROUND,
        //            Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
        //            HostileEntity::canSpawnIgnoreLightLevel
        //    );
        //}

        // SpawnLocationTypes.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES

        { // HORSE FIERY
            BiomeModifications.addSpawn(
                    BiomeSelectors.includeByKey(BiomeKeys.NETHER_WASTES, BiomeKeys.WARPED_FOREST, BiomeKeys.CRIMSON_FOREST, BiomeKeys.SOUL_SAND_VALLEY, BiomeKeys.BASALT_DELTAS),
                    SpawnGroup.CREATURE, CFTPEntities.HORSE_FIERY,
                    200, 1, 4
            );

            SpawnRestriction.register(
                    CFTPEntities.HORSE_FIERY,
                    SpawnLocationTypes.ON_GROUND,
                    Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                    HorseFieryEntity::canSpawn
            );
        }

        // ---

        { // VANILLA

            //{ // PHANTOM
            //    BiomeModifications.addSpawn(
            //            BiomeSelectors.includeByKey((RegistryKey<Biome>[]) END),
            //            SpawnGroup.MONSTER, EntityType.PHANTOM,
            //            20, 1, 4
            //    );
            //
            //    // From net.minecraft.entity.SpawnRestriction.java
            //    // register(EntityType.PHANTOM, SpawnLocationTypes.UNRESTRICTED, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MobEntity::canMobSpawn);
            //
            //    //SpawnRestriction.register(
            //    //        EntityType.PHANTOM,
            //    //        SpawnLocationTypes.UNRESTRICTED,
            //    //        Heightmap.Type.MOTION_BLOCKING,
            //    //        CFTPEntitySpawns::canSpawnIgnoreLightLevel
            //    //);
            //
            //    // make them spawn in end dimension // DONE
            //    // also remove golden and flip creepers from end
            //    // add a chance for thrower creeper
            //    // add an end_creeper variant -> a new end block would be cool like null-fluid or something
            //    // -> a block that somewhat looks like end-portal but it's a block and its hard collect?
            //    // also to many of them spawns there now.
            //
            //}

        }

    }

}
