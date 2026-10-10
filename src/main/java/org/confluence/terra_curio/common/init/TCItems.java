package org.confluence.terra_curio.common.init;

import com.google.common.collect.ImmutableListMultimap;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Tuple;
import net.minecraft.util.Unit;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.Fluid;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;
import org.confluence.lib.common.item.CustomRarityItem;
import org.confluence.terra_curio.TerraCurio;
import org.confluence.terra_curio.api.primitive.*;
import org.confluence.terra_curio.common.item.CellPhone;
import org.confluence.terra_curio.common.item.DemonHeart;
import org.confluence.terra_curio.common.item.DivingHelmet;
import org.confluence.terra_curio.common.item.MagicMirror;
import org.confluence.terra_curio.common.item.curio.BaseCurioItem;
import org.confluence.terra_curio.common.item.curio.NightBonusCurioItem;
import org.confluence.terra_curio.common.item.curio.ParticlePlacements;
import org.confluence.terra_curio.common.item.curio.ParticleTriggers;
import org.confluence.terra_curio.common.item.curio.combat.*;
import org.confluence.terra_curio.common.item.curio.expert.GravityGlobe;
import org.confluence.terra_curio.common.item.curio.expert.ShieldOfCthulhu;
import org.confluence.terra_curio.common.item.curio.expert.ShinnyStone;
import org.confluence.terra_curio.common.item.curio.health.BandOfRegeneration;
import org.confluence.terra_curio.common.item.curio.information.MetalDetector;
import org.confluence.terra_curio.common.item.curio.information.MultiInfoCurioItem;
import org.confluence.terra_curio.common.item.curio.master.BasePoint;
import org.confluence.terra_curio.common.item.curio.movement.BaseSpeedBoots;
import org.confluence.terra_curio.common.item.curio.movement.DuneriderBoots;
import org.confluence.terra_curio.common.item.curio.movement.StepStool;
import org.mesdag.portlib.registries.PortDeferredItem;
import org.mesdag.portlib.registries.PortItemRegistration;
import org.mesdag.portlib.registries.PortRegisterHandler;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static org.confluence.lib.common.component.ModRarity.*;
import static org.confluence.terra_curio.api.primitive.ValueType.create;
import static org.confluence.terra_curio.api.primitive.ValueType.ofUnit;

@SuppressWarnings("all")
public final class TCItems {
    public static void init() {}

    public static final PortItemRegistration OTHERS = PortRegisterHandler.item(TerraCurio.MODID);
    public static final PortItemRegistration CURIOS = PortRegisterHandler.item(TerraCurio.MODID);
    public static final PortItemRegistration WINGS = PortRegisterHandler.item(TerraCurio.MODID);

    // client side info_check
    public static final ValueType<List<TooltipComponentsValue.Storage>, TooltipComponentsValue> INFORMATION = create("information", TooltipComponentsValue.EXPANSION, TooltipComponentsValue.CODEC, List.of(), TooltipComponentsValue::new);
    public static final TooltipComponentsValue.Storage HOUR$WATCH = TooltipComponentsValue.create("hour_watch");
    public static final TooltipComponentsValue.Storage HALF$HOUR$WATCH = TooltipComponentsValue.create("half_hour_watch");
    public static final TooltipComponentsValue.Storage MINUTE$WATCH = TooltipComponentsValue.create("minute_watch");
    public static final TooltipComponentsValue.Storage WEATHER$RADIO = TooltipComponentsValue.create("weather_radio");
    public static final TooltipComponentsValue.Storage $SEXTANT = TooltipComponentsValue.create("sextant");
    public static final TooltipComponentsValue.Storage FISHERMANS$POCKET$GUIDE = TooltipComponentsValue.create("fishermans_pocket_guide");
    public static final TooltipComponentsValue.Storage METAL$DETECTOR = TooltipComponentsValue.create("metal_detector");
    public static final TooltipComponentsValue.Storage LIFE$FORM$ANALYZER = TooltipComponentsValue.create("life_form_analyzer");
    public static final TooltipComponentsValue.Storage $RADAR = TooltipComponentsValue.create("radar");
    public static final TooltipComponentsValue.Storage TALLY$COUNTER = TooltipComponentsValue.create("tally_counter");
    public static final TooltipComponentsValue.Storage DPS$METER = TooltipComponentsValue.create("dps_meter");
    public static final TooltipComponentsValue.Storage $STOPWATCH = TooltipComponentsValue.create("stopwatch");
    public static final TooltipComponentsValue.Storage $COMPASS = TooltipComponentsValue.create("compass");
    public static final TooltipComponentsValue.Storage DEPTH$METER = TooltipComponentsValue.create("depth_meter");
    public static final TooltipComponentsValue.Storage MECHANICAL$LENS = TooltipComponentsValue.create("mechanical_lens");

    public static final List<TooltipComponentsValue.Storage> FULL_INFO = List.of(
            MINUTE$WATCH,
            WEATHER$RADIO,
            $SEXTANT,
            FISHERMANS$POCKET$GUIDE,
            METAL$DETECTOR,
            LIFE$FORM$ANALYZER,
            $RADAR,
            TALLY$COUNTER,
            DPS$METER,
            $STOPWATCH,
            $COMPASS,
            DEPTH$METER
    );


    // client side
    public static final ValueType<Set<TagKey<Fluid>>, FluidTagsValue> FLUID$WALK = ValueType.create("fluid_walk", FluidTagsValue.EXPANSION, FluidTagsValue.CODEC, Set.of(), FluidTagsValue::new);
    public static final ValueType.UnitType AUTO$ATTACK = ofUnit("auto_attack");
    public static final ValueType.UnitType SPRINTING = ofUnit("sprinting");
    public static final ValueType.UnitType SCOPE = ofUnit("scope");
    public static final ValueType.UnitType GRAVITY$GLOBE = ofUnit("gravity_globe");
    public static final ValueType.UnitType $MAGILUMINESCENCE = ofUnit("magiluminescence");
    public static final ValueType.UnitType BONE$GLOVE = ofUnit("bone_glove");

    // both side
    public static final ValueType.UnitType FLOAT$ON$LIQUID$SURFACE = ofUnit("float_on_liquid_surface");
    public static final ValueType.UnitType ICE$SAFE = ofUnit("ice_safe");
    public static final ValueType.UnitType SHIELD$OF$CTHULHU = ofUnit("shield_of_cthulhu");

