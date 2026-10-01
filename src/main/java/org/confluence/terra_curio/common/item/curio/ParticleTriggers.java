package org.confluence.terra_curio.common.item.curio;

import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.NeoForgeMod;
import org.confluence.terra_curio.client.handler.PlayerJumpHandler;
import org.confluence.terra_curio.common.init.TCItems;
import org.confluence.terra_curio.common.init.TCTags;
import org.confluence.terra_curio.mixed.ITCEntity;
import org.confluence.terra_curio.mixed.ITCLivingEntity;
import org.confluence.terra_curio.util.JumpParticleState;

/// 预置的粒子触发器，供 [#particle] 声明式使用。
///
/// 跳跃类触发器读取/消费实体上独立的 [JumpParticleState]，
/// 因此本地与远程玩家互不干扰，多能力合一的饰品（如气球束）可以按跳跃类型分段播放。
///
/// 触发器只在客户端执行（curioTick 的客户端分支），因此可以安全引用客户端状态
/// （如 [PlayerJumpHandler]），但要注意这类状态只有本地玩家才有，
/// 依赖它的触发器不会在远程玩家身上生效。
public final class ParticleTriggers {
    private ParticleTriggers() {
    }

    /// 沙暴瓶/沙暴气球：按住跳跃且剩余跳跃次数 > 0 时激活（持续）
    public static final ParticleTrigger SANDSTORM_JUMP = living -> {
        JumpParticleState state = ITCLivingEntity.of(living).terra_curio$getJumpParticleState();
        boolean active = state.sandstormTicks > 0;
        if (active) {
            state.sandstormTicks--;
        }
        return active;
    };

    /// 暴雪瓶/暴雪气球：同沙暴
    public static final ParticleTrigger BLIZZARD_JUMP = living -> {
        JumpParticleState state = ITCLivingEntity.of(living).terra_curio$getJumpParticleState();
        boolean active = state.blizzardTicks > 0;
        if (active) {
            state.blizzardTicks--;
        }
        return active;
    };

    /// 海啸瓶/鲨鱼龙气球：一次性跳跃触发
    public static final ParticleTrigger TSUNAMI_JUMP = living -> {
        JumpParticleState state = ITCLivingEntity.of(living).terra_curio$getJumpParticleState();
        boolean active = state.tsunamiPending;
        state.tsunamiPending = false;
        return active;
    };

    /// 云朵瓶/云朵气球：一次性跳跃触发
    public static final ParticleTrigger CLOUD_JUMP = living -> {
        JumpParticleState state = ITCLivingEntity.of(living).terra_curio$getJumpParticleState();
        boolean active = state.cloudPending;
        state.cloudPending = false;
        return active;
    };

    /// 泰拉闪耀靴等：向前移动且未撞墙时激活
    public static final ParticleTrigger RUNNING = living -> living.zza > 0.0F && !living.horizontalCollision;

    /// 冰冻海龟壳：生命值低于一半时激活
    public static final ParticleTrigger LOW_HEALTH = living -> living.getHealth() / living.getMaxHealth() < 0.5F;

    /// 火箭靴：本地玩家正以火箭靴飞行时激活（仅本地玩家可见）。
    /// 注意不能依赖 [PlayerJumpHandler#isOnFlight]：火箭靴飞行走 otherFlyStacks 分支，
    /// 该分支不会把 onFlight 置 true，只有翅膀循环会。与火箭靴音效一样改用 getCurrentFlight() 判定。
    public static final ParticleTrigger ROCKET_FLYING = living -> living instanceof Player player &&
                    player.isLocalPlayer() &&
                    TCItems.ROCKET_BOOTS.getKey().equals(PlayerJumpHandler.getCurrentFlight());

    /// 潜水装备/水母系列：实体处于水中时激活（远程玩家同样生效）
    public static final ParticleTrigger UNDERWATER = Entity::isInWater;

    /// 克苏鲁护盾：正在冲刺时激活（本地玩家可见）
    public static final ParticleTrigger DASHING = living -> living instanceof Player player &&
            ITCEntity.of(player).terra_curio$getCthulhuSprintingTime() >= 20;

    /// 翅膀/飞行饰品：本地玩家处于飞行状态时激活（翅膀循环会置 onFlight，仅本地玩家可见）
    public static final ParticleTrigger FLYING = living -> living instanceof Player player &&
            player.isLocalPlayer() &&
            PlayerJumpHandler.isOnFlight() || PlayerJumpHandler.isOnGlide();

    /// 花靴：站在可长花的地面上时激活（远程玩家同样生效）
    public static final ParticleTrigger WALKING_ON_GRASS = living -> living.onGround() &&
            living.level().getBlockState(living.getOnPos()).is(TCTags.FLOWER_BOOTS_AVAILABLE);

    /// 熔岩靴：站在可走熔岩面上且正在移动，或浸入熔岩中时激活（远程玩家同样生效）。
    /// 与水上漂同理：熔岩靴行走时玩家踩在熔岩上方（AABB 不浸入，isInLava 不触发），
    /// 正确信号是"眼睛未浸入液体 + 脚下方块为熔岩"；完全入熔岩时仍走 isInLava。
    public static final ParticleTrigger IN_LAVA = living -> living.isInLava() ||
            (living.isEyeInFluidType(NeoForgeMod.EMPTY_TYPE.value()) &&
             living.getBlockStateOn().getFluidState().is(FluidTags.LAVA) &&
             living.getDeltaMovement().horizontalDistanceSqr() > 0.01);

