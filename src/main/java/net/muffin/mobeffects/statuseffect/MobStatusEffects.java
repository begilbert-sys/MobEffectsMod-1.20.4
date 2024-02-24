package net.muffin.mobeffects.statuseffect;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
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
            new MobStatusEffect.Settings().glows().hydrophobic(),
            20
    ));
    public static final MobStatusEffect CAT = create(new MobStatusEffect(
            EntityType.CAT,
            new StatusEffect[]{},
            new Item[]{Items.COD, Items.SALMON},
            new MobStatusEffect.Settings().fallDamageImmune()
            ).addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, "91AEAA56-376B-4498-935B-2F7F68070635", 0.2f, EntityAttributeModifier.Operation.MULTIPLY_TOTAL)
    );
    public static final MobStatusEffect OCELOT = create(new MobStatusEffect(
            EntityType.OCELOT,
            new StatusEffect[]{},
            new Item[]{Items.COD, Items.SALMON},
            new MobStatusEffect.Settings().fallDamageImmune()
            ).addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, "91AEAA56-376B-4498-935B-2F7F68070635", 0.2f, EntityAttributeModifier.Operation.MULTIPLY_TOTAL)
    );
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
    public static final MobStatusEffect GLOW_SQUID = create(new GlowsquidStatusEffect(
            EntityType.GLOW_SQUID,
            new StatusEffect[]{StatusEffects.WATER_BREATHING},
            new Item[]{},
            new MobStatusEffect.Settings().glows().suffocatesAboveWater()
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
    public static final MobStatusEffect PARROT = create(new MobStatusEffect(
            EntityType.PARROT,
            new StatusEffect[]{},
            new Item[]{},
            new MobStatusEffect.Settings().flutters().jukeboxHealing()
    ));
    public static final MobStatusEffect PIG = create(new MobStatusEffect(
            EntityType.PIG,
            new StatusEffect[]{},
            new Item[]{Items.BEETROOT, Items.POTATO},
            new MobStatusEffect.Settings()
    ));
    public static final MobStatusEffect PUFFERFISH = create(new PufferfishStatusEffect(
            EntityType.PUFFERFISH,
            new StatusEffect[]{},
            new Item[]{},
            new AbstractMobStatusEffect.Settings().suffocatesAboveWater()
    ));
    public static final MobStatusEffect SLIME = create(new SlimeStatusEffect(
            EntityType.SLIME,
            new StatusEffect[]{},
            new Item[]{},
            new MobStatusEffect.Settings().fallDamageImmune(),
            1
    ));
    public static final MobStatusEffect SNIFFER = create(new SnifferStatusEffect(
            EntityType.SNIFFER,
            new StatusEffect[]{},
            new Item[]{Items.TORCHFLOWER_SEEDS},
            new MobStatusEffect.Settings()
            ).addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, "7107DE5E-7CE8-4030-940E-514C1F160890", -1.5f, EntityAttributeModifier.Operation.MULTIPLY_TOTAL)
    );
    public static final MobStatusEffect SNOW_GOLEM = create(new SnowGolemStatusEffect(
            EntityType.SNOW_GOLEM,
            new StatusEffect[]{},
            new Item[]{},
            new MobStatusEffect.Settings().canWalkOnSnow().hydrophobic(),
            1
    ));
    public static final MobStatusEffect STRIDER = create(new MobStatusEffect(
            EntityType.STRIDER,
            new StatusEffect[]{StatusEffects.FIRE_RESISTANCE},
            new Item[]{},
            new MobStatusEffect.Settings()
    ));

    private static MobStatusEffect create(StatusEffect effect) {
        MobStatusEffect mobEffect = (MobStatusEffect)effect;
        mobEffectMap.put(mobEffect.getMobType(), mobEffect);
        return mobEffect;
    }
}
