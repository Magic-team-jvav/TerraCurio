package org.confluence.terra_curio.common.item.curio.combat;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.confluence.lib.common.LibEffects;
import org.confluence.lib.util.LibEntityUtils;
import org.confluence.terra_curio.common.item.curio.BaseCurioItem;
import org.confluence.terra_curio.util.CuriosUtils;
import top.theillusivec4.curios.api.SlotContext;

public class PaladinsShield extends BaseCurioItem {
    public PaladinsShield(Builder builder) {
        super(builder);
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        if (slotContext.entity() instanceof ServerPlayer serverPlayer && serverPlayer.level().getGameTime() % 200 == 0) {
            Object team = LibEntityUtils.getTeam(serverPlayer);
            for (Player player : serverPlayer.level().players()) {
                if (LibEntityUtils.getTeam(player) != team) continue;
                player.addEffect(new MobEffectInstance(LibEffects.PALADINS_SHIELD, 600, player == serverPlayer ? 1 : 0));
            }
        }
    }

    @Override
    public boolean canEquip(ItemStack stack, EquipmentSlot armorType, LivingEntity entity) {
        return CuriosUtils.noSameCurio(entity, PaladinsShield.class);
    }
}
