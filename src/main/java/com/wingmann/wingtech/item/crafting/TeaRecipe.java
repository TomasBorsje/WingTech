package com.wingmann.wingtech.item.crafting;

import com.google.common.collect.Maps;

import java.awt.*;
import java.util.Map;

import com.wingmann.wingtech.item.ModItems;
import com.wingmann.wingtech.util.TeaData;
import javafx.util.Pair;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.Effect;
import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.world.World;

public class TeaRecipe extends SpecialRecipe {
    private static final Ingredient INGREDIENT_TEACUP = Ingredient.fromItems(ModItems.TEACUP);


    private static final Map<Item, TeaData> ITEM_TEADATA_MAP = Util.make(Maps.newHashMap(), (itemShapeMap) -> {
        itemShapeMap.put(ModItems.PALLADIUM_INGOT, new TeaData(new Color(252, 186, 3).getRGB(), Effects.HASTE)); // Palladium ingot makes yellow haste tea
        itemShapeMap.put(ModItems.FLOWER_ITEM, new TeaData(new Color(255, 80, 57).getRGB(), Effects.STRENGTH)); // Flower makes orange strength tea
        itemShapeMap.put(Items.CACTUS, new TeaData(new Color(16, 208, 23).getRGB(), Effects.RESISTANCE)); // Cactus makes green resistance tea
    });

    private static final Ingredient INGREDIENT_FLOWER = Ingredient.fromItems(ITEM_TEADATA_MAP.keySet().toArray(new Item[0]));

    public TeaRecipe(ResourceLocation id) {
        super(id);
    }

    public boolean matches(CraftingInventory inv, World worldIn) {
        boolean flagTeacup = false;
        boolean flagFlower = false;

        for(int i = 0; i < inv.getSizeInventory(); ++i) {
            ItemStack itemstack = inv.getStackInSlot(i);
            if (!itemstack.isEmpty()) {
                if (INGREDIENT_TEACUP.test(itemstack)) {
                    if (flagTeacup) {
                        return false;
                    }
                    flagTeacup = true;
                } else if (INGREDIENT_FLOWER.test(itemstack)) {
                    if (flagFlower) {
                        return false;
                    }
                    flagFlower = true;
                }
            }
        }
        return flagTeacup && flagFlower;
    }

    /**
     * Returns an Item that is the result of this recipe
     */
    public ItemStack getCraftingResult(CraftingInventory inv) {
        ItemStack itemstack = new ItemStack(ModItems.TEA);
        CompoundNBT compoundnbt = itemstack.getOrCreateTag();
        Integer teaItemColour = Color.BLACK.getRGB(); // error tea colour
        Effect teaItemEffect = Effects.HASTE; // default effect is haste

        for(int i = 0; i < inv.getSizeInventory(); ++i) {
            ItemStack itemstack1 = inv.getStackInSlot(i);
            if (!itemstack1.isEmpty()) {
                if (INGREDIENT_FLOWER.test(itemstack1)) {
                    teaItemColour = ITEM_TEADATA_MAP.get(itemstack1.getItem()).getColourRGB();
                    teaItemEffect = ITEM_TEADATA_MAP.get(itemstack1.getItem()).getEffect();
                }
            }
        }

        compoundnbt.putInt("Colour", teaItemColour);
        compoundnbt.putByte("EffectId", (byte)Effect.getId(teaItemEffect));
        itemstack.setTag(compoundnbt);
        return itemstack;
    }

    /**
     * Used to determine if this recipe can fit in a grid of the given width/height
     */
    public boolean canFit(int width, int height) {
        return width * height >= 2;
    }

    /**
     * Get the result of this recipe, usually for display purposes (e.g. recipe book). If your recipe has more than one
     * possible result (e.g. it's dynamic and depends on its inputs), then return an empty stack.
     */
    public ItemStack getRecipeOutput() {
        return new ItemStack(ModItems.TEA);
    }

    public IRecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.TEA_RECIPE_SERIALIZER;
    }
}
