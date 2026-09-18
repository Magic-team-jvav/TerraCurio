package org.confluence.terra_curio.network.s2c;

import io.netty.buffer.ByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.server.ServerLifecycleHooks;
import org.confluence.terra_curio.TerraCurio;
import org.confluence.terra_curio.client.handler.TCClientPacketHandler;
import org.confluence.terra_curio.common.init.TCItems;
import org.confluence.terra_curio.util.TCUtils;
import org.mesdag.portlib.network.IPortPacket;
import org.mesdag.portlib.network.PortPacketDistributor;
import org.mesdag.portlib.network.codec.PortByteBufCodecs;
import org.mesdag.portlib.network.codec.PortStreamCodec;

public record BroadcastRenderPacketS2C(int playerId, short render) implements IPortPacket.S2C {
    public static final short LUMINANCE_MASK = 0b001111;
    public static final short NEPTUNES_SHELL = 0b010000;
    public static final short MOON_CHARM = 0b100000;
    public static final ResourceLocation ID = TerraCurio.asResource("broadcast_render");
    public static final PortStreamCodec<ByteBuf, BroadcastRenderPacketS2C> STREAM_CODEC = PortStreamCodec.composite(
            PortByteBufCodecs.VAR_INT, BroadcastRenderPacketS2C::playerId,
            PortByteBufCodecs.SHORT, BroadcastRenderPacketS2C::render,
            BroadcastRenderPacketS2C::new
    );

    @Override
    public ResourceLocation identifier() {
        return ID;
    }

    @Override
    public void work(Player player) {
        TCClientPacketHandler.handleRender(playerId, render, player);
    }

    public static void sendToPlayersTrackingTarget(ServerPlayer target) {
        if (ServerLifecycleHooks.getCurrentServer() != null) {
            short luminance = (short) (TCUtils.getValue(target, TCItems.LUMINANCE) & LUMINANCE_MASK);
            short neptunesShell = TCUtils.hasType(target, TCItems.NEPTUNES$SHELL) ? NEPTUNES_SHELL : 0;
            PortPacketDistributor.sendToPlayersTrackingEntityAndSelf(target, new BroadcastRenderPacketS2C(target.getId(), (short) (luminance | neptunesShell)));
        }
    }
}
