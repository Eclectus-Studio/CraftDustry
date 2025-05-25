package org.minetrio1256.craftdustry.api.electric;

import org.minetrio1256.craftdustry.api.energy.Watts;

public interface ElectricConsumer {
    public Watts getConsumption();
    public void run();
    public void run(Watts watts);
}
