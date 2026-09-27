package org.confluence.terra_curio.common.data.gen;

import com.google.common.collect.Iterables;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.data.LanguageProvider;
import org.confluence.lib.common.LibEffects;
import org.confluence.lib.util.LibUtils;
import org.confluence.terra_curio.TerraCurio;
import org.confluence.terra_curio.common.init.TCBlocks;
import org.confluence.terra_curio.common.init.TCEntities;
import org.confluence.terra_curio.common.init.TCItems;

import java.util.function.Supplier;

public class TCLanguageProvider extends LanguageProvider {
    public static final String[] ALL_INFO_EN = {
            "Tell the time",
            "Displays the weather",
            "Displays the phase of the moon",
            "Displays fishing information",
            "Displays the most valuable ore around you",
            "Displays the name of rare creatures around you",
            "Detects enemies around you",
            "Displays how many monsters have been killed",
            "Displays your damage per second",
            "Displays how fast the player is moving",
            "Displays horizontal position",
            "Displays depth"
    };
    public static final String[] ALL_INFO_ZH = {
            "报时",
            "显示天气",
            "显示月相",
            "显示钓鱼信息",
            "显示你周围最贵重的矿石",
            "显示你周围稀有生物的名称",
            "探测你周围的敌人",
            "显示怪物击杀数量",
            "显示你的每秒伤害",
            "显示玩家的移动速度",
            "显示水平位置",
            "显示深度"
    };
    public static final String[] ALL_INFO_NAME = new String[]{
            "minute_watch",
            "weather_radio",
            "sextant",
            "fishermans_pocket_guide",
            "metal_detector",
            "life_form_analyzer",
            "radar",
            "tally_counter",
            "dps_meter",
            "stopwatch",
            "compass",
            "depth_meter"
    };
    private final boolean isEn;

    public TCLanguageProvider(PackOutput output, boolean isEn) {
        super(output, TerraCurio.MODID, isEn ? "en_us" : "zh_cn");
        this.isEn = isEn;
    }

