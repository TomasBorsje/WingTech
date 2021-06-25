package com.wingmann.wingtech.item;

import com.wingmann.wingtech.WingTech;
import net.minecraft.item.BoneMealItem;
import net.minecraft.item.Item;

public class Fertilizer extends BoneMealItem {
    public Fertilizer() {
        super(new Item.Properties().tab(WingTech.setup.itemGroup));
    }
}
