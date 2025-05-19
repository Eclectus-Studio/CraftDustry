package org.minetrio1256.craftdustry.api.energy;

public class Watts {
    private long amount;

    public Watts(int quantity){
        this.amount = quantity;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }
}
