package org.minetrio1256.craftdustry.block.entity.custom.belts;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.minetrio1256.craftdustry.api.item.IItemTransfer;
import org.minetrio1256.craftdustry.block.custom.belts.TurboBelts;
import org.minetrio1256.craftdustry.block.entity.modBlockEntities;

public class TurboBeltsBlockEntity extends BlockEntity implements IItemTransfer {
    public final ItemStackHandler itemHandler = new ItemStackHandler(8) {
        @Override
        protected void onContentsChanged(final int slot) {
            TurboBeltsBlockEntity.this.setChanged();
            if (!TurboBeltsBlockEntity.this.level.isClientSide()) {
                TurboBeltsBlockEntity.this.level.sendBlockUpdated(TurboBeltsBlockEntity.this.getBlockPos(), TurboBeltsBlockEntity.this.getBlockState(), TurboBeltsBlockEntity.this.getBlockState(), 3);
            }
        }

        @Override
        public int getSlotLimit(final int slot) {
            // Limit the stack size to 1 for each slot
            return 1;
        }

        @Override
        public ItemStack insertItem(final int slot, final ItemStack stack, final boolean simulate) {
            // Only allow inserting a single item at a time
            if (1 < stack.getCount()) {
                final ItemStack singleItemStack = stack.copy();
                singleItemStack.setCount(1);
                final ItemStack remaining = stack.copy();
                remaining.setCount(stack.getCount() - 1);

                if (!simulate) {
                    super.insertItem(slot, singleItemStack, false);
                }
                return remaining;
            }
            return super.insertItem(slot, stack, simulate);
        }
    };

    private final int slot_1 = 0;
    private final int slot_2 = 1;
    private final int slot_3 = 2;
    private final int slot_4 = 3;
    private final int slot_5 = 4;
    private final int slot_6 = 5;
    private final int slot_7 = 6;
    private final int slot_8 = 7;

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();


    public TurboBeltsBlockEntity(final BlockPos pPos, final BlockState pBlockState) {
        super(modBlockEntities.TURBO_BELTS_BE.get(), pPos, pBlockState);
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
        pTag.put("inventory", this.itemHandler.serializeNBT(pRegistries));

        super.saveAdditional(pTag, pRegistries);
    }

    @Override
    protected void loadAdditional(final CompoundTag pTag, final HolderLookup.Provider pRegistries) {
        super.loadAdditional(pTag, pRegistries);

        this.itemHandler.deserializeNBT(pRegistries, pTag.getCompound("inventory"));
    }

    public void drops() {
        final SimpleContainer inventory = new SimpleContainer(this.itemHandler.getSlots());
        for (int i = 0; i < this.itemHandler.getSlots(); i++) {
            inventory.setItem(i, this.itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(level, worldPosition, inventory);
    }

    public void tick(final Level level, final BlockPos pos, final BlockState state) {
        if (level.isClientSide()) return;

        for (int step = 0; 4 > step; step++) { // Run 4 times per tick
            // Move items forward in the belt itemHandler
            for (int i = this.itemHandler.getSlots() - 1; 0 < i; i--) {
                final ItemStack current = this.itemHandler.getStackInSlot(i);
                final ItemStack previous = this.itemHandler.getStackInSlot(i - 1);

                if (current.isEmpty() && !previous.isEmpty()) {
                    this.itemHandler.setStackInSlot(i, previous.copy()); // Move item forward
                    this.itemHandler.setStackInSlot(i - 1, ItemStack.EMPTY); // Clear previous slot
                }
            }

            // Handle last slot (drop item if necessary)
            final ItemStack lastItem = this.itemHandler.getStackInSlot(0);
            if (!lastItem.isEmpty()) {
                final BlockPos frontPos = pos.relative(state.getValue(TurboBelts.FACING)); // Get front position
                final BlockEntity frontBlockEntity = level.getBlockEntity(frontPos);

                if (frontBlockEntity instanceof final IItemTransfer transfer && transfer.canInsert(lastItem)) {
                    if (transfer.insertItem(lastItem)) {
                        this.itemHandler.setStackInSlot(this.itemHandler.getSlots() - 1, ItemStack.EMPTY); // Remove from belt
                    }
                }
            }
        }
    }

    @Override
    public boolean canInsert(final ItemStack stack) {
        // Check if any slot in the itemHandler is empty
        for (int i = 0; i < this.itemHandler.getSlots(); i++) {
            if (this.itemHandler.getStackInSlot(i).isEmpty()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean insertItem(final ItemStack stack) {
        if (!this.canInsert(stack)) return false;

        for (int i = 0; i < this.itemHandler.getSlots(); i++) {
            if (this.itemHandler.getStackInSlot(i).isEmpty()) {
                this.itemHandler.setStackInSlot(i, stack.copy());
                stack.setCount(0); // Mark the stack as used up
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean canExtract(final ItemStack stack) {
        // Allow extraction if any slot contains an item
        for (int i = 0; i < this.itemHandler.getSlots(); i++) {
            if (!this.itemHandler.getStackInSlot(i).isEmpty()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public ItemStack extractItem() {
        for (int i = 0; i < this.itemHandler.getSlots(); i++) {
            final ItemStack stack = this.itemHandler.getStackInSlot(i);
            if (!stack.isEmpty()) {
                this.itemHandler.setStackInSlot(i, ItemStack.EMPTY);
                return stack;
            }
        }
        return ItemStack.EMPTY;
    }

    @Override
    public boolean isValid() {
        return false;
    }
}