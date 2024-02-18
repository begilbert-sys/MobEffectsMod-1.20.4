package net.muffin.mobeffects.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.LivingEntity;

import net.minecraft.world.World;
import net.muffin.mobeffects.MobEffectsMod;
import net.muffin.mobeffects.statuseffect.MobStatusEffect;
import net.muffin.mobeffects.statuseffect.MobStatusEffects;
import net.muffin.mobeffects.statuseffect.UseHandMobStatusEffect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.muffin.mobeffects.MobStatusEntity;

import java.util.Map;



@Mixin(LivingEntity.class)
public abstract class FauxStatusEffectMixin extends Entity implements MobStatusEntity {
	private static final Logger LOGGER = LoggerFactory.getLogger(MobEffectsMod.MOD_ID);
	@Shadow
	@Final
	private Map<StatusEffect, StatusEffectInstance> activeStatusEffects;

	public FauxStatusEffectMixin(EntityType<?> type, World world) {
		super(type, world);
	}

	@Shadow public abstract boolean addStatusEffect(StatusEffectInstance effect);
	@Shadow
	protected abstract void onStatusEffectRemoved(StatusEffectInstance effect);
	@Unique
	public MobStatusEffect getMobStatusEffect() {
		for (StatusEffect effect : activeStatusEffects.keySet()) {
			if (effect instanceof MobStatusEffect mobEffect) {
				return mobEffect;
			}
		}
		return null;
	}
	@Unique
	public void setMobStatusEffect(MobStatusEffect effect) {
		MobStatusEffect prevEffect = getMobStatusEffect();
		if (prevEffect != null) {
			this.onStatusEffectRemoved(activeStatusEffects.get(prevEffect));
			activeStatusEffects.remove(prevEffect);
		}
		addStatusEffect(new StatusEffectInstance(effect, 12000));
	}


	@Inject(method = "hasStatusEffect", at = @At("HEAD"), cancellable = true)
	private void injected(StatusEffect effect, CallbackInfoReturnable<Boolean> cir) {
		MobStatusEffect currentMobEffect = getMobStatusEffect();
		if (currentMobEffect != null && currentMobEffect.mimicsStatusEffect(effect)) {
			cir.setReturnValue(true);
			cir.cancel();
		}
	}
	@Inject(method = "hurtByWater", at = @At("HEAD"), cancellable = true)
	private void hurtByWaterInjection(CallbackInfoReturnable<Boolean> cir) {
		MobStatusEffect currentMobEffect = getMobStatusEffect();
		if (currentMobEffect != null && currentMobEffect.hurtByWater()) {
			cir.setReturnValue(true);
			cir.cancel();
		}
	}
}