package com.flying__8lack.datagen;

import com.flying__8lack.ModBlock;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

import static com.flying__8lack.advancedmovementmod.MODID;

public class ModBlockTagProvider extends BlockTagsProvider {
    public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
                               @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {

        tag(Tags.Blocks.ORES_IN_GROUND_STONE)
                .add(ModBlock.TITANIUM_ORE.get());

        tag(Tags.Blocks.ORES)
                .add(ModBlock.TITANIUM_ORE.get());

    }
}
