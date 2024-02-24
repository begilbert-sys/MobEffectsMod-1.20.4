package net.muffin.mobeffects.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.world.BlockView;
import net.muffin.mobeffects.MSEPlayerEntity;
import net.muffin.mobeffects.MobEffectsMod;
import net.muffin.mobeffects.statuseffect.MobStatusEffects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Block.class)
public abstract class BounceyMixin {
    private static final Logger LOGGER = LoggerFactory.getLogger(MobEffectsMod.MOD_ID);
    @Inject(method = "onEntityLand", at = @At("HEAD"), cancellable = true)
    public void allBlocksAreBouncy(BlockView world, Entity entity, CallbackInfo ci) {
        if (entity instanceof MSEPlayerEntity MSEPlayerEntity && MSEPlayerEntity.getMobStatusEffect() == MobStatusEffects.SLIME && !entity.bypassesLandingEffects()) {
            Blocks.SLIME_BLOCK.onEntityLand(world, entity);
            ci.cancel();
        }
    }
}
