package org.confluence.terra_curio.common.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.*;

import org.confluence.lib.util.LibEntityUtils;
import org.confluence.terra_curio.common.init.TCEntities;
import org.jetbrains.annotations.Nullable;

public class BeeProjectile extends Projectile {
    private static final LibEntityUtils.WeaponTargeting TARGETING = new LibEntityUtils.WeaponTargeting(LibEntityUtils.TargetPolicy.ENEMY_ONLY);

    protected static final EntityDataAccessor<Boolean> DATA_IS_GIANT = SynchedEntityData.defineId(BeeProjectile.class, EntityDataSerializers.BOOLEAN);
    protected static final EntityDimensions SMALL = TCEntities.BEE_PROJECTILE.get().getDimensions().scale(0.5f);
    protected static final EntityDimensions GIANT = TCEntities.BEE_PROJECTILE.get().getDimensions();

    protected int blockHitCount;
    protected float baseDamage = 5.0F;
    protected transient Entity target;

    public BeeProjectile(EntityType<? extends BeeProjectile> entityType, Level level) {
        super(entityType, level);
        this.blockHitCount = 0;
    }

    public BeeProjectile(EntityType<? extends BeeProjectile> entityType, Level level, @Nullable LivingEntity owner, boolean isGiant) {
        this(entityType, level);
        setOwner(owner);
        this.blockHitCount = 0;
        entityData.set(DATA_IS_GIANT, isGiant);
    }

    public BeeProjectile(Level level, @Nullable LivingEntity owner, boolean isGiant) {
        this(TCEntities.BEE_PROJECTILE.get(), level, owner, isGiant);
    }

    public void setBaseDamage(float baseDamage) {
        this.baseDamage = baseDamage;
    }

    @Override
    protected void defineSynchedData() {
        entityData.define(DATA_IS_GIANT, false);
    }

    public boolean isGiant() {
        return entityData.get(DATA_IS_GIANT);
    }

    @Override
    public void tick() {
        trackTarget();
        if (tickCount % 4 == 0) {
            AABB boundingBox = getBoundingBox().inflate(1.0);
            HitResult hitresult = ProjectileUtil.getEntityHitResult(
                    level(), this,
                    boundingBox.getMinPosition(),
                    boundingBox.getMaxPosition(),
                    boundingBox, this::canHitEntity
            );
            if (hitresult instanceof EntityHitResult entityHitResult) {
                onHitEntity(entityHitResult);
            }
        }

        checkInsideBlocks();
        updateRotation();
        Vec3 vec3 = getDeltaMovement();
        move(MoverType.SELF, vec3);
        Vec3 motion = getDeltaMovement();
        if (motion.x != vec3.x || motion.y != vec3.y || motion.z != vec3.z) {
            if (motion.x != vec3.x) motion = new Vec3(-vec3.x, vec3.y, vec3.z);
            if (motion.y != vec3.y) motion = new Vec3(vec3.x, -vec3.y, vec3.z);
            if (motion.z != vec3.z) motion = new Vec3(vec3.x, vec3.y, -vec3.z);
            setDeltaMovement(motion);
            blockHitCount++;
        }
        if (this.getInBlockState().liquid()) discard();
        else if (blockHitCount > (isGiant() ? 2 : 1) || tickCount > (isGiant() ? 220 : 200))
            discard();

        HitResult hitresult = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);
        checkInsideBlocks();
        HitResult.Type hitresult$type = hitresult.getType();
        if (hitresult$type == HitResult.Type.BLOCK) {
            onHitBlock((BlockHitResult) hitresult);
        }
    }

    protected void trackTarget() {
        if (target == null) {
            double d0 = -1.0;
            Entity enemy = null;
            for (Entity entity : level().getEntities(this, new AABB(blockPosition()).inflate(8))) {
                if (canAcquireTarget(entity)) {
                    double d1 = entity.distanceToSqr(getX(), getY(), getZ());
                    if (d0 == -1.0 || d1 < d0) {
                        d0 = d1;
                        enemy = entity;
                    }
                }
            }
            this.target = enemy;
        }
        if (target != null) {
            if (!canKeepTarget(target))
                this.target = null;
            if (target != null && canKeepTarget(target)) {
                Vec3 vec3 = target.getBoundingBox().getCenter().subtract(position()).normalize();
                addDeltaMovement(vec3.scale(0.95).scale(isGiant() ? 0.15 : 0.05));
            }
        }
    }

    protected boolean canAcquireTarget(Entity candidate) {
        return TARGETING.canAcquire(getOwner(), candidate);
    }

    protected boolean canKeepTarget(Entity candidate) {
        return TARGETING.canKeep(getOwner(), candidate);
    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        Entity entity = entityHitResult.getEntity();
        if (!level().isClientSide && getOwner() != null) {
            float damage = baseDamage + (isGiant() ? random.nextInt(1, 4) : (random.nextBoolean() ? 1 : 0));
            entity.hurt(getDamageSource(), damage);
            if (isGiant()) {
                Entity target = LibEntityUtils.encounterOwner(entity);
                Vec3 motion = target.position().subtract(position()).normalize().scale(0.5);
                target.push(motion.x, motion.y, motion.z);
            }
        }
    }

    protected DamageSource getDamageSource() {
        return damageSources().indirectMagic(this, getOwner());
    }

    @Override
    public EntityDimensions getDimensions(Pose pPose) {
        return isGiant() ? GIANT : SMALL;
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        super.onSyncedDataUpdated(key);
        if (DATA_IS_GIANT.equals(key)) {
            refreshDimensions();
            setBoundingBox(makeBoundingBox());
        }
    }

    @Override
    protected boolean canHitEntity(Entity target) {
        return LibEntityUtils.canHit(getOwner(), target);
    }

    @Override
    public void shootFromRotation(Entity shooter, float x, float y, float z, float velocity, float inaccuracy) {
        float cos = Mth.cos(x * Mth.DEG_TO_RAD);
        float value = y * Mth.DEG_TO_RAD;
        float f = -Mth.sin(value) * cos;
        float f1 = -Mth.sin((x + z) * Mth.DEG_TO_RAD);
        float f2 = Mth.cos(value) * cos;
        shoot(f, f1, f2, velocity, inaccuracy);
    }

    @Override
    public boolean shouldRender(double x, double y, double z) {
        return true;
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.tickCount = compound.getInt("Age");
        this.blockHitCount = compound.getInt("BlockHitCount");
        this.baseDamage = compound.getFloat("BaseDamage");
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("Age", tickCount);
        compound.putInt("BlockHitCount", blockHitCount);
        compound.putFloat("BaseDamage", baseDamage);
    }

    @Override
    public boolean canChangeDimensions() {
        return false;
    }
}
