package org.confluence.terra_curio.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import org.confluence.lib.common.LibEffects;
import org.confluence.lib.mixed.SelfGetter;
import org.confluence.terra_curio.mixed.ITCLivingEntity;
import org.confluence.terra_curio.util.TCUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mesdag.particlestorm.particle.ParticleEmitter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin implements ITCLivingEntity, SelfGetter<LivingEntity> {
    @Unique
    private int terra_curio$totem_cooldown = -1;
    @Unique
    private Map<ResourceLocation, ParticleEmitter> terra_curio$emitters;
    @Unique
    private FluidState terra_curio$lastWalkedFluidState = null;
    @Unique
    private Set<FluidState> terra_curio$walkableFluidStates;

    @Override
    public void terra_curio$setLastWalkedFluidState(FluidState fluidState) {
        this.terra_curio$lastWalkedFluidState = fluidState;
    }

    @Override
    public @Nullable FluidState terra_curio$getLastWalkedFluidState() {
        return terra_curio$lastWalkedFluidState;
    }

    @Override
    public void terra_curio$resetLastWalkedFluidState(Set<FluidState> fluidStates) {
        this.terra_curio$lastWalkedFluidState = null;
        this.terra_curio$walkableFluidStates = fluidStates;
    }

    @Override
    public boolean terra_curio$isFluidWalkable(FluidState fluidState) {
        return terra_curio$walkableFluidStates != null && terra_curio$walkableFluidStates.contains(fluidState);
    }

    @Override
    public void terra_curio$setTotemCooldown(int cooldown) {
        this.terra_curio$totem_cooldown = cooldown;
    }

    @Override
    public int terra_curio$getTotemCooldown() {
        return terra_curio$totem_cooldown;
    }

    @Override
    public @Nullable Map<ResourceLocation, ParticleEmitter> terra_curio$getParticleEmitters() {
        return terra_curio$emitters;
    }

    @Override
    public @NotNull Map<ResourceLocation, ParticleEmitter> terra_curio$getOrCreateParticleEmitters() {
        if (terra_curio$emitters == null) {
            this.terra_curio$emitters = new Hashtable<>();
        }
        return terra_curio$emitters;
    }

    @Shadow
    public abstract boolean hasEffect(Holder<MobEffect> effect);

    @ModifyReturnValue(method = "canFreeze", at = @At(value = "RETURN", ordinal = 1))
    private boolean checkFreeze(boolean original) {
        return TCUtils.applyFrozenImmune(confluence$self(), original);
    }

    /// `travel` 的 `@ModifyVariable(HEAD, argsOnly)`：**只保留 `confused` 那半**。
    ///
    /// WP6c 第二步处置（逐 hook 比对）：
    /// - 重力那半（`isShouldRot()` → `new Vec3(-vec3.x, vec3.y, vec3.z)`）**已删** ——
    ///   1.20 归属 Lib，1.21 的 Lib 里已经有它
    ///   （`Confluence-Magic-Lib/.../mixin/LivingEntityMixin.java:61 reversed`），留着会重复变换；
    /// - `confused` 那半**保留**：1.21 的 Lib `LivingEntityMixin` 目前只有
    ///   `armorPenetration` / `modifyParticlePosY` / `reversed` **三个** hook，
    ///   **没有** 1.20 Lib 的 `confused`（1.20 Lib `LivingEntityMixin:39-45`）——
    ///   删掉就会丢「迷乱效果反转移动」的行为，所以先留在 TC 侧。
    ///   ⚠️ 若之后 Lib 按 1.20 补上 `confused`，这两处会对同一注入点各命中一次
    ///   （`reverse()` 执行两遍 = 等于不反转），**届时必须删掉本 hook**（已回报）。
    @ModifyVariable(method = "travel", at = @At("HEAD"), argsOnly = true)
    private Vec3 confused(Vec3 vec3) {
        if (hasEffect(LibEffects.CONFUSED)) {
            return vec3.reverse();
        }
        return vec3;
    }

    @Inject(method = "travel", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;isInWater()Z", ordinal = 0))
    private void cacheFluidWalkable(CallbackInfo ci, @Local FluidState fluidState, @Share("isFluidWalkable") LocalBooleanRef isFluidWalkable) {
        isFluidWalkable.set(TCUtils.isFluidWalkable(confluence$self(), fluidState));
    }

    @WrapOperation(method = "travel", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;getAttributeValue(Lnet/minecraft/core/Holder;)D", ordinal = 0))
    private double skipEfficiency(LivingEntity instance, Holder<Attribute> attribute, Operation<Double> original, @Share("isFluidWalkable") LocalBooleanRef isFluidWalkable) {
        if (isFluidWalkable.get()) return 0;
        return original.call(instance, attribute);
    }

    @WrapOperation(method = "travel", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/phys/Vec3;multiply(DDD)Lnet/minecraft/world/phys/Vec3;", ordinal = 0))
    private Vec3 notSlowdown(Vec3 instance, double factorX, double factorY, double factorZ, Operation<Vec3> original, @Share("isFluidWalkable") LocalBooleanRef isFluidWalkable) {
        if (isFluidWalkable.get()) {
            return original.call(instance, 0.94, factorY, 0.94);
        }
        return original.call(instance, factorX, factorY, factorZ);
    }

    @WrapOperation(method = "travel", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;canStandOnFluid(Lnet/minecraft/world/level/material/FluidState;)Z"))
    private boolean onFluid(LivingEntity instance, FluidState fluidState, Operation<Boolean> original, @Share("isFluidWalkable") LocalBooleanRef isFluidWalkable) {
        if (isFluidWalkable.get()) {
            return false;
        }
        return original.call(instance, fluidState);
    }

    @Inject(method = "canStandOnFluid", at = @At("RETURN"), cancellable = true)
    private void standOnFluid(FluidState fluidState, CallbackInfoReturnable<Boolean> cir) {
        if (!cir.getReturnValue() && TCUtils.isFluidWalkable(confluence$self(), fluidState)) {
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "onChangedBlock", at = @At("TAIL"))
    private void onMoved(CallbackInfo ci, @Local(argsOnly = true) ServerLevel level) {
        TCUtils.onChangedBlock(confluence$self(), level);
    }

    @Inject(method = "checkTotemDeathProtection", at = @At(value = "CONSTANT", args = "nullValue=true"), cancellable = true)
    private void useTotemAbility(CallbackInfoReturnable<Boolean> cir) {
        if (TCUtils.applyTotemAbility(confluence$self())) {
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void tick(CallbackInfo ci) {
        if (terra_curio$totem_cooldown > 0) {
            --this.terra_curio$totem_cooldown;
        }
    }

    @Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
    private void saveData(CompoundTag compound, CallbackInfo ci) {
        compound.putInt("terra_curio:totem_cooldown", terra_curio$totem_cooldown);
    }

    @Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
    private void readData(CompoundTag compound, CallbackInfo ci) {
        this.terra_curio$totem_cooldown = compound.getInt("terra_curio:totem_cooldown");
    }
}
