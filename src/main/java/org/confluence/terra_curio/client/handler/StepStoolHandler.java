package org.confluence.terra_curio.client.handler;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.phys.Vec3;
import org.confluence.lib.client.handler.GravitationHandler;
import org.confluence.terra_curio.client.TCKeyBindings;
import org.confluence.terra_curio.network.c2s.StepStoolSteppingPacketC2S;
import org.confluence.terra_curio.network.s2c.StepStoolSteppingPacketS2C;

import static org.confluence.terra_curio.network.c2s.StepStoolSteppingPacketC2S.INCREASE;

public final class StepStoolHandler {
    private static boolean upKeyDown = false;
    private static boolean shiftKeyDown = false;
    private static byte actualStep = 0;
    private static int maxStep = 0;
    private static int slot = StepStoolSteppingPacketS2C.NO_CURIO;

    public static void handle(LocalPlayer player) {
        if (slot == StepStoolSteppingPacketS2C.NO_CURIO || (actualStep == 0 && !player.onGround())) {
            setActualStep((byte) 0);
            return;
        }

        if (actualStep > 0) {
            if (player.input.jumping) {
                player.jumpFromGround();
                setStep((byte) 0, false);
                return;
            } else if (player.getVehicle() != null) {
                setStep((byte) 0, false);
                return;
            }
        }

        if (TCKeyBindings.STEP_STOOL.get().isDown()) {
            if (!upKeyDown && actualStep < maxStep) {
                setStep((byte) (actualStep + 1), true);
                upKeyDown = true;
            }
        } else {
            upKeyDown = false;
        }

        if (!upKeyDown && player.isShiftKeyDown()) {
            if (!shiftKeyDown && actualStep > 0) {
                setStep((byte) (actualStep - 1), false);
                shiftKeyDown = true;
            }
        } else {
            shiftKeyDown = false;
        }

        if (actualStep > 0) {
            player.setDeltaMovement(new Vec3(0.0, player.getDeltaMovement().y, 0.0));
        }
    }

    public static void reset() {
        setActualStep((byte) 0);
        maxStep = 0;
        slot = StepStoolSteppingPacketS2C.NO_CURIO;
    }

    public static void setStep(byte actualStep, boolean increase) {
        setActualStep(actualStep);
        byte step = actualStep;
        if (increase) step = (byte) (actualStep | INCREASE);
        StepStoolSteppingPacketC2S.sendToServer(slot, step);
    }

    public static int getActualStep() {
        return actualStep;
    }

    public static boolean onStool() {
        return actualStep > 0;
    }

    ///
    private static void setActualStep(byte step) {
        actualStep = step;
        GravitationHandler.setForceCancel(onStool());
    }

    public static void handlePacket(int slot, int maxStep) {
        if (slot == StepStoolSteppingPacketS2C.RESET_STEP) {
            setActualStep((byte) 0);
            StepStoolHandler.maxStep = maxStep;
        } else {
            StepStoolHandler.maxStep = maxStep;
            StepStoolHandler.slot = maxStep == 0 ? StepStoolSteppingPacketS2C.NO_CURIO : slot;
        }
    }
}
