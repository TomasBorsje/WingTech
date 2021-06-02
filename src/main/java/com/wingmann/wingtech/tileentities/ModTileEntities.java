package com.wingmann.wingtech.tileentities;

import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.registries.ObjectHolder;

public class ModTileEntities {

    @ObjectHolder("wingtech:testblock")
    public static TileEntityType<TestBlockTile> TESTBLOCK_TILE;

    @ObjectHolder("wingtech:atmospheric_condenser")
    public static TileEntityType<TestBlockTile> ATMOSPHERIC_CONDENSER_TILE;
}
