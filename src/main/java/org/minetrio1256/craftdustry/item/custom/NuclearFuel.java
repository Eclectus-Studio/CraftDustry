package org.minetrio1256.craftdustry.item.custom;

import net.minecraft.world.item.Item;
import org.minetrio1256.craftdustry.api.energy.Joule;

public class NuclearFuel extends Item implements Joule {
    private long joule;

    public NuclearFuel(Properties properties) {
        super(properties);
        this.joule = 1210000000;
    }

    @Override
    public long getJouleAmount() {
        return joule;
    }
}
