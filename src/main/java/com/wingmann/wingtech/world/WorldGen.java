package com.wingmann.wingtech.world;

import com.wingmann.wingtech.blocks.ModBlocks;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.WeightedBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraftforge.event.world.BiomeLoadingEvent;

public class WorldGen {

    public static final BlockClusterFeatureConfig FLOWER_CONFIG = (new BlockClusterFeatureConfig.Builder((new WeightedBlockStateProvider()).addWeightedBlockstate(ModBlocks.FLOWER.getDefaultState(), 2), SimpleBlockPlacer.PLACER)).tries(64).build();

    public static void generateChunk(final BiomeLoadingEvent event)
    {
        // Generate Palladium Ore between y=48 and y=0
        ConfiguredFeature<?, ?> ORE_PALLADIUM = Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, ModBlocks.PALLADIUM_ORE.getDefaultState(), 7))
                .range(48).square().func_242731_b(6); // Iron ore is 20

        ConfiguredFeature<?, ?> FLOWER_GEN = Feature.FLOWER.withConfiguration(FLOWER_CONFIG).withPlacement(Features.Placements.VEGETATION_PLACEMENT).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).func_242731_b(2);

        // Generate ores
        event.getGeneration().withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, ORE_PALLADIUM);

        // Generate vegetation
        event.getGeneration().withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, FLOWER_GEN);
    }
}
