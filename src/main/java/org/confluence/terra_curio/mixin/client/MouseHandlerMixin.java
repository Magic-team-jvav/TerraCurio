package org.confluence.terra_curio.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.MouseHandler;
import net.minecraft.client.player.LocalPlayer;
import org.confluence.lib.client.handler.GravitationHandler;
import org.confluence.terra_curio.client.handler.ScopeFovHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/// ⚠️ 任务书说本类「1.20 Lib 无对应物 → 保留」，但实测**1.20 Lib 有对应物**
/// （`Confluence-Magic-Lib/.../mixin/client/MouseHandlerMixin.java`，hook 就是这里的
/// `modify` + `GravitationHandler.isShouldRot()` 反转鼠标）。按任务书的「保留 + import 改指 Lib」处置，
/// 但**必须提醒**：1.21 的 Lib 目前**还没有**这个 mixin
/// （`Confluence-Magic-Lib/src/main/java/org/confluence/lib/mixin/` 下无 `client/MouseHandlerMixin.java`）。
/// 若 Lib 侧按 1.20 补上它，两边会各自把 `x`/`y` 取反一次 → **负负得正，重力反转下鼠标失效**；
/// 到那时应当删掉本类的重力那半（保留 `ScopeFovHandler` 那半），或整体删类。
@Mixin(MouseHandler.class)
public abstract class MouseHandlerMixin {
    @WrapOperation(method = "turnPlayer", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;turn(DD)V"))
    private void modify(LocalPlayer instance, double y, double x, Operation<Void> original) {
        if (GravitationHandler.isShouldRot()) {
            x = -x;
            y = -y;
        }
        if (ScopeFovHandler.isScoping()) {
            double factor = ScopeFovHandler.getCameraMoveFactor();
            x *= factor;
            y *= factor;
        }
        original.call(instance, y, x);
    }
}
