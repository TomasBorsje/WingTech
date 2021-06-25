package com.wingmann.wingtech.tileentities;

import com.wingmann.wingtech.containers.AtmosphericCondenserContainer;
import com.wingmann.wingtech.tools.CustomEnergyStorage;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

import static com.wingmann.wingtech.tileentities.ModTileEntities.ATMOSPHERIC_CONDENSER_TILE;

public class AtmosphericCondenserTile extends TileEntity implements ITickableTileEntity, INamedContainerProvider {
    public AtmosphericCondenserTile() {
        super(ATMOSPHERIC_CONDENSER_TILE);
    }

    public static int TICKS_PER_OPERATION = 120;
    public static int RF_PER_TICK_USAGE = 0;
    private static int AWAITING_OPERATION = -1;
    private static int PROCESSING = 0;


    private ItemStackHandler itemHandler = createHandler();
    private CustomEnergyStorage energyStorage = createEnergy();
    private int progressTicks = AWAITING_OPERATION;
    private Random rand = new Random();
    private LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);
    private LazyOptional<IEnergyStorage> energy = LazyOptional.of(() -> energyStorage);

    @Override
    public void setRemoved() {
        super.setRemoved();
        handler.invalidate();
        energy.invalidate();
    }

    @Override
    public void tick() {
        if(level.isClientSide()) // Don't operate on clients
        {
            return;
        }
        if(getProgressTicks() >= TICKS_PER_OPERATION) // Complete machine's operation
        {
            itemHandler.insertItem(0, new ItemStack(Items.COAL), false);
            setProgressTicks(AWAITING_OPERATION);
        }
        if(getProgressTicks() >= PROCESSING) { // Machine has received a diamond and is processing
            if(energyStorage.getEnergyStored() >= RF_PER_TICK_USAGE) { // If machine has enough rf for a tick of processing
                energyStorage.consumeEnergy(RF_PER_TICK_USAGE); // Use rf
                setProgressTicks(getProgressTicks() + 1);
            }
        }
        if (getProgressTicks() == AWAITING_OPERATION) { // Machine will begin operating again
            setProgressTicks(PROCESSING);
            this.setChanged();
        }
    }

    @Override
    public void load(BlockState state, CompoundNBT nbt) {
        itemHandler.deserializeNBT(nbt.getCompound("inv"));
        energyStorage.deserializeNBT(nbt.getCompound("energy"));
        setProgressTicks(nbt.getInt("progress"));
        super.load(state, nbt);
    }

    @Override
    public CompoundNBT save(CompoundNBT tag) {
        tag.put("inv", itemHandler.serializeNBT());
        tag.put("energy", energyStorage.serializeNBT());
        tag.putInt("progress", getProgressTicks());
        return super.save(tag);
    }

    private ItemStackHandler createHandler() {
        return new ItemStackHandler(1) {
            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                return stack.getItem() == Items.COAL;
            }

            @Nonnull
            @Override
            public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
                if (stack.getItem() != Items.COAL) {
                    return stack;
                }
                return super.insertItem(slot, stack, simulate);
            }
        };
    }

    private CustomEnergyStorage createEnergy() {
        return new CustomEnergyStorage(50000) {
            @Override
            protected void onEnergyChanged() {
                setChanged();
            }
        };
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if(cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
        {
            return handler.cast();
        }
        if (cap == CapabilityEnergy.ENERGY)
        {
            return energy.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public ITextComponent getDisplayName() {
        return new StringTextComponent(getType().getRegistryName().getPath());
    }

    @Nullable
    @Override
    public Container createMenu(int id, PlayerInventory inv, PlayerEntity playerEntity) {
        return new AtmosphericCondenserContainer(id, level, getBlockPos(), inv, playerEntity);
    }

    public int getProgressTicks() {
        return progressTicks;
    }

    public void setProgressTicks(int progressTicks) {
        this.progressTicks = progressTicks;
    }
}