    // require updates
    public static final ValueType.UnitType FIRE$ATTACK = ofUnit("fire_attack");
    public static final ValueType.UnitType HONEY$COMB = ofUnit("honey_comb");
    public static final ValueType.UnitType FROZEN$TURTLE$SHELL = ofUnit("frozen_turtle_shell");
    public static final ValueType.UnitType MAGIC$QUIVER = ofUnit("magic_quiver");
    public static final ValueType.UnitType IGNITE$ARROW = ofUnit("ignite_arrow");
    public static final ValueType.UnitType FIRE$IMMUNE = ofUnit("fire_immune");
    public static final ValueType.UnitType FROZEN$IMMUNE = ofUnit("frozen_immune");
    public static final ValueType.UnitType FLOWER$BOOTS = ofUnit("flower_boots");
    public static final ValueType.UnitType ICE$SPEED = ofUnit("ice_speed");
    public static final ValueType.UnitType DIVING = ofUnit("diving");
    public static final ValueType.UnitType BRAIN$OF$CONFUSION = ofUnit("brain_of_confusion");
    public static final ValueType.UnitType HIVE$PACK = ofUnit("hive_pack");
    public static final ValueType.UnitType INFINITE$FLIGHT = ofUnit("infinite_flight");

    public static final ValueType<Set<MobEffect>, MobEffectsValue> EFFECT$IMMUNITIES = ValueType.create("effect_immunities", MobEffectsValue.MERGE, MobEffectsValue.CODEC, Set.of(), MobEffectsValue::new);
    public static final ValueType<Boolean, BooleanValue> STAR$CLOCK = ValueType.create("star_clock", BooleanValue.OR, BooleanValue.CODEC, false, BooleanValue::new);
    public static final ValueType.FloatType INVULNERABLE$TICKS$MULTIPLIER = ValueType.ofFloat("invulnerable_ticks_multiplier", FloatValue.GET_MAX_WITHIN_0_TO_100, 1.0F);
    public static final ValueType.FloatType LAVA$HURT$REDUCE = ValueType.ofFloat("lava_hurt_reduce", FloatValue.GET_MAX_WITHIN_0_TO_1, 0.0F);
    public static final ValueType.IntegerType LAVA$IMMUNE$TICKS = ValueType.ofInteger("lava_immune_ticks", IntegerValue.GET_MAX, 0);
    public static final ValueType<Byte, ByteValue> RIGHT$CLICK$DELAY$SUBSTRACTOR = ValueType.create("right_click_delay_substractor", ByteValue.GET_MAX, ByteValue.CODEC, (byte) 0, ByteValue::new);
    public static final ValueType<Map<ResourceKey<Item>, MayFlyAbilityValue.FlyStack>, MayFlyAbilityValue> MAY$FLY = ValueType.create("may_fly", MayFlyAbilityValue.COMBINE_RULE, MayFlyAbilityValue.CODEC, Map.of(), MayFlyAbilityValue::new);
    public static final ValueType.IntegerType LUMINANCE = ValueType.ofInteger("luminance", IntegerValue.GET_ABS_MAX, 0);
    public static final ValueType<Unit, UnitValue> NEPTUNES$SHELL = ofUnit("neptunes_shell");
    public static final ValueType<Byte, ByteValue> WALL$CLIMB = ValueType.create("wall_climb", ByteValue.ADDITION_WITHIN_0_TO_2, ByteValue.CODEC, (byte) 0, ByteValue::new);
    public static final ValueType.FloatType CLOUD = ValueType.ofFloat("cloud", FloatValue.GET_SELF, 0.0F);
    public static final ValueType<Tuple<Float, Integer>, OneTimeJumpAbilityValue> BLIZZARD = ValueType.create("blizzard", OneTimeJumpAbilityValue.COMBINE_RULE, OneTimeJumpAbilityValue.CODEC, new Tuple<>(0.0F, 0), OneTimeJumpAbilityValue::new);
    public static final ValueType<Tuple<Float, Integer>, OneTimeJumpAbilityValue> SAND$STORM = ValueType.create("sand_storm", OneTimeJumpAbilityValue.COMBINE_RULE, OneTimeJumpAbilityValue.CODEC, new Tuple<>(0.0F, 0), OneTimeJumpAbilityValue::new);
    public static final ValueType.FloatType FART = ValueType.ofFloat("fart", FloatValue.GET_SELF, 0.0F);
    public static final ValueType.FloatType TSUNAMI = ValueType.ofFloat("tsunami", FloatValue.GET_SELF, 0.0F);
    public static final ValueType<TagKey<EntityType<?>>, EntityTypeTagValue> MOB$IGNORE = ValueType.create("mob_ignore", EntityTypeTagValue.GET_SELF, EntityTypeTagValue.CODEC, TCTags.NOTHING, EntityTypeTagValue::new);
    public static final ValueType.FloatType INJURY$FREE = ValueType.ofFloat("injury_free", FloatValue.ADDITION_WITHIN_0_TO_1, 0.0F);
    public static final ValueType.IntegerType TOTEM$WITH$COOLDOWN = ValueType.ofInteger("totem_with_cooldown", IntegerValue.GET_MIN_GREAT_EQUAL_THAN_0, -1);

    // no updates
    public static final ValueType<ImmutableListMultimap<Attribute, AttributeModifier>, AttributeModifiersValue> ATTRIBUTES = ValueType.create("attributes", AttributeModifiersValue.MERGE, AttributeModifiersValue.CODEC, ImmutableListMultimap.of(), AttributeModifiersValue::new);
    public static final ValueType<List<Component>, ComponentsValue> COMPONENTS = ValueType.create("components", ComponentsValue.COMBINE_RULE, ComponentsValue.CODEC, List.of(), ComponentsValue::new);


    public static final PortDeferredItem<CustomRarityItem> STAR = OTHERS.register("star", () -> new CustomRarityItem(MASTER));
    public static final PortDeferredItem<CustomRarityItem> ICON = OTHERS.register("icon", () -> new CustomRarityItem(MASTER));
    public static final PortDeferredItem<BasePoint> BASE_POINT = OTHERS.register("base_point", BasePoint::new);
    public static final PortDeferredItem<BaseCurioItem> EVERLASTING = OTHERS.register("everlasting", () -> BaseCurioItem.builder("everlasting").rarity(ModRarity.MASTER).build());

    public static final PortDeferredItem<BlockItem> WORKSHOP = OTHERS.register("workshop", () -> new BlockItem(TCBlocks.WORKSHOP.get(), new Item.Properties()));
    public static final PortDeferredItem<DemonHeart> DEMON_HEART = OTHERS.register("demon_heart", DemonHeart::new);
    public static final PortDeferredItem<MagicMirror> MAGIC_MIRROR = OTHERS.register("magic_mirror", () -> new MagicMirror(BLUE));
    public static final PortDeferredItem<CellPhone> CELL_PHONE = OTHERS.register("cell_phone", CellPhone::new);
    public static final PortDeferredItem<DivingHelmet> DIVING_HELMET = OTHERS.register("diving_helmet", DivingHelmet::new);

