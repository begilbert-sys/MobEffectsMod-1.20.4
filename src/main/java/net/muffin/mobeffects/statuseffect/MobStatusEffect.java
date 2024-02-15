package net.muffin.mobeffects.statuseffect;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

import java.util.ArrayList;

public class MobStatusEffect extends StatusEffect {
    protected final StatusEffect[] mimickedEffects;
    protected final Item[] foodifiedItems;
    protected final EntityType<? extends LivingEntity> mobType;

    public ActionResult UseHandEventListener(PlayerEntity player, World world, EntityHitResult target) {
        return ActionResult.PASS;
    }
    public MobStatusEffect(EntityType<? extends LivingEntity> mob, StatusEffect[] mimickedEffects, Item[] foodifiedItems) {
        super(StatusEffectCategory.NEUTRAL,0xFFFFFF);
        this.mobType = mob;
        this.mimickedEffects = mimickedEffects;
        this.foodifiedItems = foodifiedItems;
    }
    public MobStatusEffect(EntityType<? extends LivingEntity> mob, StatusEffect[] mimickedEffects) { this(mob, mimickedEffects, new Item[]{}); }
    public MobStatusEffect(EntityType<? extends LivingEntity> mob, Item[] foodifiedItems) { this(mob, new StatusEffect[]{}, foodifiedItems); }
    public MobStatusEffect(EntityType<? extends LivingEntity> mob) { this(mob, new StatusEffect[]{}, new Item[]{}); }

    public String toString() {
        return EntityType.getId(mobType).getPath();
    }

    public EntityType<? extends LivingEntity> getMobType() { return mobType; }
    public boolean mimicsStatusEffect(StatusEffect effect) {
        for(StatusEffect e : mimickedEffects) {
            if (e == effect) {
                return true;
            }
        }
        return false;
    }
    public boolean foodifiesItem(Item item) {
        for(Item i: foodifiedItems) {
            if (i == item) {
                return true;
            }
        }
        return false;
    }
}
