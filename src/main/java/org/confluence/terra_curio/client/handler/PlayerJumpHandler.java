package org.confluence.terra_curio.client.handler;

import it.unimi.dsi.fastutil.objects.ObjectIntMutablePair;
import it.unimi.dsi.fastutil.objects.ObjectIntPair;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.Mth;
import net.minecraft.world.item.Item;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;
import org.apache.commons.lang3.mutable.MutableBoolean;
import org.apache.commons.lang3.mutable.MutableFloat;
// WP6c：`GravitationHandler` 已按 1.20 的归属搬到 Magic-Lib。本类与该类原本**同包**
// （`org.confluence.terra_curio.client.handler`），所以原来没有 import、直接裸引用；
// 这里补一条显式 import 改指到 Lib 那份（单类型导入会遮蔽同包同名类型，JLS 6.4.1），逻辑一行未动。
import org.confluence.lib.client.handler.GravitationHandler;
import org.confluence.terra_curio.api.primitive.MayFlyAbilityValue;
import org.confluence.terra_curio.client.sound.RocketBootsBoostSoundInstance;
import org.confluence.terra_curio.client.sound.RocketBootsStopSoundInstance;
import org.confluence.terra_curio.common.init.TCItems;
import org.confluence.terra_curio.common.init.TCSoundEvents;
import org.confluence.terra_curio.common.item.curio.combat.RamRune;
import org.confluence.terra_curio.integration.airhop.AirHopHelper;
import org.confluence.terra_curio.mixin.accessor.LivingEntityAccessor;
import org.confluence.terra_curio.network.c2s.PlayerJumpPacketC2S;
import org.confluence.terra_curio.network.c2s.RamRuneFallPacketC2S;
import org.confluence.terra_curio.util.CuriosUtils;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.confluence.terra_curio.network.c2s.PlayerJumpPacketC2S.JUMP_BY_SELF;
import static org.confluence.terra_curio.network.c2s.PlayerJumpPacketC2S.RESET_FALL_DISTANCE;

public final class PlayerJumpHandler {
    private static boolean jumpKeyDown = true;

    private static float fartSpeed = 0.0F;
    private static boolean fartFinished = false;

    private static float sandstormSpeed = 0.0F;
    private static int maxSandstormTicks = 0;
    private static int remainSandstormTicks = 0;
    private static boolean sandstormFinished = false;
    public static boolean isOnSandstormJump = false;

    private static float blizzardSpeed = 0.0F;
    private static int maxBlizzardTicks = 0;
    private static int remainBlizzardTicks = 0;
    private static boolean blizzardFinished = false;
    public static boolean isOnBlizzardJump = false;

    private static float tsunamiSpeed = 0.0F;
    private static boolean tsunamiFinished = false;
    public static boolean isOnTsunamiJump = false;

    private static float cloudSpeed = 0.0F;
    private static boolean cloudFinished = false;
    public static boolean isOnCloudJump = false;

    private static Map<ResourceKey<Item>, ObjectIntPair<MayFlyAbilityValue.FlyStack>> wingsFlyStacks = Map.of();
    private static Map<ResourceKey<Item>, ObjectIntPair<MayFlyAbilityValue.FlyStack>> otherFlyStacks = Map.of();
    private static boolean onFlight = false;
    private static boolean horizontalFlight = false;
    private static boolean infiniteFlight = false;
    private static float infiniteFlightSpeed = 0.0F;

    private static ResourceKey<Item> currentFlight;
    private static ResourceKey<Item> lastFlight;
    private static boolean onGlide = false;

