package net.muffin.mobeffects.statuseffect;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.item.Item;


public class MobStatusEffect extends StatusEffect {
    protected final StatusEffect[] mimickedEffects;
    protected final Item[] foodifiedItems;
    protected final EntityType<? extends LivingEntity> mobType;

    protected final boolean hydrophobic;
    protected final boolean canWalkOnSnow;

    public MobStatusEffect(EntityType<? extends LivingEntity> mob, StatusEffect[] mimickedEffects, Item[] foodifiedItems, Settings settings) {
        super(StatusEffectCategory.NEUTRAL,0xFFFFFF);
        this.mobType = mob;
        this.mimickedEffects = mimickedEffects;
        this.foodifiedItems = foodifiedItems;
        this.hydrophobic = settings.hydrophobic;
        this.canWalkOnSnow = settings.canWalkOnSnow;
    }

    public String toString() {
        return EntityType.getId(mobType).getPath().toLowerCase();
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
    public boolean hurtByWater() {
        return hydrophobic;
    }

    public static class Settings {
        boolean hydrophobic = false;
        boolean canWalkOnSnow = false;

        public Settings hydrophobic() {
            this.hydrophobic = true;
            return this;
        }
        public Settings canWalkOnSnow() {
            this.canWalkOnSnow = true;
            return this;
        }

    }
}
