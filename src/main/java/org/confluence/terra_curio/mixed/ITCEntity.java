package org.confluence.terra_curio.mixed;

import net.minecraft.world.entity.Entity;
import org.confluence.lib.mixed.SelfGetter;

/// TC 侧留在自己身上的那半条「实体扩展」接口。
///
/// WP6c 第二步（TerraCurio 侧拆除）：重力那半（`isShouldRot`/`setShouldRot`/`getDimensionHeight`）
/// 已按 1.20 的归属搬去 Lib 的 `org.confluence.lib.mixed.ILibEntity`（方法名 `confluence$…`），
/// 本接口只保留**克苏鲁冲刺计时**这两个成员 —— 与 1.20
/// `TerraCurio/.../mixed/ITCEntity.java` 逐字一致。
///
/// 原 1.21 的 `IEntity` 还把 `terra_curio$isPlayer()` 塞了进来，那是 1.20 没有的发明
/// （1.20 一律直接写 `instanceof Player`），已随本次拆除一并去掉。
public interface ITCEntity extends SelfGetter<Entity> {
    int terra_curio$getCthulhuSprintingTime();

    void terra_curio$setCthulhuSprintingTime(int amount);

    static ITCEntity of(Entity entity) {
        return (ITCEntity) entity;
    }
}
