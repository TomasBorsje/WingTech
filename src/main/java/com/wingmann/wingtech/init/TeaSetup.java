package com.wingmann.wingtech.init;

import com.google.gson.Gson;
import com.wingmann.wingtech.WingTech;
import com.wingmann.wingtech.item.DynamicTea;
import com.wingmann.wingtech.item.ModItems;
import com.wingmann.wingtech.registry.TeaRegistry;
import com.wingmann.wingtech.util.TeaData;
import net.minecraft.item.Item;
import net.minecraftforge.fml.loading.FMLPaths;

import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;

import static com.wingmann.wingtech.WingTech.configFolder;
import static com.wingmann.wingtech.WingTech.setup;

public class TeaSetup {
    private static final Gson GSON = new Gson();
    private static final String JSON_FILE_EXTENSION = ".json";

    public static void setupTeas() {
        try {
            FilenameFilter filter = new FilenameFilter() {
                @Override
                public boolean accept(File configFolder, String name) {
                    return name.endsWith(JSON_FILE_EXTENSION);
                }
            };
            File[] teaDataFiles = configFolder.listFiles(filter);
            if (teaDataFiles != null && teaDataFiles.length > 0) {
                for (File teaData : teaDataFiles) {
                    TeaData data = GSON.fromJson(new String(Files.readAllBytes(Paths.get(teaData.getPath())), StandardCharsets.UTF_8), TeaData.class);
                    TeaRegistry.getRegistry().registerTea(data.Type, data);
                }
            }
            WingTech.LOGGER.info("Loaded " + teaDataFiles.length + " custom tea recipes.");
        }
        catch (Exception e){
            WingTech.LOGGER.error("Error loading tea datagen!", e);
        }
    }

    public static void registerTeasItems() {
        for(TeaData data : TeaRegistry.getRegistry().getTeaData().values()) {
            ModItems.ITEMS.register("dynamictea_" + data.Type.toLowerCase(Locale.ENGLISH).replace(' ', '_'),
                    () -> new DynamicTea(new Item.Properties().craftRemainder(ModItems.TEACUP.get()).tab(setup.itemGroup).stacksTo(16), data));
        }
    }

    public static void registerTeaModels() {
        WingTech.LOGGER.info("Begun writing tea models!");
        Path itemModelPath = Paths.get(FMLPaths.CONFIGDIR.get().toAbsolutePath().toString(), WingTech.MODID, "resources", "assets", "wingtech", "models", "item");
        try {
            Files.createDirectories(itemModelPath);
            for (TeaData data : TeaRegistry.getRegistry().getTeaData().values()) {
                FileWriter writer = new FileWriter(Paths.get(itemModelPath.toAbsolutePath().toString(), "dynamictea_" + data.Type.toLowerCase().replace(' ', '_') + ".json").toFile());
                //writer.write("{\"parent\": \"item/generated\", \"textures\": {\"layer0\": \"wingtech:item/tea_layer0\", \"layer1\": \"wingtech:item/tea_layer1\"}}");
                writer.write("{\"parent\": \"wingtech:item/tea\"}");
                writer.close();
                WingTech.LOGGER.info("Wrote one tea model json");
            }
        }
        catch (Exception e){
            WingTech.LOGGER.error("Error creating tea models!", e);
        }
    }

    public static void registerTeaLang() {
        WingTech.LOGGER.info("Begun writing tea lang!");
        Path langPath = Paths.get(FMLPaths.CONFIGDIR.get().toAbsolutePath().toString(), WingTech.MODID, "resources", "assets", "wingtech", "lang");
        try {
            Files.createDirectories(langPath);
            FileWriter writer = new FileWriter(Paths.get(langPath.toAbsolutePath().toString(), "en_us.json").toFile());
            writer.write("{\n");
            for (TeaData data : TeaRegistry.getRegistry().getTeaData().values()) {
                writer.write("item.wingtech.dynamictea_"+data.Type.toLowerCase().replace(' ', '_')+": \""+data.Type +" Tea\",\n");
            }
            writer.write("ui.wingtech.dummy: \"dummy string\"\n");
            writer.write("}");
            writer.close();
        }
        catch (Exception e){
            WingTech.LOGGER.error("Error creating tea lang file!", e);
        }
    }
}
