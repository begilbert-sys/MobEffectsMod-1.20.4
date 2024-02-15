package net.muffin.mobeffects;


import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.TrackedData;

public interface GuardianPlayer {
    /*
    This interface exists to be implemented by the PlayerEntity via the GuardianBeamMixin.
    It allows the GuardianBeamRendererMixin to access public methods from GuardianBeamMixin, which helps render the player's guardian beam.
    All methods here are originally defined in net.minecraft.entity.mob.GuardianEntity.
    */
    int getWarmupTime();
    void setBeamTarget(int entityId);
    boolean hasBeamTarget();

    LivingEntity getBeamTarget();

    float getBeamProgress(float tickDelta);

    float getBeamTicks();


}
