package net.muffin.mobeffects.statuseffect;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;


public class MobStatusEffect extends AbstractMobStatusEffect {
    protected final StatusEffect[] mimickedEffects;
    protected final Item[] foodifiedItems;
    protected final EntityType<? extends LivingEntity> mobType;

    public MobStatusEffect(EntityType<? extends LivingEntity> mob, StatusEffect[] mimickedEffects, Item[] foodifiedItems, Settings settings) {
        super(settings);
        this.mobType = mob;
        this.mimickedEffects = mimickedEffects;
        this.foodifiedItems = foodifiedItems;
    }

    public String toString() {
        return EntityType.getId(mobType).getPath().toLowerCase();
    }

    public EntityType<? extends LivingEntity> getMobType() { return mobType; }
    public boolean mimicsStatusEffect(StatusEffect effect) {
        for(StatusEffect e : mimickedEffects) {
            if (e == effect) {
                return true;
            }
        }
        return false;
    }
    public boolean foodifiesItem(Item item) {
        for(Item i: foodifiedItems) {
            if (i == item) {
                return true;
            }
        }
        return false;
    }

}
