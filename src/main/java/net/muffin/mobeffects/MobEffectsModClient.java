package net.muffin.mobeffects;

import net.fabricmc.api.ClientModInitializer;

import net.minecraft.client.MinecraftClient;
import net.muffin.mobeffects.networking.ModMessages;

public class MobEffectsModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModMessages.registerS2CPackets();
    }
}