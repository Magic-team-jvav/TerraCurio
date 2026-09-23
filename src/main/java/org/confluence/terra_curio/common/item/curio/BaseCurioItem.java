package org.confluence.terra_curio.common.item.curio;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;
import org.confluence.lib.mixed.ILibEntity;
import org.confluence.terra_curio.TCStartupConfigs;
import org.confluence.terra_curio.TerraCurio;
import org.confluence.terra_curio.api.primitive.AttributeModifiersValue;
import org.confluence.terra_curio.api.primitive.ComponentsValue;
import org.confluence.terra_curio.api.primitive.PrimitiveValue;
import org.confluence.terra_curio.api.primitive.ValueType;
import org.confluence.terra_curio.client.TCClientConfigs;
import org.confluence.terra_curio.common.component.PrimitiveValueComponent;
import org.confluence.terra_curio.common.init.TCDataComponentTypes;
import org.confluence.terra_curio.common.init.TCDataMaps;
import org.confluence.terra_curio.common.init.TCItems;
import org.confluence.terra_curio.mixed.ITCLivingEntity;
import org.confluence.terra_curio.network.s2c.RemoveCurioParticleEmitterPacketS2C;
import org.confluence.terra_curio.util.CuriosUtils;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4x3f;
import org.mesdag.particlestorm.particle.MolangParticleEngine;
import org.mesdag.particlestorm.particle.ParticleEmitter;
import org.mesdag.portlib.component.PortDataComponentType;
import org.mesdag.portlib.diff.Diff;
import org.mesdag.portlib.registries.PortRegistryEntry;
import org.mesdag.portlib.wrapper.world.entity.ai.attributes.PortAttributeModifier;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.OverridingMethodsMustInvokeSuper;
import java.util.*;
import java.util.function.BiConsumer;

public class BaseCurioItem extends Item implements ICurioItem {
    protected static final Multimap<Attribute, AttributeModifier> EMPTY_ATTRIBUTE = ImmutableMultimap.of();

    protected Builder builder;

    public BaseCurioItem(Builder builder) {
        super(builder.initialize().properties);
        this.builder = builder;
    }

    public BaseCurioItem(Properties properties) {
        super(properties.stacksTo(1));
    }

