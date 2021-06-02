package com.wingmann.wingtech.init;

import com.wingmann.wingtech.WingTech;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.FolderPack;
import net.minecraft.resources.IPackNameDecorator;
import net.minecraft.resources.ResourcePackInfo;
import net.minecraftforge.fml.loading.FMLPaths;

import java.io.FileWriter;
import java.nio.file.Paths;

public class ClientSetup {
    // Create internal ResourcePack to hold lang, models
    public static void setupResourcePack() {
        try {
            FileWriter writer = new FileWriter(Paths.get(FMLPaths.CONFIGDIR.get().toAbsolutePath().toString(),WingTech.MODID, "resources", "pack.mcmeta").toFile());
            writer.write("{\"pack\":{\"pack_format\":6,\"description\":\"WingTech resource pack used for lang purposes for the user to add lang for teas.\"}}");
            writer.close();
        }
        catch (Exception e){
            WingTech.LOGGER.error("Error creating pack.mcmeta", e);
        }
        Minecraft.getInstance().getResourcePackRepository().addPackFinder((consumer, factory) -> {
            final ResourcePackInfo packInfo = ResourcePackInfo.create(
                    WingTech.MODID,
                    true,
                    () -> new FolderPack(Paths.get(FMLPaths.CONFIGDIR.get().toAbsolutePath().toString(),WingTech.MODID, "resources").toFile()) {
                        @Override
                        public boolean isHidden() {
                            return true;
                        }
                    },
                    factory,
                    ResourcePackInfo.Priority.TOP,
                    IPackNameDecorator.BUILT_IN
            );
            if (packInfo == null) {
                WingTech.LOGGER.error("Failed to load tea resource pack!");
                return;
            }
            consumer.accept(packInfo);
        });
    }
}
