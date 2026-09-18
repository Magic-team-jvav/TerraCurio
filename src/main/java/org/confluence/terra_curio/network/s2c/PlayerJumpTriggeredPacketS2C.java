package org.confluence.terra_curio.network.s2c;

import io.netty.buffer.ByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import org.confluence.terra_curio.TerraCurio;
import org.confluence.terra_curio.client.handler.PlayerJumpHandler;
import org.mesdag.portlib.network.IPortPacket;
import org.mesdag.portlib.network.PortPacketDistributor;
import org.mesdag.portlib.network.codec.PortByteBufCodecs;
import org.mesdag.portlib.network.codec.PortStreamCodec;

/**
 * 服务端向追踪该玩家的其他客户端广播"某玩家触发了跳跃"，用于远程玩家的跳跃粒子独立显示。
 * 发送方玩家自己（以及单机模式）不需要该包：本地玩家的粒子由 {@link PlayerJumpHandler} 直接驱动。
 */
public record PlayerJumpTriggeredPacketS2C(int entityId, byte jumpType) implements IPortPacket.S2C {
    public static final ResourceLocation ID = TerraCurio.asResource("player_jump_triggered_s2c");
    public static final PortStreamCodec<ByteBuf, PlayerJumpTriggeredPacketS2C> STREAM_CODEC = PortStreamCodec.composite(
            PortByteBufCodecs.VAR_INT, PlayerJumpTriggeredPacketS2C::entityId,
            PortByteBufCodecs.BYTE, PlayerJumpTriggeredPacketS2C::jumpType,
            PlayerJumpTriggeredPacketS2C::new
    );

    @Override
    public ResourceLocation identifier() {
        return ID;
    }

    @Override
    public void work(Player player) {
        PlayerJumpHandler.handleJumpTriggered(entityId, jumpType);
    }

    public static void sendToTrackingPlayers(ServerPlayer player, byte jumpType) {
        PortPacketDistributor.sendToPlayersTrackingEntity(player, new PlayerJumpTriggeredPacketS2C(player.getId(), jumpType));
    }
}
