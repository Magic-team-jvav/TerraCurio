package org.confluence.terra_curio.common.datagen;

import com.google.common.collect.Iterables;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.confluence.lib.common.LibTags;
import org.confluence.terra_curio.TerraCurio;
import org.confluence.terra_curio.common.init.TCItems;
import org.confluence.terra_curio.common.init.TCTags;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class TCItemTagsProvider extends ItemTagsProvider {
    public TCItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, CompletableFuture<TagLookup<Block>> b, @Nullable ExistingFileHelper helper) {
        super(output, provider, b, TerraCurio.MODID, helper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        IntrinsicTagAppender<Item> accessory = tag(TCTags.Items.ACCESSORY);
        accessory.add(TCItems.BASE_POINT.get());
        accessory.add(TCItems.EVERLASTING.get());
        Iterables.concat(TCItems.CURIOS.getEntries(), TCItems.WINGS.getEntries()).forEach(holder -> accessory.add(holder.get()));
        tag(TCTags.Items.DIVING).add(
                TCItems.DIVING_HELMET.get(),
                TCItems.DIVING_GEAR.get(),
                TCItems.JELLYFISH_DIVING_GEAR.get(),
                TCItems.ARCTIC_DIVING_GEAR.get()
        );
        tag(TCTags.Items.ANY_SANDSTORM_BALLOONS).add(
                TCItems.SANDSTORM_IN_A_BALLOON.get(),
                TCItems.YELLOW_HORSESHOE_BALLOON.get()
        );
        tag(TCTags.Items.ANY_BLIZZARD_BALLOONS).add(
                TCItems.BLIZZARD_IN_A_BALLOON.get(),
                TCItems.WHITE_HORSESHOE_BALLOON.get()
        );
        tag(TCTags.Items.ANY_CLOUD_BALLOONS).add(
                TCItems.CLOUD_IN_A_BALLOON.get(),
                TCItems.BLUE_HORSESHOE_BALLOON.get()
        );
        IntrinsicHolderTagsProvider.IntrinsicTagAppender<Item> wings = tag(TCTags.Items.WINGS);
        TCItems.WINGS.getEntries().forEach(holder -> wings.add(holder.get()));
        wings.add(TCItems.CELESTIAL_STARBOARD.get());

        tag(LibTags.Items.WIP).add(
                TCItems.FROZEN_WINGS.get(),
                TCItems.JETPACK.get(),
                TCItems.LEAF_WINGS.get(),
                TCItems.BAT_WINGS.get(),
                TCItems.BUTTERFLY_WINGS.get(),
                TCItems.FLAME_WINGS.get(),
                TCItems.HOVERBOARD.get(),
                TCItems.BONE_WINGS.get(),
                TCItems.MOTHRON_WINGS.get(),
                TCItems.SPECTRE_WINGS.get(),
                TCItems.BEETLE_WINGS.get(),
                TCItems.FESTIVE_WINGS.get(),
                TCItems.SPOOKY_WINGS.get(),
                TCItems.TATTERED_WINGS.get(),
                TCItems.STEAMPUNK_WINGS.get(),
                TCItems.BETSYS_WINGS.get(),
                TCItems.EMPRESS_WINGS.get(),
                TCItems.FISHRON_WINGS.get(),
                TCItems.NEBULA_WINGS.get(),
                TCItems.VORTEX_BOOSTER.get(),
                TCItems.SOLAR_WINGS.get(),
                TCItems.STARDUST_WINGS.get(),
                TCItems.EVERLASTING.get(),
                TCItems.BASE_POINT.get()
        );
    }
}
