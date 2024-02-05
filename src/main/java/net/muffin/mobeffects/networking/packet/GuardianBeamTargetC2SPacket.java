package net.muffin.mobeffects.networking.packet;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.muffin.mobeffects.GuardianPlayer;

public class GuardianBeamTargetC2SPacket {
    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender) {
        int targetedEntityId = buf.readInt();
        ((GuardianPlayer) player).setBeamTarget(targetedEntityId);
    }
}
