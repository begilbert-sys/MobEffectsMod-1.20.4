package net.muffin.mobeffects;


import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.effect.StatusEffect;

import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;


import net.minecraft.registry.Registry;
import net.minecraft.registry.Registries;

import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityCombatEvents;


import net.muffin.mobeffects.event.UseHandCallback;
import net.muffin.mobeffects.statuseffect.MobStatusEffect;
import net.muffin.mobeffects.statuseffect.MobStatusEffects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.HashMap;


import net.muffin.mobeffects.networking.ModMessages;


public class MobEffectsMod implements ModInitializer {
	public static final String MOD_ID = "mobeffects";
	private static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	private static void addMobStatusEffect(ServerWorld world, Entity entity, LivingEntity killedEntity) {
		if (entity instanceof PlayerEntity && MobStatusEffects.mobEffectMap.containsKey(killedEntity.getType())) {
			MobStatusEffect mobEffect = MobStatusEffects.mobEffectMap.get(killedEntity.getType());
			((MobStatusEntity) entity).setMobStatusEffect(mobEffect);
		}
	}
	public void registerMobStatusEffects() {
		for (MobStatusEffect effect : MobStatusEffects.mobEffectMap.values()) {
			// Register status effects
			Registry.register(Registries.STATUS_EFFECT, new Identifier(MOD_ID, effect.toString().toLowerCase()), effect);
			// Register UseHandCallback event listeners
			UseHandCallback.EVENT.register((player, world, target) -> {
				if (player.hasStatusEffect(effect)) {
					return effect.UseHandEventListener(player, world, target);
				}
				return ActionResult.PASS;
			});
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
	}
}