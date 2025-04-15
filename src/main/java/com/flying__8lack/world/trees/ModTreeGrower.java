package com.flying__8lack.world.trees;

import com.flying__8lack.world.config.ModConfigFeature;
import net.minecraft.world.level.block.grower.TreeGrower;

import java.util.Optional;

import static com.flying__8lack.advancedmovementmod.MODID;

public class ModTreeGrower {
    public static final TreeGrower PAIN_TREE = new TreeGrower(MODID + ":pain_tree",
            Optional.empty(), Optional.of(ModConfigFeature.PAIN_TREE), Optional.empty());
}
