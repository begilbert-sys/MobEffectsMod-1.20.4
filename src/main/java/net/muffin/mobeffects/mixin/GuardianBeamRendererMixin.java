package net.muffin.mobeffects.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.GuardianEntityRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import net.muffin.mobeffects.MobEffectsMod;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.muffin.mobeffects.GuardianPlayer;
import net.muffin.mobeffects.MobEffectsMod;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public class GuardianBeamRendererMixin {
    @Unique
    private static final Identifier EXPLOSION_BEAM_TEXTURE = new Identifier("textures/entity/guardian_beam.png");
    @Unique
    private static final RenderLayer LAYER = RenderLayer.getEntityCutoutNoCull(EXPLOSION_BEAM_TEXTURE);


    @Unique
    private Vec3d fromLerpedPosition(LivingEntity entity, double yOffset, float delta) {
        double d = MathHelper.lerp((double)delta, entity.lastRenderX, entity.getX());
        double e = MathHelper.lerp((double)delta, entity.lastRenderY, entity.getY()) + yOffset;
        double f = MathHelper.lerp((double)delta, entity.lastRenderZ, entity.getZ());
        return new Vec3d(d, e, f);
    }
    @Unique
    private static void vertex(VertexConsumer vertexConsumer, Matrix4f positionMatrix, Matrix3f normalMatrix, float x, float y, float z, int red, int green, int blue, float u, float v) {
        vertexConsumer.vertex(positionMatrix, x, y, z).color(red, green, blue, 255).texture(u, v).overlay(OverlayTexture.DEFAULT_UV).light(LightmapTextureManager.MAX_LIGHT_COORDINATE).normal(normalMatrix, 0.0f, 1.0f, 0.0f).next();
    }
    @Unique
    private static final Logger LOGGER = LoggerFactory.getLogger(MobEffectsMod.MOD_ID);
    @Shadow
    @Final
    private BufferBuilderStorage bufferBuilders;

    @Shadow
    @Final
    private MinecraftClient client;

    @Unique
    private void renderGuardianBeam(PlayerEntity playerEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, boolean firstPerson) {
        float playerYoffset = -2.0f;
        float targetYoffset = 0.5f;
        if (firstPerson) {
            playerYoffset = -0.5f;
            targetYoffset = -0.5f;
        }
        if (playerEntity instanceof GuardianPlayer guardianPlayerEntity) {
            if (playerEntity.hasStatusEffect(MobEffectsMod.GUARDIANSTATUS)) {
                LivingEntity livingEntity = guardianPlayerEntity.getBeamTarget();
                if (livingEntity != null) {
                    float h = guardianPlayerEntity.getBeamProgress(tickDelta);
                    float j = guardianPlayerEntity.getBeamTicks() + tickDelta;
                    float k = j * 0.5f % 1.0f;
                    float l = playerEntity.getStandingEyeHeight() * playerYoffset;
                    matrices.push();
                    matrices.translate(0.0f, l, 0.0f);
                    Vec3d vec3d = this.fromLerpedPosition(livingEntity, -2.0 + (0.5 * (double)livingEntity.getHeight()), tickDelta);
                    LOGGER.info(Float.toString(livingEntity.getHeight()));
                    LOGGER.info(Double.toString((double)livingEntity.getHeight() * targetYoffset));
                    Vec3d vec3d2 = this.fromLerpedPosition(playerEntity, l, tickDelta);
                    Vec3d vec3d3 = vec3d.subtract(vec3d2);
                    float m = (float)(vec3d3.length() + 1.0);
                    vec3d3 = vec3d3.normalize();
                    float n = (float)Math.acos(vec3d3.y);
                    float o = (float)Math.atan2(vec3d3.z, vec3d3.x);
                    matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((1.5707964f - o) * 57.295776f));
                    matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(n * 57.295776f));
                    boolean p = true;
                    float q = j * 0.05f * -1.5f;
                    float r = h * h;
                    int s = 64 + (int)(r * 191.0f);
                    int t = 32 + (int)(r * 191.0f);
                    int u = 128 - (int)(r * 64.0f);
                    float v = 0.2f;
                    float w = 0.282f;
                    float x = MathHelper.cos(q + 2.3561945f) * 0.282f;
                    float y = MathHelper.sin(q + 2.3561945f) * 0.282f;
                    float z = MathHelper.cos(q + 0.7853982f) * 0.282f;
                    float aa = MathHelper.sin(q + 0.7853982f) * 0.282f;
                    float ab = MathHelper.cos(q + 3.926991f) * 0.282f;
                    float ac = MathHelper.sin(q + 3.926991f) * 0.282f;
                    float ad = MathHelper.cos(q + 5.4977875f) * 0.282f;
                    float ae = MathHelper.sin(q + 5.4977875f) * 0.282f;
                    float af = MathHelper.cos(q + (float)Math.PI) * 0.2f;
                    float ag = MathHelper.sin(q + (float)Math.PI) * 0.2f;
                    float ah = MathHelper.cos(q + 0.0f) * 0.2f;
                    float ai = MathHelper.sin(q + 0.0f) * 0.2f;
                    float aj = MathHelper.cos(q + 1.5707964f) * 0.2f;
                    float ak = MathHelper.sin(q + 1.5707964f) * 0.2f;
                    float al = MathHelper.cos(q + 4.712389f) * 0.2f;
                    float am = MathHelper.sin(q + 4.712389f) * 0.2f;
                    float an = m;
                    float ao = 0.0f;
                    float ap = 0.4999f;
                    float aq = -1.0f + k;
                    float ar = m * 2.5f + aq;
                    VertexConsumer vertexConsumer = vertexConsumers.getBuffer(LAYER);
                    MatrixStack.Entry entry = matrices.peek();
                    Matrix4f matrix4f = entry.getPositionMatrix();
                    Matrix3f matrix3f = entry.getNormalMatrix();
                    GuardianBeamRendererMixin.vertex(vertexConsumer, matrix4f, matrix3f, af, an, ag, s, t, u, 0.4999f, ar);
                    GuardianBeamRendererMixin.vertex(vertexConsumer, matrix4f, matrix3f, af, 0.0f, ag, s, t, u, 0.4999f, aq);
                    GuardianBeamRendererMixin.vertex(vertexConsumer, matrix4f, matrix3f, ah, 0.0f, ai, s, t, u, 0.0f, aq);
                    GuardianBeamRendererMixin.vertex(vertexConsumer, matrix4f, matrix3f, ah, an, ai, s, t, u, 0.0f, ar);
                    GuardianBeamRendererMixin.vertex(vertexConsumer, matrix4f, matrix3f, aj, an, ak, s, t, u, 0.4999f, ar);
                    GuardianBeamRendererMixin.vertex(vertexConsumer, matrix4f, matrix3f, aj, 0.0f, ak, s, t, u, 0.4999f, aq);
                    GuardianBeamRendererMixin.vertex(vertexConsumer, matrix4f, matrix3f, al, 0.0f, am, s, t, u, 0.0f, aq);
                    GuardianBeamRendererMixin.vertex(vertexConsumer, matrix4f, matrix3f, al, an, am, s, t, u, 0.0f, ar);
                    float as = 0.0f;
                    if (playerEntity.age % 2 == 0) {
                        as = 0.5f;
                    }
                    GuardianBeamRendererMixin.vertex(vertexConsumer, matrix4f, matrix3f, x, an, y, s, t, u, 0.5f, as + 0.5f);
                    GuardianBeamRendererMixin.vertex(vertexConsumer, matrix4f, matrix3f, z, an, aa, s, t, u, 1.0f, as + 0.5f);
                    GuardianBeamRendererMixin.vertex(vertexConsumer, matrix4f, matrix3f, ad, an, ae, s, t, u, 1.0f, as);
                    GuardianBeamRendererMixin.vertex(vertexConsumer, matrix4f, matrix3f, ab, an, ac, s, t, u, 0.5f, as);
                    matrices.pop();
                }
            }
        }
    }

    @Inject(method = "render", at = @At("TAIL"))
    private void renderFirstPersonBeam(MatrixStack matrices, float tickDelta, long limitTime, boolean renderBlockOutline, Camera camera, GameRenderer gameRenderer, LightmapTextureManager lightmapTextureManager, Matrix4f projectionMatrix, CallbackInfo ci) {
        if (this.client != null) {
            VertexConsumerProvider vertexConsumers = this.bufferBuilders.getEntityVertexConsumers();
            renderGuardianBeam(this.client.player, tickDelta, matrices, vertexConsumers, true);
        }
    }
    @Inject(method = "renderEntity", at = @At("TAIL"))
    private void renderThirdPersonBeam(Entity entity, double cameraX, double cameraY, double cameraZ, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, CallbackInfo ci) {
        if (entity instanceof PlayerEntity) {
            renderGuardianBeam((PlayerEntity)entity, tickDelta, matrices, vertexConsumers, false);
        }
    }
}
