package net.muffin.mobeffects;


import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.effect.StatusEffect;

import net.minecraft.server.world.ServerWorld;


import net.minecraft.registry.Registry;
import net.minecraft.registry.Registries;

import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityCombatEvents;


import net.muffin.mobeffects.event.UseHandCallback;
import net.muffin.mobeffects.statuseffect.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import net.muffin.mobeffects.networking.ModMessages;


public class MobEffectsMod implements ModInitializer {
	public static final String MOD_ID = "mobeffects";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	private static void addMobStatusEffect(ServerWorld world, Entity entity, LivingEntity killedEntity) {
		if (entity instanceof PlayerEntity && MobStatusEffects.mobEffectMap.containsKey(killedEntity.getType())) {
			MobStatusEffect mobEffect = MobStatusEffects.mobEffectMap.get(killedEntity.getType());
			((MSEPlayerEntity) entity).setMobStatusEffect(mobEffect);
		}
	}

	public void registerMobStatusEffects() {
		// Register mob effects
		for (MobStatusEffect effect : MobStatusEffects.mobEffectMap.values()) {
			// Register status effects
			Registry.register(Registries.STATUS_EFFECT, new Identifier(MOD_ID, effect.toString()), effect);

			// Register UseHandCallback event listeners
			if (effect instanceof UseHandMobStatusEffect useHandEffect) {
				UseHandCallback.EVENT.register((player, world, target) -> {
					if (player.hasStatusEffect(effect)) {
						return useHandEffect.UseHandEventListener(player, world, target);
					}
					return ActionResult.PASS;
				});
			}
			if (effect instanceof SnifferStatusEffect snifferEffect) {
				PlayerBlockBreakEvents.AFTER.register(snifferEffect::afterPlayerBlockBreakEventListener);
			}
		}
	}
	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		ModMessages.registerC2SPackets();
		registerMobStatusEffects();
		ServerEntityCombatEvents.AFTER_KILLED_OTHER_ENTITY.register(MobEffectsMod::addMobStatusEffect);
		ServerLivingEntityEvents.ALLOW_DAMAGE.register((livingEntity, source, amount) -> {
			if (livingEntity instanceof PlayerEntity player && ((MSEPlayerEntity)player).getMobStatusEffect() instanceof ModifyDamageMSE modifyDamageEffect) {
				modifyDamageEffect.AllowDamageEventListener(player, source, amount);
			}
			return true;
		});
	}
}