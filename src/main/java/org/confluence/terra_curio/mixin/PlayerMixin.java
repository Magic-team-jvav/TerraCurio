package org.confluence.terra_curio.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import org.confluence.terra_curio.common.init.TCItems;
import org.confluence.terra_curio.util.CuriosUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/// 羊毛鞋：佩戴者不再发出脚步声（客户端本地声音与服务端广播一并取消）。
@Mixin(Player.class)
public abstract class PlayerMixin {
    @Inject(method = "playStepSound", at = @At("HEAD"), cancellable = true)
    private void terra_curio$woolShoesSilenceStepSound(BlockPos pos, BlockState state, CallbackInfo ci) {
        if (CuriosUtils.hasCurio((Player) (Object) this, TCItems.WOOL_SHOES.get())) {
            ci.cancel();
        }
    }
}
