package com.wingmann.wingtech.item;

import com.wingmann.wingtech.WingTech;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ObjectHolder;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, WingTech.MODID);

    @ObjectHolder("wingtech:tungsten_ingot")
    public static Item TUNGSTEN_INGOT;

    @ObjectHolder("wingtech:teacup")
    public static Item TEACUP;

    @ObjectHolder("wingtech:tea")
    public static Item TEA;

    @ObjectHolder("wingtech:flower")
    public static BlockItem FLOWER_ITEM;

    /***
     Return a generic item with no special properties other than belonging in the mod's itemgroup.
     * @param registryName The item's registryname.
     * @return A generic item.
     */
    public static Item genericItem(String registryName)
    {
        return new Item(new Item.Properties().tab(WingTech.setup.itemGroup)).setRegistryName(registryName);
    }

}
