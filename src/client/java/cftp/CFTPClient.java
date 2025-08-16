package cftp;

import cftp.registries.CFTPEntitiesClient;
import cftp.registries.CFTPBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;

public class CFTPClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		CFTP.LOGGER.info("Hello Fabric Client!");
		CFTPEntitiesClient.register();

		// TEST
		//BlockRenderLayerMap.INSTANCE.putBlock(CFTPBlocks.SAW_DUST_BLOCK, RenderLayer.getTranslucent());

		BlockRenderLayerMap.INSTANCE.putBlock(CFTPBlocks.YELLOW_MUSHROOM, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(CFTPBlocks.BLUE_MUSHROOM, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(CFTPBlocks.YELLOW_COBWEB, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(CFTPBlocks.BLUE_COBWEB, RenderLayer.getCutout());
	}
}