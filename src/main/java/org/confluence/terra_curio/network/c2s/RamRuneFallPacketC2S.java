package org.confluence.terra_curio.network.c2s;

import io.netty.buffer.ByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import org.confluence.terra_curio.TerraCurio;
import org.confluence.terra_curio.common.item.curio.combat.RamRune;
import org.mesdag.portlib.network.IPortPacket;
import org.mesdag.portlib.network.PortPacketDistributor;
import org.mesdag.portlib.network.codec.PortStreamCodec;

public record RamRuneFallPacketC2S() implements IPortPacket.C2S {
    public static final RamRuneFallPacketC2S INSTANCE = new RamRuneFallPacketC2S();
    public static final ResourceLocation ID = TerraCurio.asResource("ram_rune_fall");
    public static final PortStreamCodec<ByteBuf, RamRuneFallPacketC2S> STREAM_CODEC = PortStreamCodec.unit(INSTANCE);

    @Override
    public ResourceLocation identifier() {
        return ID;
    }

    @Override
    public void work(ServerPlayer player) {
        RamRune.startFalling(player);
    }

    public static void sendToServer() {
        PortPacketDistributor.sendToServer(INSTANCE);
    }
}