    public static final PortDeferredItem<BaseCurioItem> BEZOAR = registerCurio("bezoar", builder -> builder.rarity(LIGHT_RED)), // 牛黄 中毒
            HOLY_WATER = registerCurio("holy_water", builder -> builder.rarity(LIGHT_RED)), // 圣水 凋零
            DETOXIFICATION_CAPSULE = registerCurio("detoxification_capsule", builder -> builder.rarity(PINK).infos(0)), // 解毒囊
            VITAMINS = registerCurio("vitamins", builder -> builder.rarity(LIGHT_RED)), // 维生素 虚弱
            ENERGY_BAR = registerCurio("energy_bar", builder -> builder.rarity(LIGHT_RED)), // 能量棒 饥饿
            NUTRIENT_SOLUTION = registerCurio("nutrient_solution", builder -> builder.rarity(PINK).infos(0)), // 营养液
            BLINDFOLD = registerCurio("blindfold", builder -> builder.rarity(LIGHT_RED)), // 蒙眼布 失明
            FLASHLIGHT = registerCurio("flashlight", builder -> builder.rarity(ORANGE)), // 手电筒 黑暗
            SEARCHLIGHT = registerCurio("searchlight", builder -> builder.rarity(PINK).infos(0)), // 探照灯
            FAST_CLOCK = registerCurio("fast_clock", builder -> builder.rarity(LIGHT_RED)), // 快走时钟 缓慢
            TRIFOLD_MAP = registerCurio("trifold_map", builder -> builder.rarity(LIGHT_RED)), // 三折地图 反胃
            THE_PLAN = registerCurio("the_plan", builder -> builder.rarity(PINK).infos(0)), // 计划书
            HAND_DRILL = registerCurio("hand_drill", builder -> builder.rarity(LIGHT_RED)), // 手钻 挖掘疲劳
            SHOT_PUT = registerCurio("shot_put", builder -> builder.rarity(GREEN)), // 铅球 漂浮
            EXPLORERS_EQUIPMENT = registerCurio("explorers_equipment", builder -> builder.infos(0).rarity(PINK)), // 探险家宝具
            ANKH_CHARM = registerCurio("ankh_charm", builder -> builder.infos(0).rarity(LIGHT_PURPLE)), // 十字章护身符
            ANKH_SHIELD = registerCurio("ankh_shield", builder -> builder.infos(0).tooltips(1).rarity(LIME)), // 十字章护盾
            STAR_CLOAK = registerCurio("star_cloak", builder -> builder.infos(1).rarity(LIGHT_RED)), // 星星斗篷
            STAR_VEIL = registerCurio("star_veil", builder -> builder.rarity(LIGHT_PURPLE).infos(0).tooltips(1)), // 星星面纱
            BEE_CLOAK = registerCurio("bee_cloak", builder -> builder.infos(0).rarity(LIGHT_RED).tooltips(1)), // 蜜蜂斗篷
            BLACK_BELT = registerCurio("black_belt", builder -> builder.rarity(LIME)), // 黑腰带
            SUN_STONE = registerCurio("sun_stone", SunStone::new), // 太阳石
            MOON_STONE = registerDirectly("moon_stone", (name, builder) -> new NightBonusCurioItem(0.2F, builder.rarity(PINK))), // 月亮石
            CELESTIAL_STONE = registerDirectly("celestial_stone", (name, builder) -> new CelestialStone(builder.rarity(LIME).infos(0))), // 天界石
            MOON_CHARM = registerDirectly("moon_charm", (name, builder) -> new MoonCharm(builder.rarity(LIGHT_RED))), // 月光护身符
            NEPTUNES_SHELL = registerDirectly("neptunes_shell", (name, builder) -> new NeptunesShell(builder.rarity(PINK))), // 海神贝壳
            MOON_SHELL = registerDirectly("moon_shell", (name, builder) -> new MoonShell(builder.rarity(LIGHT_PURPLE).infos(0))), // 月亮贝壳
            CELESTIAL_SHELL = registerDirectly("celestial_shell", (name, builder) -> new CelestialShell(builder.rarity(YELLOW).infos(0))), // 天界贝壳
            COBALT_SHIELD = registerCurio("cobalt_shield", builder -> builder.rarity(GREEN)), // 钴护盾
            CROSS_NECKLACE = registerCurio("cross_necklace", builder -> builder.rarity(LIGHT_RED)), // 十字项链
            RANGER_EMBLEM = registerCurio("ranger_emblem", builder -> builder.rarity(LIGHT_RED).noTooltip()), // 游侠徽章
            WARRIOR_EMBLEM = registerCurio("warrior_emblem", builder -> builder.rarity(LIGHT_RED).noTooltip()), // 战士徽章
            SORCERER_EMBLEM = registerCurio("sorcerer_emblem", builder -> builder.rarity(LIGHT_RED).noTooltip()), // 巫士徽章
            AVENGER_EMBLEM = registerCurio("avenger_emblem", builder -> builder.infos(0).rarity(PINK).noTooltip()), // 复仇者勋章
            EYE_OF_THE_GOLEM = registerCurio("eye_of_the_golem", builder -> builder.rarity(LIME).noTooltip()), // 石巨人之眼
            DESTROYER_EMBLEM = registerCurio("destroyer_emblem", builder -> builder.infos(0).rarity(LIME).noTooltip()), // 毁灭者勋章
            FERAL_CLAWS = registerCurio("feral_claws", builder -> builder.rarity(ORANGE)), // 狂爪手套
            TITAN_GLOVE = registerCurio("titan_glove", builder -> builder.rarity(LIGHT_RED).noTooltip()), // 泰坦手套
            POWER_GLOVE = registerCurio("power_glove", builder -> builder.infos(0).rarity(PINK)), // 强力手套
            MECHANICAL_GLOVE = registerCurio("mechanical_glove", builder -> builder.infos(0).rarity(LIGHT_PURPLE)), // 机械手套
            FIRE_GAUNTLET = registerCurio("fire_gauntlet", builder -> builder.infos(0).rarity(LIME).tooltips(1)), // 烈火手套
            FLESH_KNUCKLES = registerCurio("flesh_knuckles", builder -> builder.rarity(PINK)), // 血肉指虎
            BERSERKERS_GLOVE = registerCurio("berserkers_glove", builder -> builder.infos(0).noTooltip().rarity(PINK)), // 狂战士手套
            PALADINS_SHIELD = registerDirectly("paladins_shield", name -> new PaladinsShield(BaseCurioItem.builder(name).rarity(YELLOW))), // 圣骑士护盾
            HERO_SHIELD = registerDirectly("hero_shield", name -> new PaladinsShield(BaseCurioItem.builder(name).infos(0).rarity(PINK))), // 英雄护盾
            FROZEN_TURTLE_SHELL = registerCurio("frozen_turtle_shell", builder -> builder.rarity(PINK).noParticlePosition().particle(TerraCurio.asResource("frozen_turtle_shell"), ParticleTriggers.LOW_HEALTH)), // 冰冻海龟壳
            FROZEN_SHIELD = registerDirectly("frozen_shield", name -> new PaladinsShield(BaseCurioItem.builder(name).infos(0).rarity(PINK).tooltips(1))), // 冰冻护盾
            HONEY_COMB = registerCurio("honey_comb", builder -> builder.rarity(GREEN)), // 蜂窝
            SHARK_TOOTH_NECKLACE = registerCurio("shark_tooth_necklace", builder -> builder.noTooltip()), // 鲨牙项链
            STINGER_NECKLACE = registerCurio("stinger_necklace", builder -> builder.rarity(PINK).infos(0)), // 毒刺项链
            PANIC_NECKLACE = registerDirectly("panic_necklace", name -> new PanicNecklace(BaseCurioItem.builder(name))), // 恐慌项链
            SWEETHEART_NECKLACE = registerDirectly("sweetheart_necklace", name -> new PaladinsShield(BaseCurioItem.builder(name).infos(0).rarity(ORANGE))), // 甜心项链
            MAGIC_QUIVER = registerCurio("magic_quiver", builder -> builder.rarity(LIGHT_RED)), // 魔法箭袋
            MOLTEN_QUIVER = registerCurio("molten_quiver", builder -> builder.rarity(PINK).infos(0).tooltips(2)), // 熔火箭袋
            STALKERS_QUIVER = registerCurio("stalkers_quiver", builder -> builder.rarity(PINK).infos(0).tooltips(1)), // 潜行者箭袋
            RIFLE_SCOPE = registerCurio("rifle_scope", builder -> builder.rarity(LIGHT_RED).tooltips(1)), // 步枪瞄准镜
            SNIPER_SCOPE = registerCurio("sniper_scope", builder -> builder.rarity(LIME).infos(0).tooltips(1)), // 狙击镜
            RECON_SCOPE = registerCurio("recon_scope", builder -> builder.rarity(PINK).infos(0).tooltips(3)), // 侦察镜
            MAGMA_STONE = registerCurio("magma_stone", builder -> builder.rarity(ORANGE).infos(1)), // 岩浆石
            OBSIDIAN_ROSE = registerCurio("obsidian_rose", builder -> builder.rarity(ORANGE)), // 黑曜石玫瑰
            OBSIDIAN_SHIELD = registerCurio("obsidian_shield", builder -> builder.infos(0).rarity(LIGHT_RED)), // 黑曜石护盾
            OBSIDIAN_SKULL = registerCurio("obsidian_skull", builder -> builder.infos(0).rarity(GREEN)), // 黑曜石骷髅头
            MOLTEN_SKULL_ROSE = registerCurio("molten_skull_rose", builder -> builder.rarity(LIGHT_PURPLE).infos(0).tooltips(2)), // 熔火骷髅头玫瑰
            OBSIDIAN_SKULL_ROSE = registerCurio("obsidian_skull_rose", builder -> builder.infos(0).rarity(PINK).tooltips(1)), // 黑曜石骷髅头玫瑰
            HAND_WARMER = registerCurio("hand_warmer", builder -> builder.infos(0).rarity(GREEN)), // 暖手宝
            PUTRID_SCENT = registerCurio("putrid_scent", builder -> builder.rarity(LIGHT_PURPLE)), // 腐香囊
            SHACKLE = registerCurio("shackle", builder -> builder.rarity(BLUE).noTooltip()), // 镣铐
            RAM_RUNE = registerDirectly("ram_rune", name -> new RamRune(BaseCurioItem.builder(name).rarity(GREEN).tooltips(1).infos(0))); // 牧羊符文

