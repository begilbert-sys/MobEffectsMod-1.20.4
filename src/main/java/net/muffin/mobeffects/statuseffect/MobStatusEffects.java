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

    public static final MobStatusEffect BLAZE = create(new BlazeStatusEffect(
            EntityType.BLAZE,
            new StatusEffect[]{StatusEffects.FIRE_RESISTANCE},
            new Item[]{},
            new MobStatusEffect.Settings().hydrophobic(),
            20
    ));
    public static final MobStatusEffect CHICKEN = create(new ChickenStatusEffect(
            EntityType.CHICKEN,
            new StatusEffect[]{StatusEffects.SLOW_FALLING},
            new Item[]{Items.WHEAT_SEEDS, Items.BEETROOT_SEEDS, Items.MELON_SEEDS, Items.PUMPKIN_SEEDS, Items.TORCHFLOWER_SEEDS},
            new MobStatusEffect.Settings(),
            1
    ));
    public static final MobStatusEffect CREEPER = create(new CreeperStatusEffect(
            EntityType.CREEPER,
            new StatusEffect[]{},
            new Item[]{},
            new MobStatusEffect.Settings(),
            20
    ));
    public static final MobStatusEffect ENDERMAN = create(new EndermanStatusEffect(
            EntityType.ENDERMAN,
            new StatusEffect[]{},
            new Item[]{},
            new MobStatusEffect.Settings().hydrophobic(),
            5
    ));
    public static final MobStatusEffect EVOKER = create(new EvokerStatusEffect(
            EntityType.EVOKER,
            new StatusEffect[]{},
            new Item[]{},
            new MobStatusEffect.Settings(),
            20
    ));
    public static final MobStatusEffect GUARDIAN = create(new GuardianStatusEffect(
            EntityType.GUARDIAN,
            new StatusEffect[]{StatusEffects.WATER_BREATHING},
            new Item[]{},
            new MobStatusEffect.Settings(),
            20,
            false
    ));
    public static final MobStatusEffect ELDER_GUARDIAN = create(new GuardianStatusEffect(
            EntityType.ELDER_GUARDIAN,
            new StatusEffect[]{StatusEffects.WATER_BREATHING},
            new Item[]{},
            new MobStatusEffect.Settings(),
            20,
            true
    ));

    public static final MobStatusEffect PANDA = create(new MobStatusEffect(
            EntityType.PANDA,
            new StatusEffect[]{},
            new Item[]{Items.BAMBOO},
            new MobStatusEffect.Settings()
    ));
    public static final MobStatusEffect PIG = create(new MobStatusEffect(
            EntityType.PIG,
            new StatusEffect[]{},
            new Item[]{Items.BEETROOT, Items.POTATO},
            new MobStatusEffect.Settings()
    ));
    public static final MobStatusEffect SLIME = create(new MobStatusEffect(
            EntityType.SLIME,
            new StatusEffect[]{},
            new Item[]{},
            new MobStatusEffect.Settings()
    ));


    public static MobStatusEffect create(MobStatusEffect effect) {
        mobEffectMap.put(effect.getMobType(), effect);
        return effect;
    }
}
