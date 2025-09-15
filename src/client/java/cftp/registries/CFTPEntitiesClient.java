package cftp.registries;

import cftp.entity.CFTPEntities;
import cftp.entity.HorseFieryRenderer;
import cftp.entity.SlendermanRenderer;
import cftp.entity.charge.*;
import cftp.entity.creeper.*;
import cftp.entity.spider.SpiderBlueRenderer;
import cftp.entity.spider.SpiderYellowRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class CFTPEntitiesClient {

    public static void register() {
        EntityRendererRegistry.register(CFTPEntities.CREEPER_LIGHTING, CreeperLightingRenderer::new);
        EntityRendererRegistry.register(CFTPEntities.CREEPER_COOKIE, CreeperCookieRenderer::new);
        EntityRendererRegistry.register(CFTPEntities.CREEPER_ENDER, CreeperEnderRenderer::new);
        EntityRendererRegistry.register(CFTPEntities.CREEPER_WATER, CreeperWaterRenderer::new);
        EntityRendererRegistry.register(CFTPEntities.CREEPER_EARTH, CreeperEarthRenderer::new);
        EntityRendererRegistry.register(CFTPEntities.CREEPER_FIRE, CreeperFireRenderer::new);
        EntityRendererRegistry.register(CFTPEntities.CREEPER_LAVA, CreeperLavaRenderer::new);
        EntityRendererRegistry.register(CFTPEntities.CREEPER_DIRT, CreeperDirtRenderer::new);
        EntityRendererRegistry.register(CFTPEntities.CREEPER_WIND, CreeperWindRenderer::new);
        EntityRendererRegistry.register(CFTPEntities.CREEPER_GHOST, CreeperGhostRenderer::new);
        EntityRendererRegistry.register(CFTPEntities.CREEPER_NETHER, CreeperNetherRenderer::new);
        EntityRendererRegistry.register(CFTPEntities.CREEPER_FLIP, CreeperFlipRenderer::new);
        EntityRendererRegistry.register(CFTPEntities.CREEPER_FRIENDLY, CreeperFriendlyRenderer::new);
        EntityRendererRegistry.register(CFTPEntities.CREEPER_SNOW, CreeperSnowRenderer::new);
        EntityRendererRegistry.register(CFTPEntities.CREEPER_SWAMP, CreeperSwampRenderer::new);
        EntityRendererRegistry.register(CFTPEntities.CREEPER_DARK, CreeperDarkRenderer::new);
        EntityRendererRegistry.register(CFTPEntities.CREEPER_BALLISTIC, CreeperBallisticRenderer::new);
        EntityRendererRegistry.register(CFTPEntities.CREEPER_GOLDEN, CreeperGoldenRenderer::new);


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
    }

}