    @Override
    protected void addTranslations() {
        add("creativetab.terra_curio", "Terra Curio", "泰拉饰品");

        add("tooltip.terra_curio.requires_mod_loaded", "This item requires any of the following mods: %s", "该物品需要以下任意模组：%s");
        add("tooltip.terra_curio.hold_and_scroll", "Hold [Left Shift] and scroll the mouse wheel", "按住[左Shift]并滚动鼠标滚轮");
        add("tooltip.terra_curio.right_click", "Right click it on inventory to toggle", "在物品栏内右键以开关");
        for (int i = 0; i < ALL_INFO_EN.length; i++) {
            add("tooltip.terra_curio." + ALL_INFO_NAME[i], ALL_INFO_EN[i], ALL_INFO_ZH[i]);
        }

        add("info.terra_curio.time", "Time: [%s:%s]", "时间: [%s:%s]");
        add("info.terra_curio.radar", "Enemies: %s", "敌人: %s");
        add("info.terra_curio.compass.east", "East: %s, ", "东: %s, ");
        add("info.terra_curio.compass.west", "West: %s, ", "西: %s, ");
        add("info.terra_curio.compass.south", "South: %s", "南: %s");
        add("info.terra_curio.compass.north", "North: %s", "北: %s");
        add("info.terra_curio.depth_meter.surface", "Surface: %s", "地表: %s");
        add("info.terra_curio.depth_meter.underground", "Underground: %s", "地下: %s");
        add("info.terra_curio.tally_counter.unknown", "Kill count unavailable", "击杀数不可用");
        add("info.terra_curio.tally_counter", "Killed '", "已杀死 '");
        add("info.terra_curio.life_form_analyzer.none", "No rare creatures nearby!", "未发现稀有生物");
        add("info.terra_curio.life_form_analyzer", "%s detected nearby!", "发现稀有生物: %s");
        add("info.terra_curio.metal_detector.none", "No treasure nearby!", "未发现稀有方块");
        add("info.terra_curio.metal_detector", "%s detected nearby!", "在附近发现%s!");
        add("info.terra_curio.stopwatch", "Speed: %s m/s", "速度: %s m/s");
        add("info.terra_curio.dps_meter", "DPS: %s", "DPS: %s");
        add("info.terra_curio.sextant.0", "Moon phase: Full Moon", "月相: 满月");
        add("info.terra_curio.sextant.1", "Moon phase: Waning Gibbous", "月相: 亏凸月");
        add("info.terra_curio.sextant.2", "Moon phase: Third Quarter", "月相: 下弦月");
        add("info.terra_curio.sextant.3", "Moon phase: Waning Crescent", "月相: 残月");
        add("info.terra_curio.sextant.4", "Moon phase: New Moon", "月相: 新月");
        add("info.terra_curio.sextant.5", "Moon phase: Waxing Crescent", "月相: 峨眉月");
        add("info.terra_curio.sextant.6", "Moon phase: First Quarter", "月相: 上弦月");
        add("info.terra_curio.sextant.7", "Moon phase: Waxing Gibbous", "月相: 盈凸月");
        add("info.terra_curio.weather_radio.clear", "Weather: Clear", "天气: 晴天");
        add("info.terra_curio.weather_radio.cloudy", "Weather: Cloudy", "天气: 阴天");
        add("info.terra_curio.weather_radio.rain", "Weather: Rain", "天气: 下雨");
        add("info.terra_curio.weather_radio.snow", "Weather: Snow", "天气: 下雪");
        add("info.terra_curio.weather_radio.thunder", "Weather: Thunder", "天气: 雷暴");
        add("info.terra_curio.weather_radio.thunder_snow", "Weather: Thunder Snow", "天气: 雷打雪");
        add("info.terra_curio.fishermans_pocket_guide", "Fishing Power: %s", "渔力: %s");

        add("key.terra_curio.gameplay", "Terra Curio", "泰拉饰品");
        add("key.terra_curio.metal_detector", "Detect Metal", "检测矿物");
        add("key.terra_curio.step_stool", "Step Stool", "上梯凳");
        add("key.terra_curio.flip_gravitation", "Flip Gravitation", "反转重力");
        add("key.terra_curio.cthulhu_sprinting", "Cthulhu Sprinting", "克苏鲁冲刺");

        add("curios.identifier.accessory", "Accessory", "配饰");
        add("accessories.slot.accessory", "Accessory", "配饰");
        add("curios.modifiers.accessory", "When worn as accessory:", "佩戴配饰时：");

        add("container.terra_curio.workshop", "Workshop", "工匠作坊");
        add("title.terra_curio.workshop", "Workshop", "工匠作坊");
        add("title.confluence.workshop", "Workshop", "工匠作坊");

        add("argument.terra_curio.unknown_type", "Unknown Value Type: %s", "未知的值类型: %s");

        add("tooltip.item.terra_curio.demon_heart.1", "Remaining Times: %s", "剩余次数: %s");

        add("terra_curio.subtitle.transmission", "Transmission Magic: Activated", "传送魔法：开启");
        add("terra_curio.subtitle.fart_sound", "Player: Fart Sound", "玩家：放屁声");
        add("terra_curio.subtitle.double_jump", "Player: Double Jump", "玩家：二段跳");
        add("terra_curio.subtitle.shoes_walk", "Shoes: Walking", "鞋：跑动");
        add("terra_curio.subtitle.rocket_boots_boost", "Rocket Boots: Boost", "火箭靴：助推");
        add("terra_curio.subtitle.rocket_boots_stop", "Rocket Boots: Stop", "火箭靴：关闭");


        onlyTooltip(TCItems.BASE_POINT, "基点", "'Can you hear the sound in this silence?'", "“你可听闻这寂静之声？”");
        add(TCItems.BASE_POINT.get().getDescriptionId(), "Base Point", "基点");
        add(TCItems.EVERLASTING.get().getDescriptionId(), "Everlasting", "亘古");
        add(TCItems.CELL_PHONE.get().getDescriptionId(), "Cell Phone", "手机");
        add(TCItems.MAGIC_MIRROR.get().getDescriptionId(), "Magic Mirror", "魔镜");
        add(TCItems.DEMON_HEART.get().getDescriptionId(), "Demon Heart", "恶魔之心");

        onlyInfo(TCItems.AGLET, "鞋带束头", "It can be found in Shipwreck Chests.", "它出现在沉船宝箱中。");
        onlyTooltips(TCItems.AMBER_HORSESHOE_BALLOON, "琥珀马掌气球",
                new String[]{
                        "Increases jump height",
                        "Negates fall damage",
                        "Releases bees and douses the user in honey when damaged"
                },
                new String[]{
                        "增加跳跃高度",
                        "消除掉落伤害",
                        "受到伤害后释放蜜蜂并将使用者浸入蜂蜜中"
                }
        );
        onlyTooltips(TCItems.AMBHIPIAN_BOOTS, "水陆两用靴",
                new String[]{
                        "The wearer can run super fast",
                        "Increases jump height",
                        "Increases fall resistance"
                },
                new String[]{
                        "穿戴者可飞速奔跑",
                        "增加跳跃高度",
                        "增加抗坠落性"
                }
        );
        tooltipAndInfo(TCItems.ANCIENT_CHISEL, "远古凿子", "“Age-old problems require age-old solutions”", "“古老的问题需要古老的解决方案”", "The item can be obtained through Archaeology.", "该物品可以通过考古获得。");
        onlyInfo(TCItems.ANGLER_EARRING, "渔夫耳环", "This item can be located in the chests found within Fisherman Villagers' houses.", "这件物品可以在渔夫村民家中的箱子中找到。");
        onlyTooltip(TCItems.ANKH_CHARM, "十字章护身符", "Grants immunity to most debuffs.", "对大部分减益免疫");
        onlyTooltips(TCItems.ANKH_SHIELD, "十字章护盾", new String[]{"Grants immunity to most debuffs.", "Grants immunity to fire blocks"}, new String[]{"对大部分减益免疫", "对火块免疫"});
        onlyZhName(TCItems.ANKLET_OF_THE_WIND, "疾风脚镯");
        onlyTooltip(TCItems.ARCHITECT_GIZMO_PACK, "建筑师发明背包", "Decreased 'Right Click Delay' by 2, cannot stack the decrease of its materials", "右键点击延迟降低2，且降低效果不能与其材料的降低效果叠加。");
        onlyZhName(TCItems.AVENGER_EMBLEM, "复仇者徽章");
        onlyTooltip(TCItems.BALLOON_PUFFERFISH, "气球河豚鱼", "Increases jump height", "增加跳跃高度");
        tooltipAndInfo(TCItems.BAND_OF_REGENERATION, "再生手环", "Slowly regenerates life", "缓慢再生生命", "The Band of Regeneration is an accessory that has a Chance to be dropped from Witch", "它有几率从女巫身上掉落。");
        onlyTooltips(TCItems.BEE_CLOAK, "蜜蜂斗篷",
                new String[]{
                        "Causes stars to fall after taking damage",
                        "Releases bees and douses the user in honey when damaged"
                },
                new String[]{
                        "受到伤害后会使星星坠落",
                        "受到伤害后释放蜜蜂并将使用者浸入蜂蜜中"
                });
        onlyTooltips(TCItems.BERSERKERS_GLOVE, "狂战士手套",
                new String[]{
                        "Enables auto swing for melee weapons",
                        "Enemies are more likely to target you"
                },
                new String[]{
                        "启用近战武器自动挥动",
                        "多人模式时，敌人更可能以你为目标"
                }
        );
        tooltipAndInfo(TCItems.BEZOAR, "牛黄", "Immunity to Poison", "对中毒免疫", "It has a Chance to be dropped from Cave Spider.", "它有几率从洞穴蜘蛛身上掉落。");
        tooltipAndInfo(TCItems.BLACK_BELT, "黑腰带", "Gives a chance to dodge attacks", "有几率避开攻击", "It has a Chance to be dropped from Wither Skeleton.", "它有几率从凋灵骷髅身上掉落。");
        tooltipAndInfo(TCItems.BLINDFOLD, "蒙眼布", "Immunity to Blindness", "对失明免疫", "It can be discovered in chests found within Ancient City.", "它可以在远古城市内的箱子中找到。");
        onlyTooltips(TCItems.BLIZZARD_IN_A_BALLOON, "暴雪气球",
                new String[]{
                        "Allows the holder to double jump",
                        "Increases jump height"
                },
                new String[]{
                        "可让持有者二段跳",
                        "增加跳跃高度"
                }
        );
        onlyTooltip(TCItems.BLIZZARD_IN_A_BOTTLE, "暴雪瓶", "Allows the holder to double jump", "可让持有者二段跳");
        onlyTooltips(TCItems.BLUE_HORSESHOE_BALLOON, "蓝马掌气球",
                new String[]{
                        "Allows the holder to double jump",
                        "Increases jump height",
                        "Negates fall damage"
                },
                new String[]{
                        "可让持有者二段跳",
                        "增加跳跃高度",
                        "消除掉落伤害"
                }
        );
        onlyTooltips(TCItems.WHITE_HORSESHOE_BALLOON, "白马掌气球",
                new String[]{
                        "Allows the holder to double jump",
                        "Increases jump height",
                        "Negates fall damage"
                },
                new String[]{
                        "可让持有者二段跳",
                        "增加跳跃高度",
                        "消除掉落伤害"
                }
        );
        tooltipsAndInfo(TCItems.BRAIN_OF_CONFUSION, "混乱之脑",
                new String[]{
                        "Has a chance to create illusions and dodge an attack",
                        "Temporarily increase critical chance for 4% after dodge",
                        "May confuse nearby enemies after being struck"
                },
                new String[]{
                        "有几率制造幻觉并躲避攻击",
                        "闪避后暂时增加4%暴击几率",
                        "被击中后可能会迷惑附近的敌人"
                },
                "It has a Chance to be dropped from Zombie Villager.",
                "它有几率从僵尸村民身上掉落。"
        );
        tooltipAndInfo(TCItems.BRICK_LAYER, "砌砖刀", "Decreased 'Right Click Delay' by 1", "右键点击延迟降低1", "This item can be located in the chests found within Toolsmith Villagers' houses.", "这件物品可以在工具匠村民家中的箱子中找到。");
        onlyTooltips(TCItems.BUNDLE_OF_BALLOONS, "气球束",
                new String[]{
                        "Allows the holder to quadruple jump",
                        "Increases jump height"
                },
                new String[]{
                        "可让持有者四段跳",
                        "增加跳跃高度"
                }
        );
        onlyTooltips(TCItems.BUNDLE_OF_HORSESHOE_BALLOONS, "马掌气球束",
                new String[]{
                        "Allows the holder to quadruple jump",
                        "Increases jump height",
                        "Negates fall damage"
                },
                new String[]{
                        "可让持有者四段跳",
                        "增加跳跃高度",
                        "消除掉落伤害"
                }
        );
        onlyTooltip(TCItems.CELESTIAL_STONE, "天界石", "Slightly increases the attribute value", "小幅提高属性值");
        onlyTooltips(TCItems.CELL_PHONE, "手机",
                new String[]{
                        "Displays everything",
                        "Allows you to return home at will"
                },
                new String[]{
                        "显示所有信息",
                        "可随意回家"
                }
        );
        tooltipsAndInfo(TCItems.CLIMBING_CLAWS, "攀爬爪",
                new String[]{
                        "Allows the ability to slide down walls, hold Crouch key to slide down quickly",
                        "Improved ability if combined with Shoe Spikes"
                }, new String[]{
                        "可沿墙滑下，按住潜行键可更快地下滑",
                        "结合鞋钉使用时能力还会有所提升"
                }, "It has a Chance to be dropped from Spider.", "它有几率从蜘蛛身上掉落。");
        onlyTooltips(TCItems.CLOUD_IN_A_BALLOON, "云朵气球",
                new String[]{
                        "Allows the holder to double jump",
                        "Increases jump height"
                },
                new String[]{
                        "可让持有者二段跳",
                        "增加跳跃高度"
                }
        );
        tooltipAndInfo(TCItems.CLOUD_IN_A_BOTTLE, "云朵瓶", "Allows the holder to double jump", "可让持有者二段跳", "It can be found in Chests in the Mineshaft and Dungeon.", "它可以在矿井和地牢的宝箱中找到。");
        tooltipAndInfo(TCItems.COBALT_SHIELD, "钴护盾", "Grants immunity to knockback", "对击退免疫", "It has a Chance to be dropped from Ravager.", "它有几率从劫掠兽身上掉落。");
        tooltipAndInfo(TCItems.COMPASS, "罗盘", "Displays horizontal position", "显示水平位置", "The Compass has a Chance to be dropped from Bats.", "它有几率从蝙蝠身上掉落。");
        onlyTooltip(TCItems.COPPER_WATCH, "铜表", "Tell the time", "报时");
        onlyTooltip(TCItems.TIN_WATCH, "锡表", "Tell the time", "报时");
        onlyTooltip(TCItems.TUNGSTEN_WATCH, "钨表", "Tell the time", "报时");
        tooltipAndInfo(TCItems.CROSS_NECKLACE, "十字项链", "Increases length of invincibility after taking damage", "增加受到伤害后的无敌时间", "It has a Chance to be dropped from Evoker.", "它有几率从唤魔者身上掉落。");
        tooltipAndInfo(TCItems.DEMON_HEART, "恶魔之心", "Permanently increases the number of accessory slots", "永久增加配饰栏数量", "It is dropped by the Wither.", "它必定从凋灵身上掉落。");
        tooltipAndInfo(TCItems.DEPTH_METER, "深度计", "Displays depth", "显示深度", "The DepthMeter has a Chance to be dropped from Bats.", "它有几率从蝙蝠身上掉落。");
        onlyZhName(TCItems.DESTROYER_EMBLEM, "毁灭者徽章");
        onlyTooltip(TCItems.DETOXIFICATION_CAPSULE, "解毒囊", "Immunity to Poison and Wither", "对中毒和凋零免疫");
        tooltipAndInfo(TCItems.DPS_METER, "每秒伤害计数器", "Displays your damage per second", "显示你的每秒伤害", "The DPS Meter has a Chance to be dropped from Creeper.", "它有几率从苦力怕身上掉落。");
        tooltipsAndInfo(TCItems.DUNERIDER_BOOTS, "沙丘行者靴",
                new String[]{
                        "The wearer can run super fast, and even faster on sand",
                        "'Walk without rhythm and you won't attract the worm'"
                },
                new String[]{
                        "穿戴者可飞速奔跑，在沙地上还能跑得更快",
                        "“无节律行走就不会引来蠕虫”"
                },
                "It has a Chance to be dropped from Husk.",
                "它有几率从尸壳身上掉落。"
        );
        tooltipAndInfo(TCItems.ENERGY_BAR, "能量棒", "Immunity to Hunger", "对饥饿免疫", "It has a Chance to be dropped from Zombified Piglin.", "它有几率从僵尸猪灵身上掉落。");
        onlyTooltip(TCItems.EXPLORERS_EQUIPMENT, "探险家宝具", "Immunity to Mining Fatigue and Levitation", "对挖掘疲劳和漂浮免疫");
        onlyInfo(TCItems.EXTENDO_GRIP, "加长握爪", "It can be discovered in chests found within Desert Pyramids.", "它可以在沙漠神殿内的箱子中找到。");
        onlyInfo(TCItems.EYE_OF_THE_GOLEM, "石巨人之眼", "It can be discovered in chests found within Jungle Pyramids.", "它可以在丛林神庙内的箱子中找到。");
        onlyTooltips(TCItems.FAIRY_BOOTS, "仙灵靴",
                new String[]{
                        "Allows flight, super fast running",
                        "Flowers grow on the grass you walk on"
                },
                new String[]{
                        "可飞行、可飞速奔跑",
                        "你走过的草地上会长出花朵"
                }
        );
        onlyTooltips(TCItems.FART_IN_A_BALLOON, "臭屁气球",
                new String[]{
                        "Allows the holder to double jump",
                        "Increases jump height"
                },
                new String[]{
                        "可让持有者二段跳",
                        "增加跳跃高度"
                }
        );
        tooltipAndInfo(TCItems.FART_IN_A_JAR, "罐中臭屁", "Allows the holder to double jump", "可让持有者二段跳", "It has a Chance to be dropped from Ghost.", "它有几率从恶魂身上掉落。");
        tooltipAndInfo(TCItems.FAST_CLOCK, "快走时钟", "Immunity to Slowness", "对缓慢免疫", "It has a Chance to be dropped from Stray.", "它有几率从流浪者身上掉落。");
        tooltipAndInfo(TCItems.FERAL_CLAWS, "猛爪手套", "Enables auto swing for melee weapons", "启用近战武器自动挥动", "It can be discovered in chests found within Jungle Pyramids.", "它可以在丛林神庙内的箱子中找到。");
        onlyTooltips(TCItems.FIRE_GAUNTLET, "烈火手套",
                new String[]{
                        "Enables auto swing for melee weapons",
                        "Increases melee knockback and melee attacks inflict fire damage"
                },
                new String[]{
                        "启用近战武器自动挥动",
                        "增加近战击退并使攻击附着火焰伤害"
                });
        onlyTooltips(TCItems.FISH_FINDER, "探鱼器",
                new String[]{
                        "Displays fishing information",
                        "Displays the weather",
                        "Displays the phase of the moon"
                },
                new String[]{
                        "显示钓鱼信息",
                        "显示天气",
                        "显示月相"
                }
        );
        onlyTooltip(TCItems.TIGER_CLIMBING_GEAR, "猛虎攀爬装备", "Allows the ability to climb walls, hold Crouch key to slide down", "可爬墙，按住潜行键可快速下滑");
        tooltipAndInfo(TCItems.FISHERMANS_POCKET_GUIDE, "渔民袖珍宝典", "Displays fishing information", "显示钓鱼信息", "This item can be located in the chests found within Fisherman Villagers' houses.", "这件物品可以在渔夫村民家中的箱子中找到。");
        tooltipAndInfo(TCItems.FLASHLIGHT, "手电筒", "Immunity to Darkness", "对黑暗免疫", "It can be discovered in chests found within Stronghold.", "它可以在要塞内的箱子中找到。");
        tooltipsAndInfo(TCItems.FLESH_KNUCKLES, "血肉指虎",
                new String[]{
                        "Enemies are more likely to target you"
                },
                new String[]{
                        "多人模式时，敌人更可能以你为目标"
                },
                "It has a Chance to be dropped from Piglin Brute.",
                "它有几率从猪灵蛮兵身上掉落"
        );
        onlyInfo(TCItems.FLIPPER, "脚蹼", "It can be found in Chests in the Shipwreck.", "它出现在沉船宝箱中。");
        tooltipAndInfo(TCItems.FLOWER_BOOTS, "花靴", "Flowers grow on the grass you walk on", "你走过的草地上会长出花朵", "It has a chance of falling from digging through Moss Block.", "它有几率从挖掘苔藓块时掉落。");
        tooltipAndInfo(TCItems.FLURRY_BOOTS, "疾风雪靴", "The wearer can run super fast", "穿戴者可飞速奔跑", "It can be found in Igloo's chest.", "它出现在雪地小屋中。");
        onlyTooltips(TCItems.FROG_FLIPPER, "青蛙脚蹼",
                new String[]{
                        "Increases jump height",
                        "Increases fall resistance"
                },
                new String[]{
                        "增加跳跃高度",
                        "增加抗坠落性"
                }
        );
        onlyTooltips(TCItems.FROG_GEAR, "青蛙装备",
                new String[]{
                        "'It ain't easy being green'",
                        "Increases jump height",
                        "Increases fall resistance",
                        "Allows the ability to climb walls, hold Crouch key to slide down"
                },
                new String[]{
                        "“身为绿皮生物可太难了”",
                        "增加跳跃高度",
                        "增加抗坠落性",
                        "可爬墙，按住潜行键可快速下滑"
                }
        );
        tooltipsAndInfo(TCItems.FROG_LEG, "蛙腿",
                new String[]{
                        "Increases jump height",
                        "Increases fall resistance",
                },
                new String[]{
                        "增加跳跃高度",
                        "增加抗坠落性",
                },
                "The Frog Leg has a Chance to be dropped from Frog.", "它有几率从青蛙身上掉落。");
        onlyTooltips(TCItems.FROG_WEBBING, "青蛙蹼",
                new String[]{
                        "Increases jump height",
                        "Increases fall resistance",
                        "Allows the ability to climb walls, hold Crouch key to slide down"
                },
                new String[]{
                        "增加跳跃高度",
                        "增加抗坠落性",
                        "可爬墙，按住潜行键可快速下滑"
                }
        );
        onlyTooltip(TCItems.FROSTSPARK_BOOTS, "霜花靴", "Allows flight, super fast running, and extra mobility on ice", "可飞行、飞速奔跑、并提供额外冰面行动力");
        onlyTooltips(TCItems.FROZEN_SHIELD, "冰冻护盾",
                new String[]{
                        "Absorbs 25% of damage done to players on your team when above 25% life",
                        "Puts a shell around the owner when below 50% life that reduces damage by 25%"
                },
                new String[]{
                        "当生命值超过25%时，吸收对团队中玩家造成的25%伤害",
                        "当生命值低于50%时，在主人周围放置一个外壳，使伤害降低25%"
                }
        );
        tooltipAndInfo(TCItems.FROZEN_TURTLE_SHELL, "冰冻海龟壳", "Puts a shell around the owner when below 50% life that reduces damage by 25%", "当生命值低于50%时，在主人周围放置一个外壳，使伤害降低25%", "When the Turtle died for Freezing chance to dropped this item.", "当海龟因冰冻而死时，有几率掉落这个饰品。");
        onlyTooltips(TCItems.GOBLIN_TECH, "哥布林数据仪",
                new String[]{
                        "Displays the most valuable ore around you",
                        "Displays how fast the player is moving",
                        "Displays your damage per second"
                },
                new String[]{
                        "显示你周围最贵重的矿石",
                        "显示玩家的移动速度",
                        "显示你的每秒伤害"
                }
        );
        onlyTooltip(TCItems.GOLD_WATCH, "金表", "Tell the time", "报时");
        onlyTooltips(TCItems.GPS, "全球定位系统",
                new String[]{
                        "Tell the time",
                        "Displays depth",
                        "Displays horizontal position"
                },
                new String[]{
                        "报时",
                        "显示深度",
                        "显示水平位置"
                }
        );
        tooltipAndInfo(TCItems.GRAVITY_GLOBE, "重力球",
                "Allows the holder to reverse gravity",
                "可让持有者反转重力",
                "It has a Chance to be dropped from Shulker.",
                "它有几率从潜影贝身上掉落。"
        );
        addTooltip(TCItems.GRAVITY_GLOBE, 1, "Press [%s] to change gravity", "按 [%s] 可改变重力");
        onlyTooltips(TCItems.GREEN_HORSESHOE_BALLOON, "绿马掌气球",
                new String[]{
                        "Allows the holder to double jump",
                        "Increases jump height",
                        "Negates fall damage"
                },
                new String[]{
                        "可让持有者二段跳",
                        "增加跳跃高度",
                        "消除掉落伤害"
                }
        );
        tooltipAndInfo(TCItems.HAND_DRILL, "手钻", "Immunity to Mining Fatigue", "对挖掘疲劳免疫", "It has a Chance to be dropped from Elder Guardian.", "它有几率从远古守卫者身上掉落。");
        onlyTooltips(TCItems.HAND_OF_CREATION, "创造之手",
                new String[]{
                        "Decreased 'Right Click Delay' by 3, cannot stack the decrease of its material",
                        "Press ↑ key to stand higher, and press Crouch key to down"

                },
                new String[]{
                        "右键点击延迟降低3，且降低效果不能与其材料的降低效果叠加",
                        "按↑键站得更高，按潜行键下来"
                }
        );
        onlyTooltip(TCItems.HAND_WARMER, "暖手宝", "Provides immunity to powder snow", "对细雪免疫");
        tooltipAndInfo(TCItems.HERMES_BOOTS, "赫尔墨斯靴", "The wearer can run super fast", "穿戴者可飞速奔跑", "It can be found in Chests in the Village Armorer.", "它可以在村庄盔甲匠的宝箱中找到。");
        onlyTooltip(TCItems.HERO_SHIELD, "英雄护盾", "Enemies are more likely to target you", "多人模式时，敌人更可能以你为目标");
        tooltipAndInfo(TCItems.HIVE_PACK, "蜂巢背包", "Increases the strength of friendly bees", "增加友好蜜蜂的力量", "It has a Chance to be dropped from Bee.", "它有几率从蜜蜂身上掉落。");
        tooltipAndInfo(TCItems.HOLY_WATER, "圣水", "Immunity to Wither", "对凋零免疫", "It has a Chance to be dropped from Wither Skeleton.", "它有几率从凋灵骷髅身上掉落。");
        onlyTooltips(TCItems.HONEY_BALLOON, "蜂蜜气球",
                new String[]{
                        "Releases bees and douses the user in honey when damaged",
                        "Increases jump height"
                },
                new String[]{
                        "受到伤害后释放蜜蜂并将使用者浸入蜂蜜中",
                        "增加跳跃高度"
                }
        );
        tooltipAndInfo(TCItems.HONEY_COMB, "蜂窝", "Releases bees and douses the user in honey when damaged", "受到伤害后释放蜜蜂并将使用者浸入蜂蜜中", "It has a Chance to be dropped from Bee.", "它有几率从蜜蜂身上掉落。");
        onlyTooltip(TCItems.ICE_SKATES, "溜冰鞋", "Provides extra mobility on ice", "提供额外冰面行动力");
        tooltipAndInfo(TCItems.LAVA_CHARM, "熔岩护身符", "Provides 7 seconds of immunity to lava", "对熔岩免疫7秒", "It has a Chance to be dropped from Blaze.", "它有几率从烈焰人掉落");
        onlyTooltips(TCItems.LAVA_WADERS, "熔岩靴",
                new String[]{
                        "Allows flight, super fast running, and extra mobility on ice",
                        "Grants immunity to fire blocks and 7 seconds of immunity to lava"
                },
                new String[]{
                        "提供在水、蜂蜜、熔岩上行走的能力",
                        "对火块免疫、对熔岩免疫7秒"
                }
        );
        tooltipAndInfo(TCItems.LIFE_FORM_ANALYZER, "生命体分析机", "Displays the name of rare creatures around you", "显示你周围稀有生物的名称", "The Lifeform Analyzer has a Chance to be dropped from Glow Squid.", "它有几率从发光鱿鱼身上掉落。");
        onlyTooltip(TCItems.LIGHTNING_BOOTS, "闪电靴", "Allows flight, super fast running", "可飞行、可飞速奔跑");
        tooltipsAndInfo(TCItems.LUCKY_HORSESHOE, "幸运马掌",
                new String[]{
                        "'Said to bring good fortune and keep evil spirits at bay'",
                        "Negates fall damage"

                },
                new String[]{
                        "“据说能带来好运、驱除邪灵”",
                        "消除掉落伤害"

                },
                "It can be discovered in chests found within Desert Pyramids, End City, Jungle Pyramids, or Ancient Cities.", "它可以在沙漠神殿，末地城，丛林遗迹或远古城市市的箱子中找到。");
        onlyTooltip(TCItems.MAGIC_MIRROR, "魔镜", "Gaze in the mirror to return home", "盯着镜子便可回家");
        tooltipAndInfo(TCItems.MAGIC_QUIVER, "魔法箭袋", "20% chance to not consume arrows", "20%几率不消耗箭", "This item has a Chance to be dropped from Skeleton.", "这件物品有几率从骷髅身上掉落。");
        tooltipsAndInfo(TCItems.MAGILUMINESCENCE, "魔光护符",
                new String[]{
                        "Increases movement speed and acceleration",
                        "'A brief light in my dark life.'"
                },
                new String[]{
                        "提高移动速度和加速度",
                        "“我黑暗生命中的一道短暂曙光”"
                },
                "It can be found in End City Chests.",
                "它可以在末地城的宝箱中找到。"
        );
        onlyTooltips(TCItems.MAGMA_SKULL, "岩浆骷髅头",
                new String[]{
                        "Provides 7 seconds of immunity to lava",
                        "Grants immunity to fire blocks"
                },
                new String[]{
                        "对熔岩免疫7秒",
                        "对火块免疫"
                }
        );
        tooltipAndInfo(TCItems.MAGMA_STONE, "岩浆石", "Melee attacks inflict fire damage", "近战攻击造成火焰伤害", "It has a Chance to be dropped from Blaze.", "它有几率从烈焰人掉落。");
        onlyTooltips(TCItems.MASTER_NINJA_GEAR, "忍者大师装备",
                new String[]{
                        "Allows the ability to climb walls, hold Crouch key to slide down",
                        "Allows the ability to dash while double tap a direction",
                        "Gives a chance to dodge attacks"
                },
                new String[]{
                        "可爬墙，按住潜行键可快速下滑",
                        "双击一个方向可猛冲",
                        "有几率避开攻击"
                }
        );
        onlyTooltip(TCItems.MECHANICAL_GLOVE, "机械手套", "Enables auto swing for melee weapons", "启用近战武器自动挥动");
        tooltipAndInfo(TCItems.METAL_DETECTOR, "金属探测器", "Displays the most valuable ore around you", "显示你周围最贵重的矿石", "The Metal Detector has a Chance to be dropped from Warden.", "它必定从监守者身上掉落。");
        add("tooltip.item.terra_curio.metal_detector.keybinding", "Press [%s] to detect", "按[%s]以探测");
        onlyTooltips(TCItems.MOLTEN_CHARM, "熔火护身符",
                new String[]{
                        "Grants immunity to fire blocks",
                        "Provides 7 seconds of immunity to lava"
                },
                new String[]{
                        "对火块免疫",
                        "对熔岩免疫7秒"
                });
        onlyTooltips(TCItems.MOLTEN_QUIVER, "熔火箭袋",
                new String[]{
                        "20% chance to not consume arrows",
                        "Lights wooden arrows ablaze",
                        "'Quiver in fear!'"
                },
                new String[]{
                        "20%几率不消耗箭",
                        "点燃木箭，火光熊熊",
                        "“在恐惧中颤抖吧！”"
                }
        );
        onlyTooltips(TCItems.MOLTEN_SKULL_ROSE, "熔火骷髅头玫瑰",
                new String[]{
                        "Grants immunity to fire blocks",
                        "Provides 7 seconds of immunity to lava",
                        "Reduces damage from touching lava"
                },
                new String[]{
                        "对火块免疫",
                        "对熔岩免疫7秒",
                        "减少因触碰熔岩而造成的伤害"
                });
        tooltipAndInfo(TCItems.MOON_STONE, "月亮石", "It has a Chance to be dropped from Phantom.", "在夜晚时略微增强属性值", "It has a Chance to be dropped from Phantom.", "它有几率从幻翼身上掉落。");
        onlyTooltip(TCItems.NUTRIENT_SOLUTION, "营养液", "Immunity to Weakness and Hunger", "对虚弱和饥饿免疫");
        onlyTooltips(TCItems.OBSIDIAN_HORSESHOE, "黑曜石马掌",
                new String[]{
                        "Negates fall damage",
                        "Grants immunity to fire blocks"
                },
                new String[]{
                        "消除掉落伤害",
                        "对火块免疫"
                }
        );
        tooltipAndInfo(TCItems.OBSIDIAN_ROSE, "黑曜石玫瑰", "Reduces damage from touching lava", "减少因触碰熔岩而造成的伤害", "It can be discovered in chests found within Bastion Remnant.", "它可以在堡垒遗迹中发现的箱子中找到。");
        onlyTooltip(TCItems.OBSIDIAN_SHIELD, "黑曜石护盾", "Grants immunity to fire blocks", "对火块免疫");
        onlyTooltip(TCItems.OBSIDIAN_SKULL, "黑曜石骷髅头", "Grants immunity to fire blocks", "对火块免疫");
        onlyTooltips(TCItems.OBSIDIAN_SKULL_ROSE, "黑曜石骷髅头玫瑰",
                new String[]{
                        "Grants immunity to fire blocks",
                        "Reduces damage from touching lava"
                },
                new String[]{
                        "对火块免疫",
                        "减少因触碰熔岩而造成的伤害"
                });
        onlyTooltips(TCItems.OBSIDIAN_WATER_WALKING_BOOTS, "黑曜石水上漂靴",
                new String[]{
                        "Provides the ability to walk on water & honey",
                        "Grants immunity to fire blocks"
                },
                new String[]{
                        "提供在水和蜂蜜上行走的能力",
                        "对火块免疫"
                }
        );
        tooltipAndInfo(TCItems.PALADINS_SHIELD, "圣骑士护盾", "Absorbs 25% of damage done to players on your team when above 25% life", "当生命值高于25%时，吸收团队中其他玩家所受伤害的25%", "It is dropped by the Ender Dragon.", "它必定从末影龙身上掉落。");
        tooltipAndInfo(TCItems.PANIC_NECKLACE, "恐慌项链", "Increases movement speed after taking damage", "受到伤害后增加移动速度", "It can be found in Chests in the Dungeon.", "它可以在地牢中的宝箱中找到。");
        onlyTooltips(TCItems.PDA, "个人数字助手", ALL_INFO_EN, ALL_INFO_ZH);
        onlyTooltips(TCItems.PINK_HORSESHOE_BALLOON, "粉马掌气球",
                new String[]{
                        "Allows the holder to double jump",
                        "Increases jump height",
                        "Negates fall damage"
                },
                new String[]{
                        "可让持有者二段跳",
                        "增加跳跃高度",
                        "消除掉落伤害"
                }
        );
        onlyTooltips(TCItems.YELLOW_HORSESHOE_BALLOON, "黄马掌气球",
                new String[]{
                        "Allows the holder to double jump",
                        "Increases jump height",
                        "Negates fall damage"
                },
                new String[]{
                        "可让持有者二段跳",
                        "增加跳跃高度",
                        "消除掉落伤害"
                }
        );
        onlyTooltip(TCItems.PLATINUM_WATCH, "铂金表", "Tell the time", "报时");
        tooltipAndInfo(TCItems.PORTABLE_CEMENT_MIXER, "便携式水泥搅拌机", "Decreased 'Right Click Delay' by 1", "右键点击延迟降低1", "This item can be located in the chests found within Toolsmith Villagers' houses.", "这件物品可以在工具匠村民家中的箱子中找到。");
        onlyTooltip(TCItems.POWER_GLOVE, "强力手套", "Enables auto swing for melee weapons", "启用近战武器自动挥动");
        tooltipAndInfo(TCItems.PUTRID_SCENT, "腐香囊", "Enemies are less likely to target you", "多人模式下，敌怪不太可能以你为目标", "It can be found in Chests in the Dungeon.", "它可以在地牢中的宝箱中找到。");
        tooltipAndInfo(TCItems.RADAR, "雷达", "Detects enemies around you", "探测你周围的敌人", "The Radar has a Chance to be dropped from Bats.", "它有几率从蝙蝠身上掉落。");
        onlyInfo(TCItems.RANGER_EMBLEM, "游侠徽章", "It has a Chance to be dropped from Skeleton.", "它有几率从骷髅身上掉落。");
        onlyTooltips(TCItems.RECON_SCOPE, "侦察镜",
                new String[]{
                        "Increases view range for ranged weapons",
                        "Hold ranged weapon and crouch to zoom out",
                        "Enemies are less likely to target you",
                        "'Enemy spotted'"

                },
                new String[]{
                        "扩大远程武器的视野范围",
                        "手持远程武器并潜行可拉远视野",
                        "多人模式下，敌怪不太可能以你为目标",
                        "“发现敌人”"
                });
        onlyTooltips(TCItems.REK_3000, "R.E.K.3000",
                new String[]{
                        "Detects enemies around you",
                        "Displays the name of rare creatures around you",
                        "Displays how many monsters have been killed"
                },
                new String[]{
                        "探测你周围的敌人",
                        "显示你周围稀有生物的名称",
                        "显示怪物击杀数量"
                });
        onlyTooltips(TCItems.SNIPER_SCOPE, "狙击镜",
                new String[]{
                        "Increases view range for ranged weapons",
                        "Hold ranged weapon and crouch to zoom out"
                },
                new String[]{
                        "扩大远程武器的视野范围",
                        "手持远程武器并潜行可拉远视野"
                });
        tooltipsAndInfo(TCItems.RIFLE_SCOPE, "步枪瞄准镜",
                new String[]{
                        "Increases view range for ranged weapons",
                        "Hold ranged weapon and crouch to zoom out"
                },
                new String[]{
                        "扩大远程武器的视野范围",
                        "手持远程武器并潜行可拉远视野"
                },
                "It has a Chance to be dropped from Enderman.",
                "它有几率从末影人身上掉落。"
        );
        onlyTooltip(TCItems.ROCKET_BOOTS, "火箭靴", "Allows flight", "可飞行");
        tooltipAndInfo(TCItems.ROYAL_GEL, "皇家凝胶", "Slimes become friendly", "史莱姆将变成友好生物", "It has a Chance to be dropped from Slime.", "有几率从史莱姆身上掉落。");
        tooltipAndInfo(TCItems.SAILFISH_BOOTS, "旗鱼靴", "The wearer can run super fast", "穿戴者可飞速奔跑", "It can be found in Shipwreck Chests.", "它可以在沉船宝箱中找到。");
        onlyTooltips(TCItems.SANDSTORM_IN_A_BALLOON, "沙暴气球",
                new String[]{
                        "Allows the holder to double jump",
                        "Increases jump height"
                },
                new String[]{
                        "可让持有者二段跳",
                        "增加跳跃高度"
                }
        );
        tooltipAndInfo(TCItems.SANDSTORM_IN_A_BOTTLE, "沙暴瓶", "Allows the holder to double jump", "可让持有者二段跳", "It can be found in Chests in the Desert Pyramid.", "它可以在沙漠神殿的宝箱中找到。");
        onlyTooltip(TCItems.SEARCHLIGHT, "探照灯", "Immunity to Blindness and Darkness", "对失明和黑暗免疫");
        tooltipAndInfo(TCItems.SEXTANT, "六分仪", "Displays the phase of the moon", "显示月相", "This item can be located in the chests found within Fisherman Villagers' houses.", "这件物品可以在渔夫村民家中的箱子中找到。");
        onlyInfo(TCItems.SHACKLE, "镣铐", "It can be found in Chests in the Dungeon.", "它可以在地牢中的宝箱中找到。");
        /*tooltipsAndInfo*/onlyTooltips(TCItems.RAM_RUNE, "牧羊符文",
                new String[]{
                        "Jump while holding crouch to slam downward",
                        "Slamming into the ground will deal damage to nearby enemies"
                },
                new String[]{
                        "按住潜行键跳跃即可向下猛击",
                        "猛击地面会对附近的敌人造成伤害"
                }/*,
                "It can be found in Chests in the Dungeon.",
                "它可以在地牢中的宝箱中找到。"*/
        );
        onlyInfo(TCItems.SHARK_TOOTH_NECKLACE, "鲨牙项链", "It has a chance to drop from Drowned.", "它有几率从溺尸身上掉落。");
        onlyTooltips(TCItems.SHARKRON_BALLOON, "鲨鱼龙气球",
                new String[]{
                        "Allows the holder to double jump",
                        "Increases jump height"
                },
                new String[]{
                        "可让持有者二段跳",
                        "增加跳跃高度"
                }
        );
        tooltipAndInfo(TCItems.SHIELD_OF_CTHULHU, "克苏鲁护盾", "Allows the player to dash into the enemy, tap [%s] once to dash", "允许冲刺，按一次[%s]以冲刺", "It has a Chance to be dropped from Ravager.", "它有几率从劫掠兽身上掉落。");
        tooltipAndInfo(TCItems.SHINY_RED_BALLOON, "闪亮红气球", "Increases jump height", "增加跳跃高度", "When the player has the Hero of the Village effect, the librarian has a chance to give the player this item.It can be found in chests in plains villages.", "它可以在平原村庄中的宝箱中找到。当玩家拥有村庄英雄效果时，图书管理员有几率将此物品交给玩家。");
        tooltipsAndInfo(TCItems.SHOE_SPIKES, "鞋钉",
                new String[]{
                        "Allows the ability to slide down walls, hold Crouch key to slide down quickly",
                        "Improved ability if combined with Climbing Claws"
                }, new String[]{
                        "可沿墙滑下，按住潜行键可更快地下滑",
                        "结合攀爬爪使用时能力还会有所提升"
                }, "It has a Chance to be dropped from Cave Spider.", "它有几率从洞穴蜘蛛身上掉落。");
        tooltipAndInfo(TCItems.SHOT_PUT, "铅球", "Immunity to Levitation", "对漂浮免疫", "It can be found in Chests in the Stronghold.", "它可以在要塞的宝箱中找到。");
        onlyTooltip(TCItems.SILVER_WATCH, "银表", "Tell the time", "报时");

        onlyInfo(TCItems.SORCERER_EMBLEM, "巫师徽章", "It has a Chance to be dropped from Evoker.", "它有几率从唤魔者身上掉落。");
        onlyTooltip(TCItems.SPECTRE_BOOTS, "幽灵靴", "Allows flight, super fast running", "可飞行、可飞速奔跑");
        onlyTooltips(TCItems.STALKERS_QUIVER, "潜行者箭袋",
                new String[]{
                        "20% chance to not consume arrows",
                        "Enemies are less likely to target you"
                },
                new String[]{
                        "20%几率不消耗箭",
                        "多人模式下，敌怪不太可能以你为目标"
                }
        );
        tooltipAndInfo(TCItems.STAR_CLOAK, "星星斗篷", "Causes stars to fall after taking damage", "受到伤害后会使星星坠落", "It can be discovered in chests found within Woodland Mansion.", "它可以在林地府邸内的箱子中找到。");
        onlyTooltips(TCItems.STAR_VEIL, "星星面纱",
                new String[]{
                        "Increases length of invincibility after taking damage",
                        "Causes stars to fall after taking damage"
                },
                new String[]{
                        "增加受到伤害后的无敌时间",
                        "受到伤害后会使星星坠落"
                });
        tooltipsAndInfo(TCItems.STEP_STOOL, "梯凳",
                new String[]{
                        "Press [%s] to stand higher, and press Crouch key to down",
                        "Extra Step: %s"
                },
                new String[]{
                        "按[%s]站得更高，按潜行键下来",
                        "额外高度：%s"
                },
                "You can use Step Stool to upgrade its Extra Step from Smithing Table.",
                "你可以在锻造台使用梯凳升级它的额外高度。"
        );
        onlyTooltip(TCItems.STINGER_NECKLACE, "毒刺项链", "Releases bees and douses the user in honey when damaged.", "受到伤害后释放蜜蜂并将使用者浸入蜂蜜中。");
        onlyTooltip(TCItems.STOPWATCH, "秒表", "Displays how fast the player is moving", "显示玩家的移动速度");
        tooltipAndInfo(TCItems.SUN_STONE, "太阳石", "During daytime, grants minor increase.", "在白天时略微增强属性值", "It has a Chance to be dropped from Blaze.", "它有几率由烈焰人掉落。");
        onlyTooltip(TCItems.SWEETHEART_NECKLACE, "甜心项链", "When damaged, the bee is released and the user is immersed in honey and increases movement speed.", "受到伤害后释放蜜蜂并将使用者浸入蜂蜜中、并提高移动速度。");
        tooltipAndInfo(TCItems.TABI, "分趾厚底袜", "Allows the ability to dash while double tap a direction", "双击一个方向可猛冲", "It can be found in Chests in the Nether Bridge.", "它可以在下界要塞的宝箱中找到。");
        tooltipAndInfo(TCItems.TALLY_COUNTER, "杀怪计数器", "Displays how many monsters have been killed", "显示怪物击杀数量", "The Tally Counter a Chance to be dropped from Creeper.", "它有几率从苦力怕身上掉落。");
        onlyTooltips(TCItems.TERRASPARK_BOOTS, "泰拉闪耀靴",
                new String[]{
                        "Allows flight, super fast running, and extra mobility on ice",
                        "Provides the ability to walk on water, honey & lava",
                        "Grants immunity to fire blocks and 7 seconds of immunity to lava",
                        "Reduces damage from touching lava"
                },
                new String[]{
                        "可飞行、飞速奔跑、并提供额外冰面行动力",
                        "提供在水、蜂蜜、熔岩上行走的能力",
                        "对火块免疫并在7秒内对熔岩免疫",
                        "减少因触碰熔岩而造成的伤害"
                }
        );
        onlyTooltip(TCItems.THE_PLAN, "计划书", "Immunity to Slowness and Nausea", "对缓慢和反胃免疫");
        onlyInfo(TCItems.TITAN_GLOVE, "泰坦手套", "It can be found in Chests in the Mineshaft.", "它可以在废弃矿井的宝箱中找到。");
        onlyInfo(TCItems.TOOLBELT, "工具腰带", "It can be located in the chests found within Toolsmith Villagers' houses.", "它可以在工具匠村民家中的箱子中找到。");
        onlyInfo(TCItems.TOOLBOX, "工具箱", "It can be located in the chests found within Toolsmith Villagers' houses.", "它可以在工具匠村民家中的箱子中找到。");
        tooltipAndInfo(TCItems.TREASURE_MAGNET, "宝藏磁石", "Expanded item pickup", "扩大物品拾取范围", "It can be discovered in chests found within Desert Pyramids, End City, Jungle Pyramids, or Ancient Cities.", "它可以在沙漠神殿、末地城、丛林神庙或远古城市内的宝箱中找到。");
        tooltipAndInfo(TCItems.TRIFOLD_MAP, "三折地图", "Immunity to Nausea", "对反胃免疫", "It can be found in Chests in the Stronghold Library.", "它可以在要塞图书馆的宝箱中找到。");
        tooltipAndInfo(TCItems.TSUNAMI_IN_A_BOTTLE, "海啸瓶", "Allows the holder to double jump", "可让持有者二段跳", "It can be found in Shipwreck Chests.", "它可以在沉船宝箱中找到。");
        tooltipAndInfo(TCItems.VITAMINS, "维生素", "Immunity to Weakness", "对虚弱免疫", "It has a Chance to be dropped from Witch.", "它有几率从女巫身上掉落。");
        onlyInfo(TCItems.WARRIOR_EMBLEM, "战士徽章", "It has a Chance to be dropped from Vindicator.", "它有几率从卫道士身上掉落。");
        tooltipAndInfo(TCItems.WATER_WALKING_BOOTS, "水上漂靴", "Provides the ability to walk on water & honey", "提供在水和蜂蜜上行走的能力", "It can be found in Ocean Ruins Chests.", "它可以在海洋废墟宝箱中找到。");
        tooltipAndInfo(TCItems.WEATHER_RADIO, "天气收音机", "Displays the weather", "显示天气", "This item can be located in the chests found within Fisherman Villagers' houses.", "这件物品可以在渔夫村民家中的箱子中找到。");
        tooltipAndInfo(TCItems.WORM_SCARF, "蠕虫围巾", "Reduces damage taken by 17%", "所受伤害减少17%", "When a player has a tamed cat, the cat may give the player this item when they wake up.", "当玩家有一只被驯服的猫时，猫可能会在玩家醒来时给玩家这个物品。");
        tooltipAndInfo(TCItems.BONE_GLOVE, "骨头手套", "Shoots crossbones at enemies while you are attacking", "在你攻击时向敌人射出交叉骨", "It has a Chance to be dropped from Skeleton Horse.", "它有几率从骷髅马身上掉落。");
        tooltipAndInfos(TCItems.DIVING_HELMET, "潜水头盔",
                "Greatly extends underwater breathing",
                "大大延长水下呼吸时间",
                new String[]{
                        "When equipped, it causes the breath meter to deplete much slower and slows the rate at which drowning damage is taken.",
                        "When the drowned wears it, you can take it away."
                },
                new String[]{
                        "当装备时，它会让呼吸计以慢得多的速度消耗，并且还能减慢溺水伤害的速度。",
                        "当溺尸穿戴它时，你可以夺走它。"
                });
        onlyTooltip(TCItems.DIVING_GEAR, "潜水装备", "Greatly extends underwater breathing", "大大延长水下呼吸时间");
        tooltipAndInfo(TCItems.JELLYFISH_NECKLACE, "水母项链", "Generates a very subtle glow which becomes more vibrant underwater", "发出非常微弱的光芒，这种光芒在水下会变得更醒目", "It has a Chance to be dropped from Glow Squid.", "它有几率从发光鱿鱼身上掉落。");
        onlyTooltips(TCItems.JELLYFISH_DIVING_GEAR, "水母潜水装备",
                new String[]{
                        "Greatly extends underwater breathing",
                        "Generates a very subtle glow which becomes more vibrant underwater"
                },
                new String[]{
                        "大大延长水下呼吸时间",
                        "发出非常微弱的光芒，这种光芒在水下会变得更醒目"
                });
        tooltipsAndInfo(TCItems.ARCTIC_DIVING_GEAR, "北极潜水装备",
                new String[]{
                        "Greatly extends underwater breathing",
                        "Provides extra mobility on ice",
                        "Generates a very subtle glow which becomes more vibrant underwater"
                },
                new String[]{
                        "大大延长水下呼吸时间",
                        "提供额外冰面行动力",
                        "发出非常微弱的光芒，这种光芒在水下会变得更醒目"
                },
                "Provides immunity to powder snow",
                "对细雪免疫");
        tooltipAndInfo(TCItems.INNER_TUBE, "浮游圈", "Grants the ability to float in water", "可让人浮在水面", "It has a Chance to be dropped from Slime.", "它有几率从史莱姆身上掉落。");
        tooltipAndInfo(TCItems.FLYING_CARPET, "飞毯", "Allows the owner to float for a few seconds", "可让持有者漂浮几秒钟", "It can be discovered in chests found within Desert Pyramids.", "它可以在沙漠神殿内的箱子中找到。");
        tooltipAndInfo(TCItems.SHINY_STONE, "闪亮石", "Greatly increases life regen when not moving", "不移动时，大大提高生命再生速度", "When an Iron Golem is killed by an explosion", "铁傀儡被爆炸击杀时。");
        tooltipAndInfo(TCItems.SOARING_INSIGNIA, "翱翔徽章", "Grants infinite wing and rocket boot flight", "给予无限翅膀飞行时间和无限火箭靴飞行时间", "Kill Wither with elytra", "使用鞘翅的情况下击杀凋灵。");
        tooltipsAndInfo(TCItems.CELESTIAL_STARBOARD, "天界星盘",
                new String[]{
                        "Allows flight and slow fall",
                        "Hold Crouch and Jump key to horizontal flight",
                        "'The more you know'"
                },
                new String[]{
                        "可飞行和缓慢坠落",
                        "按住潜行与跳跃键以水平飞行",
                        "“你懂得越多”"
                },
                "Kill the Ender Dragon with elytra",
                "使用鞘翅的情况下击杀末影龙。"
        );
        tooltipAndInfo(TCItems.NEPTUNES_SHELL, "海神贝壳", "Transforms the holder into merfolk when entering water", "入水时将持有者变成人鱼", "It has a Chance to be dropped from Guardian.", "它有几率从守卫者身上掉落。");
        tooltipAndInfo(TCItems.MOON_CHARM, "月光护身符", "Turns the holder into a werewolf at night", "在晚上将持有者变成狼人", "When zombies are killed by wolves.", "僵尸被狼击杀时。");
        onlyTooltip(TCItems.MOON_SHELL, "月亮贝壳", "Turns the holder into a werewolf at night and a merfolk when entering water", "在晚上将持有者变成狼人，入水时将持有者变成人鱼");
        onlyTooltip(TCItems.CELESTIAL_SHELL, "天界壳", "Turns the holder into a werewolf at night and a merfolk when entering water", "在晚上将持有者变成狼人，入水时将持有者变成人鱼");

        add(TCItems.FLEDGLING_WINGS.get(), "雏翼");
        add(TCItems.ANGEL_WINGS.get(), "天使之翼");
        add(TCItems.DEMON_WINGS.get(), "恶魔之翼");
        add(TCItems.FAIRY_WINGS.get(), "仙灵之翼");
        add(TCItems.FIN_WINGS.get(), "鳍翼");
        add(TCItems.FROZEN_WINGS.get(), "冰冻之翼");
        add(TCItems.HARPY_WINGS.get(), "女妖之翼");
        add(TCItems.JETPACK.get(), "喷气背包");
        add(TCItems.LEAF_WINGS.get(), "叶之翼");
        add(TCItems.BAT_WINGS.get(), "蝙蝠之翼");
        add(TCItems.BEE_WINGS.get(), "蜜蜂之翼");
        add(TCItems.BUTTERFLY_WINGS.get(), "蝴蝶之翼");
        add(TCItems.FLAME_WINGS.get(), "烈焰之翼");
        add(TCItems.HOVERBOARD.get(), "悬浮板");
        add(TCItems.BONE_WINGS.get(), "骨之翼");
        add(TCItems.MOTHRON_WINGS.get(), "蛾怪之翼");
        add(TCItems.SPECTRE_WINGS.get(), "幽灵之翼");
        add(TCItems.BEETLE_WINGS.get(), "甲虫之翼");
        add(TCItems.FESTIVE_WINGS.get(), "喜庆之翼");
        add(TCItems.SPOOKY_WINGS.get(), "阴森之翼");
        add(TCItems.TATTERED_WINGS.get(), "褴褛仙灵之翼");
        add(TCItems.STEAMPUNK_WINGS.get(), "蒸汽朋克之翼");
        add(TCItems.BETSYS_WINGS.get(), "双足翼龙之翼");
        add(TCItems.EMPRESS_WINGS.get(), "女皇之翼");
        add(TCItems.FISHRON_WINGS.get(), "猪鱼龙之翼");
        add(TCItems.NEBULA_WINGS.get(), "星云斗篷");
        add(TCItems.VORTEX_BOOSTER.get(), "星旋强化翼");
        add(TCItems.SOLAR_WINGS.get(), "日耀之翼");
        add(TCItems.STARDUST_WINGS.get(), "星尘之翼");

        if (isEn) {
            add(TCItems.EXPLORERS_EQUIPMENT.get().getDescriptionId(), "Explorer's Equipment");
            add(TCItems.PALADINS_SHIELD.get().getDescriptionId(), "Paladin's Shield");
            add(TCItems.STALKERS_QUIVER.get().getDescriptionId(), "Stalker's Quiver");
            add(TCItems.DPS_METER.get().getDescriptionId(), "DPS Meter");
            add(TCItems.FISHERMANS_POCKET_GUIDE.get().getDescriptionId(), "Fisherman's Pocket Guide");
            add(TCItems.GPS.get().getDescriptionId(), "GPS");
            add(TCItems.PDA.get().getDescriptionId(), "PDA");
            add(TCItems.REK_3000.get().getDescriptionId(), "R.E.K.3000");
            add(TCItems.NEPTUNES_SHELL.get().getDescriptionId(), "Neptune's Shell");
            add(TCItems.DIVING_HELMET.get().getDescriptionId(), "Diving Helmet");
            Iterables.concat(TCItems.CURIOS.getEntries(), TCItems.OTHERS.getEntries(), TCItems.WINGS.getEntries()).forEach(holder -> {
                assert holder != null;
                add(holder.get().getDescriptionId(), LibUtils.toTitleCase(holder.getId().getPath()));
            });
            TCEntities.ENTITIES.getEntries().forEach(entity -> add(entity.get().getDescriptionId(), LibUtils.toTitleCase(entity.getId().getPath())));
        } else {
            add(TCEntities.BEE_PROJECTILE.get().getDescriptionId(), "蜜蜂射弹");
            add(TCEntities.STAR_CLOAK.get().getDescriptionId(), "星星斗篷");
            add(TCEntities.STEP_STOOL.get().getDescriptionId(), "梯凳");
        }
        add(TCBlocks.WORKSHOP.get().getDescriptionId(), "Workshop", "工匠作坊");

        add("terra_curio.configuration.Speed Shoes", "Speed Shoes", "加速靴");
        add("terra_curio.configuration.rareBlocks", "Rare Blocks", "稀有方块");
        add("terra_curio.configuration.attributeReplacements", "Attribute Replacements", "属性替换");
        add("terra_curio.configuration.enable", "Enable", "启用");
        add("terra_curio.configuration.Random Attack Damage", "Random Attack Damage", "随机攻击伤害");
        add("terra_curio.configuration.Max Accessory Amount", "Max Accessory Amount", "最大配饰数量");
        add("terra_curio.configuration.min", "Min damage multiplier", "最低伤害乘算");
        add("terra_curio.configuration.rareCreatures", "Rare Creatures", "稀有生物");
        add("terra_curio.configuration.Information HUD", "Information HUD", "信息HUD");
        add("terra_curio.configuration.max", "Max damage multiplier", "最高伤害乘算");
        add("terra_curio.configuration.Functional", "Functional", "功能性");
        add("terra_curio.configuration.rareBlocks.button", "Configurations for Metal Detector", "金属探测器的配置");
        add("terra_curio.configuration.Speed Shoes.tooltip", "Configurations for Speed Shoes", "加速类靴子的配置");
        add("terra_curio.configuration.Functional.tooltip", "Configurations for functional accessories", "功能类配饰的配置");
        add("terra_curio.configuration.attributeReplacements.button", "Allows you to replace some attributes", "允许部分属性的替换");
        add("terra_curio.configuration.shoesExtraStepHeight", "Shoes Extra Step Height", "靴类额外最大行走高度");
        add("terra_curio.configuration.shoesExtraStepHeight.button", "When enabled, players will be able to walk over blocks half a meter higher.", "开启时玩家将能多走上半米高度");
        add("terra_curio.configuration.top", "Relative to the top of the screen as a percentage of distance", "相对屏幕顶部距离百分比");
        add("terra_curio.configuration.isLeft", "Whether it is placed on the left", "是否置于左边");
        add("terra_curio.configuration.rightClickDelay", "Right Click Delay Decrease", "右击延迟降低");
        add("terra_curio.configuration.rightClickDelay.tooltip", "Configurations for construction accessories", "建筑类配饰的配置");
        add("terra_curio.configuration.speedUp", "Enable speed up", "启用加速");
        add("terra_curio.configuration.playSound", "Play sound", "播放音效");
        add("terra_curio.configuration.showParticle", "Show Particle", "显示粒子");
        add("terra_curio.configuration.shoesSoundVolume", "Shoes Sound Volume", "靴子音效音量");
        add("terra_curio.configuration.autoAttack", "Auto Attack", "自动攻击");
        add("terra_curio.configuration.autoAttack.tooltip", "Auto Attack", "启用近战武器自动挥动");
        add("terra_curio.configuration.displayInfoTooltip", "Display the green tooltip", "显示绿色的工具提示");
    }

