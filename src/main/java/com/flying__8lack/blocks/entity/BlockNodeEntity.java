package com.flying__8lack.blocks.entity;

import com.flying__8lack.ModBlock;
import com.flying__8lack.util.energy.EnergyNetwork;
import com.flying__8lack.util.ICapProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.items.IItemHandler;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BlockNodeEntity extends BlockEntity implements ICapProvider {


    private int discovery_delay = 28;
    private int update_delay = 7;





    private final List<IEnergyStorage> energySources =
            new ArrayList<>();
    private final List<BlockNodeEntity> connections =
            new ArrayList<>();

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    private Optional<EnergyNetwork> access = Optional.empty();


    private static final List<Direction> directions = List.of(
            Direction.EAST,
            Direction.NORTH,
            Direction.WEST,
            Direction.SOUTH,
            Direction.UP,
            Direction.DOWN);


    public BlockNodeEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntity.NODE_ENTITY.get(), pos, blockState);



    }

    /**
     * A getter for the energy network.
     * @author flying__8lack
     * @return An optional network object.
     * @since 0.1
     * **/
    public Optional<EnergyNetwork> access(){
        return this.access;
   }


    public void invalidateAccess(){
        this.access = Optional.empty();
        setChanged();
    }

    public void changeAccess(Optional<EnergyNetwork> new_access){
        this.access = new_access;
    }

    public List<BlockNodeEntity> getConnections() {
        return connections;
    }



    public void updateConnectionsForSelfRemoval(Level level){
        this.invalidateAccess();
        for (BlockNodeEntity m : connections) {
            if(m.access().isPresent()) {
                m.checkEnergyBlocks(level);
                m.updateConnectionsForSelfRemoval(level);
            }
        }
    }

    /**
     * A method used to check adjacent blocks for {@code IEnergyStorage} interface or {@code BlockNodeEntity} blocks.
     * the blocks that meet the requirement are stored in {@code energySources} and {@code connections}
     * @param level the level which the block entity exists.
     * @author flying__8lack
     * @since 0.1
     * **/
    public void checkEnergyBlocks(Level level){

        BlockPos pos = this.getBlockPos();
        energySources.clear();
        connections.clear();
        for (Direction d : directions) {
            if (level.getBlockState(pos.relative(d)).is(ModBlock.NODE_BLOCK)) {
                Optional<BlockNodeEntity> m = level.getBlockEntity(pos.relative(d), ModBlockEntity.NODE_ENTITY.get());
                m.ifPresent(connections::add);
                continue;
            }
            var f = level.getCapability(
                    Capabilities.EnergyStorage.BLOCK,
                    pos.relative(d),
                    d
            );
            if (f != null) {
                energySources.add(f);
            }
        }
        discovery_delay = 30;
    }

    private void checkEnergyBlocks(Level level, BlockPos pos) {
        energySources.clear();
        connections.clear();
        for (Direction d : directions) {
            if (level.getBlockState(pos.relative(d)).is(ModBlock.NODE_BLOCK)) {
                Optional<BlockNodeEntity> m = level.getBlockEntity(pos.relative(d), ModBlockEntity.NODE_ENTITY.get());
                m.ifPresent(connections::add);
                continue;
            }
            var f = level.getCapability(
                    Capabilities.EnergyStorage.BLOCK,
                    pos.relative(d),
                    d
            );
            if (f != null) {
                energySources.add(f);
            }
        }

    }


    public void updateNetwork(BlockPos pos){

        if(this.access.isEmpty() && !this.energySources.isEmpty() && !this.connections.isEmpty()){
            this.access = EnergyNetwork.registerToNetwork(pos, this);
        }

        for (BlockNodeEntity connection : this.connections) {
            this.access.ifPresent(c -> c.inviteMember(connection.getBlockPos(), connection));

        }


    }



    public void pushEnergy(){
        if(this.access.isPresent()) {
            for (IEnergyStorage storage : this.energySources) {
                if(!storage.canReceive() ){
                    continue;
                }
                storage.receiveEnergy(
                        this.access.get().getEnergy().extractEnergy(
                                Math.clamp(storage.getMaxEnergyStored() - storage.getEnergyStored(), 0, 1000),
                        false), false); //push energy to the storage enough to fill it
            }
        }
    }



    public void tick(Level level, BlockPos pos, BlockState state, BlockNodeEntity entity){
        if(!level.isClientSide()) {
            if (update_delay == 0) {
                updateNetwork(pos);
                update_delay = 7;
            } else {
                update_delay--;
            }

            if (discovery_delay == 0) {
                checkEnergyBlocks(level, pos);
                discovery_delay = 28;
            } else {
                discovery_delay--;
            }

            if(discovery_delay % 4 == 0){
                pushEnergy();
            }
        }

    }




    @Override
    public @Nullable IItemHandler getCapabilityItem(@Nullable Direction pos, Object o) {
        return null;
    }

    @Override
    public @Nullable IEnergyStorage getCapabilityEnergy(@Nullable Direction pos, Object o) {
        return this.access.map(EnergyNetwork::getEnergy).orElse(null);
    }


}
