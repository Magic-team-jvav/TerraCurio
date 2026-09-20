package org.confluence.terra_curio.network.s2c;

import it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import org.confluence.terra_curio.TerraCurio;
import org.confluence.terra_curio.api.primitive.MayFlyAbilityValue;
import org.confluence.terra_curio.client.handler.PlayerJumpHandler;
import org.confluence.terra_curio.common.init.TCItems;
import org.confluence.terra_curio.util.TCUtils;
import org.mesdag.portlib.network.IPortPacket;
import org.mesdag.portlib.network.PortPacketDistributor;
import org.mesdag.portlib.network.PortRegistryFriendlyByteBuf;
import org.mesdag.portlib.network.codec.PortByteBufCodecs;
import org.mesdag.portlib.network.codec.PortStreamCodec;
import org.mesdag.portlib.wrapper.common.extensions.IPortResourceKeyExtension;

import java.util.Map;
import java.util.function.IntFunction;

public record PlayerFlyPacketS2C(
        Map<ResourceKey<Item>, MayFlyAbilityValue.FlyStack> flyStacks) implements IPortPacket.S2C {
    public static final ResourceLocation ID = TerraCurio.asResource("player_fly");
    public static final PortStreamCodec<PortRegistryFriendlyByteBuf, PlayerFlyPacketS2C> STREAM_CODEC = PortByteBufCodecs.map(
            (IntFunction<Map<ResourceKey<Item>, MayFlyAbilityValue.FlyStack>>) Reference2ObjectOpenHashMap::new,
            IPortResourceKeyExtension.streamCodec(Registries.ITEM), MayFlyAbilityValue.FlyStack.STREAM_CODEC
    ).map(PlayerFlyPacketS2C::new, PlayerFlyPacketS2C::flyStacks);

    @Override
    public ResourceLocation identifier() {
        return ID;
    }

    @Override
    public void work(Player player) {
        PlayerJumpHandler.handleFlyPacket(flyStacks);
    }

    public static void sendToClient(ServerPlayer player) {
        PortPacketDistributor.sendToPlayer(player, new PlayerFlyPacketS2C(TCUtils.getValue(player, TCItems.MAY$FLY)));
    }
}
