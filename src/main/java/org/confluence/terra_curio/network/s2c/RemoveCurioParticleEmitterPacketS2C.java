package org.confluence.terra_curio.network.s2c;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.PacketDistributor;
import org.confluence.lib.network.IPacketS2C;
import org.confluence.terra_curio.TerraCurio;
import org.confluence.terra_curio.mixed.ITCLivingEntity;
import org.mesdag.particlestorm.particle.ParticleEmitter;

public record RemoveCurioParticleEmitterPacketS2C(
        ResourceLocation particleId
) implements IPacketS2C {
    public static final Type<RemoveCurioParticleEmitterPacketS2C> TYPE = new Type<>(TerraCurio.asResource("remove_emitter"));
    public static final StreamCodec<ByteBuf, RemoveCurioParticleEmitterPacketS2C> STREAM_CODEC = ResourceLocation.STREAM_CODEC
            .map(RemoveCurioParticleEmitterPacketS2C::new, RemoveCurioParticleEmitterPacketS2C::particleId);

    @Override
    public void work(Player player) {
        ParticleEmitter emitter = ITCLivingEntity.of(player).terra_curio$getOrCreateParticleEmitters().remove(particleId);
        if (emitter != null) {
            emitter.remove();
        }
    }

    @Override
    public Type<RemoveCurioParticleEmitterPacketS2C> type() {
        return TYPE;
    }

    public static void sendToClient(ServerPlayer player, ResourceLocation particle) {
        PacketDistributor.sendToPlayer(player, new RemoveCurioParticleEmitterPacketS2C(particle));
    }
}
