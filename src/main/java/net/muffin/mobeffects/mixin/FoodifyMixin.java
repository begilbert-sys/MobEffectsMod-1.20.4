package net.muffin.mobeffects.mixin;

import net.fabricmc.fabric.api.item.v1.FabricItem;
import net.minecraft.block.Blocks;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.LivingEntity;

import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.resource.featuretoggle.ToggleableFeature;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.muffin.mobeffects.ExtraFoodComponents;
import net.muffin.mobeffects.statuseffect.ChickenStatusEffect;
import net.muffin.mobeffects.statuseffect.MobStatusEffect;
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

import net.muffin.mobeffects.MobStatusEntity;

import java.util.Map;



@Mixin(Item.class)
public abstract class FoodifyMixin {
    @Unique
    private static final Logger LOGGER = LoggerFactory.getLogger(MobEffectsMod.MOD_ID);
    @Shadow
    public abstract String toString();
    @Shadow
    public abstract Item asItem();
    @Shadow
    public abstract FoodComponent getFoodComponent();
    @Inject(method = "getFoodComponent", at = @At("HEAD"), cancellable = true)
    public void getFoodifiedComponent(CallbackInfoReturnable<FoodComponent> cir) {
        Item thisItem = asItem();
        if (ExtraFoodComponents.itemFoodComponentMap.containsKey(thisItem)) {
            cir.setReturnValue(ExtraFoodComponents.itemFoodComponentMap.get(thisItem));
            cir.cancel();
        }
    }

    @Inject(method = "use", at = @At("HEAD"), cancellable = true)
    public void useItemInject(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir) {
        Item thisItem = asItem();
        if (ExtraFoodComponents.isModdedFoodComponent(thisItem.getFoodComponent())) {
            MobStatusEffect mobEffect = ((MobStatusEntity)user).getMobStatusEffect();
            ItemStack itemStack = user.getStackInHand(hand);
            if (mobEffect != null && mobEffect.foodifiesItem(thisItem)) {
                user.setCurrentHand(hand);
                cir.setReturnValue(TypedActionResult.consume(itemStack));
                cir.cancel();
            } else {
                cir.setReturnValue(TypedActionResult.pass(itemStack));
                cir.cancel();
            }
        }
    }
}