package net.muffin.mobeffects.statuseffect;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;

public class PufferfishStatusEffect extends MobStatusEffect implements ModifyDamageMSE {
    public PufferfishStatusEffect(EntityType<? extends LivingEntity> mob, StatusEffect[] mimickedEffects, Item[] foodifiedItems, MobStatusEffect.Settings settings) {
        super(mob, mimickedEffects, foodifiedItems, settings);
    }

    public ActionResult AllowDamageEventListener(PlayerEntity player, DamageSource source, float amount) {
        Entity attacker = source.getAttacker();
        if (attacker instanceof LivingEntity livingEntity) {
            livingEntity.damage(player.getDamageSources().thorns(player), 2.0f);
            livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 180, 0), player);
            player.playSound(SoundEvents.ENTITY_PUFFER_FISH_STING, 1.0f, 1.0f);
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }
}
