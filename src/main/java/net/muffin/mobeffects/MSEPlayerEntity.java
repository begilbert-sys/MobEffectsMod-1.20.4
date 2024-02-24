package net.muffin.mobeffects;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.event.listener.EntityGameEventHandler;
import net.muffin.mobeffects.helper.JukeboxEventListener;
import net.muffin.mobeffects.statuseffect.MobStatusEffect;
import org.jetbrains.annotations.Nullable;

public interface MSEPlayerEntity {
    MobStatusEffect getMobStatusEffect();
    void setMobStatusEffect(MobStatusEffect effect);

    int getMobStatusEffectDuration();

    void setLiftOffY(double y);
    @Nullable
    Double getLiftOffY();

    void incrementEffectDuration();

    int getEffectDuration();

    void updateJukeboxPos(BlockPos blockPos, boolean playing);

    BlockPos getJukeboxPos();

    EntityGameEventHandler<JukeboxEventListener> getJukeboxEventHandler();
}
