package org.confluence.terra_curio.common.data.gen;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.conditions.ICondition;
import org.confluence.lib.common.LibTags;
import org.confluence.lib.common.recipe.AmountIngredient;
import org.confluence.lib.common.recipe.EnvironmentLevelAccess;
import org.confluence.terra_curio.TerraCurio;
import org.confluence.terra_curio.common.init.TCItems;
import org.confluence.terra_curio.common.init.TCTags;
import org.confluence.terra_curio.common.recipe.ExtraStepStoolRecipe;
import org.confluence.terra_curio.common.recipe.WorkshopRecipe;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class TCRecipeProvider extends RecipeProvider {
    public TCRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput output) {
        workshop(output, TCItems.AMBER_HORSESHOE_BALLOON, Ingredient.of(TCItems.HONEY_BALLOON), Ingredient.of(TCItems.LUCKY_HORSESHOE));
        workshop(output, "ambhipian_boots", TCItems.AMBHIPIAN_BOOTS, Ingredient.of(TCItems.SAILFISH_BOOTS.get()), Ingredient.of(TCItems.FROG_LEG.get()));
        workshop(output, "ankh_shield", TCItems.ANKH_SHIELD, Ingredient.of(TCItems.ANKH_CHARM.get()), Ingredient.of(TCItems.OBSIDIAN_SHIELD.get()));
        workshop(output, "arctic_diving_gear", TCItems.ARCTIC_DIVING_GEAR, Ingredient.of(TCItems.JELLYFISH_DIVING_GEAR.get()), Ingredient.of(TCItems.ICE_SKATES.get()));
        workshop(output, "balloon_pufferfish", TCItems.BALLOON_PUFFERFISH, Ingredient.of(Items.PUFFERFISH), Ingredient.of(TCItems.SHINY_RED_BALLOON.get()));
        workshop(output, "bee_cloak", TCItems.BEE_CLOAK, Ingredient.of(TCItems.STAR_CLOAK.get()), Ingredient.of(TCItems.HONEY_COMB.get()));
        workshop(output, "berserkers_glove", TCItems.BERSERKERS_GLOVE, Ingredient.of(TCItems.POWER_GLOVE.get()), Ingredient.of(TCItems.FLESH_KNUCKLES.get()));
        workshop(output, "blizzard_in_a_balloon", TCItems.BLIZZARD_IN_A_BALLOON, Ingredient.of(TCItems.BLIZZARD_IN_A_BOTTLE.get()), Ingredient.of(TCItems.SHINY_RED_BALLOON.get()));
        workshop(output, "blue_horseshoe_balloon", TCItems.BLUE_HORSESHOE_BALLOON, Ingredient.of(TCItems.CLOUD_IN_A_BALLOON.get()), Ingredient.of(TCItems.LUCKY_HORSESHOE.get()));
        workshop(output, "bundle_of_balloons", TCItems.BUNDLE_OF_BALLOONS,
                Ingredient.of(TCItems.CLOUD_IN_A_BALLOON.get()),
                Ingredient.of(TCItems.SANDSTORM_IN_A_BALLOON.get()),
                Ingredient.of(TCItems.BLIZZARD_IN_A_BALLOON.get()));
        workshop(output, "bundle_of_horseshoe_balloons_0", TCItems.BUNDLE_OF_HORSESHOE_BALLOONS, Ingredient.of(TCItems.BUNDLE_OF_BALLOONS.get()), Ingredient.of(TCItems.LUCKY_HORSESHOE.get()));
        Ingredient anyBlizzardBalloons = Ingredient.of(TCTags.Items.ANY_BLIZZARD_BALLOONS);
        Ingredient anyCloudBalloons = Ingredient.of(TCTags.Items.ANY_CLOUD_BALLOONS);
        Ingredient anySandstormBalloons = Ingredient.of(TCTags.Items.ANY_SANDSTORM_BALLOONS);
        workshop(output, "bundle_of_horseshoe_balloons_1", TCItems.BUNDLE_OF_HORSESHOE_BALLOONS,
                Ingredient.of(TCItems.YELLOW_HORSESHOE_BALLOON.get()),
                anyBlizzardBalloons,
                anyCloudBalloons);
        workshop(output, "bundle_of_horseshoe_balloons_2", TCItems.BUNDLE_OF_HORSESHOE_BALLOONS,
                anySandstormBalloons,
                Ingredient.of(TCItems.WHITE_HORSESHOE_BALLOON.get()),
                anyCloudBalloons);
        workshop(output, "bundle_of_horseshoe_balloons_3", TCItems.BUNDLE_OF_HORSESHOE_BALLOONS,
                anySandstormBalloons,
                anyBlizzardBalloons,
                Ingredient.of(TCItems.BLUE_HORSESHOE_BALLOON.get()));
        workshop(output, "bundle_of_horseshoe_balloons_4", TCItems.BUNDLE_OF_HORSESHOE_BALLOONS,
                Ingredient.of(TCItems.SANDSTORM_IN_A_BALLOON.get()),
                Ingredient.of(TCItems.BLIZZARD_IN_A_BALLOON.get()),
                Ingredient.of(TCItems.CLOUD_IN_A_BALLOON.get()),
                Ingredient.of(TCItems.LUCKY_HORSESHOE.get()));
        workshop(output, "celestial_shell", TCItems.CELESTIAL_SHELL, Ingredient.of(TCItems.MOON_SHELL.get()), Ingredient.of(TCItems.CELESTIAL_STONE.get()));
        workshop(output, "celestial_stone", TCItems.CELESTIAL_STONE, Ingredient.of(TCItems.SUN_STONE.get()), Ingredient.of(TCItems.MOON_STONE.get()));
        workshop(output, "cell_phone", TCItems.CELL_PHONE, Ingredient.of(TCItems.PDA.get()), Ingredient.of(TCItems.MAGIC_MIRROR.get()));
        workshop(output, "cloud_in_a_balloon", TCItems.CLOUD_IN_A_BALLOON, Ingredient.of(TCItems.CLOUD_IN_A_BOTTLE.get()), Ingredient.of(TCItems.SHINY_RED_BALLOON.get()));
        workshop(output, "destroyer_emblem", TCItems.DESTROYER_EMBLEM, Ingredient.of(TCItems.AVENGER_EMBLEM.get()), Ingredient.of(TCItems.EYE_OF_THE_GOLEM.get()));
        workshop(output, "detoxification_capsule", TCItems.DETOXIFICATION_CAPSULE, Ingredient.of(TCItems.BEZOAR.get()), Ingredient.of(TCItems.HOLY_WATER.get()));
        workshop(output, "diving_gear", TCItems.DIVING_GEAR, Ingredient.of(TCItems.DIVING_HELMET.get()), Ingredient.of(TCItems.FLIPPER.get()));
        workshop(output, "explorers_equipment", TCItems.EXPLORERS_EQUIPMENT, Ingredient.of(TCItems.HAND_DRILL.get()), Ingredient.of(TCItems.SHOT_PUT.get()));
        workshop(output, "fairy_boots", TCItems.FAIRY_BOOTS, Ingredient.of(TCItems.SPECTRE_BOOTS.get()), Ingredient.of(TCItems.FLOWER_BOOTS.get()));
        workshop(output, "fart_in_a_balloon", TCItems.FART_IN_A_BALLOON, Ingredient.of(TCItems.FART_IN_A_JAR.get()), Ingredient.of(TCItems.SHINY_RED_BALLOON.get()));
        workshop(output, "fire_gauntlet", TCItems.FIRE_GAUNTLET, Ingredient.of(TCItems.MAGMA_STONE.get()), Ingredient.of(TCItems.MECHANICAL_GLOVE.get()));
        workshop(output, "fish_finder", TCItems.FISH_FINDER,
                Ingredient.of(TCItems.FISHERMANS_POCKET_GUIDE.get()),
                Ingredient.of(TCItems.WEATHER_RADIO.get()),
                Ingredient.of(TCItems.SEXTANT.get()));
        workshop(output, "frog_flipper", TCItems.FROG_FLIPPER, Ingredient.of(TCItems.FROG_LEG.get()), Ingredient.of(TCItems.FLIPPER.get()));
        workshop(output, "frog_gear", TCItems.FROG_GEAR, Ingredient.of(TCItems.TIGER_CLIMBING_GEAR.get()), Ingredient.of(TCItems.FROG_FLIPPER.get()));
        workshop(output, "frog_gear_from_flipper", TCItems.FROG_GEAR, Ingredient.of(TCItems.FROG_WEBBING.get()), Ingredient.of(TCItems.FLIPPER.get()));
        workshop(output, "frog_webbing", TCItems.FROG_WEBBING, Ingredient.of(TCItems.TIGER_CLIMBING_GEAR.get()), Ingredient.of(TCItems.FROG_LEG.get()));
        workshop(output, "frostspark_boots", TCItems.FROSTSPARK_BOOTS, Ingredient.of(TCItems.LIGHTNING_BOOTS.get()), Ingredient.of(TCItems.ICE_SKATES.get()));
        workshop(output, "frozen_shield", TCItems.FROZEN_SHIELD, Ingredient.of(TCItems.PALADINS_SHIELD.get()), Ingredient.of(TCItems.FROZEN_TURTLE_SHELL.get()));
        workshop(output, "goblin_tech", TCItems.GOBLIN_TECH,
                Ingredient.of(TCItems.STOPWATCH.get()),
                Ingredient.of(TCItems.METAL_DETECTOR.get()),
                Ingredient.of(TCItems.DPS_METER.get()));
        workshop(output, "gps_from_gold_watch", TCItems.GPS,
                Ingredient.of(TCItems.GOLD_WATCH.get()),
                Ingredient.of(TCItems.DEPTH_METER.get()),
                Ingredient.of(TCItems.COMPASS.get()));
        workshop(output, "gps_from_platinum_watch", TCItems.GPS,
                Ingredient.of(TCItems.PLATINUM_WATCH.get()),
                Ingredient.of(TCItems.DEPTH_METER.get()),
                Ingredient.of(TCItems.COMPASS.get()));
        workshop(output, "green_horseshoe_balloon", TCItems.GREEN_HORSESHOE_BALLOON, Ingredient.of(TCItems.FART_IN_A_BALLOON.get()), Ingredient.of(TCItems.LUCKY_HORSESHOE.get()));
        workshop(output, "hand_of_creation", TCItems.HAND_OF_CREATION,
                Ingredient.of(TCItems.STEP_STOOL.get()),
                Ingredient.of(TCItems.ARCHITECT_GIZMO_PACK.get()),
                Ingredient.of(TCItems.ANCIENT_CHISEL.get()),
                Ingredient.of(TCItems.TREASURE_MAGNET.get()));
        workshop(output, "hand_warmer", TCItems.HAND_WARMER,
                Ingredient.of(Items.RED_WOOL),
                Ingredient.of(Items.WHITE_WOOL),
                Ingredient.of(Items.LEATHER));
        workshop(output, "hero_shield", TCItems.HERO_SHIELD, Ingredient.of(TCItems.PALADINS_SHIELD.get()), Ingredient.of(TCItems.FLESH_KNUCKLES.get()));
        workshop(output, "honey_balloon", TCItems.HONEY_BALLOON, Ingredient.of(TCItems.HONEY_COMB.get()), Ingredient.of(TCItems.SHINY_RED_BALLOON.get()));
        workshop(output, "jellyfish_diving_gear", TCItems.JELLYFISH_DIVING_GEAR, Ingredient.of(TCItems.DIVING_GEAR.get()), Ingredient.of(TCItems.JELLYFISH_NECKLACE.get()));
        workshop(output, "lava_waders_from_lava_charm", TCItems.LAVA_WADERS,
                Ingredient.of(TCItems.OBSIDIAN_WATER_WALKING_BOOTS.get()),
                Ingredient.of(TCItems.OBSIDIAN_ROSE.get()),
                Ingredient.of(TCItems.LAVA_CHARM.get()));
        workshop(output, "lava_waders_from_molten_charm", TCItems.LAVA_WADERS,
                Ingredient.of(TCItems.OBSIDIAN_WATER_WALKING_BOOTS.get()),
                Ingredient.of(TCItems.OBSIDIAN_ROSE.get()),
                Ingredient.of(TCItems.MOLTEN_CHARM.get()));
        workshop(output, "lava_waders_from_molten_skull_rose", TCItems.LAVA_WADERS, Ingredient.of(TCItems.WATER_WALKING_BOOTS.get()), Ingredient.of(TCItems.MOLTEN_SKULL_ROSE.get()));
        workshop(output, "lava_waders_from_obsidian_water_walking_boots", TCItems.LAVA_WADERS, Ingredient.of(TCItems.OBSIDIAN_WATER_WALKING_BOOTS.get()), Ingredient.of(TCItems.MOLTEN_SKULL_ROSE.get()));
        workshop(output, "lava_waders_from_water_walking_boots", TCItems.LAVA_WADERS,
                Ingredient.of(TCItems.WATER_WALKING_BOOTS.get()),
                Ingredient.of(TCItems.OBSIDIAN_ROSE.get()),
                Ingredient.of(TCItems.MOLTEN_CHARM.get()));
        workshop(output, "lightning_boots", TCItems.LIGHTNING_BOOTS,
                Ingredient.of(TCItems.SPECTRE_BOOTS.get()),
                Ingredient.of(TCItems.AGLET.get()),
                Ingredient.of(TCItems.ANKLET_OF_THE_WIND.get()));
        workshop(output, "magma_skull", TCItems.MAGMA_SKULL, Ingredient.of(TCItems.LAVA_CHARM.get()), Ingredient.of(TCItems.OBSIDIAN_SKULL.get()));
        workshop(output, "master_ninja_gear", TCItems.MASTER_NINJA_GEAR,
                Ingredient.of(TCItems.BLACK_BELT.get()),
                Ingredient.of(TCItems.TIGER_CLIMBING_GEAR.get()),
                Ingredient.of(TCItems.TABI.get()));
        workshop(output, "mechanical_glove", TCItems.MECHANICAL_GLOVE, Ingredient.of(TCItems.POWER_GLOVE.get()), Ingredient.of(TCItems.AVENGER_EMBLEM.get()));
        workshop(output, "molten_charm", TCItems.MOLTEN_CHARM, Ingredient.of(TCItems.LAVA_CHARM.get()), Ingredient.of(TCItems.OBSIDIAN_SKULL.get()));
        workshop(output, "molten_quiver", TCItems.MOLTEN_QUIVER, Ingredient.of(TCItems.MAGIC_QUIVER.get()), Ingredient.of(TCItems.MAGMA_STONE.get()));
        workshop(output, "molten_skull_rose_from_lava_charm", TCItems.MOLTEN_SKULL_ROSE, Ingredient.of(TCItems.OBSIDIAN_SKULL_ROSE.get()), Ingredient.of(TCItems.LAVA_CHARM.get()));
        workshop(output, "molten_skull_rose_from_obsidian_rose", TCItems.MOLTEN_SKULL_ROSE, Ingredient.of(TCItems.MAGMA_SKULL.get()), Ingredient.of(TCItems.OBSIDIAN_ROSE.get()));
        workshop(output, "molten_skull_rose_from_obsidian_skull_rose", TCItems.MOLTEN_SKULL_ROSE, Ingredient.of(TCItems.MAGMA_SKULL.get()), Ingredient.of(TCItems.OBSIDIAN_SKULL_ROSE.get()));
        workshop(output, "moon_shell", TCItems.MOON_SHELL, Ingredient.of(TCItems.MOON_CHARM.get()), Ingredient.of(TCItems.NEPTUNES_SHELL.get()));
        workshop(output, "nutrient_solution", TCItems.NUTRIENT_SOLUTION, Ingredient.of(TCItems.VITAMINS.get()), Ingredient.of(TCItems.ENERGY_BAR.get()));
        workshop(output, "obsidian_horseshoe", TCItems.OBSIDIAN_HORSESHOE, Ingredient.of(TCItems.LUCKY_HORSESHOE.get()), Ingredient.of(TCItems.OBSIDIAN_SKULL.get()));
        workshop(output, "obsidian_shield", TCItems.OBSIDIAN_SHIELD, Ingredient.of(TCItems.COBALT_SHIELD.get()), Ingredient.of(TCItems.OBSIDIAN_SKULL.get()));
        workshop(output, "obsidian_skull_rose", TCItems.OBSIDIAN_SKULL_ROSE, Ingredient.of(TCItems.OBSIDIAN_ROSE.get()), Ingredient.of(TCItems.OBSIDIAN_SKULL.get()));
        workshop(output, "obsidian_water_walking_boots", TCItems.OBSIDIAN_WATER_WALKING_BOOTS, Ingredient.of(TCItems.WATER_WALKING_BOOTS.get()), Ingredient.of(TCItems.OBSIDIAN_SKULL.get()));
        workshop(output, "pda", TCItems.PDA,
                Ingredient.of(TCItems.FISH_FINDER.get()),
                Ingredient.of(TCItems.GOBLIN_TECH.get()),
                Ingredient.of(TCItems.REK_3000.get()),
                Ingredient.of(TCItems.GPS.get()));
        workshop(output, "pink_horseshoe_balloon", TCItems.PINK_HORSESHOE_BALLOON, Ingredient.of(TCItems.SHARKRON_BALLOON.get()), Ingredient.of(TCItems.LUCKY_HORSESHOE.get()));
        workshop(output, "power_glove", TCItems.POWER_GLOVE, Ingredient.of(TCItems.FERAL_CLAWS.get()), Ingredient.of(TCItems.TITAN_GLOVE.get()));
        workshop(output, "recon_scope", TCItems.RECON_SCOPE, Ingredient.of(TCItems.SNIPER_SCOPE.get()), Ingredient.of(TCItems.PUTRID_SCENT.get()));
        workshop(output, "rek_3000", TCItems.REK_3000,
                Ingredient.of(TCItems.RADAR.get()),
                Ingredient.of(TCItems.LIFE_FORM_ANALYZER.get()),
                Ingredient.of(TCItems.TALLY_COUNTER.get()));
        workshop(output, "sandstorm_in_a_balloon", TCItems.SANDSTORM_IN_A_BALLOON, Ingredient.of(TCItems.SANDSTORM_IN_A_BOTTLE.get()), Ingredient.of(TCItems.SHINY_RED_BALLOON.get()));
        workshop(output, "searchlight", TCItems.SEARCHLIGHT, Ingredient.of(TCItems.BLINDFOLD.get()), Ingredient.of(TCItems.FLASHLIGHT.get()));
        workshop(output, "sharkron_balloon", TCItems.SHARKRON_BALLOON, Ingredient.of(TCItems.TSUNAMI_IN_A_BOTTLE.get()), Ingredient.of(TCItems.BALLOON_PUFFERFISH.get()));
        workshop(output, "sniper_scope", TCItems.SNIPER_SCOPE, Ingredient.of(TCItems.RIFLE_SCOPE.get()), Ingredient.of(TCItems.DESTROYER_EMBLEM.get()));
        workshop(output, "spectre_boots_from_dunerider_boots", TCItems.SPECTRE_BOOTS, Ingredient.of(TCItems.DUNERIDER_BOOTS.get()), Ingredient.of(TCItems.ROCKET_BOOTS.get()));
        workshop(output, "spectre_boots_from_flurry_boots", TCItems.SPECTRE_BOOTS, Ingredient.of(TCItems.FLURRY_BOOTS.get()), Ingredient.of(TCItems.ROCKET_BOOTS.get()));
        workshop(output, "spectre_boots_from_hermes_boots", TCItems.SPECTRE_BOOTS, Ingredient.of(TCItems.HERMES_BOOTS.get()), Ingredient.of(TCItems.ROCKET_BOOTS.get()));
        workshop(output, "spectre_boots_from_sailfish_boots", TCItems.SPECTRE_BOOTS, Ingredient.of(TCItems.SAILFISH_BOOTS.get()), Ingredient.of(TCItems.ROCKET_BOOTS.get()));
        workshop(output, "stalkers_quiver", TCItems.STALKERS_QUIVER, Ingredient.of(TCItems.PUTRID_SCENT.get()), Ingredient.of(TCItems.MAGIC_QUIVER.get()));
        workshop(output, "star_veil", TCItems.STAR_VEIL, Ingredient.of(TCItems.STAR_CLOAK.get()), Ingredient.of(TCItems.CROSS_NECKLACE.get()));
        workshop(output, "step_stool", TCItems.STEP_STOOL, AmountIngredient.of(2, Items.STICK), AmountIngredient.of(5, Items.SCAFFOLDING));
        workshop(output, "stinger_necklace", TCItems.STINGER_NECKLACE, Ingredient.of(TCItems.HONEY_COMB.get()), Ingredient.of(TCItems.SHARK_TOOTH_NECKLACE.get()));
        workshop(output, "sweetheart_necklace", TCItems.SWEETHEART_NECKLACE, Ingredient.of(TCItems.PANIC_NECKLACE.get()), Ingredient.of(TCItems.HONEY_COMB.get()));
        workshop(output, "terraspark_boots", TCItems.TERRASPARK_BOOTS, Ingredient.of(TCItems.FROSTSPARK_BOOTS.get()), Ingredient.of(TCItems.LAVA_WADERS.get()));
        workshop(output, "the_plan", TCItems.THE_PLAN, Ingredient.of(TCItems.FAST_CLOCK.get()), Ingredient.of(TCItems.TRIFOLD_MAP.get()));
        workshop(output, "tiger_climbing_gear", TCItems.TIGER_CLIMBING_GEAR, Ingredient.of(TCItems.CLIMBING_CLAWS.get()), Ingredient.of(TCItems.SHOE_SPIKES.get()));
        workshop(output, "white_horseshoe_balloon", TCItems.WHITE_HORSESHOE_BALLOON, Ingredient.of(TCItems.BLIZZARD_IN_A_BALLOON.get()), Ingredient.of(TCItems.LUCKY_HORSESHOE.get()));
        workshop(output, "yellow_horseshoe_balloon", TCItems.YELLOW_HORSESHOE_BALLOON, Ingredient.of(TCItems.SANDSTORM_IN_A_BALLOON.get()), Ingredient.of(TCItems.LUCKY_HORSESHOE.get()));

        extraStepStool(output, "extra_step_stool");
        extraStepStool(output, "extra_hand_of_creation");

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TCItems.COPPER_WATCH.get())
                .pattern(" # ")
                .pattern("cRc")
                .pattern(" c ")
                .define('#', Items.CHAIN)
                .define('c', Tags.Items.INGOTS_COPPER)
                .define('R', Items.REDSTONE)
                .unlockedBy("has", has(TCItems.COPPER_WATCH.get()))
                .save(output, key("copper_watch"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TCItems.TIN_WATCH.get())
                .pattern(" # ")
                .pattern("cRc")
                .pattern(" c ")
                .define('#', Items.CHAIN)
                .define('c', LibTags.Items.INGOTS_TIN)
                .define('R', Items.REDSTONE)
                .unlockedBy("has", has(TCItems.TIN_WATCH.get()))
                .save(output, key("tin_watch"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TCItems.SILVER_WATCH.get())
                .pattern(" # ")
                .pattern("cRc")
                .pattern(" c ")
                .define('#', Items.CHAIN)
                .define('c', LibTags.Items.INGOTS_SILVER)
                .define('R', Items.REDSTONE)
                .unlockedBy("has", has(TCItems.SILVER_WATCH.get()))
                .save(output, key("silver_watch"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TCItems.TUNGSTEN_WATCH.get())
                .pattern(" # ")
                .pattern("cRc")
                .pattern(" c ")
                .define('#', Items.CHAIN)
                .define('c', LibTags.Items.INGOTS_TUNGSTEN)
                .define('R', Items.REDSTONE)
                .unlockedBy("has", has(TCItems.TUNGSTEN_WATCH.get()))
                .save(output, key("tungsten_watch"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TCItems.GOLD_WATCH.get())
                .pattern(" # ")
                .pattern("cRc")
                .pattern(" c ")
                .define('#', Items.CHAIN)
                .define('c', Tags.Items.INGOTS_GOLD)
                .define('R', Items.REDSTONE)
                .unlockedBy("has", has(TCItems.GOLD_WATCH.get()))
                .save(output, key("gold_watch"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TCItems.PLATINUM_WATCH.get())
                .pattern(" # ")
                .pattern("cRc")
                .pattern(" c ")
                .define('#', Items.CHAIN)
                .define('c', LibTags.Items.INGOTS_PLATINUM)
                .define('R', Items.REDSTONE)
                .unlockedBy("has", has(TCItems.PLATINUM_WATCH.get()))
                .save(output, key("platinum_watch"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TCItems.WOOL_SHOES.get())
                .pattern("a a")
                .pattern("a a")
                .define('a', ItemTags.WOOL)
                .unlockedBy("has", has(TCItems.WOOL_SHOES.get()))
                .save(output, key("wool_shoes"));

        // conditional
        RecipeOutput conditional = new RecipeOutput() {
            @Override
            public Advancement.Builder advancement() {
                return output.advancement();
            }

            @Override
            public void accept(ResourceLocation resourceLocation, Recipe<?> recipe, @Nullable AdvancementHolder advancementHolder, ICondition... iConditions) {
                output.accept(resourceLocation, recipe, advancementHolder, TCDataGenerator.CONFLUENCE_NOT_LOADED);
            }
        };

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TCItems.ANKLET_OF_THE_WIND.get())
                .pattern(" # ")
                .pattern("#S#")
                .pattern(" # ")
                .define('#', Items.VINE)
                .define('S', Items.SPORE_BLOSSOM)
                .unlockedBy("has", has(TCItems.ANKLET_OF_THE_WIND.get()))
                .save(conditional, key("anklet_of_the_wind"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TCItems.BLIZZARD_IN_A_BOTTLE.get())
                .pattern(" # ")
                .pattern("#S#")
                .pattern(" # ")
                .define('#', Items.BLUE_ICE)
                .define('S', TCItems.CLOUD_IN_A_BOTTLE.get())
                .unlockedBy("has", has(TCItems.BLIZZARD_IN_A_BOTTLE.get()))
                .save(conditional, key("blizzard_in_a_bottle"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TCItems.DPS_METER.get())
                .pattern(" i ")
                .pattern("iti")
                .pattern(" i ")
                .define('i', Items.IRON_INGOT)
                .define('t', Items.TARGET)
                .unlockedBy("has", has(TCItems.DPS_METER.get()))
                .save(conditional, key("dps_meter"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TCItems.ICE_SKATES.get())
                .pattern(" # ")
                .pattern("#S#")
                .pattern(" # ")
                .define('#', Items.BLUE_ICE)
                .define('S', TCItems.HERMES_BOOTS.get())
                .unlockedBy("has", has(TCItems.ICE_SKATES.get()))
                .save(conditional, key("ice_skates"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TCItems.MAGIC_MIRROR.get())
                .pattern("gGg")
                .pattern("GDG")
                .pattern("gGg")
                .define('g', Items.GOLD_INGOT)
                .define('G', Items.GLASS)
                .define('D', Items.DIAMOND)
                .unlockedBy("has", has(TCItems.MAGIC_MIRROR.get()))
                .save(conditional, key("magic_mirror_from_gold_ingot"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TCItems.MAGIC_MIRROR.get())
                .pattern("gGg")
                .pattern("GDG")
                .pattern("gGg")
                .define('g', LibTags.Items.INGOTS_PLATINUM)
                .define('G', Items.GLASS)
                .define('D', Items.DIAMOND)
                .unlockedBy("has", has(TCItems.MAGIC_MIRROR.get()))
                .save(conditional, key("magic_mirror_from_platinum_ingot"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TCItems.OBSIDIAN_SKULL.get())
                .pattern("###")
                .pattern("#W#")
                .pattern("###")
                .define('#', Items.OBSIDIAN)
                .define('W', Items.WITHER_SKELETON_SKULL)
                .unlockedBy("has", has(TCItems.OBSIDIAN_SKULL.get()))
                .save(conditional, key("obsidian_skull"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TCItems.ROCKET_BOOTS.get())
                .pattern("#I#")
                .pattern("i#i")
                .define('#', Items.FIRE_CHARGE)
                .define('I', Items.IRON_BOOTS)
                .define('i', Items.IRON_INGOT)
                .unlockedBy("has", has(TCItems.ROCKET_BOOTS.get()))
                .save(conditional, key("rocket_boots"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TCItems.STOPWATCH.get())
                .pattern("ici")
                .pattern("iri")
                .pattern("iii")
                .define('c', Items.CHAIN)
                .define('r', Items.REDSTONE)
                .define('i', Items.IRON_INGOT)
                .unlockedBy("has", has(TCItems.STOPWATCH.get()))
                .save(conditional, key("stopwatch"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TCItems.WORKSHOP.get())
                .pattern("aba")
                .pattern("ada")
                .pattern("c c")
                .define('a', Items.RED_WOOL)
                .define('b', Items.CRAFTING_TABLE)
                .define('c', Items.STICK)
                .define('d', Items.BOOKSHELF)
                .unlockedBy("has", has(TCItems.WORKSHOP.get()))
                .save(conditional, key("workshop"));
        workshop(conditional, "avenger_emblem", TCItems.AVENGER_EMBLEM,
                Ingredient.of(TCItems.WARRIOR_EMBLEM.get()),
                Ingredient.of(TCItems.SORCERER_EMBLEM.get()),
                Ingredient.of(TCItems.RANGER_EMBLEM.get()));
        workshop(conditional, "architect_gizmo_pack", TCItems.ARCHITECT_GIZMO_PACK,
                Ingredient.of(TCItems.BRICK_LAYER.get()),
                Ingredient.of(TCItems.EXTENDO_GRIP.get()),
                Ingredient.of(TCItems.PORTABLE_CEMENT_MIXER.get()));
        workshop(conditional, "ankh_charm", TCItems.ANKH_CHARM,
                Ingredient.of(TCItems.DETOXIFICATION_CAPSULE.get()),
                Ingredient.of(TCItems.NUTRIENT_SOLUTION.get()),
                Ingredient.of(TCItems.SEARCHLIGHT.get()),
                Ingredient.of(TCItems.THE_PLAN.get()),
                Ingredient.of(TCItems.EXPLORERS_EQUIPMENT.get()));
    }

    protected void workshop(RecipeOutput output, ItemLike result, Ingredient... ingredients) {
        workshop(output, result, EnvironmentLevelAccess.Matcher.EMPTY, ingredients);
    }

    protected void workshop(RecipeOutput output, ItemLike result, EnvironmentLevelAccess.Matcher environment, Ingredient... ingredients) {
        output.accept(BuiltInRegistries.ITEM.getKey(result.asItem()), new WorkshopRecipe(result.asItem().getDefaultInstance(), NonNullList.of(Ingredient.EMPTY, ingredients), environment), null);
    }

    protected void workshop(RecipeOutput output, String name, ItemLike result, Ingredient... ingredients) {
        workshop(output, name, result, EnvironmentLevelAccess.Matcher.EMPTY, ingredients);
    }

    protected void workshop(RecipeOutput output, String name, ItemLike result, EnvironmentLevelAccess.Matcher environment, Ingredient... ingredients) {
        output.accept(key(name), new WorkshopRecipe(result.asItem().getDefaultInstance(), NonNullList.of(Ingredient.EMPTY, ingredients), environment), null);
    }

    private void extraStepStool(RecipeOutput output, String name) {
        output.accept(key(name), ExtraStepStoolRecipe.getInstance(), null);
    }

    private static ResourceLocation key(String path) {
        return TerraCurio.asResource(path);
    }
}
