package org.minetrio1256.craftdustry.data.cape;

import com.google.gson.JsonObject;

public class CapeSerializer {

    public static Cape fromJson(JsonObject json) {
        String name = json.get("name").getAsString();
        String texture = json.get("texture").getAsString();
        return new Cape(name, texture);
    }
}
