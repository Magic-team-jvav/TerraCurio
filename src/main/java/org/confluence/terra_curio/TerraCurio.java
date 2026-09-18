package org.confluence.terra_curio;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.confluence.lib.util.LibClientUtils;
import org.confluence.lib.util.LibUtils;
import org.confluence.terra_curio.client.TCClientConfigs;
import org.confluence.terra_curio.client.event.TCGameClientEvents;
import org.confluence.terra_curio.client.event.TCModClientEvent;
import org.confluence.terra_curio.common.TCCommonConfigs;
import org.confluence.terra_curio.common.advancement.CuriosEquippedTrigger;
import org.confluence.terra_curio.common.attachment.AccessoriesValueCommand;
import org.confluence.terra_curio.common.event.TCGameEvents;
import org.confluence.terra_curio.common.event.TCModEvents;
import org.confluence.terra_curio.common.init.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(TerraCurio.MODID)
public class TerraCurio {
    public static final String MODID = "terra_curio";
    public static final Logger LOGGER = LoggerFactory.getLogger("Terra Curio");
    public static final String CURIO_SLOT = "accessory";

    public TerraCurio(FMLJavaModLoadingContext context) {
        IEventBus eventBus = context.getModEventBus();
        TCStartupConfigs.register();
        TCCommonConfigs.register(context);
        TCModEvents.init();
        TCGameEvents.init();
        if (LibUtils.isPhysicalClient()) {
            TCModClientEvent.init();
            TCGameClientEvents.init();
            TCClientConfigs.register(context);
            LibClientUtils.registerConfigScreen(context);
        }
        TCAttachments.init();
        TCDataComponentTypes.init();
        TCArmorMaterials.init();
        TCSoundEvents.SOUNDS.register(eventBus);
        TCEntities.ENTITIES.register(eventBus);
        TCItems.init();
        TCTabs.TABS.register(eventBus);
        TCRecipes.register(eventBus);
        TCBlocks.BLOCKS.register(eventBus);
        TCMenus.TYPES.register(eventBus);
        AccessoriesValueCommand.INFOS.register(eventBus);
        eventBus.addListener(TCDataMaps::registerDataMapTypes);
        CriteriaTriggers.register(CuriosEquippedTrigger.INSTANCE);
    }

    public static ResourceLocation asResource(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }
}
