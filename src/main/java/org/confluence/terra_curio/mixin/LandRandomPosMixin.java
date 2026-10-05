package org.confluence.terra_curio.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import org.confluence.lib.mixed.ILibEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.function.Predicate;

// todo 反转AI
@Mixin(LandRandomPos.class)
public abstract class LandRandomPosMixin {
    @WrapOperation(method = "movePosUpOutOfSolid", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/ai/util/RandomPos;moveUpOutOfSolid(Lnet/minecraft/core/BlockPos;ILjava/util/function/Predicate;)Lnet/minecraft/core/BlockPos;"))
    private static BlockPos movePosDownOutOfSolid(BlockPos pos, int maxY, Predicate<BlockPos> posPredicate, Operation<BlockPos> original, @Local(argsOnly = true) PathfinderMob mob) {
        if (ILibEntity.of(mob).confluence$isShouldRot()) {
            int minY = mob.level().getMinBuildHeight();
            if (!posPredicate.test(pos)) {
                return pos;
            } else {
                BlockPos below = pos.below();
                while (below.getY() > minY && posPredicate.test(below)) {
                    below = below.below();
                }
                return below;
            }
        }
        return original.call(pos, maxY, posPredicate);
    }
}
