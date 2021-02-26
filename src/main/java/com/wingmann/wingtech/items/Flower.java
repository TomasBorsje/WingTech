package com.wingmann.wingtech.items;

import com.wingmann.wingtech.WingTech;
import net.minecraft.item.Item;

public class Flower extends Item {
    public Flower() {
        super(new Properties().group(WingTech.setup.itemGroup));
        setRegistryName("flower");
    }
}
