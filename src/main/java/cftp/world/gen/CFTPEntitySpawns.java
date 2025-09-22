package cftp.world.gen;

import cftp.entity.CFTPEntities;
import cftp.entity.HorseFieryEntity;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.entity.*;
import net.minecraft.entity.mob.AmbientEntity;
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

    public static boolean canSpawnIgnoreLightLevel(
            EntityType<? extends LivingEntity> type,
            WorldAccess world,
            SpawnReason spawnReason,
            BlockPos pos, Random random
    ) {
        if (world.getDifficulty() != Difficulty.PEACEFUL) {
            BlockPos blockPos = pos.down();
            return SpawnReason.isAnySpawner(spawnReason) || world.getBlockState(blockPos).allowsSpawning(world, blockPos, type);
        } else {
            return false;
        }
    }

    @SuppressWarnings("unchecked")
    public static void register() {

        // OVERWORLD_DRY
        // BiomeKeys.DESERT, BiomeKeys.SAVANNA, BiomeKeys.SAVANNA_PLATEAU,

        // OVERWORLD_JUNGLE
        // BiomeKeys.JUNGLE, BiomeKeys.SPARSE_JUNGLE, BiomeKeys.BAMBOO_JUNGLE,

        // OVERWORLD_WATER
        // BiomeKeys.WARM_OCEAN, BiomeKeys.LUKEWARM_OCEAN,
        // BiomeKeys.DEEP_LUKEWARM_OCEAN, BiomeKeys.OCEAN,
        // BiomeKeys.DEEP_OCEAN, BiomeKeys.COLD_OCEAN,
        // BiomeKeys.DEEP_COLD_OCEAN,

        final RegistryKey<?>[] OVERWORLD = {
                BiomeKeys.PLAINS, BiomeKeys.SUNFLOWER_PLAINS,
                BiomeKeys.FOREST, BiomeKeys.FLOWER_FOREST,
                BiomeKeys.BIRCH_FOREST, BiomeKeys.DARK_FOREST,
                BiomeKeys.OLD_GROWTH_BIRCH_FOREST, BiomeKeys.TAIGA,
                BiomeKeys.WINDSWEPT_HILLS, BiomeKeys.WINDSWEPT_GRAVELLY_HILLS,
                BiomeKeys.WINDSWEPT_FOREST, BiomeKeys.WINDSWEPT_SAVANNA,
                BiomeKeys.BADLANDS, BiomeKeys.ERODED_BADLANDS,
                BiomeKeys.WOODED_BADLANDS, BiomeKeys.MEADOW,
                BiomeKeys.CHERRY_GROVE, BiomeKeys.GROVE,
                BiomeKeys.JAGGED_PEAKS, BiomeKeys.STONY_PEAKS,
                BiomeKeys.RIVER, BiomeKeys.BEACH,
                //
                BiomeKeys.DRIPSTONE_CAVES, BiomeKeys.LUSH_CAVES,
                BiomeKeys.DEEP_DARK, BiomeKeys.PALE_GARDEN,
                //
                BiomeKeys.SNOWY_PLAINS, BiomeKeys.ICE_SPIKES,
                BiomeKeys.SNOWY_TAIGA, BiomeKeys.SNOWY_SLOPES,
                BiomeKeys.FROZEN_PEAKS, BiomeKeys.FROZEN_RIVER,
                BiomeKeys.SNOWY_BEACH, BiomeKeys.FROZEN_OCEAN,
                BiomeKeys.DEEP_FROZEN_OCEAN,
                //
                BiomeKeys.SWAMP, BiomeKeys.MANGROVE_SWAMP,
        };

        final RegistryKey<?>[] OVERWORLD_TEMPERATE = {
                BiomeKeys.PLAINS, BiomeKeys.SUNFLOWER_PLAINS,
                BiomeKeys.FOREST, BiomeKeys.FLOWER_FOREST,
                BiomeKeys.BIRCH_FOREST, BiomeKeys.DARK_FOREST,
                BiomeKeys.OLD_GROWTH_BIRCH_FOREST, BiomeKeys.TAIGA,
                BiomeKeys.WINDSWEPT_HILLS, BiomeKeys.WINDSWEPT_GRAVELLY_HILLS,
                BiomeKeys.WINDSWEPT_FOREST, BiomeKeys.WINDSWEPT_SAVANNA,
                BiomeKeys.BADLANDS, BiomeKeys.ERODED_BADLANDS,
                BiomeKeys.WOODED_BADLANDS, BiomeKeys.MEADOW,
                BiomeKeys.CHERRY_GROVE, BiomeKeys.GROVE,
                BiomeKeys.JAGGED_PEAKS, BiomeKeys.STONY_PEAKS,
                BiomeKeys.RIVER, BiomeKeys.BEACH,
                //
                BiomeKeys.DRIPSTONE_CAVES, BiomeKeys.LUSH_CAVES,
                BiomeKeys.DEEP_DARK, BiomeKeys.PALE_GARDEN,
        };

        final RegistryKey<?>[] OVERWORLD_SNOW = {
                BiomeKeys.SNOWY_PLAINS, BiomeKeys.ICE_SPIKES,
                BiomeKeys.SNOWY_TAIGA, BiomeKeys.SNOWY_SLOPES,
                BiomeKeys.FROZEN_PEAKS, BiomeKeys.FROZEN_RIVER,
                BiomeKeys.SNOWY_BEACH, BiomeKeys.FROZEN_OCEAN,
                BiomeKeys.DEEP_FROZEN_OCEAN,
        };

        final RegistryKey<?>[] OVERWORLD_SWAMP = {
                BiomeKeys.SWAMP, BiomeKeys.MANGROVE_SWAMP,
        };

        final RegistryKey<?>[] OVERWORLD_JUNGLE = {
                BiomeKeys.JUNGLE, BiomeKeys.BAMBOO_JUNGLE,
        };

        final RegistryKey<?>[] NETHER = {
                BiomeKeys.NETHER_WASTES, BiomeKeys.WARPED_FOREST,
                BiomeKeys.CRIMSON_FOREST, BiomeKeys.SOUL_SAND_VALLEY,
                BiomeKeys.BASALT_DELTAS,
        };

        final RegistryKey<?>[] OVERWORLD_NETHER = {
                BiomeKeys.PLAINS, BiomeKeys.SUNFLOWER_PLAINS,
                BiomeKeys.SNOWY_PLAINS, BiomeKeys.ICE_SPIKES,
                BiomeKeys.DESERT, BiomeKeys.SWAMP,
                BiomeKeys.MANGROVE_SWAMP, BiomeKeys.FOREST,
                BiomeKeys.FLOWER_FOREST, BiomeKeys.BIRCH_FOREST,
                BiomeKeys.DARK_FOREST, BiomeKeys.PALE_GARDEN,
                BiomeKeys.OLD_GROWTH_BIRCH_FOREST, BiomeKeys.TAIGA,
                BiomeKeys.SNOWY_TAIGA, BiomeKeys.SAVANNA,
                BiomeKeys.SAVANNA_PLATEAU, BiomeKeys.WINDSWEPT_HILLS,
                BiomeKeys.WINDSWEPT_GRAVELLY_HILLS, BiomeKeys.WINDSWEPT_FOREST,
                BiomeKeys.WINDSWEPT_SAVANNA, BiomeKeys.JUNGLE,
                BiomeKeys.SPARSE_JUNGLE, BiomeKeys.BAMBOO_JUNGLE,
                BiomeKeys.BADLANDS, BiomeKeys.ERODED_BADLANDS,
                BiomeKeys.WOODED_BADLANDS, BiomeKeys.MEADOW,
                BiomeKeys.CHERRY_GROVE, BiomeKeys.GROVE,
                BiomeKeys.SNOWY_SLOPES, BiomeKeys.FROZEN_PEAKS,
                BiomeKeys.JAGGED_PEAKS, BiomeKeys.STONY_PEAKS,
                BiomeKeys.RIVER, BiomeKeys.FROZEN_RIVER,
                BiomeKeys.BEACH, BiomeKeys.SNOWY_BEACH,
                BiomeKeys.WARM_OCEAN, BiomeKeys.LUKEWARM_OCEAN,
                BiomeKeys.DEEP_LUKEWARM_OCEAN, BiomeKeys.OCEAN,
                BiomeKeys.DEEP_OCEAN, BiomeKeys.COLD_OCEAN,
                BiomeKeys.DEEP_COLD_OCEAN, BiomeKeys.FROZEN_OCEAN,
                BiomeKeys.DEEP_FROZEN_OCEAN, BiomeKeys.MUSHROOM_FIELDS,
                BiomeKeys.DRIPSTONE_CAVES, BiomeKeys.LUSH_CAVES,
                BiomeKeys.DEEP_DARK, BiomeKeys.NETHER_WASTES,
                BiomeKeys.WARPED_FOREST, BiomeKeys.CRIMSON_FOREST,
                BiomeKeys.SOUL_SAND_VALLEY, BiomeKeys.BASALT_DELTAS,
        };

        final RegistryKey<?>[] END = {
                BiomeKeys.THE_END, BiomeKeys.END_HIGHLANDS,
                BiomeKeys.END_MIDLANDS, BiomeKeys.SMALL_END_ISLANDS,
                BiomeKeys.END_BARRENS,
        };

        final RegistryKey<?>[] ALL_BIOMES = {
                BiomeKeys.PLAINS, BiomeKeys.SUNFLOWER_PLAINS,
                BiomeKeys.SNOWY_PLAINS, BiomeKeys.ICE_SPIKES,
                BiomeKeys.DESERT, BiomeKeys.SWAMP,
                BiomeKeys.MANGROVE_SWAMP, BiomeKeys.FOREST,
                BiomeKeys.FLOWER_FOREST, BiomeKeys.BIRCH_FOREST,
                BiomeKeys.DARK_FOREST, BiomeKeys.PALE_GARDEN,
                BiomeKeys.OLD_GROWTH_BIRCH_FOREST, BiomeKeys.TAIGA,
                BiomeKeys.SNOWY_TAIGA, BiomeKeys.SAVANNA,
                BiomeKeys.SAVANNA_PLATEAU, BiomeKeys.WINDSWEPT_HILLS,
                BiomeKeys.WINDSWEPT_GRAVELLY_HILLS, BiomeKeys.WINDSWEPT_FOREST,
                BiomeKeys.WINDSWEPT_SAVANNA, BiomeKeys.JUNGLE,
                BiomeKeys.SPARSE_JUNGLE, BiomeKeys.BAMBOO_JUNGLE,
                BiomeKeys.BADLANDS, BiomeKeys.ERODED_BADLANDS,
                BiomeKeys.WOODED_BADLANDS, BiomeKeys.MEADOW,
                BiomeKeys.CHERRY_GROVE, BiomeKeys.GROVE,
                BiomeKeys.SNOWY_SLOPES, BiomeKeys.FROZEN_PEAKS,
                BiomeKeys.JAGGED_PEAKS, BiomeKeys.STONY_PEAKS,
                BiomeKeys.RIVER, BiomeKeys.FROZEN_RIVER,
                BiomeKeys.BEACH, BiomeKeys.SNOWY_BEACH,
                BiomeKeys.WARM_OCEAN, BiomeKeys.LUKEWARM_OCEAN,
                BiomeKeys.DEEP_LUKEWARM_OCEAN, BiomeKeys.OCEAN,
                BiomeKeys.DEEP_OCEAN, BiomeKeys.COLD_OCEAN,
                BiomeKeys.DEEP_COLD_OCEAN, BiomeKeys.FROZEN_OCEAN,
                BiomeKeys.DEEP_FROZEN_OCEAN, BiomeKeys.MUSHROOM_FIELDS,
                BiomeKeys.DRIPSTONE_CAVES, BiomeKeys.LUSH_CAVES,
                BiomeKeys.DEEP_DARK, BiomeKeys.NETHER_WASTES,
                BiomeKeys.WARPED_FOREST, BiomeKeys.CRIMSON_FOREST,
                BiomeKeys.SOUL_SAND_VALLEY, BiomeKeys.BASALT_DELTAS,
                BiomeKeys.THE_END, BiomeKeys.END_HIGHLANDS,
                BiomeKeys.END_MIDLANDS, BiomeKeys.SMALL_END_ISLANDS,
                BiomeKeys.END_BARRENS
        };

        /// RegistryKey<?>[] SELECTED_BIOMES = {
        ///         BiomeKeys.PLAINS, BiomeKeys.SUNFLOWER_PLAINS, BiomeKeys.DESERT, BiomeKeys.SWAMP,
        ///         BiomeKeys.SWAMP, BiomeKeys.MANGROVE_SWAMP, BiomeKeys.FOREST, BiomeKeys.MEADOW
        /// };


        final int CREEPER_GROUP_WEIGHT = 20;
        final int CREEPER_GROUP_MIN = 2;
        final int CREEPER_GROUP_MAX = 7;

        final int ZOMBIE_GROUP_WEIGHT = 30;
        final int ZOMBIE_GROUP_MIN = 5;
        final int ZOMBIE_GROUP_MAX = 12;


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
                    BiomeSelectors.includeByKey((RegistryKey<Biome>[]) OVERWORLD),
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
                    BiomeSelectors.includeByKey((RegistryKey<Biome>[]) ALL_BIOMES),
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
                    BiomeSelectors.includeByKey((RegistryKey<Biome>[]) OVERWORLD_NETHER),
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
                    BiomeSelectors.includeByKey((RegistryKey<Biome>[]) OVERWORLD),
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
                    BiomeSelectors.includeByKey((RegistryKey<Biome>[]) OVERWORLD_TEMPERATE),
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
                    BiomeSelectors.includeByKey((RegistryKey<Biome>[]) OVERWORLD_TEMPERATE),
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
                    BiomeSelectors.includeByKey((RegistryKey<Biome>[]) OVERWORLD_TEMPERATE),
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

        { // LAVA
            BiomeModifications.addSpawn(
                    BiomeSelectors.includeByKey((RegistryKey<Biome>[]) NETHER),
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
                    BiomeSelectors.includeByKey((RegistryKey<Biome>[]) ALL_BIOMES),
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
                    BiomeSelectors.includeByKey((RegistryKey<Biome>[]) NETHER),
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
                    BiomeSelectors.includeByKey((RegistryKey<Biome>[]) ALL_BIOMES),
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
                    BiomeSelectors.includeByKey(BiomeKeys.MUSHROOM_FIELDS),
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
                    BiomeSelectors.includeByKey((RegistryKey<Biome>[]) OVERWORLD_SNOW),
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
                    BiomeSelectors.includeByKey((RegistryKey<Biome>[]) OVERWORLD_SWAMP),
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
                    BiomeSelectors.includeByKey((RegistryKey<Biome>[]) OVERWORLD),
                    SpawnGroup.MONSTER, CFTPEntities.CREEPER_DARK,
                    CREEPER_GROUP_WEIGHT, CREEPER_GROUP_MIN, CREEPER_GROUP_MAX
            );

            SpawnRestriction.register(
                    CFTPEntities.CREEPER_DARK,
                    SpawnLocationTypes.ON_GROUND,
                    Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                    HostileEntity::canSpawnIgnoreLightLevel
            );
        }

        { // BALLISTIC
            BiomeModifications.addSpawn(
                    BiomeSelectors.includeByKey((RegistryKey<Biome>[]) OVERWORLD),
                    SpawnGroup.MONSTER, CFTPEntities.CREEPER_BALLISTIC,
                    CREEPER_GROUP_WEIGHT, CREEPER_GROUP_MIN, CREEPER_GROUP_MAX
            );

            SpawnRestriction.register(
                    CFTPEntities.CREEPER_BALLISTIC,
                    SpawnLocationTypes.ON_GROUND,
                    Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                    HostileEntity::canSpawnIgnoreLightLevel
            );
        }

        { // GOLDEN
            BiomeModifications.addSpawn(
                    BiomeSelectors.includeByKey((RegistryKey<Biome>[]) ALL_BIOMES),
                    SpawnGroup.MONSTER, CFTPEntities.CREEPER_GOLDEN,
                    CREEPER_GROUP_WEIGHT, CREEPER_GROUP_MIN, CREEPER_GROUP_MAX
            );

            SpawnRestriction.register(
                    CFTPEntities.CREEPER_GOLDEN,
                    SpawnLocationTypes.ON_GROUND,
                    Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                    HostileEntity::canSpawnIgnoreLightLevel
            );
        }

        { // SPIDER YELLOW
            BiomeModifications.addSpawn(
                    BiomeSelectors.includeByKey((RegistryKey<Biome>[]) OVERWORLD_SWAMP),
                    SpawnGroup.MONSTER, CFTPEntities.SPIDER_YELLOW,
                    CREEPER_GROUP_WEIGHT, CREEPER_GROUP_MIN, CREEPER_GROUP_MAX
            );

            SpawnRestriction.register(
                    CFTPEntities.SPIDER_YELLOW,
                    SpawnLocationTypes.ON_GROUND,
                    Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                    HostileEntity::canSpawnIgnoreLightLevel
            );
        }

        { // SPIDER BLUE
            BiomeModifications.addSpawn(
                    BiomeSelectors.includeByKey((RegistryKey<Biome>[]) OVERWORLD_JUNGLE),
                    SpawnGroup.MONSTER, CFTPEntities.SPIDER_BLUE,
                    CREEPER_GROUP_WEIGHT, CREEPER_GROUP_MIN, CREEPER_GROUP_MAX
            );

            SpawnRestriction.register(
                    CFTPEntities.SPIDER_BLUE,
                    SpawnLocationTypes.ON_GROUND,
                    Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                    HostileEntity::canSpawnIgnoreLightLevel
            );
        }

        // SpawnLocationTypes.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES

        { // HORSE FIERY
            BiomeModifications.addSpawn(
                    BiomeSelectors.includeByKey((RegistryKey<Biome>[]) NETHER),
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

            { // PHANTOM
                BiomeModifications.addSpawn(
                        BiomeSelectors.includeByKey((RegistryKey<Biome>[]) END),
                        SpawnGroup.MONSTER, EntityType.PHANTOM,
                        20, 1, 4
                );

                // From net.minecraft.entity.SpawnRestriction.java
                // register(EntityType.PHANTOM, SpawnLocationTypes.UNRESTRICTED, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MobEntity::canMobSpawn);

                //SpawnRestriction.register(
                //        EntityType.PHANTOM,
                //        SpawnLocationTypes.UNRESTRICTED,
                //        Heightmap.Type.MOTION_BLOCKING,
                //        CFTPEntitySpawns::canSpawnIgnoreLightLevel
                //);

                // make them spawn in end dimension // DONE
                // also remove golden and flip creepers from end
                // add a chance for thrower creeper
                // add an end_creeper variant -> a new end block would be cool like null-fluid or something
                // -> a block that somewhat looks like end-portal but it's a block and its hard collect?
                // also to many of them spawns there now.

            }

        }

    }

}
