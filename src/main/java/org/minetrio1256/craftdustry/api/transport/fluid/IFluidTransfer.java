package org.minetrio1256.craftdustry.api.transport.fluid;

import net.minecraft.world.item.ItemStack;
import org.minetrio1256.craftdustry.api.fluid.CraftdustryFluid;
import org.minetrio1256.craftdustry.api.fluid.FluidStack;

public interface IFluidTransfer {
    boolean canInsertFluid(CraftdustryFluid fluid);
    boolean insertFluid(CraftdustryFluid fluid);
    boolean canExtract(CraftdustryFluid fluid);
    FluidStack extractFluid();
    boolean isValid();
}
