package com.wingmann.wingtech.screens;

import com.wingmann.wingtech.WingTech;
import com.wingmann.wingtech.containers.TestBlockContainer;
import com.wingmann.wingtech.tile.AtmosphericCondenserTile;
import com.wingmann.wingtech.tile.TestBlockTile;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

public class TestBlockScreen extends ContainerScreen<TestBlockContainer> {

    private ResourceLocation GUI = new ResourceLocation(WingTech.MODID, "textures/gui/testblock_gui.png");
    private TestBlockTile tile;

    public TestBlockScreen(TestBlockContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
        this.tile = (TestBlockTile) screenContainer.getTile();
    }

    @Override
    public void render(MatrixStack stack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(stack);
        super.render(stack, mouseX, mouseY, partialTicks);
        if(isHovering(157, 9, 11, 51, mouseX, mouseY))
        {
            renderTooltip(stack, new StringTextComponent(TextFormatting.GREEN+"Energy: "+menu.getEnergy()+" / 50,000RF"), mouseX, mouseY);
        }
        renderTooltip(stack, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(MatrixStack stack, int mouseX, int mouseY) {
        if(menu.getTileEntityCounter() != -1)
            drawString(stack, Minecraft.getInstance().font, "Progress: " + Math.round((menu.getTileEntityCounter()) / (float) AtmosphericCondenserTile.TICKS_PER_OPERATION * 100) +"%", 10, 10, 0xffffff);
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
