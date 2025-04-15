package com.flying__8lack.blocks.entity;

import com.flying__8lack.cap.ModCap;
import com.flying__8lack.util.IDataSharing;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.BlockCapabilityCache;

public class BlockWardenToyEntity extends BlockEntity {

    private BlockCapabilityCache<IDataSharing, Void> capCache;
    public int DELAY = 20;
    private static final int EnergyCost = 1250;

    public BlockWardenToyEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntity.WARDEN_TOY_ENTITY.get(), pos, blockState);
    }


    public void tick(Level level, BlockPos pos, BlockState state, BlockWardenToyEntity blockEntity){

            if (this.DELAY > 0) {
                this.DELAY -= 1;
                return;
            }


            IDataSharing m = level.getCapability(ModCap.DATA_SHARING_CAP, pos.west());

            if(m == null){
                this.DELAY = level.getRandom().nextInt(40, 160);
                return;
            }

            var data = m.acquireData();
            if(data.isEmpty()){
                this.DELAY = level.getRandom().nextInt(40, 160);
                return;
            }



            var wl = data.get().filter(e -> e instanceof Warden).map(c -> (Warden) c).toList();

//            var wl = level.getEntities(EntityTypeTest.forClass(Warden.class), AABB.ofSize(pos.getCenter(),
//                            28, 28, 28), p -> p.getTarget() != null);

            for(Warden w: wl){
                if(w.getTarget() == null){
                    continue;
                }
                w.getAngerManagement().clearAnger(w.getTarget());
                w.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 190, 10));
                w.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 190, 3));
                w.addEffect(new MobEffectInstance(MobEffects.GLOWING, 190, 1));
            }
            //level.gameEvent(GameEvent.BLOCK_DESTROY.getDelegate(), pos.getBottomCenter(), GameEvent.Context.of());


            this.DELAY = level.getRandom().nextInt(20, 60);

    }

}
