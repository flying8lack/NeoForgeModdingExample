package com.flying__8lack;

import com.flying__8lack.blocks.entity.ModBlockEntity;
import com.flying__8lack.blocks.fluids.ModFluids;
import com.flying__8lack.damage.ModDamageSource;
import com.flying__8lack.effects.ModEffect;
import com.flying__8lack.entity.ModEntity;
import com.flying__8lack.gui.ModMenu;
import com.flying__8lack.sound.ModSound;
import com.flying__8lack.world.density.ModDensityFunction;
import com.flying__8lack.world.structures.ModStructure;
import net.minecraft.data.worldgen.SurfaceRuleData;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.damagesource.DeathMessageType;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.SurfaceSystem;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(advancedmovementmod.MODID)
public class advancedmovementmod
{
    // Define mod id in a common place for everything to reference
    public static final String MODID = "advancedmovementmod";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public static Logger getLog(){
        return LOGGER;
    }
    // Create a Deferred Register to hold Blocks which will all be registered under the "advancedmovementmod" namespace

    // Create a Deferred Register to hold Items which will all be registered under the "advancedmovementmod" namespace

    // Create a Deferred Register to hold CreativeModeTabs which will all be registered under the "advancedmovementmod" namespace
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    // Creates a new Block with the id "advancedmovementmod:example_block", combining the namespace and path

    // Creates a new BlockItem with the id "advancedmovementmod:example_block", combining the namespace and path

    // Creates a new food item with the id "advancedmovementmod:example_id", nutrition 1 and saturation 2


    // Creates a creative tab with the id "advancedmovementmod:example_tab" for the example item, that is placed after the combat tab
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> EXAMPLE_TAB = CREATIVE_MODE_TABS.register("example_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.advancedmovementmod")) //The language key for the title of your CreativeModeTab
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> ModItem.TITANIUM_INGOT.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(ModItem.THERMITE_DIGGER_BLOCK_ITEM.get());
                output.accept(ModItem.TITANIUM_INGOT.get());
                output.accept(ModItem.THRUSTER.get());
                output.accept(ModItem.TITANIUM_ORE_BLOCK_ITEM.get());
                output.accept(ModItem.WARDEN_CALLER_BLOCK_ITEM.get());
                output.accept(ModItem.UNSTABLE_CREATIVE_ITEM.get());// Add the example item to the tab. For your own tabs, this method is preferred over the event
            }).build());

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public advancedmovementmod(IEventBus modEventBus, ModContainer modContainer)
    {

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);
        // Register the Deferred Register to the mod event bus so blocks get registered
        ModBlock.register(modEventBus);
        // Register the Deferred Register to the mod event bus so items get registered
        ModItem.register(modEventBus);
        ModBlockEntity.register(modEventBus);
        ModMenu.Register(modEventBus);
        ModStructure.Register(modEventBus);
        ModEffect.register(modEventBus);
        ModEntity.register(modEventBus);
        ModFluids.register(modEventBus);
        ModDensityFunction.register(modEventBus);
        ModSound.register(modEventBus);
        // Register the Deferred Register to the mod event bus so tabs get registered
        CREATIVE_MODE_TABS.register(modEventBus);

        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (advancedmovementmod) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        //NeoForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }




    private void commonSetup(final FMLCommonSetupEvent event)
    {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");

        if (Config.logDirtBlock)
            LOGGER.info("DIRT BLOCK >> {}", BuiltInRegistries.BLOCK.getKey(Blocks.DIRT));

        LOGGER.info(Config.magicNumberIntroduction + Config.magicNumber);

        Config.items.forEach((item) -> LOGGER.info("ITEM >> {}", item.toString()));

        event.enqueueWork(() -> {

        });



    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {
        if (event.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS) {
            event.accept(ModBlock.THERMITE_DIGGER_BLOCK);
        }

        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(ModItem.TITANIUM_INGOT);
        }
        if(event.getTabKey() == CreativeModeTabs.SPAWN_EGGS){
            event.accept(ModItem.GRASS_WALKER_EGG);
        }


    }




    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
//    @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
//    public static class ClientModEvents
//    {
//
//    }
}
