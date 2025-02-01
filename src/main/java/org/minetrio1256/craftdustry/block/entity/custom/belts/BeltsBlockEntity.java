package org.minetrio1256.craftdustry.block.entity.custom.belts;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import org.minetrio1256.craftdustry.block.custom.belts.Belts;
import org.minetrio1256.craftdustry.block.custom.belts.FastBelts;
import org.minetrio1256.craftdustry.block.entity.modBlockEntities;
import org.minetrio1256.craftdustry.block.modBlocks;

public class BeltsBlockEntity extends BlockEntity implements MenuProvider {
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


    public BeltsBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(modBlockEntities.BELTS_BE.get(), pPos, pBlockState);
    }

    @Override
    public Component getDisplayName() {
        return null;
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return null;
    }
    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == ForgeCapabilities.ITEM_HANDLER) {
            if(side == null) {
                return lazyItemHandler.cast();
            }
        }

        return super.getCapability(cap, side);
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
    public void tick(Level level, BlockPos pPos, BlockState pState) {
        // Get the facing direction from the block state
        Direction facing = pState.getValue(Belts.FACING); // Replace with your block's facing property

        // Define direction offsets based on the facing direction
        int xOffset = 0, yOffset = 0, zOffset = 0;

        // Calculate offsets based on the facing direction
        switch (facing) {
            case NORTH:
                zOffset = -1;
                break;
            case SOUTH:
                zOffset = 1;
                break;
            case EAST:
                xOffset = 1;
                break;
            case WEST:
                xOffset = -1;
                break;
            case UP:
                yOffset = 1;
                break;
            case DOWN:
                yOffset = -1;
                break;
            default:
                // Handle unexpected facing direction
                System.out.println("Invalid facing direction");
                return;
        }

        BlockPos currentPos = getBlockPos();
        BlockPos targetPos = currentPos.offset(xOffset, yOffset, zOffset);

        // Get the block at the target position
        Block targetBlock = level.getBlockState(targetPos).getBlock();

        // Check if the target block is a belt
        if (targetBlock instanceof Belts){
            BlockEntity targetBlockEntity = level.getBlockEntity(targetPos);
            if (targetBlockEntity instanceof FastBeltsBlockEntity) {
                FastBeltsBlockEntity targetBeltsEntity = (FastBeltsBlockEntity) targetBlockEntity;
                ItemStackHandler beltItemHandler = targetBeltsEntity.itemHandler;

                // Loop over each slot in the current belt
                for (int i = 0; i < itemHandler.getSlots(); i++) {
                    if (!itemHandler.getStackInSlot(i).isEmpty()) {
                        // Find the first empty slot in the target belt
                        for (int beltTargetSlot = 0; beltTargetSlot < beltItemHandler.getSlots(); beltTargetSlot++) {
                            if (beltItemHandler.getStackInSlot(beltTargetSlot).isEmpty()) {
                                // Transfer the item to the target belt slot
                                ItemStack itemToTransfer = itemHandler.getStackInSlot(i).copy();
                                beltItemHandler.setStackInSlot(beltTargetSlot, itemToTransfer);
                                itemHandler.setStackInSlot(i, ItemStack.EMPTY);

                                // Stop after transferring one item
                                return;
                            }
                        }
                    }
                }
            }
        } else if (targetBlock instanceof FastBelts) {
            BlockEntity targetBlockEntity = level.getBlockEntity(targetPos);
            if (targetBlockEntity instanceof FastBeltsBlockEntity) {
                FastBeltsBlockEntity targetBeltsEntity = (FastBeltsBlockEntity) targetBlockEntity;
                ItemStackHandler beltItemHandler = targetBeltsEntity.itemHandler;

                // Loop over each slot in the current belt
                for (int i = 0; i < itemHandler.getSlots(); i++) {
                    if (!itemHandler.getStackInSlot(i).isEmpty()) {
                        // Find the first empty slot in the target belt
                        for (int beltTargetSlot = 0; beltTargetSlot < beltItemHandler.getSlots(); beltTargetSlot++) {
                            if (beltItemHandler.getStackInSlot(beltTargetSlot).isEmpty()) {
                                // Transfer the item to the target belt slot
                                ItemStack itemToTransfer = itemHandler.getStackInSlot(i).copy();
                                beltItemHandler.setStackInSlot(beltTargetSlot, itemToTransfer);
                                itemHandler.setStackInSlot(i, ItemStack.EMPTY);

                                // Stop after transferring one item
                                return;
                            }
                        }
                    }
                }
            }
        }
    }
}