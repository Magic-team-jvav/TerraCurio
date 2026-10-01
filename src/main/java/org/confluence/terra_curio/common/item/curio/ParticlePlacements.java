package org.confluence.terra_curio.common.item.curio;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.FluidState;
import org.confluence.lib.mixed.ILibEntity;
import org.joml.Matrix4x3f;

import java.util.function.BiConsumer;

/// 预置的 emitter 本地空间放置器，供 [org.confluence.terra_curio.common.item.curio.BaseCurioItem.Builder#particle] 声明式使用。
///
/// 每个放置器负责把传入的 localSpace 矩阵变换到目标发射点：
/// 直接操作矩阵（旋转 + 平移），逐 tick 随实体状态应用。
/// 注意 [org.mesdag.particlestorm.particle.ParticleEmitter#updatePos]
/// 会用矩阵的平移列（m30/m31/m32）计算 emitter 世界坐标，因此平移必须写成世界空间偏移。
public final class ParticlePlacements {
    private ParticlePlacements() {}

    /// 嘴部：相对实体顶部基点向前 0.25、向下 0.3，并随头部朝向偏航旋转。
    /// 用于潜水装备的水下气泡等需要跟随面向的发射点。
    public static final BiConsumer<LivingEntity, Matrix4x3f> MOUTH = (living, space) -> {
        float baseY = ILibEntity.of(living).confluence$isShouldRot()
                ? living.getBbHeight() - living.getEyeHeight()
                : living.getEyeHeight();
        space.rotationY(-living.getYHeadRot() * Mth.DEG_TO_RAD).translate(0, baseY - 0.3F, 0.25F);
    };

    /// 脚部：不抬升，随头部朝向旋转（用于行走类地面特效，如花靴的草尘）。
    public static final BiConsumer<LivingEntity, Matrix4x3f> FEET = (living, space) ->
            space.rotationY(-living.getYHeadRot() * Mth.DEG_TO_RAD);

    /// 水面：把 emitter 放到脚下水方块的水面高度（用于游泳圈等漂浮效果，涟漪贴水面而非脚底）。
    public static final BiConsumer<LivingEntity, Matrix4x3f> WATER_SURFACE = (living, space) -> {
        BlockPos pos = living.blockPosition().above();
        Level level = living.level();
        FluidState fluid = level.getFluidState(pos);
        float surfaceY = pos.getY() + (fluid.isEmpty() ? 0.0F : fluid.getHeight(level, pos));
        space.rotationY(-living.getYHeadRot() * Mth.DEG_TO_RAD).translate(0, surfaceY - (float) living.getY(), 0);
    };
}
