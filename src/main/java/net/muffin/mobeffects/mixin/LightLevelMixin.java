package net.muffin.mobeffects.mixin;

import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.muffin.mobeffects.MSEPlayerEntity;
import net.muffin.mobeffects.statuseffect.MobStatusEffect;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/*
Set a custom light level for the PlayerEntity
*/

@Mixin(EntityRenderer.class)
public abstract class LightLevelMixin<T extends Entity> {
    @Inject(method = "getBlockLight", at = @At("HEAD"), cancellable = true)
    private void modifyPlayerEntityLightLevel(T entity, BlockPos pos, CallbackInfoReturnable<Integer> cir) {
        if (entity instanceof MSEPlayerEntity MSEPlayer) {
            MobStatusEffect mobEffect = MSEPlayer.getMobStatusEffect();
            if (mobEffect != null && mobEffect.glows()) {
                cir.setReturnValue(15);
                cir.cancel();
            }
        }
    }
}
