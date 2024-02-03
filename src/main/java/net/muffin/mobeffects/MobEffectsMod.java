package net.muffin.mobeffects;

import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.ElderGuardianEntity;
import net.minecraft.entity.mob.GuardianEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.effect.StatusEffect;

import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;


import net.minecraft.registry.Registry;
import net.minecraft.registry.Registries;

import net.minecraft.util.Identifier;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityCombatEvents;

import net.muffin.mobeffects.statuseffect.GuardianStatusEffect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.HashMap;

import net.muffin.mobeffects.statuseffect.ChickenStatusEffect;

import net.muffin.mobeffects.networking.ModMessages;


public class MobEffectsMod implements ModInitializer {
	public static StatusEffect CurrentMobStatus = null;
	public static final String MOD_ID = "mobeffects";
	public static final StatusEffect CHICKENSTATUS = new ChickenStatusEffect();
	public static final StatusEffect GUARDIANSTATUS = new GuardianStatusEffect();
	public static final Map<Class<? extends LivingEntity>, StatusEffect> mobEffects;
	static {
		mobEffects = new HashMap<>();
		mobEffects.put(ChickenEntity.class, CHICKENSTATUS);
		mobEffects.put(GuardianEntity.class, GUARDIANSTATUS);
		mobEffects.put(ElderGuardianEntity.class, GUARDIANSTATUS);
	}
	private static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	private static void addMobStatusEffect(ServerWorld world, Entity entity, LivingEntity killedEntity) {
		if (entity instanceof PlayerEntity && mobEffects.containsKey(killedEntity.getClass())) {
			StatusEffect mobEffect = mobEffects.get(killedEntity.getClass());
			((PlayerEntity) entity).addStatusEffect(new StatusEffectInstance(mobEffect, 12000));
			CurrentMobStatus = mobEffect;
		}
	}
	public void registerStatuses() {
		Registry.register(Registries.STATUS_EFFECT, new Identifier("mobeffects", "chicken"), CHICKENSTATUS);
		Registry.register(Registries.STATUS_EFFECT, new Identifier("mobeffects", "guardian"), GUARDIANSTATUS);
	}
	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		ModMessages.registerC2SPackets();
		registerStatuses();
		ServerEntityCombatEvents.AFTER_KILLED_OTHER_ENTITY.register(MobEffectsMod::addMobStatusEffect);
	}
}