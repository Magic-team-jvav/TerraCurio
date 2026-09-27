package org.confluence.terra_curio.common.item.curio.combat;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.apache.commons.lang3.mutable.MutableFloat;
import org.confluence.lib.common.LibEffects;
import org.confluence.terra_curio.common.item.curio.BaseCurioItem;
import org.confluence.terra_curio.util.CuriosUtils;
import org.confluence.terra_curio.util.TCUtils;
import top.theillusivec4.curios.api.SlotContext;

public class PaladinsShield extends BaseCurioItem {
    public PaladinsShield(Builder builder) {
        super(builder);
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        if (slotContext.entity() instanceof ServerPlayer serverPlayer && serverPlayer.level().getGameTime() % 200 == 0) {
            Object team = TCUtils.getTeam(serverPlayer);
            for (Player player : serverPlayer.level().players()) {
                if (TCUtils.getTeam(player) != team) continue;
                player.addEffect(new MobEffectInstance(LibEffects.PALADINS_SHIELD, 600, player == serverPlayer ? 1 : 0));
            }
        }
    }

    @Override
    public boolean canEquip(ItemStack stack, EquipmentSlot armorType, LivingEntity entity) {
        return CuriosUtils.noSameCurio(entity, PaladinsShield.class);
    }

    public static float apply(LivingEntity living, DamageSource damageSource, float amount) {
        if (living instanceof ServerPlayer serverPlayer && !isOwner(serverPlayer)) {
            MutableFloat atomic = new MutableFloat(amount);
            Object team = TCUtils.getTeam(serverPlayer);
            serverPlayer.level().players().stream().filter(player -> player != serverPlayer && // player不是自己
                    player != damageSource.getEntity() && // player不是给自己造成过伤害的
                    TCUtils.getTeam(player) == team && // player的队伍与自己的相同
                    player.getHealth() / player.getMaxHealth() > 0.25F && // player血量大于最大血量的25%
                    isOwner(player) && // player拥有圣骑士盾
                    player.distanceToSqr(serverPlayer) < 1024.0 // player与自己的距离在32米内
            ).min((playerA, playerB) -> (int) (playerA.distanceToSqr(serverPlayer) - playerB.distanceToSqr(serverPlayer))).ifPresent(player -> {
                float damage = amount * 0.25F;
                player.hurt(living.damageSources().playerAttack(serverPlayer), damage);
                atomic.subtract(damage);
            });
            return atomic.getValue();
        }
        return amount;
    }

    public static boolean isOwner(LivingEntity living) {
        MobEffectInstance effect = living.getEffect(LibEffects.PALADINS_SHIELD);
        return effect != null && effect.getAmplifier() != 0;
    }
}
