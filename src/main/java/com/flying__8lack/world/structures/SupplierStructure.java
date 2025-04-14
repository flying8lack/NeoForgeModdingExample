package com.flying__8lack.world.structures;


import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.WorldGenerationContext;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pools.DimensionPadding;
import net.minecraft.world.level.levelgen.structure.pools.JigsawPlacement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.pools.alias.PoolAliasLookup;
import net.minecraft.world.level.levelgen.structure.structures.JigsawStructure;
import net.minecraft.world.level.levelgen.structure.templatesystem.LiquidSettings;

import java.lang.reflect.Type;
import java.util.Optional;

import static com.flying__8lack.advancedmovementmod.getLog;

public class SupplierStructure extends Structure {

    public static final MapCodec<SupplierStructure> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(SupplierStructure.settingsCodec(instance),
                            StructureTemplatePool.CODEC.fieldOf("start_pool").forGetter(structure -> structure.startPool),
                            ResourceLocation.CODEC.optionalFieldOf("start_jigsaw_name").forGetter(structure -> structure.startJigsawName),
                            Codec.intRange(0, 30).fieldOf("size").forGetter(structure -> structure.size),
                            HeightProvider.CODEC.fieldOf("start_height").forGetter(structure -> structure.startHeight),
                            Heightmap.Types.CODEC.optionalFieldOf("project_start_to_heightmap").forGetter(structure -> structure.projectStartToHeightmap),
                            Codec.intRange(1, 128).fieldOf("max_distance_from_center").forGetter(structure -> structure.maxDistanceFromCenter),
                            DimensionPadding.CODEC.optionalFieldOf("dimension_padding", JigsawStructure.DEFAULT_DIMENSION_PADDING).forGetter(structure -> structure.dimensionPadding),
                            LiquidSettings.CODEC.optionalFieldOf("liquid_settings", JigsawStructure.DEFAULT_LIQUID_SETTINGS).forGetter(structure -> structure.liquidSettings))
                    .apply(instance, SupplierStructure::new));


    private final Holder<StructureTemplatePool> startPool;
    private final Optional<ResourceLocation> startJigsawName;
    private final int size;
    private final HeightProvider startHeight;
    private final Optional<Heightmap.Types> projectStartToHeightmap;
    private final int maxDistanceFromCenter;
    private final DimensionPadding dimensionPadding;
    private final LiquidSettings liquidSettings;
    protected SupplierStructure(StructureSettings settings,
                                Holder<StructureTemplatePool> startPool,
                                Optional<ResourceLocation> startJigsawName,
                                int size,
                                HeightProvider startHeight,
                                Optional<Heightmap.Types> projectStartToHeightmap,
                                int maxDistanceFromCenter,
                                DimensionPadding dimensionPadding,
                                LiquidSettings liquidSettings) {

        super(settings);
        this.startPool = startPool;
        this.startJigsawName = startJigsawName;
        this.size = size;
        this.startHeight = startHeight;
        this.projectStartToHeightmap = projectStartToHeightmap;
        this.maxDistanceFromCenter = maxDistanceFromCenter;
        this.dimensionPadding = dimensionPadding;
        this.liquidSettings = liquidSettings;
    }



    @Override
    protected Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
        ChunkPos chunkPos = context.chunkPos();

        var height = context.chunkGenerator().getFirstOccupiedHeight(context.chunkPos().getMinBlockX(),
                context.chunkPos().getMinBlockZ(), Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                context.heightAccessor(), context.randomState());




        if(height > 120){
            return Optional.empty();

        }


        var sample_h = startHeight.sample(context.random(), new WorldGenerationContext(context.chunkGenerator(), context.heightAccessor()));
        // Turns the chunk coordinates into actual coordinates we can use. (Gets corner of that chunk)
        BlockPos blockPos = new BlockPos(chunkPos.getMinBlockX(), sample_h+1, chunkPos.getMinBlockZ());



        return JigsawPlacement.addPieces(
                context, // Used for JigsawPlacement to get all the proper behaviors done.
                this.startPool, // The starting pool to use to create the structure layout from
                this.startJigsawName, // Can be used to only spawn from one Jigsaw block. But we don't need to worry about this.
                this.size, // How deep a branch of pieces can go away from center piece. (5 means branches cannot be longer than 5 pieces from center piece)
                blockPos, // Where to spawn the structure.
                false, // "useExpansionHack" This is for legacy villages to generate properly. You should keep this false always.
                this.projectStartToHeightmap, // Adds the terrain height's y value to the passed in blockpos's y value. (This uses WORLD_SURFACE_WG heightmap which stops at top water too)
                // Here, blockpos's y value is 60 which means the structure spawn 60 blocks above terrain height.
                // Set this to false for structure to be place only at the passed in blockpos's Y value instead.
                // DefinitelstructurePiecesGeneratory keep this false when placing structures in the nether as otherwise, heightmap placing will put the structure on the Bedrock roof.
                this.maxDistanceFromCenter, // Maximum limit for how far pieces can spawn from center. You cannot set this bigger than 128 or else pieces gets cutoff.
                PoolAliasLookup.EMPTY, // Optional thing that allows swapping a template pool with another per structure json instance. We don't need this but see vanilla JigsawStructure class for how to wire it up if you want it.
                this.dimensionPadding, // Optional thing to prevent generating too close to the bottom or top of the dimension.
                this.liquidSettings);
    }

    @Override
    public StructureType<?> type() {
        return ModStructure.SUPPLIER_STRUCTURE.get();
    }
}
