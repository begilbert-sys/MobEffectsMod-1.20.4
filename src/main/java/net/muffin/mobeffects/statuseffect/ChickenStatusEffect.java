package net.muffin.mobeffects.statuseffect;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class ChickenStatusEffect extends StatusEffect {
    public static final String PATHNAME = "chicken";
    public ChickenStatusEffect() {
        super(StatusEffectCategory.NEUTRAL,0xFFFFFF);
    }
}