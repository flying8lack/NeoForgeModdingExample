package com.flying__8lack.util;

import net.minecraft.core.Direction;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.items.IItemHandler;
import org.jetbrains.annotations.Nullable;

public interface ICapProvider {
    @Nullable IItemHandler getCapabilityItem(@Nullable Direction pos, Object o);

    @Nullable IEnergyStorage getCapabilityEnergy(@Nullable Direction pos, Object o);
}
