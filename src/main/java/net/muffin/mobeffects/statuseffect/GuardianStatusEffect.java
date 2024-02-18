package net.muffin.mobeffects.statuseffect;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.enchantment.ThornsEnchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;
import net.muffin.mobeffects.GuardianPlayer;
import net.muffin.mobeffects.networking.ModMessages;

public class GuardianStatusEffect extends UseHandMobStatusEffect implements ModifyDamageMSE {
    private final boolean elder;
    public GuardianStatusEffect(EntityType<? extends LivingEntity> mob, StatusEffect[] mimickedEffects, Item[] foodifiedItems, Settings settings, int cooldown, boolean elder) {
        super(mob, mimickedEffects, foodifiedItems, settings, cooldown);
        this.elder = elder;
    }
    public boolean isElder() {
        return elder;
    }
    @Override
    public ActionResult UseHandEventListener(PlayerEntity player, World world, EntityHitResult target) {
        /* Target a living entity with the guardian beam */
        GuardianPlayer guardianPlayerEntity = (GuardianPlayer)player;
        if (cooldownCounter == 0 && target != null && target.getEntity() instanceof LivingEntity entityTarget) {
            cooldownCounter = cooldown;
            int entityId = entityTarget.getId();
            PacketByteBuf packet = PacketByteBufs.create();
            packet.writeInt(entityId);
            ClientPlayNetworking.send(getC2SReceiverID(), packet);
            guardianPlayerEntity.setBeamTarget(entityTarget.getId());
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }
    public void receiveUseHandC2SPacket(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender) {
        int targetedEntityId = buf.readInt();
        ((GuardianPlayer) player).setBeamTarget(targetedEntityId);
    }
    public ActionResult AllowDamageEventListener(PlayerEntity player, DamageSource source, float amount) {
        Entity attacker = source.getAttacker();
        if (attacker != null) {
            attacker.damage(player.getDamageSources().thorns(player), 2.0f);
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }
}

