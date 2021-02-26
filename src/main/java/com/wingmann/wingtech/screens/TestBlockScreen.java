package com.wingmann.wingtech.screens;

import com.wingmann.wingtech.WingTech;
import com.wingmann.wingtech.containers.TestBlockContainer;
import com.wingmann.wingtech.tileentities.TestBlockTile;
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
        if(isPointInRegion(157, 9, 11, 51, mouseX, mouseY))
        {
            renderTooltip(stack, new StringTextComponent(TextFormatting.GREEN+"Energy: "+container.getEnergy()+" / 50,000RF"), mouseX, mouseY);
        }
        renderHoveredTooltip(stack, mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(MatrixStack stack, int mouseX, int mouseY) {
        if(container.getTileEntityCounter() != -1)
        drawString(stack, Minecraft.getInstance().fontRenderer, "Progress: " + Math.round((container.getTileEntityCounter())/40f*100) +"%", 10, 10, 0xffffff);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack stack, float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(GUI);
        int relX = (this.width - this.xSize) / 2;
        int relY = (this.height - this.ySize) / 2;
        this.blit(stack, relX, relY, 0, 0, this.xSize, this.ySize);
        if(container.getTileEntityCounter() != -1) {
            this.blit(stack, this.getGuiLeft() + 79, this.getGuiTop() + 51, 180, 0, Math.round((container.getTileEntityCounter()) / 40f * 24), 16);
        }
        this.blit(stack, this.getGuiLeft() + 159, this.getGuiTop() + 11 + 47 - Math.round((container.getEnergy()) / 50000f * 47), 180, 17 + 47 - Math.round((container.getEnergy()) / 50000f * 47), 7, Math.round((container.getEnergy()) / 50000f * 47));
    }
}
