package com.flying__8lack.blocks.entity;

import com.flying__8lack.gui.menus.MenuCoalPower;
import com.flying__8lack.util.ICapProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.EnergyStorage;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

import static com.flying__8lack.advancedmovementmod.getLog;

public class BlockCoalPowerEntity extends BlockEntity implements MenuProvider, ICapProvider {

    private final EnergyStorage storage = new EnergyStorage(1_000_000);

    private final ItemStackHandler Input = new ItemStackHandler(1){
        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            setChanged();
        }
    };
    private static final int EnergyPerTick = Math.floorDiv(1200, 250);
    private int processing_time_left = 0;
    private boolean active = false;
    private int energy = this.storage.getEnergyStored();
    private Direction push_step = Direction.SOUTH;
    private int push_step_delay = 5;

    public ContainerData sync_data = new ContainerData() {
        @Override
        public int get(int i) {
            return switch (i) {
                case 0 -> active ? 1 : 0;
                case 1 -> processing_time_left;
                case 2 -> energy;
                default -> 0;
            };

        }

        @Override
        public void set(int i, int value) {
            switch (i) {
                case 0:
                    active = (value == 1);
                    break;
                case 1:
                    processing_time_left = value;
                    break;
                case 2:
                    energy = value;
                    break;
            }
        }

        @Override
        public int getCount() {
            return 3;
        }
    };



    public BlockCoalPowerEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntity.COAL_POWER_ENTITY.get(), pos, blockState);
    }

    public ItemStackHandler get_inv(){
        return this.Input;
    }

    private void pushPower(Level level, BlockPos pos){
        IEnergyStorage m = level.getCapability(
                Capabilities.EnergyStorage.BLOCK,
                pos.relative(push_step),
                push_step
        );
        if(m != null && m.canReceive()){
            m.receiveEnergy(this.storage.extractEnergy(1200, false), false);
            getLog().debug("Sent power!");
        }
        push_step = switch (push_step){
            case DOWN -> Direction.UP;
            case UP -> Direction.SOUTH;
            case NORTH -> Direction.WEST;
            case SOUTH -> Direction.EAST;
            case WEST -> Direction.DOWN;
            case EAST -> Direction.NORTH;
        };
    }



    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("inv", this.Input.serializeNBT(registries));
        tag.put("energy", this.storage.serializeNBT(registries));
        tag.putInt("processing_time", this.processing_time_left);
        tag.putBoolean("active", this.active);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        this.processing_time_left = tag.getInt("processing_time");
        this.active = tag.getBoolean("active");
        if(tag.contains("inv")) {
            this.Input.deserializeNBT(registries, (CompoundTag) Objects.requireNonNull(tag.get("inv")));
        }
        if(tag.contains("energy")) {
            this.storage.deserializeNBT(registries, Objects.requireNonNull(tag.get("energy")));
        }
    }



    @Override
    public Component getDisplayName() {
        return Component.literal("Coal Power Generator");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new MenuCoalPower(i, inventory, ContainerLevelAccess.NULL, this);
    }

    public <T extends BlockCoalPowerEntity> void tick(Level level, BlockPos pos, BlockState blockState, T t) {
        if(push_step_delay > 0){
            push_step_delay--;
        } else {
            this.pushPower(level, pos);
        }
        if (processing_time_left > 0) {

            this.storage.receiveEnergy(EnergyPerTick, false);
            energy = this.storage.getEnergyStored();
            if(level instanceof ServerLevel s && processing_time_left % 16 == 0){
                s.sendParticles(ParticleTypes.SMOKE, pos.getX()+0.5, pos.getY()+1.2, pos.getZ()+0.5, 16, 0,
                        0.5, 0, 0.04);
            }

            processing_time_left--;
            setChanged();
            return;
        } else {
            if(active) {
                active = false;
                setChanged();
            }
        }
        
        if (this.Input.getStackInSlot(0).is(ItemTags.COALS)) {
            this.Input.extractItem(0, 1, false);
            processing_time_left = 250;
            active = true;
            setChanged();
        }
        if(energy != this.storage.getEnergyStored()) {
            energy = this.storage.getEnergyStored();
            setChanged();
        }



    }


    @Override
    public @Nullable IItemHandler getCapabilityItem(@Nullable Direction pos, Object o) {
        return this.Input;
    }

    public @Nullable IEnergyStorage getCapabilityEnergy(@Nullable Direction pos, Object o) {
        return this.storage;
    }
}
