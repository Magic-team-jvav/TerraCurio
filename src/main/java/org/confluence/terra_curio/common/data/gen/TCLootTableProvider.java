package org.confluence.terra_curio.common.data.gen;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.ExplosionCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemKilledByPlayerCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import org.confluence.terra_curio.TerraCurio;
import org.confluence.terra_curio.common.init.TCItems;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class TCLootTableProvider extends LootTableProvider {
    public TCLootTableProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, Set.of(), List.of(), registries);
    }

    @Override
    public List<SubProviderEntry> getTables() {
        return List.of(new SubProviderEntry(provider -> TCLootTableProvider::buildTables, LootContextParamSets.ALL_PARAMS));
    }

    private static void buildTables(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> tables) {
        tables.accept(key("blocks/workshop"), loot_blocks_workshop());
        tables.accept(key("with/archaeology/common"), loot_with_archaeology_common());
        tables.accept(key("with/blocks/moss_block"), loot_with_blocks_moss_block());
        tables.accept(key("with/chests/abandoned_mineshaft"), loot_with_chests_abandoned_mineshaft());
        tables.accept(key("with/chests/ancient_city"), loot_with_chests_ancient_city());
        tables.accept(key("with/chests/ancient_city_ice_box"), loot_with_chests_ancient_city_ice_box());
        tables.accept(key("with/chests/bastion_treasure"), loot_with_chests_bastion_treasure());
        tables.accept(key("with/chests/desert_pyramid"), loot_with_chests_desert_pyramid());
        tables.accept(key("with/chests/end_city_treasure"), loot_with_chests_end_city_treasure());
        tables.accept(key("with/chests/igloo_chest"), loot_with_chests_igloo_chest());
        tables.accept(key("with/chests/jungle_temple"), loot_with_chests_jungle_temple());
        tables.accept(key("with/chests/nether_bridge"), loot_with_chests_nether_bridge());
        tables.accept(key("with/chests/shipwreck_supply"), loot_with_chests_shipwreck_supply());
        tables.accept(key("with/chests/shipwreck_treasure"), loot_with_chests_shipwreck_treasure());
        tables.accept(key("with/chests/simple_dungeon"), loot_with_chests_simple_dungeon());
        tables.accept(key("with/chests/stronghold_corridor"), loot_with_chests_stronghold_corridor());
        tables.accept(key("with/chests/stronghold_crossing"), loot_with_chests_stronghold_crossing());
        tables.accept(key("with/chests/stronghold_library"), loot_with_chests_stronghold_library());
        tables.accept(key("with/chests/underwater_ruin_big"), loot_with_chests_underwater_ruin_big());
        tables.accept(key("with/chests/underwater_ruin_small"), loot_with_chests_underwater_ruin_small());
        tables.accept(key("with/chests/village/village_armorer"), loot_with_chests_village_village_armorer());
        tables.accept(key("with/chests/village/village_fisher"), loot_with_chests_village_village_fisher());
        tables.accept(key("with/chests/village/village_plains_house"), loot_with_chests_village_village_plains_house());
        tables.accept(key("with/chests/village/village_toolsmith"), loot_with_chests_village_village_toolsmith());
        tables.accept(key("with/chests/woodland_mansion"), loot_with_chests_woodland_mansion());
        tables.accept(key("with/entities/bat"), loot_with_entities_bat());
        tables.accept(key("with/entities/bee"), loot_with_entities_bee());
        tables.accept(key("with/entities/blaze"), loot_with_entities_blaze());
        tables.accept(key("with/entities/cave_spider"), loot_with_entities_cave_spider());
        tables.accept(key("with/entities/creeper"), loot_with_entities_creeper());
        tables.accept(key("with/entities/drowned"), loot_with_entities_drowned());
        tables.accept(key("with/entities/elder_guardian"), loot_with_entities_elder_guardian());
        tables.accept(key("with/entities/ender_dragon"), loot_with_entities_ender_dragon());
        tables.accept(key("with/entities/enderman"), loot_with_entities_enderman());
        tables.accept(key("with/entities/evoker"), loot_with_entities_evoker());
        tables.accept(key("with/entities/frog"), loot_with_entities_frog());
        tables.accept(key("with/entities/ghast"), loot_with_entities_ghast());
        tables.accept(key("with/entities/glow_squid"), loot_with_entities_glow_squid());
        tables.accept(key("with/entities/guardian"), loot_with_entities_guardian());
        tables.accept(key("with/entities/husk"), loot_with_entities_husk());
        tables.accept(key("with/entities/iron_golem"), loot_with_entities_iron_golem());
        tables.accept(key("with/entities/magma_cube"), loot_with_entities_magma_cube());
        tables.accept(key("with/entities/phantom"), loot_with_entities_phantom());
        tables.accept(key("with/entities/piglin_brute"), loot_with_entities_piglin_brute());
        tables.accept(key("with/entities/ravager"), loot_with_entities_ravager());
        tables.accept(key("with/entities/shulker"), loot_with_entities_shulker());
        tables.accept(key("with/entities/skeleton"), loot_with_entities_skeleton());
        tables.accept(key("with/entities/skeleton_horse"), loot_with_entities_skeleton_horse());
        tables.accept(key("with/entities/slime"), loot_with_entities_slime());
        tables.accept(key("with/entities/spider"), loot_with_entities_spider());
        tables.accept(key("with/entities/stray"), loot_with_entities_stray());
        tables.accept(key("with/entities/turtle"), loot_with_entities_turtle());
        tables.accept(key("with/entities/vindicator"), loot_with_entities_vindicator());
        tables.accept(key("with/entities/warden"), loot_with_entities_warden());
        tables.accept(key("with/entities/witch"), loot_with_entities_witch());
        tables.accept(key("with/entities/wither"), loot_with_entities_wither());
        tables.accept(key("with/entities/wither_skeleton"), loot_with_entities_wither_skeleton());
        tables.accept(key("with/entities/zombie"), loot_with_entities_zombie());
        tables.accept(key("with/entities/zombie_villager"), loot_with_entities_zombie_villager());
        tables.accept(key("with/entities/zombified_piglin"), loot_with_entities_zombified_piglin());
        tables.accept(key("with/gameplay/cat_morning_gift"), loot_with_gameplay_cat_morning_gift());
        tables.accept(key("with/gameplay/hero_of_the_village/librarian_gift"), loot_with_gameplay_hero_of_the_village_librarian_gift());
    }

    private static ResourceKey<LootTable> key(String path) {
        return ResourceKey.create(Registries.LOOT_TABLE, TerraCurio.asResource(path));
    }

    private static LootTable.Builder loot_blocks_workshop() {
        LootPool.Builder pool0 = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1.0f))
                .add(LootItem.lootTableItem(TCItems.WORKSHOP.get()))
                .when(ExplosionCondition.survivesExplosion());

        return LootTable.lootTable().withPool(pool0);
    }

    private static LootTable.Builder loot_with_archaeology_common() {
        LootPool.Builder pool0 = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1f))
                .add(LootItem.lootTableItem(TCItems.ANCIENT_CHISEL.get()));

        return LootTable.lootTable().withPool(pool0);
    }

    private static LootTable.Builder loot_with_blocks_moss_block() {
        LootPool.Builder pool0 = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1f))
                .add(LootItem.lootTableItem(TCItems.FLOWER_BOOTS.get()))
                .when(LootItemRandomChanceCondition.randomChance(0.005f));

        return LootTable.lootTable().withPool(pool0);
    }

    private static LootTable.Builder loot_with_chests_abandoned_mineshaft() {
        LootPool.Builder pool0 = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1f))
                .add(LootItem.lootTableItem(TCItems.TITAN_GLOVE.get()))
                .when(LootItemRandomChanceCondition.randomChance(0.25f));

        LootPool.Builder pool1 = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1f))
                .add(LootItem.lootTableItem(TCItems.CLOUD_IN_A_BOTTLE.get()))
                .when(LootItemRandomChanceCondition.randomChance(0.25f));

        LootTable.Builder table = LootTable.lootTable();
        table = table.withPool(pool0);
        table = table.withPool(pool1);
        return table;
    }

    private static LootTable.Builder loot_with_chests_ancient_city() {
        LootPool.Builder pool0 = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1f))
                .add(LootItem.lootTableItem(TCItems.LUCKY_HORSESHOE.get())
                        .when(LootItemRandomChanceCondition.randomChance(0.5f))
                );

        LootPool.Builder pool1 = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1f))
                .add(LootItem.lootTableItem(TCItems.TREASURE_MAGNET.get())
                        .when(LootItemRandomChanceCondition.randomChance(0.5f))
                );

        LootPool.Builder pool2 = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1f))
                .add(LootItem.lootTableItem(TCItems.BLINDFOLD.get())
                        .when(LootItemRandomChanceCondition.randomChance(0.4f))
                );

        LootPool.Builder pool3 = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1f))
                .add(LootItem.lootTableItem(TCItems.DEODORIZER.get())
                        .when(LootItemRandomChanceCondition.randomChance(0.4f))
                );

        LootTable.Builder table = LootTable.lootTable();
        table = table.withPool(pool0);
        table = table.withPool(pool1);
        table = table.withPool(pool2);
        table = table.withPool(pool3);
        return table;
    }

    private static LootTable.Builder loot_with_chests_ancient_city_ice_box() {
        LootPool.Builder pool0 = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1f))
                .add(LootItem.lootTableItem(TCItems.BLINDFOLD.get()));

        return LootTable.lootTable().withPool(pool0);
    }

    private static LootTable.Builder loot_with_chests_bastion_treasure() {
        LootPool.Builder pool0 = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1f))
                .add(LootItem.lootTableItem(TCItems.OBSIDIAN_ROSE.get()));

        return LootTable.lootTable().withPool(pool0);
    }

    private static LootTable.Builder loot_with_chests_desert_pyramid() {
        LootPool.Builder pool0 = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1f))
                .add(LootItem.lootTableItem(TCItems.EXTENDO_GRIP.get())
                        .when(LootItemRandomChanceCondition.randomChance(0.5f))
                );

        LootPool.Builder pool1 = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1f))
                .add(LootItem.lootTableItem(TCItems.SANDSTORM_IN_A_BOTTLE.get())
                        .when(LootItemRandomChanceCondition.randomChance(0.3f))
                );

        LootPool.Builder pool2 = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1f))
                .add(LootItem.lootTableItem(TCItems.LUCKY_HORSESHOE.get())
                        .when(LootItemRandomChanceCondition.randomChance(0.02f))
                );

        LootPool.Builder pool3 = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1f))
                .add(LootItem.lootTableItem(TCItems.TREASURE_MAGNET.get())
                        .when(LootItemRandomChanceCondition.randomChance(0.02f))
                );

        LootPool.Builder pool4 = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1f))
                .add(LootItem.lootTableItem(TCItems.FLYING_CARPET.get())
                        .when(LootItemRandomChanceCondition.randomChance(0.1f))
                );

        LootTable.Builder table = LootTable.lootTable();
        table = table.withPool(pool0);
        table = table.withPool(pool1);
        table = table.withPool(pool2);
        table = table.withPool(pool3);
        table = table.withPool(pool4);
        return table;
    }

    private static LootTable.Builder loot_with_chests_end_city_treasure() {
        LootPool.Builder pool0 = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1f))
                .add(LootItem.lootTableItem(TCItems.MAGILUMINESCENCE.get()))
                .when(LootItemRandomChanceCondition.randomChance(0.5f));

        LootPool.Builder pool1 = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1f))
                .add(LootItem.lootTableItem(TCItems.LUCKY_HORSESHOE.get())
                        .when(LootItemRandomChanceCondition.randomChance(0.02f))
                );

        LootPool.Builder pool2 = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1f))
                .add(LootItem.lootTableItem(TCItems.TREASURE_MAGNET.get())
                        .when(LootItemRandomChanceCondition.randomChance(0.02f))
                );

        LootTable.Builder table = LootTable.lootTable();
        table = table.withPool(pool0);
        table = table.withPool(pool1);
        table = table.withPool(pool2);
        return table;
    }

    private static LootTable.Builder loot_with_chests_igloo_chest() {
        LootPool.Builder pool0 = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1f))
                .add(LootItem.lootTableItem(TCItems.FLURRY_BOOTS.get()));

        LootPool.Builder pool1 = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1f))
                .add(LootItem.lootTableItem(TCItems.BLIZZARD_IN_A_BOTTLE.get())
                        .when(LootItemRandomChanceCondition.randomChance(0.3f))
                );

        LootTable.Builder table = LootTable.lootTable();
        table = table.withPool(pool0);
        table = table.withPool(pool1);
        return table;
    }

    private static LootTable.Builder loot_with_chests_jungle_temple() {
        LootPool.Builder pool0 = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1f))
                .add(LootItem.lootTableItem(TCItems.EYE_OF_THE_GOLEM.get()));

        LootPool.Builder pool1 = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1f))
                .add(LootItem.lootTableItem(TCItems.FERAL_CLAWS.get()));

        LootPool.Builder pool2 = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1f))
                .add(LootItem.lootTableItem(TCItems.LUCKY_HORSESHOE.get())
                        .when(LootItemRandomChanceCondition.randomChance(0.02f))
                );

        LootPool.Builder pool3 = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1f))
                .add(LootItem.lootTableItem(TCItems.TREASURE_MAGNET.get())
                        .when(LootItemRandomChanceCondition.randomChance(0.02f))
                );

        LootTable.Builder table = LootTable.lootTable();
        table = table.withPool(pool0);
        table = table.withPool(pool1);
        table = table.withPool(pool2);
        table = table.withPool(pool3);
        return table;
    }

    private static LootTable.Builder loot_with_chests_nether_bridge() {
        LootPool.Builder pool0 = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1f))
                .add(LootItem.lootTableItem(TCItems.TABI.get())
                        .when(LootItemRandomChanceCondition.randomChance(0.1f))
                );

        return LootTable.lootTable().withPool(pool0);
    }

    private static LootTable.Builder loot_with_chests_shipwreck_supply() {
        LootPool.Builder pool0 = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1f))
                .add(LootItem.lootTableItem(TCItems.TSUNAMI_IN_A_BOTTLE.get()))
                .when(LootItemRandomChanceCondition.randomChance(0.3f));

        LootPool.Builder pool1 = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1f))
                .add(LootItem.lootTableItem(TCItems.AGLET.get()))
                .when(LootItemRandomChanceCondition.randomChance(0.3f));

        LootPool.Builder pool2 = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1f))
                .add(LootItem.lootTableItem(TCItems.FLIPPER.get()))
                .when(LootItemRandomChanceCondition.randomChance(0.3f));

        LootPool.Builder pool3 = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1f))
                .add(LootItem.lootTableItem(TCItems.SAILFISH_BOOTS.get()))
                .when(LootItemRandomChanceCondition.randomChance(0.3f));

        LootTable.Builder table = LootTable.lootTable();
        table = table.withPool(pool0);
        table = table.withPool(pool1);
        table = table.withPool(pool2);
        table = table.withPool(pool3);
        return table;
    }

    private static LootTable.Builder loot_with_chests_shipwreck_treasure() {
        LootPool.Builder pool0 = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1f))
                .add(LootItem.lootTableItem(TCItems.TSUNAMI_IN_A_BOTTLE.get()))
                .when(LootItemRandomChanceCondition.randomChance(0.3f));

        LootPool.Builder pool1 = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1f))
                .add(LootItem.lootTableItem(TCItems.AGLET.get()))
                .when(LootItemRandomChanceCondition.randomChance(0.3f));

        LootPool.Builder pool2 = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1f))
                .add(LootItem.lootTableItem(TCItems.FLIPPER.get()))
                .when(LootItemRandomChanceCondition.randomChance(0.3f));

        LootPool.Builder pool3 = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1f))
                .add(LootItem.lootTableItem(TCItems.SAILFISH_BOOTS.get()))
                .when(LootItemRandomChanceCondition.randomChance(0.3f));

        LootTable.Builder table = LootTable.lootTable();
        table = table.withPool(pool0);
        table = table.withPool(pool1);
        table = table.withPool(pool2);
        table = table.withPool(pool3);
        return table;
    }

    private static LootTable.Builder loot_with_chests_simple_dungeon() {
        LootPool.Builder pool0 = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(2f))
                .add(LootItem.lootTableItem(TCItems.PANIC_NECKLACE.get())
                        .when(LootItemRandomChanceCondition.randomChance(0.5f))
                )
                .add(LootItem.lootTableItem(TCItems.PUTRID_SCENT.get())
                        .when(LootItemRandomChanceCondition.randomChance(0.5f))
                )
                .add(LootItem.lootTableItem(TCItems.SHACKLE.get())
                        .when(LootItemRandomChanceCondition.randomChance(0.5f))
                )
                .add(LootItem.lootTableItem(TCItems.CLOUD_IN_A_BOTTLE.get())
                        .when(LootItemRandomChanceCondition.randomChance(0.25f))
                );

        return LootTable.lootTable().withPool(pool0);
    }

    private static LootTable.Builder loot_with_chests_stronghold_corridor() {
        LootPool.Builder pool0 = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1f))
                .add(LootItem.lootTableItem(TCItems.FLASHLIGHT.get()))
                .when(LootItemRandomChanceCondition.randomChance(0.25f));

        LootPool.Builder pool1 = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1f))
                .add(LootItem.lootTableItem(TCItems.SHOT_PUT.get()))
                .when(LootItemRandomChanceCondition.randomChance(0.25f));

        LootTable.Builder table = LootTable.lootTable();
        table = table.withPool(pool0);
        table = table.withPool(pool1);
        return table;
    }

    private static LootTable.Builder loot_with_chests_stronghold_crossing() {
        LootPool.Builder pool0 = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1f))
                .add(LootItem.lootTableItem(TCItems.FLASHLIGHT.get()))
                .when(LootItemRandomChanceCondition.randomChance(0.25f));

        LootPool.Builder pool1 = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1f))
                .add(LootItem.lootTableItem(TCItems.SHOT_PUT.get()))
                .when(LootItemRandomChanceCondition.randomChance(0.25f));

        LootTable.Builder table = LootTable.lootTable();
        table = table.withPool(pool0);
        table = table.withPool(pool1);
        return table;
    }

    private static LootTable.Builder loot_with_chests_stronghold_library() {
        LootPool.Builder pool0 = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1f))
                .add(LootItem.lootTableItem(TCItems.TRIFOLD_MAP.get()));

        return LootTable.lootTable().withPool(pool0);
    }

    private static LootTable.Builder loot_with_chests_underwater_ruin_big() {
        LootPool.Builder pool0 = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1f))
                .add(LootItem.lootTableItem(TCItems.WATER_WALKING_BOOTS.get()))
                .when(LootItemRandomChanceCondition.randomChance(0.25f));

        return LootTable.lootTable().withPool(pool0);
    }

    private static LootTable.Builder loot_with_chests_underwater_ruin_small() {
        LootPool.Builder pool0 = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1f))
                .add(LootItem.lootTableItem(TCItems.WATER_WALKING_BOOTS.get()))
                .when(LootItemRandomChanceCondition.randomChance(0.25f));

        return LootTable.lootTable().withPool(pool0);
    }

    private static LootTable.Builder loot_with_chests_village_village_armorer() {
        LootPool.Builder pool0 = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1f))
                .add(LootItem.lootTableItem(TCItems.HERMES_BOOTS.get())
                        .when(LootItemRandomChanceCondition.randomChance(1f))
                );

        return LootTable.lootTable().withPool(pool0);
    }

    private static LootTable.Builder loot_with_chests_village_village_fisher() {
        LootPool.Builder pool0 = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1f))
                .add(LootItem.lootTableItem(TCItems.SEXTANT.get())
                        .when(LootItemRandomChanceCondition.randomChance(0.3f))
                )
                .add(LootItem.lootTableItem(TCItems.WEATHER_RADIO.get())
                        .when(LootItemRandomChanceCondition.randomChance(0.3f))
                )
                .add(LootItem.lootTableItem(TCItems.ANGLER_EARRING.get())
                        .when(LootItemRandomChanceCondition.randomChance(0.05f))
                )
                .add(LootItem.lootTableItem(TCItems.FISHERMANS_POCKET_GUIDE.get())
                        .when(LootItemRandomChanceCondition.randomChance(0.3f))
                );

        return LootTable.lootTable().withPool(pool0);
    }

    private static LootTable.Builder loot_with_chests_village_village_plains_house() {
        LootPool.Builder pool0 = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1f))
                .add(LootItem.lootTableItem(TCItems.HERMES_BOOTS.get())
                        .when(LootItemRandomChanceCondition.randomChance(0.4f))
                )
                .add(LootItem.lootTableItem(TCItems.SHINY_RED_BALLOON.get())
                        .when(LootItemRandomChanceCondition.randomChance(0.2f))
                );

        return LootTable.lootTable().withPool(pool0);
    }

    private static LootTable.Builder loot_with_chests_village_village_toolsmith() {
        LootPool.Builder pool0 = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1f))
                .add(LootItem.lootTableItem(TCItems.TOOLBELT.get())
                        .when(LootItemRandomChanceCondition.randomChance(0.6f))
                )
                .add(LootItem.lootTableItem(TCItems.TOOLBOX.get())
                        .when(LootItemRandomChanceCondition.randomChance(0.6f))
                )
                .add(LootItem.lootTableItem(TCItems.PORTABLE_CEMENT_MIXER.get())
                        .when(LootItemRandomChanceCondition.randomChance(0.2f))
                )
                .add(LootItem.lootTableItem(TCItems.BRICK_LAYER.get())
                        .when(LootItemRandomChanceCondition.randomChance(0.2f))
                );

        return LootTable.lootTable().withPool(pool0);
    }

    private static LootTable.Builder loot_with_chests_woodland_mansion() {
        LootPool.Builder pool0 = LootPool.lootPool()
                .setRolls(UniformGenerator.between(1, 2))
                .add(LootItem.lootTableItem(TCItems.STAR_CLOAK.get())
                        .when(LootItemRandomChanceCondition.randomChance(0.2f))
                );

        return LootTable.lootTable().withPool(pool0);
    }

    private static LootTable.Builder loot_with_entities_bat() {
        LootPool.Builder pool0 = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1f))
                .add(LootItem.lootTableItem(TCItems.RADAR.get())
                        .when(LootItemRandomChanceCondition.randomChance(0.5f))
                        .when(LootItemKilledByPlayerCondition.killedByPlayer())
                )
                .add(LootItem.lootTableItem(TCItems.COMPASS.get())
                        .when(LootItemRandomChanceCondition.randomChance(0.2f))
                        .when(LootItemKilledByPlayerCondition.killedByPlayer())
                )
                .add(LootItem.lootTableItem(TCItems.DEPTH_METER.get())
                        .when(LootItemRandomChanceCondition.randomChance(0.2f))
                        .when(LootItemKilledByPlayerCondition.killedByPlayer())
                );

        return LootTable.lootTable().withPool(pool0);
    }

    private static LootTable.Builder loot_with_entities_bee() {
        LootPool.Builder pool0 = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1f))
                .add(LootItem.lootTableItem(TCItems.HIVE_PACK.get())
                        .when(LootItemRandomChanceCondition.randomChance(0.01f))
                        .when(LootItemKilledByPlayerCondition.killedByPlayer())
                )
                .add(LootItem.lootTableItem(TCItems.HONEY_COMB.get())
                        .when(LootItemRandomChanceCondition.randomChance(0.07f))
                        .when(LootItemKilledByPlayerCondition.killedByPlayer())
                );

        return LootTable.lootTable().withPool(pool0);
    }

    private static LootTable.Builder loot_with_entities_blaze() {
        LootPool.Builder pool0 = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1f))
                .add(LootItem.lootTableItem(TCItems.SUN_STONE.get())
                        .when(LootItemRandomChanceCondition.randomChance(0.07f))
                        .when(LootItemKilledByPlayerCondition.killedByPlayer())
                );

        LootPool.Builder pool1 = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1f))
                .add(LootItem.lootTableItem(TCItems.LAVA_CHARM.get())
                        .when(LootItemRandomChanceCondition.randomChance(0.05f))
                        .when(LootItemKilledByPlayerCondition.killedByPlayer())
                );

        LootTable.Builder table = LootTable.lootTable();
        table = table.withPool(pool0);
        table = table.withPool(pool1);
        return table;
    }

    private static LootTable.Builder loot_with_entities_cave_spider() {
        LootPool.Builder pool0 = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1f))
                .add(LootItem.lootTableItem(TCItems.BEZOAR.get()))
                .when(LootItemRandomChanceCondition.randomChance(0.05f));

        LootPool.Builder pool1 = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1f))
                .add(LootItem.lootTableItem(TCItems.SHOE_SPIKES.get()))
                .when(LootItemRandomChanceCondition.randomChance(0.008f));

        LootTable.Builder table = LootTable.lootTable();
        table = table.withPool(pool0);
        table = table.withPool(pool1);
        return table;
    }

    private static LootTable.Builder loot_with_entities_creeper() {
        LootPool.Builder pool0 = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1f))
                .add(LootItem.lootTableItem(TCItems.TALLY_COUNTER.get())
                        .when(LootItemRandomChanceCondition.randomChance(0.07f))
                        .when(LootItemKilledByPlayerCondition.killedByPlayer())
                )
                .add(LootItem.lootTableItem(TCItems.DPS_METER.get())
                        .when(LootItemRandomChanceCondition.randomChance(0.07f))
                        .when(LootItemKilledByPlayerCondition.killedByPlayer())
                );

        return LootTable.lootTable().withPool(pool0);
    }

    private static LootTable.Builder loot_with_entities_drowned() {
        LootPool.Builder pool0 = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1f))
                .add(LootItem.lootTableItem(TCItems.SHARK_TOOTH_NECKLACE.get())
                        .when(LootItemRandomChanceCondition.randomChance(0.08f))
                        .when(LootItemKilledByPlayerCondition.killedByPlayer())
                );

        return LootTable.lootTable().withPool(pool0);
    }

    private static LootTable.Builder loot_with_entities_elder_guardian() {
        LootPool.Builder pool0 = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1f))
                .add(LootItem.lootTableItem(TCItems.HAND_DRILL.get())
                        .when(LootItemRandomChanceCondition.randomChance(1f))
                        .when(LootItemKilledByPlayerCondition.killedByPlayer())
                );

        return LootTable.lootTable().withPool(pool0);
    }

    private static LootTable.Builder loot_with_entities_ender_dragon() {
        LootPool.Builder pool0 = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1f))
                .add(LootItem.lootTableItem(TCItems.PALADINS_SHIELD.get())
                        .when(LootItemRandomChanceCondition.randomChance(1f))
                        .when(LootItemKilledByPlayerCondition.killedByPlayer())
                );

        LootPool.Builder pool1 = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1f))
                .add(LootItem.lootTableItem(TCItems.CELESTIAL_STARBOARD.get())
                        .when(LootItemRandomChanceCondition.randomChance(1f))
                );

        LootTable.Builder table = LootTable.lootTable();
        table = table.withPool(pool0);
        table = table.withPool(pool1);
        return table;
    }

    private static LootTable.Builder loot_with_entities_enderman() {
        LootPool.Builder pool0 = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1f))
                .add(LootItem.lootTableItem(TCItems.RIFLE_SCOPE.get())
                        .when(LootItemRandomChanceCondition.randomChance(0.04f))
                        .when(LootItemKilledByPlayerCondition.killedByPlayer())
                );

        return LootTable.lootTable().withPool(pool0);
    }

    private static LootTable.Builder loot_with_entities_evoker() {
        LootPool.Builder pool0 = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1f))
                .add(LootItem.lootTableItem(TCItems.SORCERER_EMBLEM.get()))
                .when(LootItemRandomChanceCondition.randomChance(0.1f));

        LootPool.Builder pool1 = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1f))
                .add(LootItem.lootTableItem(TCItems.CROSS_NECKLACE.get()))
                .when(LootItemRandomChanceCondition.randomChance(0.1f));

        LootTable.Builder table = LootTable.lootTable();
        table = table.withPool(pool0);
        table = table.withPool(pool1);
        return table;
    }

    private static LootTable.Builder loot_with_entities_frog() {
        LootPool.Builder pool0 = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1f))
                .add(LootItem.lootTableItem(TCItems.FROG_LEG.get())
                        .when(LootItemRandomChanceCondition.randomChance(1f))
                        .when(LootItemKilledByPlayerCondition.killedByPlayer())
                );

        return LootTable.lootTable().withPool(pool0);
    }

    private static LootTable.Builder loot_with_entities_ghast() {
        LootPool.Builder pool0 = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1f))
                .add(LootItem.lootTableItem(TCItems.FART_IN_A_JAR.get())
                        .when(LootItemRandomChanceCondition.randomChance(0.08f))
                        .when(LootItemKilledByPlayerCondition.killedByPlayer())
                );

        return LootTable.lootTable().withPool(pool0);
    }

    private static LootTable.Builder loot_with_entities_glow_squid() {
        LootPool.Builder pool0 = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1f))
                .add(LootItem.lootTableItem(TCItems.LIFE_FORM_ANALYZER.get())
                        .when(LootItemRandomChanceCondition.randomChance(0.08f))
                        .when(LootItemKilledByPlayerCondition.killedByPlayer())
                )
                .add(LootItem.lootTableItem(TCItems.JELLYFISH_NECKLACE.get())
                        .when(LootItemRandomChanceCondition.randomChance(0.08f))
                        .when(LootItemKilledByPlayerCondition.killedByPlayer())
                );

        return LootTable.lootTable().withPool(pool0);
    }

    private static LootTable.Builder loot_with_entities_guardian() {
        LootPool.Builder pool0 = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1f))
                .add(LootItem.lootTableItem(TCItems.NEPTUNES_SHELL.get())
                        .when(LootItemRandomChanceCondition.randomChance(0.07f))
                        .when(LootItemKilledByPlayerCondition.killedByPlayer())
                );

        return LootTable.lootTable().withPool(pool0);
    }

    private static LootTable.Builder loot_with_entities_husk() {
        LootPool.Builder pool0 = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1f))
                .add(LootItem.lootTableItem(TCItems.DUNERIDER_BOOTS.get())
                        .when(LootItemRandomChanceCondition.randomChance(0.06f))
                        .when(LootItemKilledByPlayerCondition.killedByPlayer())
                );

        return LootTable.lootTable().withPool(pool0);
    }

    private static LootTable.Builder loot_with_entities_iron_golem() {
        LootPool.Builder pool0 = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1.0f))
                .add(LootItem.lootTableItem(TCItems.SHINY_STONE.get()))
                .when(LootItemRandomChanceCondition.randomChance(0.09f));

        return LootTable.lootTable().withPool(pool0);
    }

    private static LootTable.Builder loot_with_entities_magma_cube() {
        LootPool.Builder pool0 = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1f))
                .add(LootItem.lootTableItem(TCItems.MAGMA_STONE.get()))
                .when(LootItemRandomChanceCondition.randomChance(0.02f));

        return LootTable.lootTable().withPool(pool0);
    }

    private static LootTable.Builder loot_with_entities_phantom() {
        LootPool.Builder pool0 = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1f))
                .add(LootItem.lootTableItem(TCItems.MOON_STONE.get())
                        .when(LootItemRandomChanceCondition.randomChance(0.03f))
                        .when(LootItemKilledByPlayerCondition.killedByPlayer())
                );

        return LootTable.lootTable().withPool(pool0);
    }

    private static LootTable.Builder loot_with_entities_piglin_brute() {
        LootPool.Builder pool0 = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1f))
                .add(LootItem.lootTableItem(TCItems.FLESH_KNUCKLES.get()))
                .when(LootItemRandomChanceCondition.randomChance(0.1f));

        return LootTable.lootTable().withPool(pool0);
    }

    private static LootTable.Builder loot_with_entities_ravager() {
        LootPool.Builder pool0 = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1f))
                .add(LootItem.lootTableItem(TCItems.COBALT_SHIELD.get()))
                .when(LootItemRandomChanceCondition.randomChance(0.2f));

        LootPool.Builder pool1 = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1f))
                .add(LootItem.lootTableItem(TCItems.SHIELD_OF_CTHULHU.get()))
                .when(LootItemRandomChanceCondition.randomChance(0.1f));

        LootTable.Builder table = LootTable.lootTable();
        table = table.withPool(pool0);
        table = table.withPool(pool1);
        return table;
    }

    private static LootTable.Builder loot_with_entities_shulker() {
        LootPool.Builder pool0 = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1f))
                .add(LootItem.lootTableItem(TCItems.GRAVITY_GLOBE.get())
                        .when(LootItemRandomChanceCondition.randomChance(0.1f))
                        .when(LootItemKilledByPlayerCondition.killedByPlayer())
                );

        return LootTable.lootTable().withPool(pool0);
    }

    private static LootTable.Builder loot_with_entities_skeleton() {
        LootPool.Builder pool0 = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1f))
                .add(LootItem.lootTableItem(TCItems.RANGER_EMBLEM.get()))
                .when(LootItemRandomChanceCondition.randomChance(0.008f));

        LootPool.Builder pool1 = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1f))
                .add(LootItem.lootTableItem(TCItems.MAGIC_QUIVER.get()))
                .when(LootItemRandomChanceCondition.randomChance(0.025f));

        LootTable.Builder table = LootTable.lootTable();
        table = table.withPool(pool0);
        table = table.withPool(pool1);
        return table;
    }

    private static LootTable.Builder loot_with_entities_skeleton_horse() {
        LootPool.Builder pool0 = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1f))
                .add(LootItem.lootTableItem(TCItems.BONE_GLOVE.get())
                        .when(LootItemRandomChanceCondition.randomChance(0.5f))
                        .when(LootItemKilledByPlayerCondition.killedByPlayer())
                );

        return LootTable.lootTable().withPool(pool0);
    }

    private static LootTable.Builder loot_with_entities_slime() {
        LootPool.Builder pool0 = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1f))
                .add(LootItem.lootTableItem(TCItems.ROYAL_GEL.get())
                        .when(LootItemRandomChanceCondition.randomChance(0.01f))
                        .when(LootItemKilledByPlayerCondition.killedByPlayer())
                );

        LootPool.Builder pool1 = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1f))
                .add(LootItem.lootTableItem(TCItems.INNER_TUBE.get())
                        .when(LootItemRandomChanceCondition.randomChance(0.02f))
                        .when(LootItemKilledByPlayerCondition.killedByPlayer())
                );

        LootTable.Builder table = LootTable.lootTable();
        table = table.withPool(pool0);
        table = table.withPool(pool1);
        return table;
    }

    private static LootTable.Builder loot_with_entities_spider() {
        LootPool.Builder pool0 = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1f))
                .add(LootItem.lootTableItem(TCItems.CLIMBING_CLAWS.get())
                        .when(LootItemRandomChanceCondition.randomChance(0.008f))
                        .when(LootItemKilledByPlayerCondition.killedByPlayer())
                );

        return LootTable.lootTable().withPool(pool0);
    }

    private static LootTable.Builder loot_with_entities_stray() {
        LootPool.Builder pool0 = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1f))
                .add(LootItem.lootTableItem(TCItems.FAST_CLOCK.get()))
                .when(LootItemRandomChanceCondition.randomChance(0.08f));

        return LootTable.lootTable().withPool(pool0);
    }

    private static LootTable.Builder loot_with_entities_turtle() {
        LootPool.Builder pool0 = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1.0f))
                .add(LootItem.lootTableItem(TCItems.FROZEN_TURTLE_SHELL.get()));

        return LootTable.lootTable().withPool(pool0);
    }

    private static LootTable.Builder loot_with_entities_vindicator() {
        LootPool.Builder pool0 = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1f))
                .add(LootItem.lootTableItem(TCItems.WARRIOR_EMBLEM.get()))
                .when(LootItemRandomChanceCondition.randomChance(0.08f));

        return LootTable.lootTable().withPool(pool0);
    }

    private static LootTable.Builder loot_with_entities_warden() {
        LootPool.Builder pool0 = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1f))
                .add(LootItem.lootTableItem(TCItems.METAL_DETECTOR.get())
                        .when(LootItemRandomChanceCondition.randomChance(1f))
                        .when(LootItemKilledByPlayerCondition.killedByPlayer())
                );

        return LootTable.lootTable().withPool(pool0);
    }

    private static LootTable.Builder loot_with_entities_witch() {
        LootPool.Builder pool0 = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1f))
                .add(LootItem.lootTableItem(TCItems.VITAMINS.get()))
                .when(LootItemRandomChanceCondition.randomChance(0.08f));

        LootPool.Builder pool1 = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1f))
                .add(LootItem.lootTableItem(TCItems.BAND_OF_REGENERATION.get()))
                .when(LootItemRandomChanceCondition.randomChance(0.05f));

        LootTable.Builder table = LootTable.lootTable();
        table = table.withPool(pool0);
        table = table.withPool(pool1);
        return table;
    }

    private static LootTable.Builder loot_with_entities_wither() {
        LootPool.Builder pool0 = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1f))
                .add(LootItem.lootTableItem(TCItems.DEMON_HEART.get())
                        .when(LootItemRandomChanceCondition.randomChance(1f))
                        .when(LootItemKilledByPlayerCondition.killedByPlayer())
                );

        LootPool.Builder pool1 = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1f))
                .add(LootItem.lootTableItem(TCItems.SOARING_INSIGNIA.get())
                        .when(LootItemRandomChanceCondition.randomChance(1f))
                );

        LootTable.Builder table = LootTable.lootTable();
        table = table.withPool(pool0);
        table = table.withPool(pool1);
        return table;
    }

    private static LootTable.Builder loot_with_entities_wither_skeleton() {
        LootPool.Builder pool0 = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1f))
                .add(LootItem.lootTableItem(TCItems.BLACK_BELT.get()))
                .when(LootItemRandomChanceCondition.randomChance(0.08f));

        LootPool.Builder pool1 = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1f))
                .add(LootItem.lootTableItem(TCItems.HOLY_WATER.get()))
                .when(LootItemRandomChanceCondition.randomChance(0.06f));

        LootTable.Builder table = LootTable.lootTable();
        table = table.withPool(pool0);
        table = table.withPool(pool1);
        return table;
    }

    private static LootTable.Builder loot_with_entities_zombie() {
        LootPool.Builder pool0 = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1f))
                .add(LootItem.lootTableItem(TCItems.SHACKLE.get())
                        .when(LootItemRandomChanceCondition.randomChance(0.007f))
                        .when(LootItemKilledByPlayerCondition.killedByPlayer())
                );

        LootPool.Builder pool1 = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1f))
                .add(LootItem.lootTableItem(TCItems.MOON_CHARM.get())
                        .when(LootItemRandomChanceCondition.randomChance(0.1f))
                );

        LootTable.Builder table = LootTable.lootTable();
        table = table.withPool(pool0);
        table = table.withPool(pool1);
        return table;
    }

    private static LootTable.Builder loot_with_entities_zombie_villager() {
        LootPool.Builder pool0 = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1f))
                .add(LootItem.lootTableItem(TCItems.BRAIN_OF_CONFUSION.get())
                        .when(LootItemRandomChanceCondition.randomChance(0.15f))
                        .when(LootItemKilledByPlayerCondition.killedByPlayer())
                );

        return LootTable.lootTable().withPool(pool0);
    }

    private static LootTable.Builder loot_with_entities_zombified_piglin() {
        LootPool.Builder pool0 = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1f))
                .add(LootItem.lootTableItem(TCItems.ENERGY_BAR.get())
                        .when(LootItemRandomChanceCondition.randomChance(0.03f))
                        .when(LootItemKilledByPlayerCondition.killedByPlayer())
                );

        return LootTable.lootTable().withPool(pool0);
    }

    private static LootTable.Builder loot_with_gameplay_cat_morning_gift() {
        LootPool.Builder pool0 = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1f))
                .add(LootItem.lootTableItem(TCItems.WORM_SCARF.get())
                        .when(LootItemRandomChanceCondition.randomChance(0.1f))
                );

        return LootTable.lootTable().withPool(pool0);
    }

    private static LootTable.Builder loot_with_gameplay_hero_of_the_village_librarian_gift() {
        LootPool.Builder pool0 = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1f))
                .add(LootItem.lootTableItem(TCItems.SHINY_RED_BALLOON.get())
                        .when(LootItemRandomChanceCondition.randomChance(0.5f))
                );

        return LootTable.lootTable().withPool(pool0);
    }
}
