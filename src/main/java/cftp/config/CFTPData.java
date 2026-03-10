package cftp.config;

public class CFTPData {

    public static class CreeperData {

        public int hardcodedSpawnChance;
        public float power;
        public int health;
        public float movementSpeed;
        public int followRange;

        public CreeperData(
                int hardcodedSpawnChance,
                float power,
                int health,
                float movementSpeed,
                int followRange
        ) {
            this.hardcodedSpawnChance = hardcodedSpawnChance;
            this.health = health;
            this.movementSpeed = movementSpeed;
            this.followRange = followRange;
            this.power = power;
        }
    }

    public static class CreeperDarkData extends CreeperData {

        public boolean isSpawnInLightButEasier;

        public CreeperDarkData(
                int hardcodedSpawnChance,
                float power,
                int health,
                float movementSpeed,
                int followRange,
                boolean isSpawnInLightButEasier
        ) {
            super(hardcodedSpawnChance, power, health, movementSpeed, followRange);
            this.isSpawnInLightButEasier = isSpawnInLightButEasier;
        }
    }

    public static class CreeperGhostData extends CreeperData {

        public boolean isUponDeathEnabled;
        public boolean isShearAndGildResistant;

        public CreeperGhostData(
                int hardcodedSpawnChance,
                float power,
                int health,
                float movementSpeed,
                int followRange,
                boolean isUponDeathEnabled,
                boolean isShearAndGildResistant
        ) {
            super(hardcodedSpawnChance, power, health, movementSpeed, followRange);
            this.isUponDeathEnabled = isUponDeathEnabled;
            this.isShearAndGildResistant = isShearAndGildResistant;
        }
    }

    public static String configVersion;

    public static boolean isCreeperShearingEnabled;
    public static boolean isCreeperRefusingEnabled;
    public static boolean isCreeperGildingEnabled;

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

    public static void initializeDefault() {
        configVersion = "1";

        isCreeperShearingEnabled = true;
        isCreeperRefusingEnabled = false;
        isCreeperGildingEnabled = true;

        creeperAmalgam      = new CreeperData(25, 5f, 20, 0.25f, 20);
        creeperAmethyst     = new CreeperData(25, 7f, 20, 0.25f, 20);
        creeperBallistic    = new CreeperData(25, 5f, 20, 0.25f, 20);
        creeperBrewer       = new CreeperData(0, 5f, 20, 0.25f, 20);
        creeperBridger      = new CreeperData(0, 5f, 20, 0.25f, 20);
        creeperCookie       = new CreeperData(25, 5f, 20, 0.25f, 20);
        creeperDark         = new CreeperDarkData(25, 12f, 20, 0.25f, 20, false);
        creeperDirt         = new CreeperData(25, 5f, 20, 0.25f, 20);
        creeperEarth        = new CreeperData(25, 5f, 20, 0.25f, 20);
        creeperEnder        = new CreeperData(25, 5f, 20, 0.25f, 20);
        creeperFire         = new CreeperData(25, 9f, 20, 0.25f, 20);
        creeperFlip         = new CreeperData(25, 6f, 20, 0.25f, 20);
        creeperFriendly     = new CreeperData(25, 3f, 20, 0.25f, 20);
        creeperGhost        = new CreeperGhostData(25, 3f, 14, 0.30f, 20, true, true);
        creeperGiant        = new CreeperData(0, 5f, 20, 0.25f, 20);
        creeperGolden       = new CreeperData(0, 3f, 20, 0.25f, 20);
        creeperHarvest      = new CreeperData(0, 5f, 20, 0.25f, 20);
        creeperHerobrine    = new CreeperData(0, 5f, 20, 0.25f, 20);
        creeperLava         = new CreeperData(25, 5f, 20, 0.25f, 20);
        creeperLighting     = new CreeperData(25, 2f, 20, 0.45f, 20);
        creeperNether       = new CreeperData(25, 5f, 20, 0.25f, 20);
        creeperOceanid      = new CreeperData(0, 5f, 20, 0.25f, 20);
        creeperPiggy        = new CreeperData(0, 5f, 20, 0.25f, 20);
        creeperSand         = new CreeperData(0, 5f, 20, 0.25f, 20);
        creeperSculk        = new CreeperData(25, 8f, 20, 0.25f, 20);
        creeperSnow         = new CreeperData(25, 5f, 20, 0.25f, 20);
        creeperSpectre      = new CreeperData(0, 5f, 20, 0.25f, 20);
        creeperSwamp        = new CreeperData(25, 5f, 20, 0.25f, 20);
        creeperWater        = new CreeperData(25, 5f, 20, 0.25f, 20);
        creeperWind         = new CreeperData(25, 3.27f, 20, 0.25f, 20);
    }

    public static void checkConfigVersion() {
        //todo
    }

}
