package com.flying__8lack.util;


import net.minecraft.core.BlockPos;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;


public record DataMessage<E> (List<E> data, BlockPos source) {

    public Stream<E> filter(Predicate<E> predicate){
        return this.data.stream().filter(predicate);
    }


}
