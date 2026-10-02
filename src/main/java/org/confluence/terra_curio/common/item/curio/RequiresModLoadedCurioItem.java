package org.confluence.terra_curio.common.item.curio;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.fml.ModList;

import java.util.Arrays;
import java.util.List;

public class RequiresModLoadedCurioItem extends BaseCurioItem {
    private final Component[] required;
    private final boolean noneLoaded;

    public RequiresModLoadedCurioItem(Builder builder, String... required) {
        super(builder);
        this.required = Arrays.stream(required).map(modid -> Component.literal(modid).withStyle(ChatFormatting.RED)).toArray(Component[]::new);
        this.noneLoaded = Arrays.stream(required).noneMatch(ModList.get()::isLoaded);
    }

    public RequiresModLoadedCurioItem(Properties properties, String... required) {
        super(properties);
        this.required = Arrays.stream(required).map(modid -> Component.literal(modid).withStyle(ChatFormatting.RED)).toArray(Component[]::new);
        this.noneLoaded = Arrays.stream(required).noneMatch(ModList.get()::isLoaded);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        if (noneLoaded) {
            tooltipComponents.add(CommonComponents.EMPTY);
            MutableComponent component = Component.empty();
            for (int i = 0; i < required.length; i++) {
                Component require = required[i];
                component.append(require);
                if (i < required.length - 1) {
                    component.append(",");
                }
            }
            tooltipComponents.add(Component.translatable("tooltip.terra_curio.requires_mod_loaded", component).withStyle(ChatFormatting.DARK_RED));
        }
    }
}
