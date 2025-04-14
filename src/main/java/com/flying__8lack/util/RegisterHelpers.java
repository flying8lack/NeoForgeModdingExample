package com.flying__8lack.util;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.schedule.Activity;

import java.util.function.Supplier;

public class RegisterHelpers {

    public static Activity registerActivity(String key) {
        return Registry.register(BuiltInRegistries.ACTIVITY, key, new Activity(key));
    }

    public static <U extends Sensor<?>> SensorType<U> registerSensor(String key, Supplier<U> sensorSupplier) {
        return Registry.register(BuiltInRegistries.SENSOR_TYPE,
                ResourceLocation.withDefaultNamespace(key), new SensorType<>(sensorSupplier));
    }

}
