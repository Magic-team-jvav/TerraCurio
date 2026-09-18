package org.confluence.terra_curio.network.s2c;

import io.netty.buffer.ByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import org.confluence.terra_curio.TerraCurio;
import org.confluence.terra_curio.mixed.ITCLivingEntity;
import org.mesdag.particlestorm.particle.ParticleEmitter;
import org.mesdag.portlib.diff.Diff;
import org.mesdag.portlib.network.IPortPacket;
import org.mesdag.portlib.network.PortPacketDistributor;
import org.mesdag.portlib.network.codec.PortStreamCodec;

@Diff
public record RemoveCurioParticleEmitterPacketS2C(
        ResourceLocation particleId
) implements IPortPacket.S2C {
    public static final ResourceLocation ID = TerraCurio.asResource("remove_emitter");
    public static final PortStreamCodec<ByteBuf, RemoveCurioParticleEmitterPacketS2C> STREAM_CODEC = ResourceLocation.STREAM_CODEC
            .map(RemoveCurioParticleEmitterPacketS2C::new, RemoveCurioParticleEmitterPacketS2C::particleId);

    @Override
    public void work(Player player) {
        ParticleEmitter emitter = ITCLivingEntity.of(player).terra_curio$getOrCreateParticleEmitters().remove(particleId);
        if (emitter != null) {
            emitter.remove();
        }
    }

    @Override
    public ResourceLocation identifier() {
        return ID;
    }

    public static void sendToClient(ServerPlayer player, ResourceLocation particle) {
        PortPacketDistributor.sendToPlayer(player, new RemoveCurioParticleEmitterPacketS2C(particle));
    }
}
