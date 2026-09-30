package org.confluence.terra_curio.common.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import org.confluence.terra_curio.TerraCurio;
import top.theillusivec4.curios.CuriosConstants;

public final class TCTags {
    public static class Items {
        public static final TagKey<Item> ACCESSORY = ItemTags.create(ResourceLocation.fromNamespaceAndPath(CuriosConstants.MOD_ID, TerraCurio.CURIO_SLOT));
        public static final TagKey<Item> DIVING = register("diving");
        public static final TagKey<Item> ANY_SANDSTORM_BALLOONS = register("any_sandstorm_balloons");
        public static final TagKey<Item> ANY_BLIZZARD_BALLOONS = register("any_blizzard_balloons");
        public static final TagKey<Item> ANY_CLOUD_BALLOONS = register("any_cloud_balloons");
        public static final TagKey<Item> WINGS = register("wings");

        private static TagKey<Item> register(String id) {
            return ItemTags.create(TerraCurio.asResource(id));
        }
    }

    public static final TagKey<Block> FLOWER_BOOTS_AVAILABLE = BlockTags.create(TerraCurio.asResource("flower_boots_available"));
    public static final TagKey<Fluid> WATER_LIKE_WALK = FluidTags.create(TerraCurio.asResource("water_like_walk"));
    public static final TagKey<Fluid> LAVA_LIKE_WALK = FluidTags.create(TerraCurio.asResource("lava_like_walk"));
    public static final TagKey<DamageType> HARMFUL_EFFECT = TagKey.create(Registries.DAMAGE_TYPE, TerraCurio.asResource("harmful_effect"));
    public static final TagKey<EntityType<?>> NOTHING = TagKey.create(Registries.ENTITY_TYPE, TerraCurio.asResource("nothing"));
}
