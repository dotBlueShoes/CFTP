package cftp.entity;

import cftp.entity.creeper.*;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class CFTPEntitiesClient {

    public static void register() {
        EntityRendererRegistry.register(CFTPEntities.CREEPER_LIGHTING, CreeperLightingRenderer::new);
        EntityRendererRegistry.register(CFTPEntities.CREEPER_COOKIE, CreeperCookieRenderer::new);
        EntityRendererRegistry.register(CFTPEntities.CREEPER_WATER, CreeperWaterRenderer::new);
        EntityRendererRegistry.register(CFTPEntities.CREEPER_FIRE, CreeperFireRenderer::new);
        EntityRendererRegistry.register(CFTPEntities.CREEPER_LAVA, CreeperLavaRenderer::new);
        EntityRendererRegistry.register(CFTPEntities.CREEPER_DIRT, CreeperDirtRenderer::new);

        // WindChargeEntityRenderer
        // EntityRenderers
        // EntityModels
        // WindChargeEntityModel

        EntityRendererRegistry.register(CFTPEntities.WATER_CHARGE, WaterChargeEntityRenderer::new);
    }

}
