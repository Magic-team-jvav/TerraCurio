package org.confluence.terra_curio.common.item.curio.movement;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.confluence.lib.util.LibUtils;
import org.confluence.terra_curio.TerraCurio;
import org.confluence.terra_curio.client.TCClientConfigs;
import org.confluence.terra_curio.client.handler.PlayerJumpHandler;
import org.confluence.terra_curio.client.handler.TCClientPacketHandler;
import org.confluence.terra_curio.common.init.TCSoundEvents;
import org.confluence.terra_curio.common.item.curio.BaseCurioItem;
import org.confluence.terra_curio.network.c2s.SpeedBootsNBTPacketC2S;
import org.confluence.terra_curio.util.CuriosUtils;
import top.theillusivec4.curios.api.SlotContext;

import java.util.UUID;

public class BaseSpeedBoots extends BaseCurioItem {
    public static final String KEY = TerraCurio.MODID + ":boots_speed";
    public static final UUID ID = UUID.nameUUIDFromBytes("base_speed_boots".getBytes());

    private final int acceleration;
    private final int maxSpeed;

    public BaseSpeedBoots(int acceleration, int maxSpeed, Builder builder) {
        super(builder);
        this.acceleration = acceleration;
        this.maxSpeed = maxSpeed;
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        super.curioTick(slotContext, stack);
        speedUp(slotContext, stack, acceleration, maxSpeed);
    }

    @Override
    protected void particleTick(SlotContext slotContext, LivingEntity living) {
        if (TCClientConfigs.showShoesParticle) {
            super.particleTick(slotContext, living);
        }
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        super.onUnequip(slotContext, newStack, stack);
        LibUtils.updateItemStackNbt(stack, tag -> tag.putInt(KEY, 0));
    }

    protected void speedUp(SlotContext slotContext, ItemStack stack, int acceleration, int maxSpeed) {
        if (TCClientConfigs.speedUp && slotContext.entity() instanceof Player player && player.isLocalPlayer()) {
            int speed = LibUtils.getItemStackNbtNoCopy(stack).getInt(KEY);
            if (player.zza > 0 && !player.horizontalCollision && !player.isCrouching()) {
                if (player.onGround() && !player.swinging) {
                    if (TCClientPacketHandler.isHasMagiluminescence() || PlayerJumpHandler.isInfiniteFlight()) {
                        acceleration *= 2;
                    }
                    int actually = Math.min(maxSpeed - speed, acceleration);
                    int value = speed + actually;
                    if (actually > 0) {
                        SpeedBootsNBTPacketC2S.sendToServer(slotContext.index(), value);
                    }
                    float ratio = (float) value / maxSpeed;
                    if (TCClientConfigs.playShoesSound && player.level().getGameTime() % (ratio < 0.5F ? 6L : 4L) == 0) {
                        player.playSound(TCSoundEvents.SHOES_WALK.get(), TCClientConfigs.shoesSoundVolume, 1.0F);
                    }
                }
            } else if (speed != 0) {
                SpeedBootsNBTPacketC2S.sendToServer(slotContext.index(), 0);
            }
        }
    }

    @Override
    public boolean canSync(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID id, ItemStack stack) {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder1 = ImmutableMultimap.builder();
        builder1.putAll(super.getAttributeModifiers(slotContext, id, stack));
        builder1.putAll(builder == null ? EMPTY_ATTRIBUTE : builder.getAttributes()); // 一般是step_height
        double speed = LibUtils.getItemStackNbtNoCopy(stack).getInt(KEY) * 0.01;
        if (speed > 0.0) {
            builder1.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(ID, "base_speed_boots", speed, AttributeModifier.Operation.MULTIPLY_TOTAL));
        }
        return builder1.build();
    }

    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        return CuriosUtils.noSameCurio(slotContext.entity(), BaseSpeedBoots.class);
    }
}
