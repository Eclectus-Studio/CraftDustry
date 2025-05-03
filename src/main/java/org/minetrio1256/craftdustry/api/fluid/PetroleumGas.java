package org.minetrio1256.craftdustry.api.fluid;

import org.minetrio1256.craftdustry.api.energy.Temperature;

public class PetroleumGas extends CraftdustryFluid{
    public PetroleumGas() {
        super("petroleum", new Temperature(15));
    }
}
