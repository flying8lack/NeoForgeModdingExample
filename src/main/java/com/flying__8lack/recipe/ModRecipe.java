package com.flying__8lack.recipe;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.flying__8lack.advancedmovementmod.MODID;

public class ModRecipe {

    public static final DeferredRegister<RecipeSerializer<?>> SERIAL = DeferredRegister.create(
            Registries.RECIPE_SERIALIZER,
            MODID);

    public static final DeferredRegister<RecipeType<?>> TYPE = DeferredRegister.create(Registries.RECIPE_TYPE,
            MODID);


    public static final DeferredHolder<RecipeType<?>,
            RecipeType<CombinerRecipe>> CombinerType = TYPE.register(
                    "combiner",
            () -> new RecipeType<CombinerRecipe>() {
                @Override
                public String toString() {
                    return "combiner";
                }
            }
    );

    public static final DeferredHolder<RecipeSerializer<?>,
            RecipeSerializer<CombinerRecipe>> Combiner = SERIAL.register(
            "combiner",
            CombinerRecipe.Serialiser::new);


    public static void Register(IEventBus bus){
        SERIAL.register(bus);
        TYPE.register(bus);
    }

}
