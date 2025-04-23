package com.flying__8lack;


import com.flying__8lack.blocks.entity.ModBlockEntity;
import com.flying__8lack.cap.ModCap;
import com.flying__8lack.client.hud.BasicHUD;
import com.flying__8lack.entity.ModEntity;
import com.flying__8lack.entity.custom.GrassWalkerMonsterEntity;
import com.flying__8lack.entity.models.GrassWalkerMonsterModel;
import com.flying__8lack.network.PlayerData;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.DirectionalPayloadHandler;
import net.neoforged.neoforge.network.handling.MainThreadPayloadHandler;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

import static com.flying__8lack.advancedmovementmod.MODID;

@EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD)
public class BusEvents {

    @SubscribeEvent
    public static void RegisterPayload(final RegisterPayloadHandlersEvent event){
        final PayloadRegistrar register = event.registrar("1");
        register.commonToClient(PlayerData.TYPE,
                PlayerData.STREAM_CODEC,
                new DirectionalPayloadHandler<>(
                        BasicHUD::handleClientData,
                        BasicHUD::handleServerData
                ));
    }

    @SubscribeEvent
    public static void RegisterCapabilities(RegisterCapabilitiesEvent event){
        //items
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK,
                ModBlockEntity.COAL_PROCESSOR_ENTITY.get(),
                (b, pos) -> b.getCapabilityItem(pos, null));

        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK,
                ModBlockEntity.COAL_POWER_ENTITY.get(),
                (b, pos) -> b.getCapabilityItem(pos, null));

        //energy
        event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK,
                ModBlockEntity.COAL_POWER_ENTITY.get(),
                (b, pos) -> b.getCapabilityEnergy(pos, null));

        event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK,
                ModBlockEntity.NODE_ENTITY.get(),
                (b, pos) -> b.getCapabilityEnergy(pos, null));

        //custom
        event.registerBlockEntity(ModCap.DATA_SHARING_CAP,
                ModBlockEntity.RADAR_ENTITY.get(),
                (b, p) -> b.getCapability());
    }

    @SubscribeEvent
    public static void RegisterSpawnPlacement(RegisterSpawnPlacementsEvent event){
        event.register(ModEntity.GRASS_WALKER_MONSTER_ENTITY.get(), SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING,
                (entityType, serverLevelAccessor, mobSpawnType, blockPos, randomSource) ->
                serverLevelAccessor.getLightEmission(blockPos) <= 12,
                RegisterSpawnPlacementsEvent.Operation.OR);

    }

    @SubscribeEvent
    public static void RegisterRenderer(EntityRenderersEvent.RegisterLayerDefinitions event){
        event.registerLayerDefinition(GrassWalkerMonsterModel.LAYER_LOCATION, GrassWalkerMonsterModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void RegisterAttributes(EntityAttributeCreationEvent event){
        event.put(ModEntity.GRASS_WALKER_MONSTER_ENTITY.get(), GrassWalkerMonsterEntity.createAttributes().build());
    }
}
