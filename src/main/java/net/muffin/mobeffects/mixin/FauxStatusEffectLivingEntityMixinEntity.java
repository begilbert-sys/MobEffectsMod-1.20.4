package net.muffin.mobeffects.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.LivingEntity;

import net.minecraft.world.World;
import net.muffin.mobeffects.MSEPlayerEntity;
import net.muffin.mobeffects.MobEffectsMod;
import net.muffin.mobeffects.statuseffect.MobStatusEffect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;



@Mixin(LivingEntity.class)
public abstract class FauxStatusEffectLivingEntityMixinEntity extends Entity {
	private static final Logger LOGGER = LoggerFactory.getLogger(MobEffectsMod.MOD_ID);
	@Shadow
	@Final
	private Map<StatusEffect, StatusEffectInstance> activeStatusEffects;

	public FauxStatusEffectLivingEntityMixinEntity(EntityType<?> type, World world) {
		super(type, world);
	}

	@Shadow public abstract boolean addStatusEffect(StatusEffectInstance effect);
	@Shadow
	protected abstract void onStatusEffectRemoved(StatusEffectInstance effect);


	@Inject(method = "hasStatusEffect", at = @At("HEAD"), cancellable = true)
	private void fauxStatusEffect(StatusEffect effect, CallbackInfoReturnable<Boolean> cir) {
		if (this instanceof MSEPlayerEntity MSEPlayer) {
			MobStatusEffect currentMobEffect = MSEPlayer.getMobStatusEffect();
			if (currentMobEffect != null && currentMobEffect.mimicsStatusEffect(effect)) {
				cir.setReturnValue(true);
				cir.cancel();
			}
		}
	}
	@Inject(method = "handleFallDamage", at = @At("HEAD"), cancellable = true)
	private void immuneToFallDamage(CallbackInfoReturnable<Boolean> cir) {
		Entity thisEntity = (Entity)(Object)this;
		if (thisEntity instanceof MSEPlayerEntity MSEPlayerEntity) {
			MobStatusEffect effect =  MSEPlayerEntity.getMobStatusEffect();
			if (effect != null && effect.fallDamageImmune()) {
				cir.setReturnValue(false);
				cir.cancel();
			}
		}
	}
}