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

///
///
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
