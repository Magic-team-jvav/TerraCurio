package org.confluence.terra_curio.network.s2c;

import io.netty.buffer.ByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import org.confluence.terra_curio.TerraCurio;
import org.confluence.terra_curio.client.handler.PlayerClimbHandler;
import org.confluence.terra_curio.common.init.TCItems;
import org.confluence.terra_curio.util.TCUtils;
import org.mesdag.portlib.network.IPortPacket;
import org.mesdag.portlib.network.PortPacketDistributor;
import org.mesdag.portlib.network.codec.PortByteBufCodecs;
import org.mesdag.portlib.network.codec.PortStreamCodec;

public record PlayerClimbPacketS2C(byte climberAmount) implements IPortPacket.S2C {
    public static final ResourceLocation ID = TerraCurio.asResource("player_climb");
    public static final PortStreamCodec<ByteBuf, PlayerClimbPacketS2C> STREAM_CODEC = PortByteBufCodecs.BYTE.map(PlayerClimbPacketS2C::new, PlayerClimbPacketS2C::climberAmount);

    @Override
    public ResourceLocation identifier() {
        return ID;
    }

    @Override
    public void work(Player player) {
        PlayerClimbHandler.handlePacket(climberAmount);
    }

    public static void sendToClient(ServerPlayer player) {
        PortPacketDistributor.sendToPlayer(player, new PlayerClimbPacketS2C(TCUtils.getValue(player, TCItems.WALL$CLIMB)));
    }
}
