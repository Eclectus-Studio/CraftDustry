package org.minetrio1256.craftdustry.item.custom;

import net.minecraft.world.item.Item;
import org.minetrio1256.craftdustry.api.energy.Joule;

public class RocketFuel extends Item implements Joule {
    private long joules;

    public RocketFuel(Properties pProperties) {
        super(pProperties);
        this.joules = 1000000000L;
    }

    @Override
    public long getJouleAmount() {
        return joules;
    }
}