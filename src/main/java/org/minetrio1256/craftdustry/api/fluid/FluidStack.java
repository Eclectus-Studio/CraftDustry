package org.minetrio1256.craftdustry.api.fluid;

public class FluidStack {
    private CraftdustryFluid fluid;
    private int quantity;

    public FluidStack(CraftdustryFluid craftdustryFluid, int amount){
        this.fluid = craftdustryFluid;
        this.quantity = amount;
    }

    public CraftdustryFluid getFluid() {
        return fluid;
    }

    public void setFluid(CraftdustryFluid fluid) {
        this.fluid = fluid;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
