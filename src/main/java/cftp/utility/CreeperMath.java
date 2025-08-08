package cftp.utility;


import cftp.entity.creeper.*;
import cftp.registries.CFTPItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

public class CreeperMath {

    final public static Item[] CREEPER_EGGS = {
            Items.CREEPER_SPAWN_EGG,
            CFTPItems.CREEPER_COOKIE_SPAWN_EGG,
            CFTPItems.CREEPER_DIRT_SPAWN_EGG,
            CFTPItems.CREEPER_EARTH_SPAWN_EGG,
            CFTPItems.CREEPER_ENDER_SPAWN_EGG,
            CFTPItems.CREEPER_FIRE_SPAWN_EGG,
            CFTPItems.CREEPER_GHOST_SPAWN_EGG,
            CFTPItems.CREEPER_LAVA_SPAWN_EGG,
            CFTPItems.CREEPER_LIGHTING_SPAWN_EGG,
            CFTPItems.CREEPER_NETHER_SPAWN_EGG,
            CFTPItems.CREEPER_WATER_SPAWN_EGG,
            CFTPItems.CREEPER_WIND_SPAWN_EGG,
    };

    public enum CREEPER_TYPE {
        VANILLA     (0),
        COOKIE      (1),
        DIRT        (2),
        EARTH       (3),
        ENDER       (4),
        FIRE        (5),
        GHOST       (6),
        LAVA        (7),
        LIGHTING    (8),
        NETHER      (9),
        WATER       (10),
        WIND        (11);

        private final int type;

        CREEPER_TYPE(int type) {
            this.type = type;
        }

        public int getType() {
            return type;
        }
    }

    final public static BlockState[] EARTH_BLOCKS = {
            Blocks.DIRT.getDefaultState(),
            Blocks.STONE.getDefaultState(),
            Blocks.GRANITE.getDefaultState(),
            Blocks.DIORITE.getDefaultState(),
            Blocks.ANDESITE.getDefaultState(),
            Blocks.GRAVEL.getDefaultState(),
            Blocks.CLAY.getDefaultState(),
    };

    final public static BlockState[] NETHER_BLOCKS = {
            Blocks.NETHERRACK.getDefaultState(),
            Blocks.BASALT.getDefaultState(),
            Blocks.SOUL_SAND.getDefaultState(),
            Blocks.SOUL_SOIL.getDefaultState(),
            Blocks.GRAVEL.getDefaultState(),
            Blocks.BLACKSTONE.getDefaultState(),
            Blocks.GLOWSTONE.getDefaultState(),
            // More valuable blocks.
            Blocks.NETHER_GOLD_ORE.getDefaultState(),
            Blocks.NETHER_QUARTZ_ORE.getDefaultState(),
            // It has higher chance of producing NETHER_RACK and SOUL_SAND
            Blocks.NETHERRACK.getDefaultState(),
            Blocks.SOUL_SAND.getDefaultState(),
    }; // + 4

    //public enum CREEPERS {
    //    COOKIE      (CreeperCookieEntity.class)     ,
    //    DIRT        (CreeperDirtEntity.class)       ,
    //    EARTH       (CreeperEarthEntity.class)      ,
    //    ENDER       (CreeperEnderEntity.class)      ,
    //    FIRE        (CreeperFireEntity.class)       ,
    //    GHOST       (CreeperGhostEntity.class)      ,
    //    LAVA        (CreeperLavaEntity.class)       ,
    //    LIGHTING    (CreeperLightingEntity.class)   ,
    //    NETHER      (CreeperNetherEntity.class)     ,
    //    WATER       (CreeperWaterEntity.class)      ,
    //    WIND        (CreeperWindEntity.class)       ;
    //
    //    private final Class<? extends HostileEntity> entityClass;
    //
    //    CREEPERS(Class<? extends HostileEntity> entityClass) {
    //        this.entityClass = entityClass;
    //    }
    //
    //    public Class<? extends HostileEntity> getEntityClass() {
    //        return entityClass;
    //    }
    //}

}
