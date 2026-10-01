package org.confluence.terra_curio.common.event;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import org.confluence.terra_curio.TerraCurio;
import org.confluence.terra_curio.common.init.TCCommonConfigs;
import org.confluence.terra_curio.network.InfoDisablePacket;
import org.confluence.terra_curio.network.c2s.*;
import org.confluence.terra_curio.network.s2c.*;

@EventBusSubscriber(modid = TerraCurio.MODID)
public final class TCModEvents {
    @SubscribeEvent
    public static void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(NeoForgeMod::enableMergedAttributeTooltips);
    }

    @SubscribeEvent
    public static void modConfig$Loading(ModConfigEvent.Loading event) {
        if (event.getConfig().getType() == ModConfig.Type.COMMON && TerraCurio.MODID.equals(event.getConfig().getModId())) {
            TCCommonConfigs.onLoad();
        }
    }

    @SubscribeEvent
    public static void modConfig$Reloading(ModConfigEvent.Reloading event) {
        if (event.getConfig().getType() == ModConfig.Type.COMMON && TerraCurio.MODID.equals(event.getConfig().getModId())) {
            TCCommonConfigs.onLoad();
        }
    }

    @SubscribeEvent
    public static void registerPayloadHandlers(RegisterPayloadHandlersEvent event) {
        event.registrar("1")
                // WP6c 第二步：`GravitationPacketC2S` 与 `BroadcastGravitationRotPacketS2C` 两个包
                // 已随重力整条特性搬到 Lib（1.20 归属），TC 侧不再注册它们的 handler。
                .playToServer(StepStoolSteppingPacketC2S.TYPE, StepStoolSteppingPacketC2S.STREAM_CODEC, StepStoolSteppingPacketC2S::handle)
                .playToServer(PlayerJumpPacketC2S.TYPE, PlayerJumpPacketC2S.STREAM_CODEC, PlayerJumpPacketC2S::handle)
                .playToServer(SpeedBootsNBTPacketC2S.TYPE, SpeedBootsNBTPacketC2S.STREAM_CODEC, SpeedBootsNBTPacketC2S::handle)
                .playToServer(PlayerSprintPacketC2S.TYPE, PlayerSprintPacketC2S.STREAM_CODEC, PlayerSprintPacketC2S::handle)
                .playToServer(ShootXBonePacketC2S.TYPE, ShootXBonePacketC2S.STREAM_CODEC, ShootXBonePacketC2S::handle)
                .playToServer(RamRuneFallPacketC2S.TYPE, RamRuneFallPacketC2S.STREAM_CODEC, RamRuneFallPacketC2S::handle)

                .playToClient(CurioExistsPacketS2C.TYPE, CurioExistsPacketS2C.STREAM_CODEC, CurioExistsPacketS2C::handle)
                .playToClient(EntityKilledPacketS2C.TYPE, EntityKilledPacketS2C.STREAM_CODEC, EntityKilledPacketS2C::handle)
                .playToClient(InfoCurioCheckPacketS2C.TYPE, InfoCurioCheckPacketS2C.STREAM_CODEC, InfoCurioCheckPacketS2C::handle)
                .playToClient(StepStoolSteppingPacketS2C.TYPE, StepStoolSteppingPacketS2C.STREAM_CODEC, StepStoolSteppingPacketS2C::handle)
                .playToClient(PlayerJumpPacketS2C.TYPE, PlayerJumpPacketS2C.STREAM_CODEC, PlayerJumpPacketS2C::handle)
                .playToClient(PlayerFlyPacketS2C.TYPE, PlayerFlyPacketS2C.STREAM_CODEC, PlayerFlyPacketS2C::handle)
                .playToClient(PlayerClimbPacketS2C.TYPE, PlayerClimbPacketS2C.STREAM_CODEC, PlayerClimbPacketS2C::handle)
                .playToClient(RightClickSubtractorPacketS2C.TYPE, RightClickSubtractorPacketS2C.STREAM_CODEC, RightClickSubtractorPacketS2C::handle)
                .playToClient(SetItemEntityPickupDelayPacketS2C.TYPE, SetItemEntityPickupDelayPacketS2C.STREAM_CODEC, SetItemEntityPickupDelayPacketS2C::handle)
                .playToClient(BroadcastRenderPacketS2C.TYPE, BroadcastRenderPacketS2C.STREAM_CODEC, BroadcastRenderPacketS2C::handle)
                .playToClient(InfiniteFlightPacketS2C.TYPE, InfiniteFlightPacketS2C.STREAM_CODEC, InfiniteFlightPacketS2C::handle)
                .playToClient(PlayerJumpTriggeredPacketS2C.TYPE, PlayerJumpTriggeredPacketS2C.STREAM_CODEC, PlayerJumpTriggeredPacketS2C::handle)
                .playToClient(FluidWalkUpdatePacketS2C.TYPE, FluidWalkUpdatePacketS2C.STREAM_CODEC, FluidWalkUpdatePacketS2C::handle)
                .playToClient(RemoveCurioParticleEmitterPacketS2C.TYPE, RemoveCurioParticleEmitterPacketS2C.STREAM_CODEC, RemoveCurioParticleEmitterPacketS2C::handle)
                .playToClient(FlushRenderLayerPacketS2C.TYPE, FlushRenderLayerPacketS2C.STREAM_CODEC, FlushRenderLayerPacketS2C::handle)

                .playBidirectional(InfoDisablePacket.TYPE, InfoDisablePacket.STREAM_CODEC, InfoDisablePacket::handle);
    }
}
