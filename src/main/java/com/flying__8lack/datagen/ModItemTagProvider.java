package com.flying__8lack.datagen;

import com.flying__8lack.ModItem;
import com.flying__8lack.util.ModTag;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.internal.NeoForgeItemTagsProvider;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

import static com.flying__8lack.advancedmovementmod.MODID;

public class ModItemTagProvider extends ItemTagsProvider {
    public ModItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
                              CompletableFuture<TagLookup<Block>> blockTags,
                              @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {

        tag(ModTag.Items.TITANIUM_INGOTS)
                .add(ModItem.TITANIUM_INGOT.get());

        tag(Tags.Items.INGOTS)
                .add(ModItem.TITANIUM_INGOT.get());

    }
}
