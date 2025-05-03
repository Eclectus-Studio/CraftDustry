package org.minetrio1256.craftdustry.api.fluid;

import org.minetrio1256.craftdustry.api.energy.Temperature;

public class Oil extends CraftdustryFluid{
    public Oil() {
        super("oil", new Temperature(15));
    }
}
