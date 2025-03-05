package org.minetrio1256.craftdustry.block.entity.custom.chests;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.minetrio1256.craftdustry.block.entity.modBlockEntities;
import org.minetrio1256.craftdustry.screen.chests.iron_chest.IronChestMenu;

public class IronChestBlockEntity extends BlockEntity implements MenuProvider {
    private final ItemStackHandler itemHandler = new ItemStackHandler(32) {
        @Override
        protected void onContentsChanged(final int slot) {
            IronChestBlockEntity.this.setChanged();
            if (!IronChestBlockEntity.this.level.isClientSide()) {
                IronChestBlockEntity.this.level.sendBlockUpdated(IronChestBlockEntity.this.getBlockPos(), IronChestBlockEntity.this.getBlockState(), IronChestBlockEntity.this.getBlockState(), 3);
            }
        }
    };

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.of(() -> this.itemHandler);

    public IronChestBlockEntity(final BlockPos pPos, final BlockState pBlockState) {
        super(modBlockEntities.IRON_CHEST_BE.get(), pPos, pBlockState);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        this.lazyItemHandler = LazyOptional.of(() -> this.itemHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        this.lazyItemHandler.invalidate();
    }

    @Override
    protected void saveAdditional(final CompoundTag pTag, final HolderLookup.Provider pRegistries) {
        pTag.put("itemHandler", this.itemHandler.serializeNBT(pRegistries));
        super.saveAdditional(pTag, pRegistries);
    }

    @Override
    protected void loadAdditional(final CompoundTag pTag, final HolderLookup.Provider pRegistries) {
        super.loadAdditional(pTag, pRegistries);
        this.itemHandler.deserializeNBT(pRegistries, pTag.getCompound("itemHandler")); // Fix: Corrected key name
    }

    public void drops() {
        final SimpleContainer inventory = new SimpleContainer(this.itemHandler.getSlots());
        for (int i = 0; i < this.itemHandler.getSlots(); i++) {
            inventory.setItem(i, this.itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(level, worldPosition, inventory);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("name.craftdustry.wooden_chest");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(final int i, final Inventory inventory, final Player player) {
        return new IronChestMenu(i, inventory, this);
    }

    @Override
    public <T> @NotNull LazyOptional<T> getCapability(@NotNull final Capability<T> cap) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return this.lazyItemHandler.cast();
        }
        return super.getCapability(cap);
    }
}