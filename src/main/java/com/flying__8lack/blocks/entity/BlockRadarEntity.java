package com.flying__8lack.blocks.entity;

import com.flying__8lack.sound.ModSound;
import com.flying__8lack.util.DataMessage;
import com.flying__8lack.util.IDataSharing;
import com.flying__8lack.util.ValueCache;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class BlockRadarEntity extends BlockEntity implements IDataSharing {
    private int SoundDelay = 0;
    private final ValueCache<List<Entity>> data = ValueCache.of(List.of());
    private final AABB detection_box = new AABB(this.getBlockPos().getX()-16,
            this.getBlockPos().getY(),
            this.getBlockPos().getZ()-16,
            this.getBlockPos().getX()+16,
            this.getBlockPos().getY()+4,
            this.getBlockPos().getZ()+16);
    private int Delay = 60;

    public BlockRadarEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntity.RADAR_ENTITY.get(), pos, blockState);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("delay", this.Delay);

    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        this.Delay = tag.getInt("delay");
    }

    public void tick(Level level, BlockPos pos, BlockState state, BlockRadarEntity be){
        if(!level.isClientSide()){
            if(SoundDelay > 0){
                SoundDelay--;
            } else {
                level.playSound(null, pos, ModSound.HUM_60HZ.get(), SoundSource.BLOCKS);
                SoundDelay = 90;
            }
            if(this.Delay > 0){
                this.Delay--;
                return;
            }

            this.data.set(
                    level.getEntities(null, detection_box).stream()
                    .filter(m -> m instanceof LivingEntity)
                    .sorted((m, c) -> Double.compare(
                            m.distanceToSqr(this.getBlockPos().getCenter()),
                            c.distanceToSqr(this.getBlockPos().getCenter()))).toList()
            );



            this.Delay = 60;
        }
    }


    public @Nullable IDataSharing getCapability() {
        return this;
    }





    @Override
    public Optional<DataMessage<?>> acquireData() {
        if(this.data.get().isEmpty()){
            return Optional.empty();
        }

        return Optional.of(new DataMessage<>(this.data.get(), this.getBlockPos()));
    }

    @Override
    public boolean hasDataChanged() {
        return this.data.changed();
    }

}
