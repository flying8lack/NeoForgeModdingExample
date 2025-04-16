package com.flying__8lack.datagen;

import com.flying__8lack.ModItem;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import static com.flying__8lack.advancedmovementmod.MODID;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {

        basicItem(ModItem.TITANIUM_INGOT.get());
        basicItem(ModItem.THRUSTER.get());


        spawnEggItem(ModItem.GRASS_WALKER_EGG.get());

    }
}