    private void tooltipsAndInfo(Supplier<? extends Item> item, String zhName, String[] enTooltip, String[] zhTooltip, String enInfo, String zhInfo) {
        String key = item.get().getDescriptionId();
        if (!isEn) add(key, zhName);
        addTooltips(key, enTooltip, zhTooltip);
        add("info.tooltip." + key + ".0", enInfo, zhInfo);
    }

    private void tooltipAndInfo(Supplier<? extends Item> item, String zhName, String enTooltip, String zhTooltip, String enInfo, String zhInfo) {
        String key = item.get().getDescriptionId();
        if (!isEn) add(key, zhName);
        add("tooltip." + key + ".0", enTooltip, zhTooltip);
        add("info.tooltip." + key + ".0", enInfo, zhInfo);
    }

    private void tooltipAndInfos(Supplier<? extends Item> item, String zhName, String enTooltip, String zhTooltip, String[] enInfo, String[] zhInfo) {
        String key = item.get().getDescriptionId();
        if (!isEn) add(key, zhName);
        add("tooltip." + key + ".0", enTooltip, zhTooltip);
        addInfos(key, enInfo, zhInfo);
    }

    private void onlyTooltips(Supplier<? extends Item> item, String zhName, String[] enTooltip, String[] zhTooltip) {
        String key = item.get().getDescriptionId();
        if (!isEn) add(key, zhName);
        addTooltips(key, enTooltip, zhTooltip);
    }

