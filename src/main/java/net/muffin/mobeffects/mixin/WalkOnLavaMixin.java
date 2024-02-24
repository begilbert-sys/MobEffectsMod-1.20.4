package net.muffin.mobeffects.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.world.World;
import net.muffin.mobeffects.MSEPlayerEntity;
import net.muffin.mobeffects.statuseffect.MobStatusEffect;
import net.muffin.mobeffects.statuseffect.MobStatusEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class WalkOnLavaMixin extends Entity {
    public WalkOnLavaMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "canWalkOnFluid", at = @At("HEAD"), cancellable = true)
    private void canWalkOnLava(FluidState state, CallbackInfoReturnable<Boolean> cir) {
        if (state.isIn(FluidTags.LAVA) && this instanceof MSEPlayerEntity MSEPlayer) {
            MobStatusEffect effect = MSEPlayer.getMobStatusEffect();
            if (effect == MobStatusEffects.STRIDER) {
                cir.setReturnValue(true);
                cir.cancel();
            }
        }
    }
}
