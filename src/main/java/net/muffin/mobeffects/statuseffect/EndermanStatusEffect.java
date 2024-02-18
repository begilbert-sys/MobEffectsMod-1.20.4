package net.muffin.mobeffects.statuseffect;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.event.GameEvent;
import net.minecraft.util.math.random.Random;


public class EndermanStatusEffect extends UseHandMobStatusEffect implements ModifyDamageMSE {
    public EndermanStatusEffect(EntityType<? extends LivingEntity> mob, StatusEffect[] mimickedEffects, Item[] foodifiedItems, MobStatusEffect.Settings settings, int cooldown) {
        super(mob, mimickedEffects, foodifiedItems, settings, cooldown);
    }
    private boolean teleportTo(PlayerEntity player, double x, double y, double z) {
        /* TAKEN FROM SOURCE: net.minecraft.entity.mob.EndermanEntity.teleportTo */
        BlockPos.Mutable mutable = new BlockPos.Mutable(x, y, z);
        while (mutable.getY() > player.getWorld().getBottomY() && !player.getWorld().getBlockState(mutable).blocksMovement()) {
            mutable.move(Direction.DOWN);
        }
        BlockState blockState = player.getWorld().getBlockState(mutable);
        boolean bl = blockState.blocksMovement();
        boolean bl2 = blockState.getFluidState().isIn(FluidTags.WATER);
        if (!bl || bl2) {
            return false;
        }
        Vec3d vec3d = player.getPos();
        boolean bl3 = player.teleport(x, y, z, true);
        if (bl3) {
            player.getWorld().emitGameEvent(GameEvent.TELEPORT, vec3d, GameEvent.Emitter.of(player));
            if (!player.isSilent()) {
                player.getWorld().playSound(null, player.prevX, player.prevY, player.prevZ, SoundEvents.ENTITY_ENDERMAN_TELEPORT, player.getSoundCategory(), 1.0f, 1.0f);
                player.playSound(SoundEvents.ENTITY_ENDERMAN_TELEPORT, 1.0f, 1.0f);
            }
        }
        return bl3;
    }
    private boolean teleportRandomly(PlayerEntity player) {
        /* TAKEN FROM SOURCE: net.minecraft.entity.mob.EndermanEntity.teleportRandomly */
        if (player.getWorld().isClient() || !player.isAlive()) {
            return false;
        }
        Random random = Random.create();
        double d = player.getX() + (random.nextDouble() - 0.5) * 64.0;
        double e = player.getY() + (double)(random.nextInt(64) - 32);
        double f = player.getZ() + (random.nextDouble() - 0.5) * 64.0;
        return this.teleportTo(player, d, e, f);
    }

    public void receiveUseHandC2SPacket(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
                                        PacketByteBuf buf, PacketSender responseSender) {
        teleportRandomly(player);
    }
    public ActionResult AllowDamageEventListener(PlayerEntity player, DamageSource source, float amount) {
        teleportRandomly(player);
        return ActionResult.SUCCESS;
    }
}
