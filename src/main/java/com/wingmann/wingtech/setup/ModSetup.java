package com.wingmann.wingtech.setup;

import com.wingmann.wingtech.blocks.ModBlocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class ModSetup {

    public ItemGroup itemGroup = new ItemGroup("wingtech") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModBlocks.TESTBLOCK.get());
        }
    };



    public void init()
    {

    }
}
