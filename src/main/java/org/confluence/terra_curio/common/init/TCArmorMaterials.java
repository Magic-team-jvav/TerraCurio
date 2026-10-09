package org.confluence.terra_curio.common.init;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import org.confluence.terra_curio.TerraCurio;
import org.mesdag.portlib.registries.PortArmorMaterialRegistration;
import org.mesdag.portlib.registries.PortRegisterHandler;
import org.mesdag.portlib.registries.PortRegistryEntry;
import org.mesdag.portlib.wrapper.sounds.SoundEventHolder;
import org.mesdag.portlib.wrapper.world.item.PortArmorMaterial;

public final class TCArmorMaterials {
    public static void init() {}

    public static final PortArmorMaterialRegistration MATERIALS = PortRegisterHandler.armorMaterial(TerraCurio.MODID);

    public static final PortRegistryEntry<ArmorMaterial, PortArmorMaterial> DIVING = MATERIALS.register("diving", () -> new PortArmorMaterial(
            PortArmorMaterial.Settings.create()
                    .name("diving")
                    .defense(2, 5, 6, 2)
                    .enchantmentValue(9)
                    .equipSound(SoundEventHolder.wrap(SoundEvents.ARMOR_EQUIP_IRON))
                    .repairIngredient(() -> Ingredient.of(Items.IRON_INGOT))
                    .layer(new PortArmorMaterial.Layer(TerraCurio.asResource("diving")))
                    .toughness(0)
                    .knockbackResistance(0)
    ).setName(TerraCurio.asResource("diving").toString()));
}
