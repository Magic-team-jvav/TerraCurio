package org.confluence.terra_curio.network.s2c;

import io.netty.buffer.ByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.server.ServerLifecycleHooks;
import org.confluence.terra_curio.TerraCurio;
import org.confluence.terra_curio.client.handler.TCClientPacketHandler;
import org.mesdag.portlib.network.IPortPacket;
import org.mesdag.portlib.network.PortPacketDistributor;
import org.mesdag.portlib.network.codec.PortByteBufCodecs;
import org.mesdag.portlib.network.codec.PortStreamCodec;

public record SetItemEntityPickupDelayPacketS2C(int id, int delay) implements IPortPacket.S2C {
    public static final ResourceLocation ID = TerraCurio.asResource("set_item_entity_pickup_delay");
    public static final PortStreamCodec<ByteBuf, SetItemEntityPickupDelayPacketS2C> STREAM_CODEC = PortStreamCodec.composite(
            PortByteBufCodecs.VAR_INT, SetItemEntityPickupDelayPacketS2C::id,
            PortByteBufCodecs.VAR_INT, SetItemEntityPickupDelayPacketS2C::delay,
            SetItemEntityPickupDelayPacketS2C::new
    );

    @Override
    public ResourceLocation identifier() {
        return ID;
    }

    @Override
    public void work(Player player) {
        TCClientPacketHandler.handleItemPickupDelay(id, delay);
    }

    public static void sendToAll(int id, int delay) {
        if (ServerLifecycleHooks.getCurrentServer() != null) {
            PortPacketDistributor.sendToAllPlayers(new SetItemEntityPickupDelayPacketS2C(id, delay));
        } else {
            TerraCurio.LOGGER.warn("Trying send a packet with no server!");
        }
    }
}
