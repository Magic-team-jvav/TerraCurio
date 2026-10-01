package org.confluence.terra_curio.util;

/// 每个实体独立的跳跃粒子状态。
///
/// 本地玩家的状态由 [org.confluence.terra_curio.client.handler.PlayerJumpHandler] 在输入事件中写入；
/// 远程玩家的状态由服务端广播的跳跃事件包（`PlayerJumpTriggeredPacketS2C`）写入，
/// 避免本地玩家的跳跃把客户端上所有佩戴同款饰品的实体粒子一并点亮。
public final class JumpParticleState {
    /// 沙暴瓶：大于 0 表示激活中，由粒子触发器每 tick 递减
    public int sandstormTicks;
    /// 暴雪瓶：同上
    public int blizzardTicks;
    /// 海啸瓶：一次性触发标志，由粒子触发器消费
    public boolean tsunamiPending;
    /// 云朵瓶：一次性触发标志，由粒子触发器消费
    public boolean cloudPending;
    /// 罐中臭屁：一次性触发标志，由粒子触发器消费
    public boolean fartPending;
    /// 火箭靴：起飞瞬间一次性触发标志，由粒子触发器消费
    public boolean rocketBoostPending;
    /// 忍者冲刺：冲刺瞬间激活的 tick 数，由粒子触发器递减
    public int sprintDashTicks;
    /// 攀爬爪：正在贴墙攀爬的 tick 标志，由粒子触发器递减
    public int climbingTicks;

    public void clear() {
        sandstormTicks = 0;
        blizzardTicks = 0;
        tsunamiPending = false;
        cloudPending = false;
        fartPending = false;
        rocketBoostPending = false;
        climbingTicks = 0;
        // 注意：sprintDashTicks 不在此清零——clear() 会在落地分支每 tick 调用，
        // 而冲刺残影应播满 3 tick（含落地后），否则地上冲刺只出 1 个粒子。
    }
}
