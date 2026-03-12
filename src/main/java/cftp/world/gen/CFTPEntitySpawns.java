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

    public static void register() {

        //final RegistryKey<?>[] ALL_BIOMES = {
        //        BiomeKeys.PLAINS, BiomeKeys.SUNFLOWER_PLAINS, BiomeKeys.SNOWY_PLAINS,
        //        BiomeKeys.ICE_SPIKES, BiomeKeys.DESERT, BiomeKeys.SWAMP,
        //        BiomeKeys.MANGROVE_SWAMP, BiomeKeys.FOREST, BiomeKeys.FLOWER_FOREST,
        //        BiomeKeys.BIRCH_FOREST, BiomeKeys.DARK_FOREST, BiomeKeys.PALE_GARDEN,
        //        BiomeKeys.OLD_GROWTH_BIRCH_FOREST, BiomeKeys.TAIGA, BiomeKeys.SNOWY_TAIGA,
        //        BiomeKeys.SAVANNA, BiomeKeys.SAVANNA_PLATEAU, BiomeKeys.WINDSWEPT_HILLS,
        //        BiomeKeys.WINDSWEPT_GRAVELLY_HILLS, BiomeKeys.WINDSWEPT_FOREST, BiomeKeys.WINDSWEPT_SAVANNA,
        //        BiomeKeys.JUNGLE, BiomeKeys.SPARSE_JUNGLE, BiomeKeys.BAMBOO_JUNGLE,
        //        BiomeKeys.BADLANDS, BiomeKeys.ERODED_BADLANDS, BiomeKeys.WOODED_BADLANDS,
        //        BiomeKeys.MEADOW, BiomeKeys.CHERRY_GROVE, BiomeKeys.GROVE,
        //        BiomeKeys.SNOWY_SLOPES, BiomeKeys.FROZEN_PEAKS, BiomeKeys.JAGGED_PEAKS,
        //        BiomeKeys.STONY_PEAKS, BiomeKeys.RIVER, BiomeKeys.FROZEN_RIVER,
        //        BiomeKeys.BEACH, BiomeKeys.SNOWY_BEACH, BiomeKeys.WARM_OCEAN,
        //        BiomeKeys.LUKEWARM_OCEAN, BiomeKeys.DEEP_LUKEWARM_OCEAN, BiomeKeys.OCEAN,
        //        BiomeKeys.DEEP_OCEAN, BiomeKeys.COLD_OCEAN, BiomeKeys.DEEP_COLD_OCEAN,
        //        BiomeKeys.FROZEN_OCEAN, BiomeKeys.DEEP_FROZEN_OCEAN, BiomeKeys.MUSHROOM_FIELDS,
        //        BiomeKeys.DRIPSTONE_CAVES, BiomeKeys.LUSH_CAVES, BiomeKeys.DEEP_DARK,
        //        BiomeKeys.NETHER_WASTES, BiomeKeys.WARPED_FOREST, BiomeKeys.CRIMSON_FOREST,
        //        BiomeKeys.SOUL_SAND_VALLEY, BiomeKeys.BASALT_DELTAS, BiomeKeys.THE_END,
        //        BiomeKeys.END_HIGHLANDS, BiomeKeys.END_MIDLANDS, BiomeKeys.SMALL_END_ISLANDS,
        //        BiomeKeys.END_BARRENS
        //};

        final int CREEPER_GROUP_MIN = 1;
        final int CREEPER_GROUP_MAX = 3;

        if (CFTPData.creeperAmalgam.hardcodedSpawnCondition.spawnChance > 0) { // AMALGAM
            BiomeModifications.addSpawn(
                    BiomeSelectors.includeByKey(CFTPData.parseBiomes(CFTPData.creeperAmalgam.hardcodedSpawnCondition.biomeList)),
                    SpawnGroup.MONSTER, CFTPEntities.CREEPER_AMALGAM,
                    CFTPData.creeperAmalgam.hardcodedSpawnCondition.spawnChance, CREEPER_GROUP_MIN, CREEPER_GROUP_MAX
            );

            SpawnRestriction.register(
                    CFTPEntities.CREEPER_AMALGAM,
                    SpawnLocationTypes.ON_GROUND,
                    Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                    HostileEntity::canSpawnInDark
            );
        }

        if (CFTPData.creeperAmethyst.hardcodedSpawnCondition.spawnChance > 0) { // AMETHYST
            BiomeModifications.addSpawn(
                    BiomeSelectors.includeByKey((RegistryKey<Biome>[]) CFTPData.parseBiomes(CFTPData.creeperAmethyst.hardcodedSpawnCondition.biomeList)),
                    SpawnGroup.MONSTER, CFTPEntities.CREEPER_AMETHYST,
                    CFTPData.creeperAmethyst.hardcodedSpawnCondition.spawnChance, CREEPER_GROUP_MIN, CREEPER_GROUP_MAX
            );

            SpawnRestriction.register(
                    CFTPEntities.CREEPER_AMETHYST,
                    SpawnLocationTypes.ON_GROUND,
                    Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                    HostileEntity::canSpawnInDark
            );
        }

        if (CFTPData.creeperBallistic.hardcodedSpawnCondition.spawnChance > 0) { // BALLISTIC
            BiomeModifications.addSpawn(
                    BiomeSelectors.includeByKey((RegistryKey<Biome>[]) CFTPData.parseBiomes(CFTPData.creeperBallistic.hardcodedSpawnCondition.biomeList)),
                    SpawnGroup.MONSTER, CFTPEntities.CREEPER_BALLISTIC,
                    CFTPData.creeperBallistic.hardcodedSpawnCondition.spawnChance, CREEPER_GROUP_MIN, CREEPER_GROUP_MAX
            );

            SpawnRestriction.register(
                    CFTPEntities.CREEPER_BALLISTIC,
                    SpawnLocationTypes.ON_GROUND,
                    Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                    HostileEntity::canSpawnInDark
            );
        }

        if (CFTPData.creeperBrewer.hardcodedSpawnCondition.spawnChance > 0) { // BREWER
            BiomeModifications.addSpawn(
                    BiomeSelectors.includeByKey((RegistryKey<Biome>[]) CFTPData.parseBiomes(CFTPData.creeperBrewer.hardcodedSpawnCondition.biomeList)),
                    SpawnGroup.MONSTER, CFTPEntities.CREEPER_BREWER,
                    CFTPData.creeperBrewer.hardcodedSpawnCondition.spawnChance, CREEPER_GROUP_MIN, CREEPER_GROUP_MAX
            );

            SpawnRestriction.register(
                    CFTPEntities.CREEPER_BREWER,
                    SpawnLocationTypes.ON_GROUND,
                    Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                    HostileEntity::canSpawnInDark
            );
        }

        if (CFTPData.creeperBridger.hardcodedSpawnCondition.spawnChance > 0) { // BRIDGER
            BiomeModifications.addSpawn(
                    BiomeSelectors.includeByKey((RegistryKey<Biome>[]) CFTPData.parseBiomes(CFTPData.creeperBridger.hardcodedSpawnCondition.biomeList)),
                    SpawnGroup.MONSTER, CFTPEntities.CREEPER_BRIDGER,
                    CFTPData.creeperBridger.hardcodedSpawnCondition.spawnChance, CREEPER_GROUP_MIN, CREEPER_GROUP_MAX
            );

            SpawnRestriction.register(
                    CFTPEntities.CREEPER_BRIDGER,
                    SpawnLocationTypes.ON_GROUND,
                    Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                    HostileEntity::canSpawnInDark
            );
        }

        if (CFTPData.creeperCookie.hardcodedSpawnCondition.spawnChance > 0) { // COOKIE
            BiomeModifications.addSpawn(
                    BiomeSelectors.includeByKey((RegistryKey<Biome>[]) CFTPData.parseBiomes(CFTPData.creeperCookie.hardcodedSpawnCondition.biomeList)),
                    SpawnGroup.MONSTER, CFTPEntities.CREEPER_COOKIE,
                    CFTPData.creeperCookie.hardcodedSpawnCondition.spawnChance, CREEPER_GROUP_MIN, CREEPER_GROUP_MAX
            );

            SpawnRestriction.register(
                    CFTPEntities.CREEPER_COOKIE,
                    SpawnLocationTypes.ON_GROUND,
                    Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                    HostileEntity::canSpawnIgnoreLightLevel
            );
        }

        if (CFTPData.creeperDark.hardcodedSpawnCondition.spawnChance > 0) { // DARK
            BiomeModifications.addSpawn(
                    BiomeSelectors.includeByKey((RegistryKey<Biome>[]) CFTPData.parseBiomes(CFTPData.creeperDark.hardcodedSpawnCondition.biomeList)),
                    SpawnGroup.MONSTER, CFTPEntities.CREEPER_DARK,
                    CFTPData.creeperDark.hardcodedSpawnCondition.spawnChance, CREEPER_GROUP_MIN, CREEPER_GROUP_MAX
            );

            SpawnRestriction.register(
                    CFTPEntities.CREEPER_DARK,
                    SpawnLocationTypes.ON_GROUND,
                    Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                    HostileEntity::canSpawnInDark
            );
        }

        if (CFTPData.creeperDirt.hardcodedSpawnCondition.spawnChance > 0) { // DIRT
            BiomeModifications.addSpawn(
                    BiomeSelectors.includeByKey((RegistryKey<Biome>[]) CFTPData.parseBiomes(CFTPData.creeperDirt.hardcodedSpawnCondition.biomeList)),
                    SpawnGroup.MONSTER, CFTPEntities.CREEPER_DIRT,
                    CFTPData.creeperDirt.hardcodedSpawnCondition.spawnChance, CREEPER_GROUP_MIN, CREEPER_GROUP_MAX
            );

            SpawnRestriction.register(
                    CFTPEntities.CREEPER_DIRT,
                    SpawnLocationTypes.ON_GROUND,
                    Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                    HostileEntity::canSpawnInDark
            );
        }

        if (CFTPData.creeperEarth.hardcodedSpawnCondition.spawnChance > 0) { // EARTH
            BiomeModifications.addSpawn(
                    BiomeSelectors.includeByKey((RegistryKey<Biome>[]) CFTPData.parseBiomes(CFTPData.creeperEarth.hardcodedSpawnCondition.biomeList)),
                    SpawnGroup.MONSTER, CFTPEntities.CREEPER_EARTH,
                    CFTPData.creeperEarth.hardcodedSpawnCondition.spawnChance, CREEPER_GROUP_MIN, CREEPER_GROUP_MAX
            );

            SpawnRestriction.register(
                    CFTPEntities.CREEPER_EARTH,
                    SpawnLocationTypes.ON_GROUND,
                    Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                    HostileEntity::canSpawnInDark
            );
        }

        if (CFTPData.creeperEnder.hardcodedSpawnCondition.spawnChance > 0){ // ENDER
            BiomeModifications.addSpawn(
                    BiomeSelectors.includeByKey((RegistryKey<Biome>[]) CFTPData.parseBiomes(CFTPData.creeperEnder.hardcodedSpawnCondition.biomeList)),
                    SpawnGroup.MONSTER, CFTPEntities.CREEPER_ENDER,
                    CFTPData.creeperEnder.hardcodedSpawnCondition.spawnChance, CREEPER_GROUP_MIN, CREEPER_GROUP_MAX
            );

            SpawnRestriction.register(
                    CFTPEntities.CREEPER_ENDER,
                    SpawnLocationTypes.ON_GROUND,
                    Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                    HostileEntity::canSpawnInDark
            );
        }

        if (CFTPData.creeperFire.hardcodedSpawnCondition.spawnChance > 0) { // FIRE
            BiomeModifications.addSpawn(
                    BiomeSelectors.includeByKey((RegistryKey<Biome>[]) CFTPData.parseBiomes(CFTPData.creeperFire.hardcodedSpawnCondition.biomeList)),
                    SpawnGroup.MONSTER, CFTPEntities.CREEPER_FIRE,
                    CFTPData.creeperFire.hardcodedSpawnCondition.spawnChance, CREEPER_GROUP_MIN, CREEPER_GROUP_MAX
            );

            SpawnRestriction.register(
                    CFTPEntities.CREEPER_FIRE,
                    SpawnLocationTypes.ON_GROUND,
                    Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                    HostileEntity::canSpawnInDark
            );
        }

        if (CFTPData.creeperFlip.hardcodedSpawnCondition.spawnChance > 0) { // FLIP
            BiomeModifications.addSpawn(
                    BiomeSelectors.includeByKey((RegistryKey<Biome>[]) CFTPData.parseBiomes(CFTPData.creeperFlip.hardcodedSpawnCondition.biomeList)),
                    SpawnGroup.MONSTER, CFTPEntities.CREEPER_FLIP,
                    CFTPData.creeperFlip.hardcodedSpawnCondition.spawnChance, CREEPER_GROUP_MIN, CREEPER_GROUP_MAX
            );

            SpawnRestriction.register(
                    CFTPEntities.CREEPER_FLIP,
                    SpawnLocationTypes.ON_GROUND,
                    Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                    HostileEntity::canSpawnInDark
            );
        }

        if (CFTPData.creeperFriendly.hardcodedSpawnCondition.spawnChance > 0) { // FRIENDLY
            BiomeModifications.addSpawn(
                    BiomeSelectors.includeByKey((RegistryKey<Biome>[]) CFTPData.parseBiomes(CFTPData.creeperFriendly.hardcodedSpawnCondition.biomeList)),
                    SpawnGroup.MONSTER, CFTPEntities.CREEPER_FRIENDLY,
                    CFTPData.creeperFriendly.hardcodedSpawnCondition.spawnChance, CREEPER_GROUP_MIN, CREEPER_GROUP_MAX
            );

            SpawnRestriction.register(
                    CFTPEntities.CREEPER_FRIENDLY,
                    SpawnLocationTypes.ON_GROUND,
                    Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                    HostileEntity::canSpawnInDark
            );
        }

        if (CFTPData.creeperGhost.hardcodedSpawnCondition.spawnChance > 0) { // GHOST
            BiomeModifications.addSpawn(
                    BiomeSelectors.includeByKey((RegistryKey<Biome>[]) CFTPData.parseBiomes(CFTPData.creeperGhost.hardcodedSpawnCondition.biomeList)),
                    SpawnGroup.MONSTER, CFTPEntities.CREEPER_GHOST,
                    CFTPData.creeperGhost.hardcodedSpawnCondition.spawnChance, CREEPER_GROUP_MIN, CREEPER_GROUP_MAX
            );

            SpawnRestriction.register(
                    CFTPEntities.CREEPER_GHOST,
                    SpawnLocationTypes.ON_GROUND,
                    Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                    HostileEntity::canSpawnInDark
            );
        }

        if (CFTPData.creeperGiant.hardcodedSpawnCondition.spawnChance > 0) { // GIANT
            BiomeModifications.addSpawn(
                    BiomeSelectors.includeByKey((RegistryKey<Biome>[]) CFTPData.parseBiomes(CFTPData.creeperGiant.hardcodedSpawnCondition.biomeList)),
                    SpawnGroup.MONSTER, CFTPEntities.CREEPER_GIANT,
                    CFTPData.creeperGiant.hardcodedSpawnCondition.spawnChance, CREEPER_GROUP_MIN, CREEPER_GROUP_MAX
            );

            SpawnRestriction.register(
                    CFTPEntities.CREEPER_GIANT,
                    SpawnLocationTypes.ON_GROUND,
                    Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                    HostileEntity::canSpawnInDark
            );
        }

        if (CFTPData.creeperGolden.hardcodedSpawnCondition.spawnChance > 0) { // GOLDEN
            BiomeModifications.addSpawn(
                    BiomeSelectors.includeByKey((RegistryKey<Biome>[]) CFTPData.parseBiomes(CFTPData.creeperGolden.hardcodedSpawnCondition.biomeList)),
                    SpawnGroup.MONSTER, CFTPEntities.CREEPER_GOLDEN,
                    CFTPData.creeperGolden.hardcodedSpawnCondition.spawnChance, CREEPER_GROUP_MIN, CREEPER_GROUP_MAX
            );

            SpawnRestriction.register(
                    CFTPEntities.CREEPER_GOLDEN,
                    SpawnLocationTypes.ON_GROUND,
                    Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                    HostileEntity::canSpawnInDark
            );
        }

        if (CFTPData.creeperHarvest.hardcodedSpawnCondition.spawnChance > 0) { // HARVEST
            BiomeModifications.addSpawn(
                    BiomeSelectors.includeByKey((RegistryKey<Biome>[]) CFTPData.parseBiomes(CFTPData.creeperHarvest.hardcodedSpawnCondition.biomeList)),
                    SpawnGroup.MONSTER, CFTPEntities.CREEPER_HARVEST,
                    CFTPData.creeperHarvest.hardcodedSpawnCondition.spawnChance, CREEPER_GROUP_MIN, CREEPER_GROUP_MAX
            );

            SpawnRestriction.register(
                    CFTPEntities.CREEPER_HARVEST,
                    SpawnLocationTypes.ON_GROUND,
                    Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                    HostileEntity::canSpawnInDark
            );
        }

        if (CFTPData.creeperHerobrine.hardcodedSpawnCondition.spawnChance > 0) { // HEROBRINE
            BiomeModifications.addSpawn(
                    BiomeSelectors.includeByKey((RegistryKey<Biome>[]) CFTPData.parseBiomes(CFTPData.creeperHerobrine.hardcodedSpawnCondition.biomeList)),
                    SpawnGroup.MONSTER, CFTPEntities.CREEPER_HEROBRINE,
                    CFTPData.creeperHerobrine.hardcodedSpawnCondition.spawnChance, CREEPER_GROUP_MIN, CREEPER_GROUP_MAX
            );

            SpawnRestriction.register(
                    CFTPEntities.CREEPER_HEROBRINE,
                    SpawnLocationTypes.ON_GROUND,
                    Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                    HostileEntity::canSpawnInDark
            );
        }

        if (CFTPData.creeperLava.hardcodedSpawnCondition.spawnChance > 0) { // LAVA
            BiomeModifications.addSpawn(
                    BiomeSelectors.includeByKey((RegistryKey<Biome>[]) CFTPData.parseBiomes(CFTPData.creeperLava.hardcodedSpawnCondition.biomeList)),
                    SpawnGroup.MONSTER, CFTPEntities.CREEPER_LAVA,
                    CFTPData.creeperLava.hardcodedSpawnCondition.spawnChance, CREEPER_GROUP_MIN, CREEPER_GROUP_MAX
            );

            SpawnRestriction.register(
                    CFTPEntities.CREEPER_LAVA,
                    SpawnLocationTypes.ON_GROUND,
                    Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                    HostileEntity::canSpawnInDark
            );
        }

        if (CFTPData.creeperLighting.hardcodedSpawnCondition.spawnChance > 0) { // LIGHTING
            BiomeModifications.addSpawn(
                    BiomeSelectors.includeByKey((RegistryKey<Biome>[]) CFTPData.parseBiomes(CFTPData.creeperLighting.hardcodedSpawnCondition.biomeList)),
                    SpawnGroup.MONSTER, CFTPEntities.CREEPER_LIGHTING,
                    CFTPData.creeperLighting.hardcodedSpawnCondition.spawnChance, CREEPER_GROUP_MIN, CREEPER_GROUP_MAX
            );

            SpawnRestriction.register(
                    CFTPEntities.CREEPER_LIGHTING,
                    SpawnLocationTypes.ON_GROUND,
                    Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                    HostileEntity::canSpawnInDark
            );
        }

        if (CFTPData.creeperNether.hardcodedSpawnCondition.spawnChance > 0) { // NETHER
            BiomeModifications.addSpawn(
                    BiomeSelectors.includeByKey((RegistryKey<Biome>[]) CFTPData.parseBiomes(CFTPData.creeperNether.hardcodedSpawnCondition.biomeList)),
                    SpawnGroup.MONSTER, CFTPEntities.CREEPER_NETHER,
                    CFTPData.creeperNether.hardcodedSpawnCondition.spawnChance, CREEPER_GROUP_MIN, CREEPER_GROUP_MAX
            );

            SpawnRestriction.register(
                    CFTPEntities.CREEPER_NETHER,
                    SpawnLocationTypes.ON_GROUND,
                    Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                    HostileEntity::canSpawnInDark
            );
        }

        if (CFTPData.creeperOceanid.hardcodedSpawnCondition.spawnChance > 0) { // OCEANID
            BiomeModifications.addSpawn(
                    BiomeSelectors.includeByKey((RegistryKey<Biome>[]) CFTPData.parseBiomes(CFTPData.creeperOceanid.hardcodedSpawnCondition.biomeList)),
                    SpawnGroup.MONSTER, CFTPEntities.CREEPER_OCEANID,
                    CFTPData.creeperOceanid.hardcodedSpawnCondition.spawnChance, CREEPER_GROUP_MIN, CREEPER_GROUP_MAX
            );

            SpawnRestriction.register(
                    CFTPEntities.CREEPER_OCEANID,
                    SpawnLocationTypes.ON_GROUND,
                    Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                    HostileEntity::canSpawnInDark
            );
        }

        if (CFTPData.creeperPiggy.hardcodedSpawnCondition.spawnChance > 0) { // PIGGY
            BiomeModifications.addSpawn(
                    BiomeSelectors.includeByKey((RegistryKey<Biome>[]) CFTPData.parseBiomes(CFTPData.creeperPiggy.hardcodedSpawnCondition.biomeList)),
                    SpawnGroup.MONSTER, CFTPEntities.CREEPER_PIGGY,
                    CFTPData.creeperPiggy.hardcodedSpawnCondition.spawnChance, CREEPER_GROUP_MIN, CREEPER_GROUP_MAX
            );

            SpawnRestriction.register(
                    CFTPEntities.CREEPER_PIGGY,
                    SpawnLocationTypes.ON_GROUND,
                    Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                    HostileEntity::canSpawnIgnoreLightLevel
            );
        }

        if (CFTPData.creeperSand.hardcodedSpawnCondition.spawnChance > 0) { // SAND
            BiomeModifications.addSpawn(
                    BiomeSelectors.includeByKey((RegistryKey<Biome>[]) CFTPData.parseBiomes(CFTPData.creeperSand.hardcodedSpawnCondition.biomeList)),
                    SpawnGroup.MONSTER, CFTPEntities.CREEPER_SAND,
                    CFTPData.creeperSand.hardcodedSpawnCondition.spawnChance, CREEPER_GROUP_MIN, CREEPER_GROUP_MAX
            );

            SpawnRestriction.register(
                    CFTPEntities.CREEPER_SAND,
                    SpawnLocationTypes.ON_GROUND,
                    Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                    HostileEntity::canSpawnInDark
            );
        }

        if (CFTPData.creeperSculk.hardcodedSpawnCondition.spawnChance > 0) { // SCULK
            BiomeModifications.addSpawn(
                    BiomeSelectors.includeByKey((RegistryKey<Biome>[]) CFTPData.parseBiomes(CFTPData.creeperSculk.hardcodedSpawnCondition.biomeList)),
                    SpawnGroup.MONSTER, CFTPEntities.CREEPER_SCULK,
                    CFTPData.creeperSculk.hardcodedSpawnCondition.spawnChance, CREEPER_GROUP_MIN, CREEPER_GROUP_MAX
            );

            SpawnRestriction.register(
                    CFTPEntities.CREEPER_SCULK,
                    SpawnLocationTypes.ON_GROUND,
                    Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                    HostileEntity::canSpawnInDark
            );
        }

        if (CFTPData.creeperSnow.hardcodedSpawnCondition.spawnChance > 0) { // SNOW
            BiomeModifications.addSpawn(
                    BiomeSelectors.includeByKey((RegistryKey<Biome>[]) CFTPData.parseBiomes(CFTPData.creeperSnow.hardcodedSpawnCondition.biomeList)),
                    SpawnGroup.MONSTER, CFTPEntities.CREEPER_SNOW,
                    CFTPData.creeperSnow.hardcodedSpawnCondition.spawnChance, CREEPER_GROUP_MIN, CREEPER_GROUP_MAX
            );

            SpawnRestriction.register(
                    CFTPEntities.CREEPER_SNOW,
                    SpawnLocationTypes.ON_GROUND,
                    Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                    HostileEntity::canSpawnInDark
            );
        }

        if (CFTPData.creeperSpectre.hardcodedSpawnCondition.spawnChance > 0) { // SPECTRE
            BiomeModifications.addSpawn(
                    BiomeSelectors.includeByKey((RegistryKey<Biome>[]) CFTPData.parseBiomes(CFTPData.creeperSpectre.hardcodedSpawnCondition.biomeList)),
                    SpawnGroup.MONSTER, CFTPEntities.CREEPER_SPECTRE,
                    CFTPData.creeperSpectre.hardcodedSpawnCondition.spawnChance, CREEPER_GROUP_MIN, CREEPER_GROUP_MAX
            );

            SpawnRestriction.register(
                    CFTPEntities.CREEPER_SPECTRE,
                    SpawnLocationTypes.ON_GROUND,
                    Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                    HostileEntity::canSpawnInDark
            );
        }

        if (CFTPData.creeperSwamp.hardcodedSpawnCondition.spawnChance > 0) { // SWAMP
            BiomeModifications.addSpawn(
                    BiomeSelectors.includeByKey((RegistryKey<Biome>[]) CFTPData.parseBiomes(CFTPData.creeperSwamp.hardcodedSpawnCondition.biomeList)),
                    SpawnGroup.MONSTER, CFTPEntities.CREEPER_SWAMP,
                    CFTPData.creeperSwamp.hardcodedSpawnCondition.spawnChance, CREEPER_GROUP_MIN, CREEPER_GROUP_MAX
            );

            SpawnRestriction.register(
                    CFTPEntities.CREEPER_SWAMP,
                    SpawnLocationTypes.ON_GROUND,
                    Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                    HostileEntity::canSpawnInDark
            );
        }

        if (CFTPData.creeperWater.hardcodedSpawnCondition.spawnChance > 0) { // WATER
            BiomeModifications.addSpawn(
                    BiomeSelectors.includeByKey((RegistryKey<Biome>[]) CFTPData.parseBiomes(CFTPData.creeperWater.hardcodedSpawnCondition.biomeList)),
                    SpawnGroup.MONSTER, CFTPEntities.CREEPER_WATER,
                    CFTPData.creeperWater.hardcodedSpawnCondition.spawnChance, CREEPER_GROUP_MIN, CREEPER_GROUP_MAX
            );

            SpawnRestriction.register(
                    CFTPEntities.CREEPER_WATER,
                    SpawnLocationTypes.ON_GROUND,
                    Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                    HostileEntity::canSpawnInDark
            );
        }

        if (CFTPData.creeperWind.hardcodedSpawnCondition.spawnChance > 0) { // WIND
            BiomeModifications.addSpawn(
                    BiomeSelectors.includeByKey((RegistryKey<Biome>[]) CFTPData.parseBiomes(CFTPData.creeperWind.hardcodedSpawnCondition.biomeList)),
                    SpawnGroup.MONSTER, CFTPEntities.CREEPER_WIND,
                    CFTPData.creeperWind.hardcodedSpawnCondition.spawnChance, CREEPER_GROUP_MIN, CREEPER_GROUP_MAX
            );

            SpawnRestriction.register(
                    CFTPEntities.CREEPER_WIND,
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
