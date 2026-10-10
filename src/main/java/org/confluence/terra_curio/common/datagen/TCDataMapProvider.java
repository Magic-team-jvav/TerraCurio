package org.confluence.terra_curio.common.datagen;

import it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.util.Unit;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.ForgeRegistries;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.LibAttributes;
import org.confluence.lib.common.LibTags;
import org.confluence.terra_curio.api.primitive.*;
import org.confluence.terra_curio.common.component.PrimitiveValueComponent;
import org.confluence.terra_curio.common.init.TCDataMaps;
import org.confluence.terra_curio.common.init.TCItems;
import org.confluence.terra_curio.common.init.TCTags;
import org.mesdag.portlib.datamap.PortDataMapProvider;
import org.mesdag.portlib.wrapper.world.entity.ai.attributes.PortAttributeModifier;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class TCDataMapProvider extends PortDataMapProvider {
    private final AdvancedBuilder<PrimitiveValueComponent, Item, PrimitiveValueComponent.Remover> builder = builder(TCDataMaps.ACCESSORIES);

    protected TCDataMapProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @Override
    protected void gather(HolderLookup.Provider provider) {
        // 已迁移
        add(TCItems.ICE_SKATES, helper -> helper.unit(TCItems.ICE$SPEED));
        add(TCItems.ANGLER_EARRING, helper -> {
            helper.entry(TCItems.ATTRIBUTES, AttributeModifiersValue.simple(Attributes.LUCK, helper.asId(), 10, PortAttributeModifier.Operation.ADD_VALUE));
            helper.of(TCItems.COMPONENTS, List.of());
        });
        add(TCItems.MAGMA_STONE, helper -> helper.unit(TCItems.FIRE$ATTACK));
        add(TCItems.SHACKLE, helper -> helper.entry(TCItems.ATTRIBUTES, AttributeModifiersValue.simple(Attributes.ARMOR, helper.asId(), 1, PortAttributeModifier.Operation.ADD_VALUE)));
        add(TCItems.BASE_POINT, helper -> {
            helper.unit(TCItems.FIRE$IMMUNE);
            helper.unit(TCItems.ICE$SPEED);
            helper.of(TCItems.MAY$FLY, MayFlyAbilityValue.of(helper.asKey(), 1.1F, 70, true, true));
            helper.of(TCItems.FLUID$WALK, Set.of(TCTags.WATER_LIKE_WALK, TCTags.LAVA_LIKE_WALK));
            helper.of(TCItems.LAVA$IMMUNE$TICKS, 200);
            helper.of(TCItems.LAVA$HURT$REDUCE, 0.75F);
            ResourceLocation id = helper.asId();
            helper.entry(TCItems.ATTRIBUTES, AttributeModifiersValue.builder()
                    .add(Attributes.MOVEMENT_SPEED, id, 0.16, PortAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
                    .add(Attributes.FALL_DAMAGE_MULTIPLIER.value(), id, -100, PortAttributeModifier.Operation.ADD_VALUE)
                    .add(Attributes.JUMP_STRENGTH_1211, id, 1, PortAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
                    .add(Attributes.STEP_HEIGHT.value(), id, 0.5, PortAttributeModifier.Operation.ADD_VALUE)
                    .build());
        });
        add(TCItems.EVERLASTING, helper -> {
            helper.unit(TCItems.FROZEN$IMMUNE);
            helper.unit(TCItems.FIRE$IMMUNE);
            helper.unit(TCItems.SHIELD$OF$CTHULHU);
            helper.of(TCItems.EFFECT$IMMUNITIES, Set.of(
                    MobEffects.POISON,
                    MobEffects.WITHER,
                    MobEffects.WEAKNESS,
                    MobEffects.HUNGER,
                    MobEffects.BLINDNESS,
                    MobEffects.DARKNESS,
                    MobEffects.MOVEMENT_SLOWDOWN,
                    MobEffects.CONFUSION,
                    MobEffects.DIG_SLOWDOWN,
                    MobEffects.LEVITATION
            ));
            helper.of(TCItems.TOTEM$WITH$COOLDOWN, 2400);
            helper.of(TCItems.INVULNERABLE$TICKS$MULTIPLIER, 2.0F);
            helper.entry(TCItems.ATTRIBUTES, AttributeModifiersValue.simple(LibAttributes.getDodgeChance().value(), helper.asId(), 0.1, PortAttributeModifier.Operation.ADD_VALUE));
        });

        // 免疫效果
        add(TCItems.BEZOAR, helper -> helper.of(TCItems.EFFECT$IMMUNITIES, Set.of(MobEffects.POISON))); // 牛黄 中毒
        add(TCItems.HOLY_WATER, helper -> helper.of(TCItems.EFFECT$IMMUNITIES, Set.of(MobEffects.WITHER))); // 圣水 凋零
        add(TCItems.DETOXIFICATION_CAPSULE, helper -> helper.of(TCItems.EFFECT$IMMUNITIES, Set.of(MobEffects.POISON, MobEffects.WITHER))); // 解毒囊
        add(TCItems.VITAMINS, helper -> helper.of(TCItems.EFFECT$IMMUNITIES, Set.of(MobEffects.WEAKNESS))); // 维生素 虚弱
        add(TCItems.ENERGY_BAR, helper -> helper.of(TCItems.EFFECT$IMMUNITIES, Set.of(MobEffects.HUNGER))); // 能量棒 饥饿
        add(TCItems.NUTRIENT_SOLUTION, helper -> helper.of(TCItems.EFFECT$IMMUNITIES, Set.of(MobEffects.WEAKNESS, MobEffects.HUNGER))); // 营养液
        add(TCItems.BLINDFOLD, helper -> helper.of(TCItems.EFFECT$IMMUNITIES, Set.of(MobEffects.BLINDNESS))); // 蒙眼布 失明
        add(TCItems.FLASHLIGHT, helper -> {
            helper.of(TCItems.EFFECT$IMMUNITIES, Set.of(MobEffects.DARKNESS));
            helper.of(TCItems.LUMINANCE, 14);
        }); // 手电筒 黑暗
        add(TCItems.SEARCHLIGHT, helper -> {
            helper.of(TCItems.EFFECT$IMMUNITIES, Set.of(MobEffects.BLINDNESS, MobEffects.DARKNESS));
            helper.of(TCItems.LUMINANCE, 15);
        }); // 探照灯
        add(TCItems.FAST_CLOCK, helper -> helper.of(TCItems.EFFECT$IMMUNITIES, Set.of(MobEffects.MOVEMENT_SLOWDOWN))); // 快走时钟 缓慢
        add(TCItems.TRIFOLD_MAP, helper -> helper.of(TCItems.EFFECT$IMMUNITIES, Set.of(MobEffects.CONFUSION))); // 三折地图 反胃
        add(TCItems.THE_PLAN, helper -> helper.of(TCItems.EFFECT$IMMUNITIES, Set.of(MobEffects.MOVEMENT_SLOWDOWN, MobEffects.CONFUSION))); // 计划书
        add(TCItems.HAND_DRILL, helper -> helper.of(TCItems.EFFECT$IMMUNITIES, Set.of(MobEffects.DIG_SLOWDOWN))); // 手钻 挖掘疲劳
        add(TCItems.SHOT_PUT, helper -> helper.of(TCItems.EFFECT$IMMUNITIES, Set.of(MobEffects.LEVITATION))); // 铅球 漂浮
        add(TCItems.EXPLORERS_EQUIPMENT, helper -> helper.of(TCItems.EFFECT$IMMUNITIES, Set.of(MobEffects.DIG_SLOWDOWN, MobEffects.LEVITATION))); // 探险家宝具
        add(TCItems.ANKH_CHARM, helper -> helper.of(TCItems.EFFECT$IMMUNITIES, Set.of(
                MobEffects.POISON, MobEffects.WITHER,
                MobEffects.WEAKNESS, MobEffects.HUNGER,
                MobEffects.BLINDNESS, MobEffects.DARKNESS,
                MobEffects.MOVEMENT_SLOWDOWN, MobEffects.CONFUSION,
                MobEffects.DIG_SLOWDOWN, MobEffects.LEVITATION))); // 十字章护身符
        add(TCItems.ANKH_SHIELD, helper -> {
            helper.unit(TCItems.FROZEN$IMMUNE);
            helper.of(TCItems.EFFECT$IMMUNITIES, Set.of(MobEffects.POISON, MobEffects.WITHER, MobEffects.WEAKNESS, MobEffects.HUNGER, MobEffects.BLINDNESS, MobEffects.DARKNESS, MobEffects.MOVEMENT_SLOWDOWN, MobEffects.CONFUSION, MobEffects.DIG_SLOWDOWN, MobEffects.LEVITATION));
            ResourceLocation id = helper.asId();
            helper.entry(TCItems.ATTRIBUTES, AttributeModifiersValue.builder()
                    .add(Attributes.KNOCKBACK_RESISTANCE, id, 1.0, PortAttributeModifier.Operation.ADD_VALUE)
                    .add(Attributes.ARMOR, id, 3.0, PortAttributeModifier.Operation.ADD_VALUE)
                    .build());
        }); // 十字章护盾
        add(TCItems.STAR_CLOAK, helper -> helper.of(TCItems.STAR$CLOCK, false)); // 星星斗篷
        add(TCItems.STAR_VEIL, helper -> {
            helper.of(TCItems.STAR$CLOCK, false);
            helper.of(TCItems.INVULNERABLE$TICKS$MULTIPLIER, 2.0F);
        }); // 星星面纱
        add(TCItems.BEE_CLOAK, helper -> {
            helper.unit(TCItems.HONEY$COMB);
            helper.of(TCItems.STAR$CLOCK, false);
            helper.of(TCItems.INVULNERABLE$TICKS$MULTIPLIER, 2.0F);
        }); // 蜜蜂斗篷

        // 战斗
        add(TCItems.BLACK_BELT, helper -> helper.entry(TCItems.ATTRIBUTES, AttributeModifiersValue.simple(LibAttributes.getDodgeChance().value(), helper.asId(), 0.1, PortAttributeModifier.Operation.ADD_VALUE))); // 黑腰带
        add(TCItems.SUN_STONE, helper -> helper.entry(TCItems.ATTRIBUTES, celestial(helper.asId(), 2.0))); // 太阳石
        add(TCItems.MOON_STONE, helper -> helper.entry(TCItems.ATTRIBUTES, celestial(helper.asId(), 4.0))); // 月亮石
        add(TCItems.CELESTIAL_STONE, helper -> helper.entry(TCItems.ATTRIBUTES, celestial(helper.asId(), 2.0))); // 天界石
        add(TCItems.MOON_CHARM, helper -> {
            ResourceLocation id = helper.asId();
            helper.entry(TCItems.ATTRIBUTES, AttributeModifiersValue.builder()
                    .add(LibAttributes.getCriticalChance().value(), id, 0.02, PortAttributeModifier.Operation.ADD_VALUE)
                    .add(LibAttributes.getAttackDamage().value(), id, 0.051, PortAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
                    .add(Attributes.ATTACK_SPEED, id, 0.051, PortAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
                    .add(Attributes.MOVEMENT_SPEED, id, 0.05, PortAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
                    .add(Attributes.ARMOR, id, 3.0, PortAttributeModifier.Operation.ADD_VALUE)
                    .add(Attributes.JUMP_STRENGTH_1211, id, 0.1, PortAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
                    .build());
        }); // 月光护身符
        add(TCItems.NEPTUNES_SHELL, helper -> {
            helper.unit(TCItems.NEPTUNES$SHELL);
            helper.entry(TCItems.ATTRIBUTES, AttributeModifiersValue.simple(Attributes.SUBMERGED_MINING_SPEED.value(), helper.asId(), 0.8, PortAttributeModifier.Operation.ADD_VALUE));
        }); // 海神贝壳
        add(TCItems.MOON_SHELL, helper -> {
            helper.unit(TCItems.NEPTUNES$SHELL);
            ResourceLocation id = helper.asId();
            helper.entry(TCItems.ATTRIBUTES, AttributeModifiersValue.builder()
                    .add(LibAttributes.getCriticalChance().value(), id, 0.02, PortAttributeModifier.Operation.ADD_VALUE)
                    .add(LibAttributes.getAttackDamage().value(), id, 0.051, PortAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
                    .add(Attributes.ATTACK_SPEED, id, 0.051, PortAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
                    .add(Attributes.MOVEMENT_SPEED, id, 0.05, PortAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
                    .add(Attributes.ARMOR, id, 3.0, PortAttributeModifier.Operation.ADD_VALUE)
                    .add(Attributes.JUMP_STRENGTH_1211, id, 0.1, PortAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
                    .build());
        }); // 月亮贝壳
        add(TCItems.CELESTIAL_SHELL, helper -> {
            helper.unit(TCItems.NEPTUNES$SHELL);
            ResourceLocation id = helper.asId();
            helper.entry(TCItems.ATTRIBUTES, AttributeModifiersValue.builder()
                    .add(Attributes.SUBMERGED_MINING_SPEED.value(), id, 0.8, PortAttributeModifier.Operation.ADD_VALUE)
                    .add(Attributes.ATTACK_SPEED, id, 0.1, PortAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
                    .add(LibAttributes.getAttackDamage().value(), id, 0.1, PortAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
                    .add(Attributes.ARMOR, id, 2.0, PortAttributeModifier.Operation.ADD_VALUE)
                    .add(Attributes.BLOCK_BREAK_SPEED.value(), id, 0.15, PortAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
                    .add(LibAttributes.getCriticalChance().value(), id, 0.02, PortAttributeModifier.Operation.ADD_VALUE)
                    .add(LibAttributes.getRangedDamage().value(), id, 0.1, PortAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
                    .add(LibAttributes.getMagicDamage().value(), id, 0.1, PortAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
                    .build());
        }); // 天界贝壳
        add(TCItems.COBALT_SHIELD, helper -> helper.entry(TCItems.ATTRIBUTES, AttributeModifiersValue.simple(Attributes.KNOCKBACK_RESISTANCE, helper.asId(), 1.0, PortAttributeModifier.Operation.ADD_VALUE))); // 钴护盾
        add(TCItems.CROSS_NECKLACE, helper -> helper.of(TCItems.INVULNERABLE$TICKS$MULTIPLIER, 2.0F)); // 十字项链
        add(TCItems.RANGER_EMBLEM, helper -> helper.entry(TCItems.ATTRIBUTES, AttributeModifiersValue.simple(LibAttributes.getRangedDamage().value(), helper.asId(), 0.15, PortAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL))); // 游侠徽章
        add(TCItems.WARRIOR_EMBLEM, helper -> helper.entry(TCItems.ATTRIBUTES, AttributeModifiersValue.simple(LibAttributes.getAttackDamage().value(), helper.asId(), 0.15, PortAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL))); // 战士徽章
        add(TCItems.SORCERER_EMBLEM, helper -> helper.entry(TCItems.ATTRIBUTES, AttributeModifiersValue.simple(LibAttributes.getMagicDamage().value(), helper.asId(), 0.15, PortAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL))); // 巫士徽章
        add(TCItems.AVENGER_EMBLEM, helper -> helper.entry(TCItems.ATTRIBUTES, fourClasses(helper.asId(), 0.12))); // 复仇者勋章
        add(TCItems.EYE_OF_THE_GOLEM, helper -> helper.entry(TCItems.ATTRIBUTES, AttributeModifiersValue.simple(LibAttributes.getCriticalChance().value(), helper.asId(), 0.1, PortAttributeModifier.Operation.ADD_VALUE))); // 石巨人之眼
        add(TCItems.DESTROYER_EMBLEM, helper -> {
            ResourceLocation id = helper.asId();
            helper.entry(TCItems.ATTRIBUTES, fourClassesBuilder(id, 0.1)
                    .add(LibAttributes.getCriticalChance().value(), id, 0.08, PortAttributeModifier.Operation.ADD_VALUE)
                    .build());
        }); // 毁灭者勋章
        add(TCItems.FERAL_CLAWS, helper -> {
            helper.unit(TCItems.AUTO$ATTACK);
            helper.entry(TCItems.ATTRIBUTES, AttributeModifiersValue.simple(Attributes.ATTACK_SPEED, helper.asId(), 0.12, PortAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
        }); // 狂爪手套
        add(TCItems.TITAN_GLOVE, helper -> {
            ResourceLocation id = helper.asId();
            helper.entry(TCItems.ATTRIBUTES, AttributeModifiersValue.builder()
                    .add(Attributes.ATTACK_KNOCKBACK, id, 1.0, PortAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
                    .add(Attributes.ENTITY_INTERACTION_RANGE.value(), id, 0.1, PortAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
                    .build());
        }); // 泰坦手套
        add(TCItems.POWER_GLOVE, helper -> {
            helper.unit(TCItems.AUTO$ATTACK);
            ResourceLocation id = helper.asId();
            helper.entry(TCItems.ATTRIBUTES, AttributeModifiersValue.builder()
                    .add(Attributes.ATTACK_SPEED, id, 0.12, PortAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
                    .add(Attributes.ATTACK_KNOCKBACK, id, 1.0, PortAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
                    .add(Attributes.ENTITY_INTERACTION_RANGE.value(), id, 0.1, PortAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
                    .build());
        }); // 强力手套
        add(TCItems.MECHANICAL_GLOVE, helper -> {
            helper.unit(TCItems.AUTO$ATTACK);
            ResourceLocation id = helper.asId();
            helper.entry(TCItems.ATTRIBUTES, AttributeModifiersValue.builder()
                    .add(LibAttributes.getAttackDamage().value(), id, 0.12, PortAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
                    .add(Attributes.ATTACK_SPEED, id, 0.12, PortAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
                    .add(Attributes.ATTACK_KNOCKBACK, id, 1.0, PortAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
                    .add(Attributes.ENTITY_INTERACTION_RANGE.value(), id, 0.1, PortAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
                    .build());
        }); // 机械手套
        add(TCItems.FIRE_GAUNTLET, helper -> {
            helper.unit(TCItems.AUTO$ATTACK);
            helper.unit(TCItems.FIRE$ATTACK);
            ResourceLocation id = helper.asId();
            helper.entry(TCItems.ATTRIBUTES, AttributeModifiersValue.builder()
                    .add(LibAttributes.getAttackDamage().value(), id, 0.12, PortAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
                    .add(Attributes.ATTACK_SPEED, id, 0.12, PortAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
                    .add(Attributes.ATTACK_KNOCKBACK, id, 1.0, PortAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
                    .add(Attributes.ENTITY_INTERACTION_RANGE.value(), id, 0.1, PortAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
                    .build());
        }); // 烈火手套
        add(TCItems.FLESH_KNUCKLES, helper -> {
            ResourceLocation id = helper.asId();
            helper.entry(TCItems.ATTRIBUTES, AttributeModifiersValue.builder()
                    .add(Attributes.ARMOR, id, 4.0, PortAttributeModifier.Operation.ADD_VALUE)
                    .add(ConfluenceMagicLib.AGGRO, id, 400, PortAttributeModifier.Operation.ADD_VALUE)
                    .build());
        }); // 血肉指虎
        add(TCItems.BERSERKERS_GLOVE, helper -> {
            helper.unit(TCItems.AUTO$ATTACK);
            ResourceLocation id = helper.asId();
            helper.entry(TCItems.ATTRIBUTES, AttributeModifiersValue.builder()
                    .add(Attributes.ARMOR, id, 4.0, PortAttributeModifier.Operation.ADD_VALUE)
                    .add(Attributes.ATTACK_SPEED, id, 0.12, PortAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
                    .add(Attributes.ATTACK_KNOCKBACK, id, 1.0, PortAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
                    .add(Attributes.ENTITY_INTERACTION_RANGE.value(), id, 0.1, PortAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
                    .add(ConfluenceMagicLib.AGGRO, id, 400, PortAttributeModifier.Operation.ADD_VALUE)
                    .build());
        }); // 狂战士手套
        add(TCItems.PALADINS_SHIELD, helper -> {
            ResourceLocation id = helper.asId();
            helper.entry(TCItems.ATTRIBUTES, AttributeModifiersValue.builder()
                    .add(Attributes.ARMOR, id, 3.0, PortAttributeModifier.Operation.ADD_VALUE)
                    .add(Attributes.KNOCKBACK_RESISTANCE, id, 1.0, PortAttributeModifier.Operation.ADD_VALUE)
                    .build());
        }); // 圣骑士护盾
        add(TCItems.HERO_SHIELD, helper -> {
            ResourceLocation id = helper.asId();
            helper.entry(TCItems.ATTRIBUTES, AttributeModifiersValue.builder()
                    .add(Attributes.ARMOR, id, 5.0, PortAttributeModifier.Operation.ADD_VALUE)
                    .add(Attributes.KNOCKBACK_RESISTANCE, id, 1.0, PortAttributeModifier.Operation.ADD_VALUE)
                    .add(ConfluenceMagicLib.AGGRO, id, 400, PortAttributeModifier.Operation.ADD_VALUE)
                    .build());
        }); // 英雄护盾
        add(TCItems.FROZEN_TURTLE_SHELL, helper -> helper.unit(TCItems.FROZEN$TURTLE$SHELL)); // 冰冻海龟壳
        add(TCItems.FROZEN_SHIELD, helper -> {
            helper.unit(TCItems.FROZEN$TURTLE$SHELL);
            ResourceLocation id = helper.asId();
            helper.entry(TCItems.ATTRIBUTES, AttributeModifiersValue.builder()
                    .add(Attributes.ARMOR, id, 3.0, PortAttributeModifier.Operation.ADD_VALUE)
                    .add(Attributes.KNOCKBACK_RESISTANCE, id, 1.0, PortAttributeModifier.Operation.ADD_VALUE)
                    .build());
        }); // 冰冻护盾
        add(TCItems.HONEY_COMB, helper -> helper.unit(TCItems.HONEY$COMB)); // 蜂窝
        add(TCItems.SHARK_TOOTH_NECKLACE, helper -> helper.entry(TCItems.ATTRIBUTES, AttributeModifiersValue.simple(LibAttributes.getArmorPenetration().value(), helper.asId(), 5.0, PortAttributeModifier.Operation.ADD_VALUE))); // 鲨牙项链
        add(TCItems.STINGER_NECKLACE, helper -> {
            helper.unit(TCItems.HONEY$COMB);
            helper.entry(TCItems.ATTRIBUTES, AttributeModifiersValue.simple(LibAttributes.getArmorPenetration().value(), helper.asId(), 5.0, PortAttributeModifier.Operation.ADD_VALUE));
        }); // 毒刺项链
        add(TCItems.SWEETHEART_NECKLACE, helper -> helper.unit(TCItems.HONEY$COMB)); // 甜心项链

        // 箭袋与瞄准镜
        add(TCItems.MAGIC_QUIVER, helper -> {
            helper.unit(TCItems.MAGIC$QUIVER);
            ResourceLocation id = helper.asId();
            helper.entry(TCItems.ATTRIBUTES, AttributeModifiersValue.builder()
                    .add(LibAttributes.getRangedDamage().value(), id, 0.1, PortAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
                    .add(LibAttributes.getRangedVelocity().value(), id, 0.2, PortAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
                    .build());
        }); // 魔法箭袋
        add(TCItems.MOLTEN_QUIVER, helper -> {
            helper.unit(TCItems.MAGIC$QUIVER);
            helper.unit(TCItems.IGNITE$ARROW);
            ResourceLocation id = helper.asId();
            helper.entry(TCItems.ATTRIBUTES, AttributeModifiersValue.builder()
                    .add(LibAttributes.getRangedDamage().value(), id, 0.1, PortAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
                    .add(LibAttributes.getRangedVelocity().value(), id, 0.2, PortAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
                    .build());
        }); // 熔火箭袋
        add(TCItems.STALKERS_QUIVER, helper -> {
            helper.unit(TCItems.MAGIC$QUIVER);
            ResourceLocation id = helper.asId();
            helper.entry(TCItems.ATTRIBUTES, AttributeModifiersValue.builder()
                    .add(LibAttributes.getRangedDamage().value(), id, 0.1, PortAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
                    .add(LibAttributes.getRangedVelocity().value(), id, 0.2, PortAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
                    .add(ConfluenceMagicLib.AGGRO, id, -400, PortAttributeModifier.Operation.ADD_VALUE)
                    .build());
        }); // 潜行者箭袋
        add(TCItems.RIFLE_SCOPE, helper -> helper.unit(TCItems.SCOPE)); // 步枪瞄准镜
        add(TCItems.SNIPER_SCOPE, helper -> {
            helper.unit(TCItems.SCOPE);
            ResourceLocation id = helper.asId();
            helper.entry(TCItems.ATTRIBUTES, AttributeModifiersValue.builder()
                    .add(LibAttributes.getCriticalChance().value(), id, 0.1, PortAttributeModifier.Operation.ADD_VALUE)
                    .add(LibAttributes.getRangedDamage().value(), id, 0.1, PortAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
                    .build());
        }); // 狙击镜
        add(TCItems.RECON_SCOPE, helper -> {
            helper.unit(TCItems.SCOPE);
            ResourceLocation id = helper.asId();
            helper.entry(TCItems.ATTRIBUTES, AttributeModifiersValue.builder()
                    .add(LibAttributes.getCriticalChance().value(), id, 0.1, PortAttributeModifier.Operation.ADD_VALUE)
                    .add(LibAttributes.getRangedDamage().value(), id, 0.1, PortAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
                    .add(ConfluenceMagicLib.AGGRO, id, -400, PortAttributeModifier.Operation.ADD_VALUE)
                    .build());
        }); // 侦察镜

        // 火与熔岩
        add(TCItems.OBSIDIAN_ROSE, helper -> helper.of(TCItems.LAVA$HURT$REDUCE, 0.5F)); // 黑曜石玫瑰
        add(TCItems.OBSIDIAN_SHIELD, helper -> {
            helper.unit(TCItems.FIRE$IMMUNE);
            ResourceLocation id = helper.asId();
            helper.entry(TCItems.ATTRIBUTES, AttributeModifiersValue.builder()
                    .add(Attributes.KNOCKBACK_RESISTANCE, id, 1.0, PortAttributeModifier.Operation.ADD_VALUE)
                    .add(Attributes.ARMOR, id, 1.0, PortAttributeModifier.Operation.ADD_VALUE)
                    .build());
        }); // 黑曜石护盾
        add(TCItems.OBSIDIAN_SKULL, helper -> {
            helper.unit(TCItems.FIRE$IMMUNE);
            helper.entry(TCItems.ATTRIBUTES, AttributeModifiersValue.simple(Attributes.ARMOR, helper.asId(), 1, PortAttributeModifier.Operation.ADD_VALUE));
        }); // 黑曜石骷髅头
        add(TCItems.MOLTEN_SKULL_ROSE, helper -> {
            helper.unit(TCItems.FIRE$IMMUNE);
            helper.of(TCItems.LAVA$IMMUNE$TICKS, 140);
            helper.of(TCItems.LAVA$HURT$REDUCE, 0.5F);
        }); // 熔火骷髅头玫瑰
        add(TCItems.OBSIDIAN_SKULL_ROSE, helper -> {
            helper.unit(TCItems.FIRE$IMMUNE);
            helper.of(TCItems.LAVA$HURT$REDUCE, 0.5F);
        }); // 黑曜石骷髅头玫瑰
        add(TCItems.HAND_WARMER, helper -> helper.unit(TCItems.FROZEN$IMMUNE)); // 暖手宝
        add(TCItems.PUTRID_SCENT, helper -> {
            ResourceLocation id = helper.asId();
            helper.entry(TCItems.ATTRIBUTES, AttributeModifiersValue.builder()
                    .add(LibAttributes.getAttackDamage().value(), id, 0.05, PortAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
                    .add(LibAttributes.getCriticalChance().value(), id, 0.05, PortAttributeModifier.Operation.ADD_VALUE)
                    .add(ConfluenceMagicLib.AGGRO, id, -400, PortAttributeModifier.Operation.ADD_VALUE)
                    .build());
        }); // 腐香囊

        // 建筑
        add(TCItems.TOOLBELT, helper -> helper.entry(TCItems.ATTRIBUTES, AttributeModifiersValue.simple(Attributes.BLOCK_INTERACTION_RANGE.value(), helper.asId(), 1.0, PortAttributeModifier.Operation.ADD_VALUE))); // 工具腰带
        add(TCItems.TOOLBOX, helper -> helper.entry(TCItems.ATTRIBUTES, AttributeModifiersValue.simple(Attributes.BLOCK_INTERACTION_RANGE.value(), helper.asId(), 1.0, PortAttributeModifier.Operation.ADD_VALUE))); // 工具箱
        add(TCItems.EXTENDO_GRIP, helper -> helper.entry(TCItems.ATTRIBUTES, AttributeModifiersValue.simple(Attributes.BLOCK_INTERACTION_RANGE.value(), helper.asId(), 3.0, PortAttributeModifier.Operation.ADD_VALUE))); // 加长握爪
        add(TCItems.PORTABLE_CEMENT_MIXER, helper -> helper.of(TCItems.RIGHT$CLICK$DELAY$SUBSTRACTOR, (byte) 1)); // 便携式水泥搅拌机
        add(TCItems.BRICK_LAYER, helper -> helper.of(TCItems.RIGHT$CLICK$DELAY$SUBSTRACTOR, (byte) 1)); // 砌砖刀
        add(TCItems.ARCHITECT_GIZMO_PACK, helper -> {
            helper.of(TCItems.RIGHT$CLICK$DELAY$SUBSTRACTOR, (byte) 2);
            helper.entry(TCItems.ATTRIBUTES, AttributeModifiersValue.simple(Attributes.BLOCK_INTERACTION_RANGE.value(), helper.asId(), 3.0, PortAttributeModifier.Operation.ADD_VALUE));
        }); // 建筑师发明背包
        add(TCItems.ANCIENT_CHISEL, helper -> helper.entry(TCItems.ATTRIBUTES, AttributeModifiersValue.simple(Attributes.BLOCK_BREAK_SPEED.value(), helper.asId(), 0.25, PortAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL))); // 远古凿子
        add(TCItems.HAND_OF_CREATION, helper -> {
            helper.of(TCItems.RIGHT$CLICK$DELAY$SUBSTRACTOR, (byte) 3);
            ResourceLocation id = helper.asId();
            helper.entry(TCItems.ATTRIBUTES, AttributeModifiersValue.builder()
                    .add(Attributes.BLOCK_INTERACTION_RANGE.value(), id, 3.0, PortAttributeModifier.Operation.ADD_VALUE)
                    .add(Attributes.BLOCK_BREAK_SPEED.value(), id, 0.25, PortAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
                    .add(ConfluenceMagicLib.PICKUP_RANGE, id, 6.25, PortAttributeModifier.Operation.ADD_VALUE)
                    .build());
        }); // 创造之手

        // 信息
        add(TCItems.COPPER_WATCH, helper -> helper.of(TCItems.INFORMATION, List.of(TCItems.HOUR$WATCH))); // 铜表
        add(TCItems.TIN_WATCH, helper -> helper.of(TCItems.INFORMATION, List.of(TCItems.HOUR$WATCH))); // 锡表
        add(TCItems.SILVER_WATCH, helper -> helper.of(TCItems.INFORMATION, List.of(TCItems.HALF$HOUR$WATCH))); // 银表
        add(TCItems.TUNGSTEN_WATCH, helper -> helper.of(TCItems.INFORMATION, List.of(TCItems.HALF$HOUR$WATCH))); // 钨表
        add(TCItems.GOLD_WATCH, helper -> helper.of(TCItems.INFORMATION, List.of(TCItems.MINUTE$WATCH))); // 金表
        add(TCItems.PLATINUM_WATCH, helper -> helper.of(TCItems.INFORMATION, List.of(TCItems.MINUTE$WATCH))); // 铂金表
        add(TCItems.DEPTH_METER, helper -> helper.of(TCItems.INFORMATION, List.of(TCItems.DEPTH$METER))); // 深度计
        add(TCItems.COMPASS, helper -> helper.of(TCItems.INFORMATION, List.of(TCItems.$COMPASS))); // 罗盘
        add(TCItems.RADAR, helper -> helper.of(TCItems.INFORMATION, List.of(TCItems.$RADAR))); // 雷达
        add(TCItems.LIFE_FORM_ANALYZER, helper -> helper.of(TCItems.INFORMATION, List.of(TCItems.LIFE$FORM$ANALYZER))); // 生命体分析机
        add(TCItems.TALLY_COUNTER, helper -> helper.of(TCItems.INFORMATION, List.of(TCItems.TALLY$COUNTER))); // 杀怪计数器
        add(TCItems.METAL_DETECTOR, helper -> helper.of(TCItems.INFORMATION, List.of(TCItems.METAL$DETECTOR))); // 金属探测器
        add(TCItems.STOPWATCH, helper -> helper.of(TCItems.INFORMATION, List.of(TCItems.$STOPWATCH))); // 秒表
        add(TCItems.DPS_METER, helper -> helper.of(TCItems.INFORMATION, List.of(TCItems.DPS$METER))); // 每秒伤害计数器
        add(TCItems.FISHERMANS_POCKET_GUIDE, helper -> helper.of(TCItems.INFORMATION, List.of(TCItems.FISHERMANS$POCKET$GUIDE))); // 渔民袖珍宝典
        add(TCItems.WEATHER_RADIO, helper -> helper.of(TCItems.INFORMATION, List.of(TCItems.WEATHER$RADIO))); // 天气收音机
        add(TCItems.SEXTANT, helper -> helper.of(TCItems.INFORMATION, List.of(TCItems.$SEXTANT))); // 六分仪
        add(TCItems.GPS, helper -> helper.of(TCItems.INFORMATION, List.of(TCItems.MINUTE$WATCH, TCItems.DEPTH$METER, TCItems.$COMPASS))); // 全球定位系统
        add(TCItems.REK_3000, helper -> helper.of(TCItems.INFORMATION, List.of(TCItems.$RADAR, TCItems.LIFE$FORM$ANALYZER, TCItems.TALLY$COUNTER))); // R.E.K.3000
        add(TCItems.GOBLIN_TECH, helper -> helper.of(TCItems.INFORMATION, List.of(TCItems.METAL$DETECTOR, TCItems.$STOPWATCH, TCItems.DPS$METER))); // 哥布林数据仪
        add(TCItems.FISH_FINDER, helper -> helper.of(TCItems.INFORMATION, List.of(TCItems.FISHERMANS$POCKET$GUIDE, TCItems.WEATHER$RADIO, TCItems.$SEXTANT))); // 探鱼器
        add(TCItems.PDA, helper -> helper.of(TCItems.INFORMATION, TCItems.FULL_INFO)); // 个人数字助手
        add(TCItems.CELL_PHONE, helper -> helper.of(TCItems.INFORMATION, TCItems.FULL_INFO)); // 手机

        // 移动
        add(TCItems.FLYING_CARPET, helper -> helper.of(TCItems.MAY$FLY, MayFlyAbilityValue.of(helper.asKey(), 1200, 0.5625F, 100, false, true))); // 飞毯
        add(TCItems.AGLET, helper -> helper.entry(TCItems.ATTRIBUTES, AttributeModifiersValue.simple(Attributes.MOVEMENT_SPEED, helper.asId(), 0.05, PortAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL))); // 金属带扣
        add(TCItems.ANKLET_OF_THE_WIND, helper -> helper.entry(TCItems.ATTRIBUTES, AttributeModifiersValue.simple(Attributes.MOVEMENT_SPEED, helper.asId(), 0.1, PortAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL))); // 疾风脚镯
        add(TCItems.MAGILUMINESCENCE, helper -> {
            helper.unit(TCItems.$MAGILUMINESCENCE);
            helper.of(TCItems.LUMINANCE, 14);
            helper.entry(TCItems.ATTRIBUTES, AttributeModifiersValue.simple(Attributes.MOVEMENT_SPEED, helper.asId(), 0.15, PortAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
        }); // 魔光护符
        add(TCItems.LAVA_CHARM, helper -> helper.of(TCItems.LAVA$IMMUNE$TICKS, 140)); // 熔岩护身符
        add(TCItems.MAGMA_SKULL, helper -> {
            helper.unit(TCItems.FIRE$IMMUNE);
            helper.of(TCItems.LAVA$IMMUNE$TICKS, 140);
        }); // 岩浆骷髅头
        add(TCItems.MOLTEN_CHARM, helper -> {
            helper.unit(TCItems.FIRE$IMMUNE);
            helper.of(TCItems.LAVA$IMMUNE$TICKS, 140);
        }); // 熔火护身符
        add(TCItems.CLIMBING_CLAWS, helper -> helper.of(TCItems.WALL$CLIMB, (byte) 1)); // 攀爬爪
        add(TCItems.SHOE_SPIKES, helper -> helper.of(TCItems.WALL$CLIMB, (byte) 1)); // 鞋钉
        add(TCItems.TIGER_CLIMBING_GEAR, helper -> helper.of(TCItems.WALL$CLIMB, (byte) 2)); // 猛虎攀爬装备
        add(TCItems.TABI, helper -> helper.unit(TCItems.SPRINTING)); // 分趾厚底袜
        add(TCItems.MASTER_NINJA_GEAR, helper -> {
            helper.unit(TCItems.SPRINTING);
            helper.of(TCItems.WALL$CLIMB, (byte) 2);
            helper.entry(TCItems.ATTRIBUTES, AttributeModifiersValue.simple(LibAttributes.getDodgeChance().value(), helper.asId(), 0.1, PortAttributeModifier.Operation.ADD_VALUE));
        }); // 忍者大师装备
        add(TCItems.DUNERIDER_BOOTS, helper -> helper.entry(TCItems.ATTRIBUTES, AttributeModifiersValue.simple(Attributes.STEP_HEIGHT.value(), helper.asId(), 0.5, PortAttributeModifier.Operation.ADD_VALUE))); // 沙丘行者靴
        add(TCItems.ROCKET_BOOTS, helper -> helper.of(TCItems.MAY$FLY, MayFlyAbilityValue.of(helper.asKey(), 0.3F, 36, false, false))); // 火箭靴
        add(TCItems.SPECTRE_BOOTS, helper -> helper.of(TCItems.MAY$FLY, MayFlyAbilityValue.of(helper.asKey(), 0.3F, 36, false, false))); // 幽灵靴
        add(TCItems.FAIRY_BOOTS, helper -> {
            helper.unit(TCItems.FLOWER$BOOTS);
            helper.of(TCItems.MAY$FLY, MayFlyAbilityValue.of(helper.asKey(), 0.3F, 36, false, false));
        }); // 仙灵靴
        add(TCItems.LIGHTNING_BOOTS, helper -> {
            helper.of(TCItems.MAY$FLY, MayFlyAbilityValue.of(helper.asKey(), 0.3F, 36, false, false));
            helper.entry(TCItems.ATTRIBUTES, AttributeModifiersValue.simple(Attributes.MOVEMENT_SPEED, helper.asId(), 0.08, PortAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
        }); // 闪电靴
        add(TCItems.FROSTSPARK_BOOTS, helper -> {
            helper.unit(TCItems.ICE$SPEED);
            helper.unit(TCItems.ICE$SAFE);
            helper.of(TCItems.MAY$FLY, MayFlyAbilityValue.of(helper.asKey(), 0.3F, 40, false, false));
            helper.entry(TCItems.ATTRIBUTES, AttributeModifiersValue.simple(Attributes.MOVEMENT_SPEED, helper.asId(), 0.08, PortAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
        }); // 霜花靴
        add(TCItems.WATER_WALKING_BOOTS, helper -> helper.of(TCItems.FLUID$WALK, Set.of(TCTags.WATER_LIKE_WALK))); // 水上漂靴
        add(TCItems.OBSIDIAN_WATER_WALKING_BOOTS, helper -> {
            helper.unit(TCItems.FIRE$IMMUNE);
            helper.of(TCItems.FLUID$WALK, Set.of(TCTags.WATER_LIKE_WALK, TCTags.LAVA_LIKE_WALK));
        }); // 黑曜石水上漂靴
        add(TCItems.LAVA_WADERS, helper -> {
            helper.unit(TCItems.FIRE$IMMUNE);
            helper.of(TCItems.FLUID$WALK, Set.of(TCTags.WATER_LIKE_WALK, TCTags.LAVA_LIKE_WALK));
            helper.of(TCItems.LAVA$IMMUNE$TICKS, 140);
            helper.of(TCItems.LAVA$HURT$REDUCE, 0.5F);
        }); // 熔岩靴
        add(TCItems.TERRASPARK_BOOTS, helper -> {
            helper.unit(TCItems.ICE$SPEED);
            helper.unit(TCItems.ICE$SAFE);
            helper.unit(TCItems.FIRE$IMMUNE);
            helper.of(TCItems.MAY$FLY, MayFlyAbilityValue.of(helper.asKey(), 0.3F, 40, false, false));
            helper.of(TCItems.FLUID$WALK, Set.of(TCTags.WATER_LIKE_WALK, TCTags.LAVA_LIKE_WALK));
            helper.of(TCItems.LAVA$IMMUNE$TICKS, 140);
            helper.of(TCItems.LAVA$HURT$REDUCE, 0.5F);
            helper.entry(TCItems.ATTRIBUTES, AttributeModifiersValue.simple(Attributes.MOVEMENT_SPEED, helper.asId(), 0.08, PortAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
        }); // 泰拉闪耀靴

        // 瓶
        add(TCItems.CLOUD_IN_A_BOTTLE, helper -> {
            helper.of(TCItems.CLOUD, 1.3F);
            helper.entry(TCItems.ATTRIBUTES, AttributeModifiersValue.simple(Attributes.SAFE_FALL_DISTANCE.value(), helper.asId(), 3, PortAttributeModifier.Operation.ADD_VALUE));
        }); // 云朵瓶
        add(TCItems.BLIZZARD_IN_A_BOTTLE, helper -> {
            helper.of(TCItems.BLIZZARD, new Tuple<>(0.4F, 14));
            helper.entry(TCItems.ATTRIBUTES, AttributeModifiersValue.simple(Attributes.SAFE_FALL_DISTANCE.value(), helper.asId(), 3, PortAttributeModifier.Operation.ADD_VALUE));
        }); // 暴雪瓶
        add(TCItems.SANDSTORM_IN_A_BOTTLE, helper -> {
            helper.of(TCItems.SAND$STORM, new Tuple<>(0.45F, 17));
            helper.entry(TCItems.ATTRIBUTES, AttributeModifiersValue.simple(Attributes.SAFE_FALL_DISTANCE.value(), helper.asId(), 3, PortAttributeModifier.Operation.ADD_VALUE));
        }); // 沙暴瓶
        add(TCItems.FART_IN_A_JAR, helper -> {
            helper.of(TCItems.FART, 1.7F);
            helper.entry(TCItems.ATTRIBUTES, AttributeModifiersValue.simple(Attributes.SAFE_FALL_DISTANCE.value(), helper.asId(), 3, PortAttributeModifier.Operation.ADD_VALUE));
        }); // 罐中臭屁
        add(TCItems.TSUNAMI_IN_A_BOTTLE, helper -> {
            helper.of(TCItems.TSUNAMI, 1.5F);
            helper.entry(TCItems.ATTRIBUTES, AttributeModifiersValue.simple(Attributes.SAFE_FALL_DISTANCE.value(), helper.asId(), 3, PortAttributeModifier.Operation.ADD_VALUE));
        }); // 海啸瓶

        // 气球
        add(TCItems.SHINY_RED_BALLOON, helper -> {
            ResourceLocation id = helper.asId();
            helper.entry(TCItems.ATTRIBUTES, balloonAttributes(id, 2));
        }); // 闪亮红气球
        add(TCItems.BALLOON_PUFFERFISH, helper -> helper.entry(TCItems.ATTRIBUTES, balloonAttributes(helper.asId(), 2))); // 气球河豚鱼
        add(TCItems.CLOUD_IN_A_BALLOON, helper -> {
            helper.of(TCItems.CLOUD, 1.3F);
            helper.entry(TCItems.ATTRIBUTES, balloonAttributes(helper.asId(), 4));
        }); // 云朵气球
        add(TCItems.BLIZZARD_IN_A_BALLOON, helper -> {
            helper.of(TCItems.BLIZZARD, new Tuple<>(0.4F, 14));
            helper.entry(TCItems.ATTRIBUTES, balloonAttributes(helper.asId(), 4));
        }); // 暴雪气球
        add(TCItems.SANDSTORM_IN_A_BALLOON, helper -> {
            helper.of(TCItems.SAND$STORM, new Tuple<>(0.45F, 17));
            helper.entry(TCItems.ATTRIBUTES, balloonAttributes(helper.asId(), 4));
        }); // 沙暴气球
        add(TCItems.FART_IN_A_BALLOON, helper -> {
            helper.of(TCItems.FART, 1.1F);
            helper.entry(TCItems.ATTRIBUTES, balloonAttributes(helper.asId(), 4));
        }); // 臭屁气球
        add(TCItems.SHARKRON_BALLOON, helper -> {
            helper.of(TCItems.TSUNAMI, 1.3F);
            helper.entry(TCItems.ATTRIBUTES, balloonAttributes(helper.asId(), 4));
        }); // 鲨鱼龙气球
        add(TCItems.HONEY_BALLOON, helper -> {
            helper.unit(TCItems.HONEY$COMB);
            helper.entry(TCItems.ATTRIBUTES, balloonAttributes(helper.asId(), 2));
        }); // 蜂蜜气球
        add(TCItems.BUNDLE_OF_BALLOONS, helper -> {
            helper.of(TCItems.SAND$STORM, new Tuple<>(0.45F, 17));
            helper.of(TCItems.BLIZZARD, new Tuple<>(0.4F, 14));
            helper.of(TCItems.CLOUD, 1.3F);
            helper.entry(TCItems.ATTRIBUTES, balloonAttributes(helper.asId(), 7));
        }); // 气球束

        // 马掌
        add(TCItems.LUCKY_HORSESHOE, helper -> helper.entry(TCItems.ATTRIBUTES, horseshoeAttributes(helper.asId()))); // 幸运马掌
        add(TCItems.OBSIDIAN_HORSESHOE, helper -> {
            helper.unit(TCItems.FIRE$IMMUNE);
            helper.entry(TCItems.ATTRIBUTES, horseshoeAttributes(helper.asId()));
        }); // 黑曜石马掌
        add(TCItems.BLUE_HORSESHOE_BALLOON, helper -> {
            helper.of(TCItems.CLOUD, 1.3F);
            helper.entry(TCItems.ATTRIBUTES, horseshoeBalloonAttributes(helper.asId(), 0.75));
        }); // 蓝马掌气球
        add(TCItems.WHITE_HORSESHOE_BALLOON, helper -> {
            helper.of(TCItems.BLIZZARD, new Tuple<>(0.4F, 14));
            helper.entry(TCItems.ATTRIBUTES, horseshoeBalloonAttributes(helper.asId(), 0.43));
        }); // 白马掌气球
        add(TCItems.YELLOW_HORSESHOE_BALLOON, helper -> {
            helper.of(TCItems.SAND$STORM, new Tuple<>(0.45F, 17));
            helper.entry(TCItems.ATTRIBUTES, horseshoeBalloonAttributes(helper.asId(), 0.75));
        }); // 黄马掌气球
        add(TCItems.GREEN_HORSESHOE_BALLOON, helper -> {
            helper.of(TCItems.FART, 1.1F);
            helper.entry(TCItems.ATTRIBUTES, horseshoeBalloonAttributes(helper.asId(), 0.75));
        }); // 绿马掌气球
        add(TCItems.PINK_HORSESHOE_BALLOON, helper -> {
            helper.of(TCItems.TSUNAMI, 1.3F);
            helper.entry(TCItems.ATTRIBUTES, horseshoeBalloonAttributes(helper.asId(), 0.43));
        }); // 粉马掌气球
        add(TCItems.AMBER_HORSESHOE_BALLOON, helper -> {
            helper.unit(TCItems.HONEY$COMB);
            helper.entry(TCItems.ATTRIBUTES, horseshoeBalloonAttributes(helper.asId(), 0.43));
        }); // 琥珀马掌气球
        add(TCItems.BUNDLE_OF_HORSESHOE_BALLOONS, helper -> {
            helper.of(TCItems.SAND$STORM, new Tuple<>(0.45F, 17));
            helper.of(TCItems.BLIZZARD, new Tuple<>(0.4F, 14));
            helper.of(TCItems.CLOUD, 1.3F);
            helper.entry(TCItems.ATTRIBUTES, horseshoeBalloonAttributes(helper.asId(), 0.43));
        }); // 马掌气球束

        // 游泳
        add(TCItems.INNER_TUBE, helper -> helper.unit(TCItems.FLOAT$ON$LIQUID$SURFACE)); // 游泳圈
        add(TCItems.FLIPPER, helper -> helper.entry(TCItems.ATTRIBUTES, AttributeModifiersValue.simple(Attributes.SWIM_SPEED.value(), helper.asId(), 0.5, PortAttributeModifier.Operation.ADD_VALUE))); // 脚蹼
        add(TCItems.DIVING_GEAR, helper -> {
            helper.unit(TCItems.DIVING);
            helper.entry(TCItems.ATTRIBUTES, AttributeModifiersValue.simple(Attributes.SWIM_SPEED.value(), helper.asId(), 0.5, PortAttributeModifier.Operation.ADD_VALUE));
        }); // 潜水装备
        add(TCItems.JELLYFISH_NECKLACE, helper -> helper.of(TCItems.LUMINANCE, -12)); // 水母项链
        add(TCItems.JELLYFISH_DIVING_GEAR, helper -> {
            helper.unit(TCItems.DIVING);
            helper.of(TCItems.LUMINANCE, -12);
            helper.of(TCItems.EFFECT$IMMUNITIES, Set.of());
            helper.entry(TCItems.ATTRIBUTES, AttributeModifiersValue.simple(Attributes.SWIM_SPEED.value(), helper.asId(), 0.5, PortAttributeModifier.Operation.ADD_VALUE));
        }); // 水母潜水装备
        add(TCItems.ARCTIC_DIVING_GEAR, helper -> {
            helper.unit(TCItems.DIVING);
            helper.unit(TCItems.ICE$SPEED);
            helper.unit(TCItems.FROZEN$IMMUNE);
            helper.of(TCItems.LUMINANCE, -12);
            helper.entry(TCItems.ATTRIBUTES, AttributeModifiersValue.simple(Attributes.SWIM_SPEED.value(), helper.asId(), 0.5, PortAttributeModifier.Operation.ADD_VALUE));
        }); // 北极潜水装备

        // 青蛙
        add(TCItems.FROG_LEG, helper -> helper.entry(TCItems.ATTRIBUTES, frogAttributes(helper.asId()))); // 蛙腿
        add(TCItems.FROG_FLIPPER, helper -> {
            ResourceLocation id = helper.asId();
            helper.entry(TCItems.ATTRIBUTES, frogAttributesBuilder(id)
                    .add(Attributes.SWIM_SPEED.value(), id, 0.5, PortAttributeModifier.Operation.ADD_VALUE)
                    .build());
        }); // 青蛙脚蹼
        add(TCItems.FROG_WEBBING, helper -> {
            helper.of(TCItems.WALL$CLIMB, (byte) 2);
            helper.entry(TCItems.ATTRIBUTES, frogAttributes(helper.asId()));
        }); // 青蛙蹼
        add(TCItems.FROG_GEAR, helper -> {
            helper.of(TCItems.WALL$CLIMB, (byte) 2);
            ResourceLocation id = helper.asId();
            helper.entry(TCItems.ATTRIBUTES, frogAttributesBuilder(id)
                    .add(Attributes.SWIM_SPEED.value(), id, 0.5, PortAttributeModifier.Operation.ADD_VALUE)
                    .build());
        }); // 青蛙装备
        add(TCItems.AMBHIPIAN_BOOTS, helper -> helper.entry(TCItems.ATTRIBUTES, frogAttributes(helper.asId()))); // 水陆两用靴

        // 其他
        add(TCItems.TREASURE_MAGNET, helper -> helper.entry(TCItems.ATTRIBUTES, AttributeModifiersValue.simple(ConfluenceMagicLib.PICKUP_RANGE, helper.asId(), 6.25, PortAttributeModifier.Operation.ADD_VALUE))); // 宝藏磁石
        add(TCItems.FLOWER_BOOTS, helper -> helper.unit(TCItems.FLOWER$BOOTS)); // 花靴

        // 专家
        add(TCItems.ROYAL_GEL, helper -> helper.of(TCItems.MOB$IGNORE, LibTags.EntityTypes.SLIME)); // 皇家凝胶
        add(TCItems.SHIELD_OF_CTHULHU, helper -> {
            helper.unit(TCItems.SHIELD$OF$CTHULHU);
            helper.entry(TCItems.ATTRIBUTES, AttributeModifiersValue.simple(Attributes.ARMOR, helper.asId(), 2, PortAttributeModifier.Operation.ADD_VALUE));
        }); // 克苏鲁护盾
        add(TCItems.WORM_SCARF, helper -> helper.of(TCItems.INJURY$FREE, 0.17F)); // 蠕虫围巾
        add(TCItems.BRAIN_OF_CONFUSION, helper -> helper.unit(TCItems.BRAIN$OF$CONFUSION)); // 混乱之脑
        add(TCItems.HIVE_PACK, helper -> helper.unit(TCItems.HIVE$PACK)); // 蜂巢背包
        add(TCItems.BONE_GLOVE, helper -> helper.unit(TCItems.BONE$GLOVE)); // 骨头手套
        add(TCItems.SOARING_INSIGNIA, helper -> {
            helper.unit(TCItems.INFINITE$FLIGHT);
            ResourceLocation id = helper.asId();
            helper.entry(TCItems.ATTRIBUTES, AttributeModifiersValue.builder()
                    .add(Attributes.MOVEMENT_SPEED, id, 0.075, PortAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
                    .add(Attributes.JUMP_STRENGTH_1211, id, 0.8, PortAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
                    .build());
        }); // 翱翔徽章
        add(TCItems.GRAVITY_GLOBE, helper -> helper.unit(TCItems.GRAVITY$GLOBE)); // 重力球
        add(TCItems.CELESTIAL_STARBOARD, helper -> {
            helper.of(TCItems.MAY$FLY, MayFlyAbilityValue.of(helper.asKey(), 1100, 1.0F, 60, true, true));
            helper.entry(TCItems.ATTRIBUTES, AttributeModifiersValue.simple(Attributes.FALL_DAMAGE_MULTIPLIER.value(), helper.asId(), -100.0, PortAttributeModifier.Operation.ADD_VALUE));
        }); // 天界星盘

        // 翅膀
        add(TCItems.FLEDGLING_WINGS, wings(0.3F, 28, false)); // 飞行高度：12
        add(TCItems.ANGEL_WINGS, wings(0.6F, 50, false)); // 飞行高度：34
        add(TCItems.DEMON_WINGS, wings(0.6F, 50, false)); // 飞行高度：34
        add(TCItems.FAIRY_WINGS, wings(0.65F, 56, false)); // 飞行高度：44
        add(TCItems.FIN_WINGS, wings(0.65F, 56, false)); // 飞行高度：44
        add(TCItems.FROZEN_WINGS, wings(0.65F, 56, false)); // 飞行高度：44
        add(TCItems.HARPY_WINGS, wings(0.65F, 56, false)); // 飞行高度：44
        add(TCItems.JETPACK, wings(0.65F, 63, false)); // 飞行高度：51
        add(TCItems.LEAF_WINGS, wings(0.6F, 50, false)); // 飞行高度：34
        add(TCItems.BAT_WINGS, wings(0.68F, 62, false)); // 飞行高度：54
        add(TCItems.BEE_WINGS, wings(0.68F, 62, false)); // 飞行高度：54
        add(TCItems.BUTTERFLY_WINGS, wings(0.68F, 62, false)); // 飞行高度：54
        add(TCItems.FLAME_WINGS, wings(0.68F, 62, false)); // 飞行高度：54
        add(TCItems.HOVERBOARD, wings(0.68F, 74, true)); // 飞行高度：62
        add(TCItems.BONE_WINGS, wings(0.68F, 74, false)); // 飞行高度：62
        add(TCItems.MOTHRON_WINGS, wings(0.68F, 74, false)); // 飞行高度：62
        add(TCItems.SPECTRE_WINGS, wings(0.68F, 74, false)); // 飞行高度：62
        add(TCItems.BEETLE_WINGS, wings(0.68F, 74, false)); // 飞行高度：62
        add(TCItems.FESTIVE_WINGS, wings(0.7F, 84, false)); // 飞行高度：71
        add(TCItems.SPOOKY_WINGS, wings(0.7F, 84, false)); // 飞行高度：71
        add(TCItems.TATTERED_WINGS, wings(0.7F, 84, false)); // 飞行高度：71
        add(TCItems.STEAMPUNK_WINGS, wings(0.7F, 84, false)); // 飞行高度：71
        add(TCItems.BETSYS_WINGS, wings(0.72F, 84, true)); // 飞行高度：79
        add(TCItems.EMPRESS_WINGS, wings(0.85F, 86, false)); // 飞行高度：85
        add(TCItems.FISHRON_WINGS, wings(0.85F, 92, false)); // 飞行高度：95
        add(TCItems.NEBULA_WINGS, wings(0.85F, 92, true)); // 飞行高度：95
        add(TCItems.VORTEX_BOOSTER, wings(0.85F, 92, true)); // 飞行高度：95
        add(TCItems.SOLAR_WINGS, wings(0.85F, 92, false)); // 飞行高度：95
        add(TCItems.STARDUST_WINGS, wings(0.85F, 92, false)); // 飞行高度：95
    }

    private static Consumer<Helper> wings(float flySpeed, int flyTicks, boolean horizontalFlight) {
        return helper -> {
            helper.of(TCItems.MAY$FLY, MayFlyAbilityValue.of(helper.asKey(), 1100, flySpeed, flyTicks, true, horizontalFlight));
            helper.entry(TCItems.ATTRIBUTES, AttributeModifiersValue.simple(Attributes.FALL_DAMAGE_MULTIPLIER.value(), helper.asId(), -100, PortAttributeModifier.Operation.ADD_VALUE));
        };
    }

    private static AttributeModifiersValue celestial(ResourceLocation id, double armor) {
        return AttributeModifiersValue.builder()
                .add(Attributes.ATTACK_SPEED, id, 0.1, PortAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
                .add(LibAttributes.getAttackDamage().value(), id, 0.1, PortAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
                .add(Attributes.ARMOR, id, armor, PortAttributeModifier.Operation.ADD_VALUE)
                .add(Attributes.BLOCK_BREAK_SPEED.value(), id, 0.15, PortAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
                .add(LibAttributes.getCriticalChance().value(), id, 0.02, PortAttributeModifier.Operation.ADD_VALUE)
                .add(LibAttributes.getRangedDamage().value(), id, 0.1, PortAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
                .add(LibAttributes.getMagicDamage().value(), id, 0.1, PortAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
                .build();
    }

    private static AttributeModifiersValue fourClasses(ResourceLocation id, double amount) {
        return fourClassesBuilder(id, amount).build();
    }

    private static AttributeModifiersValue.Builder fourClassesBuilder(ResourceLocation id, double amount) {
        return AttributeModifiersValue.builder()
                .add(LibAttributes.getAttackDamage().value(), id, amount, PortAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
                .add(LibAttributes.getRangedDamage().value(), id, amount, PortAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
                .add(LibAttributes.getMagicDamage().value(), id, amount, PortAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
    }

    private static AttributeModifiersValue balloonAttributes(ResourceLocation id, double safeFallDistance) {
        return AttributeModifiersValue.builder()
                .add(Attributes.JUMP_STRENGTH_1211, id, 0.43, PortAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
                .add(Attributes.SAFE_FALL_DISTANCE.value(), id, safeFallDistance, PortAttributeModifier.Operation.ADD_VALUE)
                .build();
    }

    private static AttributeModifiersValue horseshoeAttributes(ResourceLocation id) {
        return AttributeModifiersValue.builder()
                .add(Attributes.LUCK, id, 0.05, PortAttributeModifier.Operation.ADD_VALUE)
                .add(Attributes.FALL_DAMAGE_MULTIPLIER.value(), id, -100.0, PortAttributeModifier.Operation.ADD_VALUE)
                .build();
    }

    private static AttributeModifiersValue horseshoeBalloonAttributes(ResourceLocation id, double jumpStrength) {
        return AttributeModifiersValue.builder()
                .add(Attributes.JUMP_STRENGTH_1211, id, jumpStrength, PortAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
                .add(Attributes.LUCK, id, 0.05, PortAttributeModifier.Operation.ADD_VALUE)
                .add(Attributes.FALL_DAMAGE_MULTIPLIER.value(), id, -100.0, PortAttributeModifier.Operation.ADD_VALUE)
                .build();
    }

    private static AttributeModifiersValue frogAttributes(ResourceLocation id) {
        return frogAttributesBuilder(id).build();
    }

    private static AttributeModifiersValue.Builder frogAttributesBuilder(ResourceLocation id) {
        return AttributeModifiersValue.builder()
                .add(Attributes.SAFE_FALL_DISTANCE.value(), id, 7.0, PortAttributeModifier.Operation.ADD_VALUE)
                .add(Attributes.JUMP_STRENGTH_1211, id, 0.6, PortAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
    }

    protected void add(ItemLike item, Consumer<Helper> consumer) {
        builder.add(item.asItem().builtInRegistryHolder().key(), new PrimitiveValueComponent(wrap(item.asItem(), consumer)), false);
    }

    @SuppressWarnings("deprecation")
    public static Map<ValueType<?, ? extends PrimitiveValue<?>>, PrimitiveValue<?>> wrap(Item item, Consumer<Helper> consumer) {
        Map<ValueType<?, ? extends PrimitiveValue<?>>, PrimitiveValue<?>> map = new Reference2ObjectOpenHashMap<>();
        consumer.accept(new Helper() {
            @Override
            public void unit(ValueType<Unit, ? extends UnitValue> type) {
                map.put(type, type.newInstance(Unit.INSTANCE));
            }

            @Override
            public <T1, V1 extends PrimitiveValue<T1>> void of(ValueType<T1, V1> type, T1 value) {
                map.put(type, type.newInstance(value));
            }

            @Override
            public <T1, V1 extends PrimitiveValue<T1>> void entry(ValueType<T1, V1> type, V1 value) {
                map.put(type, value);
            }

            @Override
            public ResourceLocation asId() {
                return Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item));
            }

            @Override
            public ResourceKey<Item> asKey() {
                return item.builtInRegistryHolder().key();
            }
        });
        return map;
    }

    public interface Helper {
        void unit(ValueType<Unit, ? extends UnitValue> type);

        <T, V extends PrimitiveValue<T>> void of(ValueType<T, V> type, T value);

        <T, V extends PrimitiveValue<T>> void entry(ValueType<T, V> type, V value);

        ResourceLocation asId();

        ResourceKey<Item> asKey();
    }
}
