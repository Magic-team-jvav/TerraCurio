package org.confluence.terra_curio.network.c2s;

import io.netty.buffer.ByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import org.confluence.lib.util.LibUtils;
import org.confluence.terra_curio.TerraCurio;
import org.confluence.terra_curio.common.entity.StepStoolEntity;
import org.confluence.terra_curio.common.item.curio.movement.StepStool;
import org.confluence.terra_curio.util.CuriosUtils;
import org.mesdag.portlib.network.IPortPacket;
import org.mesdag.portlib.network.PortPacketDistributor;
import org.mesdag.portlib.network.codec.PortByteBufCodecs;
import org.mesdag.portlib.network.codec.PortStreamCodec;

import java.util.function.Predicate;

public record StepStoolSteppingPacketC2S(int slot, byte step) implements IPortPacket.C2S {
    public static final byte STEP_MASK = 0b0111111;
    public static final byte INCREASE = 0b1000000;

    public static final ResourceLocation ID = TerraCurio.asResource("step_stool_stepping_c2s");
    public static final PortStreamCodec<ByteBuf, StepStoolSteppingPacketC2S> STREAM_CODEC = PortStreamCodec.composite(
            PortByteBufCodecs.VAR_INT, StepStoolSteppingPacketC2S::slot,
            PortByteBufCodecs.BYTE, StepStoolSteppingPacketC2S::step,
            StepStoolSteppingPacketC2S::new
    );
    private static final Predicate<ItemStack> PREDICATE = itemStack -> itemStack.getItem() instanceof StepStool;

    @Override
    public ResourceLocation identifier() {
        return ID;
    }

    @Override
    public void work(ServerPlayer player) {
        if (slot == -1) return;
        ItemStack itemStack = CuriosUtils.getSlot(player, PREDICATE, slot);
        if (itemStack == null) return;
        int actualStep = step & STEP_MASK;
        boolean increase = (step & INCREASE) == INCREASE;

        if (actualStep == 1 && increase) {
            StepStoolEntity entity = new StepStoolEntity(player, StepStool.getMaxStep(itemStack));
            player.level().addFreshEntity(entity);
            player.teleportRelative(0.0, 1.001, 0.0);
            LibUtils.updateItemStackNbt(itemStack, nbt -> nbt.putInt("id", entity.getId()));
        } else {
            int id = LibUtils.getItemStackNbtNoCopy(itemStack).getInt("id");
            if (player.level().getEntity(id) instanceof StepStoolEntity stepStool) {
                if (actualStep == 0) {
                    stepStool.setOwner(null);
                } else {
                    stepStool.setStep(actualStep);
                    if (increase) {
                        player.teleportRelative(0.0, 1.001, 0.0);
                    }
                }
            }
        }
    }

    public static void sendToServer(int slot, byte step) {
        PortPacketDistributor.sendToServer(new StepStoolSteppingPacketC2S(slot, step));
    }
}
