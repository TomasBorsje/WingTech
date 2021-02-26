package com.wingmann.wingtech;

import com.wingmann.wingtech.blocks.*;
import com.wingmann.wingtech.containers.TestBlockContainer;
import com.wingmann.wingtech.items.ModItems;
import com.wingmann.wingtech.setup.ClientProxy;
import com.wingmann.wingtech.setup.IProxy;
import com.wingmann.wingtech.setup.ModSetup;
import com.wingmann.wingtech.setup.ServerProxy;
import com.wingmann.wingtech.tileentities.TestBlockTile;
import com.wingmann.wingtech.world.WorldGen;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(WingTech.MODID)
public class WingTech
{
    private static final Logger LOGGER = LogManager.getLogger();
    public static IProxy proxy = DistExecutor.unsafeRunForDist(() -> () -> new ClientProxy(), () -> () -> new ServerProxy());
    public static ModSetup setup = new ModSetup();
    public static final String MODID = "wingtech";

    public WingTech() {
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.addListener(EventPriority.HIGH, WorldGen::generateChunk); // World gen listener
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        // All blocks, items, biomes, etc will be registered by now
        setup.init();
        proxy.init();
    }

    // Subscribe to the event bus
    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> event) {
            event.getRegistry().register(new TestBlock());
            event.getRegistry().register(new PalladiumOre());
            event.getRegistry().register(new Flower());
            event.getRegistry().register(new MachineCasing());
        }

        @SubscribeEvent
        public static void onClientSetupEvent(FMLClientSetupEvent event) {
            RenderTypeLookup.setRenderLayer(ModBlocks.FLOWER, RenderType.getCutout()); // Set flower block to be rendered properly
        }

        @SubscribeEvent
        public static void onItemsRegistry(final RegistryEvent.Register<Item> event) {
            // Register items
            event.getRegistry().register(ModItems.genericItem("palladium_ingot"));
            event.getRegistry().register(ModItems.genericItem("microminer"));

            // Register BlockItems
            event.getRegistry().register(new BlockItem(ModBlocks.TESTBLOCK, new Item.Properties().group(setup.itemGroup)).setRegistryName("testblock"));
            event.getRegistry().register(new BlockItem(ModBlocks.PALLADIUM_ORE, new Item.Properties().group(setup.itemGroup)).setRegistryName("palladium_ore"));
            event.getRegistry().register(new BlockItem(ModBlocks.FLOWER, new Item.Properties().group(setup.itemGroup)).setRegistryName("flower"));
            event.getRegistry().register(new BlockItem(ModBlocks.MACHINE_CASING, new Item.Properties().group(setup.itemGroup)).setRegistryName("machine_casing"));
        }

        @SubscribeEvent
        public static void onTileEntityRegistry(final RegistryEvent.Register<TileEntityType<?>> event)
        {
            event.getRegistry().register(TileEntityType.Builder.create(TestBlockTile::new, ModBlocks.TESTBLOCK).build((null)).setRegistryName("testblock"));
        }

        @SubscribeEvent
        public static void onContainerRegistry(final RegistryEvent.Register<ContainerType<?>> event)
        {
            event.getRegistry().register(IForgeContainerType.create(((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                return new TestBlockContainer(windowId, proxy.getclientWorld(), pos, inv, proxy.getClientPlayer());
            })).setRegistryName("wingtech:testblock"));
        }
    }
}
