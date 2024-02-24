package net.muffin.mobeffects.statuseffect;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.muffin.mobeffects.MobEffectsMod;

public class GlowsquidStatusEffect extends MobStatusEffect {
    public GlowsquidStatusEffect(EntityType<? extends LivingEntity> mob, StatusEffect[] mimickedEffects, Item[] foodifiedItems, Settings settings) {
        super(mob, mimickedEffects, foodifiedItems, settings);
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        super.applyUpdateEffect(entity, amplifier);
        PlayerEntity player = (PlayerEntity)entity;
        player.getWorld().addParticle(ParticleTypes.GLOW, player.getParticleX(0.6), player.getRandomBodyY(), player.getParticleZ(0.6), 0.0, 0.0, 0.0);
    }

}
