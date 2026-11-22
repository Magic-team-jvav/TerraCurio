package org.confluence.terra_curio.client;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.confluence.lib.ConfluenceMagicLib;

public final class TCClientConfigs {
    private static ForgeConfigSpec.BooleanValue PLAY_SHOES_SOUND;
    private static ForgeConfigSpec.DoubleValue SHOES_SOUND_VOLUME;
    private static ForgeConfigSpec.BooleanValue SHOW_SHOES_PARTICLE;
    private static ForgeConfigSpec.BooleanValue SPEED_UP;

    private static ForgeConfigSpec.BooleanValue AUTO_ATTACK;
    private static ForgeConfigSpec.BooleanValue RIGHT_CLICK_DELAY;

    private static ForgeConfigSpec.DoubleValue INFORMATION_HUD_TOP;
    private static ForgeConfigSpec.BooleanValue INFORMATION_HUD_LEFT;

    private static ForgeConfigSpec.BooleanValue DISPLAY_INFO_TOOLTIP;

    public static boolean playShoesSound = true;
    public static float shoesSoundVolume = 1.0F;
    public static boolean showShoesParticle = true;
    public static boolean speedUp = true;

    public static boolean autoAttack = true;
    public static boolean rightClickDelay = true;

    public static float informationHudTop = 0.5F;
    public static boolean informationIsLeft = false;

    public static boolean displayInfoTooltip = !ConfluenceMagicLib.CONFLUENCE_LOADED;

    public static void onLoad() {
        playShoesSound = PLAY_SHOES_SOUND.get();
        shoesSoundVolume = SHOES_SOUND_VOLUME.get().floatValue();
        showShoesParticle = SHOW_SHOES_PARTICLE.get();
        speedUp = SPEED_UP.get();

        autoAttack = AUTO_ATTACK.get();
        rightClickDelay = RIGHT_CLICK_DELAY.get();

        informationHudTop = INFORMATION_HUD_TOP.get().floatValue();
        informationIsLeft = INFORMATION_HUD_LEFT.get();
        displayInfoTooltip = !ConfluenceMagicLib.CONFLUENCE_LOADED && DISPLAY_INFO_TOOLTIP.get();
    }

    public static void register(FMLJavaModLoadingContext context) {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        PLAY_SHOES_SOUND = builder.push("Speed Shoes").define("playSound", true);
        SHOES_SOUND_VOLUME = builder.defineInRange("shoesSoundVolume", 1.0, 0.0, 1.0);
        SHOW_SHOES_PARTICLE = builder.define("showParticle", true);
        SPEED_UP = builder.define("speedUp", true);

        AUTO_ATTACK = builder.pop().push("Functional").define("autoAttack", true);
        RIGHT_CLICK_DELAY = builder.define("rightClickDelay", true);

        INFORMATION_HUD_TOP = builder.pop().push("Information HUD").comment("finalTop = screenHeight * top").defineInRange("top", 0.5, 0.0, 1.0);
        INFORMATION_HUD_LEFT = builder.comment("left or right").define("isLeft", false);

        if (!ConfluenceMagicLib.CONFLUENCE_LOADED) {
            DISPLAY_INFO_TOOLTIP = builder.comment("display the green tooltip").define("displayInfoTooltip", true);
        }
        context.registerConfig(ModConfig.Type.CLIENT, builder.build());
    }
}
