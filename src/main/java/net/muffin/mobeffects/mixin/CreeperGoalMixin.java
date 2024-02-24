package net.muffin.mobeffects.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.world.World;
import net.muffin.mobeffects.FleeMSEGoal;
import net.muffin.mobeffects.statuseffect.MobStatusEffects;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CreeperEntity.class)
public abstract class CreeperGoalMixin extends HostileEntity {
    protected CreeperGoalMixin(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "initGoals", at = @At("TAIL"))
    private void afraidOfCatStatusEffect(CallbackInfo ci) {
        this.goalSelector.add(2, new FleeMSEGoal(this, MobStatusEffects.CAT, 10.0f, 1.0, 1.2));
        this.goalSelector.add(2, new FleeMSEGoal(this, MobStatusEffects.OCELOT, 10.0f, 1.0, 1.2));
    }
}
