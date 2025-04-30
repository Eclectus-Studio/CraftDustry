package org.minetrio1256.craftdustry.item.custom;

import net.minecraft.world.item.Item;
import org.minetrio1256.craftdustry.api.energy.Joule;

public class Wood extends Item implements Joule  {
    private long joules;

    public Wood(Properties properties) {
        super(properties);
        this.joules = 20000000;
    }
    @Override
    public long getJouleAmount() {
        return joules;
    }
}
