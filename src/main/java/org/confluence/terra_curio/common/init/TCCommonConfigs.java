package org.confluence.terra_curio.common.init;

import it.unimi.dsi.fastutil.objects.Object2IntLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntSortedMap;
import net.minecraft.commands.arguments.blocks.BlockStateParser;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.confluence.terra_curio.TerraCurio;

import java.util.regex.*;
import java.util.List;
import java.util.ArrayList;

public final class TCCommonConfigs {
    private static ModConfigSpec.ConfigValue<List<? extends String>> RARE_BLOCKS;
    private static ModConfigSpec.ConfigValue<List<? extends String>> RARE_CREATURES;
    public static Object2IntSortedMap<BlockState> rareBlocks = new Object2IntLinkedOpenHashMap<>();
    public static Object2IntSortedMap<EntityType<?>> rareCreatures = new Object2IntLinkedOpenHashMap<>();

    public static ModConfigSpec.BooleanValue RANDOM_ATTACK_DAMAGE;
    public static ModConfigSpec.DoubleValue RANDOM_ATTACK_DAMAGE_MIN;
    public static ModConfigSpec.DoubleValue RANDOM_ATTACK_DAMAGE_MAX;

    public static ModConfigSpec.IntValue MAX_ACCESSORIES;

    public record CoordElement(double key, Pattern restriction, String value, double scaleX, double scaleY, double scaleZ, double offsetX, double offsetY, double offsetZ, int precisionX, int precisionY, int precisionZ) {}
    private static ModConfigSpec.ConfigValue<List<? extends String>> COORD_VALUES;
    public static CoordElement[] coordValues = {};

    public static ModConfigSpec.BooleanValue USE_12H_TIME;

    public static void onLoad() {
        Object2IntSortedMap<BlockState> blockStates = new Object2IntLinkedOpenHashMap<>();
        RARE_BLOCKS.get().forEach(s -> {
            try {
                blockStates.put(BlockStateParser.parseForBlock(BuiltInRegistries.BLOCK.asLookup(), s, false).blockState(), blockStates.size());
            } catch (Exception e) {
                TerraCurio.LOGGER.warn("BlockState {} not found", s);
            }
        });
        rareBlocks = blockStates;

        Object2IntSortedMap<EntityType<?>> entityTypes = new Object2IntLinkedOpenHashMap<>();
        RARE_CREATURES.get().forEach(s -> {
            ResourceLocation id = ResourceLocation.parse(s);
            BuiltInRegistries.ENTITY_TYPE.getOptional(id).ifPresentOrElse(
                    entityType -> entityTypes.put(entityType, entityTypes.size()),
                    () -> TerraCurio.LOGGER.warn("EntityType {} not found", id)
            );
        });
        rareCreatures = entityTypes;

        ArrayList<CoordElement> enumeratedCoords = new ArrayList<>();
        COORD_VALUES.get().forEach(s -> {
            try {
                String[] fields = s.split(";");
                if (fields.length != 12) {
                    TerraCurio.LOGGER.warn("Coord value '{}' is not in the correct format (use '<max_height=-64>;<dimension_restriction=.*>;<translation_key>;<x_scale=1>;<y_scale=1>;<z_scale=1>;<x_offset=0>;<y_offset=0>;<z_offset=0>;<x_precision=2>;<y_precision=2>;<z_precision=2>')", s);
                } else {
                    enumeratedCoords.add(new CoordElement(
                        Double.parseDouble(fields[0]),
                        Pattern.compile(fields[1]),
                        fields[2],
                        Double.parseDouble(fields[3]),
                        Double.parseDouble(fields[4]),
                        Double.parseDouble(fields[5]),
                        Double.parseDouble(fields[6]),
                        Double.parseDouble(fields[7]),
                        Double.parseDouble(fields[8]),
                        Integer.parseInt(fields[9]),
                        Integer.parseInt(fields[10]),
                        Integer.parseInt(fields[11])
                    ));
                }
            } catch (NumberFormatException e) {
                TerraCurio.LOGGER.warn("Could not parse value as a number {}", e.getMessage());
            } catch (PatternSyntaxException e) {
                TerraCurio.LOGGER.warn("Invalid syntax ({}) for regex '{}' at index {}", e.getDescription(), e.getPattern(), e.getIndex());
            }
        });
        coordValues = enumeratedCoords.toArray(coordValues);
    }

