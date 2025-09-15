package cftp.entity;

import cftp.CFTP;
import cftp.entity.charge.EarthChargeEntity;
import cftp.entity.charge.WaterChargeEntity;
import cftp.entity.creeper.*;
import cftp.entity.spider.SpiderBlueEntity;
import cftp.entity.spider.SpiderYellowEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.passive.StriderEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class CFTPEntities {

    public static final EntityType<CreeperCookieEntity> CREEPER_COOKIE = Registry.register(
            Registries.ENTITY_TYPE,
            RegistryKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(CFTP.MOD_ID, "creeper_cookie")),
            EntityType.Builder.create(CreeperCookieEntity::new, SpawnGroup.MONSTER)
                    .dimensions(0.6F, 1.7F)
                    .build(RegistryKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(CFTP.MOD_ID, "creeper_cookie")))
    );

    public static final EntityType<CreeperEnderEntity> CREEPER_ENDER = Registry.register(
            Registries.ENTITY_TYPE,
            RegistryKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(CFTP.MOD_ID, "creeper_ender")),
            EntityType.Builder.create(CreeperEnderEntity::new, SpawnGroup.MONSTER)
                    .dimensions(0.6F, 1.7F)
                    .build(RegistryKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(CFTP.MOD_ID, "creeper_ender")))
    );

    public static final EntityType<CreeperWindEntity> CREEPER_WIND = Registry.register(
            Registries.ENTITY_TYPE,
            RegistryKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(CFTP.MOD_ID, "creeper_wind")),
            EntityType.Builder.create(CreeperWindEntity::new, SpawnGroup.MONSTER)
                    .dimensions(0.6F, 1.7F)
                    .build(RegistryKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(CFTP.MOD_ID, "creeper_wind")))
    );

    public static final EntityType<CreeperLightingEntity> CREEPER_LIGHTING = Registry.register(
            Registries.ENTITY_TYPE,
            RegistryKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(CFTP.MOD_ID, "creeper_lighting")),
            EntityType.Builder.create(CreeperLightingEntity::new, SpawnGroup.MONSTER)
                    .dimensions(0.6F, 1.7F)
                    .build(RegistryKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(CFTP.MOD_ID, "creeper_lighting")))
    );

    public static final EntityType<CreeperWaterEntity> CREEPER_WATER = Registry.register(
            Registries.ENTITY_TYPE,
            RegistryKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(CFTP.MOD_ID, "creeper_water")),
            EntityType.Builder.create(CreeperWaterEntity::new, SpawnGroup.MONSTER)
                    .dimensions(0.6F, 1.7F)
                    .build(RegistryKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(CFTP.MOD_ID, "creeper_water")))
    );

    public static final EntityType<CreeperFireEntity> CREEPER_FIRE = Registry.register(
            Registries.ENTITY_TYPE,
            RegistryKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(CFTP.MOD_ID, "creeper_fire")),
            EntityType.Builder.create(CreeperFireEntity::new, SpawnGroup.MONSTER)
                    .makeFireImmune()
                    .dimensions(0.6F, 1.7F)
                    .build(RegistryKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(CFTP.MOD_ID, "creeper_fire")))
    );

    public static final EntityType<CreeperDirtEntity> CREEPER_DIRT = Registry.register(
            Registries.ENTITY_TYPE,
            RegistryKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(CFTP.MOD_ID, "creeper_dirt")),
            EntityType.Builder.create(CreeperDirtEntity::new, SpawnGroup.MONSTER)
                    .dimensions(0.6F, 1.7F)
                    .build(RegistryKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(CFTP.MOD_ID, "creeper_dirt")))
    );

    //StriderEntity

    public static final EntityType<CreeperEarthEntity> CREEPER_EARTH = Registry.register(
            Registries.ENTITY_TYPE,
            RegistryKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(CFTP.MOD_ID, "creeper_earth")),
            EntityType.Builder.create(CreeperEarthEntity::new, SpawnGroup.MONSTER)
                    .dimensions(0.6F, 1.7F)
                    .build(RegistryKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(CFTP.MOD_ID, "creeper_earth")))
    );

    public static final EntityType<CreeperLavaEntity> CREEPER_LAVA = Registry.register(
            Registries.ENTITY_TYPE,
            RegistryKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(CFTP.MOD_ID, "creeper_lava")),
            EntityType.Builder.create(CreeperLavaEntity::new, SpawnGroup.MONSTER)
                    .makeFireImmune()
                    .dimensions(0.6F, 1.7F)
                    .build(RegistryKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(CFTP.MOD_ID, "creeper_lava")))
    );

    public static final EntityType<CreeperGhostEntity> CREEPER_GHOST = Registry.register(
            Registries.ENTITY_TYPE,
            RegistryKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(CFTP.MOD_ID, "creeper_ghost")),
            EntityType.Builder.create(CreeperGhostEntity::new, SpawnGroup.MONSTER)
                    .makeFireImmune()
                    .dimensions(0.6F, 1.7F)
                    .build(RegistryKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(CFTP.MOD_ID, "creeper_ghost")))
    );

    public static final EntityType<CreeperNetherEntity> CREEPER_NETHER = Registry.register(
            Registries.ENTITY_TYPE,
            RegistryKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(CFTP.MOD_ID, "creeper_nether")),
            EntityType.Builder.create(CreeperNetherEntity::new, SpawnGroup.MONSTER)
                    .dimensions(0.6F, 1.7F)
                    .build(RegistryKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(CFTP.MOD_ID, "creeper_nether")))
    );

    public static final EntityType<CreeperFlipEntity> CREEPER_FLIP = Registry.register(
            Registries.ENTITY_TYPE,
            RegistryKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(CFTP.MOD_ID, "creeper_flip")),
            EntityType.Builder.create(CreeperFlipEntity::new, SpawnGroup.MONSTER)
                    .dimensions(0.6F, 1.7F)
                    .build(RegistryKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(CFTP.MOD_ID, "creeper_flip")))
    );

    public static final EntityType<CreeperFriendlyEntity> CREEPER_FRIENDLY = Registry.register(
            Registries.ENTITY_TYPE,
            RegistryKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(CFTP.MOD_ID, "creeper_friendly")),
            EntityType.Builder.create(CreeperFriendlyEntity::new, SpawnGroup.MONSTER)
                    .dimensions(0.6F, 1.7F)
                    .build(RegistryKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(CFTP.MOD_ID, "creeper_friendly")))
    );

    public static final EntityType<CreeperSnowEntity> CREEPER_SNOW = Registry.register(
            Registries.ENTITY_TYPE,
            RegistryKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(CFTP.MOD_ID, "creeper_snow")),
            EntityType.Builder.create(CreeperSnowEntity::new, SpawnGroup.MONSTER)
                    .dimensions(0.6F, 1.7F)
                    .build(RegistryKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(CFTP.MOD_ID, "creeper_snow")))
    );

    public static final EntityType<CreeperSwampEntity> CREEPER_SWAMP = Registry.register(
            Registries.ENTITY_TYPE,
            RegistryKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(CFTP.MOD_ID, "creeper_swamp")),
            EntityType.Builder.create(CreeperSwampEntity::new, SpawnGroup.MONSTER)
                    .dimensions(0.6F, 1.7F)
                    .build(RegistryKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(CFTP.MOD_ID, "creeper_swamp")))
    );

    public static final EntityType<CreeperDarkEntity> CREEPER_DARK = Registry.register(
            Registries.ENTITY_TYPE,
            RegistryKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(CFTP.MOD_ID, "creeper_dark")),
            EntityType.Builder.create(CreeperDarkEntity::new, SpawnGroup.MONSTER)
                    .dimensions(0.6F, 1.7F)
                    .build(RegistryKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(CFTP.MOD_ID, "creeper_dark")))
    );

    public static final EntityType<CreeperBallisticEntity> CREEPER_BALLISTIC = Registry.register(
            Registries.ENTITY_TYPE,
            RegistryKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(CFTP.MOD_ID, "creeper_ballistic")),
            EntityType.Builder.create(CreeperBallisticEntity::new, SpawnGroup.MONSTER)
                    .dimensions(0.6F, 1.7F)
                    .build(RegistryKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(CFTP.MOD_ID, "creeper_ballistic")))
    );

    public static final EntityType<CreeperGoldenEntity> CREEPER_GOLDEN = Registry.register(
            Registries.ENTITY_TYPE,
            RegistryKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(CFTP.MOD_ID, "creeper_golden")),
            EntityType.Builder.create(CreeperGoldenEntity::new, SpawnGroup.MONSTER)
                    .dimensions(0.6F, 1.7F)
                    .build(RegistryKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(CFTP.MOD_ID, "creeper_golden")))
    );

    public static final EntityType<SpiderYellowEntity> SPIDER_YELLOW = Registry.register(
            Registries.ENTITY_TYPE,
            RegistryKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(CFTP.MOD_ID, "spider_yellow")),
            EntityType.Builder.create(SpiderYellowEntity::new, SpawnGroup.MONSTER)
                    .dimensions(1.4F, 0.9F)
                    .eyeHeight(0.65F)
                    .passengerAttachments(0.765F)
                    .maxTrackingRange(8)
                    .build(RegistryKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(CFTP.MOD_ID, "spider_yellow")))
    );

    public static final EntityType<SpiderBlueEntity> SPIDER_BLUE = Registry.register(
            Registries.ENTITY_TYPE,
            RegistryKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(CFTP.MOD_ID, "spider_blue")),
            EntityType.Builder.create(SpiderBlueEntity::new, SpawnGroup.MONSTER)
                    .dimensions(1.4F, 0.9F)
                    .eyeHeight(0.65F)
                    .passengerAttachments(0.765F)
                    .maxTrackingRange(8)
                    .build(RegistryKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(CFTP.MOD_ID, "spider_blue")))
    );

    public static final EntityType<SlendermanEntity> SLENDERMAN = Registry.register(
            Registries.ENTITY_TYPE,
            RegistryKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(CFTP.MOD_ID, "slenderman")),
            EntityType.Builder.create(SlendermanEntity::new, SpawnGroup.MONSTER)
                    .dimensions(0.6F, 2.9F)
                    .eyeHeight(2.55F)
                    .passengerAttachments(2.80625F)
                    .maxTrackingRange(8)
                    .build(RegistryKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(CFTP.MOD_ID, "slenderman")))
    );

    public static final EntityType<HorseFieryEntity> HORSE_FIERY = Registry.register(
            Registries.ENTITY_TYPE,
            RegistryKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(CFTP.MOD_ID, "horse_fiery")),
            EntityType.Builder.create(HorseFieryEntity::new, SpawnGroup.MONSTER)
                    .dimensions(1.3964844F, 1.6F)
                    .eyeHeight(1.52F)
                    .passengerAttachments(1.44375F)
                    .maxTrackingRange(10)
                    .makeFireImmune()
                    .build(RegistryKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(CFTP.MOD_ID, "horse_fiery")))
    );

    public static final EntityType<WaterChargeEntity> WATER_CHARGE = Registry.register(
            Registries.ENTITY_TYPE,
            RegistryKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(CFTP.MOD_ID, "water_charge")),
            EntityType.Builder.<WaterChargeEntity>create(WaterChargeEntity::new, SpawnGroup.MISC)
                    .dropsNothing()
                    .dimensions(0.3125F, 0.3125F)
                    .eyeHeight(0.0F)
                    .maxTrackingRange(4)
                    .trackingTickInterval(10)
                    .build(RegistryKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(CFTP.MOD_ID, "water_charge")))
    );

    public static final EntityType<EarthChargeEntity> EARTH_CHARGE = Registry.register(
            Registries.ENTITY_TYPE,
            RegistryKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(CFTP.MOD_ID, "earth_charge")),
            EntityType.Builder.<EarthChargeEntity>create(EarthChargeEntity::new, SpawnGroup.MISC)
                    .dropsNothing()
                    .dimensions(0.3125F, 0.3125F)
                    .eyeHeight(0.0F)
                    .maxTrackingRange(4)
                    .trackingTickInterval(10)
                    .build(RegistryKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(CFTP.MOD_ID, "earth_charge")))
    );

    //public static final EntityType<WaterChargeEntity> WATER_CHARGE = Registry.register(
    //        Registries.ENTITY_TYPE,
    //        Identifier.of(CFTP.MOD_ID, "packed_snowball"),
    //        FabricEntityTypeBuilder.<WaterChargeEntity>create(SpawnGroup.MISC, WaterChargeEntity::new)
    //                .dimensions(EntityDimensions.fixed(0.25F, 0.25F)) // dimensions in Minecraft units of the projectile
    //                .trackRangeBlocks(4).trackedUpdateRate(10) // necessary for all thrown projectiles (as it prevents it from breaking, lol)
    //                .build() // VERY IMPORTANT DONT DELETE FOR THE LOVE OF GOD PSLSSSSSS
    //);

    public static void register(){
        FabricDefaultAttributeRegistry.register(CREEPER_LIGHTING, CreeperLightingEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(CREEPER_ENDER, CreeperEnderEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(CREEPER_COOKIE, CreeperCookieEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(CREEPER_WATER, CreeperWaterEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(CREEPER_EARTH, CreeperEarthEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(CREEPER_FIRE, CreeperFireEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(CREEPER_DIRT, CreeperDirtEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(CREEPER_LAVA, CreeperLavaEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(CREEPER_WIND, CreeperWindEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(CREEPER_GHOST, CreeperGhostEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(CREEPER_NETHER, CreeperNetherEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(CREEPER_FLIP, CreeperFlipEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(CREEPER_FRIENDLY, CreeperFriendlyEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(CREEPER_SNOW, CreeperSnowEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(CREEPER_SWAMP, CreeperSwampEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(CREEPER_DARK, CreeperDarkEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(CREEPER_BALLISTIC, CreeperBallisticEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(CREEPER_GOLDEN, CreeperGoldenEntity.createAttributes());
        //
        FabricDefaultAttributeRegistry.register(SPIDER_YELLOW, SpiderYellowEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(SPIDER_BLUE, SpiderBlueEntity.createAttributes());
        //
        FabricDefaultAttributeRegistry.register(SLENDERMAN, SlendermanEntity.createAttributes());
        //
        FabricDefaultAttributeRegistry.register(HORSE_FIERY, HorseFieryEntity.createAttributes());

    }

}
