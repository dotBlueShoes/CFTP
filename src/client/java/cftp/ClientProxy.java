package cftp;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

@Environment(EnvType.CLIENT)
public class ClientProxy implements CommonProxy {

    public void registerTickHandler() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            // Your logic here
            CFTP.LOGGER.info("call");
        });
    }

}
