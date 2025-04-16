package com.flying__8lack.datagen;

import com.flying__8lack.ModItem;
import com.flying__8lack.util.ModTag;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static com.flying__8lack.advancedmovementmod.MODID;

public class ModRecipeProvider  extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);

    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        super.buildRecipes(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.TORCH)
                .requires(Items.LAPIS_LAZULI)
                .requires(ItemTags.MEAT)
                .unlockedBy("has_lapis_lazuli", has(Items.LAPIS_LAZULI))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItem.THERMITE_DIGGER_BLOCK_ITEM)
                .pattern("PGP")
                .pattern("SIS")
                .define('P', ItemTags.PLANKS)
                .define('G', Items.GUNPOWDER)
                .define('S', Items.SUGAR)
                .define('I', Tags.Items.INGOTS_IRON)
                .unlockedBy("has_iron_ingot", has(Tags.Items.INGOTS_IRON))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItem.THRUSTER)
                .pattern(" T ")
                .pattern("TIT")
                .pattern("T T")
                .define('T', ModTag.Items.TITANIUM_INGOTS)
                .define('I', Tags.Items.INGOTS_IRON)
                .unlockedBy("has_titanium_ingot", has(ModTag.Items.TITANIUM_INGOTS))
                .save(recipeOutput);

        oreSmelting(recipeOutput, List.of(ModItem.TITANIUM_ORE_BLOCK_ITEM.get()),
                RecipeCategory.MISC, ModItem.TITANIUM_INGOT.get(), 0.6f, 200,  "titanium");
    }



    protected static void oreSmelting(RecipeOutput recipeOutput, List<ItemLike> pIngrediant,
                                   RecipeCategory pCategory, ItemLike result, float exp, int cookingTime,
                                   String group){
        oreCooking(recipeOutput, RecipeSerializer.SMELTING_RECIPE, SmeltingRecipe::new,
                pIngrediant, pCategory, result, exp, cookingTime, group, "_from_smelting");
    }

    protected  static <T extends AbstractCookingRecipe> void oreCooking(RecipeOutput recipeOutput,
                                                                    RecipeSerializer<T> pCookingSerializer,
                                                                    AbstractCookingRecipe.Factory<T> factory,
                                                                    List<ItemLike> pIngredients, RecipeCategory pCategory,
                                                                    ItemLike pResult, float pExperience, int pCookingTime,
                                                                    String pGroup, String pRecipeName){
        for(ItemLike item : pIngredients){
            SimpleCookingRecipeBuilder.generic(Ingredient.of(item), pCategory, pResult, pExperience, pCookingTime,
                    pCookingSerializer, factory).group(pGroup).unlockedBy(getHasName(item), has(item))
                    .save(recipeOutput, MODID +":"+getItemName(pResult) + pRecipeName + "_" + getItemName(item));
        }
    }
}
