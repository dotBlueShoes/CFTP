package cftp.world.gen;

import cftp.config.CFTPData;
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

        final RegistryKey<?>[] ALL_BIOMES = {
                BiomeKeys.PLAINS, BiomeKeys.SUNFLOWER_PLAINS, BiomeKeys.SNOWY_PLAINS,
                BiomeKeys.ICE_SPIKES, BiomeKeys.DESERT, BiomeKeys.SWAMP,
                BiomeKeys.MANGROVE_SWAMP, BiomeKeys.FOREST, BiomeKeys.FLOWER_FOREST,
                BiomeKeys.BIRCH_FOREST, BiomeKeys.DARK_FOREST, BiomeKeys.PALE_GARDEN,
                BiomeKeys.OLD_GROWTH_BIRCH_FOREST, BiomeKeys.TAIGA, BiomeKeys.SNOWY_TAIGA,
                BiomeKeys.SAVANNA, BiomeKeys.SAVANNA_PLATEAU, BiomeKeys.WINDSWEPT_HILLS,
                BiomeKeys.WINDSWEPT_GRAVELLY_HILLS, BiomeKeys.WINDSWEPT_FOREST, BiomeKeys.WINDSWEPT_SAVANNA,
                BiomeKeys.JUNGLE, BiomeKeys.SPARSE_JUNGLE, BiomeKeys.BAMBOO_JUNGLE,
                BiomeKeys.BADLANDS, BiomeKeys.ERODED_BADLANDS, BiomeKeys.WOODED_BADLANDS,
                BiomeKeys.MEADOW, BiomeKeys.CHERRY_GROVE, BiomeKeys.GROVE,
                BiomeKeys.SNOWY_SLOPES, BiomeKeys.FROZEN_PEAKS, BiomeKeys.JAGGED_PEAKS,
                BiomeKeys.STONY_PEAKS, BiomeKeys.RIVER, BiomeKeys.FROZEN_RIVER,
                BiomeKeys.BEACH, BiomeKeys.SNOWY_BEACH, BiomeKeys.WARM_OCEAN,
                BiomeKeys.LUKEWARM_OCEAN, BiomeKeys.DEEP_LUKEWARM_OCEAN, BiomeKeys.OCEAN,
                BiomeKeys.DEEP_OCEAN, BiomeKeys.COLD_OCEAN, BiomeKeys.DEEP_COLD_OCEAN,
                BiomeKeys.FROZEN_OCEAN, BiomeKeys.DEEP_FROZEN_OCEAN, BiomeKeys.MUSHROOM_FIELDS,
                BiomeKeys.DRIPSTONE_CAVES, BiomeKeys.LUSH_CAVES, BiomeKeys.DEEP_DARK,
                BiomeKeys.NETHER_WASTES, BiomeKeys.WARPED_FOREST, BiomeKeys.CRIMSON_FOREST,
                BiomeKeys.SOUL_SAND_VALLEY, BiomeKeys.BASALT_DELTAS, BiomeKeys.THE_END,
                BiomeKeys.END_HIGHLANDS, BiomeKeys.END_MIDLANDS, BiomeKeys.SMALL_END_ISLANDS,
                BiomeKeys.END_BARRENS
        };

        final RegistryKey<?>[] BIOMES_LIGHTING  = {BiomeKeys.WINDSWEPT_HILLS, BiomeKeys.WINDSWEPT_GRAVELLY_HILLS, BiomeKeys.WINDSWEPT_FOREST, BiomeKeys.WINDSWEPT_SAVANNA};
        final RegistryKey<?>[] BIOMES_COOKIE    = {BiomeKeys.JUNGLE, BiomeKeys.SPARSE_JUNGLE, BiomeKeys.BAMBOO_JUNGLE};
        final RegistryKey<?>[] BIOMES_ENDER     = {BiomeKeys.JUNGLE, BiomeKeys.SPARSE_JUNGLE, BiomeKeys.BAMBOO_JUNGLE, BiomeKeys.THE_END, BiomeKeys.END_HIGHLANDS, BiomeKeys.END_MIDLANDS, BiomeKeys.SMALL_END_ISLANDS, BiomeKeys.END_BARRENS, BiomeKeys.WARPED_FOREST, BiomeKeys.CRIMSON_FOREST};
        final RegistryKey<?>[] BIOMES_WATER     = {BiomeKeys.PLAINS, BiomeKeys.SUNFLOWER_PLAINS, BiomeKeys.SNOWY_PLAINS};
        final RegistryKey<?>[] BIOMES_EARTH     = {BiomeKeys.PLAINS, BiomeKeys.SUNFLOWER_PLAINS, BiomeKeys.SNOWY_PLAINS};
        final RegistryKey<?>[] BIOMES_FIRE      = {BiomeKeys.PLAINS, BiomeKeys.SUNFLOWER_PLAINS, BiomeKeys.DESERT, BiomeKeys.SAVANNA, BiomeKeys.SAVANNA_PLATEAU, BiomeKeys.WINDSWEPT_SAVANNA, BiomeKeys.BADLANDS, BiomeKeys.ERODED_BADLANDS, BiomeKeys.WOODED_BADLANDS};
        final RegistryKey<?>[] BIOMES_DIRT      = {BiomeKeys.DRIPSTONE_CAVES};
        final RegistryKey<?>[] BIOMES_LAVA      = {BiomeKeys.NETHER_WASTES, BiomeKeys.BASALT_DELTAS};
        final RegistryKey<?>[] BIOMES_WIND      = {BiomeKeys.PLAINS, BiomeKeys.SUNFLOWER_PLAINS, BiomeKeys.SNOWY_PLAINS, BiomeKeys.DESERT, BiomeKeys.SAVANNA, BiomeKeys.SAVANNA_PLATEAU, BiomeKeys.WINDSWEPT_SAVANNA, BiomeKeys.BADLANDS, BiomeKeys.ERODED_BADLANDS, BiomeKeys.WOODED_BADLANDS};
        final RegistryKey<?>[] BIOMES_GHOST     = {BiomeKeys.SWAMP, BiomeKeys.SOUL_SAND_VALLEY};
        final RegistryKey<?>[] BIOMES_NETHER    = {BiomeKeys.NETHER_WASTES, BiomeKeys.BASALT_DELTAS, BiomeKeys.WARPED_FOREST, BiomeKeys.CRIMSON_FOREST};
        final RegistryKey<?>[] BIOMES_FLIP      = {BiomeKeys.WARPED_FOREST, BiomeKeys.CRIMSON_FOREST};
        final RegistryKey<?>[] BIOMES_FRIENDLY  = {BiomeKeys.MUSHROOM_FIELDS};
        final RegistryKey<?>[] BIOMES_SNOW      = {BiomeKeys.SNOWY_PLAINS, BiomeKeys.SNOWY_TAIGA, BiomeKeys.ICE_SPIKES, BiomeKeys.FROZEN_OCEAN, BiomeKeys.DEEP_FROZEN_OCEAN, BiomeKeys.SNOWY_SLOPES, BiomeKeys.FROZEN_PEAKS, BiomeKeys.FROZEN_RIVER};
        final RegistryKey<?>[] BIOMES_SWAMP     = {BiomeKeys.SWAMP, BiomeKeys.MANGROVE_SWAMP};
        final RegistryKey<?>[] BIOMES_DARK      = {BiomeKeys.DESERT, BiomeKeys.SAVANNA, BiomeKeys.SAVANNA_PLATEAU, BiomeKeys.WINDSWEPT_SAVANNA, BiomeKeys.BADLANDS, BiomeKeys.ERODED_BADLANDS, BiomeKeys.WOODED_BADLANDS};
        final RegistryKey<?>[] BIOMES_BALLISTIC = {BiomeKeys.JAGGED_PEAKS, BiomeKeys.STONY_PEAKS, BiomeKeys.DARK_FOREST};
        final RegistryKey<?>[] BIOMES_AMETHYST  = {BiomeKeys.LUSH_CAVES};
        final RegistryKey<?>[] BIOMES_SCULK     = {BiomeKeys.DEEP_DARK};
        final RegistryKey<?>[] BIOMES_AMALGAM   = {BiomeKeys.MEADOW};
        final RegistryKey<?>[] BIOMES_BREWER     = ALL_BIOMES;
        final RegistryKey<?>[] BIOMES_BRIDGER    = ALL_BIOMES;
        final RegistryKey<?>[] BIOMES_GIANT      = ALL_BIOMES;
        final RegistryKey<?>[] BIOMES_GOLDEN     = ALL_BIOMES;
        final RegistryKey<?>[] BIOMES_HARVEST    = ALL_BIOMES;
        final RegistryKey<?>[] BIOMES_HEROBRINE  = ALL_BIOMES;
        final RegistryKey<?>[] BIOMES_OCEANID    = ALL_BIOMES;
        final RegistryKey<?>[] BIOMES_PIGGY      = ALL_BIOMES;
        final RegistryKey<?>[] BIOMES_SAND       = ALL_BIOMES;
        final RegistryKey<?>[] BIOMES_SPECTRE    = ALL_BIOMES;

        final int CREEPER_GROUP_MIN = 1;
        final int CREEPER_GROUP_MAX = 3;

        if (CFTPData.creeperCookie.hardcodedSpawnChance > 0) { // COOKIE
            BiomeModifications.addSpawn(
                    BiomeSelectors.includeByKey((RegistryKey<Biome>[]) BIOMES_COOKIE),
                    SpawnGroup.MONSTER, CFTPEntities.CREEPER_COOKIE,
                    CFTPData.creeperCookie.hardcodedSpawnChance, CREEPER_GROUP_MIN, CREEPER_GROUP_MAX
            );

            SpawnRestriction.register(
                    CFTPEntities.CREEPER_COOKIE,
                    SpawnLocationTypes.ON_GROUND,
                    Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                    HostileEntity::canSpawnIgnoreLightLevel
            );
        }

        if (CFTPData.creeperEnder.hardcodedSpawnChance > 0){ // ENDER
            BiomeModifications.addSpawn(
                    BiomeSelectors.includeByKey((RegistryKey<Biome>[]) BIOMES_ENDER),
                    SpawnGroup.MONSTER, CFTPEntities.CREEPER_ENDER,
                    CFTPData.creeperEnder.hardcodedSpawnChance, CREEPER_GROUP_MIN, CREEPER_GROUP_MAX
            );

            SpawnRestriction.register(
                    CFTPEntities.CREEPER_ENDER,
                    SpawnLocationTypes.ON_GROUND,
                    Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                    HostileEntity::canSpawnInDark
            );
        }

        if (CFTPData.creeperFire.hardcodedSpawnChance > 0) { // FIRE
            BiomeModifications.addSpawn(
                    BiomeSelectors.includeByKey((RegistryKey<Biome>[]) BIOMES_FIRE),
                    SpawnGroup.MONSTER, CFTPEntities.CREEPER_FIRE,
                    CFTPData.creeperFire.hardcodedSpawnChance, CREEPER_GROUP_MIN, CREEPER_GROUP_MAX
            );

            SpawnRestriction.register(
                    CFTPEntities.CREEPER_FIRE,
                    SpawnLocationTypes.ON_GROUND,
                    Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                    HostileEntity::canSpawnInDark
            );
        }

        if (CFTPData.creeperWind.hardcodedSpawnChance > 0) { // WIND
            BiomeModifications.addSpawn(
                    BiomeSelectors.includeByKey((RegistryKey<Biome>[]) BIOMES_WIND),
                    SpawnGroup.MONSTER, CFTPEntities.CREEPER_WIND,
                    CFTPData.creeperWind.hardcodedSpawnChance, CREEPER_GROUP_MIN, CREEPER_GROUP_MAX
            );

            SpawnRestriction.register(
                    CFTPEntities.CREEPER_WIND,
                    SpawnLocationTypes.ON_GROUND,
                    Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                    HostileEntity::canSpawnInDark
            );
        }

        if (CFTPData.creeperLighting.hardcodedSpawnChance > 0) { // LIGHTING
            BiomeModifications.addSpawn(
                    BiomeSelectors.includeByKey((RegistryKey<Biome>[]) BIOMES_LIGHTING),
                    SpawnGroup.MONSTER, CFTPEntities.CREEPER_LIGHTING,
                    CFTPData.creeperLighting.hardcodedSpawnChance, CREEPER_GROUP_MIN, CREEPER_GROUP_MAX
            );

            SpawnRestriction.register(
                    CFTPEntities.CREEPER_LIGHTING,
                    SpawnLocationTypes.ON_GROUND,
                    Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                    HostileEntity::canSpawnInDark
            );
        }

        if (CFTPData.creeperWater.hardcodedSpawnChance > 0) { // WATER
            BiomeModifications.addSpawn(
                    BiomeSelectors.includeByKey((RegistryKey<Biome>[]) BIOMES_WATER),
                    SpawnGroup.MONSTER, CFTPEntities.CREEPER_WATER,
                    CFTPData.creeperWater.hardcodedSpawnChance, CREEPER_GROUP_MIN, CREEPER_GROUP_MAX
            );

            SpawnRestriction.register(
                    CFTPEntities.CREEPER_WATER,
                    SpawnLocationTypes.ON_GROUND,
                    Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                    HostileEntity::canSpawnInDark
            );
        }

        if (CFTPData.creeperEarth.hardcodedSpawnChance > 0) { // EARTH
            BiomeModifications.addSpawn(
                    BiomeSelectors.includeByKey((RegistryKey<Biome>[]) BIOMES_EARTH),
                    SpawnGroup.MONSTER, CFTPEntities.CREEPER_EARTH,
                    CFTPData.creeperEarth.hardcodedSpawnChance, CREEPER_GROUP_MIN, CREEPER_GROUP_MAX
            );

            SpawnRestriction.register(
                    CFTPEntities.CREEPER_EARTH,
                    SpawnLocationTypes.ON_GROUND,
                    Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                    HostileEntity::canSpawnInDark
            );
        }

        if (CFTPData.creeperDirt.hardcodedSpawnChance > 0) { // DIRT
            BiomeModifications.addSpawn(
                    BiomeSelectors.includeByKey((RegistryKey<Biome>[]) BIOMES_DIRT),
                    SpawnGroup.MONSTER, CFTPEntities.CREEPER_DIRT,
                    CFTPData.creeperDirt.hardcodedSpawnChance, CREEPER_GROUP_MIN, CREEPER_GROUP_MAX
            );

            SpawnRestriction.register(
                    CFTPEntities.CREEPER_DIRT,
                    SpawnLocationTypes.ON_GROUND,
                    Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                    HostileEntity::canSpawnInDark
            );
        }

        if (CFTPData.creeperLava.hardcodedSpawnChance > 0) { // LAVA
            BiomeModifications.addSpawn(
                    BiomeSelectors.includeByKey((RegistryKey<Biome>[]) BIOMES_LAVA),
                    SpawnGroup.MONSTER, CFTPEntities.CREEPER_LAVA,
                    CFTPData.creeperLava.hardcodedSpawnChance, CREEPER_GROUP_MIN, CREEPER_GROUP_MAX
            );

            SpawnRestriction.register(
                    CFTPEntities.CREEPER_LAVA,
                    SpawnLocationTypes.ON_GROUND,
                    Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                    HostileEntity::canSpawnInDark
            );
        }

        if (CFTPData.creeperGhost.hardcodedSpawnChance > 0) { // GHOST
            BiomeModifications.addSpawn(
                    BiomeSelectors.includeByKey((RegistryKey<Biome>[]) BIOMES_GHOST),
                    SpawnGroup.MONSTER, CFTPEntities.CREEPER_GHOST,
                    CFTPData.creeperGhost.hardcodedSpawnChance, CREEPER_GROUP_MIN, CREEPER_GROUP_MAX
            );

            SpawnRestriction.register(
                    CFTPEntities.CREEPER_GHOST,
                    SpawnLocationTypes.ON_GROUND,
                    Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                    HostileEntity::canSpawnInDark
            );
        }

        if (CFTPData.creeperNether.hardcodedSpawnChance > 0) { // NETHER
            BiomeModifications.addSpawn(
                    BiomeSelectors.includeByKey((RegistryKey<Biome>[]) BIOMES_NETHER),
                    SpawnGroup.MONSTER, CFTPEntities.CREEPER_NETHER,
                    CFTPData.creeperNether.hardcodedSpawnChance, CREEPER_GROUP_MIN, CREEPER_GROUP_MAX
            );

            SpawnRestriction.register(
                    CFTPEntities.CREEPER_NETHER,
                    SpawnLocationTypes.ON_GROUND,
                    Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                    HostileEntity::canSpawnInDark
            );
        }

        if (CFTPData.creeperFlip.hardcodedSpawnChance > 0) { // FLIP
            BiomeModifications.addSpawn(
                    BiomeSelectors.includeByKey((RegistryKey<Biome>[]) BIOMES_FLIP),
                    SpawnGroup.MONSTER, CFTPEntities.CREEPER_FLIP,
                    CFTPData.creeperFlip.hardcodedSpawnChance, CREEPER_GROUP_MIN, CREEPER_GROUP_MAX
            );

            SpawnRestriction.register(
                    CFTPEntities.CREEPER_FLIP,
                    SpawnLocationTypes.ON_GROUND,
                    Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                    HostileEntity::canSpawnInDark
            );
        }

        if (CFTPData.creeperFriendly.hardcodedSpawnChance > 0) { // FRIENDLY
            BiomeModifications.addSpawn(
                    BiomeSelectors.includeByKey((RegistryKey<Biome>[]) BIOMES_FRIENDLY),
                    SpawnGroup.MONSTER, CFTPEntities.CREEPER_FRIENDLY,
                    CFTPData.creeperFriendly.hardcodedSpawnChance, CREEPER_GROUP_MIN, CREEPER_GROUP_MAX
            );

            SpawnRestriction.register(
                    CFTPEntities.CREEPER_FRIENDLY,
                    SpawnLocationTypes.ON_GROUND,
                    Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                    HostileEntity::canSpawnInDark
            );
        }

        if (CFTPData.creeperSnow.hardcodedSpawnChance > 0) { // SNOW
            BiomeModifications.addSpawn(
                    BiomeSelectors.includeByKey((RegistryKey<Biome>[]) BIOMES_SNOW),
                    SpawnGroup.MONSTER, CFTPEntities.CREEPER_SNOW,
                    CFTPData.creeperSnow.hardcodedSpawnChance, CREEPER_GROUP_MIN, CREEPER_GROUP_MAX
            );

            SpawnRestriction.register(
                    CFTPEntities.CREEPER_SNOW,
                    SpawnLocationTypes.ON_GROUND,
                    Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                    HostileEntity::canSpawnInDark
            );
        }

        if (CFTPData.creeperSwamp.hardcodedSpawnChance > 0) { // SWAMP
            BiomeModifications.addSpawn(
                    BiomeSelectors.includeByKey((RegistryKey<Biome>[]) BIOMES_SWAMP),
                    SpawnGroup.MONSTER, CFTPEntities.CREEPER_SWAMP,
                    CFTPData.creeperSwamp.hardcodedSpawnChance, CREEPER_GROUP_MIN, CREEPER_GROUP_MAX
            );

            SpawnRestriction.register(
                    CFTPEntities.CREEPER_SWAMP,
                    SpawnLocationTypes.ON_GROUND,
                    Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                    HostileEntity::canSpawnInDark
            );
        }

        if (CFTPData.creeperDark.hardcodedSpawnChance > 0) { // DARK
            BiomeModifications.addSpawn(
                    BiomeSelectors.includeByKey((RegistryKey<Biome>[]) BIOMES_DARK),
                    SpawnGroup.MONSTER, CFTPEntities.CREEPER_DARK,
                    CFTPData.creeperDark.hardcodedSpawnChance, CREEPER_GROUP_MIN, CREEPER_GROUP_MAX
            );

            SpawnRestriction.register(
                    CFTPEntities.CREEPER_DARK,
                    SpawnLocationTypes.ON_GROUND,
                    Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                    HostileEntity::canSpawnInDark
            );
        }

        if (CFTPData.creeperBallistic.hardcodedSpawnChance > 0) { // BALLISTIC
            BiomeModifications.addSpawn(
                    BiomeSelectors.includeByKey((RegistryKey<Biome>[]) BIOMES_BALLISTIC),
                    SpawnGroup.MONSTER, CFTPEntities.CREEPER_BALLISTIC,
                    CFTPData.creeperBallistic.hardcodedSpawnChance, CREEPER_GROUP_MIN, CREEPER_GROUP_MAX
            );

            SpawnRestriction.register(
                    CFTPEntities.CREEPER_BALLISTIC,
                    SpawnLocationTypes.ON_GROUND,
                    Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                    HostileEntity::canSpawnInDark
            );
        }

        if (CFTPData.creeperGolden.hardcodedSpawnChance > 0) { // GOLDEN
            BiomeModifications.addSpawn(
                    BiomeSelectors.includeByKey((RegistryKey<Biome>[]) BIOMES_GOLDEN),
                    SpawnGroup.MONSTER, CFTPEntities.CREEPER_GOLDEN,
                    CFTPData.creeperGolden.hardcodedSpawnChance, CREEPER_GROUP_MIN, CREEPER_GROUP_MAX
            );

            SpawnRestriction.register(
                    CFTPEntities.CREEPER_GOLDEN,
                    SpawnLocationTypes.ON_GROUND,
                    Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                    HostileEntity::canSpawnInDark
            );
        }

        if (CFTPData.creeperBridger.hardcodedSpawnChance > 0) { // BRIDGER
            BiomeModifications.addSpawn(
                    BiomeSelectors.includeByKey((RegistryKey<Biome>[]) BIOMES_BRIDGER),
                    SpawnGroup.MONSTER, CFTPEntities.CREEPER_BRIDGER,
                    CFTPData.creeperBridger.hardcodedSpawnChance, CREEPER_GROUP_MIN, CREEPER_GROUP_MAX
            );

            SpawnRestriction.register(
                    CFTPEntities.CREEPER_BRIDGER,
                    SpawnLocationTypes.ON_GROUND,
                    Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                    HostileEntity::canSpawnInDark
            );
        }

        if (CFTPData.creeperPiggy.hardcodedSpawnChance > 0) { // PIGGY
            BiomeModifications.addSpawn(
                    BiomeSelectors.includeByKey((RegistryKey<Biome>[]) BIOMES_PIGGY),
                    SpawnGroup.MONSTER, CFTPEntities.CREEPER_PIGGY,
                    CFTPData.creeperPiggy.hardcodedSpawnChance, CREEPER_GROUP_MIN, CREEPER_GROUP_MAX
            );

            SpawnRestriction.register(
                    CFTPEntities.CREEPER_PIGGY,
                    SpawnLocationTypes.ON_GROUND,
                    Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                    HostileEntity::canSpawnIgnoreLightLevel
            );
        }

        if (CFTPData.creeperAmalgam.hardcodedSpawnChance > 0) { // AMALGAM
            BiomeModifications.addSpawn(
                    BiomeSelectors.includeByKey((RegistryKey<Biome>[]) BIOMES_AMALGAM),
                    SpawnGroup.MONSTER, CFTPEntities.CREEPER_AMALGAM,
                    CFTPData.creeperAmalgam.hardcodedSpawnChance, CREEPER_GROUP_MIN, CREEPER_GROUP_MAX
            );

            SpawnRestriction.register(
                    CFTPEntities.CREEPER_AMALGAM,
                    SpawnLocationTypes.ON_GROUND,
                    Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                    HostileEntity::canSpawnInDark
            );
        }

        if (CFTPData.creeperAmethyst.hardcodedSpawnChance > 0) { // AMETHYST
            BiomeModifications.addSpawn(
                    BiomeSelectors.includeByKey((RegistryKey<Biome>[]) BIOMES_AMETHYST),
                    SpawnGroup.MONSTER, CFTPEntities.CREEPER_AMETHYST,
                    CFTPData.creeperAmethyst.hardcodedSpawnChance, CREEPER_GROUP_MIN, CREEPER_GROUP_MAX
            );

            SpawnRestriction.register(
                    CFTPEntities.CREEPER_AMETHYST,
                    SpawnLocationTypes.ON_GROUND,
                    Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                    HostileEntity::canSpawnInDark
            );
        }

        if (CFTPData.creeperBrewer.hardcodedSpawnChance > 0) { // BREWER
            BiomeModifications.addSpawn(
                    BiomeSelectors.includeByKey((RegistryKey<Biome>[]) BIOMES_BREWER),
                    SpawnGroup.MONSTER, CFTPEntities.CREEPER_BREWER,
                    CFTPData.creeperBrewer.hardcodedSpawnChance, CREEPER_GROUP_MIN, CREEPER_GROUP_MAX
            );

            SpawnRestriction.register(
                    CFTPEntities.CREEPER_BREWER,
                    SpawnLocationTypes.ON_GROUND,
                    Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                    HostileEntity::canSpawnInDark
            );
        }

        if (CFTPData.creeperGiant.hardcodedSpawnChance > 0) { // GIANT
            BiomeModifications.addSpawn(
                    BiomeSelectors.includeByKey((RegistryKey<Biome>[]) BIOMES_GIANT),
                    SpawnGroup.MONSTER, CFTPEntities.CREEPER_GIANT,
                    CFTPData.creeperGiant.hardcodedSpawnChance, CREEPER_GROUP_MIN, CREEPER_GROUP_MAX
            );

            SpawnRestriction.register(
                    CFTPEntities.CREEPER_GIANT,
                    SpawnLocationTypes.ON_GROUND,
                    Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                    HostileEntity::canSpawnInDark
            );
        }

        if (CFTPData.creeperHarvest.hardcodedSpawnChance > 0) { // HARVEST
            BiomeModifications.addSpawn(
                    BiomeSelectors.includeByKey((RegistryKey<Biome>[]) BIOMES_HARVEST),
                    SpawnGroup.MONSTER, CFTPEntities.CREEPER_HARVEST,
                    CFTPData.creeperHarvest.hardcodedSpawnChance, CREEPER_GROUP_MIN, CREEPER_GROUP_MAX
            );

            SpawnRestriction.register(
                    CFTPEntities.CREEPER_HARVEST,
                    SpawnLocationTypes.ON_GROUND,
                    Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                    HostileEntity::canSpawnInDark
            );
        }

        if (CFTPData.creeperHerobrine.hardcodedSpawnChance > 0) { // HEROBRINE
            BiomeModifications.addSpawn(
                    BiomeSelectors.includeByKey((RegistryKey<Biome>[]) BIOMES_HEROBRINE),
                    SpawnGroup.MONSTER, CFTPEntities.CREEPER_HEROBRINE,
                    CFTPData.creeperHerobrine.hardcodedSpawnChance, CREEPER_GROUP_MIN, CREEPER_GROUP_MAX
            );

            SpawnRestriction.register(
                    CFTPEntities.CREEPER_HEROBRINE,
                    SpawnLocationTypes.ON_GROUND,
                    Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                    HostileEntity::canSpawnInDark
            );
        }

        if (CFTPData.creeperOceanid.hardcodedSpawnChance > 0) { // OCEANID
            BiomeModifications.addSpawn(
                    BiomeSelectors.includeByKey((RegistryKey<Biome>[]) BIOMES_OCEANID),
                    SpawnGroup.MONSTER, CFTPEntities.CREEPER_OCEANID,
                    CFTPData.creeperOceanid.hardcodedSpawnChance, CREEPER_GROUP_MIN, CREEPER_GROUP_MAX
            );

            SpawnRestriction.register(
                    CFTPEntities.CREEPER_OCEANID,
                    SpawnLocationTypes.ON_GROUND,
                    Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                    HostileEntity::canSpawnInDark
            );
        }

        if (CFTPData.creeperSand.hardcodedSpawnChance > 0) { // SAND
            BiomeModifications.addSpawn(
                    BiomeSelectors.includeByKey((RegistryKey<Biome>[]) BIOMES_SAND),
                    SpawnGroup.MONSTER, CFTPEntities.CREEPER_SAND,
                    CFTPData.creeperSand.hardcodedSpawnChance, CREEPER_GROUP_MIN, CREEPER_GROUP_MAX
            );

            SpawnRestriction.register(
                    CFTPEntities.CREEPER_SAND,
                    SpawnLocationTypes.ON_GROUND,
                    Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                    HostileEntity::canSpawnInDark
            );
        }

        if (CFTPData.creeperSculk.hardcodedSpawnChance > 0) { // SCULK
            BiomeModifications.addSpawn(
                    BiomeSelectors.includeByKey((RegistryKey<Biome>[]) BIOMES_SCULK),
                    SpawnGroup.MONSTER, CFTPEntities.CREEPER_SCULK,
                    CFTPData.creeperSculk.hardcodedSpawnChance, CREEPER_GROUP_MIN, CREEPER_GROUP_MAX
            );

            SpawnRestriction.register(
                    CFTPEntities.CREEPER_SCULK,
                    SpawnLocationTypes.ON_GROUND,
                    Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                    HostileEntity::canSpawnInDark
            );
        }

        if (CFTPData.creeperSpectre.hardcodedSpawnChance > 0) { // SPECTRE
            BiomeModifications.addSpawn(
                    BiomeSelectors.includeByKey((RegistryKey<Biome>[]) BIOMES_SPECTRE),
                    SpawnGroup.MONSTER, CFTPEntities.CREEPER_SPECTRE,
                    CFTPData.creeperSpectre.hardcodedSpawnChance, CREEPER_GROUP_MIN, CREEPER_GROUP_MAX
            );

            SpawnRestriction.register(
                    CFTPEntities.CREEPER_SPECTRE,
                    SpawnLocationTypes.ON_GROUND,
                    Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                    HostileEntity::canSpawnInDark
            );
        }

        //final int ZOMBIE_GROUP_WEIGHT = 10;
        //final int ZOMBIE_GROUP_MIN = 5;
        //final int ZOMBIE_GROUP_MAX = 12;

        // Solving the issue
        // mod -> https://www.curseforge.com/minecraft/mc-mods/custom-spawns/files/all?page=1&pageSize=20
        // mod's git -> https://github.com/b3spectacled/custom-spawns/blob/1.19.x/src/main/java/mod/bespectacled/customspawn/mixin/MixinServerChunkManager.java
        // other know settings -> https://www.reddit.com/r/admincraft/comments/o1cvi1/too_many_mob_spawns_paper_1165/
        // how it works 1 -> https://www.reddit.com/r/technicalminecraft/comments/kgkbkx/can_anyone_explain_the_mob_cap_to_me/
        // how it works 2 -> https://minecraft.wiki/w/Mob_spawning

        // ISSUE -> this was fixed see how.
        // 4 is max for some reason
        // also it might be impossible to have ZOMBIE again.
        //BiomeModifications.addSpawn(
        //        BiomeSelectors.includeByKey( (RegistryKey<Biome>[]) ALL_BIOMES),
        //        SpawnGroup.MONSTER, EntityType.ZOMBIE,
        //        ZOMBIE_GROUP_WEIGHT, ZOMBIE_GROUP_MIN, ZOMBIE_GROUP_MAX
        //);

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

        //{ // HORSE FIERY
        //    BiomeModifications.addSpawn(
        //            BiomeSelectors.includeByKey(BiomeKeys.NETHER_WASTES, BiomeKeys.WARPED_FOREST, BiomeKeys.CRIMSON_FOREST, BiomeKeys.SOUL_SAND_VALLEY, BiomeKeys.BASALT_DELTAS),
        //            SpawnGroup.CREATURE, CFTPEntities.HORSE_FIERY,
        //            200, 1, 4
        //    );
        //
        //    SpawnRestriction.register(
        //            CFTPEntities.HORSE_FIERY,
        //            SpawnLocationTypes.ON_GROUND,
        //            Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
        //            HorseFieryEntity::canSpawn
        //    );
        //}

        // ---
        
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
