package org.confluence.terra_curio.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.registries.DeferredItem;
import org.confluence.lib.util.LibUtils;
import org.confluence.terra_curio.TerraCurio;
import org.confluence.terra_curio.client.model.accessory.*;
import org.confluence.terra_curio.client.renderer.accessory.*;
import org.confluence.terra_curio.common.init.TCItems;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

public final class CuriosClient {
    public static final ResourceLocation DUMMY_ID = TerraCurio.asResource("");

    public static void registerRenderers() {
        EntityModelSet entityModels = Minecraft.getInstance().getEntityModels();
        CuriosRendererRegistry.register(TCItems.WORM_SCARF.get(), () -> new WormScarfRenderer(entityModels));
        CuriosRendererRegistry.register(TCItems.TERRASPARK_BOOTS.get(), () -> new TerrasparkBootsRenderer(entityModels));
        CuriosRendererRegistry.register(TCItems.DUNERIDER_BOOTS.get(), () -> new DuneriderBootsRenderer(entityModels));
        CuriosRendererRegistry.register(TCItems.MAGMA_SKULL.get(), () -> new MagmaSkullRenderer(entityModels));
        CuriosRendererRegistry.register(TCItems.FLURRY_BOOTS.get(), () -> new FlurryBootsRenderer(entityModels));
        CuriosRendererRegistry.register(TCItems.HERMES_BOOTS.get(), () -> new HermesBootsRenderer(entityModels));
        CuriosRendererRegistry.register(TCItems.SHIELD_OF_CTHULHU.get(), () -> new ShieldOfCthulhuRenderer(entityModels));
        CuriosRendererRegistry.register(TCItems.NEPTUNES_SHELL.get(), () -> new MermanRenderer(entityModels));
        CuriosRendererRegistry.register(TCItems.MOON_CHARM.get(), () -> new WerewolfRenderer(entityModels));
        CuriosRendererRegistry.register(TCItems.MOON_SHELL.get(), () -> new MoonShellRenderer(entityModels));
        CuriosRendererRegistry.register(TCItems.CELESTIAL_SHELL.get(), () -> new MoonShellRenderer(entityModels));
        normalWings(TCItems.FLEDGLING_WINGS);

        if (LibUtils.isDev()) {
            normalBalloon(TCItems.BLIZZARD_IN_A_BALLOON);
            normalBalloon(TCItems.CLOUD_IN_A_BALLOON);
            normalBalloon(TCItems.FART_IN_A_BALLOON);
            normalBalloon(TCItems.HONEY_BALLOON);
            normalBalloon(TCItems.SANDSTORM_IN_A_BALLOON);
            normalBalloon(TCItems.SHINY_RED_BALLOON);
            CuriosRendererRegistry.register(TCItems.BALLOON_PUFFERFISH.get(), () -> new NormalBalloonGeoRenderer(new AccessoryGeoModel(TCItems.BALLOON_PUFFERFISH.getId())));
            CuriosRendererRegistry.register(TCItems.SHARKRON_BALLOON.get(), () -> new NormalBalloonGeoRenderer(new AccessoryGeoModel(TCItems.SHARKRON_BALLOON.getId())));
            normalBalloon(TCItems.BUNDLE_OF_BALLOONS);

            // Horseshoe balloons — same balloon model + base balloon texture + horseshoe overlay
            horseshoeBalloon(TCItems.BUNDLE_OF_HORSESHOE_BALLOONS, TerraCurio.asResource("horseshoe"));
            horseshoeBalloon(TCItems.BLUE_HORSESHOE_BALLOON, TerraCurio.asResource("blue_horseshoe"));
            horseshoeBalloon(TCItems.YELLOW_HORSESHOE_BALLOON, TerraCurio.asResource("horseshoe"));
            horseshoeBalloon(TCItems.GREEN_HORSESHOE_BALLOON, TerraCurio.asResource("green_horseshoe"));
            horseshoeBalloon(TCItems.AMBER_HORSESHOE_BALLOON, TerraCurio.asResource("horseshoe"));
            // Pink and white have their own model
            CuriosRendererRegistry.register(TCItems.PINK_HORSESHOE_BALLOON.get(), () -> new NormalBalloonGeoRenderer(new AccessoryGeoModel(TCItems.PINK_HORSESHOE_BALLOON.getId())));
            CuriosRendererRegistry.register(TCItems.WHITE_HORSESHOE_BALLOON.get(), () -> new NormalBalloonGeoRenderer(new AccessoryGeoModel(TCItems.WHITE_HORSESHOE_BALLOON.getId())));

            LayeredGeoRenderer.registerAll();
            NormalBalloonGeoRenderer.registerAll();
        }
    }

    private static void normalWings(DeferredItem<?> item) {
        CuriosRendererRegistry.register(item.get(), () -> new WingsGeoRenderer(new NormalWingsGeoModel(item.getId())));
    }

    private static void normalBalloon(DeferredItem<?> item) {
        CuriosRendererRegistry.register(item.get(), () -> new NormalBalloonGeoRenderer(item.getId()));
    }

    private static void horseshoeBalloon(DeferredItem<?> item, ResourceLocation horseshoeTextureId) {
        CuriosRendererRegistry.register(item.get(), () -> new NormalBalloonGeoRenderer(DUMMY_ID).setHorseshoe(horseshoeTextureId));
    }

    public static void registerLayers(BiConsumer<ModelLayerLocation, Supplier<LayerDefinition>> layerDefinition) {
        layerDefinition.accept(WormScarfModel.LAYER_LOCATION, WormScarfModel::createBodyLayer);
        layerDefinition.accept(TerrasparkBootsModel.LAYER_LOCATION, TerrasparkBootsModel::createBodyLayer);
        layerDefinition.accept(DuneriderBootsModel.LAYER_LOCATION, DuneriderBootsModel::createBodyLayer);
        layerDefinition.accept(MagmaSkullModel.LAYER_LOCATION, MagmaSkullModel::createBodyLayer);
        layerDefinition.accept(FlurryBootsModel.LAYER_LOCATION, FlurryBootsModel::createBodyLayer);
        layerDefinition.accept(HermesBootsModel.LAYER_LOCATION, HermesBootsModel::createBodyLayer);
        layerDefinition.accept(ShieldOfCthulhuModel.LAYER_LOCATION, ShieldOfCthulhuModel::createBodyLayer);
        layerDefinition.accept(MermanModel.LAYER_LOCATION, MermanModel::createBodyLayer);
        layerDefinition.accept(WerewolfModel.LAYER_LOCATION, WerewolfModel::createBodyLayer);
    }

    public static void onlyFollowMainBody(LivingEntity livingEntity, ModelPart... renderers) {
        EntityRenderer<? super LivingEntity> render = Minecraft.getInstance().getEntityRenderDispatcher().getRenderer(livingEntity);
        if (render instanceof LivingEntityRenderer<?, ?> livingRenderer && livingRenderer.getModel() instanceof HumanoidModel<?> humanoidModel) {
            for (ModelPart renderer : renderers) {
                renderer.copyFrom(humanoidModel.body);
            }
        }
    }
}