    @OverridingMethodsMustInvokeSuper
    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        if (builder == null || builder.particleTriggers.isEmpty() || ItemStack.isSameItem(newStack, stack))
            return;
        if (slotContext.entity() instanceof ServerPlayer player) {
            for (ResourceLocation particle : builder.particleTriggers.keySet()) {
                RemoveCurioParticleEmitterPacketS2C.sendToClient(player, particle);
            }
        }
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        if (builder == null || builder.particleTriggers.isEmpty()) return;
        LivingEntity living = slotContext.entity();
        if (living.level().isClientSide) {
            particleTick(slotContext, living);
        }
    }

    protected void particleTick(SlotContext slotContext, LivingEntity living) {
        Map<ResourceLocation, ParticleEmitter> emitters = ITCLivingEntity.of(living).terra_curio$getOrCreateParticleEmitters();
        for (Map.Entry<ResourceLocation, Builder.ParticleData> entry : builder.particleTriggers.entrySet()) {
            ResourceLocation particle = entry.getKey();
            Builder.ParticleData data = entry.getValue();
            ParticleEmitter emitter = emitters.get(particle);
            if (emitter == null || emitter.isRemoved()) {
                if (emitter != null) {
                    emitters.remove(particle);
                }
                emitter = new ParticleEmitter(living.level(), living.position(), particle);
                emitter.attachEntity(living);
                emitter.hideOutline = true;
                MolangParticleEngine.INSTANCE.addEmitter(emitter);
                emitters.put(particle, emitter);
            }
            boolean active = data.trigger().shouldActivate(living);
            emitter.active = active && slotContext.visible();
            if (active && builder.positionParticle) {
                if (!emitter.isLocalSpace()) {
                    emitter.setLocalSpace(new Matrix4x3f(), false);
                }
                Matrix4x3f space = emitter.getLocalSpace();
                if (data.placement == null) {
                    float baseY = ILibEntity.of(living).confluence$isShouldRot() ? living.getBbHeight() : 0.0F;
                    space.identity().translate(0, baseY, 0);
                } else {
                    data.placement.accept(living, space);
                }
            }
        }
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        return getAttributeModifiers(stack);
    }

    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(ItemStack stack) {
        PrimitiveValueComponent component = stack.getItemHolder().getData(TCDataMaps.ACCESSORIES);
        AttributeModifiersValue value;
        if (component != null && (value = component.get(TCItems.ATTRIBUTES)) != null) {
            return value.get();
        }
        return builder == null ? EMPTY_ATTRIBUTE : builder.attributes;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag isAdvanced) {
        PrimitiveValueComponent component = stack.getItemHolder().getData(TCDataMaps.ACCESSORIES);
        ComponentsValue value;
        if (component != null && (value = component.get(TCItems.COMPONENTS)) != null) {
            tooltipComponents.addAll(value.components());
            return;
        }
        if (builder == null || builder.hasToolTip) {
            tooltipComponents.add(Component.translatable("tooltip." + stack.getDescriptionId() + ".0").withStyle(ChatFormatting.GRAY));
            if (builder != null) tooltipComponents.addAll(builder.additionTip);
        }
        appendInfo(stack, tooltipComponents);
    }

    protected void appendInfo(ItemStack stack, List<Component> tooltipComponents) {
        if (TCClientConfigs.displayInfoTooltip && builder != null && builder.infoTooltipCount > 0) {
            tooltipComponents.add(Component.empty());
            for (int i = 0; i < builder.infoTooltipCount; i++) {
                tooltipComponents.add(Component.translatable("info.tooltip." + stack.getDescriptionId() + "." + i).withStyle(ChatFormatting.GREEN));
            }
        }
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return canEquip(slotContext, stack);
    }

    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        return slotContext.cosmetic() || CuriosUtils.noSameCurio(slotContext.entity(), this);
    }

    @Override
    public boolean makesPiglinsNeutral(SlotContext slotContext, ItemStack stack) {
        return builder != null && builder.makePiglinsNeutral;
    }

    public static Builder builder(String name, Properties properties) {
        return new Builder(name, properties);
    }

    public static Builder builder(String name) {
        return new Builder(name, new Properties());
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return enchantment.isCurse() || enchantment.category.canEnchant(stack.getItem());
    }

    public static class Builder {
        private final String name;
        private final Properties properties;
        private final ResourceLocation defaultId;
        private final UUID id;

        private final List<Component> additionTip = new ArrayList<>();
        private boolean hasToolTip = true;
        private transient ImmutableMultimap.Builder<Attribute, AttributeModifier> attributesBuilder = ImmutableMultimap.builder();
        private Multimap<Attribute, AttributeModifier> attributes;
        private ModRarity rarity = ModRarity.BLUE;
        private int infoTooltipCount = 1;
        private boolean makePiglinsNeutral = false;
        private EquipmentSlot equipmentSlot = null;

        private final LinkedHashMap<ResourceLocation, ParticleData> particleTriggers = new LinkedHashMap<>();
        private boolean positionParticle = true;

        Builder(String name, Properties properties) {
            this.name = name;
            this.properties = properties;
            this.defaultId = TerraCurio.asResource(name);
            this.id = UUID.nameUUIDFromBytes(name.getBytes());
        }

        /// 声明一个粒子及其激活条件。可声明多个（多能力合一饰品按各自条件分段播放）。
        public Builder particle(ResourceLocation particle, ParticleTrigger trigger) {
            return particle(particle, trigger, null);
        }

        /// 声明粒子，并指定 emitter 本地空间矩阵的变换（旋转 + 平移，随实体状态逐 tick 应用）。
        /// 嘴部等跟随朝向的发射点用 {@link ParticlePlacements#MOUTH}。
        public Builder particle(ResourceLocation particle, ParticleTrigger trigger, @Nullable BiConsumer<LivingEntity, Matrix4x3f> placement) {
            this.particleTriggers.put(particle, new ParticleData(trigger, placement));
            return this;
        }

        /// 一个粒子的声明数据：激活条件 + 可选的本地空间矩阵变换
        public record ParticleData(ParticleTrigger trigger,
                                   @Nullable BiConsumer<LivingEntity, Matrix4x3f> placement) {
            public ParticleData(ParticleTrigger trigger) {
                this(trigger, null);
            }
        }

        /// 不随实体高度/朝向定位 emitter（保持世界空间，如冰冻海龟壳）
        public Builder noParticlePosition() {
            this.positionParticle = false;
            return this;
        }

        public Builder equipable(EquipmentSlot slot) {
            this.equipmentSlot = slot;
            return this;
        }

        public Builder makesPiglinsNeutral() {
            this.makePiglinsNeutral = true;
            return this;
        }

        public <T> Builder component(PortRegistryEntry<PortDataComponentType<?>, PortDataComponentType<T>> type, T value) {
            properties.component(type, value);
            return this;
        }

        @Diff
        public Builder attribute(Attribute attribute, String path, double amount, PortAttributeModifier.Operation operation) {
            attributesBuilder.put(attribute, new AttributeModifier(id, name + "_" + path, amount, operation.unwrap()));
            return this;
        }

        @Diff
        public Builder attribute(Attribute attribute, double amount, PortAttributeModifier.Operation operation) {
            attributesBuilder.put(attribute, new AttributeModifier(id, defaultId.getPath(), amount, operation.unwrap()));
            return this;
        }

        public Builder attribute(Holder<Attribute> attribute, String path, double amount, PortAttributeModifier.Operation operation) {
            attributesBuilder.put(attribute.value(), new AttributeModifier(id, name + "_" + path, amount, operation.unwrap()));
            return this;
        }

        public Builder attribute(Holder<Attribute> attribute, double amount, PortAttributeModifier.Operation operation) {
            attributesBuilder.put(attribute.value(), new AttributeModifier(id, defaultId.getPath(), amount, operation.unwrap()));
            return this;
        }

        public Builder stepHeight() {
            if (TCStartupConfigs.shoesExtraStepHeight()) {
                return attribute(Attributes.STEP_HEIGHT.value(), 0.5, PortAttributeModifier.Operation.ADD_VALUE);
            }
            return this;
        }

        public Builder rarity(ModRarity rarity) {
            this.rarity = rarity;
            if (rarity != ModRarity.GRAY && rarity != ModRarity.WHITE) {
                properties.fireResistant();
            }
            return this;
        }

        public Builder accessories(PrimitiveValueComponent component, PrimitiveValueComponent... components) {
            if (components.length == 0) {
                properties.component(TCDataComponentTypes.ACCESSORIES, component);
            } else {
                Map<ValueType<?, ? extends PrimitiveValue<?>>, PrimitiveValue<?>> map = new Hashtable<>(component.types());
                for (PrimitiveValueComponent component1 : components) {
                    map.putAll(component1.types());
                }
                properties.component(TCDataComponentTypes.ACCESSORIES, new PrimitiveValueComponent(map));
            }
            return this;
        }

        /// 额外的工具提示
        public Builder tooltip(String str) {
            if (!hasToolTip) {
                throw new IllegalArgumentException("Can not add tooltip when noTooltip() invoked!");
            }
            additionTip.add(Component.translatable(str).withStyle(ChatFormatting.GRAY));
            return this;
        }

        /// 额外的工具提示
        ///
        /// @param namespace 命名空间
        /// @param extra     额外的数量
        public Builder tooltips(String namespace, int extra) {
            if (!hasToolTip) {
                throw new IllegalArgumentException("Can not add tooltip when noTooltip() invoked!");
            }
            extra += 1;
            for (int i = 1; i < extra; i++) {
                additionTip.add(Component.translatable("tooltip.item." + namespace + "." + name + "." + i).withStyle(ChatFormatting.GRAY));
            }
            return this;
        }

        public Builder tooltips(int extra) {
            return tooltips(TerraCurio.MODID, extra);
        }

        public Builder infos(int count) {
            this.infoTooltipCount = count;
            return this;
        }

        public Builder noTooltip() {
            additionTip.clear();
            this.hasToolTip = false;
            return this;
        }

        @ApiStatus.Internal
        public Builder initialize() {
            properties.stacksTo(1).component(ConfluenceMagicLib.MOD_RARITY, rarity);
            this.attributes = attributesBuilder.build();
            this.attributesBuilder = null;
            return this;
        }

        @ApiStatus.Internal
        public Multimap<Attribute, AttributeModifier> getAttributes() {
            return attributes;
        }

        @ApiStatus.Internal
        public Map<ResourceLocation, ParticleData> getParticleTriggers() {
            return particleTriggers;
        }

        public BaseCurioItem build() {
            if (equipmentSlot != null) {
                return new Equipable(this);
            }
            return new BaseCurioItem(this);
        }
    }

    public static class Equipable extends BaseCurioItem implements net.minecraft.world.item.Equipable {
        public Equipable(Builder builder) {
            super(builder);
        }

        @Override
        public EquipmentSlot getEquipmentSlot() {
            return builder.equipmentSlot;
        }
    }
}
