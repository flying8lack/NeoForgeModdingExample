package com.flying__8lack.blocks;

import com.flying__8lack.blocks.entity.BlockCoalPowerEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BlockCoalPower extends Block implements EntityBlock {

    public BlockCoalPower(Properties properties) {
        super(properties);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return level.isClientSide() ? null : (level1, blockPos, blockState, t) -> {
            if(t instanceof BlockCoalPowerEntity be){
                be.tick(level1, blockPos, blockState, be);
            }
        };
    }

    @Override
    protected List<ItemStack> getDrops(BlockState state, LootParams.Builder params) {

        return super.getDrops(state, params);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if(!level.isClientSide()){
            if(level.getBlockEntity(pos) instanceof BlockCoalPowerEntity be){
                player.openMenu(new SimpleMenuProvider(be, Component.literal("Coal Power Generator")), pos);
            }
        }

        return super.useWithoutItem(state, level, pos, player, hitResult);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new BlockCoalPowerEntity(blockPos, blockState);
    }
}
