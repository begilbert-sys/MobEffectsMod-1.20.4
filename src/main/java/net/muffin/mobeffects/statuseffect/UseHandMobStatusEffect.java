package net.muffin.mobeffects.statuseffect;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;
import net.muffin.mobeffects.MobEffectsMod;

public abstract class UseHandMobStatusEffect extends MobStatusEffect {
    protected final Identifier C2SReceiverID;
    protected final int cooldown;
    protected int cooldownCounter;

    public UseHandMobStatusEffect(EntityType<? extends LivingEntity> mob, StatusEffect[] mimickedEffects, Item[] foodifiedItems, Settings settings, int cooldown) {
        super(mob, mimickedEffects, foodifiedItems, settings);
        this.cooldown = cooldown;
        this.cooldownCounter = cooldown;
        C2SReceiverID = new Identifier(MobEffectsMod.MOD_ID, toString() + "c2sreceiver");
    }

    public Identifier getC2SReceiverID() {
        return C2SReceiverID;
    }

    public void updateCooldown () {
        if (cooldownCounter <= 0) {
            cooldownCounter = 0;
        } else {
            --cooldownCounter;
        }
    }
    public void resetCooldown() {
        cooldownCounter = cooldown;
    }

    public ActionResult UseHandEventListener(PlayerEntity player, World world, EntityHitResult target) {
        if (cooldownCounter == 0) {
            cooldownCounter = cooldown;
            ClientPlayNetworking.send(getC2SReceiverID(), PacketByteBufs.create());
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }

    public abstract void receiveUseHandC2SPacket(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
                                 PacketByteBuf buf, PacketSender responseSender);
}
