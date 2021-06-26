package com.wingmann.wingtech.world;

import com.wingmann.wingtech.blocks.ModBlocks;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraftforge.event.world.BiomeLoadingEvent;

public class WorldGen {

    public static final BlockClusterFeatureConfig FLOWER_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(ModBlocks.FLOWER.get().defaultBlockState()), SimpleBlockPlacer.INSTANCE)).tries(64).build();

    public static void generateChunk(final BiomeLoadingEvent event)
    {
        // Generate Palladium Ore between y=48 and y=0
        ConfiguredFeature<?, ?> ORE_TUNGSTEN = Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, ModBlocks.TUNGSTEN_ORE.get().defaultBlockState(), 9)).range(64).squared().count(10);

        ConfiguredFeature<?, ?> FLOWER_GEN = Feature.FLOWER.configured(FLOWER_CONFIG).decorated(Features.Placements.ADD_32).count(10);

        // Generate ores
        event.getGeneration().addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, ORE_TUNGSTEN);

        // Generate vegetation
        event.getGeneration().addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, FLOWER_GEN);
    }
}
