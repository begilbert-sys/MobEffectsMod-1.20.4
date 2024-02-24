package net.muffin.mobeffects.statuseffect;


import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.event.GameEvent;
import net.muffin.mobeffects.MobEffectsMod;

public class SnowGolemStatusEffect extends UseHandMobStatusEffect{


    public SnowGolemStatusEffect(EntityType<? extends LivingEntity> mob, StatusEffect[] mimickedEffects, Item[] foodifiedItems, Settings settings, int cooldown) {
        super(mob, mimickedEffects, foodifiedItems, settings, cooldown);
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        /* TAKEN FROM SOURCE: net.minecraft.entity.passive.SnowGolemEntity.tickMovement */
        super.applyUpdateEffect(entity, amplifier);
        PlayerEntity player = (PlayerEntity)entity;
        if (!player.getWorld().isClient()) {
            if (player.getWorld().getBiome(player.getBlockPos()).isIn(BiomeTags.SNOW_GOLEM_MELTS)) {
                player.damage(player.getDamageSources().onFire(), 1.0f);
            }
            BlockState blockState = Blocks.SNOW.getDefaultState();
            for (int i = 0; i < 4; ++i) {
                int j = MathHelper.floor(player.getX() + (double)((float)(i % 2 * 2 - 1) * 0.25f));
                int k = MathHelper.floor(player.getY());
                int l = MathHelper.floor(player.getZ() + (double)((float)(i / 2 % 2 * 2 - 1) * 0.25f));
                BlockPos blockPos = new BlockPos(j, k, l);
                if (!player.getWorld().getBlockState(blockPos).isAir() || !blockState.canPlaceAt(player.getWorld(), blockPos)) continue;
                player.getWorld().setBlockState(blockPos, blockState);
                player.getWorld().emitGameEvent(GameEvent.BLOCK_PLACE, blockPos, GameEvent.Emitter.of(player, blockState));
            }
        }
    }

    @Override
    public void receiveUseHandC2SPacket(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
                                        PacketByteBuf buf, PacketSender responseSender) {
        ItemStack itemStack = new ItemStack(Items.SNOWBALL);
        player.setStackInHand(player.getActiveHand(), itemStack);
    }

}
