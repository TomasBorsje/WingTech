package com.wingmann.wingtech.tileentities;

import com.wingmann.wingtech.containers.TestBlockContainer;
import com.wingmann.wingtech.tools.CustomEnergyStorage;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.IServerWorld;
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

import static com.wingmann.wingtech.tileentities.ModTileEntities.TESTBLOCK_TILE;

public class TestBlockTile extends TileEntity implements ITickableTileEntity, INamedContainerProvider {
    public TestBlockTile() {
        super(TESTBLOCK_TILE);
    }

    public static int TICKS_PER_OPERATION = 100;
    public static int RF_PER_TICK_USAGE = 200;
    public static Item FUEL_ITEM = Items.DIAMOND;

    private ItemStackHandler itemHandler = createHandler();
    private CustomEnergyStorage energyStorage = createEnergy();
    private int progressTicks = -1;
    private Random rand = new Random();
    private LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);
    private LazyOptional<IEnergyStorage> energy = LazyOptional.of(() -> energyStorage);

    @Override
    public void remove() {
        super.remove();
        handler.invalidate();
        energy.invalidate();
    }

    @Override
    public void tick() {
        if(world.isRemote) // Don't operate on clients
        {
            return;
        }
        if(getProgressTicks() >= TICKS_PER_OPERATION) // Machine can now accept fuel again, and will complete it's operation
        {
            setProgressTicks(-1);
            MobEntity randomMob;
            SoundEvent sound;
            switch(rand.nextInt(9)) {
                default: { randomMob= EntityType.BEE.create(world); sound = SoundEvents.ENTITY_BEE_LOOP; break;}
                case 1: { randomMob= EntityType.COW.create(world); sound = SoundEvents.ENTITY_COW_AMBIENT; break;}
                case 2: { randomMob= EntityType.SHEEP.create(world); sound = SoundEvents.ENTITY_SHEEP_AMBIENT; break;}
                case 3: { randomMob= EntityType.PIG.create(world); sound = SoundEvents.ENTITY_PIG_AMBIENT; break;}
                case 4: { randomMob= EntityType.TURTLE.create(world); sound = SoundEvents.ENTITY_TURTLE_AMBIENT_LAND; break;}
                case 5: { randomMob= EntityType.OCELOT.create(world); sound = SoundEvents.ENTITY_OCELOT_AMBIENT; break;}
                case 6: { randomMob= EntityType.WOLF.create(world); sound = SoundEvents.ENTITY_WOLF_AMBIENT; break;}
                case 7: { randomMob= EntityType.FOX.create(world); sound = SoundEvents.ENTITY_FOX_AMBIENT; break;}
                case 8: { randomMob= EntityType.DOLPHIN.create(world); sound = SoundEvents.ENTITY_DOLPHIN_AMBIENT; break;}
            }
            randomMob.setPosition(pos.getX() + 0.5D, pos.getY() + 1.0D, pos.getZ() + 0.5D);
            if (net.minecraftforge.common.ForgeHooks.canEntitySpawn(
                    randomMob,
                    world,
                    pos.getX() + 0.5D,
                    pos.getY() + 1.0D,
                    pos.getZ() + 0.5D,
                    null,
                    SpawnReason.TRIGGERED) != -1)
            {
                world.playSound((PlayerEntity)null, pos, sound, SoundCategory.BLOCKS, 0.5f, 1f);
                world.playSound((PlayerEntity)null, pos, SoundEvents.ENTITY_SLIME_JUMP, SoundCategory.BLOCKS, 0.5f, 1f);
                randomMob.onInitialSpawn((IServerWorld) world, world.getDifficultyForLocation(new BlockPos(randomMob.getPositionVec())), SpawnReason.TRIGGERED, null, null);
                world.addEntity(randomMob);
                this.markDirty();
            }
        }
        if(getProgressTicks() >=0) { // Machine has received a diamond and is processing
            if(energyStorage.getEnergyStored() >= RF_PER_TICK_USAGE) { // If machine has at least 200 rf, do a tick of processing
                energyStorage.consumeEnergy(RF_PER_TICK_USAGE); // Use rf
                setProgressTicks(getProgressTicks() + 1);
            }
        }
        ItemStack stack = itemHandler.getStackInSlot(0);
        if (stack.getItem() == Items.DIAMOND && getProgressTicks() == -1) { // Machine will accept diamond again
            setProgressTicks(0);
            itemHandler.extractItem(0, 1, false); // Remove diamond
            this.markDirty();
        }
    }

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        itemHandler.deserializeNBT(nbt.getCompound("inv"));
        energyStorage.deserializeNBT(nbt.getCompound("energy"));
        setProgressTicks(nbt.getInt("counter"));
        super.read(state, nbt);
    }

    @Override
    public CompoundNBT write(CompoundNBT tag) {
        tag.put("inv", itemHandler.serializeNBT());
        tag.put("energy", energyStorage.serializeNBT());
        tag.putInt("counter", getProgressTicks());
        return super.write(tag);
    }

    private ItemStackHandler createHandler() {
        return new ItemStackHandler(1) {
            @Override
            protected void onContentsChanged(int slot) {
                markDirty();
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                return stack.getItem() == FUEL_ITEM;
            }

            @Nonnull
            @Override
            public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
                if (stack.getItem() != FUEL_ITEM) {
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
                markDirty();
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
    public Container createMenu(int p_createMenu_1_, PlayerInventory inv, PlayerEntity playerEntity) {
        return new TestBlockContainer(p_createMenu_1_, world, pos, inv, playerEntity);
    }

    public int getProgressTicks() {
        return progressTicks;
    }

    public void setProgressTicks(int progressTicks) {
        this.progressTicks = progressTicks;
    }
}
