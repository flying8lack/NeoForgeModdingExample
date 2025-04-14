package com.flying__8lack.cap;

import com.flying__8lack.util.IDataSharing;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.capabilities.BlockCapability;

import static com.flying__8lack.advancedmovementmod.MODID;

public class ModCap {

    public static final BlockCapability<IDataSharing, Void> DATA_SHARING_CAP =
            BlockCapability.createVoid(
                    ResourceLocation.fromNamespaceAndPath(MODID, "data_sharing_cap"), IDataSharing.class);
}
