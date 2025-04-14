package com.flying__8lack.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import static com.flying__8lack.advancedmovementmod.MODID;

public class ModTag {

    public static class Items {

        public static final TagKey<Item> TITANIUM_INGOTS = register("titanium_ingots");

        public static TagKey<Item> register(String name){
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath(MODID, name));
        }
    }
}
