package cftp;

import cftp.entity.CFTPEntitiesClient;
import net.fabricmc.api.ClientModInitializer;

public class CFTPClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		CFTP.LOGGER.info("Hello Fabric Client!");
		CFTPEntitiesClient.register();
	}
}