package com.flying__8lack.blocks;

import com.flying__8lack.util.PortalHandle;
import com.flying__8lack.world.dimensions.ModDimension;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class BlockBroken extends Block{

    public static final PortalHandle portal = new PortalHandle(ModDimension.INSTABLE_WORLD_LEVEL, 40);
    public BlockBroken(Properties properties) {
        super(properties);
    }

    @Override
    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
        portal.teleportAsAPortal(entity);

    }

    private BlockPos findSpawnLocation(ServerLevel level, BlockPos pos){
        for(int y = 40; y < 128; y++){
            if(level.getBlockState(pos.atY(y)).isAir()){
                return new BlockPos(pos.getX(),
                        y+2,
                        pos.getZ());
            }
        }

        return new BlockPos(pos.getX(),
                130,
                pos.getZ());

    }


}
