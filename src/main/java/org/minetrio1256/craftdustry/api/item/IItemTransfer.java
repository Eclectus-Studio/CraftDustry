package org.minetrio1256.craftdustry.api.item;

import net.minecraft.world.item.ItemStack;

public interface IItemTransfer {
    boolean canInsert(ItemStack stack);
    boolean insertItem(ItemStack stack);
    boolean canExtract(ItemStack stack);
    ItemStack extractItem();
    boolean isValid();
}

