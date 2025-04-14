package com.flying__8lack.blocks.entity;

import com.flying__8lack.ModItem;
import com.flying__8lack.gui.menus.MenuCoalProcessor;
import com.flying__8lack.util.ICapProvider;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class BlockCoalProcessorEntity extends BlockEntity implements MenuProvider, ICapProvider {

    private static final Map<Pair<ItemLike, ItemLike>, ItemLike> RECORD = Map.of(
            Pair.of(Items.COAL, Items.COAL), ModItem.PURE_COAL,
            Pair.of(Items.ENDER_PEARL, Items.ENDER_PEARL), Items.SUGAR
    );
    private int loading_delay = 16;
    private boolean is_cooking = false;
    private ItemStackHandler output = new ItemStackHandler(1){


        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };
    private int time_left = 100;
    private final ItemStackHandler inv = new ItemStackHandler(4){


        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };

    public ItemStackHandler get_inv(){
        return this.inv;
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        tag.putBoolean("state", this.is_cooking);
        tag.putInt("time_left", this.time_left);
        tag.put("output", this.output.serializeNBT(registries));
        tag.put("inventory", this.inv.serializeNBT(registries));
        super.saveAdditional(tag, registries);

    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        this.is_cooking = tag.getBoolean("state");
        this.time_left = tag.getInt("time_left");
        if(tag.contains("inventory")) {
            this.inv.deserializeNBT(registries, (CompoundTag) tag.get("inventory"));
        }
        if(tag.contains("output")){
            this.output.deserializeNBT(registries, (CompoundTag) tag.get("output"));
        }
        super.loadAdditional(tag, registries);
    }

    public BlockCoalProcessorEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntity.COAL_PROCESSOR_ENTITY.get(), pos, blockState);
        if(this.level != null) {
            this.level.invalidateCapabilities(pos);
        }
    }

    private void load_items(Level level, BlockPos pos){
        IItemHandler m = level.getCapability(Capabilities.ItemHandler.BLOCK, pos.above(), null);

        if(m != null){
            for(int i = 0; i < m.getSlots(); i++){
                ItemStack slot = this.inv.getStackInSlot(0);
                ItemStack slot2 = this.inv.getStackInSlot(1);
                ItemStack k = m.extractItem(i, 64, false);
                if(k.isEmpty()){
                    continue;
                }

                if(slot.isEmpty()){
                    this.inv.insertItem(0, k, false);
                    continue;
                }


                if(slot2.isEmpty()){
                    this.inv.insertItem(1, k, false);
                    continue;
                }

                m.insertItem(i, k, false);
                break;

            }

        }
    }

    public void tick(Level level, BlockPos pos, BlockState state, BlockCoalProcessorEntity blockentity){
        if(!level.isClientSide()){



            if(this.is_cooking){

                if(this.time_left > 0){
                    this.time_left -= 1;
                } else {

                    this.inv.insertItem(2,
                            new ItemStack(this.output.extractItem(0, 1, false).getItem()),
                            false);
                    this.is_cooking = false;
                }
            } else {
                var coal = this.inv.getStackInSlot(0);
                var coal2 = this.inv.getStackInSlot(1);

                if(coal.isEmpty() || coal2.isEmpty()){
                    if(loading_delay == 0) {
                        this.load_items(level, pos);
                        loading_delay = 16;
                    } else {
                        loading_delay--;
                    }

                } else {
                    RECORD.forEach((pair, value) -> {

                        if ((coal.is(pair.getFirst().asItem()) && coal2.is(pair.getSecond().asItem()))
                                || (coal2.is(pair.getFirst().asItem()) && coal.is(pair.getSecond().asItem()))) {
                            this.inv.extractItem(0, 1, false);
                            this.inv.extractItem(1, 1, false);
                            this.is_cooking = true;
                            this.time_left = 100;
                            this.output.insertItem(0, new ItemStack(value.asItem()), false);
                        }

                    });
                }
            }
        }
    }



    @Override
    public Component getDisplayName() {
        return Component.literal("Machine");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new MenuCoalProcessor(i, inventory, ContainerLevelAccess.NULL, this);
    }

    @Override
    public @Nullable IItemHandler getCapabilityItem(@Nullable Direction pos, Object o) {
        return this.inv;
    }

    @Override
    public @Nullable IEnergyStorage getCapabilityEnergy(@Nullable Direction pos, Object o) {
        return null;
    }
}
