package org.minetrio1256.craftdustry.api.fluid;

import java.util.HashMap;

public class FluidRegistry {
    private static final HashMap<String, CraftdustryFluid> fluids = new HashMap<>();

    public static void registerFluid(final String id, final CraftdustryFluid fluid) {
        FluidRegistry.fluids.put(id, fluid);
    }

    public static CraftdustryFluid getFluid(final String id) {
        return FluidRegistry.fluids.get(id);
    }

    public static boolean isFluidRegistered(final String id) {
        return FluidRegistry.fluids.containsKey(id);
    }
}

