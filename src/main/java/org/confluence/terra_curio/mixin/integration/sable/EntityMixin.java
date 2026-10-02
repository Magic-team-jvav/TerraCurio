package org.confluence.terra_curio.mixin.integration.sable;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.confluence.lib.mixed.ILibEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

///
@Mixin(value = Entity.class, priority = 1100)
public abstract class EntityMixin {
    @Shadow
    public boolean verticalCollision;

    @SuppressWarnings("all")
    @WrapOperation(method = "move", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;setOnGroundWithMovement(ZLnet/minecraft/world/phys/Vec3;)V"))
    private void afterSable(Entity instance, boolean onGround, Vec3 movement, Operation<Void> original, @Local(argsOnly = true) Vec3 pos) {
        if (ILibEntity.of(instance).confluence$isShouldRot()) {
            onGround |= verticalCollision && pos.y > 0;
        }
        original.call(instance, onGround, movement);
    }
}