    public static void register(ModContainer container) {
        ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
        RARE_BLOCKS = BUILDER.comment(
                "In order for the block to be found by the Metal Detector",
                "You need to fill the list with string like 'modid:block[state1=true]' or 'modid:block'",
                "The higher the block in the list, the higher the value"
        ).defineListAllowEmpty("rareBlocks", List.of(
                "confluence:chlorophyte_ore",
                "confluence:deepslate_titanium_ore",
                "confluence:deepslate_adamantite_ore",
                "confluence:deepslate_orichalcum_ore",
                "confluence:deepslate_mythril_ore",
                "confluence:deepslate_palladium_ore",
                "confluence:deepslate_cobalt_ore",
                "confluence:life_crystal_block",
                "confluence:sword_in_stone",
                "confluence:dungeon_chest",
                "confluence:water_chest",
                "confluence:frozen_chest",
                "confluence:sandstone_chest",
                "confluence:golden_chest",
                "confluence:shadow_chest",
                "confluence:skyware_chest",
                "confluence:living_wood_chest",
                "confluence:opal_ore",
                "confluence:gelstone_ore",
                "confluence:cold_crystal_ore",
                "confluence:crimtane_ore",
                "confluence:deepslate_crimtane_ore",
                "confluence:demonite_ore",
                "confluence:deepslate_demonite_ore",
                "minecraft:ancient_debris",
                "minecraft:diamond_ore",
                "minecraft:deepslate_diamond_ore",
                "confluence:platinum_ore",
                "confluence:deepslate_platinum_ore",
                "minecraft:gold_ore",
                "minecraft:deepslate_gold_ore",
                "confluence:forest_pot",
                "confluence:tundra_pot",
                "confluence:spider_nest_pot",
                "confluence:underground_desert_pot",
                "confluence:jungle_pot",
                "confluence:marble_cave_pot",
                "confluence:pyramid_pot",
                "confluence:corruption_pot",
                "confluence:crimson_pot",
                "confluence:dungeon_pot",
                "confluence:underworld_pot",
                "confluence:lihzahrd_pot",
                "confluence:tungsten_ore",
                "confluence:deepslate_tungsten_ore",
                "confluence:silver_ore",
                "confluence:deepslate_silver_ore",
                "confluence:lead_ore",
                "confluence:deepslate_lead_ore",
                "minecraft:iron_ore",
                "minecraft:deepslate_iron_ore",
                "confluence:tin_ore",
                "confluence:deepslate_tin_ore",
                "minecraft:copper_ore",
                "minecraft:deepslate_copper_ore"
        ), () -> "minecraft:stone", o -> true);
        RARE_CREATURES = BUILDER.comment(
                "In order for the creature to be found by the Life Form Analyzer",
                "You need to fill the list with string like 'modid:entity'",
                "The higher the creature in the list, the higher the value"
        ).defineListAllowEmpty("rareCreatures", List.of(
                "terra_entity:jungle_mimic",
                "terra_entity:corrupt_mimic",
                "terra_entity:crimson_mimic",
                "terra_entity:hallowed_mimic",
                "terra_entity:golden_mimic",
                "terra_entity:ice_mimic",
                "terra_entity:shadow_mimic",
                "terra_entity:wooden_mimic",
                "terra_entity:voodoo_demon",
                "terra_entity:dungeon_slime",
                "terra_entity:nymph",
                "terra_entity:wandering_eye_fish",
                "terra_entity:golden_slime",
                "terra_entity:pink_slime",
                "minecraft:skeleton_horse",
                "minecraft:sniffer",
                "minecraft:allay",
                "minecraft:warden",
                "minecraft:mooshroom",
                "minecraft:panda"
        ), () -> "minecraft:pig", o -> true);
        RANDOM_ATTACK_DAMAGE = BUILDER.push("Random Attack Damage").define("enable", false);
        RANDOM_ATTACK_DAMAGE_MIN = BUILDER.defineInRange("min", 0.8, 0.0, 1.0);
        RANDOM_ATTACK_DAMAGE_MAX = BUILDER.defineInRange("max", 1.2, 1.0, 2.0);
        MAX_ACCESSORIES = BUILDER.pop().defineInRange("Max Accessory Amount", 7, 6, 100);
        COORD_VALUES = BUILDER.defineList("coordValues", List.of(
            "63;minecraft:overworld;info.terra_curio.depth_meter.underground;1;1;1;0;0;0;2;2;2",
            "9999;minecraft:overworld;info.terra_curio.depth_meter.surface;1;1;1;0;0;0;2;2;2"
        ), () -> "-64;.*;info.terra_curio.depth_meter.unknown;1;1;1;0;0;0;2;2;2", o -> true);
        USE_12H_TIME = BUILDER.define("use12htime", false);
        container.registerConfig(ModConfig.Type.COMMON, BUILDER.build());
    }
}
