package cftp.config;

public class CFTPData {

    boolean IS_ENABLE_GHOST_CREEPER_ON_DEATH = true;

    { // Spawn condition.
        String LOCATION = "";
        int CHANCE = 0;
        int MIN = 0;
        int MAX = 0;
    }

    { // Each creeper.
        // NOPE boolean IS_SPAWNING_ENABLED = true;
        // SpawnCondition SPAWN_CONDITION = null;

        int HEALTH = 0;
        double MOVEMENT = 0;
        int FOLLOW_RANGE = 0;
        double EXPLOSION_DIAMETER = 0;
    }

}
