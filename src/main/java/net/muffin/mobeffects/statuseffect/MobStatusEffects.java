package net.muffin.mobeffects.statuseffect;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

import java.util.HashMap;
import java.util.Map;

public class MobStatusEffects extends StatusEffects {
    public static Map<EntityType<? extends LivingEntity>, MobStatusEffect> mobEffectMap = new HashMap<>();
    public static final StatusEffect CHICKEN = create(new ChickenStatusEffect(
            EntityType.CHICKEN,
            new StatusEffect[]{StatusEffects.SLOW_FALLING},
            new Item[]{Items.WHEAT_SEEDS, Items.BEETROOT_SEEDS, Items.MELON_SEEDS, Items.PUMPKIN_SEEDS, Items.TORCHFLOWER_SEEDS}
    ));
    public static final StatusEffect GUARDIAN = create(new GuardianStatusEffect(
            EntityType.GUARDIAN,
            new StatusEffect[]{StatusEffects.WATER_BREATHING},
            new Item[]{}
    ));
    public static final StatusEffect ELDER_GUARDIAN = create(new GuardianStatusEffect(
            EntityType.ELDER_GUARDIAN,
            new StatusEffect[]{StatusEffects.WATER_BREATHING},
            new Item[]{}
    ));
    public static final StatusEffect EVOKER = create(new EvokerStatusEffect(
            EntityType.EVOKER,
            new StatusEffect[]{},
            new Item[]{}
    ));
    public static final StatusEffect PANDA = create(new MobStatusEffect(
            EntityType.PANDA,
            new StatusEffect[]{},
            new Item[]{Items.BAMBOO}
    ));

    public static MobStatusEffect create(MobStatusEffect effect) {
        mobEffectMap.put(effect.getMobType(), effect);
        return effect;
    }
}
