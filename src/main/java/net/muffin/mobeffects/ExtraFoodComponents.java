package net.muffin.mobeffects;

import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

import java.util.HashMap;
import java.util.Map;

public class ExtraFoodComponents {

    public static Map<Item, FoodComponent> itemFoodComponentMap = new HashMap<>();

    public static final FoodComponent WHEAT_SEEDS = create(Items.WHEAT_SEEDS, new FoodComponent.Builder().hunger(2).saturationModifier(0.3f).build());
    public static final FoodComponent MELON_SEEDS = create(Items.MELON_SEEDS, new FoodComponent.Builder().hunger(2).saturationModifier(0.3f).build());
    public static final FoodComponent PUMPKIN_SEEDS = create(Items.PUMPKIN_SEEDS, new FoodComponent.Builder().hunger(2).saturationModifier(0.3f).build());
    public static final FoodComponent TORCHFLOWER_SEEDS = create(Items.TORCHFLOWER_SEEDS, new FoodComponent.Builder().hunger(2).saturationModifier(0.3f).build());
    public static final FoodComponent BAMBOO = create(Items.BAMBOO, new FoodComponent.Builder().hunger(2).saturationModifier(0.3f).build());

    public static final FoodComponent BEETROOT = create(Items.BEETROOT, new FoodComponent.Builder().hunger(5).saturationModifier(0.6f).build());

    public static final FoodComponent POTATO = create(Items.POTATO, new FoodComponent.Builder().hunger(5).saturationModifier(0.6f).build());
    public static FoodComponent create(Item item, FoodComponent foodComponent) {
        itemFoodComponentMap.put(item, foodComponent);
        return foodComponent;
    }
    public static boolean isModdedFoodComponent(FoodComponent foodComponent) {
        return itemFoodComponentMap.containsValue(foodComponent);
    }
}
