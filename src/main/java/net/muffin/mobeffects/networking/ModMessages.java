package net.muffin.mobeffects.networking;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

import net.minecraft.util.Identifier;

import net.muffin.mobeffects.MobEffectsMod;
import net.muffin.mobeffects.networking.packet.GuardianBeamTargetC2SPacket;
import net.muffin.mobeffects.networking.packet.LayEggC2SPacket;

public class ModMessages {
    public static final Identifier LAY_EGG_ID = new Identifier(MobEffectsMod.MOD_ID, "egg_throw");
    public static final Identifier GUARDIAN_BEAM_TARGET_ID = new Identifier(MobEffectsMod.MOD_ID, "guardian_beam_target");

    public static void registerC2SPackets () {
        ServerPlayNetworking.registerGlobalReceiver(LAY_EGG_ID, LayEggC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(GUARDIAN_BEAM_TARGET_ID, GuardianBeamTargetC2SPacket::receive);
    }
    public static void registerS2CPackets () {

    }
}
