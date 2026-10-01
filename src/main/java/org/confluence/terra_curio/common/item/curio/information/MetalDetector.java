package org.confluence.terra_curio.common.item.curio.information;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.confluence.lib.util.LibClientUtils;
import org.confluence.lib.util.LibUtils;
import org.confluence.terra_curio.client.TCKeyBindings;

import java.util.List;

public class MetalDetector extends MultiInfoCurioItem {
    public MetalDetector(Builder builder) {
        super(builder);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        if (LibUtils.isPhysicalClient()) {
            tooltipComponents.add(Component.translatable(
                    "tooltip.item.terra_curio.metal_detector.keybinding",
                    LibClientUtils.keyMappingComponent(TCKeyBindings.METAL_DETECTOR.get(), ChatFormatting.WHITE)
            ).withStyle(ChatFormatting.GRAY));
        }
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}
