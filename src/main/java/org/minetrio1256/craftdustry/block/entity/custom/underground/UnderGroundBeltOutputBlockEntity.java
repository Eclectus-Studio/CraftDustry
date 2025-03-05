package org.minetrio1256.craftdustry.block.entity.custom.underground;

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
import org.minetrio1256.craftdustry.block.custom.underground.UnderGroundBeltOutput;
import org.minetrio1256.craftdustry.block.entity.modBlockEntities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UnderGroundBeltOutputBlockEntity extends BlockEntity implements IItemTransfer  {
    public final ItemStackHandler itemHandler = new ItemStackHandler(40) {
        @Override
        protected void onContentsChanged(final int slot) {
            UnderGroundBeltOutputBlockEntity.this.setChanged();
            if (!UnderGroundBeltOutputBlockEntity.this.level.isClientSide()) {
                UnderGroundBeltOutputBlockEntity.this.level.sendBlockUpdated(UnderGroundBeltOutputBlockEntity.this.getBlockPos(), UnderGroundBeltOutputBlockEntity.this.getBlockState(), UnderGroundBeltOutputBlockEntity.this.getBlockState(), 3);
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

    private final List<Integer> slotsRowFive = new ArrayList<>(Arrays.asList(40,39,38,37,36,35,34,33));
    private final List<Integer> slotsRowFour = new ArrayList<>(Arrays.asList(32,31,30,29,28,27,26,25));
    private final List<Integer> slotsRowThree = new ArrayList<>(Arrays.asList(24,23,22,21,20,19,18,17));
    private final List<Integer> slotsRowTwo = new ArrayList<>(Arrays.asList(16,15,14,13,12,11,10,9));
    private final List<Integer> slotsRowOne = new ArrayList<>(Arrays.asList(8,7,6,5,4,3,2,1));

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    public UnderGroundBeltOutputBlockEntity(final BlockPos pPos, final BlockState pBlockState) {
        super(modBlockEntities.UNDER_GROUND_OUTPUT_BELT_BE.get(), pPos, pBlockState);
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
            final ItemStack lastItem = this.itemHandler.getStackInSlot(this.itemHandler.getSlots() - 1);
            if (!lastItem.isEmpty()) {
                final BlockPos frontPos = pos.relative(state.getValue(UnderGroundBeltOutput.FACING)); // Get front position
                final BlockEntity frontBlockEntity = level.getBlockEntity(frontPos);

                if (frontBlockEntity instanceof final IItemTransfer transfer && transfer.canInsert(lastItem)) {
                    if (transfer.insertItem(lastItem)) {
                        this.itemHandler.setStackInSlot(this.itemHandler.getSlots() - 1, ItemStack.EMPTY); // Remove from belt
                    }
                }
            }
        }
    }
    
    public boolean insertInRowFive(ItemStack item) {
        if(canInsertInRowFive()){
            for (int i = 0; i < 8; i++) {
                if (this.itemHandler.getStackInSlot(slotsRowFive.get(i)).isEmpty()) {
                    this.itemHandler.setStackInSlot(slotsRowFive.get(i), item.copy());
                    item.setCount(0); // Mark the stack as used up
                    return true;
                }
            }
        }
        return false;
    }

    public boolean insertInRowFour(ItemStack item) {
        if(canInsertInRowFour()){
            for (int i = 0; i < 8; i++) {
                if (this.itemHandler.getStackInSlot(slotsRowFour.get(i)).isEmpty()) {
                    this.itemHandler.setStackInSlot(slotsRowFour.get(i), item.copy());
                    item.setCount(0); // Mark the stack as used up
                    return true;
                }
            }
        }
        return false;
    }
    
    public boolean insertInRowThree(ItemStack item) {
        if(canInsertInRowThree()){
            for (int i = 0; i < 8; i++) {
                if (this.itemHandler.getStackInSlot(slotsRowThree.get(i)).isEmpty()) {
                    this.itemHandler.setStackInSlot(slotsRowThree.get(i), item.copy());
                    item.setCount(0); // Mark the stack as used up
                    return true;
                }
            }
        }
        return false;
    }
    
    public boolean insertInRowTwo(ItemStack item) {
        if(canInsertInRowTwo()){
            for (int i = 0; i < 8; i++) {
                if (this.itemHandler.getStackInSlot(slotsRowTwo.get(i)).isEmpty()) {
                    this.itemHandler.setStackInSlot(slotsRowTwo.get(i), item.copy());
                    item.setCount(0); // Mark the stack as used up
                    return true;
                }
            }
        }
        return false;
    }

    public boolean insertInRowOne(ItemStack item) {
        if(canInsertInRowOne()){
            for (int i = 0; i < 8; i++) {
                if (this.itemHandler.getStackInSlot(slotsRowOne.get(i)).isEmpty()) {
                    this.itemHandler.setStackInSlot(slotsRowOne.get(i), item.copy());
                    item.setCount(0); // Mark the stack as used up
                    return true;
                }
            }
        }
        return false;
    }

    public boolean canInsertInRowFive() {
        // Check if any slot in the itemHandler is empty
        for (int i = 0; i < 8; i++) {
            if (this.itemHandler.getStackInSlot(slotsRowFive.get(i)).isEmpty()) {
                return true;
            }
        }
        return false;
    }

    public boolean canInsertInRowFour() {
        // Check if any slot in the itemHandler is empty
        for (int i = 0; i < 8; i++) {
            if (this.itemHandler.getStackInSlot(slotsRowFour.get(i)).isEmpty()) {
                return true;
            }
        }
        return false;
    }

    public boolean canInsertInRowThree() {
        // Check if any slot in the itemHandler is empty
        for (int i = 0; i < 8; i++) {
            if (this.itemHandler.getStackInSlot(slotsRowThree.get(i)).isEmpty()) {
                return true;
            }
        }
        return false;
    }

    public boolean canInsertInRowTwo() {
        // Check if any slot in the itemHandler is empty
        for (int i = 0; i < 8; i++) {
            if (this.itemHandler.getStackInSlot(slotsRowTwo.get(i)).isEmpty()) {
                return true;
            }
        }
        return false;
    }

    public boolean canInsertInRowOne() {
        // Check if any slot in the itemHandler is empty
        for (int i = 0; i < 8; i++) {
            if (this.itemHandler.getStackInSlot(slotsRowOne.get(i)).isEmpty()) {
                return true;
            }
        }
        return false;
    }

    public List<ItemStack> getItemRowFive() {
        List<ItemStack> items = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            if(!itemHandler.getStackInSlot(slotsRowFive.get(i)).isEmpty()){
                items.add(itemHandler.getStackInSlot(slotsRowFive.get(i)));
            }
        }
        return items;
    }

    public List<ItemStack> getItemRowFour() {
        List<ItemStack> items = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            if(!itemHandler.getStackInSlot(slotsRowFour.get(i)).isEmpty()){
                items.add(itemHandler.getStackInSlot(slotsRowFour.get(i)));
            }
        }
        return items;
    }

    public List<ItemStack> getItemRowThree() {
        List<ItemStack> items = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            if(!itemHandler.getStackInSlot(slotsRowThree.get(i)).isEmpty()){
                items.add(itemHandler.getStackInSlot(slotsRowThree.get(i)));
            }
        }
        return items;
    }

    public List<ItemStack> getItemRowTwo() {
        List<ItemStack> items = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            if(!itemHandler.getStackInSlot(slotsRowTwo.get(i)).isEmpty()){
                items.add(itemHandler.getStackInSlot(slotsRowTwo.get(i)));
            }
        }
        return items;
    }

    public List<ItemStack> getItemRowOne() {
        List<ItemStack> items = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            if(!itemHandler.getStackInSlot(slotsRowOne.get(i)).isEmpty()){
                items.add(itemHandler.getStackInSlot(slotsRowOne.get(i)));
            }
        }
        return items;
    }

    @Override
    public boolean canInsert(final ItemStack stack) {
        // Check if any slot in the itemHandler is empty
        for (int i = 0; i < 8; i++) {
            if (this.itemHandler.getStackInSlot(i).isEmpty()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean insertItem(final ItemStack stack) {
        if (!this.canInsert(stack)) return false;

        for (int i = 0; i < 8; i++) {
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
        for (int i = 0; i < 8; i++) {
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