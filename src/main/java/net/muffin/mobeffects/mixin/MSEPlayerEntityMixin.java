package net.muffin.mobeffects.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.muffin.mobeffects.MSEPlayerEntity;
import net.muffin.mobeffects.statuseffect.MobStatusEffect;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(PlayerEntity.class)
public abstract class MSEPlayerEntityMixin extends LivingEntity implements MSEPlayerEntity {

    @Unique
    private int mobEffectDuration = 0;

    @Unique
    @Nullable
    private Double liftOffY;

    protected MSEPlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Unique
    public MobStatusEffect getMobStatusEffect() {
        for (StatusEffect effect : getActiveStatusEffects().keySet()) {
            if (effect instanceof MobStatusEffect mobEffect) {
                return mobEffect;
            }
        }
        return null;
    }
    @Unique
    public void setMobStatusEffect(MobStatusEffect effect) {
        MobStatusEffect prevEffect = getMobStatusEffect();
        removeStatusEffect(prevEffect);
        addStatusEffect(new StatusEffectInstance(effect, 12000));
        mobEffectDuration = 0;
    }

    @Unique
    public int getMobStatusEffectDuration() {
        return mobEffectDuration;
    }

    @Unique
    public void setLiftOffY(double value) {
        liftOffY = value;
    }
    @Unique
    @Nullable
    public Double getLiftOffY() {
        return liftOffY;
    }

    @Unique
    public void incrementEffectDuration() {
        mobEffectDuration++;
    }
    @Unique
    public int getEffectDuration() {
        return mobEffectDuration;
    }
}
