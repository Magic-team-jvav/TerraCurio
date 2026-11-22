package org.confluence.terra_curio.common.item.curio.information;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.util.LibUtils;
import org.confluence.terra_curio.common.item.IMultiFunctionCouldEnable;
import org.confluence.terra_curio.common.item.curio.BaseCurioItem;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class MultiInfoCurioItem extends BaseCurioItem implements IMultiFunctionCouldEnable {
    public MultiInfoCurioItem(Builder builder) {
        super(builder);
    }

    @Override
    public Optional<TooltipComponent> getTooltipImage(ItemStack stack) {
        return Optional.ofNullable(getTooltipComponent(stack));
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        if (LibUtils.isPhysicalClient() && (ConfluenceMagicLib.CONFLUENCE_LOADED || !Screen.hasShiftDown())) {
            super.appendHoverText(stack, level, tooltipComponents, tooltipFlag);
        }
    }
}
