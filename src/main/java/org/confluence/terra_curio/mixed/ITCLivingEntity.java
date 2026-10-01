package org.confluence.terra_curio.mixed;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.material.FluidState;
import org.confluence.terra_curio.util.JumpParticleState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mesdag.particlestorm.particle.ParticleEmitter;

import java.util.Map;
import java.util.Set;

public interface ITCLivingEntity {
    void terra_curio$setLastWalkedFluidState(FluidState fluidState);

    @Nullable FluidState terra_curio$getLastWalkedFluidState();

    void terra_curio$resetLastWalkedFluidState(Set<FluidState> fluidStates);

    boolean terra_curio$isFluidWalkable(FluidState fluidState);

    void terra_curio$setTotemCooldown(int cooldown);

    int terra_curio$getTotemCooldown();

    @Nullable Map<ResourceLocation, ParticleEmitter> terra_curio$getParticleEmitters();

    @NotNull Map<ResourceLocation, ParticleEmitter> terra_curio$getOrCreateParticleEmitters();

    @NotNull JumpParticleState terra_curio$getJumpParticleState();

    static ITCLivingEntity of(LivingEntity living) {
        return (ITCLivingEntity) living;
    }
}
