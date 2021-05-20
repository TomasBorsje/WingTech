package com.wingmann.wingtech.item.crafting;

import com.wingmann.wingtech.blocks.PalladiumOre;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.SpecialRecipeSerializer;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.registries.ObjectHolder;
import net.minecraftforge.registries.RegistryManager;

public class ModRecipeSerializers {

    @ObjectHolder("wingtech:crafting_special_tea")
    public static SpecialRecipeSerializer<TeaRecipe> TEA_RECIPE_SERIALIZER ;

}
