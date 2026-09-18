package org.confluence.terra_curio.client.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.Input;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.item.BlockItem;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.client.event.ComputeFovModifierEvent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.MovementInputUpdateEvent;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.TickEvent;
import org.confluence.terra_curio.client.TCClientConfigs;
import org.confluence.terra_curio.client.TCKeyBindings;
import org.confluence.terra_curio.client.handler.*;
import org.confluence.terra_curio.client.renderer.tooltip.MultiFunctionTooltip;
import org.confluence.terra_curio.mixin.client.accessor.MinecraftAccessor;
import org.confluence.terra_curio.network.c2s.ShootXBonePacketC2S;
import org.confluence.terra_curio.util.TCUtils;
import org.mesdag.portlib.event.PortEventHandler;
import org.mesdag.portlib.event.client.PortInputEvent;
import org.mesdag.portlib.event.client.PortScreenEvent;
import org.mesdag.portlib.network.PortPacketDistributor;

public final class TCGameClientEvents {
    public static void init() {
        PortEventHandler.addListener(TCGameClientEvents::clientTick$Post);
        PortEventHandler.addListener(TCGameClientEvents::clientPlayerNetwork$LoggingOut);
        PortEventHandler.addListener(TCGameClientEvents::movementInputUpdate);
        PortEventHandler.addListener(TCGameClientEvents::fov);
        PortEventHandler.addListener(TCGameClientEvents::interactionKeyMappingTriggered);
        PortEventHandler.addListener(TCGameClientEvents::input$MouseScrolling);
        PortEventHandler.addListener(TCGameClientEvents::screen$MouseScrolled$Pre);
    }

    private static void clientTick$Post(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.START) return;
        Minecraft minecraft = Minecraft.getInstance();
        LocalPlayer player = minecraft.player;
        if (player != null) {
            StepStoolHandler.handle(player);
            TCClientPacketHandler.handle(minecraft, player);
            InformationHandler.handle(player);
            ScopeFovHandler.handle(player);
            TCUtils.applyCthulhuSprinting(TCKeyBindings.CTHULHU_SPRINTING.get().isDown(), player);
        }
    }

    private static void clientPlayerNetwork$LoggingOut(ClientPlayerNetworkEvent.LoggingOut event) {
        StepStoolHandler.reset();
        TCClientPacketHandler.reset();
        InformationHandler.reset();
        PlayerJumpHandler.reset(true);
        PlayerClimbHandler.reset();
        PlayerSprintingHandler.reset();
        ScopeFovHandler.reset();
    }

    private static void movementInputUpdate(MovementInputUpdateEvent event) {
        LocalPlayer player = (LocalPlayer) event.getEntity();
        Input input = event.getInput();
        boolean jumping = input.jumping;

        PlayerJumpHandler.handle(player, jumping);
        PlayerClimbHandler.handle(player, input.getMoveVector(), jumping);

        if (TCClientPacketHandler.isHasTabi() /* confluence mixin here */) {
            PlayerSprintingHandler.handle(player, input);
        }
    }

    private static void fov(ComputeFovModifierEvent event) {
        if (ScopeFovHandler.isScoping()) {
            event.setNewFovModifier(ScopeFovHandler.getFovModifier());
        }
    }

    private static void interactionKeyMappingTriggered(InputEvent.InteractionKeyMappingTriggered event) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) return;
        if (TCClientConfigs.rightClickDelay && event.isUseItem() && player.getItemInHand(event.getHand()).getItem() instanceof BlockItem) {
            MinecraftAccessor instance = (MinecraftAccessor) Minecraft.getInstance();
            int delay = instance.getRightClickDelay() - TCClientPacketHandler.getRightClickSubtractor();
            instance.setRightClickDelay(Math.max(0, delay));
        }
        if (TCClientPacketHandler.isBoneGlove() && player.getMainHandItem().is(Tags.Items.TOOLS)) {
            PortPacketDistributor.sendToServer(ShootXBonePacketC2S.INSTANCE);
        }
    }

    private static void input$MouseScrolling(PortInputEvent.MouseScrollingEvent event) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player != null && ScopeFovHandler.isScoping()) {
            ScopeFovHandler.handleScroll(player, event.getScrollDeltaY());
            event.setCanceled(true);
        }
    }

    private static void screen$MouseScrolled$Pre(PortScreenEvent.MouseScrolled.Pre event) {
        if (MultiFunctionTooltip.isShowing) {
            MultiFunctionTooltip.mouseScrollY -= (int) event.getScrollDeltaY();
        } else {
            MultiFunctionTooltip.mouseScrollY = 0;
        }
    }
}
