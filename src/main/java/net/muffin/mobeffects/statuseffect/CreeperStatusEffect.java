package net.muffin.mobeffects.statuseffect;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;

public class CreeperStatusEffect extends UseHandMobStatusEffect {
    public CreeperStatusEffect(EntityType<? extends LivingEntity> mob, StatusEffect[] mimickedEffects, Item[] foodifiedItems, Settings settings, int cooldown) {
        super(mob, mimickedEffects, foodifiedItems, settings, cooldown);
    }

    private void explode(PlayerEntity player) {
        player.getWorld().createExplosion(null, player.getX(), player.getY(), player.getZ(), 3.0f, World.ExplosionSourceType.NONE);
        resetCooldown();
    }
    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        super.applyUpdateEffect(entity, amplifier);
        if (entity.isOnFire()) {
            explode((PlayerEntity)entity);
        }
    }


    public void receiveUseHandC2SPacket(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
                                        PacketByteBuf buf, PacketSender responseSender) {
        explode(player);
    }
}
