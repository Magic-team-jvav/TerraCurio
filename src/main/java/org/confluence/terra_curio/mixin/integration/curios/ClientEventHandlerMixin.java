package org.confluence.terra_curio.mixin.integration.curios;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.ChatFormatting;
import net.minecraft.world.entity.ai.attributes.Attribute;
import org.mesdag.portlib.diff.IPortAttribute;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import top.theillusivec4.curios.client.ClientEventHandler;

@Mixin(value = ClientEventHandler.class, remap = false, priority = 1500)
public abstract class ClientEventHandlerMixin {
    @ModifyArg(method = "onTooltip", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/chat/MutableComponent;withStyle(Lnet/minecraft/ChatFormatting;)Lnet/minecraft/network/chat/MutableComponent;", ordinal = 6, remap = true), remap = false)
    private ChatFormatting replacePositive(ChatFormatting format, @Local Attribute attribute) {
        Attribute.Sentiment sentiment = IPortAttribute.of(attribute).portlib$getSentiment();
        if (sentiment != Attribute.Sentiment.POSITIVE) {
            return sentiment.getStyle(true);
        }
        return format;
    }

    @ModifyArg(method = "onTooltip", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/chat/MutableComponent;withStyle(Lnet/minecraft/ChatFormatting;)Lnet/minecraft/network/chat/MutableComponent;", ordinal = 7, remap = true), remap = false)
    private ChatFormatting replaceNegative(ChatFormatting format, @Local Attribute attribute) {
        Attribute.Sentiment sentiment = IPortAttribute.of(attribute).portlib$getSentiment();
        if (sentiment != Attribute.Sentiment.POSITIVE) {
            return sentiment.getStyle(false);
        }
        return format;
    }
}
