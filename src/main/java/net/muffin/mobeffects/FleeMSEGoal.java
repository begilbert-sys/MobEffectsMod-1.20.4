package net.muffin.mobeffects;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.NoPenaltyTargeting;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.FleeEntityGoal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.util.math.Vec3d;
import net.muffin.mobeffects.statuseffect.MobStatusEffect;

public class FleeMSEGoal extends FleeEntityGoal<PlayerEntity> {
    private final TargetPredicate withinRangePredicate;
    private final double slowSpeed;
    private final double fastSpeed;

    private final MobStatusEffect mobStatusEffect;

    public FleeMSEGoal(PathAwareEntity fleeingEntity, MobStatusEffect mobStatusEffect, float fleeDistance, double fleeSlowSpeed, double fleeFastSpeed) {
        super(fleeingEntity, PlayerEntity.class, livingEntity -> true, fleeDistance, fleeSlowSpeed, fleeFastSpeed, EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR::test);
        this.slowSpeed = fleeSlowSpeed;
        this.fastSpeed = fleeFastSpeed;
        this.withinRangePredicate = TargetPredicate.createAttackable().setBaseMaxDistance(fleeDistance).setPredicate(inclusionSelector.and(extraInclusionSelector));
        this.mobStatusEffect = mobStatusEffect;
    }
    @Override
    public boolean canStart() {
        /* TAKEN FROM SOURCE: net.minecraft.entity.ai.goal.FleeEntityGoal.canStart */
        this.targetEntity = this.mob.getWorld().getClosestEntity(this.mob.getWorld().getEntitiesByClass(this.classToFleeFrom, this.mob.getBoundingBox().expand(this.fleeDistance, 3.0, this.fleeDistance), livingEntity -> true), this.withinRangePredicate, this.mob, this.mob.getX(), this.mob.getY(), this.mob.getZ());
        if (this.targetEntity == null || !(this.targetEntity instanceof MSEPlayerEntity MSEPlayerEntity && MSEPlayerEntity.getMobStatusEffect() == mobStatusEffect)) {
            return false;
        }
        Vec3d vec3d = NoPenaltyTargeting.findFrom(this.mob, 16, 7, ((Entity)this.targetEntity).getPos());
        if (vec3d == null) {
            return false;
        }
        if (((Entity)this.targetEntity).squaredDistanceTo(vec3d.x, vec3d.y, vec3d.z) < ((Entity)this.targetEntity).squaredDistanceTo(this.mob)) {
            return false;
        }
        this.fleePath = this.fleeingEntityNavigation.findPathTo(vec3d.x, vec3d.y, vec3d.z, 0);
        return this.fleePath != null;
    }
}
