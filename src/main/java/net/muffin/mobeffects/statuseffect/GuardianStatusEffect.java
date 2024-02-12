package net.muffin.mobeffects.statuseffect;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;
import net.muffin.mobeffects.GuardianPlayer;
import net.muffin.mobeffects.networking.ModMessages;

public class GuardianStatusEffect extends MobStatusEffect {
    public GuardianStatusEffect(EntityType<? extends LivingEntity> mob, StatusEffect[] mimickedEffects, Item[] foodifiedItems) {
        super(mob, mimickedEffects, foodifiedItems);
    }
    @Override
    public ActionResult UseHandEventListener(PlayerEntity player, World world, HitResult target) {
        /* Target a living entity with the guardian beam */
        GuardianPlayer guardianPlayerEntity = (GuardianPlayer)player;
        if (target != null && target.getType() == HitResult.Type.ENTITY) {
            Entity entity = ((EntityHitResult)target).getEntity();
            if (entity instanceof LivingEntity) {
                guardianPlayerEntity.setBeamTarget(entity.getId());
            }
        }
        return ActionResult.PASS;
    }
}

