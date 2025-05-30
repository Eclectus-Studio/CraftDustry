package org.minetrio1256.craftdustry.data.player;

import net.minecraft.resources.ResourceLocation;

import java.util.*;

public class UnlockedCape {
    private static final Map<UUID, Set<ResourceLocation>> unlockedCapes = new HashMap<>();

    public static void unlockCape(UUID uuid, ResourceLocation capeId) {
        unlockedCapes.computeIfAbsent(uuid, k -> new HashSet<>()).add(capeId);
    }

    public static boolean hasCape(UUID uuid, ResourceLocation capeId) {
        return unlockedCapes.getOrDefault(uuid, Set.of()).contains(capeId);
    }

}
