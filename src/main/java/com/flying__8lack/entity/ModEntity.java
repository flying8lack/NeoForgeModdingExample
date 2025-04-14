package com.flying__8lack.entity;

import com.flying__8lack.entity.custom.GrassWalkerMonsterEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import static com.flying__8lack.advancedmovementmod.MODID;

public class ModEntity {

    public static final DeferredRegister<EntityType<?>> ENTITIY = DeferredRegister.create(Registries.ENTITY_TYPE,
            MODID);

    public static final Supplier<EntityType<GrassWalkerMonsterEntity>> GRASS_WALKER_MONSTER_ENTITY = ENTITIY.register(
            "grass_walker_monster_entity",
            () -> EntityType.Builder.of(GrassWalkerMonsterEntity::new, MobCategory.MONSTER)
                    .sized(0.9f, 1.2f)
                    .build("grass_walker_monster_entity"));

    public static void register(IEventBus bus){
        ENTITIY.register(bus);
    }



}
