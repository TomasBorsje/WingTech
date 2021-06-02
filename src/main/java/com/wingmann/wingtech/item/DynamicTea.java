package com.wingmann.wingtech.item;

import com.wingmann.wingtech.util.TeaData;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.stats.Stats;
import net.minecraft.util.*;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.List;

public class DynamicTea extends Item {

    private TeaData data;
    public DynamicTea(Properties builder, TeaData data) {
        super(builder);
        this.data = data;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, World worldIn, LivingEntity entityLiving) {
        super.finishUsingItem(stack, worldIn, entityLiving);
        PlayerEntity playerentity = entityLiving instanceof PlayerEntity ? (PlayerEntity)entityLiving : null;
        if(data != null) {
            entityLiving.addEffect(new EffectInstance(Effect.byId(this.data.EffectId), this.data.EffectDuration, this.data.EffectAmplifier, false, false));
        }
        if (entityLiving instanceof ServerPlayerEntity) {
            CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayerEntity)entityLiving, stack);
        }
        if (playerentity != null) {
            playerentity.awardStat(Stats.ITEM_USED.get(this));
            if (!playerentity.abilities.instabuild) {
                stack.shrink(1);
            }
        }
        if (stack.isEmpty()) {
            return new ItemStack(ModItems.TEACUP); // Replace tea with empty cup
        } else {
            if (playerentity != null && !playerentity.abilities.instabuild) {
                ItemStack itemstack = new ItemStack(ModItems.TEACUP);
                if (!playerentity.inventory.add(itemstack)) {
                    playerentity.drop(itemstack, false);
                }
            }
            return stack;
        }
    }

    public static int getColor(ItemStack stack, int tintIndex) {
        switch(tintIndex) {
            case 0: {
                return Color.WHITE.getRGB();
            }
            case 1: {
                DynamicTea honeycombItem = (DynamicTea) stack.getItem();
                return honeycombItem.getTeaColour();
            }
            default:
                return Color.WHITE.getRGB();
        }
    }

    public int getTeaColour() {
        return this.data.Colour;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        tooltip.add(new TranslationTextComponent("wingtech.ui.dynamictea_tooltip_1"));
        tooltip.add(new TranslationTextComponent("wingtech.ui.dynamictea_tooltip_2", Effect.byId(this.data.EffectId).getDisplayName().getString()));
    }

    public int getUseDuration(ItemStack stack) {
        return 30;
    }

    public UseAction getUseAnimation(ItemStack stack) {
        return UseAction.DRINK;
    }

    public SoundEvent getDrinkingSound() {
        return SoundEvents.GENERIC_DRINK;
    }

    public SoundEvent getEatingSound() {
        return SoundEvents.GENERIC_DRINK;
    }

    @Override
    public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
        return DrinkHelper.useDrink(worldIn, playerIn, handIn);
    }

}
