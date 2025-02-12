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
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.minetrio1256.craftdustry.block.custom.belts.Belts;
import org.minetrio1256.craftdustry.block.entity.modBlockEntities;

public class ExpressBeltsBlockEntity extends BlockEntity{
    public final ItemStackHandler itemHandler = new ItemStackHandler(8) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if (!level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }

        @Override
        public int getSlotLimit(int slot) {
            // Limit the stack size to 1 for each slot
            return 1;
        }

        @Override
        public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
            // Only allow inserting a single item at a time
            if (stack.getCount() > 1) {
                ItemStack singleItemStack = stack.copy();
                singleItemStack.setCount(1);
                ItemStack remaining = stack.copy();
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


    public ExpressBeltsBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(modBlockEntities.EXPRESS_BELTS_BE.get(), pPos, pBlockState);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        pTag.put("inventory", itemHandler.serializeNBT(pRegistries));

        super.saveAdditional(pTag, pRegistries);
    }

    @Override
    protected void loadAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        super.loadAdditional(pTag, pRegistries);

        itemHandler.deserializeNBT(pRegistries, pTag.getCompound("inventory"));
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    public void tick(Level level, BlockPos pos, BlockState state) {
        if (level.isClientSide()) return;

        for (int i = itemHandler.getSlots() - 1; i > 0; i--) {
            ItemStack current = itemHandler.getStackInSlot(i);
            ItemStack previous = itemHandler.getStackInSlot(i - 1);

            if (current.isEmpty() && !previous.isEmpty()) {
                itemHandler.setStackInSlot(i, previous.copy());
                itemHandler.setStackInSlot(i - 1, ItemStack.EMPTY);
            }
        }

        ItemStack lastItem = itemHandler.getStackInSlot(itemHandler.getSlots() - 1);
        if (!lastItem.isEmpty()) {
            BlockPos frontPos = pos.relative(state.getValue(Belts.FACING));
            BlockEntity frontBlockEntity = level.getBlockEntity(frontPos);

            if (frontBlockEntity != null) {
                frontBlockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(handler -> {
                    for (int i = 0; i < handler.getSlots(); i++) {
                        if (handler.insertItem(i, lastItem, true).isEmpty()) {
                            handler.insertItem(i, lastItem.copy(), false);
                            itemHandler.setStackInSlot(itemHandler.getSlots() - 1, ItemStack.EMPTY);
                            break;
                        }
                    }
                });
            }
        }
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> capability) {
        if (capability == ForgeCapabilities.ITEM_HANDLER) {
            return lazyItemHandler.cast();
        }
        return super.getCapability(capability);
    }
}