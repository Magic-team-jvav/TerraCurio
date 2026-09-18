package org.confluence.terra_curio.network.s2c;

import io.netty.buffer.ByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.player.Player;
import org.confluence.lib.util.LibStreamCodecUtils;
import org.confluence.terra_curio.TerraCurio;
import org.confluence.terra_curio.client.handler.PlayerJumpHandler;
import org.confluence.terra_curio.common.attachment.AccessoriesAttachment;
import org.confluence.terra_curio.common.init.TCItems;
import org.mesdag.portlib.network.IPortPacket;
import org.mesdag.portlib.network.PortPacketDistributor;
import org.mesdag.portlib.network.codec.PortByteBufCodecs;
import org.mesdag.portlib.network.codec.PortStreamCodec;

public record PlayerJumpPacketS2C(
        float fartSpeed,
        float sandstormSpeed,
        int sandstormTicks,
        float blizzardSpeed,
        int blizzardTicks,
        float tsunamiSpeed,
        float cloudSpeed
) implements IPortPacket.S2C {
    public static final ResourceLocation ID = TerraCurio.asResource("player_jump_s2c");
    public static final PortStreamCodec<ByteBuf, PlayerJumpPacketS2C> STREAM_CODEC = LibStreamCodecUtils.composite(
            PortByteBufCodecs.FLOAT, PlayerJumpPacketS2C::fartSpeed,
            PortByteBufCodecs.FLOAT, PlayerJumpPacketS2C::sandstormSpeed,
            PortByteBufCodecs.VAR_INT, PlayerJumpPacketS2C::sandstormTicks,
            PortByteBufCodecs.FLOAT, PlayerJumpPacketS2C::blizzardSpeed,
            PortByteBufCodecs.VAR_INT, PlayerJumpPacketS2C::blizzardTicks,
            PortByteBufCodecs.FLOAT, PlayerJumpPacketS2C::tsunamiSpeed,
            PortByteBufCodecs.FLOAT, PlayerJumpPacketS2C::cloudSpeed,
            PlayerJumpPacketS2C::new
    );

    @Override
    public ResourceLocation identifier() {
        return ID;
    }

    @Override
    public void work(Player player) {
        PlayerJumpHandler.handleJumpPacket(
                fartSpeed,
                sandstormSpeed,
                sandstormTicks,
                blizzardSpeed,
                blizzardTicks,
                tsunamiSpeed,
                cloudSpeed
        );
    }

    public static void sendToClient(ServerPlayer player) {
        AccessoriesAttachment attachment = AccessoriesAttachment.of(player);
        Tuple<Float, Integer> sandStorm = attachment.getValue(TCItems.SAND$STORM);
        Tuple<Float, Integer> blizzard = attachment.getValue(TCItems.BLIZZARD);
        PortPacketDistributor.sendToPlayer(player, new PlayerJumpPacketS2C(
                attachment.getValue(TCItems.FART),
                sandStorm.getA(), sandStorm.getB(),
                blizzard.getA(), blizzard.getB(),
                attachment.getValue(TCItems.TSUNAMI),
                attachment.getValue(TCItems.CLOUD)
        ));
    }
}
