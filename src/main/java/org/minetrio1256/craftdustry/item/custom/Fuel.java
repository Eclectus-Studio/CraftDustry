package org.minetrio1256.craftdustry.item.custom;

import net.minecraft.world.item.Item;

public class Fuel extends Item{
    private int JouleAmount;

    public Fuel(Properties pProperties, int jouleAmount) {
        super(pProperties);
        this.JouleAmount = jouleAmount;
    }

    public int getJouleAmount(){
        return JouleAmount;
    }
}
