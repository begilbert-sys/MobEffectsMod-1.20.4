package net.muffin.mobeffects.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.muffin.mobeffects.MSEPlayerEntity;
import net.muffin.mobeffects.MobEffectsMod;
import net.muffin.mobeffects.statuseffect.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerTickMixinsEntity extends LivingEntity implements MSEPlayerEntity {
    private static final Logger LOGGER = LoggerFactory.getLogger(MobEffectsMod.MOD_ID);
    protected PlayerTickMixinsEntity(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }
    @Shadow
    @Final
    private PlayerAbilities abilities;

    @Unique
    private void blazeFloat() {
        abilities.flying = true;
        abilities.setFlySpeed(0.012f);
    }
    @Inject(method = "tick", at = @At("HEAD"))
    private void tickMixins(CallbackInfo ci) {
        MobStatusEffect effect = getMobStatusEffect();
        if (effect == null) {
            return;
        }

        // Blaze Floating
        if (effect == MobStatusEffects.BLAZE) {
            blazeFloat();
        }
    }
}