    private void onlyZhName(Supplier<? extends Item> item, String zhName) {
        if (!isEn) add(item.get().getDescriptionId(), zhName);
    }

    private void onlyTooltip(Supplier<? extends Item> item, String zhName, String enTooltip, String zhTooltip) {
        String key = item.get().getDescriptionId();
        if (!isEn) add(key, zhName);
        add("tooltip." + key + ".0", enTooltip, zhTooltip);
    }

    private void onlyInfo(Supplier<? extends Item> item, String zhName, String enInfo, String zhInfo) {
        String key = item.get().getDescriptionId();
        if (!isEn) add(key, zhName);
        add("info.tooltip." + key + ".0", enInfo, zhInfo);
    }

    private void addTooltip(Supplier<? extends Item> key, int index, String en, String zh) {
        add("tooltip." + key.get().getDescriptionId() + "." + index, en, zh);
    }

    private void addTooltips(String key, String[] enTooltip, String[] zhTooltip) {
        if (enTooltip.length == zhTooltip.length) {
            String tooltip = "tooltip." + key + ".";
            for (int i = 0; i < enTooltip.length; i++) {
                String enLang = enTooltip[i];
                String cnLang = zhTooltip[i];
                add(tooltip + i, enLang, cnLang);
            }
        }
    }

    private void addInfos(String key, String[] enInfo, String[] zhInfo) {
        if (enInfo.length == zhInfo.length) {
            String infoInfo = "info.tooltip." + key + ".";
            for (int i = 0; i < enInfo.length; i++) {
                String enLang = enInfo[i];
                String zhLang = zhInfo[i];
                add(infoInfo + i, enLang, zhLang);
            }
        }
    }

    private void add(String key, String en, String zh) {
        add(key, isEn ? en : zh);
    }

    @Override
    public void add(String key, String value) {
        try {
            super.add(key, value);
        } catch (Exception ignored) {}
    }
}
