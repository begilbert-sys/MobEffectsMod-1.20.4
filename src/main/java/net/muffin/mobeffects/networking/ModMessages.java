package net.muffin.mobeffects.networking;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

import net.minecraft.util.Identifier;

import net.muffin.mobeffects.MobEffectsMod;
import net.muffin.mobeffects.statuseffect.MobStatusEffect;
import net.muffin.mobeffects.statuseffect.MobStatusEffects;
import net.muffin.mobeffects.statuseffect.UseHandMobStatusEffect;

public class ModMessages {
    public static void registerC2SPackets () {
        for (MobStatusEffect effect : MobStatusEffects.mobEffectMap.values()) {
            if (effect instanceof UseHandMobStatusEffect useHandEffect) {
                ServerPlayNetworking.registerGlobalReceiver(useHandEffect.getC2SReceiverID(), useHandEffect::receiveUseHandC2SPacket);
            }
        }
    }
    public static void registerS2CPackets () {

    }
}
