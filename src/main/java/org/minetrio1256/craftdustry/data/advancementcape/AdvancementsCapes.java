package org.minetrio1256.craftdustry.data.advancementcape;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.HashMap;
import java.util.Map;

public class AdvancementsCapes extends SimpleJsonResourceReloadListener {
    public static final AdvancementsCapes INSTANCE = new AdvancementsCapes();
    private static final String DIRECTORY = "advancementcapes";
    private final Map<ResourceLocation, AdvancementCape> map = new HashMap<>();

    public AdvancementsCapes() {
        super(new com.google.gson.GsonBuilder().create(), DIRECTORY);
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> jsons, ResourceManager manager, ProfilerFiller profiler) {
        map.clear();
        for (Map.Entry<ResourceLocation, JsonElement> entry : jsons.entrySet()) {
            try {
                JsonObject json = entry.getValue().getAsJsonObject();
                AdvancementCape ac = AdvancementCapeSerializer.fromJson(json);
                map.put(entry.getKey(), ac);
            } catch (Exception e) {
                System.err.println("Failed to load advancement cape: " + e.getMessage());
            }
        }
    }

    public Map<ResourceLocation, AdvancementCape> getAll() {
        return map;
    }
}