    public static void handle(LocalPlayer localPlayer, boolean jumping) {
        if (StepStoolHandler.onStool()) return;

        if (lastFlight != currentFlight) {
            if (currentFlight == TCItems.ROCKET_BOOTS.getKey()) {
                Minecraft.getInstance().getSoundManager().play(new RocketBootsBoostSoundInstance(localPlayer));
            } else if (lastFlight == TCItems.ROCKET_BOOTS.getKey()) {
                Minecraft.getInstance().getSoundManager().play(new RocketBootsStopSoundInstance(localPlayer));
            }
            lastFlight = currentFlight;
        }

        if (localPlayer.onGround()) {
            reset(true);
        } else if (jumping) {
            if (!jumpKeyDown && !localPlayer.getAbilities().flying && localPlayer.isShiftKeyDown() && CuriosUtils.hasCurio(localPlayer, RamRune.class)) {
                Vec3 vec3 = localPlayer.getDeltaMovement();
                localPlayer.setDeltaMovement(vec3.x, RamRune.FALL_VELOCITY, vec3.z);
                localPlayer.hasImpulse = true;
                PacketDistributor.sendToServer(RamRuneFallPacketC2S.INSTANCE);
                jumpKeyDown = true;
                return;
            }
            if (AirHopHelper.LOADED && AirHopHelper.notFinishJump(localPlayer)) {
                jumpKeyDown = true;
                return;
            }

            for (Map.Entry<ResourceKey<Item>, ObjectIntPair<MayFlyAbilityValue.FlyStack>> entry : wingsFlyStacks.entrySet()) {
                ObjectIntPair<MayFlyAbilityValue.FlyStack> pair = entry.getValue();
                int i = pair.rightInt();
                if (infiniteFlight || i > 0) {
                    float flySpeed = infiniteFlight ? infiniteFlightSpeed : pair.key().flySpeed();
                    boolean horizontal = pair.left().horizontalFlight();
                    onFlight = true;
                    onGlide = false;
                    fly(horizontal && localPlayer.isShiftKeyDown(), localPlayer, flySpeed);
                    if (!infiniteFlight) currentFlight = entry.getKey();
                    if (!horizontal || localPlayer.level().getGameTime() % 2 == 0) {
                        pair.right(--i);
                    }
                    if (infiniteFlight || i > 0) return;
                } else if (!localPlayer.getAbilities().flying && localPlayer.getDeltaMovement().y < -0.15) {
                    onFlight = false;
                    onGlide = true;
                    glide(localPlayer);
                }
            }
            currentFlight = null;
            if (jumpKeyDown) return;

            if (!fartFinished && fartSpeed > 0.0) {
                fartFinished = true;
                jumpKeyDown = true;
                multiJump(localPlayer, fartSpeed);
                localPlayer.playSound(TCSoundEvents.FART_SOUND.get());
            } else if (!sandstormFinished && sandstormSpeed > 0.0) {
                if (remainSandstormTicks-- > 0) {
                    oneTimeJump(localPlayer, sandstormSpeed);
                    isOnSandstormJump = true;
                } else {
                    jumpKeyDown = true;
                    isOnSandstormJump = false;
                }
            } else if (!blizzardFinished && blizzardSpeed > 0.0) {
                if (remainBlizzardTicks-- > 0) {
                    oneTimeJump(localPlayer, blizzardSpeed);
                    isOnBlizzardJump = true;
                } else {
                    jumpKeyDown = true;
                    isOnBlizzardJump = false;
                }
            } else if (!tsunamiFinished && tsunamiSpeed > 0.0) {
                tsunamiFinished = true;
                isOnTsunamiJump = true;
                jumpKeyDown = true;
                multiJump(localPlayer, tsunamiSpeed);
                localPlayer.playSound(TCSoundEvents.DOUBLE_JUMP.get());
            } else if (!cloudFinished && cloudSpeed > 0.0) {
                cloudFinished = true;
                isOnCloudJump = true;
                jumpKeyDown = true;
                multiJump(localPlayer, cloudSpeed);
                localPlayer.playSound(TCSoundEvents.DOUBLE_JUMP.get());
            } else if (!onFlight) {
                for (Map.Entry<ResourceKey<Item>, ObjectIntPair<MayFlyAbilityValue.FlyStack>> entry : otherFlyStacks.entrySet()) {
                    ObjectIntPair<MayFlyAbilityValue.FlyStack> pair = entry.getValue();
                    int i = pair.rightInt();
                    if (infiniteFlight || i > 0) {
                        float flySpeed = infiniteFlight ? infiniteFlightSpeed : pair.key().flySpeed();
                        fly(pair.left().horizontalFlight(), localPlayer, flySpeed);
                        if (!infiniteFlight) currentFlight = entry.getKey();
                        pair.right(--i);
                        if (infiniteFlight || i > 0) return;
                    }
                }
                currentFlight = null;
                jumpKeyDown = true;
            }
        } else {
            jumpKeyDown = false;
            sandstormFinished = remainSandstormTicks < maxSandstormTicks;
            blizzardFinished = remainBlizzardTicks < maxBlizzardTicks;
            isOnSandstormJump = false;
            isOnBlizzardJump = false;
            onFlight = false;
            onGlide = false;
            currentFlight = null;
        }
    }

