package net.muffin.mobeffects.helper;

import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.event.PositionSource;
import net.minecraft.world.event.listener.GameEventListener;
import net.muffin.mobeffects.MSEPlayerEntity;
import net.muffin.mobeffects.MobEffectsMod;

public class JukeboxEventListener
        implements GameEventListener {
    private final PositionSource positionSource;
    private final int range;

    private MSEPlayerEntity player;

    public JukeboxEventListener(MSEPlayerEntity player, PositionSource positionSource, int range) {
        this.player = player;
        this.positionSource = positionSource;
        this.range = range;
    }

    @Override
    public PositionSource getPositionSource() {
        return this.positionSource;
    }

    @Override
    public int getRange() {
        return this.range;
    }

    @Override
    public boolean listen(ServerWorld world, GameEvent event, GameEvent.Emitter emitter, Vec3d emitterPos) {
        if (event == GameEvent.JUKEBOX_PLAY) {
            player.updateJukeboxPos(BlockPos.ofFloored(emitterPos), true);
            return true;
        }
        if (event == GameEvent.JUKEBOX_STOP_PLAY) {
            player.updateJukeboxPos(BlockPos.ofFloored(emitterPos), false);
            return true;
        }
        return false;
    }
}