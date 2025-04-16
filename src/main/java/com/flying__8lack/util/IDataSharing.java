package com.flying__8lack.util;


import java.util.Optional;

public interface IDataSharing {

    Optional<DataMessage<?>> acquireData();

    boolean hasDataChanged();



}
