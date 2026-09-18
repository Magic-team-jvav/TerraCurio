package org.confluence.terra_curio.network.c2s;

import io.netty.buffer.ByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import org.confluence.terra_curio.TerraCurio;
import org.confluence.terra_curio.util.TCUtils;
import org.mesdag.portlib.network.IPortPacket;
import org.mesdag.portlib.network.PortPacketDistributor;
import org.mesdag.portlib.network.codec.PortStreamCodec;

public record PlayerSprintPacketC2S() implements IPortPacket.C2S {
    public static final PlayerSprintPacketC2S INSTANCE = new PlayerSprintPacketC2S();
    public static final ResourceLocation ID = TerraCurio.asResource("player_sprint");
    public static final PortStreamCodec<ByteBuf, PlayerSprintPacketC2S> STREAM_CODEC = PortStreamCodec.unit(INSTANCE);

    @Override
    public ResourceLocation identifier() {
        return ID;
    }

    @Override
    public void work(ServerPlayer player) {
        TCUtils.applyCthulhuSprinting(true, player);
    }

    public static void sendToServer() {
        PortPacketDistributor.sendToServer(INSTANCE);
    }
}
