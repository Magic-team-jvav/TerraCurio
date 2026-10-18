package org.confluence.terra_curio.common.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.item.GroupItem;
import org.confluence.lib.util.WipNotDisplayOutput;
import org.confluence.terra_curio.TerraCurio;

public final class TCTabs {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, TerraCurio.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> ACCESSORIES = TABS.register("accessories",
            () -> CreativeModeTab.builder().icon(() -> TCItems.ICON.get().getDefaultInstance())
                    .title(Component.translatable("creativetab.terra_curio"))
                    .displayItems((parameters, output) -> {
                        output = new WipNotDisplayOutput(output);
                        /// 分类依据泰拉瑞亚 Wiki「饰品」条目的分类：移动、信息、生命和魔力、战斗、减益免疫、建筑、悠悠球、其他、专家模式独有。
                        /// 组名取自 {@code itemGroup.terra_curio.<组名>}；悠悠球类的物品由汇流来世注册，在其事件中归入同一组。
                        // 移动
                        acceptGroup(output, "movement",
                                // 潜水与游泳
                                TCItems.DIVING_HELMET, TCItems.NEPTUNES_SHELL, TCItems.MOON_SHELL, TCItems.CELESTIAL_SHELL,
                                TCItems.INNER_TUBE, TCItems.FLIPPER, TCItems.DIVING_GEAR,
                                TCItems.JELLYFISH_NECKLACE, TCItems.JELLYFISH_DIVING_GEAR, TCItems.ARCTIC_DIVING_GEAR,
                                // 加速
                                TCItems.AGLET, TCItems.ANKLET_OF_THE_WIND, TCItems.MAGILUMINESCENCE,
                                // 跳跃瓶与气球
                                TCItems.CLOUD_IN_A_BOTTLE, TCItems.BLIZZARD_IN_A_BOTTLE, TCItems.SANDSTORM_IN_A_BOTTLE,
                                TCItems.FART_IN_A_JAR, TCItems.TSUNAMI_IN_A_BOTTLE,
                                TCItems.SHINY_RED_BALLOON, TCItems.BALLOON_PUFFERFISH, TCItems.CLOUD_IN_A_BALLOON,
                                TCItems.BLIZZARD_IN_A_BALLOON, TCItems.SANDSTORM_IN_A_BALLOON, TCItems.FART_IN_A_BALLOON,
                                TCItems.SHARKRON_BALLOON, TCItems.HONEY_BALLOON, TCItems.BUNDLE_OF_BALLOONS,
                                // 坠落伤害
                                TCItems.LUCKY_HORSESHOE, TCItems.BLUE_HORSESHOE_BALLOON, TCItems.WHITE_HORSESHOE_BALLOON,
                                TCItems.YELLOW_HORSESHOE_BALLOON, TCItems.GREEN_HORSESHOE_BALLOON,
                                TCItems.PINK_HORSESHOE_BALLOON, TCItems.AMBER_HORSESHOE_BALLOON,
                                TCItems.BUNDLE_OF_HORSESHOE_BALLOONS,
                                // 攀爬与冲刺
                                TCItems.CLIMBING_CLAWS, TCItems.SHOE_SPIKES, TCItems.TIGER_CLIMBING_GEAR, TCItems.TABI,
                                TCItems.MASTER_NINJA_GEAR, TCItems.ICE_SKATES, TCItems.WOOL_SHOES,
                                // 靴子
                                TCItems.HERMES_BOOTS, TCItems.FLURRY_BOOTS, TCItems.SAILFISH_BOOTS, TCItems.DUNERIDER_BOOTS,
                                TCItems.ROCKET_BOOTS, TCItems.SPECTRE_BOOTS, TCItems.FAIRY_BOOTS, TCItems.LIGHTNING_BOOTS,
                                TCItems.FROSTSPARK_BOOTS, TCItems.WATER_WALKING_BOOTS, TCItems.OBSIDIAN_WATER_WALKING_BOOTS,
                                TCItems.LAVA_WADERS, TCItems.TERRASPARK_BOOTS,
                                // 蛙类与梯凳、飞毯
                                TCItems.FROG_LEG, TCItems.FROG_FLIPPER, TCItems.FROG_WEBBING, TCItems.FROG_GEAR,
                                TCItems.AMBHIPIAN_BOOTS, TCItems.STEP_STOOL, TCItems.FLYING_CARPET,
                                // 翅膀
                                TCItems.FLEDGLING_WINGS, TCItems.ANGEL_WINGS, TCItems.DEMON_WINGS, TCItems.FAIRY_WINGS,
                                TCItems.FIN_WINGS, TCItems.FROZEN_WINGS, TCItems.HARPY_WINGS, TCItems.JETPACK,
                                TCItems.LEAF_WINGS, TCItems.BAT_WINGS, TCItems.BEE_WINGS, TCItems.BUTTERFLY_WINGS,
                                TCItems.FLAME_WINGS, TCItems.HOVERBOARD, TCItems.BONE_WINGS, TCItems.MOTHRON_WINGS,
                                TCItems.SPECTRE_WINGS, TCItems.BEETLE_WINGS, TCItems.FESTIVE_WINGS, TCItems.SPOOKY_WINGS,
                                TCItems.TATTERED_WINGS, TCItems.STEAMPUNK_WINGS, TCItems.BETSYS_WINGS, TCItems.EMPRESS_WINGS,
                                TCItems.FISHRON_WINGS, TCItems.NEBULA_WINGS, TCItems.VORTEX_BOOSTER, TCItems.SOLAR_WINGS,
                                TCItems.STARDUST_WINGS);
                        // 信息
                        acceptGroup(output, "information",
                                TCItems.COPPER_WATCH, TCItems.TIN_WATCH, TCItems.SILVER_WATCH, TCItems.TUNGSTEN_WATCH,
                                TCItems.GOLD_WATCH, TCItems.PLATINUM_WATCH, TCItems.DEPTH_METER, TCItems.COMPASS,
                                TCItems.RADAR, TCItems.LIFE_FORM_ANALYZER, TCItems.TALLY_COUNTER, TCItems.METAL_DETECTOR,
                                TCItems.STOPWATCH, TCItems.DPS_METER, TCItems.FISHERMANS_POCKET_GUIDE,
                                TCItems.WEATHER_RADIO, TCItems.SEXTANT, TCItems.GPS, TCItems.REK_3000,
                                TCItems.GOBLIN_TECH, TCItems.FISH_FINDER, TCItems.PDA, TCItems.CELL_PHONE);
                        // 生命和魔力
                        acceptGroup(output, "health_and_mana",
                                TCItems.BAND_OF_REGENERATION);
                        // 战斗
                        acceptGroup(output, "combat",
                                TCItems.STAR_CLOAK, TCItems.STAR_VEIL, TCItems.BEE_CLOAK, TCItems.BLACK_BELT,
                                TCItems.SUN_STONE, TCItems.MOON_STONE, TCItems.CELESTIAL_STONE, TCItems.MOON_CHARM,
                                TCItems.COBALT_SHIELD, TCItems.CROSS_NECKLACE,
                                // 徽章
                                TCItems.RANGER_EMBLEM, TCItems.WARRIOR_EMBLEM, TCItems.SORCERER_EMBLEM,
                                TCItems.AVENGER_EMBLEM, TCItems.EYE_OF_THE_GOLEM, TCItems.DESTROYER_EMBLEM,
                                // 手套
                                TCItems.FERAL_CLAWS, TCItems.TITAN_GLOVE, TCItems.POWER_GLOVE, TCItems.MECHANICAL_GLOVE,
                                TCItems.FIRE_GAUNTLET, TCItems.FLESH_KNUCKLES, TCItems.BERSERKERS_GLOVE,
                                // 盾与项链
                                TCItems.PALADINS_SHIELD, TCItems.HERO_SHIELD, TCItems.FROZEN_TURTLE_SHELL,
                                TCItems.FROZEN_SHIELD, TCItems.HONEY_COMB, TCItems.SHARK_TOOTH_NECKLACE,
                                TCItems.STINGER_NECKLACE, TCItems.PANIC_NECKLACE, TCItems.SWEETHEART_NECKLACE,
                                // 箭袋与瞄准镜
                                TCItems.MAGIC_QUIVER, TCItems.MOLTEN_QUIVER, TCItems.STALKERS_QUIVER, TCItems.RIFLE_SCOPE,
                                TCItems.SNIPER_SCOPE, TCItems.RECON_SCOPE,
                                TCItems.MAGMA_STONE, TCItems.PUTRID_SCENT, TCItems.SHACKLE, TCItems.RAM_RUNE);
                        // 减益免疫
                        acceptGroup(output, "debuff_immunity",
                                TCItems.BEZOAR, TCItems.HOLY_WATER, TCItems.DETOXIFICATION_CAPSULE, TCItems.VITAMINS,
                                TCItems.ENERGY_BAR, TCItems.NUTRIENT_SOLUTION, TCItems.BLINDFOLD, TCItems.FLASHLIGHT,
                                TCItems.SEARCHLIGHT, TCItems.FAST_CLOCK, TCItems.TRIFOLD_MAP, TCItems.THE_PLAN,
                                TCItems.HAND_DRILL, TCItems.SHOT_PUT, TCItems.EXPLORERS_EQUIPMENT, TCItems.ANKH_CHARM,
                                TCItems.ANKH_SHIELD, TCItems.LAVA_CHARM, TCItems.MAGMA_SKULL, TCItems.MOLTEN_CHARM,
                                TCItems.OBSIDIAN_ROSE, TCItems.OBSIDIAN_SHIELD, TCItems.OBSIDIAN_SKULL,
                                TCItems.MOLTEN_SKULL_ROSE, TCItems.OBSIDIAN_SKULL_ROSE, TCItems.OBSIDIAN_HORSESHOE,
                                TCItems.HAND_WARMER);
                        // 建筑
                        acceptGroup(output, "construction",
                                TCItems.TOOLBELT, TCItems.TOOLBOX, TCItems.EXTENDO_GRIP, TCItems.PORTABLE_CEMENT_MIXER,
                                TCItems.BRICK_LAYER, TCItems.ARCHITECT_GIZMO_PACK, TCItems.ANCIENT_CHISEL,
                                TCItems.HAND_OF_CREATION);
                        // 其他
                        acceptGroup(output, "other",
                                TCItems.WORKSHOP, TCItems.MAGIC_MIRROR, TCItems.TREASURE_MAGNET, TCItems.FLOWER_BOOTS,
                                TCItems.ANGLER_EARRING, TCItems.DEODORIZER);
                        // 专家模式独有
                        acceptGroup(output, "expert",
                                TCItems.DEMON_HEART, TCItems.ROYAL_GEL, TCItems.SHIELD_OF_CTHULHU, TCItems.WORM_SCARF,
                                TCItems.BRAIN_OF_CONFUSION, TCItems.HIVE_PACK, TCItems.BONE_GLOVE, TCItems.SHINY_STONE,
                                TCItems.SOARING_INSIGNIA, TCItems.GRAVITY_GLOBE, TCItems.CELESTIAL_STARBOARD);
                    })
                    .withTabsAfter(ResourceKey.create(Registries.CREATIVE_MODE_TAB, ResourceLocation.fromNamespaceAndPath(ConfluenceMagicLib.CONFLUENCE_ID, "armors")))
                    .withTabsBefore(
                            ResourceKey.create(Registries.CREATIVE_MODE_TAB, ResourceLocation.fromNamespaceAndPath(ConfluenceMagicLib.CONFLUENCE_ID, "tools")),
                            CreativeModeTabs.SPAWN_EGGS
                    ).build()
    );

    /// 取得指定分类的输出，写入的物品会被归入 {@code itemGroup.terra_curio.<name>} 对应的组。
    public static CreativeModeTab.Output group(CreativeModeTab.Output output, String name) {
        return GroupItem.belongsTo(new GroupItem.BelongsTo(TerraCurio.asResource(name)), output);
    }

    /// 将物品批量写入指定分类。
    public static void acceptGroup(CreativeModeTab.Output output, String name, ItemLike... items) {
        CreativeModeTab.Output grouped = group(output, name);
        for (ItemLike item : items) {
            grouped.accept(item);
        }
    }
}
