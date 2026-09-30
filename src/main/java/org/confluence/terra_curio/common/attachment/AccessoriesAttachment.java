package org.confluence.terra_curio.common.attachment;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.common.NeoForge;
import org.confluence.lib.util.LibUtils;
import org.confluence.terra_curio.api.event.AfterAccessoryAbilitiesFlushedEvent;
import org.confluence.terra_curio.common.component.PrimitiveValueComponent;
import org.confluence.terra_curio.common.init.TCAttachments;
import org.confluence.terra_curio.common.init.TCItems;
import org.confluence.terra_curio.common.init.TCTags;
import org.confluence.terra_curio.common.item.curio.combat.PanicNecklace;
import org.confluence.terra_curio.network.s2c.FlushRenderLayerPacketS2C;
import org.confluence.terra_curio.util.TCUtils;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

public class AccessoriesAttachment extends PrimitiveValueHolder {
    protected boolean panicNecklace;
    protected transient int remainLavaImmuneTicks;

    @Override
    public void setToDefaultValue() {
        super.setToDefaultValue();
        this.panicNecklace = false;
        this.remainLavaImmuneTicks = 0;
    }

    public boolean hasPanicNecklace() {
        return panicNecklace;
    }

    public void increaseLavaImmuneTicks() {
        if (remainLavaImmuneTicks < getValue(TCItems.LAVA$IMMUNE$TICKS)) {
            this.remainLavaImmuneTicks++;
        }
    }

    public boolean decreaseLavaImmuneTicks() {
        if (remainLavaImmuneTicks > 0) {
            this.remainLavaImmuneTicks--;
            return true;
        }
        return false;
    }

    @Override
    public void flushAbility(LivingEntity living) {
        super.flushAbility(living);
        if (living.level().isClientSide) return;
        CuriosApi.getCuriosInventory(living).ifPresent(handler -> {
            for (ICurioStacksHandler curioStacksHandler : handler.getCurios().values()) {
                IDynamicStackHandler stackHandler = curioStacksHandler.getStacks();
                for (int i = 0; i < stackHandler.getSlots(); i++) {
                    ItemStack stack = stackHandler.getStackInSlot(i);
                    if (stack.isEmpty()) continue;
                    PrimitiveValueComponent component = TCUtils.getAccessoriesComponent(stack);
                    if (component == null) continue;
                    Item item = stack.getItem();

                    compute(component);

                    if (!panicNecklace && item instanceof PanicNecklace) this.panicNecklace = true;
                    LibUtils.forMixin$Inject();
                }
            }
            TagKey<EntityType<?>> ignores = getValue(TCItems.MOB$IGNORE);
            if (!TCTags.NOTHING.equals(ignores)) {
                living.level().getEntitiesOfClass(Mob.class, new AABB(living.blockPosition()).inflate(31.5), mob -> mob.getType().is(ignores)).forEach(mob -> {
                    if (mob.getTarget() == living) mob.setTarget(null);
                });
            }
        });
        NeoForge.EVENT_BUS.post(new AfterAccessoryAbilitiesFlushedEvent(living));
        FlushRenderLayerPacketS2C.sendToPlayersTrackingEntityAndSelf(living);
    }

    @Override
    public CompoundTag serializeNBT(HolderLookup.Provider provider) {
        CompoundTag nbt = super.serializeNBT(provider);
        if (panicNecklace) {
            nbt.putBoolean("panicNecklace", true);
        }
        return nbt;
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag nbt) {
        super.deserializeNBT(provider, nbt);
        this.panicNecklace = nbt.getBoolean("panicNecklace");
    }

    public static AccessoriesAttachment of(Entity entity) {
        return entity.getData(TCAttachments.ACCESSORIES);
    }
}
