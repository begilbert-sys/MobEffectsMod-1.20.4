package net.muffin.mobeffects.statuseffect;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;
import net.muffin.mobeffects.networking.ModMessages;

public class EvokerStatusEffect extends MobStatusEffect {
    public EvokerStatusEffect(EntityType<? extends LivingEntity> mob, StatusEffect[] mimickedEffects, Item[] foodifiedItems) {
        super(mob, mimickedEffects, foodifiedItems);
    }
    @Override
    public ActionResult UseHandEventListener(PlayerEntity player, World world, EntityHitResult target) {
        ClientPlayNetworking.send(ModMessages.EVOKER_FANGS_ENTITY_ID, PacketByteBufs.create());
        return ActionResult.PASS;
    }
}