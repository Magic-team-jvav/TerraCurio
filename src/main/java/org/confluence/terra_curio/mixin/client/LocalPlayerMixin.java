package org.confluence.terra_curio.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.fluids.FluidType;
import org.confluence.lib.client.handler.GravitationHandler;
import org.confluence.lib.mixed.SelfGetter;
import org.confluence.terra_curio.client.handler.TCClientPacketHandler;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/// ⚠️ 任务书说本类「1.20 Lib 无对应物 → 保留」，但实测**1.20 Lib 有对应物**
/// （`Confluence-Magic-Lib/.../mixin/client/LocalPlayerMixin.java`，且其中
/// `sinkUpFluid` + `flip` 两个重力 hook 与这里的同名同目标）。
/// 按任务书的「保留 + `GravitationHandler` 改指 Lib」处置：
/// - 保留 `notCheckOnGround`（`TCClientPacketHandler.isHasCthulhu()`，TC 克苏鲁线）与
///   `floating`（TC 漂浮线）—— 这两个是 1.21 独有，1.20 Lib 无；
/// - `sinkUpFluid` / `flip` 的重力那半改指 Lib 的 `GravitationHandler`
///   （`isShouldRot` / `getJumpDir`，Lib 侧同名同义）。
///
/// ⚠️ 1.21 的 Lib `LocalPlayerMixin` 目前**只有** `skipSlowdown`，没有这两个重力 hook。
/// 若 Lib 侧按 1.20 补上，两边会对 `sinkInFluid` / `getFlyingSpeed` 各命中一次
/// （`flip` 会乘两次 `getJumpDir()`），届时应删掉本类的这两半（已回报）。
@Mixin(LocalPlayer.class)
public abstract class LocalPlayerMixin implements SelfGetter<Player> {
    @Shadow
    @Final
    protected Minecraft minecraft;
    @Unique
    private int terra_curio$floatOutTicks = 0;

    @ModifyExpressionValue(method = "aiStep", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;onGround()Z", ordinal = 0))
    private boolean notCheckOnGround(boolean original) {
        return original || TCClientPacketHandler.isHasCthulhu();
    }

    @WrapWithCondition(method = "aiStep", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;sinkInFluid(Lnet/neoforged/neoforge/fluids/FluidType;)V"), remap = false)
    private boolean sinkUpFluid(LocalPlayer instance, FluidType fluidType) {
        if (GravitationHandler.isShouldRot()) {
            instance.jumpInFluid(fluidType);
            return false;
        }
        return true;
    }

    @ModifyExpressionValue(method = "aiStep", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Abilities;getFlyingSpeed()F"))
    private float flip(float original) {
        return original * GravitationHandler.getJumpDir();
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void floating(CallbackInfo ci) {
        Player self;
        if (TCClientPacketHandler.isCanFloating() && !(self = confluence$self()).isCrouching()) {
            FluidType water = NeoForgeMod.WATER_TYPE.value();
            if (self.isEyeInFluidType(water)) {
                self.jumpInFluid(water);
                this.terra_curio$floatOutTicks = 0;
                TCClientPacketHandler.floating = true;
            } else if (terra_curio$floatOutTicks == 10 && self.level().getBlockState(self.blockPosition().above()).is(Blocks.WATER)) {
                Vec3 motion = self.getDeltaMovement();
                self.setDeltaMovement(motion.x, 0.0, motion.z);
            } else {
                if (terra_curio$floatOutTicks < 10) {
                    this.terra_curio$floatOutTicks++;
                }
            }
        } else {
            TCClientPacketHandler.floating = false;
        }
    }
}
