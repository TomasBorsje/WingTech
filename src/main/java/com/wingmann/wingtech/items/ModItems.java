package com.wingmann.wingtech.items;

import com.wingmann.wingtech.WingTech;
import net.minecraft.item.Item;
import net.minecraftforge.registries.ObjectHolder;

public class ModItems {

    @ObjectHolder("wingtech:palladium_ingot")
    public static Item PALLADIUM_INGOT;

    public static Item.Properties genericProperties()
    {
        return new Item.Properties().group(WingTech.setup.itemGroup);
    }

    public static Item genericItem(String registryName)
    {
        return new Item(new Item.Properties().group(WingTech.setup.itemGroup)).setRegistryName(registryName);
    }

}
