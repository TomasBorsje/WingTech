package com.wingmann.wingtech.blocks;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.potion.Effects;

public class Flower extends FlowerBlock {
    public Flower() {
        super(Effects.SATURATION, 6, AbstractBlock.Properties.of(Material.PLANT).noCollission().strength(0).sound(SoundType.GRASS));
    }
}
