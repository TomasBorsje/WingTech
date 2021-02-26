package com.wingmann.wingtech.setup;

import com.wingmann.wingtech.blocks.ModBlocks;
import com.wingmann.wingtech.screens.TestBlockScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class ClientProxy implements IProxy {

    @Override
    public void init() {
        ScreenManager.registerFactory(ModBlocks.TESTBLOCK_CONTAINER, TestBlockScreen::new);
    }

    @Override
    public World getclientWorld() {
        return Minecraft.getInstance().world;
    }

    @Override
    public PlayerEntity getClientPlayer() { return Minecraft.getInstance().player; }

    @Override
    public void setRenderType() {

    }
}
