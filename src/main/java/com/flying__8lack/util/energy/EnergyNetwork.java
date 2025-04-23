package com.flying__8lack.util.energy;

import com.flying__8lack.blocks.entity.BlockNodeEntity;
import net.minecraft.core.BlockPos;
import net.neoforged.jarjar.nio.util.Lazy;
import net.neoforged.neoforge.energy.EnergyStorage;
import net.neoforged.neoforge.energy.IEnergyStorage;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.TreeMap;

/**
 * This class is responsible for representing networks. the network hold common data, such as energy storage API,
 * which could be accessed by all members of the networks. All networks in the world are stored in the {@code Networks}
 * static field, to ensure common access and tracking across chunks.
 * <p>
 * <b>Warning</b>: Any energy stored in networks will be lost once the world is unloaded!
 *
 *
 * @author flying__8lack
 * @since 0.1
 */
public class EnergyNetwork {

    public static final List<EnergyNetwork> Networks = new ArrayList<>();

    private final int id;
    public final TreeMap<BlockPos, BlockNodeEntity> member = new TreeMap<>();
    public final Lazy<IEnergyStorage> common_energy = Lazy.of(() -> new EnergyStorage(10_000_000));


    public IEnergyStorage getEnergy(){
        return common_energy.get();
    }


    public static Optional<EnergyNetwork> registerToNetwork(BlockPos pos, BlockNodeEntity e){

        for(EnergyNetwork n : Networks){

            for(BlockNodeEntity m : n.member.values()){
                if(m.equals(e)){
                    return Optional.of(n);
                }
                if(m.getBlockPos().getCenter().distanceToSqr(pos.getCenter()) < 1.2){
                    n.member.put(pos, e);
                    return Optional.of(n);
                }
            }
        }


        if(!e.getConnections().isEmpty()){
            EnergyNetwork created = new EnergyNetwork(pos, e);
            Networks.add(created );
            return Optional.of(created);
        }

        return Optional.empty();
    }



    public static void removeFromNetwork(BlockPos pos){
        for(EnergyNetwork n : Networks){
            if(n.member.containsKey(pos)){
                n.member.get(pos).invalidateAccess();
                n.member.remove(pos);
            }
        }
    }


    /**
     *
     * @param pos the location of the block entity to move.
     */
    public void submergeNetwork(BlockPos pos){
        if((Networks.isEmpty())){
            return;
        }


        for (EnergyNetwork entry : Networks) {
            if(entry == this){
                continue;
            }
            if(entry.member.containsKey(pos)) {
                BlockNodeEntity e = entry.member.get(pos);
                e.changeAccess(Optional.of(this));
                this.member.putIfAbsent(pos, e);
                entry.member.remove(pos);
                this.common_energy.get().receiveEnergy(entry.common_energy.get()
                        .extractEnergy(entry.common_energy.get().getEnergyStored(), false), false);
            }

        }

    }

    public void inviteMember(BlockPos pos, BlockNodeEntity e){

        if(e.access().isPresent() && e.access().get() != this){
            submergeNetwork(pos);
        } else {
            if(e.access().isEmpty()){
                e.changeAccess(Optional.of(this));

            }
        }
    }

    public EnergyNetwork(BlockPos pos, BlockNodeEntity member) {

        this.id = EnergyNetwork.definedNetworksAmounts();
        this.member.put(pos, member);
    }


    public int getId() {
        return id;
    }


    public static @Nullable EnergyNetwork getNetwork(int id){
        if(!EnergyNetwork.IsIdValid(id)){
            return null;
        }
        return Networks.get(id);
    }

    public static boolean IsIdValid(int id){
        return (id <= (Networks.size()-1)) && (id >= 0);
    }

    public static int definedNetworksAmounts(){
        return Networks.size();
    }

    public static int findNetworkIdByPos(BlockPos pos){
        for(EnergyNetwork n : Networks){
            if(n.member.get(pos) != null){
                return n.id;
            }
        }

        return -1;
    }


}
