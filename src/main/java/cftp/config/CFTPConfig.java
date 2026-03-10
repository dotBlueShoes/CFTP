package cftp.config;

import cftp.CFTP;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;

public class CFTPConfig {

    private static final Path configDir = FabricLoader.getInstance().getConfigDir();
    private static final Path path = configDir.resolve("cftp/config.json");

    public static void validate() {
        CFTPData.checkConfigVersion();
    }

    public static void load(Path path, Class<?> clazz) throws IOException, IllegalAccessException {
        Gson gson = new Gson();

        JsonObject json = gson.fromJson(Files.readString(path), JsonObject.class);

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

        Files.writeString(path, gson.toJson(json));
    }

    public static void create() {
        try {
            Files.createDirectories(path.getParent());
            Files.createFile(path);

            CFTPData.initializeDefault();
            save(path, CFTPData.class);
        } catch (IOException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static void createOrAndLoad() {

        if (Files.notExists(path)) {
            create();
        }

        try {
            load(path, CFTPData.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        validate();

    }

}
