package org.confluence.terra_curio.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.confluence.terra_curio.TerraCurio;
import org.confluence.terra_curio.client.model.entity.BeeProjectileModel;
import org.confluence.terra_curio.common.entity.BeeProjectile;

public class BeeProjectileRenderer extends EntityRenderer<BeeProjectile> {
    private static final ResourceLocation TEXTURE = TerraCurio.asResource("textures/entity/projectile/bee_projectile.png");
    private final BeeProjectileModel model;

    public BeeProjectileRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new BeeProjectileModel(context.bakeLayer(BeeProjectileModel.LAYER_LOCATION));
    }

    @Override
    public ResourceLocation getTextureLocation(BeeProjectile entity) {
        return TEXTURE;
    }

    @Override
    public void render(BeeProjectile entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();
        poseStack.translate(0.00F, 0.125F, -0.125F);
        poseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(partialTick, entity.yRotO, entity.getYRot()) - 90.0F));
        poseStack.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(partialTick, entity.xRotO, entity.getXRot())));
        poseStack.mulPose(Axis.YP.rotation(-Mth.HALF_PI));
        if (entity.isGiant()) poseStack.scale(1.5F, 1.5F, 1.5F);
        model.renderToBuffer(poseStack, buffer.getBuffer(model.renderType(TEXTURE)), packedLight, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
        poseStack.popPose();
    }
}
