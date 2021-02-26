package com.wingmann.wingtech.setup;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public interface IProxy {

    void init();
    World getclientWorld();
    PlayerEntity getClientPlayer();
    void setRenderType();
}
