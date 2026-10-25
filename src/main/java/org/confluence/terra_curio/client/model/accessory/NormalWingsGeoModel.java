package org.confluence.terra_curio.client.model.accessory;

import net.minecraft.resources.ResourceLocation;
import org.confluence.terra_curio.TerraCurio;
import org.confluence.terra_curio.client.handler.PlayerJumpHandler;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.constant.dataticket.DataTicket;

public class NormalWingsGeoModel extends AccessoryGeoModel {
    public static final DataTicket<State> STATE = new DataTicket<>("state", State.class);
    /// 所有翅膀共用同一套动画（idle / fly / glide），与 1.20.1 一致。
    ///
    /// 不能按物品 id 去找 `animations/accessory/<id>.animation.json`：那批文件只搬来了 `fly`，
    /// `idle` 和 `glide`（缓冲下落）缺失，GeckoLib 找不到动画时会把骨骼姿态留在上一个动画上，
    /// 表现就是「飞得起来但没有缓冲动画」。
    protected static final ResourceLocation DEFAULT_ANIMATION = TerraCurio.asResource("animations/accessory/normal_wings.animation.json");

    public NormalWingsGeoModel(ResourceLocation id) {
        super(id);
    }

    @Override
    public ResourceLocation getAnimationResource(AccessoryGeoModel animatable) {
        return DEFAULT_ANIMATION;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        RawAnimation idle = RawAnimation.begin().thenLoop("idle");
        RawAnimation fly = RawAnimation.begin().thenLoop("fly");
        RawAnimation glide = RawAnimation.begin().thenLoop("glide");
        controllers.add(new AnimationController<>(this, state -> {
            State s = state.getData(STATE);
            if (s != null && s != State.IDLING) {
                return s == State.FLYING ? state.setAndContinue(fly) : state.setAndContinue(glide);
            }
            return state.setAndContinue(idle);
        }));
    }

    public enum State {
        IDLING, FLYING, GLIDING;

        public static State local() {
            return PlayerJumpHandler.isOnFlight() ? State.FLYING : (PlayerJumpHandler.isOnGlide() ? State.GLIDING : State.IDLING);
        }

        public static State remote(double dy) {
            if (dy > 0) return FLYING;
            if (dy < 0) return GLIDING;
            return IDLING;
        }
    }
}
