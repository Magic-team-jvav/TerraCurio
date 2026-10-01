package org.confluence.terra_curio.common.item.curio.expert;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.confluence.lib.client.LibKeyBindings;
import org.confluence.lib.util.LibClientUtils;
import org.confluence.terra_curio.common.item.curio.BaseCurioItem;

import java.util.List;

/// 重力球（专家饰品）。悬浮提示里要显示「按哪个键翻转重力」。
///
/// WP6c（重力反转整条特性搬到 Magic-Lib）：按键映射从 TerraCurio 的 `TCKeyBindings.FLIP_GRAVITATION`
/// 改指到 **1.20 的归属** `org.confluence.lib.client.LibKeyBindings.FLIP_GRAVITATION`
/// （同一个查表对象，只是宿主模块换了；1.20 同名文件里这一行就是指向 Lib 的）。
public class GravityGlobe extends BaseCurioItem {
    public GravityGlobe(Builder builder) {
        super(builder);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        tooltipComponents.add(Component.translatable(
                "tooltip.item.terra_curio.gravity_globe.1",
                LibClientUtils.keyMappingComponent(LibKeyBindings.FLIP_GRAVITATION.get(), ChatFormatting.WHITE)
        ).withStyle(ChatFormatting.GRAY));
    }
}
