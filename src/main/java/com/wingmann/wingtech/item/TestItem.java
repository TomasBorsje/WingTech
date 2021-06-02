package com.wingmann.wingtech.item;

import com.wingmann.wingtech.WingTech;
import net.minecraft.item.Item;

public class TestItem extends Item {
    public TestItem() {
        super(new Item.Properties().tab(WingTech.setup.itemGroup));
        setRegistryName("testitem");
    }
}