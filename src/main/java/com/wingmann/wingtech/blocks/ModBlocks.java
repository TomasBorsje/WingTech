package com.wingmann.wingtech.blocks;

import com.wingmann.wingtech.containers.AtmosphericCondenserContainer;
import com.wingmann.wingtech.containers.TestBlockContainer;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.registries.ObjectHolder;

public class ModBlocks {

    @ObjectHolder("wingtech:testblock")
    public static TestBlock TESTBLOCK;

    @ObjectHolder("wingtech:atmospheric_condenser")
    public static AtmosphericCondenser ATMOSPHERIC_CONDENSER;

    @ObjectHolder("wingtech:tungsten_ore")
    public static TungstenOre TUNGSTEN_ORE;

    @ObjectHolder("wingtech:machine_casing")
    public static MachineCasing MACHINE_CASING;

    @ObjectHolder("wingtech:testblock")
    public static ContainerType<TestBlockContainer> TESTBLOCK_CONTAINER;

    @ObjectHolder("wingtech:atmospheric_condenser")
    public static ContainerType<AtmosphericCondenserContainer> ATMOSPHERIC_CONDENSER_CONTAINER;

    @ObjectHolder("wingtech:flower")
    public static Flower FLOWER;

}
