package com.wingmann.wingtech.blocks;

import com.wingmann.wingtech.WingTech;
import com.wingmann.wingtech.containers.AtmosphericCondenserContainer;
import com.wingmann.wingtech.containers.TestBlockContainer;
import net.minecraft.block.Block;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ObjectHolder;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, WingTech.MODID);

    // Blocks
    public static final RegistryObject<Block> FLOWER = BLOCKS.register("flower", Flower::new);
    public static final RegistryObject<Block> TESTBLOCK = BLOCKS.register("testblock", TestBlock::new);
    public static final RegistryObject<Block> ATMOSPHERIC_CONDENSER = BLOCKS.register("atmospheric_condenser", AtmosphericCondenser::new);
    public static final RegistryObject<Block> TUNGSTEN_ORE = BLOCKS.register("tungsten_ore", TungstenOre::new);
    public static final RegistryObject<Block> MACHINE_CASING = BLOCKS.register("machine_casing", MachineCasing::new);
    public static final RegistryObject<Block> TUNGSTEN_BLOCK = BLOCKS.register("tungsten_block", TungstenBlock::new);

    @ObjectHolder("wingtech:testblock")
    public static ContainerType<TestBlockContainer> TESTBLOCK_CONTAINER;

    @ObjectHolder("wingtech:atmospheric_condenser")
    public static ContainerType<AtmosphericCondenserContainer> ATMOSPHERIC_CONDENSER_CONTAINER;



}
