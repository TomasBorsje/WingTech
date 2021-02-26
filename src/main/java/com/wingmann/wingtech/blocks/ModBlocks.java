package com.wingmann.wingtech.blocks;

import com.wingmann.wingtech.containers.TestBlockContainer;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.registries.ObjectHolder;

public class ModBlocks {

    @ObjectHolder("wingtech:testblock")
    public static TestBlock TESTBLOCK;

    @ObjectHolder("wingtech:palladium_ore")
    public static PalladiumOre PALLADIUM_ORE;

    @ObjectHolder("wingtech:machine_casing")
    public static MachineCasing MACHINE_CASING;

    @ObjectHolder("wingtech:testblock")
    public static ContainerType<TestBlockContainer> TESTBLOCK_CONTAINER;

    @ObjectHolder("wingtech:flower")
    public static Flower FLOWER;

}
