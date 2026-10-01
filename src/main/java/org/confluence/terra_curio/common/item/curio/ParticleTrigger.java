package org.confluence.terra_curio.common.item.curio;

import net.minecraft.world.entity.LivingEntity;

/**
 * 饰品粒子的激活条件，声明在 {@link BaseCurioItem.Builder} 上，由基类统一驱动播放，
 * 子类无需再覆写任何粒子方法。
 * <p>
 * 注意：实现允许（且跳跃类触发器确实会）在读取时消费实体上的状态
 * （例如把一次性 pending 标志置回 false、把 tick 计数递减），
 * 因此每 tick 最多只能被同一个 emitter 调用一次。
 */
@FunctionalInterface
public interface ParticleTrigger {
    /** @return true 表示本 tick 该 emitter 应激活 */
    boolean shouldActivate(LivingEntity living);
}
