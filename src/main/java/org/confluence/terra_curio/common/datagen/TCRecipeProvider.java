package org.confluence.terra_curio.common.datagen;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import net.minecraft.core.NonNullList;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.ConditionalRecipe;
import net.minecraftforge.common.crafting.conditions.ModLoadedCondition;
import net.minecraftforge.common.crafting.conditions.NotCondition;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.recipe.AmountIngredient;
import org.confluence.lib.common.recipe.EnvironmentLevelAccess;
import org.confluence.lib.common.recipe.SimpleFinishedRecipe;
import org.confluence.terra_curio.TerraCurio;
import org.confluence.terra_curio.common.init.TCItems;
import org.confluence.terra_curio.common.init.TCRecipes;
import org.confluence.terra_curio.common.recipe.WorkshopRecipe;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.function.Consumer;

public class TCRecipeProvider extends RecipeProvider {
    public TCRecipeProvider(PackOutput output) {
        super(output);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> writer) {
        /// 单独安装时保留简化配方；加载汇流来世后使用本体的获取与合成路线。
        Consumer<FinishedRecipe> conditional = recipe -> {
            ConditionalRecipe.Builder builder = ConditionalRecipe.builder()
                    .addCondition(new NotCondition(new ModLoadedCondition(ConfluenceMagicLib.CONFLUENCE_ID)))
                    .addRecipe(recipe);
            if (recipe.getAdvancementId() != null) {
                builder.generateAdvancement(recipe.getAdvancementId());
            }
            builder.build(writer, recipe.getId());
        };
        workshop(writer, TCItems.AMBER_HORSESHOE_BALLOON, Ingredient.of(TCItems.HONEY_BALLOON.get()), Ingredient.of(TCItems.LUCKY_HORSESHOE.get()));
        workshop(writer, "ambhipian_boots", TCItems.AMBHIPIAN_BOOTS, Ingredient.of(TCItems.SAILFISH_BOOTS.get()), Ingredient.of(TCItems.FROG_LEG.get()));
        workshop(conditional, "ankh_charm", TCItems.ANKH_CHARM,
                Ingredient.of(TCItems.DETOXIFICATION_CAPSULE.get()),
                Ingredient.of(TCItems.NUTRIENT_SOLUTION.get()),
                Ingredient.of(TCItems.SEARCHLIGHT.get()),
                Ingredient.of(TCItems.THE_PLAN.get()),
                Ingredient.of(TCItems.EXPLORERS_EQUIPMENT.get()));
        workshop(writer, "ankh_shield", TCItems.ANKH_SHIELD, Ingredient.of(TCItems.ANKH_CHARM.get()), Ingredient.of(TCItems.OBSIDIAN_SHIELD.get()));
        workshop(conditional, "architect_gizmo_pack", TCItems.ARCHITECT_GIZMO_PACK,
                Ingredient.of(TCItems.BRICK_LAYER.get()),
                Ingredient.of(TCItems.EXTENDO_GRIP.get()),
                Ingredient.of(TCItems.PORTABLE_CEMENT_MIXER.get()));
        workshop(writer, "arctic_diving_gear", TCItems.ARCTIC_DIVING_GEAR, Ingredient.of(TCItems.JELLYFISH_DIVING_GEAR.get()), Ingredient.of(TCItems.ICE_SKATES.get()));
        workshop(conditional, "avenger_emblem", TCItems.AVENGER_EMBLEM,
                Ingredient.of(TCItems.WARRIOR_EMBLEM.get()),
                Ingredient.of(TCItems.SORCERER_EMBLEM.get()),
                Ingredient.of(TCItems.RANGER_EMBLEM.get()));
        workshop(writer, "balloon_pufferfish", TCItems.BALLOON_PUFFERFISH, Ingredient.of(Items.PUFFERFISH), Ingredient.of(TCItems.SHINY_RED_BALLOON.get()));
        workshop(writer, "bee_cloak", TCItems.BEE_CLOAK, Ingredient.of(TCItems.STAR_CLOAK.get()), Ingredient.of(TCItems.HONEY_COMB.get()));
        workshop(writer, "berserkers_glove", TCItems.BERSERKERS_GLOVE, Ingredient.of(TCItems.POWER_GLOVE.get()), Ingredient.of(TCItems.FLESH_KNUCKLES.get()));
        workshop(writer, "blizzard_in_a_balloon", TCItems.BLIZZARD_IN_A_BALLOON, Ingredient.of(TCItems.BLIZZARD_IN_A_BOTTLE.get()), Ingredient.of(TCItems.SHINY_RED_BALLOON.get()));
        workshop(writer, "blue_horseshoe_balloon", TCItems.BLUE_HORSESHOE_BALLOON, Ingredient.of(TCItems.CLOUD_IN_A_BALLOON.get()), Ingredient.of(TCItems.LUCKY_HORSESHOE.get()));
        workshop(writer, "bundle_of_balloons", TCItems.BUNDLE_OF_BALLOONS,
                Ingredient.of(TCItems.CLOUD_IN_A_BALLOON.get()),
                Ingredient.of(TCItems.SANDSTORM_IN_A_BALLOON.get()),
                Ingredient.of(TCItems.BLIZZARD_IN_A_BALLOON.get()));
        workshop(writer, "bundle_of_horseshoe_balloons_0", TCItems.BUNDLE_OF_HORSESHOE_BALLOONS, Ingredient.of(TCItems.BUNDLE_OF_BALLOONS.get()), Ingredient.of(TCItems.LUCKY_HORSESHOE.get()));
        workshop(writer, "bundle_of_horseshoe_balloons_1", TCItems.BUNDLE_OF_HORSESHOE_BALLOONS,
                Ingredient.of(TCItems.YELLOW_HORSESHOE_BALLOON.get()),
                Ingredient.of(ItemTags.create(TerraCurio.asResource("any_blizzard_balloons"))),
                Ingredient.of(ItemTags.create(TerraCurio.asResource("any_cloud_balloons"))));
        workshop(writer, "bundle_of_horseshoe_balloons_2", TCItems.BUNDLE_OF_HORSESHOE_BALLOONS,
                Ingredient.of(ItemTags.create(TerraCurio.asResource("any_sandstorm_balloons"))),
                Ingredient.of(TCItems.WHITE_HORSESHOE_BALLOON.get()),
                Ingredient.of(ItemTags.create(TerraCurio.asResource("any_cloud_balloons"))));
        workshop(writer, "bundle_of_horseshoe_balloons_3", TCItems.BUNDLE_OF_HORSESHOE_BALLOONS,
                Ingredient.of(ItemTags.create(TerraCurio.asResource("any_sandstorm_balloons"))),
                Ingredient.of(ItemTags.create(TerraCurio.asResource("any_blizzard_balloons"))),
                Ingredient.of(TCItems.BLUE_HORSESHOE_BALLOON.get()));
        workshop(writer, "bundle_of_horseshoe_balloons_4", TCItems.BUNDLE_OF_HORSESHOE_BALLOONS,
                Ingredient.of(TCItems.SANDSTORM_IN_A_BALLOON.get()),
                Ingredient.of(TCItems.BLIZZARD_IN_A_BALLOON.get()),
                Ingredient.of(TCItems.CLOUD_IN_A_BALLOON.get()),
                Ingredient.of(TCItems.LUCKY_HORSESHOE.get()));
        workshop(writer, "celestial_shell", TCItems.CELESTIAL_SHELL, Ingredient.of(TCItems.MOON_SHELL.get()), Ingredient.of(TCItems.CELESTIAL_STONE.get()));
        workshop(writer, "celestial_stone", TCItems.CELESTIAL_STONE, Ingredient.of(TCItems.SUN_STONE.get()), Ingredient.of(TCItems.MOON_STONE.get()));
        workshop(writer, "cell_phone", TCItems.CELL_PHONE, Ingredient.of(TCItems.PDA.get()), Ingredient.of(TCItems.MAGIC_MIRROR.get()));
        workshop(writer, "cloud_in_a_balloon", TCItems.CLOUD_IN_A_BALLOON, Ingredient.of(TCItems.CLOUD_IN_A_BOTTLE.get()), Ingredient.of(TCItems.SHINY_RED_BALLOON.get()));
        workshop(writer, "destroyer_emblem", TCItems.DESTROYER_EMBLEM, Ingredient.of(TCItems.AVENGER_EMBLEM.get()), Ingredient.of(TCItems.EYE_OF_THE_GOLEM.get()));
        workshop(writer, "detoxification_capsule", TCItems.DETOXIFICATION_CAPSULE, Ingredient.of(TCItems.BEZOAR.get()), Ingredient.of(TCItems.HOLY_WATER.get()));
        workshop(writer, "diving_gear", TCItems.DIVING_GEAR, Ingredient.of(TCItems.DIVING_HELMET.get()), Ingredient.of(TCItems.FLIPPER.get()));
        workshop(writer, "explorers_equipment", TCItems.EXPLORERS_EQUIPMENT, Ingredient.of(TCItems.HAND_DRILL.get()), Ingredient.of(TCItems.SHOT_PUT.get()));
        workshop(writer, "fairy_boots", TCItems.FAIRY_BOOTS, Ingredient.of(TCItems.SPECTRE_BOOTS.get()), Ingredient.of(TCItems.FLOWER_BOOTS.get()));
        workshop(writer, "fart_in_a_balloon", TCItems.FART_IN_A_BALLOON, Ingredient.of(TCItems.FART_IN_A_JAR.get()), Ingredient.of(TCItems.SHINY_RED_BALLOON.get()));
        workshop(writer, "fire_gauntlet", TCItems.FIRE_GAUNTLET, Ingredient.of(TCItems.MAGMA_STONE.get()), Ingredient.of(TCItems.MECHANICAL_GLOVE.get()));
        workshop(writer, "fish_finder", TCItems.FISH_FINDER,
                Ingredient.of(TCItems.FISHERMANS_POCKET_GUIDE.get()),
                Ingredient.of(TCItems.WEATHER_RADIO.get()),
                Ingredient.of(TCItems.SEXTANT.get()));
        workshop(writer, "frog_flipper", TCItems.FROG_FLIPPER, Ingredient.of(TCItems.FROG_LEG.get()), Ingredient.of(TCItems.FLIPPER.get()));
        workshop(writer, "frog_gear", TCItems.FROG_GEAR, Ingredient.of(TCItems.TIGER_CLIMBING_GEAR.get()), Ingredient.of(TCItems.FROG_FLIPPER.get()));
        workshop(writer, "frog_gear_from_flipper", TCItems.FROG_GEAR, Ingredient.of(TCItems.FROG_WEBBING.get()), Ingredient.of(TCItems.FLIPPER.get()));
        workshop(writer, "frog_webbing", TCItems.FROG_WEBBING, Ingredient.of(TCItems.TIGER_CLIMBING_GEAR.get()), Ingredient.of(TCItems.FROG_LEG.get()));
        workshop(writer, "frostspark_boots", TCItems.FROSTSPARK_BOOTS, Ingredient.of(TCItems.LIGHTNING_BOOTS.get()), Ingredient.of(TCItems.ICE_SKATES.get()));
        workshop(writer, "frozen_shield", TCItems.FROZEN_SHIELD, Ingredient.of(TCItems.PALADINS_SHIELD.get()), Ingredient.of(TCItems.FROZEN_TURTLE_SHELL.get()));
        workshop(writer, "goblin_tech", TCItems.GOBLIN_TECH,
                Ingredient.of(TCItems.STOPWATCH.get()),
                Ingredient.of(TCItems.METAL_DETECTOR.get()),
                Ingredient.of(TCItems.DPS_METER.get()));
        workshop(writer, "gps_from_gold_watch", TCItems.GPS,
                Ingredient.of(TCItems.GOLD_WATCH.get()),
                Ingredient.of(TCItems.DEPTH_METER.get()),
                Ingredient.of(TCItems.COMPASS.get()));
        workshop(writer, "gps_from_platinum_watch", TCItems.GPS,
                Ingredient.of(TCItems.PLATINUM_WATCH.get()),
                Ingredient.of(TCItems.DEPTH_METER.get()),
                Ingredient.of(TCItems.COMPASS.get()));
        workshop(writer, "green_horseshoe_balloon", TCItems.GREEN_HORSESHOE_BALLOON, Ingredient.of(TCItems.FART_IN_A_BALLOON.get()), Ingredient.of(TCItems.LUCKY_HORSESHOE.get()));
        workshop(writer, "hand_of_creation", TCItems.HAND_OF_CREATION,
                Ingredient.of(TCItems.STEP_STOOL.get()),
                Ingredient.of(TCItems.ARCHITECT_GIZMO_PACK.get()),
                Ingredient.of(TCItems.ANCIENT_CHISEL.get()),
                Ingredient.of(TCItems.TREASURE_MAGNET.get()));
        workshop(writer, "hand_warmer", TCItems.HAND_WARMER,
                Ingredient.of(Items.RED_WOOL),
                Ingredient.of(Items.WHITE_WOOL),
                Ingredient.of(Items.LEATHER));
        workshop(writer, "hero_shield", TCItems.HERO_SHIELD, Ingredient.of(TCItems.PALADINS_SHIELD.get()), Ingredient.of(TCItems.FLESH_KNUCKLES.get()));
        workshop(writer, "honey_balloon", TCItems.HONEY_BALLOON, Ingredient.of(TCItems.HONEY_COMB.get()), Ingredient.of(TCItems.SHINY_RED_BALLOON.get()));
        workshop(writer, "jellyfish_diving_gear", TCItems.JELLYFISH_DIVING_GEAR, Ingredient.of(TCItems.DIVING_GEAR.get()), Ingredient.of(TCItems.JELLYFISH_NECKLACE.get()));
        workshop(writer, "lava_waders_from_lava_charm", TCItems.LAVA_WADERS,
                Ingredient.of(TCItems.OBSIDIAN_WATER_WALKING_BOOTS.get()),
                Ingredient.of(TCItems.OBSIDIAN_ROSE.get()),
                Ingredient.of(TCItems.LAVA_CHARM.get()));
        workshop(writer, "lava_waders_from_molten_charm", TCItems.LAVA_WADERS,
                Ingredient.of(TCItems.OBSIDIAN_WATER_WALKING_BOOTS.get()),
                Ingredient.of(TCItems.OBSIDIAN_ROSE.get()),
                Ingredient.of(TCItems.MOLTEN_CHARM.get()));
        workshop(writer, "lava_waders_from_molten_skull_rose", TCItems.LAVA_WADERS, Ingredient.of(TCItems.WATER_WALKING_BOOTS.get()), Ingredient.of(TCItems.MOLTEN_SKULL_ROSE.get()));
        workshop(writer, "lava_waders_from_obsidian_water_walking_boots", TCItems.LAVA_WADERS, Ingredient.of(TCItems.OBSIDIAN_WATER_WALKING_BOOTS.get()), Ingredient.of(TCItems.MOLTEN_SKULL_ROSE.get()));
        workshop(writer, "lava_waders_from_water_walking_boots", TCItems.LAVA_WADERS,
                Ingredient.of(TCItems.WATER_WALKING_BOOTS.get()),
                Ingredient.of(TCItems.OBSIDIAN_ROSE.get()),
                Ingredient.of(TCItems.MOLTEN_CHARM.get()));
        workshop(writer, "lightning_boots", TCItems.LIGHTNING_BOOTS,
                Ingredient.of(TCItems.SPECTRE_BOOTS.get()),
                Ingredient.of(TCItems.AGLET.get()),
                Ingredient.of(TCItems.ANKLET_OF_THE_WIND.get()));
        workshop(writer, "magma_skull", TCItems.MAGMA_SKULL, Ingredient.of(TCItems.LAVA_CHARM.get()), Ingredient.of(TCItems.OBSIDIAN_SKULL.get()));
        workshop(writer, "master_ninja_gear", TCItems.MASTER_NINJA_GEAR,
                Ingredient.of(TCItems.BLACK_BELT.get()),
                Ingredient.of(TCItems.TIGER_CLIMBING_GEAR.get()),
                Ingredient.of(TCItems.TABI.get()));
        workshop(writer, "mechanical_glove", TCItems.MECHANICAL_GLOVE, Ingredient.of(TCItems.POWER_GLOVE.get()), Ingredient.of(TCItems.AVENGER_EMBLEM.get()));
        workshop(writer, "molten_charm", TCItems.MOLTEN_CHARM, Ingredient.of(TCItems.LAVA_CHARM.get()), Ingredient.of(TCItems.OBSIDIAN_SKULL.get()));
        workshop(writer, "molten_quiver", TCItems.MOLTEN_QUIVER, Ingredient.of(TCItems.MAGIC_QUIVER.get()), Ingredient.of(TCItems.MAGMA_STONE.get()));
        workshop(writer, "molten_skull_rose_from_lava_charm", TCItems.MOLTEN_SKULL_ROSE, Ingredient.of(TCItems.OBSIDIAN_SKULL_ROSE.get()), Ingredient.of(TCItems.LAVA_CHARM.get()));
        workshop(writer, "molten_skull_rose_from_obsidian_rose", TCItems.MOLTEN_SKULL_ROSE, Ingredient.of(TCItems.MAGMA_SKULL.get()), Ingredient.of(TCItems.OBSIDIAN_ROSE.get()));
        workshop(writer, "molten_skull_rose_from_obsidian_skull_rose", TCItems.MOLTEN_SKULL_ROSE, Ingredient.of(TCItems.MAGMA_SKULL.get()), Ingredient.of(TCItems.OBSIDIAN_SKULL_ROSE.get()));
        workshop(writer, "moon_shell", TCItems.MOON_SHELL, Ingredient.of(TCItems.MOON_CHARM.get()), Ingredient.of(TCItems.NEPTUNES_SHELL.get()));
        workshop(writer, "nutrient_solution", TCItems.NUTRIENT_SOLUTION, Ingredient.of(TCItems.VITAMINS.get()), Ingredient.of(TCItems.ENERGY_BAR.get()));
        workshop(writer, "obsidian_horseshoe", TCItems.OBSIDIAN_HORSESHOE, Ingredient.of(TCItems.LUCKY_HORSESHOE.get()), Ingredient.of(TCItems.OBSIDIAN_SKULL.get()));
        workshop(writer, "obsidian_shield", TCItems.OBSIDIAN_SHIELD, Ingredient.of(TCItems.COBALT_SHIELD.get()), Ingredient.of(TCItems.OBSIDIAN_SKULL.get()));
        workshop(writer, "obsidian_skull_rose", TCItems.OBSIDIAN_SKULL_ROSE, Ingredient.of(TCItems.OBSIDIAN_ROSE.get()), Ingredient.of(TCItems.OBSIDIAN_SKULL.get()));
        workshop(writer, "obsidian_water_walking_boots", TCItems.OBSIDIAN_WATER_WALKING_BOOTS, Ingredient.of(TCItems.WATER_WALKING_BOOTS.get()), Ingredient.of(TCItems.OBSIDIAN_SKULL.get()));
        workshop(writer, "pda", TCItems.PDA,
                Ingredient.of(TCItems.FISH_FINDER.get()),
                Ingredient.of(TCItems.GOBLIN_TECH.get()),
                Ingredient.of(TCItems.REK_3000.get()),
                Ingredient.of(TCItems.GPS.get()));
        workshop(writer, "pink_horseshoe_balloon", TCItems.PINK_HORSESHOE_BALLOON, Ingredient.of(TCItems.SHARKRON_BALLOON.get()), Ingredient.of(TCItems.LUCKY_HORSESHOE.get()));
        workshop(writer, "power_glove", TCItems.POWER_GLOVE, Ingredient.of(TCItems.FERAL_CLAWS.get()), Ingredient.of(TCItems.TITAN_GLOVE.get()));
        workshop(writer, "recon_scope", TCItems.RECON_SCOPE, Ingredient.of(TCItems.SNIPER_SCOPE.get()), Ingredient.of(TCItems.PUTRID_SCENT.get()));
        workshop(writer, "rek_3000", TCItems.REK_3000,
                Ingredient.of(TCItems.RADAR.get()),
                Ingredient.of(TCItems.LIFE_FORM_ANALYZER.get()),
                Ingredient.of(TCItems.TALLY_COUNTER.get()));
        workshop(writer, "sandstorm_in_a_balloon", TCItems.SANDSTORM_IN_A_BALLOON, Ingredient.of(TCItems.SANDSTORM_IN_A_BOTTLE.get()), Ingredient.of(TCItems.SHINY_RED_BALLOON.get()));
        workshop(writer, "searchlight", TCItems.SEARCHLIGHT, Ingredient.of(TCItems.BLINDFOLD.get()), Ingredient.of(TCItems.FLASHLIGHT.get()));
        workshop(writer, "sharkron_balloon", TCItems.SHARKRON_BALLOON, Ingredient.of(TCItems.TSUNAMI_IN_A_BOTTLE.get()), Ingredient.of(TCItems.BALLOON_PUFFERFISH.get()));
        workshop(writer, "sniper_scope", TCItems.SNIPER_SCOPE, Ingredient.of(TCItems.RIFLE_SCOPE.get()), Ingredient.of(TCItems.DESTROYER_EMBLEM.get()));
        workshop(writer, "spectre_boots_from_dunerider_boots", TCItems.SPECTRE_BOOTS, Ingredient.of(TCItems.DUNERIDER_BOOTS.get()), Ingredient.of(TCItems.ROCKET_BOOTS.get()));
        workshop(writer, "spectre_boots_from_flurry_boots", TCItems.SPECTRE_BOOTS, Ingredient.of(TCItems.FLURRY_BOOTS.get()), Ingredient.of(TCItems.ROCKET_BOOTS.get()));
        workshop(writer, "spectre_boots_from_hermes_boots", TCItems.SPECTRE_BOOTS, Ingredient.of(TCItems.HERMES_BOOTS.get()), Ingredient.of(TCItems.ROCKET_BOOTS.get()));
        workshop(writer, "spectre_boots_from_sailfish_boots", TCItems.SPECTRE_BOOTS, Ingredient.of(TCItems.SAILFISH_BOOTS.get()), Ingredient.of(TCItems.ROCKET_BOOTS.get()));
        workshop(writer, "stalkers_quiver", TCItems.STALKERS_QUIVER, Ingredient.of(TCItems.PUTRID_SCENT.get()), Ingredient.of(TCItems.MAGIC_QUIVER.get()));
        workshop(writer, "star_veil", TCItems.STAR_VEIL, Ingredient.of(TCItems.STAR_CLOAK.get()), Ingredient.of(TCItems.CROSS_NECKLACE.get()));
        workshop(writer, "step_stool", TCItems.STEP_STOOL, AmountIngredient.of(2, Items.STICK), AmountIngredient.of(5, Items.SCAFFOLDING));
        workshop(writer, "stinger_necklace", TCItems.STINGER_NECKLACE, Ingredient.of(TCItems.HONEY_COMB.get()), Ingredient.of(TCItems.SHARK_TOOTH_NECKLACE.get()));
        workshop(writer, "sweetheart_necklace", TCItems.SWEETHEART_NECKLACE, Ingredient.of(TCItems.PANIC_NECKLACE.get()), Ingredient.of(TCItems.HONEY_COMB.get()));
        workshop(writer, "terraspark_boots", TCItems.TERRASPARK_BOOTS, Ingredient.of(TCItems.FROSTSPARK_BOOTS.get()), Ingredient.of(TCItems.LAVA_WADERS.get()));
        workshop(writer, "the_plan", TCItems.THE_PLAN, Ingredient.of(TCItems.FAST_CLOCK.get()), Ingredient.of(TCItems.TRIFOLD_MAP.get()));
        workshop(writer, "tiger_climbing_gear", TCItems.TIGER_CLIMBING_GEAR, Ingredient.of(TCItems.CLIMBING_CLAWS.get()), Ingredient.of(TCItems.SHOE_SPIKES.get()));
        workshop(writer, "white_horseshoe_balloon", TCItems.WHITE_HORSESHOE_BALLOON, Ingredient.of(TCItems.BLIZZARD_IN_A_BALLOON.get()), Ingredient.of(TCItems.LUCKY_HORSESHOE.get()));
        workshop(writer, "yellow_horseshoe_balloon", TCItems.YELLOW_HORSESHOE_BALLOON, Ingredient.of(TCItems.SANDSTORM_IN_A_BALLOON.get()), Ingredient.of(TCItems.LUCKY_HORSESHOE.get()));

        extraStepStool(writer, "extra_step_stool");
        extraStepStool(writer, "extra_hand_of_creation");

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TCItems.ANKLET_OF_THE_WIND.get())
                .pattern(" # ")
                .pattern("#S#")
                .pattern(" # ")
                .define('#', Items.VINE)
                .define('S', Items.SPORE_BLOSSOM)
                .unlockedBy("has", has(TCItems.ANKLET_OF_THE_WIND.get()))
                .save(conditional, TerraCurio.asResource("anklet_of_the_wind"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TCItems.BLIZZARD_IN_A_BOTTLE.get())
                .pattern(" # ")
                .pattern("#S#")
                .pattern(" # ")
                .define('#', Items.BLUE_ICE)
                .define('S', TCItems.CLOUD_IN_A_BOTTLE.get())
                .unlockedBy("has", has(TCItems.BLIZZARD_IN_A_BOTTLE.get()))
                .save(conditional, TerraCurio.asResource("blizzard_in_a_bottle"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TCItems.COPPER_WATCH.get())
                .pattern(" # ")
                .pattern("cRc")
                .pattern(" c ")
                .define('#', Items.CHAIN)
                .define('c', Items.COPPER_INGOT)
                .define('R', Items.REDSTONE)
                .unlockedBy("has", has(TCItems.COPPER_WATCH.get()))
                .save(writer, TerraCurio.asResource("copper_watch"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TCItems.DPS_METER.get())
                .pattern(" i ")
                .pattern("iti")
                .pattern(" i ")
                .define('i', Items.IRON_INGOT)
                .define('t', Items.TARGET)
                .unlockedBy("has", has(TCItems.DPS_METER.get()))
                .save(conditional, TerraCurio.asResource("dps_meter"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TCItems.GOLD_WATCH.get())
                .pattern(" # ")
                .pattern("cRc")
                .pattern(" c ")
                .define('#', Items.CHAIN)
                .define('c', Items.GOLD_INGOT)
                .define('R', Items.REDSTONE)
                .unlockedBy("has", has(TCItems.GOLD_WATCH.get()))
                .save(writer, TerraCurio.asResource("gold_watch"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TCItems.ICE_SKATES.get())
                .pattern(" # ")
                .pattern("#S#")
                .pattern(" # ")
                .define('#', Items.BLUE_ICE)
                .define('S', TCItems.HERMES_BOOTS.get())
                .unlockedBy("has", has(TCItems.ICE_SKATES.get()))
                .save(conditional, TerraCurio.asResource("ice_skates"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TCItems.MAGIC_MIRROR.get())
                .pattern("gGg")
                .pattern("GDG")
                .pattern("gGg")
                .define('g', Items.GOLD_INGOT)
                .define('G', Items.GLASS)
                .define('D', Items.DIAMOND)
                .unlockedBy("has", has(TCItems.MAGIC_MIRROR.get()))
                .save(conditional, TerraCurio.asResource("magic_mirror_from_gold_ingot"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TCItems.MAGIC_MIRROR.get())
                .pattern("gGg")
                .pattern("GDG")
                .pattern("gGg")
                .define('g', ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "ingots/platinum")))
                .define('G', Items.GLASS)
                .define('D', Items.DIAMOND)
                .unlockedBy("has", has(TCItems.MAGIC_MIRROR.get()))
                .save(conditional, TerraCurio.asResource("magic_mirror_from_platinum_ingot"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TCItems.OBSIDIAN_SKULL.get())
                .pattern("###")
                .pattern("#W#")
                .pattern("###")
                .define('#', Items.OBSIDIAN)
                .define('W', Items.WITHER_SKELETON_SKULL)
                .unlockedBy("has", has(TCItems.OBSIDIAN_SKULL.get()))
                .save(conditional, TerraCurio.asResource("obsidian_skull"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TCItems.PLATINUM_WATCH.get())
                .pattern(" # ")
                .pattern("cRc")
                .pattern(" c ")
                .define('#', Items.CHAIN)
                .define('c', ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "ingots/platinum")))
                .define('R', Items.REDSTONE)
                .unlockedBy("has", has(TCItems.PLATINUM_WATCH.get()))
                .save(writer, TerraCurio.asResource("platinum_watch"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TCItems.ROCKET_BOOTS.get())
                .pattern("#I#")
                .pattern("i#i")
                .define('#', Items.FIRE_CHARGE)
                .define('I', Items.IRON_BOOTS)
                .define('i', Items.IRON_INGOT)
                .unlockedBy("has", has(TCItems.ROCKET_BOOTS.get()))
                .save(conditional, TerraCurio.asResource("rocket_boots"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TCItems.SILVER_WATCH.get())
                .pattern(" # ")
                .pattern("cRc")
                .pattern(" c ")
                .define('#', Items.CHAIN)
                .define('c', ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "ingots/silver")))
                .define('R', Items.REDSTONE)
                .unlockedBy("has", has(TCItems.SILVER_WATCH.get()))
                .save(writer, TerraCurio.asResource("silver_watch"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TCItems.STOPWATCH.get())
                .pattern("ici")
                .pattern("iri")
                .pattern("iii")
                .define('c', Items.CHAIN)
                .define('r', Items.REDSTONE)
                .define('i', Items.IRON_INGOT)
                .unlockedBy("has", has(TCItems.STOPWATCH.get()))
                .save(conditional, TerraCurio.asResource("stopwatch"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TCItems.TIN_WATCH.get())
                .pattern(" # ")
                .pattern("cRc")
                .pattern(" c ")
                .define('#', Items.CHAIN)
                .define('c', ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "ingots/tin")))
                .define('R', Items.REDSTONE)
                .unlockedBy("has", has(TCItems.TIN_WATCH.get()))
                .save(writer, TerraCurio.asResource("tin_watch"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TCItems.TUNGSTEN_WATCH.get())
                .pattern(" # ")
                .pattern("cRc")
                .pattern(" c ")
                .define('#', Items.CHAIN)
                .define('c', ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "ingots/tungsten")))
                .define('R', Items.REDSTONE)
                .unlockedBy("has", has(TCItems.TUNGSTEN_WATCH.get()))
                .save(writer, TerraCurio.asResource("tungsten_watch"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TCItems.WORKSHOP.get())
                .pattern("aba")
                .pattern("ada")
                .pattern("c c")
                .define('a', Items.RED_WOOL)
                .define('b', Items.CRAFTING_TABLE)
                .define('c', Items.STICK)
                .define('d', Items.BOOKSHELF)
                .unlockedBy("has", has(TCItems.WORKSHOP.get()))
                .save(conditional, TerraCurio.asResource("workshop"));
    }

    protected void workshop(Consumer<FinishedRecipe> writer, ItemLike result, Ingredient... ingredients) {
        workshop(writer, result, EnvironmentLevelAccess.Matcher.EMPTY, ingredients);
    }

    protected void workshop(Consumer<FinishedRecipe> writer, ItemLike result, EnvironmentLevelAccess.Matcher environment, Ingredient... ingredients) {
        WorkshopRecipe recipe = new WorkshopRecipe(result.asItem().getDefaultInstance(), NonNullList.of(Ingredient.EMPTY, ingredients), environment);
        ResourceLocation id = key(getItemName(result.asItem()));
        writer.accept(new FinishedRecipe() {
            private static final Codec<WorkshopRecipe> CODEC = WorkshopRecipe.Serializer.CODEC.codec();

            @Override
            public void serializeRecipeData(JsonObject json) {
                CODEC.encodeStart(JsonOps.INSTANCE, recipe).result().ifPresent(element -> {
                    if (element.isJsonObject()) {
                        for (Map.Entry<String, JsonElement> entry : element.getAsJsonObject().entrySet()) {
                            json.add(entry.getKey(), entry.getValue());
                        }
                    }
                });
            }

            @Override
            public ResourceLocation getId() {
                return id;
            }

            @Override
            public RecipeSerializer<?> getType() {
                return TCRecipes.WORKSHOP_SERIALIZER.get();
            }

            @Override
            public @Nullable JsonObject serializeAdvancement() {
                return null;
            }

            @Override
            public @Nullable ResourceLocation getAdvancementId() {
                return null;
            }
        });
    }

    protected void workshop(Consumer<FinishedRecipe> writer, String name, ItemLike result, Ingredient... ingredients) {
        workshop(writer, name, result, EnvironmentLevelAccess.Matcher.EMPTY, ingredients);
    }

    protected void workshop(Consumer<FinishedRecipe> writer, String name, ItemLike result, EnvironmentLevelAccess.Matcher environment, Ingredient... ingredients) {
        WorkshopRecipe recipe = new WorkshopRecipe(result.asItem().getDefaultInstance(), NonNullList.of(Ingredient.EMPTY, ingredients), environment);
        writer.accept(new SimpleFinishedRecipe<>(key(name), recipe));
    }

    private void extraStepStool(Consumer<FinishedRecipe> writer, String name) {
        writer.accept(new FinishedRecipe() {
            @Override
            public void serializeRecipeData(JsonObject json) {}

            @Override
            public ResourceLocation getId() {
                return key(name);
            }

            @Override
            public RecipeSerializer<?> getType() {
                return TCRecipes.EXTRA_STEP_STOOL_SERIALIZER.get();
            }

            @Override
            public @Nullable JsonObject serializeAdvancement() {
                return null;
            }

            @Override
            public @Nullable ResourceLocation getAdvancementId() {
                return null;
            }
        });
    }

    private static ResourceLocation key(String path) {
        return TerraCurio.asResource(path);
    }
}
