package org.confluence.terra_curio.client.event;


import net.minecraft.client.Minecraft;
import net.minecraft.client.player.Input;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.item.BlockItem;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.network.PacketDistributor;
import org.confluence.lib.client.handler.GravitationHandler;
import org.confluence.terra_curio.TerraCurio;
import org.confluence.terra_curio.client.TCClientConfigs;
import org.confluence.terra_curio.client.TCKeyBindings;
import org.confluence.terra_curio.client.handler.*;
import org.confluence.terra_curio.client.renderer.accessory.BalloonPhysicsGroup;
import org.confluence.terra_curio.client.renderer.tooltip.MultiFunctionTooltip;
import org.confluence.terra_curio.mixin.client.accessor.MinecraftAccessor;
import org.confluence.terra_curio.network.c2s.ShootXBonePacketC2S;
import org.confluence.terra_curio.util.TCUtils;

@EventBusSubscriber(modid = TerraCurio.MODID, value = Dist.CLIENT)
public final class TCGameClientEvents {
    @SubscribeEvent
    public static void clientTick$Post(ClientTickEvent.Pre event) {
        Minecraft minecraft = Minecraft.getInstance();
        LocalPlayer player = minecraft.player;
        if (player != null) {
            // WP6c 第二步：`GravitationHandler.tryExpire(player)` 已归 Lib 的
            // `LibClientGameEvents`（1.20 的驱动点在 Lib 那一侧），TC 不再驱动重力。
            StepStoolHandler.handle(player);
            TCClientPacketHandler.handle(minecraft, player);
            InformationHandler.handle(player);
            ScopeFovHandler.handle(player);
            TCUtils.applyCthulhuSprinting(TCKeyBindings.CTHULHU_SPRINTING.get().isDown(), player);
        }
    }

    @SubscribeEvent
    public static void clientPlayerNetwork$LoggingOut(ClientPlayerNetworkEvent.LoggingOut event) {
        // WP6c 第二步：`GravitationHandler.reset()` 已归 Lib（1.20 的 `LibClientGameEvents`）。
        StepStoolHandler.reset();
        TCClientPacketHandler.reset();
        InformationHandler.reset();
        PlayerJumpHandler.reset(true);
        PlayerClimbHandler.reset();
        PlayerSprintingHandler.reset();
        ScopeFovHandler.reset();
        BalloonPhysicsGroup.reset();
    }

    @SubscribeEvent
    public static void movementInputUpdate(MovementInputUpdateEvent event) {
        LocalPlayer player = (LocalPlayer) event.getEntity();
        Input input = event.getInput();
        boolean jumping = input.jumping;

        // WP6c 第二步：原来这里有一整段重力驱动
        // （`GravitationHandler.force/handle/expire` + `isHasGlobe` 三分支，判 `LibEffects.GRAVITATION`）
        // —— 1.20 的归属在 Lib，1.21 侧已由 Lib 的 `LibClientGameEvents` 驱动，
        // 故按任务书整段删除（`LibEffects` import 一并移除，已成未用）。

        PlayerJumpHandler.handle(player, jumping);
        PlayerClimbHandler.handle(player, input.getMoveVector(), jumping);

        if (TCClientPacketHandler.isHasTabi() /* confluence mixin here */) {
            PlayerSprintingHandler.handle(player, input);
        }
    }

    @SubscribeEvent
    public static void cameraSetup(ViewportEvent.ComputeCameraAngles event) {
        // **读状态**的调用保留（1.20 的 `cameraSetup` 也读 Lib 的 `isShouldRot()`），
        // 只把 import 换成 Lib 的 `GravitationHandler`。
        if (GravitationHandler.isShouldRot()) {
            event.setRoll(180.0F);
        }
    }

    @SubscribeEvent
    public static void fov(ComputeFovModifierEvent event) {
        if (ScopeFovHandler.isScoping()) {
            event.setNewFovModifier(ScopeFovHandler.getFovModifier());
        }
    }

    @SubscribeEvent
    public static void interactionKeyMappingTriggered(InputEvent.InteractionKeyMappingTriggered event) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) return;
        if (TCClientConfigs.rightClickDelay && event.isUseItem() && player.getItemInHand(event.getHand()).getItem() instanceof BlockItem) {
            MinecraftAccessor instance = (MinecraftAccessor) Minecraft.getInstance();
            int delay = instance.getRightClickDelay() - TCClientPacketHandler.getRightClickSubtractor();
            instance.setRightClickDelay(Math.max(0, delay));
        }
        if (TCClientPacketHandler.isBoneGlove() && player.getMainHandItem().is(Tags.Items.TOOLS)) {
            PacketDistributor.sendToServer(ShootXBonePacketC2S.INSTANCE);
        }
    }

    @SubscribeEvent
    public static void input$MouseScrolling(InputEvent.MouseScrollingEvent event) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player != null && ScopeFovHandler.isScoping()) {
            ScopeFovHandler.handleScroll(player, event.getScrollDeltaY());
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void screen$MouseScrolled$Pre(ScreenEvent.MouseScrolled.Pre event) {
        if (MultiFunctionTooltip.isShowing) {
            MultiFunctionTooltip.mouseScrollY -= (int) event.getScrollDeltaY();
        } else {
            MultiFunctionTooltip.mouseScrollY = 0;
        }
    }
}
