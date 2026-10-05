package org.confluence.terra_curio.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.pathfinder.NodeEvaluator;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.level.pathfinder.PathfindingContext;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import org.confluence.lib.mixed.ILibEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

// todo 反转AI
@Mixin(WalkNodeEvaluator.class)
public abstract class WalkNodeEvaluatorMixin extends NodeEvaluator {
    @ModifyExpressionValue(method = "getStart", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Mth;floor(D)I"))
    private int modifyOnGroundY(int original) {
        if (ILibEntity.of(mob).confluence$isShouldRot()) {
            return original + Mth.ceil(entityHeight) - 1;
        }
        return original;
    }

    @ModifyVariable(method = "getPathTypeWithinMobBB", at = @At("HEAD"), ordinal = 1, argsOnly = true)
    private int entityHeight(int y) {
        if (ILibEntity.of(mob).confluence$isShouldRot()) {
            return y - entityHeight + 1;
        }
        return y;
    }

    @WrapOperation(method = "getPathType", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/pathfinder/WalkNodeEvaluator;getPathTypeStatic(Lnet/minecraft/world/level/pathfinder/PathfindingContext;Lnet/minecraft/core/BlockPos$MutableBlockPos;)Lnet/minecraft/world/level/pathfinder/PathType;"))
    private PathType getInversePathType(PathfindingContext context, BlockPos.MutableBlockPos pos, Operation<PathType> original) {
        if (ILibEntity.of(mob).confluence$isShouldRot()) {
            return terra_curio$getPathTypeStatic(context, pos);
        }
        return original.call(context, pos);
    }

    @Unique
    private static PathType terra_curio$getPathTypeStatic(PathfindingContext context, BlockPos.MutableBlockPos pos) {
        int i = pos.getX();
        int j = pos.getY();
        int k = pos.getZ();
        PathType pathtype = context.getPathTypeFromState(i, j, k);
        if (pathtype == PathType.OPEN && j <= context.level().getMaxBuildHeight() - 1) {
            return switch (context.getPathTypeFromState(i, j + 1, k)) {
                case OPEN, WATER, LAVA, WALKABLE -> PathType.OPEN;
                case DAMAGE_FIRE -> PathType.DAMAGE_FIRE;
                case DAMAGE_OTHER -> PathType.DAMAGE_OTHER;
                case STICKY_HONEY -> PathType.STICKY_HONEY;
                case POWDER_SNOW -> PathType.DANGER_POWDER_SNOW;
                case DAMAGE_CAUTIOUS -> PathType.DAMAGE_CAUTIOUS;
                case TRAPDOOR -> PathType.DANGER_TRAPDOOR;
                default ->
                        WalkNodeEvaluator.checkNeighbourBlocks(context, i, j, k, PathType.WALKABLE);
            };
        } else {
            return pathtype;
        }
    }
}
