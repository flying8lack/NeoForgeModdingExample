package com.flying__8lack.items.armor;

import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.EnumMap;
import java.util.List;
import java.util.function.Supplier;

import static com.flying__8lack.advancedmovementmod.MODID;

public class ModArmorMaterial {


    public static final Holder<ArmorMaterial> TITANIUM_MATERIAL = register("titanium",
            Util.make(new EnumMap<>(ArmorItem.Type.class), attribute -> {
                attribute.put(ArmorItem.Type.BOOTS, 5);
                attribute.put(ArmorItem.Type.LEGGINGS, 7);
                attribute.put(ArmorItem.Type.CHESTPLATE, 9);
                attribute.put(ArmorItem.Type.HELMET, 5);
                attribute.put(ArmorItem.Type.BODY, 11);
            }), 16, 2f, 0.1f, () -> Items.IRON_INGOT);

    private static Holder<ArmorMaterial> register(String name, EnumMap<ArmorItem.Type, Integer> typeProtection,
                                                  int enchant, float toughness, float knockback,
                                                  Supplier<Item> materialItem){
        ResourceLocation location = ResourceLocation.fromNamespaceAndPath(MODID, name);
        Holder<SoundEvent> sound = SoundEvents.ARMOR_EQUIP_CHAIN;
        Supplier<Ingredient> ingredient = () -> Ingredient.of(materialItem.get());
        List<ArmorMaterial.Layer> layers = List.of(new ArmorMaterial.Layer(location));

        EnumMap<ArmorItem.Type, Integer> types = new EnumMap<>(ArmorItem.Type.class);
        for(ArmorItem.Type valtype : ArmorItem.Type.values()){
            types.put(valtype, typeProtection.get(valtype));
        }

        return Registry.registerForHolder(BuiltInRegistries.ARMOR_MATERIAL, location,
                new ArmorMaterial(typeProtection, enchant, sound, ingredient, layers, toughness, knockback));

    }
}
