package org.confluence.terra_curio.network.c2s;

import io.netty.buffer.ByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import org.confluence.lib.util.LibUtils;
import org.confluence.terra_curio.TerraCurio;
import org.confluence.terra_curio.common.item.curio.movement.BaseSpeedBoots;
import org.confluence.terra_curio.util.CuriosUtils;
import org.mesdag.portlib.network.IPortPacket;
import org.mesdag.portlib.network.PortPacketDistributor;
import org.mesdag.portlib.network.codec.PortByteBufCodecs;
import org.mesdag.portlib.network.codec.PortStreamCodec;

import java.util.function.Predicate;

public record SpeedBootsNBTPacketC2S(int slot, int value) implements IPortPacket.C2S {
    public static final ResourceLocation ID = TerraCurio.asResource("speed_boots_nbt");
    public static final PortStreamCodec<ByteBuf, SpeedBootsNBTPacketC2S> STREAM_CODEC = PortStreamCodec.composite(
            PortByteBufCodecs.VAR_INT, SpeedBootsNBTPacketC2S::slot,
            PortByteBufCodecs.VAR_INT, SpeedBootsNBTPacketC2S::value,
            SpeedBootsNBTPacketC2S::new
    );
    private static final Predicate<ItemStack> PREDICATE = itemStack -> itemStack.getItem() instanceof BaseSpeedBoots;

    @Override
    public ResourceLocation identifier() {
        return ID;
    }

    @Override
    public void work(ServerPlayer player) {
        ItemStack itemStack = CuriosUtils.getSlot(player, PREDICATE, slot);
        if (itemStack != null) {
            LibUtils.updateItemStackNbt(itemStack, nbt -> nbt.putInt(BaseSpeedBoots.KEY, value));
        }
    }

    public static void sendToServer(int slot, int value) {
        PortPacketDistributor.sendToServer(new SpeedBootsNBTPacketC2S(slot, value));
    }
}
