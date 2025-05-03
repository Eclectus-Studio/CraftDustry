package org.minetrio1256.craftdustry.api.fluid;

import org.minetrio1256.craftdustry.api.energy.Temperature;

public class Water extends CraftdustryFluid{
    public Water() {
        super("water", new Temperature(15));
    }
}
