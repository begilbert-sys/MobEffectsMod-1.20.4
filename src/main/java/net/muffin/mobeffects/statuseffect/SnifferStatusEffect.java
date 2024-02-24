package net.muffin.mobeffects.statuseffect;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class SnifferStatusEffect extends MobStatusEffect {
    public SnifferStatusEffect(EntityType<? extends LivingEntity> mob, StatusEffect[] mimickedEffects, Item[] foodifiedItems, Settings settings) {
        super(mob, mimickedEffects, foodifiedItems, settings);
    }
    public ActionResult afterPlayerBlockBreakEventListener(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity) {
        Random random = Random.create();
        int randomNum = random.nextInt(20);
        if (state.isIn(BlockTags.SNIFFER_DIGGABLE_BLOCK) && randomNum == 1) { // 5% chance of spawning item on block break
            ItemStack resultingItemStack;
            if (random.nextBoolean()) { // 50-50 chance of getting either torchflower seeds or pitcher pods
                resultingItemStack = new ItemStack(Items.TORCHFLOWER_SEEDS);
            } else {
                resultingItemStack = new ItemStack(Items.PITCHER_POD);
            }
            ItemEntity resultingItemEntity = new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), resultingItemStack);
            if (world.spawnEntity(resultingItemEntity)) {
                return ActionResult.SUCCESS;
            }
        }
        return ActionResult.PASS;
    }
}
