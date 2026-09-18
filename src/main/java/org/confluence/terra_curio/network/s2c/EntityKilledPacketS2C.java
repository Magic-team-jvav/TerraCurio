package org.confluence.terra_curio.network.s2c;

import io.netty.buffer.ByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.registries.ForgeRegistries;
import org.confluence.terra_curio.TerraCurio;
import org.confluence.terra_curio.client.handler.InformationHandler;
import org.mesdag.portlib.network.IPortPacket;
import org.mesdag.portlib.network.PortPacketDistributor;
import org.mesdag.portlib.network.codec.PortByteBufCodecs;
import org.mesdag.portlib.network.codec.PortStreamCodec;

import java.util.Objects;

public record EntityKilledPacketS2C(int amount, ResourceLocation entityType) implements IPortPacket.S2C {
    public static final ResourceLocation ID = TerraCurio.asResource("entity_killed");
    public static final PortStreamCodec<ByteBuf, EntityKilledPacketS2C> STREAM_CODEC = PortStreamCodec.composite(
            PortByteBufCodecs.VAR_INT, EntityKilledPacketS2C::amount,
            ResourceLocation.STREAM_CODEC, EntityKilledPacketS2C::entityType,
            EntityKilledPacketS2C::new
    );

    @Override
    public ResourceLocation identifier() {
        return ID;
    }

    @Override
    public void work(Player player) {
        InformationHandler.handleEntityKilled(amount, entityType);
    }

    public static void sendToClient(ServerPlayer player, EntityType<?> entityType) {
        PortPacketDistributor.sendToPlayer(player, new EntityKilledPacketS2C(
                player.getStats().getValue(Stats.ENTITY_KILLED.get(entityType)),
                Objects.requireNonNull(ForgeRegistries.ENTITY_TYPES.getKey(entityType))
        ));
    }
}