    public static @Nullable ResourceKey<Item> getCurrentFlight() {
        return currentFlight;
    }

    private static void fly(boolean horizontalFlight, LocalPlayer localPlayer, float flySpeed) {
        if (horizontalFlight) {
            horizontalFlight(localPlayer, flySpeed);
        } else {
            fly(localPlayer, flySpeed);
        }
    }

    public static void reset(boolean jumpKey) {
        jumpKeyDown = jumpKey;
        fartFinished = false;
        remainSandstormTicks = maxSandstormTicks;
        sandstormFinished = false;
        remainBlizzardTicks = maxBlizzardTicks;
        blizzardFinished = false;
        tsunamiFinished = false;
        cloudFinished = false;
        setupRemainFlyTicks();
        currentFlight = null;
        lastFlight = null;
        onFlight = false;
    }

    public static void multiJump(LocalPlayer localPlayer, float speed) {
        Vec3 vec3 = localPlayer.getDeltaMovement();
        double motionY = ((LivingEntityAccessor) localPlayer).callGetJumpPower(GravitationHandler.getJumpDir()) * speed;
        localPlayer.setDeltaMovement(vec3.x, motionY, vec3.z);
        if (localPlayer.isSprinting()) {
            float f = localPlayer.getYRot() * Mth.DEG_TO_RAD;
            localPlayer.setDeltaMovement(localPlayer.getDeltaMovement().add(-Mth.sin(f) * 0.2, 0.0, Mth.cos(f) * 0.2));
        }
        localPlayer.hasImpulse = true;
        localPlayer.resetFallDistance();
        PacketDistributor.sendToServer(new PlayerJumpPacketC2S((byte) (JUMP_BY_SELF | RESET_FALL_DISTANCE), speed));
    }

    private static void oneTimeJump(LocalPlayer localPlayer, float speed) {
        speed *= GravitationHandler.getJumpDir();
        Vec3 vec3 = localPlayer.getDeltaMovement();
        localPlayer.setDeltaMovement(vec3.x, speed, vec3.z);
        localPlayer.hasImpulse = true;
        localPlayer.resetFallDistance();
        PacketDistributor.sendToServer(new PlayerJumpPacketC2S(RESET_FALL_DISTANCE, speed));
    }

    private static void fly(LocalPlayer localPlayer, float speed) {
        float y = Math.abs((float) localPlayer.getDeltaMovement().y);
        if (y < speed) {
            y += speed / 2.5F;
        } else {
            y = speed;
        }
        airMove(localPlayer, y, localPlayer.getSpeed() + speed);
    }

    private static void glide(LocalPlayer localPlayer) {
        airMove(localPlayer, -0.3F, localPlayer.getSpeed() + 0.4F);
    }

    private static void horizontalFlight(LocalPlayer localPlayer, float speed) {
        airMove(localPlayer, 0.0F, Math.min(localPlayer.getSpeed() * 4.0F + speed - 0.5F, speed + speed));
    }

