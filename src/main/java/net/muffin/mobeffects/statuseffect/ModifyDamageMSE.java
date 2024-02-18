package net.muffin.mobeffects.statuseffect;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;

public interface ModifyDamageMSE {
    ActionResult AllowDamageEventListener(PlayerEntity player, DamageSource source, float amount);
}
