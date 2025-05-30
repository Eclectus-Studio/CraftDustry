package org.minetrio1256.craftdustry.data.advancementcape;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;

public class AdvancementCapeSerializer {
    public static AdvancementCape fromJson(JsonObject json) {
        ResourceLocation advancement = ResourceLocation.parse(json.get("advancement").getAsString());
        ResourceLocation cape = ResourceLocation.parse(json.get("cape").getAsString());
        return new AdvancementCape(advancement, cape);
    }
}
