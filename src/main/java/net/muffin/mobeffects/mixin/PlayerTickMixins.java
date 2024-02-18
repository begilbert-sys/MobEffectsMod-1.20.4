package net.muffin.mobeffects.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.muffin.mobeffects.MobStatusEntity;
import net.muffin.mobeffects.statuseffect.MobStatusEffect;
import net.muffin.mobeffects.statuseffect.MobStatusEffects;
import net.muffin.mobeffects.statuseffect.UseHandMobStatusEffect;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerTickMixins extends LivingEntity implements MobStatusEntity {
    protected PlayerTickMixins(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }
    @Shadow
    @Final
    private PlayerAbilities abilities;

    @Inject(method = "tick", at = @At("HEAD"))
    private void tickMixins(CallbackInfo ci) {
        MobStatusEffect effect = getMobStatusEffect();
        // Blaze Floating
        if (effect == MobStatusEffects.BLAZE) {
            abilities.flying = true;
            abilities.setFlySpeed(0.012f);
        }

        // Creeper exploding
        if (effect == MobStatusEffects.CREEPER && ((UseHandMobStatusEffect)effect).ready() && isOnFire()) {
            this.getWorld().createExplosion(null, this.getX(), this.getY(), this.getZ(), 3.0f, World.ExplosionSourceType.MOB);
            ((UseHandMobStatusEffect)effect).resetCooldown();
        }
    }
}
