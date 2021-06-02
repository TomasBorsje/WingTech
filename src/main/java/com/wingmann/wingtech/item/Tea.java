package com.wingmann.wingtech.item;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.*;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class Tea extends Item {

    public Tea(Item.Properties builder) {
        super(builder);
    }

    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entityLiving) {
        super.finishUsingItem(stack, worldIn, entityLiving);
        if (entityLiving instanceof ServerPlayerEntity) {
            entityLiving.addEffect(new EffectInstance(Effects.DIG_SPEED, 600, 1, false, false));
        }
        if (stack.isEmpty()) {
            return new ItemStack(ModItems.TEACUP); // Replace tea with empty cup
        } else {
            if (entityLiving instanceof PlayerEntity && !((PlayerEntity)entityLiving).abilities.instabuild) {
                ItemStack itemstack = new ItemStack(ModItems.TEACUP);
                PlayerEntity playerentity = (PlayerEntity)entityLiving;
                if (!playerentity.inventory.add(itemstack)) {
                    playerentity.drop(itemstack, false);
                }
            }
            return stack;
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        tooltip.add(new TranslationTextComponent("wingtech.ui.tea_tooltip_1"));
    }



    public int getUseDuration(ItemStack stack) {
        return 30;
    }

    public UseAction getUseAction(ItemStack stack) {
        return UseAction.DRINK;
    }

    public SoundEvent getDrinkSound() {
        return SoundEvents.GENERIC_DRINK;
    }

    public SoundEvent getEatSound() {
        return SoundEvents.GENERIC_DRINK;
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        return DrinkHelper.useDrink(worldIn, playerIn, handIn);
    }

}
