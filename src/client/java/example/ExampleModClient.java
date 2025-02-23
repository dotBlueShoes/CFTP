package example;

import example.entity.CreeperCookieRenderer;
import example.entity.CreeperLightingRenderer;
import example.entity.CreeperFireRenderer;
import example.entity.CreeperWaterRenderer;
import example.entity.Entities;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class ExampleModClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ExampleMod.LOGGER.info("Hello Fabric Client!");

		EntityRendererRegistry.register(Entities.CREEPER_LIGHTING, CreeperLightingRenderer::new);
		EntityRendererRegistry.register(Entities.CREEPER_COOKIE, CreeperCookieRenderer::new);
		EntityRendererRegistry.register(Entities.CREEPER_WATER, CreeperWaterRenderer::new);
		EntityRendererRegistry.register(Entities.CREEPER_FIRE, CreeperFireRenderer::new);
	}
}