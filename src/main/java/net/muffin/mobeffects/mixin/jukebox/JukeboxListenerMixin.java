package net.muffin.mobeffects.mixin.jukebox;

import com.mojang.authlib.GameProfile;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.EntityPositionSource;
import net.minecraft.world.event.listener.EntityGameEventHandler;
import net.muffin.mobeffects.MSEPlayerEntity;
import net.muffin.mobeffects.MobEffectsMod;
import net.muffin.mobeffects.helper.JukeboxEventListener;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BiConsumer;

@Mixin(PlayerEntity.class)
public abstract class JukeboxListenerMixin extends LivingEntity implements MSEPlayerEntity {
    @Unique
    protected EntityGameEventHandler<JukeboxEventListener> jukeboxEventHandler;

    @Unique
    @Nullable
    private BlockPos jukeboxPos;

    protected JukeboxListenerMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Unique
    public void updateJukeboxPos(BlockPos jukeboxPos, boolean playing) {
        MobEffectsMod.LOGGER.info("jukebox position updated");
        if (playing) {
            this.jukeboxPos = jukeboxPos;
        }
        else if (jukeboxPos.equals(this.jukeboxPos)) {
            this.jukeboxPos = null;
        }
    }

    @Unique
    public EntityGameEventHandler<JukeboxEventListener> getJukeboxEventHandler() {
        return jukeboxEventHandler;
    }
    @Unique
    @Nullable
    public BlockPos getJukeboxPos() {
        return this.jukeboxPos;
    }
    @Inject(method = "<init>", at = @At("TAIL"))
    private void addGameEventListener(World world, BlockPos pos, float yaw, GameProfile gameProfile, CallbackInfo ci) {
        this.jukeboxEventHandler = new EntityGameEventHandler<>(new JukeboxEventListener(this, new EntityPositionSource(this, this.getStandingEyeHeight()), 16));
    }
}