    public static final PortDeferredItem<BaseCurioItem> TOOLBELT = registerCurio("toolbelt", builder -> builder.noTooltip().rarity(ORANGE)), // 工具腰带
            TOOLBOX = registerCurio("toolbox", builder -> builder.noTooltip().rarity(GREEN)), // 工具箱
            EXTENDO_GRIP = registerCurio("extendo_grip", builder -> builder.noTooltip().rarity(ORANGE)), // 加长握爪
            PORTABLE_CEMENT_MIXER = registerCurio("portable_cement_mixer", builder -> builder.rarity(ORANGE)), // 便携式水泥搅拌机
            BRICK_LAYER = registerCurio("brick_layer", builder -> builder.rarity(ORANGE)), // 砌砖刀
            ARCHITECT_GIZMO_PACK = registerCurio("architect_gizmo_pack", builder -> builder.infos(0).rarity(PINK)), // 建筑师发明背包
            ANCIENT_CHISEL = registerCurio("ancient_chisel", builder -> {}), // 远古凿子
            HAND_OF_CREATION = registerDirectly("hand_of_creation", name -> new StepStool(BaseCurioItem.builder(name).tooltips(1).infos(0).rarity(LIGHT_PURPLE))); // 创造之手

    public static final PortDeferredItem<BaseCurioItem> BAND_OF_REGENERATION = registerDirectly("band_of_regeneration", name -> new BandOfRegeneration(BaseCurioItem.builder(name)));

