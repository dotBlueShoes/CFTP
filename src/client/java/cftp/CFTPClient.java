package cftp;

import cftp.entity.CreeperCookieRenderer;
import cftp.entity.CreeperLightingRenderer;
import cftp.entity.CreeperFireRenderer;
import cftp.entity.CreeperWaterRenderer;
import cftp.entity.Entities;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class CFTPClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		CFTP.LOGGER.info("Hello Fabric Client!");

		EntityRendererRegistry.register(Entities.CREEPER_LIGHTING, CreeperLightingRenderer::new);
		EntityRendererRegistry.register(Entities.CREEPER_COOKIE, CreeperCookieRenderer::new);
		EntityRendererRegistry.register(Entities.CREEPER_WATER, CreeperWaterRenderer::new);
		EntityRendererRegistry.register(Entities.CREEPER_FIRE, CreeperFireRenderer::new);
	}
}