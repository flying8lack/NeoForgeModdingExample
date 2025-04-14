package com.flying__8lack.blocks.entity;


import com.flying__8lack.ModBlock;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import static com.flying__8lack.advancedmovementmod.MODID;

public class ModBlockEntity {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY = DeferredRegister.create(
            Registries.BLOCK_ENTITY_TYPE, MODID);

    public static final Supplier<BlockEntityType<BlockWardenToyEntity>> WARDEN_TOY_ENTITY = BLOCK_ENTITY.register(
            "warden_toy_entity", () -> BlockEntityType.Builder.of(BlockWardenToyEntity::new,
                    ModBlock.WARDEN_CALLER_BLOCK.get()).build(null));

    public static final Supplier<BlockEntityType<BlockCoalProcessorEntity>> COAL_PROCESSOR_ENTITY = BLOCK_ENTITY.register(
            "coal_processor_entity", () -> BlockEntityType.Builder.of(BlockCoalProcessorEntity::new,
                    ModBlock.COAL_PROCESSOR_BLOCK.get()).build(null));

    public static final Supplier<BlockEntityType<BlockCoalPowerEntity>> COAL_POWER_ENTITY = BLOCK_ENTITY.register(
            "coal_power_entity", () -> BlockEntityType.Builder.of(BlockCoalPowerEntity::new,
                    ModBlock.COAL_POWER_BLOCK.get()).build(null));

    public static final Supplier<BlockEntityType<BlockRadarEntity>> RADAR_ENTITY = BLOCK_ENTITY.register(
            "radar_entity", () -> BlockEntityType.Builder.of(BlockRadarEntity::new,
                    ModBlock.RADAR_BLOCK.get()).build(null));

    public static final Supplier<BlockEntityType<BlockNodeEntity>> NODE_ENTITY = BLOCK_ENTITY.register(
            "node_entity", () -> BlockEntityType.Builder.of(BlockNodeEntity::new,
                    ModBlock.NODE_BLOCK.get()).build(null));

    public static void register(IEventBus bus){
        BLOCK_ENTITY.register(bus);
    }

}