    public static final PortDeferredItem<BaseCurioItem> COPPER_WATCH = registerCurio("copper_watch", builder -> builder.rarity(WHITE).infos(0)), // 铜表
            TIN_WATCH = registerCurio("tin_watch", builder -> builder.infos(0).rarity(WHITE)), // 锡表
            SILVER_WATCH = registerCurio("silver_watch", builder -> builder.infos(0).rarity(WHITE)), // 银表
            TUNGSTEN_WATCH = registerCurio("tungsten_watch", builder -> builder.infos(0).rarity(WHITE)), // 钨表
            GOLD_WATCH = registerDirectly("gold_watch", (name, builder) -> new MultiInfoCurioItem(builder.makesPiglinsNeutral().infos(0))), // 金表
            PLATINUM_WATCH = registerDirectly("platinum_watch", (name, builder) -> new MultiInfoCurioItem(builder.infos(0))), // 铂金表
            DEPTH_METER = registerDirectly("depth_meter", (name, builder) -> new MultiInfoCurioItem(builder)), // 深度计
            COMPASS = registerDirectly("compass", (name, builder) -> new MultiInfoCurioItem(builder)), // 罗盘
            RADAR = registerDirectly("radar", (name, builder) -> new MultiInfoCurioItem(builder)), // 雷达
            LIFE_FORM_ANALYZER = registerDirectly("life_form_analyzer", (name, builder) -> new MultiInfoCurioItem(builder)), // 生命体分析机
            TALLY_COUNTER = registerDirectly("tally_counter", (name, builder) -> new MultiInfoCurioItem(builder)), // 杀怪计数器
            METAL_DETECTOR = registerDirectly("metal_detector", (name, builder) -> new MetalDetector(builder)), // 金属探测器
            STOPWATCH = registerDirectly("stopwatch", (name, builder) -> new MultiInfoCurioItem(builder.infos(0))), // 秒表
            DPS_METER = registerDirectly("dps_meter", (name, builder) -> new MultiInfoCurioItem(builder.infos(0))), // 每秒伤害计数器
            FISHERMANS_POCKET_GUIDE = registerDirectly("fishermans_pocket_guide", (name, builder) -> new MultiInfoCurioItem(builder)), // 渔民袖珍宝典
            WEATHER_RADIO = registerDirectly("weather_radio", (name, builder) -> new MultiInfoCurioItem(builder)), // 天气收音机
            SEXTANT = registerDirectly("sextant", (name, builder) -> new MultiInfoCurioItem(builder)), // 六分仪
            GPS = registerDirectly("gps", (name, builder) -> new MultiInfoCurioItem(builder.rarity(ORANGE).infos(0).tooltips(2))), // 全球定位系统
            REK_3000 = registerDirectly("rek_3000", (name, builder) -> new MultiInfoCurioItem(builder.rarity(ORANGE).infos(0).tooltips(2))), // R.E.K.3000
            GOBLIN_TECH = registerDirectly("goblin_tech", (name, builder) -> new MultiInfoCurioItem(builder.rarity(ORANGE).infos(0).tooltips(2))), // 哥布林数据仪
            FISH_FINDER = registerDirectly("fish_finder", (name, builder) -> new MultiInfoCurioItem(builder.rarity(ORANGE).infos(0).tooltips(2))), // 探鱼器
            PDA = registerDirectly("pda", (name, builder) -> new MultiInfoCurioItem(builder.rarity(PINK).infos(0).tooltips(11))); // 个人数字助手

