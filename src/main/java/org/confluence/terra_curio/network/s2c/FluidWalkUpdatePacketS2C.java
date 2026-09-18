package org.confluence.terra_curio.network.s2c;

import io.netty.buffer.ByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import org.confluence.terra_curio.TerraCurio;
import org.confluence.terra_curio.util.TCUtils;
import org.mesdag.portlib.network.IPortPacket;
import org.mesdag.portlib.network.PortPacketDistributor;
import org.mesdag.portlib.network.codec.PortStreamCodec;

public enum FluidWalkUpdatePacketS2C implements IPortPacket.S2C {
    INSTANCE;

    public static final ResourceLocation ID = TerraCurio.asResource("fluid_walk_update");
    public static final PortStreamCodec<ByteBuf, FluidWalkUpdatePacketS2C> STREAM_CODEC = PortStreamCodec.unit(INSTANCE);

    @Override
    public ResourceLocation identifier() {
        return ID;
    }

    @Override
    public void work(Player player) {
        TCUtils.updateWalkableFluidStates(player);
    }

    public static void sendToClient(ServerPlayer player) {
        TCUtils.updateWalkableFluidStates(player);
        PortPacketDistributor.sendToPlayer(player, INSTANCE);
    }
}
