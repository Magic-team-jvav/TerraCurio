package org.confluence.terra_curio.common;

import it.unimi.dsi.fastutil.objects.Object2IntLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntSortedMap;
import net.minecraft.commands.arguments.blocks.BlockStateParser;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.confluence.terra_curio.TerraCurio;

import java.util.List;

public final class TCCommonConfigs {
    private static ForgeConfigSpec.ConfigValue<List<? extends String>> RARE_BLOCKS;
    private static ForgeConfigSpec.ConfigValue<List<? extends String>> RARE_CREATURES;
    public static Object2IntSortedMap<BlockState> rareBlocks = new Object2IntLinkedOpenHashMap<>();
    public static Object2IntSortedMap<EntityType<?>> rareCreatures = new Object2IntLinkedOpenHashMap<>();

    public static ForgeConfigSpec.BooleanValue RANDOM_ATTACK_DAMAGE;
    public static ForgeConfigSpec.DoubleValue RANDOM_ATTACK_DAMAGE_MIN;
    public static ForgeConfigSpec.DoubleValue RANDOM_ATTACK_DAMAGE_MAX;

    public static ForgeConfigSpec.IntValue MAX_ACCESSORIES;

    public static void onLoad() {
        Object2IntSortedMap<BlockState> blockStates = new Object2IntLinkedOpenHashMap<>();
        RARE_BLOCKS.get().forEach(s -> {
            try {
                blockStates.put(BlockStateParser.parseForBlock(BuiltInRegistries.BLOCK.asLookup(), s, false).blockState(), blockStates.size());
            } catch (Exception e) {
                TerraCurio.LOGGER.debug("BlockState {} not found", s);
            }
        });
        rareBlocks = blockStates;

        Object2IntSortedMap<EntityType<?>> entityTypes = new Object2IntLinkedOpenHashMap<>();
        RARE_CREATURES.get().forEach(s -> {
            ResourceLocation id = ResourceLocation.parse(s);
            ForgeRegistries.ENTITY_TYPES.getHolder(id).ifPresentOrElse(
                    type -> entityTypes.put(type.value(), entityTypes.size()),
                    () -> TerraCurio.LOGGER.debug("EntityType {} not found", id)
            );
        });
        rareCreatures = entityTypes;
    }

    public static void register(FMLJavaModLoadingContext context) {
        ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
        RARE_BLOCKS = BUILDER.comment(
                "In order for the block to be found by the Metal Detector",
                "You need to fill the list with string like 'modid:block[state1=true]' or 'modid:block'",
                "The higher the block in the list, the higher the value"
        ).defineListAllowEmpty("rareBlocks", List.of(
                "confluence:life_fruit",
                "confluence:chlorophyte_ore",
                "confluence:deepslate_titanium_ore",
                "confluence:deepslate_adamantite_ore",
                "confluence:deepslate_orichalcum_ore",
                "confluence:deepslate_mythril_ore",
                "confluence:deepslate_palladium_ore",
                "confluence:deepslate_cobalt_ore",
                "confluence:dragonsal_ore",
                "confluence:lunartear_ore",
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
        ), o -> true);
        RARE_CREATURES = BUILDER.comment(
                "In order for the creature to be found by the Life Form Analyzer",
                "You need to fill the list with string like 'modid:entity'",
                "The higher the creature in the list, the higher the value"
        ).defineListAllowEmpty("rareCreatures", List.of(
                "confluence:jungle_mimic",
                "confluence:corrupt_mimic",
                "confluence:crimson_mimic",
                "confluence:hallowed_mimic",
                "confluence:golden_mimic",
                "confluence:ice_mimic",
                "confluence:shadow_mimic",
                "confluence:wooden_mimic",
                "confluence:voodoo_demon",
                "confluence:dungeon_slime",
                "confluence:nymph",
                "confluence:wandering_eye_fish",
                "confluence:golden_slime",
                "confluence:pink_slime",
                "minecraft:skeleton_horse",
                "minecraft:sniffer",
                "minecraft:allay",
                "minecraft:warden",
                "minecraft:mooshroom",
                "minecraft:panda"
        ), o -> true);
        RANDOM_ATTACK_DAMAGE = BUILDER.push("Random Attack Damage").define("enable", false);
        RANDOM_ATTACK_DAMAGE_MIN = BUILDER.defineInRange("min", 0.8, 0.0, 1.0);
        RANDOM_ATTACK_DAMAGE_MAX = BUILDER.defineInRange("max", 1.2, 1.0, 2.0);
        MAX_ACCESSORIES = BUILDER.pop().defineInRange("Max Accessory Amount", 7, 6, 100);
        context.registerConfig(ModConfig.Type.COMMON, BUILDER.build());
    }
}
