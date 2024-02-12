package net.muffin.mobeffects.mixin;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.MinecraftClient;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.ItemStack;

import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.muffin.mobeffects.event.UseHandCallback;
import net.muffin.mobeffects.networking.ModMessages;
import net.muffin.mobeffects.statuseffect.MobStatusEffects;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.muffin.mobeffects.GuardianPlayer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.muffin.mobeffects.MobEffectsMod;


@Mixin(MinecraftClient.class)
public abstract class UseHandMixin {
    @Unique
    private static final Logger LOGGER = LoggerFactory.getLogger(MobEffectsMod.MOD_ID);
    @Shadow
    @Nullable
    public ClientWorld world;
    @Shadow
    public ClientPlayerEntity player;
    @Shadow
    @Nullable
    public HitResult crosshairTarget;
    @Shadow
    @Nullable
    public Entity cameraEntity;
    @Shadow
    public abstract float getTickDelta();
    
    @Unique
    @Nullable
    private LivingEntity getLivingEntityInCrosshair(float reach) {
        /* Return a living entity in the player's crosshair, or null
        This code is taken from the source code.
        Source: net.minecraft.client.render.GameRenderer.updateTargetedEntity
        */
        Entity entity2 = cameraEntity;
        if (entity2 == null) {
            return null;
        }
        double d = reach;
        float tickDelta = this.getTickDelta();
        HitResult target = entity2.raycast(d, tickDelta, false);
        Vec3d vec3d = entity2.getCameraPosVec(tickDelta);
        double e = target != null ? target.getPos().squaredDistanceTo(vec3d) : d * d;
        Vec3d vec3d2 = entity2.getRotationVec(1.0f);
        Vec3d vec3d3 = vec3d.add(vec3d2.x * d, vec3d2.y * d, vec3d2.z * d);
        float f = 1.0f;
        Box box = entity2.getBoundingBox().stretch(vec3d2.multiply(d)).expand(1.0, 1.0, 1.0);
        EntityHitResult entityHitResult = ProjectileUtil.raycast(entity2, vec3d, vec3d3, box, entity -> !entity.isSpectator() && entity.canHit(), e);
        if (entityHitResult != null) {
            Entity hitEntity = entityHitResult.getEntity();
            return (hitEntity instanceof LivingEntity ? (LivingEntity)hitEntity : null);
        }
        return null;
    }

    @Inject(method = "doItemUse", at = @At("HEAD"))
    private void injected(CallbackInfo ci) {
        ItemStack itemStack = player.getMainHandStack();
        if(itemStack.isEmpty()) {
            ActionResult result = UseHandCallback.EVENT.invoker().interact(player, world, crosshairTarget);
            /*
            if (player.hasStatusEffect(MobStatusEffects.CHICKEN)) {
                ClientPlayNetworking.send(ModMessages.LAY_EGG_ID, PacketByteBufs.create());
            }
            else if (player.hasStatusEffect(MobStatusEffects.GUARDIAN)) {
                if (player instanceof GuardianPlayer guardianPlayerEntity) {
                    LivingEntity targetedEntity = getLivingEntityInCrosshair(15.0f);
                    if (targetedEntity != null) {
                        guardianPlayerEntity.setBeamTarget(targetedEntity.getId());
                    }
                }
            }
             */
        }
    }
}
