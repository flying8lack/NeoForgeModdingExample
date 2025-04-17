package com.flying__8lack.recipe;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

public record CombinerRecipeInput(ItemStack input_a, ItemStack input_b) implements RecipeInput {

    @Override
    public ItemStack getItem(int i) {
        return i == 0 ? this.input_a() : this.input_b();
    }

    @Override
    public int size() {
        return 2;
    }
}
