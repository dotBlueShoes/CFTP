package cftp.entity;

import cftp.CFTP;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
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

    public static final EntityType<CreeperLavaEntity> CREEPER_LAVA = Registry.register(
            Registries.ENTITY_TYPE,
            RegistryKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(CFTP.MOD_ID, "creeper_lava")),
            EntityType.Builder.create(CreeperLavaEntity::new, SpawnGroup.MONSTER)
                    .makeFireImmune()
                    .dimensions(0.6F, 1.7F)
                    .build(RegistryKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(CFTP.MOD_ID, "creeper_lava")))
    );

    public static void register(){
        FabricDefaultAttributeRegistry.register(CREEPER_LIGHTING, CreeperLightingEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(CREEPER_COOKIE, CreeperCookieEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(CREEPER_WATER, CreeperCookieEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(CREEPER_FIRE, CreeperCookieEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(CREEPER_DIRT, CreeperCookieEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(CREEPER_LAVA, CreeperCookieEntity.createAttributes());
    }

}
