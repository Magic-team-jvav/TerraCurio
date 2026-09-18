package org.confluence.terra_curio.network.c2s;

import io.netty.buffer.ByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.phys.Vec3;
import org.confluence.terra_curio.TerraCurio;
import org.confluence.terra_curio.common.item.curio.combat.RamRune;
import org.confluence.terra_curio.network.s2c.PlayerJumpTriggeredPacketS2C;
import org.mesdag.portlib.network.IPortPacket;
import org.mesdag.portlib.network.PortPacketDistributor;
import org.mesdag.portlib.network.codec.PortByteBufCodecs;
import org.mesdag.portlib.network.codec.PortStreamCodec;

public record PlayerJumpPacketC2S(byte jumpState, float motionY, byte jumpType) implements IPortPacket.C2S {
    public static final byte JUMP_BY_SELF = 1;
    public static final byte RESET_FALL_DISTANCE = 2;

    /** 无跳跃粒子（飞行、攀爬等） */
    public static final byte JUMP_NONE = 0;
    public static final byte JUMP_SANDSTORM = 1;
    public static final byte JUMP_BLIZZARD = 2;
    public static final byte JUMP_TSUNAMI = 4;
    public static final byte JUMP_CLOUD = 8;
    public static final byte JUMP_FART = 16;

    public static final ResourceLocation ID = TerraCurio.asResource("player_jump_c2s");
    public static final PortStreamCodec<ByteBuf, PlayerJumpPacketC2S> STREAM_CODEC = PortStreamCodec.composite(
            PortByteBufCodecs.BYTE, PlayerJumpPacketC2S::jumpState,
            PortByteBufCodecs.FLOAT, PlayerJumpPacketC2S::motionY,
            PortByteBufCodecs.BYTE, PlayerJumpPacketC2S::jumpType,
            PlayerJumpPacketC2S::new
    );

    @Override
    public ResourceLocation identifier() {
        return ID;
    }

    @Override
    public void work(ServerPlayer player) {
        player.hasImpulse = true;
        if ((jumpState & JUMP_BY_SELF) != 0) {
            player.awardStat(Stats.JUMP);
            player.causeFoodExhaustion(player.isSprinting() ? 0.2F : 0.05F);
        }
        if ((jumpState & RESET_FALL_DISTANCE) != 0) {
            player.resetFallDistance();
        }
        Vec3 motion = player.getDeltaMovement();
        player.setDeltaMovement(motion.x, motionY, motion.z);
        RamRune.cancelOnJump(player, motionY);
        if (jumpType != JUMP_NONE) {
            PlayerJumpTriggeredPacketS2C.sendToTrackingPlayers(player, jumpType);
        }
    }

    public static void sendToServer(byte jumpState, float motionY, byte jumpType) {
        PortPacketDistributor.sendToServer(new PlayerJumpPacketC2S(jumpState, motionY, jumpType));
    }
}
