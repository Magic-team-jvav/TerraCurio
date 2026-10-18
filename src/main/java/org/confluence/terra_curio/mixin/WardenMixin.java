package org.confluence.terra_curio.mixin;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.player.Player;
import org.confluence.terra_curio.common.init.TCItems;
import org.confluence.terra_curio.util.CuriosUtils;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/// 除味剂：监守者无法嗅出佩戴者的位置。
///
/// [Warden#canTargetEntity] 是监守者一切索敌行为的统一入口（振动感知、愤怒累积、
/// 愤怒管理器的目标筛选都会经过它），因此在这里直接让佩戴者不可被选中即可。
/// 近身攻击造成的反击走 [Warden#hurt] 里的 [Warden#setAttackTarget]，不受影响。
@Mixin(Warden.class)
public abstract class WardenMixin {
    @Inject(method = "canTargetEntity", at = @At("HEAD"), cancellable = true)
    private void terra_curio$deodorizerHideWearer(@Nullable Entity entity, CallbackInfoReturnable<Boolean> cir) {
        if (entity instanceof Player player && CuriosUtils.hasCurio(player, TCItems.DEODORIZER.get())) {
            cir.setReturnValue(false);
        }
    }
}
