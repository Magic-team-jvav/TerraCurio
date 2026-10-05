package org.confluence.terra_curio.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.pathfinder.Path;
import org.confluence.lib.mixed.ILibEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// todo 反转AI
@Mixin(GroundPathNavigation.class)
public abstract class GroundPathNavigationMixin extends PathNavigation {
    public GroundPathNavigationMixin(Mob mob, Level level) {
        super(mob, level);
    }

    @Inject(method = "createPath(Lnet/minecraft/core/BlockPos;I)Lnet/minecraft/world/level/pathfinder/Path;", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/chunk/LevelChunk;getBlockState(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/state/BlockState;", ordinal = 0), cancellable = true)
    private void modifyBlockPos(BlockPos pos, int accuracy, CallbackInfoReturnable<Path> cir, @Local LevelChunk levelchunk) {
        if (!ILibEntity.of(mob).confluence$isShouldRot()) return;
        if (levelchunk.getBlockState(pos).isAir()) {
            BlockPos blockpos = pos.above();
            while (blockpos.getY() < this.level.getMaxBuildHeight() && levelchunk.getBlockState(blockpos).isAir()) {
                blockpos = blockpos.above();
            }
            if (blockpos.getY() < this.level.getMaxBuildHeight()) {
                cir.setReturnValue(super.createPath(blockpos.below(), accuracy));
                return;
            }
            while (blockpos.getY() > this.level.getMinBuildHeight() && levelchunk.getBlockState(blockpos).isAir()) {
                blockpos = blockpos.below();
            }
            pos = blockpos;
        }

        if (!levelchunk.getBlockState(pos).isSolid()) {
            cir.setReturnValue(super.createPath(pos, accuracy));
            return;
        }

        BlockPos blockpos1 = pos.below();
        while (blockpos1.getY() < this.level.getMinBuildHeight() && levelchunk.getBlockState(blockpos1).isSolid()) {
            blockpos1 = blockpos1.below();
        }
        cir.setReturnValue(super.createPath(blockpos1, accuracy));
    }
}
