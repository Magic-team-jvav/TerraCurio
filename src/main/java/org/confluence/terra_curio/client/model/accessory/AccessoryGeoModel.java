package org.confluence.terra_curio.client.model.accessory;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import org.confluence.terra_curio.TerraCurio;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.model.GeoModel;

public class AccessoryGeoModel extends GeoModel<AccessoryGeoModel> implements GeoAnimatable {
    protected static final ResourceLocation NOTHING_ANIMATION = TerraCurio.asResource("animations/accessory/nothing.animation.json");

    protected final ResourceLocation model;
    protected final ResourceLocation texture;

    protected final AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);

    public AccessoryGeoModel(ResourceLocation id) {
        this(createModelResource(id), createTextureResource(id));
    }

    public static ResourceLocation createTextureResource(ResourceLocation id) {
        return ResourceLocation.fromNamespaceAndPath(id.getNamespace(), "textures/accessory/" + id.getPath() + ".png");
    }

    public static ResourceLocation createModelResource(ResourceLocation id) {
        return ResourceLocation.fromNamespaceAndPath(id.getNamespace(), "geo/accessory/" + id.getPath() + ".geo.json");
    }

    public AccessoryGeoModel(ResourceLocation model, ResourceLocation texture) {
        this.model = model;
        this.texture = texture;
    }

    @Override
    public ResourceLocation getModelResource(AccessoryGeoModel animatable) {
        return model;
    }

    @Override
    public ResourceLocation getTextureResource(AccessoryGeoModel animatable) {
        return texture;
    }

    @Override
    public ResourceLocation getAnimationResource(AccessoryGeoModel animatable) {
        return NOTHING_ANIMATION;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {}

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public double getTick(Object object) {
        return ((LivingEntity) object).tickCount;
    }

    public BakedGeoModel getBakedModel() {
        return getBakedModel(model);
    }

    public ResourceLocation getTexture() {
        return texture;
    }
}
