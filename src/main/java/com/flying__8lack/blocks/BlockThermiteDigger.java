package com.flying__8lack.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;


public class BlockThermiteDigger extends Block {

    public static final EnumProperty<Direction> FACING = BlockStateProperties.FACING;
    public BlockThermiteDigger(Properties properties) {
        super(properties);
        registerDefaultState(stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
        );

    }

    @Override
    protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighborBlock, BlockPos neighborPos, boolean movedByPiston) {
        if(!level.isClientSide()){
            if(level.hasNeighborSignal(pos)){
                this.activate(level, pos, state);
            }
        }

    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        if(context.getPlayer() == null){
            return null;
        }
        return this.getStateDefinition().any().setValue(FACING, context.getPlayer().getDirection());
    }

    @Override
    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        if(!level.isClientSide()){
            if(!oldState.is(state.getBlock()) && level.hasNeighborSignal(pos)){
                this.activate(level, pos, state);
            }
        }
    }

    public void activate(Level level, BlockPos pos, BlockState state){
        Vec3 target = new Vec3(state.getValue(FACING).getStepX(), 0, state.getValue(FACING).getStepZ());
//        Vec3 target_center = target.scale(8).add(pos.getCenter());
        var ti = new Vec3i(Mth.floor(target.x()), Mth.floor(target.y()), Mth.floor(target.z())).below();
        BlockPos step_pos = null;
        for (int step = 0; step < 10; step++) {
//            var t = ti.multiply(step);
            step_pos = new BlockPos(ti.getX() * step + pos.getX(), ti.getY() + pos.getY(), ti.getZ() * step + pos.getZ());
            level.removeBlock(step_pos.above(), false);

        }

//        level.explode(null, target_center.x(), target_center.y(), target_center.z(),
//                6, true, Level.ExplosionInteraction.TNT);
        level.setBlock(step_pos, Blocks.MAGMA_BLOCK.defaultBlockState(), 2);
        level.removeBlock(pos, false);
        level.playSound(null, step_pos,
                SoundEvent.createVariableRangeEvent(SoundEvents.GENERIC_EXPLODE.value().getLocation()),
                SoundSource.BLOCKS,
                0.96f, 1.22f);


    }


}
