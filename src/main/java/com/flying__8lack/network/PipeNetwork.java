package com.flying__8lack.network;

import com.flying__8lack.blocks.BlockPipe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.util.Lazy;
import net.neoforged.neoforge.energy.EnergyStorage;
import net.neoforged.neoforge.energy.IEnergyStorage;

import java.util.*;

public class PipeNetwork {


    public PipeNetwork() {
    }

    public interface InterestPoint {


        Set<BlockPos> getConnections();
        void setConnections(Set<BlockPos> conn);
        void removeConnection(BlockPos pos);
        Level getLevel();

    }

    public TreeMap<BlockPos, InterestPoint> getPoints() {
        return points;
    }

    private final TreeMap<BlockPos, InterestPoint> points = new TreeMap<>();
    private final Lazy<IEnergyStorage> EnergyAccess = Lazy.of(() -> new EnergyStorage(5000));

    private final HashMap<BlockPos, BlockPos> parents = new HashMap<>();
    private final HashMap<BlockPos, Integer> ranks = new HashMap<>();

    private void makeSet(BlockPos pos){
        if(!parents.containsKey(pos)){
            parents.put(pos, pos);
            ranks.put(pos, 0);
        }
    }

    private BlockPos find(BlockPos pos){
        makeSet(pos);
        while(parents.get(pos) != pos){
            parents.put(parents.get(pos), parents.get(parents.get(pos)));
            pos = parents.get(pos);
        }

        return pos;

    }

    private void union(BlockPos x, BlockPos y){
        BlockPos X = find(x);
        BlockPos Y = find(y);

        if(X == Y){ //both are at the same set
            return;
        }

        BlockPos lower;
        BlockPos higher;
        int xrank = ranks.get(X);
        int yrank = ranks.get(Y);

        if(xrank < yrank){
            lower = X;
            higher = Y;
        } else {
            lower = Y;
            higher = X;
        }

        parents.put(lower, higher);
        if(xrank == yrank){
            ranks.put(higher, ranks.get(higher)+1);
        }

    }

    public Collection<Set<BlockPos>> getAllDisjointNetworks(){
        Map<BlockPos, Set<BlockPos>> networks = new HashMap<>();

        for(BlockPos pos : points.keySet()){
            BlockPos root = find(pos);
            networks.computeIfAbsent(root, b -> new HashSet<>()).add(pos);
        }

        return networks.values();

    }

    public void updateConnections(){
        parents.clear();
        ranks.clear();

        for(BlockPos pos : points.keySet()){
            find(pos);
            for(BlockPos npos : points.get(pos).getConnections()){
                union(pos, npos);
            }
        }
    }

    public boolean isConnected(BlockPos a, BlockPos b){
        return find(a).equals(find(b));
    }


    public void scanUpdateConnections(BlockPos pos){

        Set<BlockPos> group = points.get(pos).getConnections();
        group.clear();
        for(Direction dir : Direction.values()){
            BlockPos side = pos.relative(dir);
            if(isValidConnection(side, points.get(pos).getLevel())){
                group.add(side);
            }
        }

    }


    private boolean isValidConnection(BlockPos destination, Level level){

        Block k = level.getBlockState(destination).getBlock();
        return k instanceof BlockPipe;
    }

    public IEnergyStorage getEnergy(){
        return this.EnergyAccess.get();
    }


    public void removeFromNetwork(BlockPos pos, Level level){
        this.points.remove(pos);
        if(this.getAllDisjointNetworks().size() > 1){
            NetworkManager.getInstance().splitNetwork(pos, level);
        }
        this.updateConnections();
    }



    public void addToNetwork(BlockPos pos, InterestPoint ip){
        this.points.put(pos, ip);
        scanUpdateConnections(pos);
    }

}
