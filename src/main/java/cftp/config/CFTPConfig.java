package cftp.config;

import cftp.CFTP;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;

public class CFTPConfig {

    private static final Path configDir = FabricLoader.getInstance().getConfigDir();
    private static final Path configPath = configDir.resolve("cftp/config.json");
    private static final Path outtedPath = configDir.resolve("cftp/old_config.json");

    public static boolean isNotValid() {
        return CFTPData.configVersion != CFTP.currentConfigVersion; // checkConfigVersion
    }

    public static void load(Path path, Class<?> clazz) throws IOException, IllegalAccessException {
        Gson gson = new Gson();

        JsonObject json = gson.fromJson(Files.readString(path), JsonObject.class);

        if (json == null) {
            CFTP.LOGGER.info("Config file is empty! Delete it and restart minecraft!");
            throw new IOException();
        }

        for (Field field : clazz.getDeclaredFields()) {
            if (!java.lang.reflect.Modifier.isStatic(field.getModifiers()))
                continue;

            JsonElement element = json.get(field.getName());
            if (element == null) continue;

            Object value = gson.fromJson(element, field.getType());
            field.setAccessible(true);
            field.set(null, value);
        }
    }

    public static void save(Path path, Class<?> clazz) throws IllegalAccessException, IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonObject json = new JsonObject();

        for (Field field : clazz.getDeclaredFields()) {
            if (!java.lang.reflect.Modifier.isStatic(field.getModifiers()))
                continue;

            field.setAccessible(true);
            Object value = field.get(null);
            json.add(field.getName(), gson.toJsonTree(value));
        }

        //Files.writeString(path, gson.toJson(json));
        Files.writeString(path, gson.toJson(json), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }

    public static void create() {
        try {
            Files.createDirectories(configPath.getParent());

            CFTPData.initializeDefault();
            save(configPath, CFTPData.class);
        } catch (IOException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static void copy() {
        try {
            Files.copy(configPath, outtedPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void createOrAndLoad(boolean recreate) {

        if (Files.notExists(configPath) || recreate) {
            CFTP.LOGGER.warn("Config was not found. Creating a config file.");
            create();
        }

        try {
            load(configPath, CFTPData.class);
        } catch (Exception ignored) {}

        if (isNotValid()) {
            CFTP.LOGGER.warn("Config file was invalid. New config file created instead. Old file renamed to old_...!");
            copy();
            create();
        }

    }

}
