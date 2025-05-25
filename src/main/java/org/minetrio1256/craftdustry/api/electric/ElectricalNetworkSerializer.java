package org.minetrio1256.craftdustry.api.electric;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import net.minecraft.server.level.ServerLevel;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class ElectricalNetworkSerializer {
    private static final Gson GSON = new Gson();
    private static Path saveDir = null;

    private static Map<Integer, ElectricalNetwork> networks = new HashMap<>();

    public static void init(Path worldSaveDirectory) {
        saveDir = worldSaveDirectory.resolve("craftdustry");
        Path filePath = saveDir.resolve("electrical_networks.json");       // Replace with your file name

        try {
            // Create the folder if it doesn't exist
            if (!Files.exists(saveDir)) {
                Files.createDirectories(saveDir);
                System.out.println("Created folder: " + saveDir);
            }

            // Create the file if it doesn't exist
            if (!Files.exists(filePath)) {
                Files.createFile(filePath);
                System.out.println("Created file: " + filePath);
            } else {
                System.out.println("File already exists: " + filePath);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadFromSerializer() {
        if (saveDir == null) {
            throw new IllegalStateException("ElectricalNetworkSerializer was not initialized with a path!");
        }

        File file = saveDir.resolve("electrical_networks.json").toFile();
        networks.clear();

        if (!file.exists()) return;

        try (FileReader reader = new FileReader(file)) {
            JsonArray array = GSON.fromJson(reader, JsonArray.class);
            for (int i = 0; i < array.size(); i++) {
                int id = array.get(i).getAsInt();
                networks.put(id, new ElectricalNetwork(id));
            }
            System.out.println("[Craftdustry] Loaded " + networks.size() + " electrical networks");
        } catch (Exception e) {
            System.err.println("[Craftdustry] Failed to load networks: " + e.getMessage());
        }
    }

    public static void saveToSerializer() {
        if (saveDir == null) {
            throw new IllegalStateException("ElectricalNetworkSerializer was not initialized with a path!");
        }

        File file = saveDir.resolve("electrical_networks.json").toFile();

        try (FileWriter writer = new FileWriter(file)) {
            JsonArray array = new JsonArray();
            for (Integer id : networks.keySet()) {
                array.add(id);
            }
            GSON.toJson(array, writer);
            System.out.println("[Craftdustry] Saved " + networks.size() + " electrical networks");
        } catch (Exception e) {
            System.err.println("[Craftdustry] Failed to save networks: " + e.getMessage());
        }
    }

    public static void register(ElectricalNetwork net) {
        networks.put(net.getId(), net);
    }

    public static Map<Integer, ElectricalNetwork> getNetworks() {
        return networks;
    }

    public static void setNetworks(Map<Integer, ElectricalNetwork> networks) {
        ElectricalNetworkSerializer.networks = networks;
    }
}
