package cftp.world.gen;

import cftp.entity.CFTPEntities;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.entity.EntityType;
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


        final int CREEPER_GROUP_WEIGHT = 20;
        final int CREEPER_GROUP_MIN = 1;
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

        BiomeModifications.addSpawn(
            BiomeSelectors.includeByKey( (RegistryKey<Biome>[]) ALL_BIOMES),
            SpawnGroup.MONSTER, CFTPEntities.CREEPER_COOKIE,
                CREEPER_GROUP_WEIGHT, CREEPER_GROUP_MIN, CREEPER_GROUP_MAX
        );

        BiomeModifications.addSpawn(
                BiomeSelectors.includeByKey( (RegistryKey<Biome>[]) ALL_BIOMES),
                SpawnGroup.MONSTER, CFTPEntities.CREEPER_ENDER,
                CREEPER_GROUP_WEIGHT, CREEPER_GROUP_MIN, CREEPER_GROUP_MAX
        );

        BiomeModifications.addSpawn(
            BiomeSelectors.includeByKey( (RegistryKey<Biome>[]) ALL_BIOMES),
            SpawnGroup.MONSTER, CFTPEntities.CREEPER_FIRE,
                CREEPER_GROUP_WEIGHT, CREEPER_GROUP_MIN, CREEPER_GROUP_MAX
        );

        BiomeModifications.addSpawn(
                BiomeSelectors.includeByKey( (RegistryKey<Biome>[]) ALL_BIOMES),
                SpawnGroup.MONSTER, CFTPEntities.CREEPER_WIND,
                CREEPER_GROUP_WEIGHT, CREEPER_GROUP_MIN, CREEPER_GROUP_MAX
        );

        BiomeModifications.addSpawn(
            BiomeSelectors.includeByKey( (RegistryKey<Biome>[]) ALL_BIOMES),
            SpawnGroup.MONSTER, CFTPEntities.CREEPER_LIGHTING,
                CREEPER_GROUP_WEIGHT, CREEPER_GROUP_MIN, CREEPER_GROUP_MAX
        );

        BiomeModifications.addSpawn(
            BiomeSelectors.includeByKey( (RegistryKey<Biome>[]) ALL_BIOMES),
            SpawnGroup.MONSTER, CFTPEntities.CREEPER_WATER,
                CREEPER_GROUP_WEIGHT, CREEPER_GROUP_MIN, CREEPER_GROUP_MAX
        );

        BiomeModifications.addSpawn(
                BiomeSelectors.includeByKey( (RegistryKey<Biome>[]) ALL_BIOMES),
                SpawnGroup.MONSTER, CFTPEntities.CREEPER_EARTH,
                CREEPER_GROUP_WEIGHT, CREEPER_GROUP_MIN, CREEPER_GROUP_MAX
        );

        //BiomeModifications.addSpawn(
        //    BiomeSelectors.includeByKey( (RegistryKey<Biome>[]) ALL_BIOMES),
        //    SpawnGroup.MONSTER, CFTPEntities.CREEPER_DIRT,
        //        CREEPER_GROUP_WEIGHT, CREEPER_GROUP_MIN, CREEPER_GROUP_MAX
        //);

        BiomeModifications.addSpawn(
            BiomeSelectors.includeByKey( (RegistryKey<Biome>[]) ALL_BIOMES),
            SpawnGroup.MONSTER, CFTPEntities.CREEPER_LAVA,
                CREEPER_GROUP_WEIGHT, CREEPER_GROUP_MIN, CREEPER_GROUP_MAX
        );

        BiomeModifications.addSpawn(
                BiomeSelectors.includeByKey( (RegistryKey<Biome>[]) ALL_BIOMES),
                SpawnGroup.MONSTER, CFTPEntities.CREEPER_GHOST,
                CREEPER_GROUP_WEIGHT, CREEPER_GROUP_MIN, CREEPER_GROUP_MAX
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
