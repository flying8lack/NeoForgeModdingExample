package com.flying__8lack.blocks;

import com.flying__8lack.blocks.entity.BlockPipeEntity;
import com.flying__8lack.network.NetworkManager;
import com.flying__8lack.network.PipeNetwork;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.Set;


public class BlockPipe extends Block implements EntityBlock {


    public BlockPipe(Properties properties) {
        super(properties);
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if(level.getBlockEntity(pos) instanceof BlockPipeEntity be){
            if(be.getNetwork() != null) be.getNetwork().scanUpdateConnections(pos);
        }

    }

    @Override
    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {

        if(level.getBlockEntity(pos) instanceof BlockPipeEntity be){
            PipeNetwork n = NetworkManager.getInstance().createNewNetwork(Set.of(pos), level);
            be.setNetwork(n);
            be.getNetwork().addToNetwork(pos, be);
        }

    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if(level.getBlockEntity(pos) instanceof BlockPipeEntity be){
            if(be.getNetwork() != null) be.getNetwork().removeFromNetwork(pos, level);
        }
    }

    @Override
    public void onNeighborChange(BlockState state, LevelReader level, BlockPos pos, BlockPos neighbor) {
        if(level.getBlockEntity(pos) instanceof BlockPipeEntity be){
            if(be.getNetwork() != null) be.getNetwork().updateConnections();
        }
    }


    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return level.isClientSide() ? null : (level1, blockPos, blockState, t) -> {
            if(level.getBlockEntity(blockPos) instanceof BlockPipeEntity be){
                be.tick(level1, blockPos, blockState, be);
            }
        };
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new BlockPipeEntity(blockPos, blockState);
    }
}
