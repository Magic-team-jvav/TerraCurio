package org.confluence.terra_curio.network.s2c;

import io.netty.buffer.ByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.confluence.lib.util.LibStreamCodecUtils;
import org.confluence.terra_curio.TerraCurio;
import org.confluence.terra_curio.api.event.RegisterAccessoriesComponentUnitValueTypeLocalSyncEvent;
import org.confluence.terra_curio.api.primitive.PrimitiveValue;
import org.confluence.terra_curio.api.primitive.UnitValue;
import org.confluence.terra_curio.api.primitive.ValueType;
import org.confluence.terra_curio.client.handler.TCClientPacketHandler;
import org.confluence.terra_curio.common.component.PrimitiveValueComponent;
import org.confluence.terra_curio.common.init.TCItems;
import org.confluence.terra_curio.util.CuriosUtils;
import org.confluence.terra_curio.util.TCUtils;
import org.mesdag.portlib.event.PortEventHandler;
import org.mesdag.portlib.network.IPortPacket;
import org.mesdag.portlib.network.PortPacketDistributor;
import org.mesdag.portlib.network.codec.PortStreamCodec;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public record CurioExistsPacketS2C(boolean[] exists) implements IPortPacket.S2C {
    private static final List<ValueType<Unit, UnitValue>> TYPES = new ArrayList<>();

    public static final int AUTO_ATTACK = register(TCItems.AUTO$ATTACK);
    public static final int SHIELD_OF_CTHULHU = register(TCItems.SHIELD$OF$CTHULHU);
    public static final int TABI = register(TCItems.SPRINTING);
    public static final int SCOPE = register(TCItems.SCOPE);
    public static final int GRAVITY_GLOBE = register(TCItems.GRAVITY$GLOBE);
    public static final int MAGILUMINESCENCE = register(TCItems.$MAGILUMINESCENCE);
    public static final int FLOAT_ON_LIQUID_SURFACE = register(TCItems.FLOAT$ON$LIQUID$SURFACE);
    public static final int ICE_SAFE = register(TCItems.ICE$SAFE);
    public static final int BONE_GLOVE = register(TCItems.BONE$GLOVE);

    private static int register(ValueType<Unit, UnitValue> type) {
        int index = TYPES.size();
        TYPES.add(type);
        return index;
    }

    public static final ResourceLocation ID = TerraCurio.asResource("curio_exists");
    public static final PortStreamCodec<ByteBuf, CurioExistsPacketS2C> STREAM_CODEC;

    static {
        PortEventHandler.postEvent(new RegisterAccessoriesComponentUnitValueTypeLocalSyncEvent(CurioExistsPacketS2C::register));
        STREAM_CODEC = LibStreamCodecUtils.booleanArray(TYPES.size()).map(CurioExistsPacketS2C::new, CurioExistsPacketS2C::exists);
    }

    @Override
    public ResourceLocation identifier() {
        return ID;
    }

    @Override
    public void work(Player player) {
        TCClientPacketHandler.handleCurioExists(exists);
    }

    public static void sendToClient(ServerPlayer player) {
        boolean[] arr = new boolean[TYPES.size()];
        for (ItemStack itemStack : CuriosUtils.getCurios(player)) {
            PrimitiveValueComponent component = TCUtils.getAccessoriesComponent(itemStack);
            if (component == null) continue;
            Map<ValueType<?, ? extends PrimitiveValue<?>>, PrimitiveValue<?>> types = component.types();
            for (int i = 0; i < TYPES.size(); i++) {
                if (types.containsKey(TYPES.get(i))) {
                    arr[i] = true;
                }
            }
        }
        PortPacketDistributor.sendToPlayer(player, new CurioExistsPacketS2C(arr));
    }
}
