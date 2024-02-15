package net.muffin.mobeffects.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.LivingEntity;

import net.minecraft.entity.player.PlayerEntity;

import net.minecraft.particle.ParticleTypes;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.muffin.mobeffects.MobEffectsMod;
import net.muffin.mobeffects.GuardianPlayer;

import java.util.Map;



@Mixin(PlayerEntity.class)
public abstract class GuardianBeamMixin extends LivingEntity implements GuardianPlayer {
    @Unique
    private static final Logger LOGGER = LoggerFactory.getLogger(MobEffectsMod.MOD_ID);
    @Unique
    private static final int WARMUP_TIME = 80;
    @Unique
    private int beamTicks;
    @Unique
    private static final TrackedData<Integer> BEAM_TARGET_ID = DataTracker.registerData(PlayerEntity.class, TrackedDataHandlerRegistry.INTEGER);

    protected GuardianBeamMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Unique
    public int getWarmupTime() {
        /* TAKEN FROM SOURCE: net.minecraft.entity.mob.GuardianEntity.getWarmupTime */
        return 80;
    }
    @Unique
    public void setBeamTarget(int entityId) {
        /* TAKEN FROM SOURCE: net.minecraft.entity.mob.GuardianEntity.setBeamTarget */
        this.dataTracker.set(BEAM_TARGET_ID, entityId);
    }
    @Unique
    public boolean hasBeamTarget() {
        /* TAKEN FROM SOURCE: net.minecraft.entity.mob.GuardianEntity.hasBeamTarget */
        return this.dataTracker.get(BEAM_TARGET_ID) != 0;
    }
    @Unique
    @Nullable
    public LivingEntity getBeamTarget() {
        /* TAKEN FROM SOURCE: net.minecraft.entity.mob.GuardianEntity.getBeamTarget */
        if (!this.hasBeamTarget()) {
            return null;
        }
        return (LivingEntity)this.getWorld().getEntityById(this.dataTracker.get(BEAM_TARGET_ID));

    }
    @Unique
    public float getBeamProgress(float tickDelta) {
        /* TAKEN FROM SOURCE: net.minecraft.entity.mob.GuardianEntity.getBeamProgress */
        return ((float)this.beamTicks + tickDelta) / (float)this.getWarmupTime();
    }
    @Unique
    public float getBeamTicks() {
        /* TAKEN FROM SOURCE: net.minecraft.entity.mob.GuardianEntity.getBeamTicks */
        return this.beamTicks;
    }
    @Unique
    private void addBubbleEffect(LivingEntity targetEntity) {
        /* TAKEN FROM SOURCE: net.minecraft.entity.mob.GuardianEntity.tickMovement */
        // my vars
        double targetYOffset = 0.5;
        double playerYOffset = 1.02;

        double d = this.getBeamProgress(0.0f);
        double e = targetEntity.getX() - this.getX();
        double f = targetEntity.getBodyY(0.5 + targetYOffset) - this.getEyeY();
        double g = targetEntity.getZ() - this.getZ();
        double h = Math.sqrt(e * e + f * f + g * g);
        e /= h;
        f /= h;
        g /= h;
        double j = this.random.nextDouble();
        while (j < h) {
            this.getWorld().addParticle(ParticleTypes.BUBBLE, this.getX() + e * (j += 1.8 - d + this.random.nextDouble() * (1.7 - d)), (this.getEyeY() + f * j) * playerYOffset, this.getZ() + g * j, 0.0, 0.0, 0.0);
        }
    }
    @Inject(method = "initDataTracker", at = @At("TAIL"))
    private void registerBeamDataTracker(CallbackInfo ci) {
        this.dataTracker.startTracking(BEAM_TARGET_ID, 0);
    }

    @Inject(method = "tickMovement", at = @At("TAIL"))
    private void beamTickMovement(CallbackInfo ci) {
        /* TAKEN FROM SOURCE: net.minecraft.entity.mob.GuardianEntity.tickMovement */
        if (!this.hasBeamTarget()) {
            return;
        }
        LivingEntity livingEntity;
        if (this.beamTicks < this.getWarmupTime()) {
                ++this.beamTicks;
        }

        if ((livingEntity = this.getBeamTarget()) != null) {
            addBubbleEffect(livingEntity);
        }
    }
    @Inject(method = "tick", at = @At("TAIL"))
    private void beamTick(CallbackInfo ci) {
        /* TAKEN FROM SOURCE: net.minecraft.entity.mob.GuardianEntity.FireBeamGoal.tick */
        LivingEntity livingEntity = getBeamTarget();
        if (livingEntity == null) {
            return;
        }
        ++this.beamTicks;
        if (this.beamTicks >= this.getWarmupTime()) {
            float f = 1.0f;
            livingEntity.damage(this.getDamageSources().indirectMagic(this, this), f);
            livingEntity.damage(this.getDamageSources().mobAttack(this), (float)this.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE));
            this.setBeamTarget(0);
            this.beamTicks = 0;
        }
    }
}
