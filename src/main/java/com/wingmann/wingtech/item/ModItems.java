package com.wingmann.wingtech.item;

import com.wingmann.wingtech.WingTech;
import com.wingmann.wingtech.blocks.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, WingTech.MODID);

    // Items
    public static final RegistryObject<Item> MESH = ITEMS.register("mesh", ModItems::genericItem);
    public static final RegistryObject<Item> POWER_CELL = ITEMS.register("power_cell", ModItems::genericItem);
    public static final RegistryObject<Item> FERTILIZER = ITEMS.register("fertilizer", Fertilizer::new);
    public static final RegistryObject<Item> NITROGEN = ITEMS.register("nitrogen", ModItems::genericItem);
    public static final RegistryObject<Item> OXYGEN = ITEMS.register("oxygen", ModItems::genericItem);
    public static final RegistryObject<Item> HYDROGEN = ITEMS.register("hydrogen", ModItems::genericItem);
    public static final RegistryObject<Item> TEACUP = ITEMS.register("teacup", ModItems::genericItem);
    public static final RegistryObject<Item> TUNGSTEN_INGOT = ITEMS.register("tungsten_ingot", ModItems::genericItem);
    public static final RegistryObject<Item> PROTEIN_PASTE = ITEMS.register("protein_paste", ModItems::genericItem);

    // Block Items
    public static final RegistryObject<BlockItem> FLOWER = ITEMS.register("flower", () -> genericBlockItem(ModBlocks.FLOWER.get()));
    public static final RegistryObject<BlockItem> TUNGSTEN_ORE = ITEMS.register("tungsten_ore", () -> genericBlockItem(ModBlocks.TUNGSTEN_ORE.get()));
    public static final RegistryObject<BlockItem> ATMOSPHERIC_CONDENSER = ITEMS.register("atmospheric_condenser", () -> genericBlockItem(ModBlocks.ATMOSPHERIC_CONDENSER.get()));
    public static final RegistryObject<BlockItem> MACHINE_CASING = ITEMS.register("machine_casing", () -> genericBlockItem(ModBlocks.MACHINE_CASING.get()));
    public static final RegistryObject<BlockItem> TESTBLOCK = ITEMS.register("testblock", () -> genericBlockItem(ModBlocks.TESTBLOCK.get()));
    public static final RegistryObject<BlockItem> TUNGSTEN_BLOCK = ITEMS.register("tungsten_block", () -> genericBlockItem(ModBlocks.TUNGSTEN_BLOCK.get()));


    /**
     Returns a generic Item with no special properties other than belonging in the mod's itemgroup.
     * @return An Item.
     */
    public static Item genericItem()
    {
        return new Item(new Item.Properties().tab(WingTech.setup.itemGroup));
    }

    /**
     Returns a generic BlockItem with no special properties other than belonging in the mod's itemgroup.
     * @param block The block to make the BlockItem with.
     * @return A BlockItem.
     */
    public static BlockItem genericBlockItem(Block block)
    {
        return new BlockItem(block, new Item.Properties().tab(WingTech.setup.itemGroup));
    }

}
