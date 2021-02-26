package com.wingmann.wingtech.items;

import com.wingmann.wingtech.WingTech;
import net.minecraft.item.Item;

public class TestItem extends Item {
    public TestItem() {
        super(new Item.Properties().group(WingTech.setup.itemGroup));
        setRegistryName("testitem");
    }
}