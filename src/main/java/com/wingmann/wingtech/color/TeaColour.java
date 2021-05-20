package com.wingmann.wingtech.color;

import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

import java.awt.*;

public class TeaColour implements IItemColor {

    static final Integer DEFAULT_COLOR = new Color(252, 186, 3).getRGB();
    @Override
    public int getColor(ItemStack stack, int tintIndex) {
        // Switch depending which layer we're on
        switch(tintIndex) {
            case 0: return Color.WHITE.getRGB();
            case 1: {
                // Green if the stack is greater than 8, blue if below

                if(!stack.hasTag()) { return DEFAULT_COLOR; } // If we have no data, render as default tea
                CompoundNBT nbt = stack.getTag();
                return nbt.getInt("Colour");
            }
            default: {
                // Should never get to this point!
                return Color.BLACK.getRGB();
            }
        }
    }
}
