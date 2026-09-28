package org.confluence.terra_curio.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.control.MoveControl;
import org.confluence.lib.mixed.ILibEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

// todo 反转AI
// ⚠️ 1.21 独有：1.20 侧没有对应物（1.20 `mixin/EntityMixin` 里只留了 `// todo 反转AI`）。
// 本类**保留**，只把判据从 1.21 的 `IEntity`（`terra_curio$` 前缀的重力成员，含维度高度）
// 改指到 Lib 的 `ILibEntity`（`confluence$` 前缀）—— 1.20 的重力成员宿主。
@Mixin(MoveControl.class)
public abstract class MoveControlMixin {
    @Shadow
    @Final
    protected Mob mob;

    @ModifyExpressionValue(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Mob;getY()D", ordinal = 0))
    private double modifyY(double y) {
        ILibEntity entity = ILibEntity.of(mob);
        if (entity.confluence$isShouldRot()) {
            return y + entity.confluence$getDimensionHeight() - 1;
        }
        return y;
    }
}
