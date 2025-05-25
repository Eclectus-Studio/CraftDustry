package org.minetrio1256.craftdustry.api.machine;

public class Durability {
    private int max;
    private int current;

    public Durability (int maximumDurability){
        this.max = maximumDurability;
        this.current = maximumDurability;
    }

    public int getCurrent() {
        return current;
    }

    public int getMax() {
        return max;
    }

    public void addDurability(){
        current++;
    }

    public void addDurability(int amount){
        int i = current + amount;
        current = i;
    }
}
