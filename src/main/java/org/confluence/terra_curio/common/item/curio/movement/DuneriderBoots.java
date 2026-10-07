package org.confluence.terra_curio.common.item.curio.movement;

import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;

public class DuneriderBoots extends BaseSpeedBoots {
    public DuneriderBoots(String name) {
        super(1, 40, builder(name).tooltips(1).attribute(Attributes.STEP_HEIGHT, 0.5, AttributeModifier.Operation.ADD_VALUE));
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        LivingEntity living = slotContext.entity();
        if (living.getBlockStateOn().is(BlockTags.SAND)) {
            speedUp(slotContext, stack, 2, 70);
        } else {
            speedUp(slotContext, stack, 1, 40);
        }
    }

}
