package net.muffin.mobeffects.networking.packet;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.muffin.mobeffects.GuardianPlayer;
import net.muffin.mobeffects.MobEffectsMod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LayEggC2SPacket {
    private static final Logger LOGGER = LoggerFactory.getLogger(MobEffectsMod.MOD_ID);
    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender) {
        ItemStack itemStack = new ItemStack(Items.EGG);
        player.setStackInHand(player.getActiveHand(), itemStack);
    }
}
