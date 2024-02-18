package net.muffin.mobeffects.mixin;

import net.fabricmc.fabric.api.attachment.v1.AttachmentTarget;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.scoreboard.ScoreHolder;
import net.minecraft.server.command.CommandOutput;
import net.minecraft.util.Nameable;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.entity.EntityLike;
import net.muffin.mobeffects.MobEffectsMod;
import net.muffin.mobeffects.MobStatusEntity;
import net.muffin.mobeffects.statuseffect.MobStatusEffects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.entity.player.PlayerEntity;

@Mixin(Entity.class)
public abstract class BounceyMixin {
    private static final Logger LOGGER = LoggerFactory.getLogger(MobEffectsMod.MOD_ID);
    @Shadow
    public abstract World getWorld();
    @Shadow
    private Vec3d adjustMovementForCollisions(Vec3d movement) {
        return Vec3d.ZERO;
    }

    @Shadow
    protected abstract Vec3d adjustMovementForSneaking(Vec3d movement, MovementType type);

    @Inject(method = "move", at = @At("TAIL"))
    private void moveInjection(MovementType movementType, Vec3d movement, CallbackInfo ci) {
        Entity thisEntity = (Entity)(Object)this;
        if (thisEntity instanceof PlayerEntity playerEntity && ((MobStatusEntity)playerEntity).getMobStatusEffect() == MobStatusEffects.SLIME) {
            Vec3d vec3d = adjustMovementForCollisions(adjustMovementForSneaking(movement, movementType));
            if (movement.y != vec3d.y) {
                thisEntity.setVelocity(vec3d.x, vec3d.y * 10, vec3d.z);
            }
        }
    }
}
