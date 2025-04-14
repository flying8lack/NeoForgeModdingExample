package com.flying__8lack.util;



import net.minecraft.core.BlockPos;

import java.util.Optional;

public interface IDataSharing {

    Optional<DataMessage<?>> acquireData();

    boolean hasDataChanged();



}
