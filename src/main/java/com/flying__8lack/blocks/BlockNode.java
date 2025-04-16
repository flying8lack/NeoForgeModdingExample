package com.flying__8lack.blocks;

import com.flying__8lack.blocks.entity.BlockNodeEntity;
import com.flying__8lack.network.energy.EnergyNetwork;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;
import org.jetbrains.annotations.Nullable;

import static com.flying__8lack.advancedmovementmod.getLog;

public class BlockNode extends Block implements EntityBlock {


    public BlockNode(Properties properties) {
        super(properties);

    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {

        if(!level.isClientSide()){
            IEnergyStorage m = level.getCapability(
                    Capabilities.EnergyStorage.BLOCK,
                    pos,
                    null
            );

            if(m != null) {

                getLog().debug("Energy in the network: {} FE (network id: {})", m.getEnergyStored(),
                        EnergyNetwork.findNetworkIdByPos(pos));
            }
        }
        return super.useWithoutItem(state, level, pos, player, hitResult);
    }




    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        BlockEntity m = level.getBlockEntity(pos);
        if(m instanceof BlockNodeEntity b) {
            EnergyNetwork.removeFromNetwork(pos);
            b.updateConnectionsForSelfRemoval(level);

        }
        super.onRemove(state, level, pos, newState, movedByPiston);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return level.isClientSide() ? null : (level1, blockPos, blockState, t) -> {
            if(t instanceof BlockNodeEntity be){
                be.tick(level1, blockPos, blockState, be);
            }
        };
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new BlockNodeEntity(blockPos, blockState);
    }
}
