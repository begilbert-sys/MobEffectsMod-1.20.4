package net.muffin.mobeffects.statuseffect;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.mob.EvokerFangsEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import net.muffin.mobeffects.networking.ModMessages;

public class EvokerStatusEffect extends UseHandMobStatusEffect {
    public EvokerStatusEffect(EntityType<? extends LivingEntity> mob, StatusEffect[] mimickedEffects, Item[] foodifiedItems, Settings settings, int cooldown) {
        super(mob, mimickedEffects, foodifiedItems, settings, cooldown);
    }
    @Override
    public ActionResult UseHandEventListener(PlayerEntity player, World world, EntityHitResult target) {
        if (cooldownCounter == 0 && target != null && target.getEntity() instanceof LivingEntity targetEntity) {
            cooldownCounter = cooldown;
            PacketByteBuf packet = PacketByteBufs.create();
            int entityId = targetEntity.getId();
            packet.writeInt(entityId);
            ClientPlayNetworking.send(getC2SReceiverID(), packet);
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }
    public static void castSpell(ServerPlayerEntity player, LivingEntity livingEntity) {
        /* TAKEN FROM SOURCE: net.minecraft.entity.mob.EvokerEntity.castSpell */
        double d = Math.min(livingEntity.getY(), player.getY());
        double e = Math.max(livingEntity.getY(), player.getY()) + 1.0;
        float f = (float)MathHelper.atan2(livingEntity.getZ() - player.getZ(), livingEntity.getX() - player.getX());
        if (player.squaredDistanceTo(livingEntity) < 9.0) {
            float g;
            int i;
            for (i = 0; i < 5; ++i) {
                g = f + (float)i * (float)Math.PI * 0.4f;
                conjureFangs(player, player.getX() + (double)MathHelper.cos(g) * 1.5, player.getZ() + (double)MathHelper.sin(g) * 1.5, d, e, g, 0);
            }
            for (i = 0; i < 8; ++i) {
                g = f + (float)i * (float)Math.PI * 2.0f / 8.0f + 1.2566371f;
                conjureFangs(player, player.getX() + (double)MathHelper.cos(g) * 2.5, player.getZ() + (double)MathHelper.sin(g) * 2.5, d, e, g, 3);
            }
        } else {
            for (int i = 0; i < 16; ++i) {
                double h = 1.25 * (double)(i + 1);
                int j = 1 * i;
                conjureFangs(player, player.getX() + (double)MathHelper.cos(f) * h, player.getZ() + (double)MathHelper.sin(f) * h, d, e, f, j);
            }
        }

    }
    public static void conjureFangs(ServerPlayerEntity player, double x, double z, double maxY, double y, float yaw, int warmup) {
        /* TAKEN FROM SOURCE: net.minecraft.entity.mob.EvokerEntity.conjureFangs */
        BlockPos blockPos = BlockPos.ofFloored(x, y, z);
        boolean bl = false;
        double d = 0.0;
        do {
            BlockState blockState2;
            VoxelShape voxelShape;
            BlockPos blockPos2 = blockPos.down();
            BlockState blockState = player.getWorld().getBlockState(blockPos2);
            if (!blockState.isSideSolidFullSquare(player.getWorld(), blockPos2, Direction.UP)) continue;
            if (!player.getWorld().isAir(blockPos) && !(voxelShape = (blockState2 = player.getWorld().getBlockState(blockPos)).getCollisionShape(player.getWorld(), blockPos)).isEmpty()) {
                d = voxelShape.getMax(Direction.Axis.Y);
            }
            bl = true;
            break;
        } while ((blockPos = blockPos.down()).getY() >= MathHelper.floor(maxY) - 1);
        if (bl) {
            player.getWorld().spawnEntity(new EvokerFangsEntity(player.getWorld(), x, (double)blockPos.getY() + d, z, yaw, warmup, player));
            player.getWorld().emitGameEvent(GameEvent.ENTITY_PLACE, new Vec3d(x, (double)blockPos.getY() + d, z), GameEvent.Emitter.of(player));
        }
    }

    public void receiveUseHandC2SPacket(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
                                 PacketByteBuf buf, PacketSender responseSender) {
        int entityId = buf.readInt();
        LivingEntity target = (LivingEntity)player.getWorld().getEntityById(entityId);
        assert target != null;
        castSpell(player, target);
    }

}