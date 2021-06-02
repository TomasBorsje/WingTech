package com.wingmann.wingtech.data;

import com.wingmann.wingtech.WingTech;
import com.wingmann.wingtech.item.ModItems;
import com.wingmann.wingtech.registry.TeaRegistry;
import com.wingmann.wingtech.util.TeaData;
import com.wingmann.wingtech.util.TeaInfoUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.item.crafting.ShapelessRecipe;
import net.minecraft.resources.IResourceManager;
import net.minecraft.resources.IResourceManagerReloadListener;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.ArrayList;
import java.util.Collection;

public class TeaRecipeBuilder implements IResourceManagerReloadListener {

    private static final TeaRegistry TEA_REGISTRY = TeaRegistry.getRegistry();
    private static RecipeManager recipeManager;

    private static void setRecipeManager(RecipeManager recipeManager) {
        TeaRecipeBuilder.recipeManager = recipeManager;
    }

    @Override
    public void onResourceManagerReload(IResourceManager resourceManager) {
        ArrayList<IRecipe<?>> teaRecipeList = new ArrayList<IRecipe<?>>();
        TEA_REGISTRY.getTeaData().forEach((type, data) -> {
            IRecipe<?> teaRecipe = this.createTeaRecipe(type, data);
            teaRecipeList.add(teaRecipe);

        });
        WingTech.LOGGER.info("Loaded "+teaRecipeList.size() + " tea recipes");
        Collection<IRecipe<?>> addedRecipes = recipeManager.getRecipes();
        addedRecipes.addAll(teaRecipeList);
        recipeManager.replaceRecipes(addedRecipes);
    }

    private IRecipe<?> createTeaRecipe(String type, TeaData data) {
        return new ShapelessRecipe(
                new ResourceLocation(WingTech.MODID, data.Type.toLowerCase().replace(' ', '_') + "_tea_recipe"),
                "",
                new ItemStack(TeaInfoUtil.GetItemFromData(data), 1),
                NonNullList.of(Ingredient.EMPTY, Ingredient.of(TeaInfoUtil.GetIngredientItemFromData(data)), Ingredient.of(ModItems.TEACUP))
        );
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public void onAddReloadListeners(AddReloadListenerEvent event) {
        event.addListener(this);
        setRecipeManager(event.getDataPackRegistries().getRecipeManager());
        WingTech.LOGGER.info("Adding Reload Listener: 'wingtech resource manager'");
    }
}
