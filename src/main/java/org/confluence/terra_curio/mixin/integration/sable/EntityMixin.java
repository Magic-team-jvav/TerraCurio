package org.confluence.terra_curio.mixin.integration.sable;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.confluence.lib.mixed.ILibEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

/// Sable 联动：把「反转重力时也算站在地面上」补进 Sable 的 `setOnGroundWithMovement` 路径。
///
/// ⚠️ **1.21 独有**：1.20 的 TerraCurio 里**没有** `mixin/integration/sable/**`（只有
/// `mixin/integration/{accessor,curios}`），所以本类按「保留 + 改指」处置，不删。
/// WP6c（重力反转整条特性搬到 Magic-Lib）把判据从 TerraCurio 的
/// `IEntity.of(instance).terra_curio$isShouldRot()` 换成 Lib 的
/// `ILibEntity.of(instance).confluence$isShouldRot()`（与 `GroundPathNavigationMixin` 等同形）。
@Mixin(value = Entity.class, priority = 1100)
public abstract class EntityMixin {
    @Shadow
    public boolean verticalCollision;

    @SuppressWarnings("all")
    @WrapOperation(method = "move", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;setOnGroundWithMovement(ZLnet/minecraft/world/phys/Vec3;)V"))
    private void afterSable(Entity instance, boolean onGround, Vec3 movement, Operation<Void> original, @Local(argsOnly = true) Vec3 pos) {
        if (ILibEntity.of(instance).confluence$isShouldRot()) {
            onGround |= verticalCollision && pos.y > 0;
        }
        original.call(instance, onGround, movement);
    }
}