    public static final PortDeferredItem<BaseCurioItem> STEP_STOOL = registerDirectly("step_stool", name -> new StepStool(BaseCurioItem.builder(name))), // 梯凳
            FLYING_CARPET = registerCurio("flying_carpet", builder -> builder.rarity(GREEN).particle(TerraCurio.asResource("carpet_dust"), ParticleTriggers.CARPET_FLYING)), // 飞毯
            AGLET = registerCurio("aglet", builder -> builder.noTooltip()), // 金属带扣
            ANKLET_OF_THE_WIND = registerCurio("anklet_of_the_wind", builder -> builder.infos(0).noTooltip()), // 疾风脚镯
            MAGILUMINESCENCE = registerDirectly("magiluminescence", name -> new BaseCurioItem(BaseCurioItem.builder(name).tooltips(1))), // 魔光护符
            LAVA_CHARM = registerCurio("lava_charm", builder -> builder.rarity(ORANGE)), // 熔岩护身符
            MAGMA_SKULL = registerCurio("magma_skull", builder -> builder.infos(0).tooltips(1).rarity(PINK).particle(TerraCurio.asResource("magma_ember"), ParticleTriggers.ALWAYS)), // 岩浆骷髅头
            MOLTEN_CHARM = registerCurio("molten_charm", builder -> builder.tooltips(1).infos(0).rarity(PINK).particle(TerraCurio.asResource("magma_ember"), ParticleTriggers.ALWAYS)), // 熔火护身符
            CLIMBING_CLAWS = registerCurio("climbing_claws", builder -> builder.tooltips(1).particle(TerraCurio.asResource("wall_dust"), ParticleTriggers.WALL_CLIMBING)), // 攀爬爪
            SHOE_SPIKES = registerCurio("shoe_spikes", builder -> builder.tooltips(1).particle(TerraCurio.asResource("wall_dust"), ParticleTriggers.WALL_CLIMBING)), // 鞋钉
            TIGER_CLIMBING_GEAR = registerCurio("tiger_climbing_gear", builder -> builder.infos(0).rarity(GREEN).particle(TerraCurio.asResource("wall_dust"), ParticleTriggers.WALL_CLIMBING)), // 猛虎攀爬装备
            TABI = registerCurio("tabi", builder -> builder.rarity(LIME).particle(TerraCurio.asResource("sprint_dash"), ParticleTriggers.SPRINT_DASH)), // 分趾厚底袜
            MASTER_NINJA_GEAR = registerCurio("master_ninja_gear", builder -> builder.tooltips(2).infos(0).rarity(YELLOW).particle(TerraCurio.asResource("wall_dust"), ParticleTriggers.WALL_CLIMBING).particle(TerraCurio.asResource("sprint_dash"), ParticleTriggers.SPRINT_DASH)), // 忍者大师装备
            ICE_SKATES = registerCurio("ice_skates", builder -> builder.rarity(BLUE).particle(TerraCurio.asResource("ice_shards"), ParticleTriggers.ON_ICE)), // 溜冰鞋
            HERMES_BOOTS = registerDirectly("hermes_boots", name -> new BaseSpeedBoots(1, 40, BaseCurioItem.builder(name).stepHeight())), // 赫尔墨斯靴
            FLURRY_BOOTS = registerDirectly("flurry_boots", name -> new BaseSpeedBoots(1, 40, BaseCurioItem.builder(name).infos(1).stepHeight())), // 疾风雪靴
            SAILFISH_BOOTS = registerDirectly("sailfish_boots", name -> new BaseSpeedBoots(1, 40, BaseCurioItem.builder(name).stepHeight())), // 旗鱼靴
            DUNERIDER_BOOTS = registerDirectly("dunerider_boots", DuneriderBoots::new), // 沙丘行者靴
            ROCKET_BOOTS = registerCurio("rocket_boots", builder -> builder.infos(0).particle(TerraCurio.asResource("rocket_flame"), ParticleTriggers.ROCKET_FLYING).particle(TerraCurio.asResource("rocket_boost_burst"), ParticleTriggers.ROCKET_BOOST)), // 火箭靴
            SPECTRE_BOOTS = registerDirectly("spectre_boots", name -> new BaseSpeedBoots(1, 40, BaseCurioItem.builder(name).rarity(LIGHT_RED).infos(0).stepHeight().particle(TerraCurio.asResource("spectre_flight"), ParticleTriggers.SPECTRE_FLYING))), // 幽灵靴
            FAIRY_BOOTS = registerDirectly("fairy_boots", name -> new BaseSpeedBoots(1, 40, BaseCurioItem.builder(name).infos(0).tooltips(1).rarity(PINK).stepHeight().particle(TerraCurio.asResource("flower_growth"), ParticleTriggers.WALKING_ON_GRASS, ParticlePlacements.FEET).particle(TerraCurio.asResource("fairy_flight"), ParticleTriggers.FAIRY_FLYING))), // 仙灵靴
            LIGHTNING_BOOTS = registerDirectly("lightning_boots", name -> new BaseSpeedBoots(1, 40, BaseCurioItem.builder(name).infos(0).rarity(PINK).stepHeight().particle(TerraCurio.asResource("lightning_flight"), ParticleTriggers.LIGHTNING_FLYING))), // 闪电靴
            FROSTSPARK_BOOTS = registerDirectly("frostspark_boots", name -> new BaseSpeedBoots(1, 40, BaseCurioItem.builder(name).infos(0).rarity(LIME).stepHeight().particle(TerraCurio.asResource("frostspark_flight"), ParticleTriggers.FROSTSPARK_FLYING).particle(TerraCurio.asResource("ice_shards"), ParticleTriggers.ON_ICE))), // 霜花靴
            WATER_WALKING_BOOTS = registerCurio("water_walking_boots", builder -> builder.rarity(LIGHT_RED).particle(TerraCurio.asResource("water_ripple"), ParticleTriggers.WATER_WALKING, ParticlePlacements.FEET)), // 水上漂靴
            OBSIDIAN_WATER_WALKING_BOOTS = registerCurio("obsidian_water_walking_boots", builder -> builder.infos(0).rarity(LIGHT_RED).tooltips(1).particle(TerraCurio.asResource("lava_fizz"), ParticleTriggers.IN_LAVA).particle(TerraCurio.asResource("water_ripple"), ParticleTriggers.WATER_WALKING, ParticlePlacements.FEET)), // 黑曜石水上漂靴
            LAVA_WADERS = registerCurio("lava_waders", builder -> builder.rarity(LIME).tooltips(1).infos(0).particle(TerraCurio.asResource("lava_fizz"), ParticleTriggers.IN_LAVA).particle(TerraCurio.asResource("water_ripple"), ParticleTriggers.WATER_WALKING, ParticlePlacements.FEET)), // 熔岩靴
            TERRASPARK_BOOTS = registerDirectly("terraspark_boots", name -> new BaseSpeedBoots(1, 40, BaseCurioItem.builder(name).rarity(LIME).tooltips(3).infos(0).stepHeight().particle(TerraCurio.asResource("terraspark"), ParticleTriggers.RUNNING).particle(TerraCurio.asResource("terraspark_flight"), ParticleTriggers.TERRASPARK_FLYING).particle(TerraCurio.asResource("ice_shards"), ParticleTriggers.ON_ICE).particle(TerraCurio.asResource("lava_fizz"), ParticleTriggers.IN_LAVA).particle(TerraCurio.asResource("water_ripple"), ParticleTriggers.WATER_WALKING, ParticlePlacements.FEET))), // 泰拉闪耀靴
            CLOUD_IN_A_BOTTLE = registerCurio("cloud_in_a_bottle", builder -> builder.particle(TerraCurio.asResource("cloud"), ParticleTriggers.CLOUD_JUMP)), // 云朵瓶
            BLIZZARD_IN_A_BOTTLE = registerCurio("blizzard_in_a_bottle", builder -> builder.infos(0).particle(TerraCurio.asResource("blizzard"), ParticleTriggers.BLIZZARD_JUMP)), // 暴雪瓶
            SANDSTORM_IN_A_BOTTLE = registerCurio("sandstorm_in_a_bottle", builder -> builder.rarity(GREEN).particle(TerraCurio.asResource("sandstorm"), ParticleTriggers.SANDSTORM_JUMP)), // 沙暴瓶
            FART_IN_A_JAR = registerCurio("fart_in_a_jar", builder -> builder.rarity(GREEN).particle(TerraCurio.asResource("fart_cloud"), ParticleTriggers.FART_JUMP)), // 罐中臭屁
            TSUNAMI_IN_A_BOTTLE = registerCurio("tsunami_in_a_bottle", builder -> builder.particle(TerraCurio.asResource("tsunami"), ParticleTriggers.TSUNAMI_JUMP)), // 海啸瓶
            SHINY_RED_BALLOON = registerCurio("shiny_red_balloon", builder -> {}), // 闪亮红气球
            BALLOON_PUFFERFISH = registerCurio("balloon_pufferfish", builder -> builder.infos(0)), // 气球河豚鱼
            CLOUD_IN_A_BALLOON = registerCurio("cloud_in_a_balloon", builder -> builder.rarity(LIGHT_RED).tooltips(1).infos(0).particle(TerraCurio.asResource("cloud"), ParticleTriggers.CLOUD_JUMP)), // 云朵气球
            BLIZZARD_IN_A_BALLOON = registerCurio("blizzard_in_a_balloon", builder -> builder.infos(0).rarity(LIGHT_RED).tooltips(1).infos(0).particle(TerraCurio.asResource("blizzard"), ParticleTriggers.BLIZZARD_JUMP)), // 暴雪气球
            SANDSTORM_IN_A_BALLOON = registerCurio("sandstorm_in_a_balloon", builder -> builder.rarity(LIGHT_RED).tooltips(1).infos(0).particle(TerraCurio.asResource("sandstorm"), ParticleTriggers.SANDSTORM_JUMP)), // 沙暴气球
            FART_IN_A_BALLOON = registerCurio("fart_in_a_balloon", builder -> builder.rarity(LIGHT_RED).tooltips(1).infos(0).particle(TerraCurio.asResource("fart_cloud"), ParticleTriggers.FART_JUMP)), // 臭屁气球
            SHARKRON_BALLOON = registerCurio("sharkron_balloon", builder -> builder.infos(0).particle(TerraCurio.asResource("tsunami"), ParticleTriggers.TSUNAMI_JUMP)), // 鲨鱼龙气球
            HONEY_BALLOON = registerCurio("honey_balloon", builder -> builder.rarity(GREEN).tooltips(1).infos(0)), // 蜂蜜气球
            BUNDLE_OF_BALLOONS = registerCurio("bundle_of_balloons", builder -> builder.rarity(YELLOW).tooltips(1).infos(0)
                    .particle(TerraCurio.asResource("cloud"), ParticleTriggers.CLOUD_JUMP)
                    .particle(TerraCurio.asResource("sandstorm"), ParticleTriggers.SANDSTORM_JUMP)
                    .particle(TerraCurio.asResource("blizzard"), ParticleTriggers.BLIZZARD_JUMP)), // 气球束（按跳跃能力分段播放）
            LUCKY_HORSESHOE = registerCurio("lucky_horseshoe", builder -> builder.tooltips(1)), // 幸运马掌
            OBSIDIAN_HORSESHOE = registerCurio("obsidian_horseshoe", builder -> builder.infos(0).rarity(LIGHT_RED).tooltips(1)), // 黑曜石马掌
            BLUE_HORSESHOE_BALLOON = registerCurio("blue_horseshoe_balloon", builder -> builder.rarity(LIGHT_RED).tooltips(2).infos(0).particle(TerraCurio.asResource("cloud"), ParticleTriggers.CLOUD_JUMP)), // 蓝马掌气球
            WHITE_HORSESHOE_BALLOON = registerCurio("white_horseshoe_balloon", builder -> builder.rarity(LIGHT_RED).tooltips(2).infos(0).particle(TerraCurio.asResource("blizzard"), ParticleTriggers.BLIZZARD_JUMP)), // 白马掌气球
            YELLOW_HORSESHOE_BALLOON = registerCurio("yellow_horseshoe_balloon", builder -> builder.rarity(LIGHT_RED).tooltips(2).infos(0).particle(TerraCurio.asResource("sandstorm"), ParticleTriggers.SANDSTORM_JUMP)), // 黄马掌气球
            GREEN_HORSESHOE_BALLOON = registerCurio("green_horseshoe_balloon", builder -> builder.rarity(LIGHT_RED).tooltips(2).infos(0).particle(TerraCurio.asResource("fart_cloud"), ParticleTriggers.FART_JUMP)), // 绿马掌气球
            PINK_HORSESHOE_BALLOON = registerCurio("pink_horseshoe_balloon", builder -> builder.rarity(LIGHT_RED).tooltips(2).infos(0)), // 粉马掌气球
            AMBER_HORSESHOE_BALLOON = registerCurio("amber_horseshoe_balloon", builder -> builder.rarity(LIGHT_RED).tooltips(2).infos(0)), // 琥珀马掌气球
            BUNDLE_OF_HORSESHOE_BALLOONS = registerCurio("bundle_of_horseshoe_balloons", builder -> builder.rarity(YELLOW).tooltips(2).infos(0)
                    .particle(TerraCurio.asResource("cloud"), ParticleTriggers.CLOUD_JUMP)
                    .particle(TerraCurio.asResource("sandstorm"), ParticleTriggers.SANDSTORM_JUMP)
                    .particle(TerraCurio.asResource("blizzard"), ParticleTriggers.BLIZZARD_JUMP)), // 马掌气球束（按跳跃能力分段播放）
            INNER_TUBE = registerCurio("inner_tube", builder -> builder.rarity(WHITE).particle(TerraCurio.asResource("water_ripple"), ParticleTriggers.FLOATING_ON_WATER, ParticlePlacements.WATER_SURFACE)), // 游泳圈
            FLIPPER = registerCurio("flipper", builder -> builder.noTooltip().particle(TerraCurio.asResource("swim_foam"), ParticleTriggers.SWIMMING)), // 脚蹼
            DIVING_GEAR = registerCurio("diving_gear", builder -> builder.infos(0).rarity(LIGHT_RED).equipable(EquipmentSlot.HEAD).particle(TerraCurio.asResource("bubble"), ParticleTriggers.UNDERWATER, ParticlePlacements.MOUTH)), // 潜水装备
            JELLYFISH_NECKLACE = registerDirectly("jellyfish_necklace", name -> new BaseCurioItem(BaseCurioItem.builder(name).rarity(GREEN).particle(TerraCurio.asResource("bubble"), ParticleTriggers.UNDERWATER, ParticlePlacements.MOUTH))), // 水母项链
            JELLYFISH_DIVING_GEAR = registerDirectly("jellyfish_diving_gear", name -> new BaseCurioItem(BaseCurioItem.builder(name).rarity(PINK).tooltips(1).infos(0).particle(TerraCurio.asResource("bubble"), ParticleTriggers.UNDERWATER, ParticlePlacements.MOUTH))), // 水母潜水装备
            ARCTIC_DIVING_GEAR = registerDirectly("arctic_diving_gear", name -> new BaseCurioItem(BaseCurioItem.builder(name).rarity(LIGHT_PURPLE).tooltips(2).particle(TerraCurio.asResource("bubble"), ParticleTriggers.UNDERWATER, ParticlePlacements.MOUTH))), // 北极潜水装备
            FROG_LEG = registerCurio("frog_leg", builder -> builder.tooltips(1)), // 蛙腿
            FROG_FLIPPER = registerCurio("frog_flipper", builder -> builder.tooltips(1).infos(0).particle(TerraCurio.asResource("swim_foam"), ParticleTriggers.SWIMMING)), // 青蛙脚蹼
            FROG_WEBBING = registerCurio("frog_webbing", builder -> builder.rarity(PINK).tooltips(2).infos(0).particle(TerraCurio.asResource("wall_dust"), ParticleTriggers.WALL_CLIMBING)), // 青蛙蹼
            FROG_GEAR = registerCurio("frog_gear", builder -> builder.rarity(PINK).tooltips(3).infos(0).particle(TerraCurio.asResource("wall_dust"), ParticleTriggers.WALL_CLIMBING).particle(TerraCurio.asResource("swim_foam"), ParticleTriggers.SWIMMING)), // 青蛙装备
            AMBHIPIAN_BOOTS = registerDirectly("ambhipian_boots", name -> new BaseSpeedBoots(1, 40, BaseCurioItem.builder(name).infos(0).stepHeight())); // 水陆两用靴

