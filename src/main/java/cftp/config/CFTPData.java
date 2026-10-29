package cftp.config;

import cftp.CFTP;
import net.minecraft.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;

import java.util.Arrays;

public class CFTPData {

    public static class SpawnCondition {
        public int spawnChance;
        public String[] biomeList;

        public SpawnCondition(
                int spawnChance,
                String[] biomeList
        ) {
            this.spawnChance = spawnChance;
            this.biomeList = biomeList;
        }
    }

    public static class CreeperData {

        public SpawnCondition hardcodedSpawnCondition;
        public float power;
        public int health;
        public float movementSpeed;
        public int followRange;

        public CreeperData(
                SpawnCondition hardcodedSpawnCondition,
                float power,
                int health,
                float movementSpeed,
                int followRange
        ) {
            this.hardcodedSpawnCondition = hardcodedSpawnCondition;
            this.health = health;
            this.movementSpeed = movementSpeed;
            this.followRange = followRange;
            this.power = power;
        }
    }

    public static class JohnData {

        public SpawnCondition hardcodedSpawnCondition;
        public int health;
        public float movementSpeed;
        public int followRange;
        public int attackDamage;
        public int armor;

        public JohnData(
                SpawnCondition hardcodedSpawnCondition,
                int health,
                float movementSpeed,
                int followRange,
                int attackDamage,
                int armor
        ) {
            this.hardcodedSpawnCondition = hardcodedSpawnCondition;
            this.health = health;
            this.movementSpeed = movementSpeed;
            this.followRange = followRange;
            this.attackDamage = attackDamage;
            this.armor = armor;
        }
    }

    public static class CreeperDarkData extends CreeperData {

        public boolean isSpawnInLightButEasier;

        public CreeperDarkData(
                SpawnCondition hardcodedSpawnCondition,
                float power,
                int health,
                float movementSpeed,
                int followRange,
                boolean isSpawnInLightButEasier
        ) {
            super(hardcodedSpawnCondition, power, health, movementSpeed, followRange);
            this.isSpawnInLightButEasier = isSpawnInLightButEasier;
        }
    }

    public static class CreeperGhostData extends CreeperData {

        public boolean isUponDeathEnabled;
        public boolean isShearAndGildResistant;

        public CreeperGhostData(
                SpawnCondition hardcodedSpawnCondition,
                float power,
                int health,
                float movementSpeed,
                int followRange,
                boolean isUponDeathEnabled,
                boolean isShearAndGildResistant
        ) {
            super(hardcodedSpawnCondition, power, health, movementSpeed, followRange);
            this.isUponDeathEnabled = isUponDeathEnabled;
            this.isShearAndGildResistant = isShearAndGildResistant;
        }
    }

    public static int configVersion;

    public static boolean isCreeperShearingEnabled;
    public static boolean isCreeperRefusingEnabled;
    public static boolean isCreeperGildingEnabled;

    public static int elementalCreeperGroupSpawnMin;
    public static int elementalCreeperGroupSpawnMax;

    public static int johnGroupSpawnMin;
    public static int johnGroupSpawnMax;

    public static CreeperData creeperAmalgam;
    public static CreeperData creeperAmethyst;
    public static CreeperData creeperBallistic;
    public static CreeperData creeperBrewer;
    public static CreeperData creeperBridger;
    public static CreeperData creeperCookie;
    public static CreeperDarkData creeperDark;
    public static CreeperData creeperDirt;
    public static CreeperData creeperEarth;
    public static CreeperData creeperEnder;
    public static CreeperData creeperFire;
    public static CreeperData creeperFlip;
    public static CreeperData creeperFriendly;
    public static CreeperGhostData creeperGhost;
    public static CreeperData creeperGiant;
    public static CreeperData creeperGolden;
    public static CreeperData creeperHarvest;
    public static CreeperData creeperHerobrine;
    public static CreeperData creeperLava;
    public static CreeperData creeperLighting;
    public static CreeperData creeperNether;
    public static CreeperData creeperOceanid;
    public static CreeperData creeperPiggy;
    public static CreeperData creeperSand;
    public static CreeperData creeperSculk;
    public static CreeperData creeperSnow;
    public static CreeperData creeperSpectre;
    public static CreeperData creeperSwamp;
    public static CreeperData creeperWater;
    public static CreeperData creeperWind;

