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
import org.minetrio1256.craftdustry.block.custom.underground.UnderGroundBeltInput;
import org.minetrio1256.craftdustry.block.entity.modBlockEntities;

public class UnderGroundBeltInputBlockEntity extends BlockEntity {
    public final ItemStackHandler itemHandler = new ItemStackHandler(8) {
        @Override
        protected void onContentsChanged(final int slot) {
            UnderGroundBeltInputBlockEntity.this.setChanged();
            if (!UnderGroundBeltInputBlockEntity.this.level.isClientSide()) {
                UnderGroundBeltInputBlockEntity.this.level.sendBlockUpdated(UnderGroundBeltInputBlockEntity.this.getBlockPos(), UnderGroundBeltInputBlockEntity.this.getBlockState(), UnderGroundBeltInputBlockEntity.this.getBlockState(), 3);
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


    public UnderGroundBeltInputBlockEntity(final BlockPos pPos, final BlockState pBlockState) {
        super(modBlockEntities.UNDER_GROUND_INPUT_BELT_BE.get(), pPos, pBlockState);
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
        BlockPos inFront = pos.relative(state.getValue(UnderGroundBeltInput.FACING));
        BlockPos inFrontTwo = inFront.relative(state.getValue(UnderGroundBeltInput.FACING));
        BlockPos inFrontThree = inFrontTwo.relative(state.getValue(UnderGroundBeltInput.FACING));
        BlockPos inFrontFour = inFrontThree.relative(state.getValue(UnderGroundBeltInput.FACING));
        BlockPos inFrontFive = inFrontFour.relative(state.getValue(UnderGroundBeltInput.FACING));

        BlockEntity blockInFront = level.getBlockEntity(inFront);
        BlockEntity blockInFrontTwo = level.getBlockEntity(inFrontTwo);
        BlockEntity blockInFrontThree = level.getBlockEntity(inFrontThree);
        BlockEntity blockInFrontFour = level.getBlockEntity(inFrontFour);
        BlockEntity blockInFrontFive = level.getBlockEntity(inFrontFive);

        for (int i = this.itemHandler.getSlots() - 1; 0 < i; i--) {
            final ItemStack current = this.itemHandler.getStackInSlot(i);
            final ItemStack previous = this.itemHandler.getStackInSlot(i - 1);

            if (current.isEmpty() && !previous.isEmpty()) {
                this.itemHandler.setStackInSlot(i, previous.copy()); // Move item forward
                this.itemHandler.setStackInSlot(i - 1, ItemStack.EMPTY); // Clear previous slot
            }
        }

        if (blockInFront instanceof UnderGroundBeltOutputBlockEntity) {
            UnderGroundBeltOutputBlockEntity underGroundBeltOutputBlockEntity = (UnderGroundBeltOutputBlockEntity) blockInFront;

            final ItemStack lastItem = this.itemHandler.getStackInSlot(0);
            if (!lastItem.isEmpty()) {
                if(underGroundBeltOutputBlockEntity.insertInRowOne(lastItem)){
                    this.itemHandler.setStackInSlot(this.itemHandler.getSlots() - 1, ItemStack.EMPTY); // Remove from belt
                }
            }
        } else if (blockInFrontTwo instanceof UnderGroundBeltOutputBlockEntity){
            UnderGroundBeltOutputBlockEntity underGroundBeltOutputBlockEntity = (UnderGroundBeltOutputBlockEntity) blockInFrontTwo;

            final ItemStack lastItem = this.itemHandler.getStackInSlot(0);
            if (!lastItem.isEmpty()) {
                if(underGroundBeltOutputBlockEntity.insertInRowOne(lastItem)){
                    this.itemHandler.setStackInSlot(this.itemHandler.getSlots() - 1, ItemStack.EMPTY); // Remove from belt
                }
            }
        } else if (blockInFrontThree instanceof UnderGroundBeltOutputBlockEntity){
            UnderGroundBeltOutputBlockEntity underGroundBeltOutputBlockEntity = (UnderGroundBeltOutputBlockEntity) blockInFrontThree;

            final ItemStack lastItem = this.itemHandler.getStackInSlot(0);
            if (!lastItem.isEmpty()) {
                if(underGroundBeltOutputBlockEntity.insertInRowOne(lastItem)){
                    this.itemHandler.setStackInSlot(this.itemHandler.getSlots() - 1, ItemStack.EMPTY); // Remove from belt
                }
            }
        } else if (blockInFrontFour instanceof UnderGroundBeltOutputBlockEntity){
            UnderGroundBeltOutputBlockEntity underGroundBeltOutputBlockEntity = (UnderGroundBeltOutputBlockEntity) blockInFrontFour;

            final ItemStack lastItem = this.itemHandler.getStackInSlot(0);
            if (!lastItem.isEmpty()) {
                if(underGroundBeltOutputBlockEntity.insertInRowOne(lastItem)){
                    this.itemHandler.setStackInSlot(this.itemHandler.getSlots() - 1, ItemStack.EMPTY); // Remove from belt
                }
            }
        } else if (blockInFrontFive instanceof UnderGroundBeltOutputBlockEntity){
            UnderGroundBeltOutputBlockEntity underGroundBeltOutputBlockEntity = (UnderGroundBeltOutputBlockEntity) blockInFrontFive;

            final ItemStack lastItem = this.itemHandler.getStackInSlot(0);
            if (!lastItem.isEmpty()) {
                if(underGroundBeltOutputBlockEntity.insertInRowOne(lastItem)){
                    this.itemHandler.setStackInSlot(this.itemHandler.getSlots() - 1, ItemStack.EMPTY); // Remove from belt
                }
            }
        }
    }
}
