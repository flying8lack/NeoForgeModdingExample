package com.flying__8lack.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public record CombinerRecipe(Ingredient a, Ingredient b, ItemStack c) implements Recipe<CombinerRecipeInput> {



    @Override
    public boolean matches(CombinerRecipeInput combinerRecipeInput, Level level) {
        if(level.isClientSide()){
            return false;
        }

        return a.test(combinerRecipeInput.getItem(0)) && b.test(combinerRecipeInput.getItem(1));
    }

    @Override
    public ItemStack assemble(CombinerRecipeInput combinerRecipeInput, HolderLookup.Provider provider) {
        return c.copy();
    }

    @Override
    public boolean canCraftInDimensions(int i, int i1) {
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider provider) {
        return c;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipe.Combiner.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipe.CombinerType.get();
    }

    static class Serialiser implements RecipeSerializer<CombinerRecipe> {

        public static final MapCodec<CombinerRecipe> CODEC = RecordCodecBuilder.mapCodec(
                inst -> inst.group(
                        Ingredient.CODEC_NONEMPTY.fieldOf("input_a").forGetter(CombinerRecipe::a),
                        Ingredient.CODEC_NONEMPTY.fieldOf("input_b").forGetter(CombinerRecipe::b),
                        ItemStack.CODEC.fieldOf("output").forGetter(CombinerRecipe::c)

                ).apply(inst, CombinerRecipe::new)
        );

        public static final StreamCodec<RegistryFriendlyByteBuf, CombinerRecipe> STREAM_CODEC = StreamCodec.composite(
                Ingredient.CONTENTS_STREAM_CODEC, CombinerRecipe::a,
                Ingredient.CONTENTS_STREAM_CODEC, CombinerRecipe::b,
                ItemStack.STREAM_CODEC, CombinerRecipe::c,
                CombinerRecipe::new
        );

        @Override
        public MapCodec<CombinerRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, CombinerRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
