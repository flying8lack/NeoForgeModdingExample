package com.flying__8lack.blocks;

import com.flying__8lack.blocks.entity.BlockWardenToyEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class BlockWardenToy extends Block implements EntityBlock {
    public BlockWardenToy(Properties properties) {
        super(properties);
    }


    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        if(level.isClientSide()){
            return null;
        } else {
            return (level1, blockPos, blockState, t) -> {
                if(t instanceof BlockWardenToyEntity be){
                    be.tick(level1, blockPos, blockState, be);
                }
            };
        }
    }



    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new BlockWardenToyEntity(blockPos,  blockState);
    }

}
