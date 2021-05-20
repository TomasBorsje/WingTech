package com.wingmann.wingtech.item;

import com.wingmann.wingtech.WingTech;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.registries.ObjectHolder;

public class ModItems {

    @ObjectHolder("wingtech:palladium_ingot")
    public static Item PALLADIUM_INGOT;

    @ObjectHolder("wingtech:teacup")
    public static Item TEACUP;

    @ObjectHolder("wingtech:tea")
    public static Item TEA;

    @ObjectHolder("wingtech:flower")
    public static BlockItem FLOWER_ITEM;

    public static Item genericItem(String registryName)
    {
        return new Item(new Item.Properties().group(WingTech.setup.itemGroup)).setRegistryName(registryName);
    }

}
