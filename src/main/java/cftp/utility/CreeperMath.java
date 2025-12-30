package cftp.utility;

import cftp.entity.base.CreeperElementalEntity;
import cftp.registries.CFTPItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.loot.context.LootWorldContext;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.world.explosion.ExplosionBehavior;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
            CFTPItems.CREEPER_FLIP_SPAWN_EGG,
            CFTPItems.CREEPER_FRIENDLY_SPAWN_EGG,
            CFTPItems.CREEPER_SNOW_SPAWN_EGG,
            CFTPItems.CREEPER_SWAMP_SPAWN_EGG,
            CFTPItems.CREEPER_DARK_SPAWN_EGG,
            CFTPItems.CREEPER_BALLISTIC_SPAWN_EGG,
            CFTPItems.CREEPER_GOLDEN_SPAWN_EGG,
            CFTPItems.CREEPER_BRIDGER_SPAWN_EGG,
            CFTPItems.CREEPER_PIGGY_SPAWN_EGG,
            CFTPItems.CREEPER_AMALGAM_SPAWN_EGG,
            CFTPItems.CREEPER_AMETHYST_SPAWN_EGG,
            CFTPItems.CREEPER_BREWER_SPAWN_EGG,
            CFTPItems.CREEPER_GIANT_SPAWN_EGG,
            CFTPItems.CREEPER_HARVEST_SPAWN_EGG,
            CFTPItems.CREEPER_OCEANID_SPAWN_EGG,
            CFTPItems.CREEPER_SAND_SPAWN_EGG,
            CFTPItems.CREEPER_SCULK_SPAWN_EGG,
    };

    public enum CREEPER_TYPE {
        VANILLA     ( 0),
        COOKIE      ( 1),
        DIRT        ( 2),
        EARTH       ( 3),
        ENDER       ( 4),
        FIRE        ( 5),
        GHOST       ( 6),
        LAVA        ( 7),
        LIGHTING    ( 8),
        NETHER      ( 9),
        WATER       (10),
        WIND        (11),
        FLIP        (12),
        FRIENDLY    (13),
        SNOW        (14),
        SWAMP       (15),
        DARK        (16),
        BALLISTIC   (17),
        GOLDEN      (18),
        BRIDGER     (19),
        PIGGY       (20),
        AMALGAM     (21),
        AMETHYST    (22),
        BREWER      (23),
        GIANT       (24),
        HARVEST     (25),
        OCEANID     (26),
        SAND        (27),
        SCULK       (28),
        ;

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
    }; // 7

    final public static BlockState[] SNOW_BLOCKS = {
            Blocks.SNOW_BLOCK.getDefaultState(),
            Blocks.POWDER_SNOW.getDefaultState(),
            Blocks.ICE.getDefaultState(),
            Blocks.PACKED_ICE.getDefaultState(),
            // It has higher chance of producing SNOW_BLOCK and POWDER_SNOW
            Blocks.SNOW_BLOCK.getDefaultState(),
            Blocks.POWDER_SNOW.getDefaultState(),
    }; // 6

    final public static BlockState[] LAVA_BLOCKS = {
            Blocks.LAVA.getDefaultState(),
            Blocks.MAGMA_BLOCK.getDefaultState(),
            // It has higher chance of producing MAGMA_BLOCK
            Blocks.LAVA.getDefaultState(),
    }; // 3

    final public static BlockState[] SWAMP_BLOCKS = {
            Blocks.GRASS_BLOCK.getDefaultState(),
            Blocks.DIRT.getDefaultState(),
            Blocks.COBWEB.getDefaultState(),
            Blocks.MUD.getDefaultState(),
            Blocks.CLAY.getDefaultState(),
            Blocks.OAK_LEAVES.getDefaultState(),
            Blocks.MANGROVE_LEAVES.getDefaultState(),
            Blocks.MANGROVE_ROOTS.getDefaultState(),
            Blocks.WATER.getDefaultState(),
            // It has higher chance of producing GRASS_BLOCK
            Blocks.GRASS_BLOCK.getDefaultState(),
    }; // 8

    final public static BlockState[] NETHER_BLOCKS = {
            Blocks.NETHERRACK.getDefaultState(),
            Blocks.BASALT.getDefaultState(),
            Blocks.SOUL_SAND.getDefaultState(),
            Blocks.SOUL_SOIL.getDefaultState(),
            Blocks.GRAVEL.getDefaultState(),
            Blocks.BLACKSTONE.getDefaultState(),
            // More valuable blocks. RARE
            Blocks.GLOWSTONE.getDefaultState(),
            Blocks.NETHER_GOLD_ORE.getDefaultState(),
            Blocks.NETHER_QUARTZ_ORE.getDefaultState(),
            // It has higher chance of producing NETHER_RACK and SOUL_SAND
            Blocks.NETHERRACK.getDefaultState(),
            Blocks.SOUL_SAND.getDefaultState(),
            // double not RARE
            Blocks.NETHERRACK.getDefaultState(),
            Blocks.BASALT.getDefaultState(),
            Blocks.SOUL_SAND.getDefaultState(),
            Blocks.SOUL_SOIL.getDefaultState(),
            Blocks.GRAVEL.getDefaultState(),
            Blocks.BLACKSTONE.getDefaultState(),
    }; // 17

    public static void createWaterWalkingParticle(CreeperElementalEntity creeper) {
        Random random = creeper.getRandom();
        creeper.getWorld().addParticle(
                ParticleTypes.DOLPHIN,
                creeper.getParticleX(0.5),
                creeper.getRandomBodyY(),
                creeper.getParticleZ(0.5),
                (random.nextDouble() - 0.5) * 2.0,
                -random.nextDouble(),
                (random.nextDouble() - 0.5) * 2.0
        );
    }

    public static ExplosionBehavior noDestroyExplosionBehaviour = new ExplosionBehavior() {
        @Override
        public boolean canDestroyBlock(Explosion explosion, BlockView world, BlockPos pos, BlockState state, float power) {
            return false; // No blocks destroyed
        }
    };

    public static List<ItemStack> GetBlockLootTable(ServerWorld serverWorld, Entity entity, Block block) {
        var blockState = block.getDefaultState();

        Optional<RegistryKey<LootTable>> registryKey = block.getLootTableKey();

        if (registryKey.isPresent()) {

            Optional<LootTable> optional = serverWorld.getServer()
                    .getReloadableRegistries()
                    .createRegistryLookup()
                    .getOptionalEntry(registryKey.get())
                    .map(RegistryEntry::value);

            LootWorldContext lootWorldContext = new LootWorldContext.Builder(serverWorld)
                    .add(LootContextParameters.ORIGIN, entity.getPos())
                    .add(LootContextParameters.THIS_ENTITY, entity)
                    .add(LootContextParameters.BLOCK_STATE, blockState)
                    .build(LootContextTypes.BLOCK_USE);

            if (optional.isPresent()) {
                return optional.get().generateLoot(lootWorldContext);
            }
        }

        // Return an empty list. No null checking just size checking.
        return new ArrayList<>();
    }

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
