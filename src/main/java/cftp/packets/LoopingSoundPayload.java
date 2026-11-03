package cftp.packets;

import cftp.CFTP;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

public record LoopingSoundPayload(Vec3d pos, boolean start)
        implements CustomPayload {

    public static final Id<LoopingSoundPayload> ID =
            new Id<>(Identifier.of(CFTP.MOD_ID, "looping_sound"));

    public static final PacketCodec<RegistryByteBuf, LoopingSoundPayload> CODEC =
            PacketCodec.tuple(
                    Vec3d.PACKET_CODEC, LoopingSoundPayload::pos,
                    PacketCodecs.BOOLEAN, LoopingSoundPayload::start,
                    LoopingSoundPayload::new
            );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
