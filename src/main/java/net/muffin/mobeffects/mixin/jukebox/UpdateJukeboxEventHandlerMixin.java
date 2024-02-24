package net.muffin.mobeffects.mixin.jukebox;

import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.event.listener.EntityGameEventHandler;
import net.muffin.mobeffects.MSEPlayerEntity;
import net.muffin.mobeffects.MobEffectsMod;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BiConsumer;

@Mixin(Entity.class)
public abstract class UpdateJukeboxEventHandlerMixin {
    @Shadow
    public abstract World getWorld();
    @Inject(method = "updateEventHandler", at = @At("HEAD"))
    public void updateJukeboxEventHandler(BiConsumer<EntityGameEventHandler<?>, ServerWorld> callback, CallbackInfo ci) {
        World world = this.getWorld();
        Entity thisEntity = (Entity)(Object)this;
        if (!(thisEntity instanceof MSEPlayerEntity MSEPlayer)) {
            return;
        }
        if (world instanceof ServerWorld) {
            ServerWorld serverWorld = (ServerWorld)world;
            callback.accept(MSEPlayer.getJukeboxEventHandler(), serverWorld);
        }
    }
}
