package org.confluence.terra_curio.mixed;

import net.minecraft.world.entity.Entity;
import org.confluence.lib.mixed.SelfGetter;

///
///
public interface ITCEntity extends SelfGetter<Entity> {
    int terra_curio$getCthulhuSprintingTime();

    void terra_curio$setCthulhuSprintingTime(int amount);

    static ITCEntity of(Entity entity) {
        return (ITCEntity) entity;
    }
}
