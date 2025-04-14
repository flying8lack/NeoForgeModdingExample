package com.flying__8lack.datagen;

import com.flying__8lack.ModBlock;
import com.flying__8lack.blocks.BlockThermiteDigger;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

import static com.flying__8lack.advancedmovementmod.MODID;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        blockWithItem(ModBlock.TITANIUM_ORE);
        blockWithItem(ModBlock.BROKEN_BLOCK);
        blockWithItem(ModBlock.RADAR_BLOCK);
        blockWithItem(ModBlock.COAL_POWER_BLOCK);



    }

    private void blockWithItem(DeferredBlock<Block> block){
        this.simpleBlockWithItem(block.get(), cubeAll(block.get()));
    }

    private void blockItem(DeferredBlock<Block> block){
        this.simpleBlockItem(block.get(), new ModelFile.UncheckedModelFile(MODID+":block/"+ block.getId().getPath()));
    }
}
