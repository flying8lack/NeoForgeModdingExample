package com.flying__8lack.client;

import com.flying__8lack.blocks.fluids.ModFluids;
import com.flying__8lack.entity.ModEntity;
import com.flying__8lack.entity.renders.GrassWalkerMonsterRender;
import com.flying__8lack.gui.ModMenu;
import com.flying__8lack.gui.screen.ScreenCoalPower;
import com.flying__8lack.gui.screen.ScreenCoalProcessor;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.Camera;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.client.settings.KeyConflictContext;
import net.neoforged.neoforge.client.settings.KeyModifier;
import net.neoforged.neoforge.common.util.Lazy;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

import static com.flying__8lack.advancedmovementmod.MODID;

@EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModClientMod {


    public static final Lazy<KeyMapping> FLYING_MAPPING_BTN = Lazy.of(() -> new KeyMapping(
            "key."+MODID+".flying_mapping_btn", // Will be localized using this translation key
            KeyConflictContext.IN_GAME,
            KeyModifier.SHIFT,
            InputConstants.Type.KEYSYM, // Default mapping is on the keyboard
            GLFW.GLFW_KEY_SPACE, // Default key is P
            "key.categories.misc" // Mapping will be in the misc category
    ));

    public static final Lazy<KeyMapping> JUMPKIT_ENABLED_BTN = Lazy.of(() -> new KeyMapping(
            "key."+MODID+".jumpkit_enabled_btn", // Will be localized using this translation key
            KeyConflictContext.IN_GAME,
            KeyModifier.NONE,
            InputConstants.Type.KEYSYM, // Default mapping is on the keyboard
            GLFW.GLFW_KEY_U, // Default key is P
            "key.categories.misc" // Mapping will be in the misc category
    ));

    @SubscribeEvent
    public static void ClientExtReg(RegisterClientExtensionsEvent event){
        IClientFluidTypeExtensions liquid_pain = new IClientFluidTypeExtensions() {
            @Override
            public ResourceLocation getStillTexture() {
                return ResourceLocation.fromNamespaceAndPath(MODID, "block/liquid_pain_fluid");
            }

            @Override
            public ResourceLocation getFlowingTexture() {
                return ResourceLocation.fromNamespaceAndPath(MODID, "block/liquid_pain_flowing");
            }

            @Override
            public int getTintColor() {
                return 0xFF4500; // Orange-red color
            }



            @Override
            public Vector3f modifyFogColor(Camera camera, float partialTick, ClientLevel level, int renderDistance, float darkenWorldAmount, Vector3f fluidFogColor) {
                if(level.getBlockState(camera.getBlockPosition()).is(ModFluids.LIQUID_PAIN_BLOCK)) {
                    return new Vector3f(128f, 0f, 0f);
                }
                return fluidFogColor;
            }


        };
        event.registerFluidType(liquid_pain, ModFluids.CUSTOM_TYPE);

    }



    @SubscribeEvent
    public static void ClientSetUp(FMLClientSetupEvent event){
        EntityRenderers.register(ModEntity.GRASS_WALKER_MONSTER_ENTITY.get(), GrassWalkerMonsterRender::new);


    }

    @SubscribeEvent
    public static void KeyMappingRegisteration(RegisterKeyMappingsEvent event){
        event.register( FLYING_MAPPING_BTN.get());
        event.register(JUMPKIT_ENABLED_BTN.get());
    }
    @SubscribeEvent
    public static void screenReg(RegisterMenuScreensEvent event){
        event.register(ModMenu.COAL_PROCESSOR.get(), ScreenCoalProcessor::new);
        event.register(ModMenu.COAL_POWER.get(), ScreenCoalPower::new);

    }



}




