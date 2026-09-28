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
            // 1.20 这里写的是 `setActualStep((byte) 0)`（从而带上 `setForceCancel` 同步）；
            // 1.21 此前直接写字段，本次按 1.20 走 setter（只改这一处**写入**；
            // 函数内其余 `actualStep` 都是**读**，与 1.20 的 `getActualStep()` 等价，未动）。
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

    /// WP6c 第二步：按 1.20 恢复「台阶凳 → 重力反转强制取消」的耦合通道。
    ///
    /// 1.20 `client/handler/StepStoolHandler.java:80-83` 有一个私有 `setActualStep(byte)`，
    /// 赋值后立刻 `GravitationHandler.setForceCancel(onStool())`；
    /// 1.21 侧把这个赋值**内联**到了三个调用点（`reset` / `setStep` / `handlePacket`），
    /// 于是耦合只剩「mixin 里直接读 `StepStoolHandler.onStool()`」这一条。
    /// 现在改回 1.20 的通道：所有写入都走本方法，`GravitationHandler` 用 Lib 的
    /// （`setForceCancel` 在 1.20/1.21 的 Lib 里都在）。
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
