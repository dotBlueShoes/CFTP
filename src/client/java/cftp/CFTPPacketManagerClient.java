package cftp;

import cftp.packets.*;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class CFTPPacketManagerClient {

    public static void initialize () {

        ClientPlayNetworking.registerGlobalReceiver(
                LoopingSoundPayload.ID, (payload, context) -> {
                    if (payload.start()) {
                        CFTPSoundManager.playBurying(payload.pos());
                    } else {
                        CFTPSoundManager.stopBurying();
                    }
                }
        );

    }
}
