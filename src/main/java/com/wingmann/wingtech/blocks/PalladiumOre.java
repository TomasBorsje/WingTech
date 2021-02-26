package com.wingmann.wingtech.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class PalladiumOre extends Block {
    public PalladiumOre() {
        super(Properties.create(Material.ROCK)
                .sound(SoundType.STONE)
                .hardnessAndResistance(2.5f)
        );
        setRegistryName("palladium_ore");
    }
}

