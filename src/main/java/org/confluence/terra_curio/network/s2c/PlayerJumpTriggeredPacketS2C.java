package org.confluence.terra_curio.network.s2c;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.PacketDistributor;
import org.confluence.lib.network.IPacketS2C;
import org.confluence.terra_curio.TerraCurio;
import org.confluence.terra_curio.client.handler.PlayerJumpHandler;

/**
 * 服务端向追踪该玩家的其他客户端广播"某玩家触发了跳跃"，用于远程玩家的跳跃粒子独立显示。
 * 发送方玩家自己（以及单机模式）不需要该包：本地玩家的粒子由 {@link PlayerJumpHandler} 直接驱动。
 */
public record PlayerJumpTriggeredPacketS2C(int entityId, byte jumpType) implements IPacketS2C {
    public static final Type<PlayerJumpTriggeredPacketS2C> TYPE = new Type<>(TerraCurio.asResource("player_jump_triggered_s2c"));
    public static final StreamCodec<ByteBuf, PlayerJumpTriggeredPacketS2C> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT, PlayerJumpTriggeredPacketS2C::entityId,
            ByteBufCodecs.BYTE, PlayerJumpTriggeredPacketS2C::jumpType,
            PlayerJumpTriggeredPacketS2C::new
    );

    @Override
    public Type<PlayerJumpTriggeredPacketS2C> type() {
        return TYPE;
    }

    @Override
    public void work(Player player) {
        PlayerJumpHandler.handleJumpTriggered(entityId, jumpType);
    }

    public static void sendToTrackingPlayers(ServerPlayer player, byte jumpType) {
        PacketDistributor.sendToPlayersTrackingEntity(player, new PlayerJumpTriggeredPacketS2C(player.getId(), jumpType));
    }
}
