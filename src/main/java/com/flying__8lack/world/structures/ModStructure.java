package com.flying__8lack.world.structures;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.flying__8lack.advancedmovementmod.MODID;

public class ModStructure {

    public static final DeferredRegister<StructureType<?>> STRUCTS = DeferredRegister.create(
            Registries.STRUCTURE_TYPE,MODID);

    public static final DeferredHolder<StructureType<?>,StructureType<SupplierStructure>>
            SUPPLIER_STRUCTURE = STRUCTS.register("supplier_structure",
            () -> () -> SupplierStructure.CODEC);

    public static void Register(IEventBus bus){
        STRUCTS.register(bus);
    }
}
