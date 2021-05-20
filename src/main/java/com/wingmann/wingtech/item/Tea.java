package com.wingmann.wingtech.item;

import com.wingmann.wingtech.item.crafting.TeaRecipe;
import javafx.util.Pair;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.stats.Stats;
import net.minecraft.util.*;
import net.minecraft.util.text.*;
import net.minecraft.util.text.Color;
import net.minecraft.util.text.TextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.List;

public class Tea extends Item {

    public Tea(Item.Properties builder) {
        super(builder);
    }

    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entityLiving) {
        super.onItemUseFinish(stack, worldIn, entityLiving);
        if (entityLiving instanceof ServerPlayerEntity) {
            ServerPlayerEntity serverplayerentity = (ServerPlayerEntity)entityLiving;
            CriteriaTriggers.CONSUME_ITEM.trigger(serverplayerentity, stack);

            if(stack.hasTag()) {
                CompoundNBT nbt = stack.getTag();
                entityLiving.addPotionEffect(new EffectInstance(Effect.get(nbt.getByte("EffectId")), 2000, 2));
            }

            serverplayerentity.addStat(Stats.ITEM_USED.get(this));
        }
        if (stack.isEmpty()) {
            return new ItemStack(ModItems.TEACUP); // Replace tea with empty cup
        } else {
            if (entityLiving instanceof PlayerEntity && !((PlayerEntity)entityLiving).abilities.isCreativeMode) {
                ItemStack itemstack = new ItemStack(ModItems.TEACUP);
                PlayerEntity playerentity = (PlayerEntity)entityLiving;
                if (!playerentity.inventory.addItemStackToInventory(itemstack)) {
                    playerentity.dropItem(itemstack, false);
                }
            }
            return stack;
        }
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        if(stack.hasTag()) {
            tooltip.add(new TranslationTextComponent("wingtech.ui.tea_tooltip_1"));
            tooltip.add(new TranslationTextComponent("wingtech.ui.tea_tooltip_2",Effect.get(stack.getTag().getByte("EffectId")).getDisplayName().getString()));
        }
    }

    /**
     * How long it takes to use or consume an item
     */
    public int getUseDuration(ItemStack stack) {
        return 30;
    }

    /**
     * returns the action that specifies what animation to play when the item is being used
     */
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.DRINK;
    }

    public SoundEvent getDrinkSound() {
        return SoundEvents.ENTITY_GENERIC_DRINK;
    }

    public SoundEvent getEatSound() {
        return SoundEvents.ENTITY_GENERIC_DRINK;
    }

    /**
     * Called to trigger the item's "innate" right click behavior. To handle when this item is used on a Block, see
     * {@link #onItemUse}.
     */
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        return DrinkHelper.startDrinking(worldIn, playerIn, handIn);
    }

}
