package org.confluence.terra_curio.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.confluence.terra_curio.mixed.ITCEntity;
import org.confluence.terra_curio.util.TCUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/// 目标类 `Entity` 的 TC 侧扩展（WP6c 第二步：**换成 1.20 TC 的形态**）。
///
/// 1.21 侧此前这一个类里混了**两条线**：
/// - 克苏鲁冲刺计时（TC 归属）—— 保留；
/// - 重力（`terra_curio$isShouldRot` / `setShouldRot` / `getDimensionHeight` 三个 `@Unique` 字段
///   + 读它们的 6 个 hook：`getOnPosAbove` / `getBoundingBox`(checkSupportingBlock) /
///   `updateFallDistance` / `modifyParticlePosY` / `modifyParticleSpeedY` / `flip`）—— **已全部删掉**：
///   1.20 的归属在 Lib，而 1.21 的 `Confluence-Magic-Lib` 里
///   `org.confluence.lib.mixin.EntityMixin`（`:96 cacheDimensionHeight`、`:101 getOnPosAbove`、
///   `:108 getBoundingBox`、`:117 updateFallDistance`、`:128 modifyParticlePosY`、
///   `:136 modifyParticleSpeedY`、`:141 flip`）**已经带着这 7 个 hook 在树里**，
///   留着就会对同一条注入点**各命中一次**。
/// - 同时去掉 `terra_curio$isPlayer`（1.20 没有这个成员，一律直接 `instanceof Player`）。
///
/// 基准：1.20 `TerraCurio/.../mixin/EntityMixin.java`（51 行）逐字，
/// 只把 `IEntity` 换成 `ITCEntity`。`resetLavaImmune` 的两个细节已核实：
/// 1.21 的 `TCUtils.applyLavaImmune(boolean, Entity)` 第 2 参是 **`Entity`**（1.20 是 `LivingEntity`），
/// 而 1.20 这份传的是 `living`（`LivingEntity`）—— 它是 `Entity` 的子类型，**逐字即可编译**，
/// 所以这里**保留 1.20 原样**（含 `instanceof LivingEntity` 守卫）：守卫本身就带着 1.20 的语义
/// （只有生物才走免疫结算），去掉它会把行为放宽到所有实体。
/// 另外 1.21 那份把「缓存 `dimensionHeight`」塞在了 `resetLavaImmune` 里 —— 那一行属于重力，
/// 已随重力一起消失（缓存改由 Lib 的 `EntityMixin#cacheDimensionHeight` 负责）。
@Mixin(Entity.class)
public abstract class EntityMixin implements ITCEntity {
    @Unique
    private int terra_curio$cthulhuSprintingTime = 0;

    @Override
    public int terra_curio$getCthulhuSprintingTime() {
        return terra_curio$cthulhuSprintingTime;
    }

    @Override
    public void terra_curio$setCthulhuSprintingTime(int amount) {
        this.terra_curio$cthulhuSprintingTime = amount;
    }

    @ModifyExpressionValue(method = "baseTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;isInLava()Z", ordinal = 1))
    private boolean resetLavaImmune(boolean original) {
        if (confluence$self() instanceof LivingEntity living) {
            return TCUtils.applyLavaImmune(original, living);
        }
        return original;
    }

    @Inject(method = "baseTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/profiling/ProfilerFiller;pop()V"))
    private void tickProfiler(CallbackInfo ci) {
        if (terra_curio$cthulhuSprintingTime > 0) this.terra_curio$cthulhuSprintingTime--;
    }

    @Inject(method = "push(Lnet/minecraft/world/entity/Entity;)V", at = @At("TAIL"))
    private void collidingCheck(Entity entity, CallbackInfo ci) {
        if (confluence$self() instanceof Player player) {
            TCUtils.applyCthulhuTouch(player, entity);
        } else if (entity instanceof Player player) {
            TCUtils.applyCthulhuTouch(player, confluence$self());
        }
    }
}
