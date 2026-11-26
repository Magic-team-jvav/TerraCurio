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
import org.confluence.terra_curio.client.model.entity.StepStoolModel;
import org.confluence.terra_curio.common.entity.StepStoolEntity;

public class StepStoolRenderer extends EntityRenderer<StepStoolEntity> {
    private static final ResourceLocation TEXTURE = TerraCurio.asResource("textures/entity/model/step_stool.png");

    private final StepStoolModel model;

    public StepStoolRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new StepStoolModel((context.bakeLayer(StepStoolModel.LAYER_LOCATION)));
    }

    @Override
    public ResourceLocation getTextureLocation(StepStoolEntity entity) {
        return TEXTURE;
    }

    @Override
    public void render(StepStoolEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();
        poseStack.translate(0.0F, 1.5F, 0.0F);
        poseStack.mulPose(Axis.ZP.rotation(Mth.PI));
        for (int i = 0; i < entity.getStep(); i++) {
            model.renderToBuffer(poseStack, buffer.getBuffer(model.renderType(getTextureLocation(entity))), packedLight, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
            poseStack.translate(0.0F, -1.0F, 0.0F);
        }
        poseStack.popPose();
    }
}
