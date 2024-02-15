package net.muffin.mobeffects;

import net.muffin.mobeffects.statuseffect.MobStatusEffect;

public interface MobStatusEntity {
    MobStatusEffect getMobStatusEffect();
    void setMobStatusEffect(MobStatusEffect effect);
}
