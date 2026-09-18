package org.confluence.terra_curio.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.nbt.ByteArrayTag;
import net.minecraft.nbt.ByteTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import org.confluence.lib.util.LibEntityUtils;
import org.confluence.lib.util.LibStreamCodecUtils;
import org.confluence.terra_curio.TerraCurio;
import org.confluence.terra_curio.client.handler.InformationHandler;
import org.mesdag.portlib.network.IPortPacket;
import org.mesdag.portlib.network.PortPacketDistributor;
import org.mesdag.portlib.network.codec.PortStreamCodec;

import javax.annotation.ParametersAreNonnullByDefault;

import static org.confluence.terra_curio.network.s2c.InfoCurioCheckPacketS2C.ARRAY_LENGTH;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public record InfoDisablePacket(boolean[] disables) implements IPortPacket {
    public static final ResourceLocation ID = TerraCurio.asResource("info_disable");
    public static final PortStreamCodec<ByteBuf, InfoDisablePacket> STREAM_CODEC = LibStreamCodecUtils.booleanArray(ARRAY_LENGTH)
            .map(InfoDisablePacket::new, InfoDisablePacket::disables);

    @Override
    public ResourceLocation identifier() {
        return ID;
    }

    @Override
    public void handle(Context context) {
        Player player = context.player();
        if (player == null) return;
        if (player.isLocalPlayer()) {
            System.arraycopy(disables, 0, InformationHandler.DISABLE, 0, ARRAY_LENGTH);
        } else {
            ByteArrayTag arrayTag = new ByteArrayTag(new byte[ARRAY_LENGTH]);
            for (int i = 0; i < ARRAY_LENGTH; i++) {
                arrayTag.set(i, ByteTag.valueOf(disables[i]));
            }
            LibEntityUtils.getOrCreatePersistedData(player).put("terra_curio:info_disable", arrayTag);
        }
    }

    public static void sendToClient(ServerPlayer player) {
        byte[] bytes = LibEntityUtils.getOrCreatePersistedData(player).getByteArray("terra_curio:info_disable");
        if (bytes.length != ARRAY_LENGTH) return;
        boolean[] disables = new boolean[ARRAY_LENGTH];
        for (int i = 0; i < ARRAY_LENGTH; i++) {
            disables[i] = bytes[i] != 0;
        }
        PortPacketDistributor.sendToPlayer(player, new InfoDisablePacket(disables));
    }

    public static void sendToServer(boolean[] disables) {
        PortPacketDistributor.sendToServer(new InfoDisablePacket(disables));
    }
}
