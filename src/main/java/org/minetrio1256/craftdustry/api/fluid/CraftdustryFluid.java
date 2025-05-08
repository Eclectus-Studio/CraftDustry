package org.minetrio1256.craftdustry.api.fluid;

import net.minecraft.client.resources.PlayerSkin;
import org.minetrio1256.craftdustry.api.energy.Temperature;

public class CraftdustryFluid {
    private final String fluidType;
    private final Temperature temperature;

    public CraftdustryFluid(final String fluidType, Temperature temperature) {
        this.fluidType = fluidType;
        this.temperature = temperature;
    }

    public String getFluidType() {
        return this.fluidType;
    }

    public Temperature getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature){
        this.temperature.setTemperature(temperature);
    }
}

