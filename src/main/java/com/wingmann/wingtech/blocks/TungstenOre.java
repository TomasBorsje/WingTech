package com.wingmann.wingtech.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class TungstenOre extends Block {
    public TungstenOre() {
        super(Properties.of(Material.STONE)
                .sound(SoundType.STONE)
                .strength(2.5f)
        );
    }
}

