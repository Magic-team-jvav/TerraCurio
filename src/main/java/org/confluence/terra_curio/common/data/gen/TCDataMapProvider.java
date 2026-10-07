package org.confluence.terra_curio.common.data.gen;

import it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Unit;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.data.DataMapProvider;
import org.confluence.lib.common.LibAttributes;
import org.confluence.terra_curio.api.primitive.*;
import org.confluence.terra_curio.common.component.PrimitiveValueComponent;
import org.confluence.terra_curio.common.init.TCDataMaps;
import org.confluence.terra_curio.common.init.TCItems;
import org.confluence.terra_curio.common.init.TCTags;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class TCDataMapProvider extends DataMapProvider {
    private final AdvancedBuilder<PrimitiveValueComponent, Item, PrimitiveValueComponent.Remover> builder = builder(TCDataMaps.ACCESSORIES);

    protected TCDataMapProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @Override
    protected void gather(HolderLookup.Provider provider) {
        add(TCItems.ICE_SKATES, helper -> helper.unit(TCItems.ICE$SPEED));
        add(TCItems.ANGLER_EARRING, helper -> {
            helper.entry(TCItems.ATTRIBUTES, AttributeModifiersValue.simple(Attributes.LUCK, helper.asId(), 10, AttributeModifier.Operation.ADD_VALUE));
            helper.of(TCItems.COMPONENTS, List.of());
        });
        add(TCItems.MAGMA_STONE, helper -> helper.unit(TCItems.FIRE$ATTACK));
        add(TCItems.SHACKLE, helper -> helper.entry(TCItems.ATTRIBUTES, AttributeModifiersValue.simple(Attributes.ARMOR, helper.asId(), 1, AttributeModifier.Operation.ADD_VALUE)));
        add(TCItems.BASE_POINT, helper -> {
            helper.unit(TCItems.FIRE$IMMUNE);
            helper.unit(TCItems.ICE$SPEED);
            helper.of(TCItems.MAY$FLY, MayFlyAbilityValue.of(helper.asKey(), 1.1F, 70, true, true));
            helper.of(TCItems.FLUID$WALK, Set.of(TCTags.WATER_LIKE_WALK, TCTags.LAVA_LIKE_WALK));
            helper.of(TCItems.LAVA$IMMUNE$TICKS, 200);
            helper.of(TCItems.LAVA$HURT$REDUCE, 0.75F);
            ResourceLocation id = helper.asId();
            helper.entry(TCItems.ATTRIBUTES, AttributeModifiersValue.builder()
                    .add(Attributes.MOVEMENT_SPEED, id, 0.16, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
                    .add(Attributes.FALL_DAMAGE_MULTIPLIER, id, -100, AttributeModifier.Operation.ADD_VALUE)
                    .add(Attributes.JUMP_STRENGTH, id, 1, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
                    .add(Attributes.STEP_HEIGHT, id, 0.5, AttributeModifier.Operation.ADD_VALUE)
                    .build());
        });
        add(TCItems.EVERLASTING, helper -> {
            helper.unit(TCItems.FROZEN$IMMUNE);
            helper.unit(TCItems.FIRE$IMMUNE);
            helper.unit(TCItems.SHIELD$OF$CTHULHU);
            helper.of(TCItems.EFFECT$IMMUNITIES, Set.of(
                    MobEffects.POISON,
                    MobEffects.WITHER,
                    MobEffects.WEAKNESS,
                    MobEffects.HUNGER,
                    MobEffects.BLINDNESS,
                    MobEffects.DARKNESS,
                    MobEffects.MOVEMENT_SLOWDOWN,
                    MobEffects.CONFUSION,
                    MobEffects.DIG_SLOWDOWN,
                    MobEffects.LEVITATION
            ));
            helper.of(TCItems.TOTEM$WITH$COOLDOWN, 2400);
            helper.of(TCItems.INVULNERABLE$TICKS$MULTIPLIER, 2.0F);
            helper.entry(TCItems.ATTRIBUTES, AttributeModifiersValue.simple(LibAttributes.getDodgeChance(), helper.asId(), 0.1, AttributeModifier.Operation.ADD_VALUE));
        });

        /// 同步已有基础能力，供本体的局部数据映射合并；属性继续使用各物品原有定义。
        add(TCItems.CELESTIAL_SHELL, helper -> helper.unit(TCItems.NEPTUNES$SHELL));
        add(TCItems.HAND_OF_CREATION, helper -> helper.of(TCItems.RIGHT$CLICK$DELAY$SUBSTRACTOR, (byte) 3));
    }

    protected void add(ItemLike item, Consumer<Helper> consumer) {
        builder.add(item.asItem().builtInRegistryHolder().key(), new PrimitiveValueComponent(wrap(item.asItem(), consumer)), false);
    }

    @SuppressWarnings("deprecation")
    public static Map<ValueType<?, ? extends PrimitiveValue<?>>, PrimitiveValue<?>> wrap(Item item, Consumer<Helper> consumer) {
        Map<ValueType<?, ? extends PrimitiveValue<?>>, PrimitiveValue<?>> map = new Reference2ObjectOpenHashMap<>();
        consumer.accept(new Helper() {
            @Override
            public void unit(ValueType<Unit, ? extends UnitValue> type) {
                map.put(type, type.newInstance(Unit.INSTANCE));
            }

            @Override
            public <T1, V1 extends PrimitiveValue<T1>> void of(ValueType<T1, V1> type, T1 value) {
                map.put(type, type.newInstance(value));
            }

            @Override
            public <T1, V1 extends PrimitiveValue<T1>> void entry(ValueType<T1, V1> type, V1 value) {
                map.put(type, value);
            }

            @Override
            public ResourceLocation asId() {
                return BuiltInRegistries.ITEM.getKey(item);
            }

            @Override
            public ResourceKey<Item> asKey() {
                return item.builtInRegistryHolder().key();
            }
        });
        return map;
    }

    public interface Helper {
        void unit(ValueType<Unit, ? extends UnitValue> type);

        <T, V extends PrimitiveValue<T>> void of(ValueType<T, V> type, T value);

        <T, V extends PrimitiveValue<T>> void entry(ValueType<T, V> type, V value);

        ResourceLocation asId();

        ResourceKey<Item> asKey();
    }
}
