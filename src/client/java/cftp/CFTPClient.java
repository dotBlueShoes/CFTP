package cftp;

import cftp.entity.CFTPEntitiesClient;
import cftp.registries.CFTPBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;
import net.fabricmc.fabric.api.client.rendering.v1.RenderTickEvents;

public class CFTPClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		CFTP.LOGGER.info("Hello Fabric Client!");
		CFTPEntitiesClient.register();

		BlockRenderLayerMap.INSTANCE.putBlock(CFTPBlocks.SAW_DUST_BLOCK, RenderLayer.getTranslucent());

		CFTP.instance = new ClientProxy();

		RenderTickEvents.START.register(context -> {
			// Called every frame before rendering
			// Put your per-frame logic here
			CFTP.LOGGER.info("Render tick started");
		});

		RenderTickEvents.END.register(context -> {
			// Called every frame after rendering
			// Alternative place for per-frame logic
		});
	}
}