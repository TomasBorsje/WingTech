package com.wingmann.wingtech.util;

import net.minecraft.potion.Effect;

public class TeaData {
    private Integer colourRGB;
    private Effect effect;

    public TeaData(Integer colourRGB, Effect effect) {
        this.colourRGB = colourRGB;
        this.effect = effect;
    }

    public Effect getEffect() {
        return this.effect;
    }

    public Integer getColourRGB() {
        return this.colourRGB;
    }
}
