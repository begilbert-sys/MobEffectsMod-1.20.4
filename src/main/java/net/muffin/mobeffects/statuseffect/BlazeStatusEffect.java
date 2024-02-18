package net.muffin.mobeffects.statuseffect;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.item.Item;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class BlazeStatusEffect extends  UseHandMobStatusEffect {
    public BlazeStatusEffect(EntityType<? extends LivingEntity> mob, StatusEffect[] mimickedEffects, Item[] foodifiedItems, Settings settings, int cooldown) {
        super(mob, mimickedEffects, foodifiedItems, settings, cooldown);
    }

    private static SmallFireballEntity setVelocityBasedOnPlayerDirection(Entity shooter, float pitch, float yaw, float roll, float speed, float divergence) {
        /* TAKEN FROM SOURCE: net.minecraft.entity.projectile.ProjectileEntity.setVelocity
        Note: I tried calling this method directly (instead of just copying it), but it caused the fireballs to stop and hang in place.
        Not sure why, might try to fix it.
        */
        float f = -MathHelper.sin(yaw * ((float)Math.PI / 180)) * MathHelper.cos(pitch * ((float)Math.PI / 180));
        float g = -MathHelper.sin((pitch + roll) * ((float)Math.PI / 180));
        float h = MathHelper.cos(yaw * ((float)Math.PI / 180)) * MathHelper.cos(pitch * ((float)Math.PI / 180));
        SmallFireballEntity smallFireballEntity = new SmallFireballEntity(shooter.getWorld(), shooter.getX(), shooter.getEyeY() - 0.5, shooter.getZ(), f * speed, g * speed, h * speed);
        return smallFireballEntity;
    }

    public void receiveUseHandC2SPacket(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
                                   PacketByteBuf buf, PacketSender responseSender) {
        SmallFireballEntity smallFireballEntity = setVelocityBasedOnPlayerDirection(player, player.getPitch(), player.getYaw(), player.getRoll(), 2.0f, 5.0f);
        player.getWorld().spawnEntity(smallFireballEntity);
    }
}
