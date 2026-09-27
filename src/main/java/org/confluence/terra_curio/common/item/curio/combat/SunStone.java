package org.confluence.terra_curio.common.item.curio.combat;

import com.google.common.collect.Multimap;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import org.confluence.lib.common.LibAttributes;
import org.confluence.lib.common.LibEffects;
import org.confluence.lib.common.component.ModRarity;
import org.confluence.lib.util.LibDateUtils;
import org.confluence.terra_curio.common.item.curio.BaseCurioItem;
import top.theillusivec4.curios.api.SlotContext;

public class SunStone extends BaseCurioItem {
    public SunStone() {
        super(builder("sun_stone").rarity(ModRarity.LIME)
                .attribute(Attributes.ATTACK_SPEED, 0.1, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
                .attribute(LibAttributes.getAttackDamage(), 0.1, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
                .attribute(Attributes.ARMOR, 2.0, AttributeModifier.Operation.ADD_VALUE)
                .attribute(Attributes.BLOCK_BREAK_SPEED, 0.15, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
                .attribute(LibAttributes.getCriticalChance(), 0.02, AttributeModifier.Operation.ADD_VALUE)
                .attribute(LibAttributes.getRangedDamage(), 0.1, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
                .attribute(LibAttributes.getMagicDamage(), 0.1, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
    }

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(SlotContext slotContext, ResourceLocation id, ItemStack stack) {
        LivingEntity living = slotContext.entity();
        return living != null && LibDateUtils.isDay(living.level()) ? super.getAttributeModifiers(slotContext, id, stack) : EMPTY_ATTRIBUTE;
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        LivingEntity living = slotContext.entity();
        if (LibDateUtils.isDay(living.level())) {
            LibEffects.healPerSecond(living, 0.4F);
        }
    }
}