    private static void airMove(LocalPlayer localPlayer, float y, float h) {
        float jumpDir = GravitationHandler.getJumpDir();
        y *= jumpDir;
        h *= jumpDir;
        float rad = localPlayer.getYRot() * Mth.DEG_TO_RAD;
        float cos = Mth.cos(rad);
        float sin = Mth.sin(rad);
        float v = h * 0.15F;
        float x = localPlayer.xxa * v;
        float z = localPlayer.zza * v * jumpDir;
        double mx = x * cos + z * -sin;
        double mz = x * sin + z * cos;
        Vec3 motion = localPlayer.getDeltaMovement();
        localPlayer.setDeltaMovement(motion.x + mx, y, motion.z + mz);
        localPlayer.hasImpulse = true;
        localPlayer.resetFallDistance();
        PacketDistributor.sendToServer(new PlayerJumpPacketC2S(RESET_FALL_DISTANCE, y));
    }

    public static void handleJumpPacket(
            float fartSpeed,
            float sandstormSpeed,
            int sandstormTicks,
            float blizzardSpeed,
            int blizzardTicks,
            float tsunamiSpeed,
            float cloudSpeed
    ) {
        if (fartSpeed > -1.5) {
            PlayerJumpHandler.fartSpeed = fartSpeed;
        }
        PlayerJumpHandler.sandstormSpeed = sandstormSpeed;
        PlayerJumpHandler.maxSandstormTicks = sandstormTicks;
        PlayerJumpHandler.blizzardSpeed = blizzardSpeed;
        PlayerJumpHandler.maxBlizzardTicks = blizzardTicks;
        if (tsunamiSpeed > -1.5) {
            PlayerJumpHandler.tsunamiSpeed = tsunamiSpeed;
        }
        PlayerJumpHandler.cloudSpeed = cloudSpeed;
    }

    public static void handleFlyPacket(Map<ResourceKey<Item>, MayFlyAbilityValue.FlyStack> map) {
        Map<ResourceKey<Item>, ObjectIntPair<MayFlyAbilityValue.FlyStack>> wings = new LinkedHashMap<>();
        Map<ResourceKey<Item>, ObjectIntPair<MayFlyAbilityValue.FlyStack>> other = new LinkedHashMap<>();
        MutableBoolean b2 = new MutableBoolean();
        MutableFloat f1 = new MutableFloat();
        map.entrySet().stream()
                .sorted(Comparator.comparingInt(entry -> entry.getValue().older()))
                .forEachOrdered(entry -> {
                    MayFlyAbilityValue.FlyStack value = entry.getValue();
                    ObjectIntMutablePair<MayFlyAbilityValue.FlyStack> pair = new ObjectIntMutablePair<>(value, value.flyTicks());
                    if (value.couldGlide()) {
                        wings.put(entry.getKey(), pair);
                    } else {
                        other.put(entry.getKey(), pair);
                    }
                    if (b2.isFalse() && value.horizontalFlight()) b2.setTrue();
                    f1.setValue(Math.max(value.flySpeed(), f1.floatValue()));
                });
        wingsFlyStacks = wings;
        otherFlyStacks = other;
        horizontalFlight = b2.isTrue();
        infiniteFlightSpeed = f1.floatValue();
    }

    private static void setupRemainFlyTicks() {
        wingsFlyStacks.values().forEach(pair -> pair.right(pair.left().flyTicks()));
        otherFlyStacks.values().forEach(pair -> pair.right(pair.left().flyTicks()));
    }

    public static void handleInfiniteFlight(boolean enable) {
        infiniteFlight = enable;
    }

    public static boolean isOnGlide() {
        return onGlide;
    }

    public static boolean isOnFlight() {
        return onFlight;
    }

    public static boolean isOnHorizontalFlight() {
        return onFlight && horizontalFlight;
    }

    public static boolean isInfiniteFlight() {
        return infiniteFlight;
    }
}
