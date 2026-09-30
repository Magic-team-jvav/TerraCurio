package org.confluence.terra_curio.mixin.client;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.LivingEntity;
import org.confluence.terra_curio.mixed.ITCClientLivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/// ⚠️ 与任务书给的处置不同，**整个类不删**：逐 hook 比对后的结论是「删一半、留一半」（已回报）。
///
/// 1.20 的归属是**两个不同的类**（本次 WP6c 第二步要还原的形态）：
/// - 重力 hook `upsideDown`（`isEntityUpsideDown`）→ 1.20 **Lib** 的
///   `mixin/client/LivingEntityRendererMixin`；1.21 的 Lib 里也**已经有**它
///   （`Confluence-Magic-Lib/.../mixin/client/LivingEntityRendererMixin.java:22 upsideDown`），
///   留着就会对同一条注入点各命中一次 → **本类里删掉**；
/// - 化妆 hook `couldRender`（`render` 里的 `renderToBuffer`）→ 1.20 **TC** 也有同名文件且**只有这一个 hook**
///   （`TerraCurio/.../mixin/client/LivingEntityRendererMixin.java`）→ **保留**
///   （属 TC 归属的克苏鲁/化妆线，与重力无关）。
///
/// 保留的 `couldRender` 用 1.21 的 `renderToBuffer(...III)V` 目标签名（1.20 是 `(...IIFFFF)V`，
/// 4 个 float 已被 1.21 合成一个 int color）—— 与本次改动前一致，未动。
@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin<T extends LivingEntity> {
    @WrapWithCondition(method = "render(Lnet/minecraft/world/entity/LivingEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/EntityModel;renderToBuffer(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;III)V"))
    private boolean couldRender(EntityModel<T> instance, PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color, @Local(argsOnly = true) T living) {
        boolean b = ((ITCClientLivingEntity) living).terra_curio$isShowingCosmetic();
        ((ITCClientLivingEntity) living).terra_curio$setShowingCosmetic(false);
        return !b;
    }
}
