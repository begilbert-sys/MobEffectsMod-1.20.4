package net.muffin.mobeffects.statuseffect;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.muffin.mobeffects.MSEPlayerEntity;
import net.minecraft.util.TypeFilter;

import net.minecraft.block.entity.JukeboxBlockEntity;
import net.muffin.mobeffects.MobEffectsMod;

import java.util.Objects;
import java.util.function.Predicate;


public abstract class AbstractMobStatusEffect extends StatusEffect {

    protected final Settings settings;

    protected AbstractMobStatusEffect(Settings settings) {
        super(StatusEffectCategory.NEUTRAL,0xFFFFFF);
        this.settings = settings;

    }

    private void suffocateAboveWater(PlayerEntity player) {
        if (!player.isSubmergedIn(FluidTags.WATER)) {
            int air = player.getAir();
            player.setAir(air - 2);
            if (player.getAir() == -20) {
                player.setAir(0);
                player.damage(player.getDamageSources().drown(), 2.0f);
            }
        } else {
            player.setAir(300);
        }
    }

    private void damageIfWet(PlayerEntity player) {
        int duration = ((MSEPlayerEntity)player).getEffectDuration();
        if (player.isWet() && duration % 10 == 0) { // damage every 10 ticks
            player.damage(player.getDamageSources().drown(), 1.0f);
        }
    }

    private void flutterMovementUpdate(PlayerEntity player) {
        final int heightLimit = 3;
        if (player instanceof ClientPlayerEntity clientPlayer && clientPlayer instanceof MSEPlayerEntity MSEPlayer) {
            double currentY = clientPlayer.getY();
            Double liftOffY = MSEPlayer.getLiftOffY();
            if (clientPlayer.isOnGround() || (liftOffY != null && clientPlayer.getY() < liftOffY)) {
                MSEPlayer.setLiftOffY(currentY);
            }
            if (clientPlayer.input.jumping) {
                if (liftOffY != null && currentY < liftOffY + heightLimit) {
                    Vec3d vec3d = player.getVelocity();
                    if (vec3d.y > 0) {
                        player.setVelocity(vec3d.add(0.0, 0.05, 0.0));
                    } else {
                        player.setVelocity(vec3d.add(0.0, 0.1, 0.0));
                    }
                }
            }
        }
    }

    private void jukeboxAOEHealing(PlayerEntity player) {
        MSEPlayerEntity MSEPlayer = (MSEPlayerEntity)player;
        BlockPos jukeboxPos = MSEPlayer.getJukeboxPos();
        boolean applyEffect = MSEPlayer.getEffectDuration() % 20 == 0;
        if (applyEffect && jukeboxPos != null && jukeboxPos.isWithinDistance(player.getPos(), 5.0)) {
            player.heal(1.0f);
        }
    }
    @Override
    public final boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {

        if (!(entity instanceof PlayerEntity player && entity instanceof MSEPlayerEntity MSEPlayer)) {
            return;
        }
        MSEPlayer.incrementEffectDuration();
        if (settings.suffocatesAboveWater) {
            suffocateAboveWater(player);
        }
        if (settings.hydrophobic) {
            damageIfWet(player);
        }
        if (settings.flutters) {
            flutterMovementUpdate(player);
        }
        if (settings.jukeboxHealing) {
            jukeboxAOEHealing(player);
        }
    }

    public boolean fallDamageImmune() {
        return settings.fallDamageImmune;
    }

    public boolean glows() { return settings.glows; }
    public static class Settings {
        boolean canWalkOnSnow = false;

        boolean glows = false;
        boolean hydrophobic = false;

        boolean suffocatesAboveWater = false;
        boolean fallDamageImmune = false;
        boolean flutters = false;
        boolean jukeboxHealing = false;

        public Settings canWalkOnSnow() {
            this.canWalkOnSnow = true;
            return this;
        }

        public Settings fallDamageImmune() {
            this.fallDamageImmune = true;
            return this;
        }
        public Settings flutters() {
            this.flutters = true;
            return this;
        }
        public Settings glows() {
            this.glows = true;
            return this;
        }
        public Settings hydrophobic() {
            this.hydrophobic = true;
            return this;
        }
        public Settings suffocatesAboveWater() {
            this.suffocatesAboveWater = true;
            return this;
        }
        public Settings jukeboxHealing() {
            this.jukeboxHealing = true;
            return this;
        }
    }
}
