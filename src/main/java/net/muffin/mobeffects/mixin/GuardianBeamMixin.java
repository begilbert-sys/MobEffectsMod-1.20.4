package net.muffin.mobeffects.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.LivingEntity;

import net.minecraft.entity.player.PlayerEntity;

import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
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
public abstract class GuardianBeamMixin extends Entity implements GuardianPlayer {
    @Unique
    private static final Logger LOGGER = LoggerFactory.getLogger(MobEffectsMod.MOD_ID);
    @Unique
    private static final int WARMUP_TIME = 80;
    @Unique
    private int beamTicks;
    @Unique
    @Nullable
    private LivingEntity cachedBeamTarget;
    @Nullable
    private LivingEntity selectedTarget;
    @Unique
    private float standingEyeHeight;
    @Unique
    private static final TrackedData<Integer> BEAM_TARGET_ID = DataTracker.registerData(GuardianBeamMixin.class, TrackedDataHandlerRegistry.INTEGER);
    public GuardianBeamMixin(EntityType<?> type, World world) {
        super(type, world);
    }
    @Unique
    public int getWarmupTime() {
        return 80;
    }
    @Unique
    public void setBeamTarget(int entityId) {
        LOGGER.info("beam target set");
        this.dataTracker.set(BEAM_TARGET_ID, entityId);
    }
    @Unique
    public boolean hasBeamTarget() {
        return this.dataTracker.get(BEAM_TARGET_ID) != 0;
    }
    @Unique
    @Nullable
    public LivingEntity getBeamTarget() {
        if (!this.hasBeamTarget()) {
            return null;
        }
        return (LivingEntity)this.getWorld().getEntityById(this.dataTracker.get(BEAM_TARGET_ID));

    }
    @Unique
    public float getBeamProgress(float tickDelta) {
        return ((float)this.beamTicks + tickDelta) / (float)this.getWarmupTime();
    }
    @Unique
    public float getBeamTicks() {
        return this.beamTicks;
    }

    @Override
    public void onTrackedDataSet(TrackedData<?> data) {
        super.onTrackedDataSet(data);
        if (BEAM_TARGET_ID.equals(data)) {
            this.beamTicks = 0;
            this.cachedBeamTarget = null;
        }
    }
    @Unique
    private Vec3d fromLerpedPosition(Entity entity, double yOffset, float delta) {
        double d = MathHelper.lerp((double)delta, entity.lastRenderX, entity.getX());
        double e = MathHelper.lerp((double)delta, entity.lastRenderY, entity.getY()) + yOffset;
        double f = MathHelper.lerp((double)delta, entity.lastRenderZ, entity.getZ());
        return new Vec3d(d, e, f);
    }

    @Unique
    private void addBubbleEffect(LivingEntity targetEntity) {
        double d = this.getBeamProgress(0.0f);
        double e = targetEntity.getX() - this.getX();
        double f = targetEntity.getBodyY(0.5) - this.getEyeY();
        double g = targetEntity.getZ() - this.getZ();
        double h = Math.sqrt(e * e + f * f + g * g);
        e /= h;
        f /= h;
        g /= h;
        double j = this.random.nextDouble();
        while (j < h) {
            this.getWorld().addParticle(ParticleTypes.BUBBLE, this.getX() + e * (j += 1.8 - d + this.random.nextDouble() * (1.7 - d)), this.getEyeY() + f * j, this.getZ() + g * j, 0.0, 0.0, 0.0);
        }
    }
    @Inject(method = "initDataTracker", at = @At("TAIL"))
    private void registerBeamDataTracker(CallbackInfo ci) {
        this.dataTracker.startTracking(BEAM_TARGET_ID, 0);
    }

    @Inject(method = "tickMovement", at = @At("TAIL"))
    private void injectBeamTickMovement(CallbackInfo ci) {
        if (!this.hasBeamTarget()) {
            return;
        }
        LivingEntity livingEntity;
        if (this.beamTicks < this.getWarmupTime()) {
                ++this.beamTicks;
        }
        /* add bubble effect */
        if ((livingEntity = this.getBeamTarget()) != null) {
            addBubbleEffect(livingEntity);
        }
    }
}