    public static JohnData john;

    @SafeVarargs
    private static String[] biomes(RegistryKey<Biome>... keys) {
        return Arrays.stream(keys)
                .map(k -> k.getValue().toString())
                .toArray(String[]::new);
    }

    public static void initializeDefault() {

        String[] biomesAmalgam      = biomes(BiomeKeys.MEADOW);
        String[] biomesAmethyst     = biomes(BiomeKeys.LUSH_CAVES);
        String[] biomesBallistic    = biomes(BiomeKeys.JAGGED_PEAKS, BiomeKeys.STONY_PEAKS, BiomeKeys.DARK_FOREST);
        String[] biomesBrewer       = {};
        String[] biomesBridger      = {};
        String[] biomesCookie       = biomes(BiomeKeys.JUNGLE, BiomeKeys.SPARSE_JUNGLE, BiomeKeys.BAMBOO_JUNGLE);
        String[] biomesDark         = biomes(BiomeKeys.DESERT, BiomeKeys.SAVANNA, BiomeKeys.SAVANNA_PLATEAU, BiomeKeys.WINDSWEPT_SAVANNA, BiomeKeys.BADLANDS, BiomeKeys.ERODED_BADLANDS, BiomeKeys.WOODED_BADLANDS);
        String[] biomesDirt         = biomes(BiomeKeys.DRIPSTONE_CAVES);
        String[] biomesEarth        = biomes(BiomeKeys.PLAINS, BiomeKeys.SUNFLOWER_PLAINS, BiomeKeys.SNOWY_PLAINS);
        String[] biomesEnder        = biomes(BiomeKeys.JUNGLE, BiomeKeys.SPARSE_JUNGLE, BiomeKeys.BAMBOO_JUNGLE, BiomeKeys.THE_END, BiomeKeys.END_HIGHLANDS, BiomeKeys.END_MIDLANDS, BiomeKeys.SMALL_END_ISLANDS, BiomeKeys.END_BARRENS, BiomeKeys.WARPED_FOREST, BiomeKeys.CRIMSON_FOREST);
        String[] biomesFire         = biomes(BiomeKeys.PLAINS, BiomeKeys.SUNFLOWER_PLAINS, BiomeKeys.DESERT, BiomeKeys.SAVANNA, BiomeKeys.SAVANNA_PLATEAU, BiomeKeys.WINDSWEPT_SAVANNA, BiomeKeys.BADLANDS, BiomeKeys.ERODED_BADLANDS, BiomeKeys.WOODED_BADLANDS);
        String[] biomesFlip         = biomes(BiomeKeys.WARPED_FOREST, BiomeKeys.CRIMSON_FOREST);
        String[] biomesFriendly     = biomes(BiomeKeys.MUSHROOM_FIELDS);
        String[] biomesGhost        = biomes(BiomeKeys.SWAMP, BiomeKeys.SOUL_SAND_VALLEY);
        String[] biomesGiant        = biomes(BiomeKeys.PLAINS);
        String[] biomesGolden       = {};
        String[] biomesHarvest      = {};
        String[] biomesHerobrine    = {};
        String[] biomesLava         = biomes(BiomeKeys.NETHER_WASTES, BiomeKeys.BASALT_DELTAS);
        String[] biomesLighting     = biomes(BiomeKeys.WINDSWEPT_HILLS, BiomeKeys.WINDSWEPT_GRAVELLY_HILLS, BiomeKeys.WINDSWEPT_FOREST, BiomeKeys.WINDSWEPT_SAVANNA);
        String[] biomesNether       = biomes(BiomeKeys.NETHER_WASTES, BiomeKeys.BASALT_DELTAS, BiomeKeys.WARPED_FOREST, BiomeKeys.CRIMSON_FOREST);
        String[] biomesOceanid      = {};
        String[] biomesPiggy        = {};
        String[] biomesSand         = {};
        String[] biomesSculk        = biomes(BiomeKeys.DEEP_DARK);
        String[] biomesSnow         = biomes(BiomeKeys.SNOWY_PLAINS, BiomeKeys.SNOWY_TAIGA, BiomeKeys.ICE_SPIKES, BiomeKeys.FROZEN_OCEAN, BiomeKeys.DEEP_FROZEN_OCEAN, BiomeKeys.SNOWY_SLOPES, BiomeKeys.FROZEN_PEAKS, BiomeKeys.FROZEN_RIVER);
        String[] biomesSpectre      = {};
        String[] biomesSwamp        = biomes(BiomeKeys.SWAMP, BiomeKeys.MANGROVE_SWAMP);
        String[] biomesWater        = biomes(BiomeKeys.PLAINS, BiomeKeys.SUNFLOWER_PLAINS, BiomeKeys.SNOWY_PLAINS);
        String[] biomesWind         = biomes(BiomeKeys.PLAINS, BiomeKeys.SUNFLOWER_PLAINS, BiomeKeys.SNOWY_PLAINS, BiomeKeys.DESERT, BiomeKeys.SAVANNA, BiomeKeys.SAVANNA_PLATEAU, BiomeKeys.WINDSWEPT_SAVANNA, BiomeKeys.BADLANDS, BiomeKeys.ERODED_BADLANDS, BiomeKeys.WOODED_BADLANDS);
        String[] biomesJohn         = biomes(BiomeKeys.PLAINS, BiomeKeys.SUNFLOWER_PLAINS, BiomeKeys.SNOWY_PLAINS, BiomeKeys.ICE_SPIKES, BiomeKeys.DESERT, BiomeKeys.SWAMP, BiomeKeys.MANGROVE_SWAMP, BiomeKeys.FOREST, BiomeKeys.FLOWER_FOREST, BiomeKeys.BIRCH_FOREST, BiomeKeys.DARK_FOREST, BiomeKeys.PALE_GARDEN, BiomeKeys.OLD_GROWTH_BIRCH_FOREST, BiomeKeys.TAIGA, BiomeKeys.SNOWY_TAIGA, BiomeKeys.SAVANNA, BiomeKeys.SAVANNA_PLATEAU, BiomeKeys.WINDSWEPT_HILLS, BiomeKeys.WINDSWEPT_GRAVELLY_HILLS, BiomeKeys.WINDSWEPT_FOREST, BiomeKeys.WINDSWEPT_SAVANNA, BiomeKeys.JUNGLE, BiomeKeys.SPARSE_JUNGLE, BiomeKeys.BAMBOO_JUNGLE, BiomeKeys.BADLANDS, BiomeKeys.ERODED_BADLANDS, BiomeKeys.WOODED_BADLANDS, BiomeKeys.MEADOW, BiomeKeys.CHERRY_GROVE, BiomeKeys.GROVE, BiomeKeys.SNOWY_SLOPES, BiomeKeys.FROZEN_PEAKS, BiomeKeys.JAGGED_PEAKS, BiomeKeys.STONY_PEAKS, BiomeKeys.RIVER, BiomeKeys.FROZEN_RIVER, BiomeKeys.BEACH, BiomeKeys.SNOWY_BEACH, BiomeKeys.WARM_OCEAN, BiomeKeys.LUKEWARM_OCEAN, BiomeKeys.DEEP_LUKEWARM_OCEAN, BiomeKeys.OCEAN, BiomeKeys.DEEP_OCEAN, BiomeKeys.COLD_OCEAN, BiomeKeys.DEEP_COLD_OCEAN, BiomeKeys.FROZEN_OCEAN, BiomeKeys.DEEP_FROZEN_OCEAN, BiomeKeys.MUSHROOM_FIELDS, BiomeKeys.DRIPSTONE_CAVES, BiomeKeys.LUSH_CAVES, BiomeKeys.DEEP_DARK, BiomeKeys.NETHER_WASTES, BiomeKeys.WARPED_FOREST, BiomeKeys.CRIMSON_FOREST, BiomeKeys.SOUL_SAND_VALLEY, BiomeKeys.BASALT_DELTAS, BiomeKeys.THE_END, BiomeKeys.END_HIGHLANDS, BiomeKeys.END_MIDLANDS, BiomeKeys.SMALL_END_ISLANDS, BiomeKeys.END_BARRENS);

        configVersion = CFTP.currentConfigVersion;

        isCreeperShearingEnabled = true;
        isCreeperRefusingEnabled = false;
        isCreeperGildingEnabled = true;

        int elementalCreeperGroupSpawnMin = 1;
        int elementalCreeperGroupSpawnMax = 3;

        int johnGroupSpawnMin = 2;
        int johnGroupSpawnMax = 3;

        creeperAmalgam = new CreeperData(
                new SpawnCondition(20, biomesAmalgam),
                5f, 20, 0.25f, 20
        );

        creeperAmethyst = new CreeperData(
                new SpawnCondition(25, biomesAmethyst),
                7f, 20, 0.25f, 20
        );

        creeperBallistic = new CreeperData(
                new SpawnCondition(20, biomesBallistic),
                5f, 20, 0.25f, 20
        );

        creeperBrewer = new CreeperData(
                new SpawnCondition(0, biomesBrewer),
                5f, 20, 0.25f, 20
        );

        creeperBridger = new CreeperData(
                new SpawnCondition(0, biomesBridger),
                5f, 20, 0.25f, 20
        );

        creeperCookie = new CreeperData(
                new SpawnCondition(25, biomesCookie),
                5f, 13, 0.25f, 20
        );

        creeperDark = new CreeperDarkData(
                new SpawnCondition(35, biomesDark),
                12f, 14, 0.25f, 20,
                false
        );

        creeperDirt = new CreeperData(
                new SpawnCondition(25, biomesDirt),
                8f, 20, 0.25f, 20
        );

        creeperEarth = new CreeperData(
                new SpawnCondition(30, biomesEarth),
                5f, 20, 0.25f, 20
        );

        creeperEnder = new CreeperData(
                new SpawnCondition(30, biomesEnder),
                5f, 20, 0.25f, 20
        );

        creeperFire = new CreeperData(
                new SpawnCondition(30, biomesFire),
                9f, 20, 0.25f, 20
        );

        creeperFlip = new CreeperData(
                new SpawnCondition(25, biomesFlip),
                6f, 20, 0.25f, 20
        );

        creeperFriendly = new CreeperData(
                new SpawnCondition(25, biomesFriendly),
                3f, 14, 0.25f, 20
        );

        creeperGhost = new CreeperGhostData(
                new SpawnCondition(30, biomesGhost),
                2f, 14, 0.25f, 15,
                true, true
        );

        creeperGiant = new CreeperData(
                new SpawnCondition(20, biomesGiant),
                11f, 32, 0.19f, 15
        );

        creeperGolden = new CreeperData(
                new SpawnCondition(0, biomesGolden),
                3f, 24, 0.25f, 20
        );

        creeperHarvest = new CreeperData(
                new SpawnCondition(0, biomesHarvest),
                5f, 20, 0.25f, 20
        );

        creeperHerobrine = new CreeperData(
                new SpawnCondition(0, biomesHerobrine),
                5f, 20, 0.25f, 26
        );

        creeperLava = new CreeperData(
                new SpawnCondition(30, biomesLava),
                5f, 24, 0.25f, 20
        );

        creeperLighting = new CreeperData(
                new SpawnCondition(30, biomesLighting),
                1.5f, 20, 0.45f, 16
        );

        creeperNether = new CreeperData(
                new SpawnCondition(30, biomesNether),
                5f, 26, 0.25f, 26
        );

        creeperOceanid = new CreeperData(
                new SpawnCondition(0, biomesOceanid),
                5f, 20, 0.25f, 20
        );
        creeperPiggy = new CreeperData(
                new SpawnCondition(0, biomesPiggy),
                5f, 20, 0.25f, 20
        );

        creeperSand = new CreeperData(
                new SpawnCondition(0, biomesSand),
                5f, 12, 0.25f, 16
        );

        creeperSculk = new CreeperData(
                new SpawnCondition(30, biomesSculk),
                8f, 20, 0.25f, 20
        );

        creeperSnow = new CreeperData(
                new SpawnCondition(30, biomesSnow),
                5f, 20, 0.25f, 20
        );

        creeperSpectre = new CreeperData(
                new SpawnCondition(0, biomesSpectre),
                5f, 14, 0.25f, 12
        );

        creeperSwamp = new CreeperData(
                new SpawnCondition(30, biomesSwamp),
                5f, 20, 0.25f, 16
        );

        creeperWater = new CreeperData(
                new SpawnCondition(30, biomesWater),
                5f, 20, 0.25f, 16
        );

        creeperWind = new CreeperData(
                new SpawnCondition(30, biomesWind),
                3.27f, 20, 0.25f, 16
        );

        john = new JohnData(
                new SpawnCondition(30, biomesJohn),
                20, 0.37f, 30, 4, 2
        );
    }

}
