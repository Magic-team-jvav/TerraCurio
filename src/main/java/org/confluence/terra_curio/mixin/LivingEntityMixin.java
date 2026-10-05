package org.confluence.terra_curio.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import org.confluence.lib.mixed.SelfGetter;
import org.confluence.terra_curio.mixed.ITCLivingEntity;
import org.confluence.terra_curio.util.JumpParticleState;
import org.confluence.terra_curio.util.TCUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mesdag.particlestorm.particle.ParticleEmitter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
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
    private JumpParticleState terra_curio$jumpParticleState;
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

    @Override
    public @NotNull JumpParticleState terra_curio$getJumpParticleState() {
        if (terra_curio$jumpParticleState == null) {
            this.terra_curio$jumpParticleState = new JumpParticleState();
        }
        return terra_curio$jumpParticleState;
    }

    @ModifyReturnValue(method = "canFreeze", at = @At(value = "RETURN", ordinal = 1))
    private boolean checkFreeze(boolean original) {
        return TCUtils.applyFrozenImmune(confluence$self(), original);
    }

    @Inject(method = "travel", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;isInWater()Z", ordinal = 0))
    private void cacheFluidWalkable(CallbackInfo ci, @Local(name = "fluidstate") FluidState fluidstate, @Share("isFluidWalkable") LocalBooleanRef isFluidWalkable) {
        isFluidWalkable.set(TCUtils.isFluidWalkable(confluence$self(), fluidstate));
    }

    @ModifyExpressionValue(method = "travel", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/enchantment/EnchantmentHelper;getDepthStrider(Lnet/minecraft/world/entity/LivingEntity;)I"))
    private int skipEfficiency(int original, @Share("isFluidWalkable") LocalBooleanRef isFluidWalkable) {
        if (isFluidWalkable.get()) return 0;
        return original;
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

    @ModifyReturnValue(method = "canStandOnFluid", at = @At("RETURN"))
    private boolean standOnFluid(boolean original, FluidState fluidState) {
        if (!original && TCUtils.isFluidWalkable(confluence$self(), fluidState)) {
            return true;
        }
        return original;
    }

    @Inject(method = "onChangedBlock", at = @At("TAIL"))
    private void onMoved(CallbackInfo ci) {
        TCUtils.onChangedBlock(confluence$self(), (ServerLevel) confluence$self().level());
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