    public static final PortDeferredItem<BaseCurioItem> TREASURE_MAGNET = registerCurio("treasure_magnet", builder -> {}), // 宝藏磁石
            FLOWER_BOOTS = registerCurio("flower_boots", builder -> builder.rarity(LIME).particle(TerraCurio.asResource("flower_growth"), ParticleTriggers.WALKING_ON_GRASS, ParticlePlacements.FEET)); // 花靴

    public static final PortDeferredItem<BaseCurioItem> ANGLER_EARRING = registerCurio("angler_earring", builder -> builder.noTooltip()); // 渔夫耳环

    public static final PortDeferredItem<BaseCurioItem> ROYAL_GEL = registerCurio("royal_gel", builder -> builder.rarity(EXPERT)), // 皇家凝胶
            SHIELD_OF_CTHULHU = registerDirectly("shield_of_cthulhu", (name, builder) -> new ShieldOfCthulhu(builder.rarity(EXPERT).noTooltip().particle(TerraCurio.asResource("cthulhu_dash"), ParticleTriggers.DASHING))), // 克苏鲁护盾
            WORM_SCARF = registerCurio("worm_scarf", builder -> builder.rarity(EXPERT).particle(TerraCurio.asResource("scarf_mist"), ParticleTriggers.ALWAYS)), // 蠕虫围巾
            BRAIN_OF_CONFUSION = registerCurio("brain_of_confusion", builder -> builder.rarity(EXPERT).tooltips(2)), // 混乱之脑
            HIVE_PACK = registerCurio("hive_pack", builder -> builder.rarity(EXPERT)), // 蜂巢背包
            BONE_GLOVE = registerCurio("bone_glove", builder -> builder.rarity(EXPERT)), // 骨头手套
    /* 骸骨头盔 */
    /* 挥发明胶 */
    /* 孢子囊 */
    SHINY_STONE = registerDirectly("shiny_stone", name -> new ShinnyStone(BaseCurioItem.builder(name).rarity(EXPERT))), // 闪亮石
            SOARING_INSIGNIA = registerCurio("soaring_insignia", builder -> builder.rarity(EXPERT).particle(TerraCurio.asResource("infinite_glow"), ParticleTriggers.INFINITE_FLYING)), // 翱翔徽章
            GRAVITY_GLOBE = registerDirectly("gravity_globe", (name, builder) -> new GravityGlobe(builder.rarity(EXPERT))), // 重力球
            CELESTIAL_STARBOARD = registerCurio("celestial_starboard", builder -> builder.rarity(EXPERT).tooltips(2).particle(TerraCurio.asResource("flying_sparkle"), ParticleTriggers.FLYING)); // 天界星盘

