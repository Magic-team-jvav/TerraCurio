package org.confluence.terra_curio.network.s2c;

import io.netty.buffer.ByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.confluence.terra_curio.TerraCurio;
import org.confluence.terra_curio.client.handler.StepStoolHandler;
import org.mesdag.portlib.network.IPortPacket;
import org.mesdag.portlib.network.PortPacketDistributor;
import org.mesdag.portlib.network.codec.PortByteBufCodecs;
import org.mesdag.portlib.network.codec.PortStreamCodec;
import top.theillusivec4.curios.api.SlotContext;

public record StepStoolSteppingPacketS2C(int slot, int maxStep) implements IPortPacket.S2C {
    public static final int NO_CURIO = -1;
    public static final int RESET_STEP = -2;
    public static final ResourceLocation ID = TerraCurio.asResource("step_stool_stepping_s2c");
    public static final PortStreamCodec<ByteBuf, StepStoolSteppingPacketS2C> STREAM_CODEC = PortStreamCodec.composite(
            PortByteBufCodecs.VAR_INT, StepStoolSteppingPacketS2C::slot,
            PortByteBufCodecs.VAR_INT, StepStoolSteppingPacketS2C::maxStep,
            StepStoolSteppingPacketS2C::new
    );

    @Override
    public ResourceLocation identifier() {
        return ID;
    }

    @Override
    public void work(Player player) {
        StepStoolHandler.handlePacket(slot, maxStep);
    }

    public static void sendToClient(SlotContext slotContext, int maxStep) {
        if (slotContext.entity() instanceof ServerPlayer player) {
            PortPacketDistributor.sendToPlayer(player, new StepStoolSteppingPacketS2C(slotContext.index(), maxStep));
        }
    }

    public static void resetStep(Entity entity, int maxStep) {
        if (entity instanceof ServerPlayer player) {
            PortPacketDistributor.sendToPlayer(player, new StepStoolSteppingPacketS2C(RESET_STEP, maxStep));
        }
    }
}
