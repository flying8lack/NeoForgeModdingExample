package com.flying__8lack.gui;

import com.flying__8lack.gui.menus.MenuCoalPower;
import com.flying__8lack.gui.menus.MenuCoalProcessor;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import static com.flying__8lack.advancedmovementmod.MODID;

public class ModMenu {

    public static final DeferredRegister<MenuType<?>> MENUTYPE = DeferredRegister.create(Registries.MENU,
            MODID);

    public static final Supplier<MenuType<MenuCoalProcessor>> COAL_PROCESSOR =
            MENUTYPE.register("coal_processor_menu", () ->IMenuTypeExtension.create(MenuCoalProcessor::new));


    public static final Supplier<MenuType<MenuCoalPower>> COAL_POWER =
            MENUTYPE.register("coal_power_menu", () -> IMenuTypeExtension.create(MenuCoalPower::new));

    public static void Register(IEventBus bus){
        MENUTYPE.register(bus);
    }


}
