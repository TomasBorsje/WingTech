package com.wingmann.wingtech.util;

import com.wingmann.wingtech.WingTech;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

public class TeaInfoUtil {
    public static ResourceLocation GetResourceLocation(TeaData tea){
        return new ResourceLocation(WingTech.MODID+":dynamictea_"+tea.Type.toLowerCase().replace(' ', '_'));
    }

    public static ResourceLocation GetIngredientResourceLocation(TeaData tea){
        return new ResourceLocation(tea.Ingredient);
    }
    public static Item GetItemFromData(TeaData tea) {
        return ForgeRegistries.ITEMS.getValue(GetResourceLocation(tea));
    }

    public static Item GetIngredientItemFromData(TeaData tea) {
        return ForgeRegistries.ITEMS.getValue(GetIngredientResourceLocation(tea));
    }
}
