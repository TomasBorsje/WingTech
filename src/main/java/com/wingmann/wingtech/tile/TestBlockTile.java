package com.wingmann.wingtech.tile;

import com.wingmann.wingtech.containers.TestBlockContainer;
import com.wingmann.wingtech.item.ModItems;
import com.wingmann.wingtech.tools.CustomEnergyStorage;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
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

import static com.wingmann.wingtech.tile.ModTileEntities.TESTBLOCK_TILE;

// TODO: Change all instances of 'testblock' to 'bio_organic_constructor'
public class TestBlockTile extends TileEntity implements ITickableTileEntity, INamedContainerProvider {
    public TestBlockTile() {
        super(TESTBLOCK_TILE);
    }

    public static int TICKS_PER_OPERATION = 100;
    public static int RF_PER_TICK_USAGE = 0;

    private ItemStackHandler itemHandler = createHandler();
    private CustomEnergyStorage energyStorage = createEnergy();
    private int progressTicks = -1;
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
        if(getProgressTicks() >= TICKS_PER_OPERATION) // Machine can now accept fuel again, and will complete it's operation
        {
            setProgressTicks(-1);
            MobEntity randomMob;
            SoundEvent sound;
            switch(rand.nextInt(9)) {
                default: { randomMob= EntityType.BEE.create(level); sound = SoundEvents.BEE_LOOP; break;}
                case 1: { randomMob= EntityType.COW.create(level); sound = SoundEvents.COW_AMBIENT; break;}
                case 2: { randomMob= EntityType.SHEEP.create(level); sound = SoundEvents.SHEEP_AMBIENT; break;}
                case 3: { randomMob= EntityType.PIG.create(level); sound = SoundEvents.PIG_AMBIENT; break;}
                case 4: { randomMob= EntityType.TURTLE.create(level); sound = SoundEvents.TURTLE_AMBIENT_LAND; break;}
                case 5: { randomMob= EntityType.OCELOT.create(level); sound = SoundEvents.OCELOT_AMBIENT; break;}
                case 6: { randomMob= EntityType.WOLF.create(level); sound = SoundEvents.WOLF_AMBIENT; break;}
                case 7: { randomMob= EntityType.FOX.create(level); sound = SoundEvents.FOX_AMBIENT; break;}
                case 8: { randomMob= EntityType.DOLPHIN.create(level); sound = SoundEvents.DOLPHIN_AMBIENT; break;}
            }
            randomMob.setPos(getBlockPos().getX() + 0.5D, getBlockPos().getY() + 1.0D, getBlockPos().getZ() + 0.5D);
            if (net.minecraftforge.common.ForgeHooks.canEntitySpawn(
                    randomMob,
                    level,
                    getBlockPos().getX() + 0.5D,
                    getBlockPos().getY() + 1.0D,
                    getBlockPos().getZ() + 0.5D,
                    null,
                    SpawnReason.TRIGGERED) != -1)
            {
                level.playSound((PlayerEntity)null, getBlockPos(), sound, SoundCategory.BLOCKS, 0.5f, 1f);
                level.playSound((PlayerEntity)null, getBlockPos(), SoundEvents.SLIME_JUMP, SoundCategory.BLOCKS, 0.5f, 1f);
                randomMob.finalizeSpawn((IServerWorld) level, level.getCurrentDifficultyAt(new BlockPos(randomMob.position())), SpawnReason.TRIGGERED, null, null);
                level.addFreshEntity(randomMob);
                this.setChanged();
            }
        }
        if(getProgressTicks() >=0) { // Machine has received fuel and is processing
            if(energyStorage.getEnergyStored() >= RF_PER_TICK_USAGE) { // If machine has at least 200 rf, do a tick of processing
                energyStorage.consumeEnergy(RF_PER_TICK_USAGE); // Use rf
                setProgressTicks(getProgressTicks() + 1);
            }
        }
        ItemStack stack = itemHandler.getStackInSlot(0);
        if (stack.getItem() == ModItems.PROTEIN_PASTE.get() && stack.getCount() > 3 && getProgressTicks() == -1) { // Machine will accept fuel again
            setProgressTicks(0);
            itemHandler.extractItem(0, 4, false); // Remove fuel
            this.setChanged();
        }
    }

    @Override
    public void load(BlockState state, CompoundNBT nbt) {
        itemHandler.deserializeNBT(nbt.getCompound("inv"));
        energyStorage.deserializeNBT(nbt.getCompound("energy"));
        setProgressTicks(nbt.getInt("counter"));
        super.load(state, nbt);
    }

    @Override
    public CompoundNBT save(CompoundNBT tag) {
        tag.put("inv", itemHandler.serializeNBT());
        tag.put("energy", energyStorage.serializeNBT());
        tag.putInt("counter", getProgressTicks());
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
                return stack.getItem() == ModItems.PROTEIN_PASTE.get();
            }

            @Nonnull
            @Override
            public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
                if (stack.getItem() != ModItems.PROTEIN_PASTE.get()) {
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
    public Container createMenu(int p_createMenu_1_, PlayerInventory inv, PlayerEntity playerEntity) {
        return new TestBlockContainer(p_createMenu_1_, level, getBlockPos(), inv, playerEntity);
    }

    public int getProgressTicks() {
        return progressTicks;
    }

    public void setProgressTicks(int progressTicks) {
        this.progressTicks = progressTicks;
    }
}
