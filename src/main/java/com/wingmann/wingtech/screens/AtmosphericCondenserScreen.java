package com.wingmann.wingtech.screens;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.wingmann.wingtech.WingTech;
import com.wingmann.wingtech.containers.AtmosphericCondenserContainer;
import com.wingmann.wingtech.tileentities.AtmosphericCondenserTile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class AtmosphericCondenserScreen extends ContainerScreen<AtmosphericCondenserContainer> {

    private ResourceLocation GUI = new ResourceLocation(WingTech.MODID, "textures/gui/atmospheric_condenser_gui.png");
    public AtmosphericCondenserScreen(AtmosphericCondenserContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
    }

    private static int GUI_WIDTH = 180;
    private static int GUI_HEIGHT = 152;


    @Override
    public void render(MatrixStack stack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(stack);
        super.render(stack, mouseX, mouseY, partialTicks);
        if(isHovering(157, 9, 11, 51, mouseX, mouseY))
        {
            renderTooltip(stack, new TranslationTextComponent("wingtech.ui.energy_bar", menu.getEnergy()), mouseX, mouseY);
        }
        renderTooltip(stack, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(MatrixStack stack, int mouseX, int mouseY) {
        String name = new TranslationTextComponent("block.wingtech.atmospheric_condenser").getString();
        Minecraft.getInstance().font.draw(stack, name,GUI_WIDTH/2 - font.width(name)/2, 4, 0x404040);
    }

    @Override
    protected void renderBg(MatrixStack stack, float partialTicks, int mouseX, int mouseY) {
        GlStateManager._color4f(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bind(GUI);
        int relX = (this.width - this.getXSize()) / 2;
        int relY = (this.height - this.getYSize()) / 2;
        this.blit(stack, relX, relY, 0, 0, this.getXSize(), this.getYSize());
        if(menu.getTileEntityCounter() != -1) {
            this.blit(stack, this.getGuiLeft() + 79, this.getGuiTop() + 51, 180, 0, Math.round((menu.getTileEntityCounter()) / (float)AtmosphericCondenserTile.TICKS_PER_OPERATION * 24), 16);
        }
        // Draw energy bar
        this.blit(stack, this.getGuiLeft() + 159, this.getGuiTop() + 11 + 47 - Math.round((menu.getEnergy()) / 50000f * 47), 180, 17 + 47 - Math.round((menu.getEnergy()) / 50000f * 47), 7, Math.round((menu.getEnergy()) / 50000f * 47));
    }
}
