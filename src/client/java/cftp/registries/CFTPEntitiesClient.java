package cftp.registries;

import cftp.entity.FieryProjectileEntityRenderer;
import cftp.entity.HorseFieryRenderer;
import cftp.entity.SlendermanRenderer;
import cftp.entity.SurphurCloudEntityRenderer;
import cftp.entity.charge.*;
import cftp.entity.creeper.*;
import cftp.entity.spider.SpiderBlueRenderer;
import cftp.entity.spider.SpiderYellowRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class CFTPEntitiesClient {

    public static void register() {
        EntityRendererRegistry.register(CFTPEntities.CREEPER_LIGHTING, CreeperLightingEntityRenderer::new);
        EntityRendererRegistry.register(CFTPEntities.CREEPER_COOKIE, CreeperCookieEntityRenderer::new);
        EntityRendererRegistry.register(CFTPEntities.CREEPER_ENDER, CreeperEnderEntityRenderer::new);
        EntityRendererRegistry.register(CFTPEntities.CREEPER_WATER, CreeperWaterEntityRenderer::new);
        EntityRendererRegistry.register(CFTPEntities.CREEPER_EARTH, CreeperEarthEntityRenderer::new);
        EntityRendererRegistry.register(CFTPEntities.CREEPER_FIRE, CreeperFireEntityRenderer::new);
        EntityRendererRegistry.register(CFTPEntities.CREEPER_LAVA, CreeperLavaEntityRenderer::new);
        EntityRendererRegistry.register(CFTPEntities.CREEPER_DIRT, CreeperDirtEntityRenderer::new);
        EntityRendererRegistry.register(CFTPEntities.CREEPER_WIND, CreeperWindEntityRenderer::new);
        EntityRendererRegistry.register(CFTPEntities.CREEPER_GHOST, CreeperGhostRenderer::new);
        EntityRendererRegistry.register(CFTPEntities.CREEPER_NETHER, CreeperNetherEntityRenderer::new);
        EntityRendererRegistry.register(CFTPEntities.CREEPER_FLIP, CreeperFlipEntityRenderer::new);
        EntityRendererRegistry.register(CFTPEntities.CREEPER_FRIENDLY, CreeperFriendlyEntityRenderer::new);
        EntityRendererRegistry.register(CFTPEntities.CREEPER_SNOW, CreeperSnowEntityRenderer::new);
        EntityRendererRegistry.register(CFTPEntities.CREEPER_SWAMP, CreeperSwampEntityRenderer::new);
        EntityRendererRegistry.register(CFTPEntities.CREEPER_DARK, CreeperDarkEntityRenderer::new);
        EntityRendererRegistry.register(CFTPEntities.CREEPER_BALLISTIC, CreeperBallisticEntityRenderer::new);
        EntityRendererRegistry.register(CFTPEntities.CREEPER_GOLDEN, CreeperGoldenEntityRenderer::new);
        EntityRendererRegistry.register(CFTPEntities.CREEPER_BRIDGER, CreeperBridgerEntityRenderer::new);
        EntityRendererRegistry.register(CFTPEntities.CREEPER_PIGGY, CreeperPiggyEntityRenderer::new);
        EntityRendererRegistry.register(CFTPEntities.CREEPER_AMALGAM, CreeperAmalgamEntityRenderer::new);
        EntityRendererRegistry.register(CFTPEntities.CREEPER_AMETHYST, CreeperAmethystEntityRenderer::new);
        EntityRendererRegistry.register(CFTPEntities.CREEPER_BREWER, CreeperBrewerEntityRenderer::new);
        EntityRendererRegistry.register(CFTPEntities.CREEPER_GIANT, CreeperGiantEntityRenderer::new);
        EntityRendererRegistry.register(CFTPEntities.CREEPER_HARVEST, CreeperHarvestEntityRenderer::new);
        EntityRendererRegistry.register(CFTPEntities.CREEPER_OCEANID, CreeperOceanidEntityRenderer::new);
        EntityRendererRegistry.register(CFTPEntities.CREEPER_SAND, CreeperSandEntityRenderer::new);
        EntityRendererRegistry.register(CFTPEntities.CREEPER_SCULK, CreeperSculkEntityRenderer::new);


        EntityRendererRegistry.register(CFTPEntities.SPIDER_YELLOW, SpiderYellowRenderer::new);
        EntityRendererRegistry.register(CFTPEntities.SPIDER_BLUE, SpiderBlueRenderer::new);
        EntityRendererRegistry.register(CFTPEntities.SLENDERMAN, SlendermanRenderer::new);

        EntityRendererRegistry.register(CFTPEntities.HORSE_FIERY, HorseFieryRenderer::new);

        // "Wind Charge" is referenced in:
        // WindChargeEntityRenderer
        // EntityRenderers
        // EntityModels
        // WindChargeEntityModel

        EntityRendererRegistry.register(CFTPEntities.WATER_CHARGE, WaterChargeEntityRenderer::new);
        EntityRendererRegistry.register(CFTPEntities.EARTH_CHARGE, EarthChargeEntityRenderer::new);
        EntityRendererRegistry.register(CFTPEntities.SULPHUR_CLOUD, SurphurCloudEntityRenderer::new);
        EntityRendererRegistry.register(CFTPEntities.FIERY_PROJECTILE, FieryProjectileEntityRenderer::new);
    }

}
