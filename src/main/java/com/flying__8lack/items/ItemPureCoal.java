package com.flying__8lack.items;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import org.jetbrains.annotations.Nullable;

public class ItemPureCoal extends Item {
    public ItemPureCoal(Properties properties) {
        super(properties);
    }


    @Override
    public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
        return 3240 + 5*itemStack.getCount();
    }
}
