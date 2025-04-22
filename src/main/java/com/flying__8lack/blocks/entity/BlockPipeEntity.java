package com.flying__8lack.blocks.entity;

import com.flying__8lack.network.NetworkManager;
import com.flying__8lack.network.PipeNetwork;
import com.flying__8lack.util.ICapProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.items.IItemHandler;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class BlockPipeEntity extends BlockEntity  implements PipeNetwork.InterestPoint, ICapProvider {

    private final Set<BlockPos> connections = new HashSet<>();
    private PipeNetwork network = null;
    private int network_cache_delay = 8;

    private HashMap<Direction, IEnergyStorage> EnergyAccess = new HashMap<>();

    public BlockPipeEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntity.PIPE_ENTITY.get(), pos, blockState);
    }

    public PipeNetwork getNetwork() {
        return network;
    }

    public void setNetwork(PipeNetwork network) {
        this.network = network;
    }




    public <T extends BlockPipeEntity> void tick(Level level, BlockPos pos, BlockState blockState, T t){


        if(network_cache_delay == 0){
            network = NetworkManager.getInstance().networks().get(pos);
            if(network == null) {
                network_cache_delay = 13;
            } else {
                network_cache_delay = 120;
            }

            return;
        } else {
            network_cache_delay--;
        }

        if(network == null) return;


        if(network.getEnergy().getEnergyStored() > 0){
            //TODO: push energy to nearby energy storages
        }
    }





    @Override
    public Set<BlockPos> getConnections() {
        return connections;
    }

    @Override
    public void setConnections(Set<BlockPos> conn) {

    }

    @Override
    public void removeConnection(BlockPos pos) {
        connections.remove(pos);
    }

    @Override
    public @Nullable IItemHandler getCapabilityItem(@Nullable Direction pos, Object o) {
        return null;
    }

    @Override
    public @Nullable IEnergyStorage getCapabilityEnergy(@Nullable Direction pos, Object o) {
        if(network == null) return null;
        return network.getEnergy();
    }
}
