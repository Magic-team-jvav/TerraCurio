package org.confluence.terra_curio.client.event;

import net.minecraft.client.RecipeBookCategories;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterClientTooltipComponentFactoriesEvent;
import net.minecraftforge.client.event.RegisterRecipeBookCategoriesEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.confluence.terra_curio.TerraCurio;
import org.confluence.terra_curio.api.primitive.TooltipComponentsValue;
import org.confluence.terra_curio.client.CuriosClient;
import org.confluence.terra_curio.client.TCClientConfigs;
import org.confluence.terra_curio.client.gui.DivingHelmetOverlay;
import org.confluence.terra_curio.client.gui.InfoHudOverlay;
import org.confluence.terra_curio.client.gui.WorkshopScreen;
import org.confluence.terra_curio.client.model.entity.BeeProjectileModel;
import org.confluence.terra_curio.client.model.entity.StepStoolModel;
import org.confluence.terra_curio.client.model.entity.XBoneProjectileModel;
import org.confluence.terra_curio.client.renderer.entity.BeeProjectileRenderer;
import org.confluence.terra_curio.client.renderer.entity.StarCloakEntityRenderer;
import org.confluence.terra_curio.client.renderer.entity.StepStoolRenderer;
import org.confluence.terra_curio.client.renderer.entity.XBoneProjectileRenderer;
import org.confluence.terra_curio.client.renderer.tooltip.MultiFunctionTooltip;
import org.confluence.terra_curio.common.init.TCEntities;
import org.confluence.terra_curio.common.init.TCMenus;
import org.confluence.terra_curio.common.init.TCRecipes;
import org.mesdag.portlib.event.PortEventHandler;
import org.mesdag.portlib.event.client.PortRegisterGuiLayersEvent;
import org.mesdag.portlib.event.client.PortRegisterMenuScreensEvent;

public final class TCModClientEvent {
    public static void init() {
        PortEventHandler.addListener(TCModClientEvent::clientSetup);
        PortEventHandler.addListener(TCModClientEvent::modConfig$Loading);
        PortEventHandler.addListener(TCModClientEvent::modConfig$Reloading);
        PortEventHandler.addListener(TCModClientEvent::registerEntityLayers);
        PortEventHandler.addListener(TCModClientEvent::registerEntityRenderers);
        PortEventHandler.addListener(TCModClientEvent::registerGuiLayers);
        PortEventHandler.addListener(TCModClientEvent::registerMenuScreens);
        PortEventHandler.addListener(TCModClientEvent::registerClientTooltipComponentFactories);
        PortEventHandler.addListener(TCModClientEvent::registerRecipeBookCategories);
    }

    private static void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(CuriosClient::registerRenderers);
    }

    private static void modConfig$Loading(ModConfigEvent.Loading event) {
        if (event.getConfig().getType() == ModConfig.Type.CLIENT && TerraCurio.MODID.equals(event.getConfig().getModId())) {
            TCClientConfigs.onLoad();
        }
    }

    private static void modConfig$Reloading(ModConfigEvent.Reloading event) {
        if (event.getConfig().getType() == ModConfig.Type.CLIENT && TerraCurio.MODID.equals(event.getConfig().getModId())) {
            TCClientConfigs.onLoad();
        }
    }

    private static void registerEntityLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        CuriosClient.registerLayers(event::registerLayerDefinition);
        event.registerLayerDefinition(BeeProjectileModel.LAYER_LOCATION, BeeProjectileModel::createBodyLayer);
        event.registerLayerDefinition(XBoneProjectileModel.LAYER_LOCATION, XBoneProjectileModel::createBodyLayer);
        event.registerLayerDefinition(StepStoolModel.LAYER_LOCATION, StepStoolModel::createBodyLayer);
    }

    private static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(TCEntities.BEE_PROJECTILE.get(), BeeProjectileRenderer::new);
        event.registerEntityRenderer(TCEntities.STEP_STOOL.get(), StepStoolRenderer::new);
        event.registerEntityRenderer(TCEntities.STAR_CLOAK.get(), StarCloakEntityRenderer::new);
        event.registerEntityRenderer(TCEntities.X_BONE.get(), XBoneProjectileRenderer::new);
    }

    private static void registerGuiLayers(PortRegisterGuiLayersEvent event) {
        event.registerAboveAll(TerraCurio.asResource("info_hud"), new InfoHudOverlay());
        event.registerBelow(VanillaGuiOverlay.HELMET.id(), TerraCurio.asResource("diving_helmet"), new DivingHelmetOverlay());
    }

    private static void registerMenuScreens(PortRegisterMenuScreensEvent event) {
        event.register(TCMenus.WORKSHOP.get(), WorkshopScreen::new);
    }

    private static void registerClientTooltipComponentFactories(RegisterClientTooltipComponentFactoriesEvent event) {
        event.register(TooltipComponentsValue.Multi.class, multi -> new MultiFunctionTooltip(multi.storages()));
    }

    private static void registerRecipeBookCategories(RegisterRecipeBookCategoriesEvent event) {
        event.registerRecipeCategoryFinder(TCRecipes.WORKSHOP_TYPE.get(), recipeHolder -> RecipeBookCategories.UNKNOWN);
    }
}
