package org.minetrio1256.craftdustry.item.custom;

import net.minecraft.world.item.Item;
import org.minetrio1256.craftdustry.api.energy.Joule;

public class Coal extends Item implements Joule {
    private long joules;

    public Coal(Properties pProperties) {
        super(pProperties);
        this.joules = 4000000L;
    }

    @Override
    public long getJouleAmount() {
        return joules;
    }
}
