package cftp;

import cftp.entity.registries.CFTPEntitiesClient;
import net.fabricmc.api.ClientModInitializer;

public class CFTPClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		CFTP.LOGGER.info("Hello Fabric Client!");
		CFTPEntitiesClient.register();

		// TEST
		//BlockRenderLayerMap.INSTANCE.putBlock(CFTPBlocks.SAW_DUST_BLOCK, RenderLayer.getTranslucent());
	}
}