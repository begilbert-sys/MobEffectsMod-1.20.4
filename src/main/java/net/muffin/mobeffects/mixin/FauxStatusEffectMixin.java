package net.muffin.mobeffects.mixin;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.LivingEntity;

import net.muffin.mobeffects.statuseffect.MobStatusEffects;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.muffin.mobeffects.MobEffectsMod;

import java.util.Map;



@Mixin(LivingEntity.class)
public abstract class FauxStatusEffectMixin {
	@Shadow
	@Final
	private Map<StatusEffect, StatusEffectInstance> activeStatusEffects;

	@Inject(method = "hasStatusEffect", at = @At("HEAD"), cancellable = true)
	private void injected(StatusEffect effect, CallbackInfoReturnable<Boolean> cir) {
		if (effect == StatusEffects.SLOW_FALLING && activeStatusEffects.containsKey(MobStatusEffects.CHICKEN)) {
			cir.setReturnValue(true);
			cir.cancel();
		}
	}
}