    /// 幽灵靴：本地玩家正以幽灵靴飞行时激活（仅本地玩家可见）
    public static final ParticleTrigger SPECTRE_FLYING = flyingBoot(TCItems.SPECTRE_BOOTS.getKey());

    /// 仙灵靴：本地玩家正以仙灵靴飞行时激活（仅本地玩家可见）
    public static final ParticleTrigger FAIRY_FLYING = flyingBoot(TCItems.FAIRY_BOOTS.getKey());

    /// 闪电靴：本地玩家正以闪电靴飞行时激活（仅本地玩家可见）
    public static final ParticleTrigger LIGHTNING_FLYING = flyingBoot(TCItems.LIGHTNING_BOOTS.getKey());

    /// 霜花靴：本地玩家正以霜花靴飞行时激活（仅本地玩家可见）
    public static final ParticleTrigger FROSTSPARK_FLYING = flyingBoot(TCItems.FROSTSPARK_BOOTS.getKey());

    /// 泰拉闪耀靴：本地玩家正以泰拉闪耀靴飞行时激活（仅本地玩家可见）
    public static final ParticleTrigger TERRASPARK_FLYING = flyingBoot(TCItems.TERRASPARK_BOOTS.getKey());

    /// 飞行靴通用触发器：靴子飞行走 otherFlyStacks 分支（onFlight 不置 true），
    /// 因此与火箭靴一样用 getCurrentFlight() 判定当前飞行源。
    private static ParticleTrigger flyingBoot(ResourceKey<Item> key) {
        return living -> living instanceof Player player &&
                player.isLocalPlayer() &&
                key.equals(PlayerJumpHandler.getCurrentFlight());
    }

    /// 罐中臭屁/臭屁气球：一次性跳跃触发
    public static final ParticleTrigger FART_JUMP = living -> {
        JumpParticleState state = ITCLivingEntity.of(living).terra_curio$getJumpParticleState();
        boolean active = state.fartPending;
        state.fartPending = false;
        return active;
    };

    /// 水上漂靴：站在可走水面上且正在移动时激活（xxa/zza 为移动输入，仅本地玩家可见）。
    /// 注意不能只用 isInWater()：水上行走时玩家并未进入水方块（AABB 不与水相交），
    /// 只有下潜几个像素才判定入水。正确信号是"眼睛未浸入液体 + 脚下方块为水"，
    /// 与 TCUtils.applyFluidWalk 的水上行走判定一致。
    public static final ParticleTrigger WATER_WALKING = living ->
            living.isEyeInFluidType(NeoForgeMod.EMPTY_TYPE.value()) &&
            living.getBlockStateOn().getFluidState().is(FluidTags.WATER) &&
            living.getDeltaMovement().horizontalDistanceSqr() > 0.01;

    /// 溜冰鞋/冰面靴：站在冰上且正在移动时激活（远程玩家同样生效）
    public static final ParticleTrigger ON_ICE = living ->
            living.getBlockStateOn().is(BlockTags.ICE) && living.getDeltaMovement().horizontalDistanceSqr() > 0.01;

    /// 飞毯：本地玩家正以飞毯飞行时激活（仅本地玩家可见）
    public static final ParticleTrigger CARPET_FLYING = flyingBoot(TCItems.FLYING_CARPET.getKey());

    /// 翱翔徽章：无限飞行状态激活时生效（仅本地玩家可见）
    public static final ParticleTrigger INFINITE_FLYING = living -> living instanceof Player player &&
            player.isLocalPlayer() &&
            PlayerJumpHandler.isInfiniteFlight();

    /// 常驻：始终激活（用于岩浆骷髅等全身环境光效）
    public static final ParticleTrigger ALWAYS = living -> true;

    /// 火箭靴：起飞瞬间一次性爆发闪光（由 PlayerJumpHandler 在飞行源切换时置位）
    public static final ParticleTrigger ROCKET_BOOST = living -> {
        JumpParticleState state = ITCLivingEntity.of(living).terra_curio$getJumpParticleState();
        boolean active = state.rocketBoostPending;
        state.rocketBoostPending = false;
        return active;
    };

    /// 忍者冲刺（分趾厚底袜/忍者大师装备）：冲刺瞬间激活 3 tick 风痕（由 PlayerSprintingHandler 置位）
    public static final ParticleTrigger SPRINT_DASH = living -> {
        JumpParticleState state = ITCLivingEntity.of(living).terra_curio$getJumpParticleState();
        boolean active = state.sprintDashTicks > 0;
        if (active) {
            state.sprintDashTicks--;
        }
        return active;
    };

    /// 脚蹼/青蛙脚蹼：在水中快速游动时激活（远程玩家同样生效）
    public static final ParticleTrigger SWIMMING = living ->
            living.isInWater() && !living.onGround() && living.getDeltaMovement().horizontalDistanceSqr() > 0.04;

    /// 攀爬爪/鞋钉等：正在贴墙攀爬时激活（由 PlayerClimbHandler 置位）
    public static final ParticleTrigger WALL_CLIMBING = living -> {
        JumpParticleState state = ITCLivingEntity.of(living).terra_curio$getJumpParticleState();
        boolean active = state.climbingTicks > 0;
        if (active) {
            state.climbingTicks--;
        }
        return active;
    };

    /// 内胎：漂浮在水面上时激活（不要求移动）
    public static final ParticleTrigger FLOATING_ON_WATER = living ->
            living.isEyeInFluidType(NeoForgeMod.EMPTY_TYPE.value()) &&
            living.getBlockStateOn().getFluidState().is(FluidTags.WATER);
}
