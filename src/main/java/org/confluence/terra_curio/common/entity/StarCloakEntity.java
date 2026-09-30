package org.confluence.terra_curio.common.entity;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.confluence.lib.common.LibDamageTypes;
import org.confluence.lib.util.LibEntityUtils;
import org.confluence.lib.util.LibUtils;
import org.confluence.terra_curio.common.init.TCEntities;

public class StarCloakEntity extends Projectile {
    private static final EntityDataAccessor<Boolean> DATA_MANA = SynchedEntityData.defineId(StarCloakEntity.class, EntityDataSerializers.BOOLEAN);
    private final boolean hasMana;
    private int age;

    public StarCloakEntity(EntityType<StarCloakEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.hasMana = false;
    }

    public StarCloakEntity(Level level, LivingEntity living, Entity target, boolean hasMana) {
        super(TCEntities.STAR_CLOAK.get(), level);
        this.hasMana = hasMana;
        setOwner(living);
        setPos(target.position().add(0.0, 32.0, 0.0));
        setDeltaMovement(0.0, -2.0, 0.0);
        setNoGravity(true);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(DATA_MANA, false);
    }

    @Override
    public void tick() {
        if (getOwner() == null) {
            discard();
            return;
        }
        super.tick();
        HitResult hitresult = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);
        Vec3 vec3 = getDeltaMovement();
        HitResult.Type hitresult$type = hitresult.getType();
        if (hitresult$type == HitResult.Type.BLOCK) {
            if (hasMana) {
                entityData.set(DATA_MANA, true);
            } else {
                discard();
                return;
            }
        }
        if (hitresult.getLocation().y < getOwner().getY()) {
            discard();
            return;
        }
        if (hitresult$type == HitResult.Type.ENTITY) {
            onHitEntity((EntityHitResult) hitresult);
        }
        boolean mana = isManaState();
        if (mana) {
            addDeltaMovement(getOwner().position().subtract(position()).normalize().scale(0.05));
        }
        double offX = getX() + vec3.x;
        double offY = getY() + vec3.y;
        double offZ = getZ() + vec3.z;
        setPos(offX, offY, offZ);
        if ((mana && age++ > 100) || tickCount > 200) {
            discard();
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        if (isManaState()) {
            LibUtils.forMixin$Inject();
        } else {
            pResult.getEntity().hurt(LibDamageTypes.of(level(), LibDamageTypes.STAR_CLOAK, getOwner()), 10.0F);
        }
    }

    private Boolean isManaState() {
        return entityData.get(DATA_MANA);
    }

    @Override
    protected boolean canHitEntity(Entity target) {
        if (!target.canBeHitByProjectile()) {
            return false;
        } else if (isManaState()) {
            return target == getOwner();
        } else {
            return LibEntityUtils.canHitEntity(target, getOwner());
        }
    }

    @Override
    public boolean canChangeDimensions(Level oldLevel, Level newLevel) {
        return false;
    }
}
