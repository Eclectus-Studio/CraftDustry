package org.minetrio1256.craftdustry.api.electric;

import org.minetrio1256.craftdustry.api.energy.Watts;

public class ElectricalNetwork {
    private final int id;
    private Watts available;
    private Watts consumed;

    public ElectricalNetwork(int id) {
        this.id = id;
        this.available = new Watts(0);
        this.consumed = new Watts(0);
    }

    public int getId() {
        return id;
    }

    public Watts getAvailable() {
        return available;
    }

    public void setAvailable(Watts available) {
        this.available = available;
    }

    public Watts getConsumed() {
        return consumed;
    }

    public void setConsumed(Watts consumed) {
        this.consumed = consumed;
    }

    public boolean consumePower(Watts amount) {
        if (available.getAmount() >= amount.getAmount()) {
            available = new Watts((int) (available.getAmount() - amount.getAmount()));
            consumed = new Watts((int) (consumed.getAmount() + amount.getAmount()));
            return true;
        }
        return false;
    }

    public void supplyPower(Watts amount) {
        available = new Watts((int) (available.getAmount() + amount.getAmount()));
    }

    public void resetConsumption() {
        consumed = new Watts(0);
    }

    public void merge(ElectricalNetwork other) {
        this.available = new Watts((int) (this.available.getAmount() + other.available.getAmount()));
        this.consumed = new Watts((int) (this.consumed.getAmount() + other.consumed.getAmount()));
        // If you want to track merged IDs, you could add a list of IDs or log them
    }

    @Override
    public String toString() {
        return "ElectricalNetwork{id=" + id +
                ", available=" + available.getAmount() + "W" +
                ", consumed=" + consumed.getAmount() + "W}";
    }
}
