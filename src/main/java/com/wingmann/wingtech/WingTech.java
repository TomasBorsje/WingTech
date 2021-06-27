package com.wingmann.wingtech;

import com.wingmann.wingtech.blocks.ModBlocks;
import com.wingmann.wingtech.containers.AtmosphericCondenserContainer;
import com.wingmann.wingtech.containers.TestBlockContainer;
import com.wingmann.wingtech.data.TeaRecipeBuilder;
import com.wingmann.wingtech.init.ClientSetup;
import com.wingmann.wingtech.init.TeaSetup;
import com.wingmann.wingtech.item.DynamicTea;
import com.wingmann.wingtech.item.ModItems;
import com.wingmann.wingtech.registry.TeaRegistry;
import com.wingmann.wingtech.setup.ClientProxy;
import com.wingmann.wingtech.setup.IProxy;
import com.wingmann.wingtech.setup.ModSetup;
import com.wingmann.wingtech.setup.ServerProxy;
import com.wingmann.wingtech.tile.AtmosphericCondenserTile;
import com.wingmann.wingtech.tile.TestBlockTile;
import com.wingmann.wingtech.util.TeaData;
import com.wingmann.wingtech.world.WorldGen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
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
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

@Mod(WingTech.MODID)
public class WingTech
{
    public static final Logger LOGGER = LogManager.getLogger();
    public static IProxy proxy = DistExecutor.unsafeRunForDist(() -> () -> new ClientProxy(), () -> () -> new ServerProxy());
    public static ModSetup setup = new ModSetup();
    public static final String MODID = "wingtech";

    public static File configFolder = new File("./config/wingtech");

    public WingTech() {
        TeaSetup.setupTeas();
        TeaSetup.registerTeaModels();
        TeaSetup.registerTeaLang();
        TeaSetup.registerTeasItems();

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> ClientSetup::setupResourcePack); // Setup client resource pack

        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register deferred registries
        ModItems.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModBlocks.BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        // Register other event listeners
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.addListener(EventPriority.HIGH, WorldGen::generateChunk); // World gen listener
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        // All blocks, item, biomes, etc will be registered by now
        setup.init();
        proxy.init();
        MinecraftForge.EVENT_BUS.register(new TeaRecipeBuilder());
    }

    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {

        @SubscribeEvent
        public static void onColorHandlerEvent(ColorHandlerEvent.Item event) {
            for(TeaData data : TeaRegistry.getRegistry().getTeaData().values()) {
                event.getItemColors().register(DynamicTea::getColor, ForgeRegistries.ITEMS.getValue(new ResourceLocation("wingtech:dynamictea_"+data.Type.toLowerCase().replace(' ', '_'))));
            }
        }

        @SubscribeEvent
        public static void onClientSetupEvent(FMLClientSetupEvent event) {
            RenderTypeLookup.setRenderLayer(ModBlocks.FLOWER.get(), RenderType.cutout()); // Set flower block to be rendered properly
        }

        @SubscribeEvent
        public static void onTileEntityRegistry(final RegistryEvent.Register<TileEntityType<?>> event)
        {
            event.getRegistry().register(TileEntityType.Builder.of(TestBlockTile::new, ModBlocks.TESTBLOCK.get()).build((null)).setRegistryName("testblock"));
            event.getRegistry().register(TileEntityType.Builder.of(AtmosphericCondenserTile::new, ModBlocks.ATMOSPHERIC_CONDENSER.get()).build((null)).setRegistryName("atmospheric_condenser"));
        }

        @SubscribeEvent
        public static void onContainerRegistry(final RegistryEvent.Register<ContainerType<?>> event)
        {
            event.getRegistry().register(IForgeContainerType.create(((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                return new TestBlockContainer(windowId, proxy.getclientWorld(), pos, inv, proxy.getClientPlayer());
            })).setRegistryName("wingtech:testblock"));
            event.getRegistry().register(IForgeContainerType.create(((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                return new AtmosphericCondenserContainer(windowId, proxy.getclientWorld(), pos, inv, proxy.getClientPlayer());
            })).setRegistryName("wingtech:atmospheric_condenser"));
        }
    }
}
