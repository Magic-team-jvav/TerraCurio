package org.confluence.terra_curio.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.confluence.lib.mixed.SelfGetter;
import org.confluence.terra_curio.client.handler.StepStoolHandler;
import org.confluence.terra_curio.client.handler.TCClientPacketHandler;
import org.confluence.terra_curio.mixed.ITCClientLivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

/// 客户端 `LivingEntity` 的 TC 扩展（WP6c 第二步：按 1.20 的 TC 形态收敛）。
///
/// 逐 hook 处置：
/// - **删** `fall`（`checkFallDamage` HEAD，读 `isShouldRot()` 加 `fallDistance`）——
///   重力归属 Lib，且 1.21 的 Lib 里已经有它
///   （`Confluence-Magic-Lib/.../mixin/client/ClientLivingEntityMixin.java:23 fall`），
///   留着会对同一条注入点各命中一次；
/// - **留** 化妆那两个成员（`terra_curio$setShowingCosmetic` / `isShowingCosmetic`，
///   1.20 TC 的 `ClientLivingEntityMixin` 也在这一个类里实现 `ITCClientLivingEntity`）；
/// - **留** `onStool` / `notSlowdown` / `neptunesShell`（台阶凳、漂浮、海王壳，TC 自己的功能），
///   只把 `IEntity#terra_curio$isPlayer()` 换成 1.20 的 `instanceof Player` 写法
///   —— `terra_curio$isPlayer` 是 1.21 的发明，1.20 **不存在**这个成员
///   （1.20 `ClientLivingEntityMixin:35/44/53` 全是 `confluence$self() instanceof Player ...`）。
@Mixin(LivingEntity.class)
public abstract class ClientLivingEntityMixin implements ITCClientLivingEntity, SelfGetter<LivingEntity> {
    @Unique
    private boolean terra_curio$showingCosmetic = false;

    @Override
    public void terra_curio$setShowingCosmetic(boolean showing) {
        this.terra_curio$showingCosmetic = showing;
    }

    @Override
    public boolean terra_curio$isShowingCosmetic() {
        return terra_curio$showingCosmetic;
    }

    @ModifyVariable(method = "travel", at = @At("HEAD"), argsOnly = true)
    private Vec3 onStool(Vec3 vec3) {
        LivingEntity living = confluence$self();
        if (StepStoolHandler.onStool() && living instanceof Player player) {
            return player.isLocalPlayer() ? Vec3.ZERO : vec3;
        }
        return vec3;
    }

    @WrapOperation(method = "travel", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/phys/Vec3;multiply(DDD)Lnet/minecraft/world/phys/Vec3;", ordinal = 0))
    private Vec3 notSlowdown(Vec3 instance, double factorX, double factorY, double factorZ, Operation<Vec3> original) {
        if (TCClientPacketHandler.floating && TCClientPacketHandler.isCanFloating()) {
            if (confluence$self() instanceof Player) {
                return original.call(instance, factorX, 1.0, factorZ);
            }
        }
        return original.call(instance, factorX, factorY, factorZ);
    }

    @ModifyExpressionValue(method = "travel", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;hasEffect(Lnet/minecraft/core/Holder;)Z", ordinal = 1))
    private boolean neptunesShell(boolean original) {
        return original || (TCClientPacketHandler.isHasNeptunesShell() && confluence$self() instanceof Player);
    }
}