    public static final PortDeferredItem<BaseCurioItem> FLEDGLING_WINGS = registerWings("fledgling_wings", WHITE),  // 飞行高度：12
            ANGEL_WINGS = registerWings("angel_wings", PINK),  // 飞行高度：34
            DEMON_WINGS = registerWings("demon_wings", PINK), // 飞行高度：34
            FAIRY_WINGS = registerWings("fairy_wings", PINK), // 飞行高度：44
            FIN_WINGS = registerWings("fin_wings", LIGHT_RED), // 飞行高度：44
            FROZEN_WINGS = registerWings("frozen_wings", PINK),  // 飞行高度：44
            HARPY_WINGS = registerWings("harpy_wings", PINK), // 飞行高度：44
            JETPACK = registerWings("jetpack", PINK),  // 飞行高度：51
            LEAF_WINGS = registerWings("leaf_wings", PINK),  // 飞行高度：34
            BAT_WINGS = registerWings("bat_wings", PINK), // 飞行高度：54
            BEE_WINGS = registerWings("bee_wings", PINK), // 飞行高度：54
            BUTTERFLY_WINGS = registerWings("butterfly_wings", PINK), // 飞行高度：54
            FLAME_WINGS = registerWings("flame_wings", PINK), // 飞行高度：54
            HOVERBOARD = registerWings("hoverboard", PINK), // 飞行高度：62
            BONE_WINGS = registerWings("bone_wings", PINK), // 飞行高度：62
            MOTHRON_WINGS = registerWings("mothron_wings", YELLOW), // 飞行高度：62
            SPECTRE_WINGS = registerWings("spectre_wings", YELLOW), // 飞行高度：62
            BEETLE_WINGS = registerWings("beetle_wings", LIME), // 飞行高度：62
            FESTIVE_WINGS = registerWings("festive_wings", PINK), // 飞行高度：71
            SPOOKY_WINGS = registerWings("spooky_wings", LIME), // 飞行高度：71
            TATTERED_WINGS = registerWings("tattered_wings", LIME), // 飞行高度：71
            STEAMPUNK_WINGS = registerWings("steampunk_wings", YELLOW), // 飞行高度：71
            BETSYS_WINGS = registerWings("betsys_wings", YELLOW), // 飞行高度：79
            EMPRESS_WINGS = registerWings("empress_wings", CYAN),  // 飞行高度：85
            FISHRON_WINGS = registerWings("fishron_wings", YELLOW),  // 飞行高度：95
            NEBULA_WINGS = registerWings("nebula_wings", RED), // 飞行高度：95
            VORTEX_BOOSTER = registerWings("vortex_booster", RED), // 飞行高度：95
            SOLAR_WINGS = registerWings("solar_wings", RED), // 飞行高度：95
            STARDUST_WINGS = registerWings("stardust", RED); // 飞行高度：95

    private static PortDeferredItem<BaseCurioItem> registerWings(String name, ModRarity rarity) {
        return WINGS.register(name, () -> {
            BaseCurioItem.Builder builder = BaseCurioItem.builder(name);
            builder.rarity(rarity).particle(TerraCurio.asResource("flying_sparkle"), ParticleTriggers.FLYING);
            return builder.build();
        });
    }

    public static PortDeferredItem<BaseCurioItem> registerCurio(String name, Consumer<BaseCurioItem.Builder> consumer) {
        return CURIOS.register(name, () -> {
            BaseCurioItem.Builder builder = BaseCurioItem.builder(name);
            consumer.accept(builder);
            return builder.build();
        });
    }

    public static PortDeferredItem<BaseCurioItem> registerCurio(String name, ModRarity rarity) {
        return CURIOS.register(name, () -> {
            Item.Properties properties = new Item.Properties().component(ConfluenceMagicLib.MOD_RARITY, rarity);
            if (rarity != WHITE && rarity != GRAY) properties.fireResistant();
            return new BaseCurioItem(properties);
        });
    }

    public static PortDeferredItem<BaseCurioItem> registerCurio(String name, Supplier<BaseCurioItem> supplier) {
        return CURIOS.register(name, () -> supplier.get());
    }

    public static PortDeferredItem<BaseCurioItem> registerDirectly(String name, Function<String, BaseCurioItem> function) {
        return CURIOS.register(name, () -> function.apply(name));
    }

    public static PortDeferredItem<BaseCurioItem> registerDirectly(String name, BiFunction<String, BaseCurioItem.Builder, BaseCurioItem> function) {
        return CURIOS.register(name, () -> function.apply(name, BaseCurioItem.builder(name)));
    }
}
