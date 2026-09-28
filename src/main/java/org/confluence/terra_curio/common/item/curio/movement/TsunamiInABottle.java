package org.confluence.terra_curio.common.item.curio.movement;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import org.confluence.lib.mixed.ILibEntity;
import org.confluence.terra_curio.client.handler.PlayerJumpHandler;
import org.confluence.terra_curio.common.item.curio.BaseCurioItem;
import org.joml.Matrix4x3f;
import org.mesdag.particlestorm.particle.ParticleEmitter;

public class TsunamiInABottle extends BaseCurioItem {
    public TsunamiInABottle(Builder builder) {
        super(builder);
    }

    @Override
    protected void particleTick(LivingEntity living, ParticleEmitter emitter, ResourceLocation particle) {
        super.particleTick(living, emitter, particle);
        if (PlayerJumpHandler.isOnTsunamiJump) {
            emitter.active = true;
            PlayerJumpHandler.isOnTsunamiJump = false;
        } else {
            emitter.active = false;
        }

        if (emitter.active) {
            if (!emitter.isLocalSpace()) {
                emitter.setLocalSpace(new Matrix4x3f(), false);
            }
            if (ILibEntity.of(living).confluence$isShouldRot()) {
                emitter.getLocalSpace().setTranslation(0, living.getBbHeight(), 0);
            } else {
                emitter.getLocalSpace().setTranslation(0, 0, 0);
            }
        }
    }
}
