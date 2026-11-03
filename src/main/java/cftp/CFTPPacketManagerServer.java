package cftp;

import cftp.packets.*;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;

public class CFTPPacketManagerServer {

    public static void initialize () {
        PayloadTypeRegistry.playS2C().register(
                LoopingSoundPayload.ID,
                LoopingSoundPayload.CODEC
        );
    }

}
