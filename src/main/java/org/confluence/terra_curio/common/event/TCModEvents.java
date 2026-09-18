package org.confluence.terra_curio.common.event;

import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import org.confluence.terra_curio.TerraCurio;
import org.confluence.terra_curio.common.TCCommonConfigs;
import org.confluence.terra_curio.network.InfoDisablePacket;
import org.confluence.terra_curio.network.c2s.*;
import org.confluence.terra_curio.network.s2c.*;
import org.mesdag.portlib.event.PortEventHandler;
import org.mesdag.portlib.event.lifecycle.PortFMLCommonSetupEvent;
import org.mesdag.portlib.event.network.PortRegisterPayloadHandlersEvent;

public final class TCModEvents {
    public static void init() {
        PortEventHandler.addListener(TCModEvents::commonSetup);
        PortEventHandler.addListener(TCModEvents::modConfig$Reloading);
        PortEventHandler.addListener(TCModEvents::registerPayloadHandlers);
    }

    private static void commonSetup(PortFMLCommonSetupEvent event) {
        event.enqueueWork(TCCommonConfigs::onLoad);
    }

    private static void modConfig$Reloading(ModConfigEvent.Reloading event) {
        if (event.getConfig().getType() == ModConfig.Type.COMMON && TerraCurio.MODID.equals(event.getConfig().getModId())) {
            TCCommonConfigs.onLoad();
        }
    }

    private static void registerPayloadHandlers(PortRegisterPayloadHandlersEvent event) {
        event.registrar("1")
                .registerInGameC2S(StepStoolSteppingPacketC2S.class, StepStoolSteppingPacketC2S.ID, StepStoolSteppingPacketC2S.STREAM_CODEC)
                .registerInGameC2S(PlayerJumpPacketC2S.class, PlayerJumpPacketC2S.ID, PlayerJumpPacketC2S.STREAM_CODEC)
                .registerInGameC2S(SpeedBootsNBTPacketC2S.class, SpeedBootsNBTPacketC2S.ID, SpeedBootsNBTPacketC2S.STREAM_CODEC)
                .registerInGameC2S(PlayerSprintPacketC2S.class, PlayerSprintPacketC2S.ID, PlayerSprintPacketC2S.STREAM_CODEC)
                .registerInGameC2S(ShootXBonePacketC2S.class, ShootXBonePacketC2S.ID, ShootXBonePacketC2S.STREAM_CODEC)
                .registerInGameC2S(RamRuneFallPacketC2S.class, RamRuneFallPacketC2S.ID, RamRuneFallPacketC2S.STREAM_CODEC)

                .registerInGameS2C(CurioExistsPacketS2C.class, CurioExistsPacketS2C.ID, CurioExistsPacketS2C.STREAM_CODEC)
                .registerInGameS2C(EntityKilledPacketS2C.class, EntityKilledPacketS2C.ID, EntityKilledPacketS2C.STREAM_CODEC)
                .registerInGameS2C(InfoCurioCheckPacketS2C.class, InfoCurioCheckPacketS2C.ID, InfoCurioCheckPacketS2C.STREAM_CODEC)
                .registerInGameS2C(StepStoolSteppingPacketS2C.class, StepStoolSteppingPacketS2C.ID, StepStoolSteppingPacketS2C.STREAM_CODEC)
                .registerInGameS2C(PlayerJumpPacketS2C.class, PlayerJumpPacketS2C.ID, PlayerJumpPacketS2C.STREAM_CODEC)
                .registerInGameS2C(PlayerFlyPacketS2C.class, PlayerFlyPacketS2C.ID, PlayerFlyPacketS2C.STREAM_CODEC)
                .registerInGameS2C(PlayerClimbPacketS2C.class, PlayerClimbPacketS2C.ID, PlayerClimbPacketS2C.STREAM_CODEC)
                .registerInGameS2C(RightClickSubtractorPacketS2C.class, RightClickSubtractorPacketS2C.ID, RightClickSubtractorPacketS2C.STREAM_CODEC)
                .registerInGameS2C(SetItemEntityPickupDelayPacketS2C.class, SetItemEntityPickupDelayPacketS2C.ID, SetItemEntityPickupDelayPacketS2C.STREAM_CODEC)
                .registerInGameS2C(BroadcastRenderPacketS2C.class, BroadcastRenderPacketS2C.ID, BroadcastRenderPacketS2C.STREAM_CODEC)
                .registerInGameS2C(InfiniteFlightPacketS2C.class, InfiniteFlightPacketS2C.ID, InfiniteFlightPacketS2C.STREAM_CODEC)
                .registerInGameS2C(PlayerJumpTriggeredPacketS2C.class, PlayerJumpTriggeredPacketS2C.ID, PlayerJumpTriggeredPacketS2C.STREAM_CODEC)
                .registerInGameS2C(FluidWalkUpdatePacketS2C.class, FluidWalkUpdatePacketS2C.ID, FluidWalkUpdatePacketS2C.STREAM_CODEC)
                .registerInGameS2C(RemoveCurioParticleEmitterPacketS2C.class, RemoveCurioParticleEmitterPacketS2C.ID, RemoveCurioParticleEmitterPacketS2C.STREAM_CODEC)

                .registerInGameBidirectional(InfoDisablePacket.class, InfoDisablePacket.ID, InfoDisablePacket.STREAM_CODEC);
    }
}
