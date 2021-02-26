package com.wingmann.wingtech.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class MachineCasing extends Block {
    public MachineCasing() {
        super(Properties.create(Material.IRON)
                .sound(SoundType.METAL)
                .hardnessAndResistance(2.5f)
        );
        setRegistryName("machine_casing");
    }
}