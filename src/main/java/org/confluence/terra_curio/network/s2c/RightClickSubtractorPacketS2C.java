package org.confluence.terra_curio.network.s2c;

import io.netty.buffer.ByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import org.confluence.terra_curio.TerraCurio;
import org.confluence.terra_curio.client.handler.TCClientPacketHandler;
import org.confluence.terra_curio.common.init.TCItems;
import org.confluence.terra_curio.util.TCUtils;
import org.mesdag.portlib.network.IPortPacket;
import org.mesdag.portlib.network.PortPacketDistributor;
import org.mesdag.portlib.network.codec.PortByteBufCodecs;
import org.mesdag.portlib.network.codec.PortStreamCodec;

public record RightClickSubtractorPacketS2C(byte amount) implements IPortPacket.S2C {
    public static final ResourceLocation ID = TerraCurio.asResource("right_click_sub");
    public static final PortStreamCodec<ByteBuf, RightClickSubtractorPacketS2C> STREAM_CODEC = PortByteBufCodecs.BYTE.map(RightClickSubtractorPacketS2C::new, RightClickSubtractorPacketS2C::amount);

    @Override
    public ResourceLocation identifier() {
        return ID;
    }

    @Override
    public void work(Player player) {
        TCClientPacketHandler.handleSubstractor(amount);
    }

    public static void sendToClient(ServerPlayer player) {
        PortPacketDistributor.sendToPlayer(player, new RightClickSubtractorPacketS2C(TCUtils.getValue(player, TCItems.RIGHT$CLICK$DELAY$SUBSTRACTOR)));
    }
}
