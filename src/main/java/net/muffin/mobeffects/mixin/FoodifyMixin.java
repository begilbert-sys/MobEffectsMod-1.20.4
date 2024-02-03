package net.muffin.mobeffects.mixin;

import net.fabricmc.fabric.api.item.v1.FabricItem;
import net.minecraft.block.Blocks;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.LivingEntity;

import net.minecraft.entity.player.HungerManager;
import net.minecraft.item.*;
import net.minecraft.resource.featuretoggle.ToggleableFeature;
import net.minecraft.text.Text;
import net.muffin.mobeffects.FoodComponentsMod;
import net.muffin.mobeffects.statuseffect.ChickenStatusEffect;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.muffin.mobeffects.MobEffectsMod;

import java.util.Map;



@Mixin(Item.class)
public abstract class FoodifyMixin {
    @Unique
    private static final Logger LOGGER = LoggerFactory.getLogger(MobEffectsMod.MOD_ID);
    @Shadow
    public abstract String toString();
    @Shadow
    public abstract FoodComponent getFoodComponent();


    @Inject(method = "getFoodComponent", at = @At("HEAD"), cancellable = true)
    private void fauxFoodComponent(CallbackInfoReturnable<FoodComponent> cir) {
        if (MobEffectsMod.CurrentMobStatus == MobEffectsMod.CHICKENSTATUS && toString().equals("wheat_seeds")) {
            cir.setReturnValue(FoodComponentsMod.WHEAT_SEEDS);
            cir.cancel();
        }
    }

    @Inject(method = "isFood", at = @At("HEAD"), cancellable = true)
    public void isStackAwareFood(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(this.getFoodComponent() != null);
    }
}