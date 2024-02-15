package net.muffin.mobeffects.networking.packet;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.block.BlockState;
import net.minecraft.entity.mob.EvokerEntity;
import net.minecraft.entity.mob.EvokerFangsEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.event.GameEvent;
import net.muffin.mobeffects.MobEffectsMod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EvokerFangsEntityC2SPacket {
    private static final Logger LOGGER = LoggerFactory.getLogger(MobEffectsMod.MOD_ID);
    public static void castSpellRanged(ServerPlayerEntity player) {
        /* TAKEN FROM SOURCE: net.minecraft.entity.mob.EvokerEntity.castSpell */
        double d = player.getY();
        double e = player.getY();

        for (int i = 0; i < 16; ++i) {
            double h = 1.25 * (double) (i + 1);
            int j = 1 * i;
            conjureFangs(player, player.getX() + h, player.getZ() + h, d, e, player.getHeadYaw(), j);
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
    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender) {
        castSpellRanged(player);

    }
}
