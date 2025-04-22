package com.flying__8lack.network;

import com.flying__8lack.blocks.entity.BlockPipeEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.util.Lazy;

import java.util.HashMap;
import java.util.Set;

/**
 * A singleton class to manage all networks in the minecraft world.
 * @author flying_8lack
 */
public class NetworkManager {

    private static final Lazy<NetworkManager> instance = Lazy.of(NetworkManager::new);
    private final HashMap<BlockPos, PipeNetwork> networks = new HashMap<>();

    public static NetworkManager getInstance() {

        return instance.get();
    }

    public HashMap<BlockPos, PipeNetwork> networks(){
        return networks;
    }

    public void splitNetwork(BlockPos pos, Level level){
        PipeNetwork og = networks.get(pos);
        if(og != null){
            for(Set<BlockPos> group : og.getAllDisjointNetworks()){
                createNewNetwork(group, level);
            }
        }

        removeNetwork(pos);
    }

    public void removeNetwork(BlockPos pos){
        networks.remove(pos);
    }


    public PipeNetwork createNewNetwork(Set<BlockPos> segment, Level level){


        PipeNetwork newNetwork = new PipeNetwork();
        for(BlockPos pos : segment) {
            if(level.getBlockEntity(pos) instanceof BlockPipeEntity be) {
                networks.put(pos, newNetwork);
                newNetwork.addToNetwork(pos, be);
            }
        }
        return newNetwork;
    }

    private NetworkManager(){

    }




}
