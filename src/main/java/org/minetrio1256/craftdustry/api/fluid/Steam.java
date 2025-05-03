package org.minetrio1256.craftdustry.api.fluid;

import org.minetrio1256.craftdustry.api.energy.Temperature;

public class Steam extends CraftdustryFluid{
    public Steam(Temperature temperature) {
        super("steam", temperature);
    }
}
