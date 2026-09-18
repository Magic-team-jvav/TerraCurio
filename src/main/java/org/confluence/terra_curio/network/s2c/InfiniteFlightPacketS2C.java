package org.confluence.terra_curio.network.s2c;

import io.netty.buffer.ByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import org.confluence.terra_curio.TerraCurio;
import org.confluence.terra_curio.client.handler.PlayerJumpHandler;
import org.confluence.terra_curio.common.init.TCItems;
import org.confluence.terra_curio.util.TCUtils;
import org.mesdag.portlib.network.IPortPacket;
import org.mesdag.portlib.network.PortPacketDistributor;
import org.mesdag.portlib.network.codec.PortByteBufCodecs;
import org.mesdag.portlib.network.codec.PortStreamCodec;

public record InfiniteFlightPacketS2C(boolean enable) implements IPortPacket.S2C {
    public static final ResourceLocation ID = TerraCurio.asResource("infinite_flight");
    public static final PortStreamCodec<ByteBuf, InfiniteFlightPacketS2C> STREAM_CODEC = PortByteBufCodecs.BOOL.map(InfiniteFlightPacketS2C::new, InfiniteFlightPacketS2C::enable);

    @Override
    public ResourceLocation identifier() {
        return ID;
    }

    @Override
    public void work(Player player) {
        PlayerJumpHandler.handleInfiniteFlight(enable);
    }

    public static void sendToClient(ServerPlayer player) {
        boolean enable = TCUtils.hasType(player, TCItems.INFINITE$FLIGHT);
        PortPacketDistributor.sendToPlayer(player, new InfiniteFlightPacketS2C(enable));
    }
}
