package org.minetrio1256.craftdustry.screen.chests.wooden_chest;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.SlotItemHandler;
import org.minetrio1256.craftdustry.block.entity.custom.chests.WoodenChestBE;
import org.minetrio1256.craftdustry.screen.ModMenuTypes;

public class WoodenChestMenu extends AbstractContainerMenu {
    public final WoodenChestBE woodenChestBe;

    public WoodenChestMenu(final int pContainerId, final Inventory inventory, final FriendlyByteBuf extraData) {
        this(pContainerId, inventory, inventory.player.level().getBlockEntity(extraData.readBlockPos()));
    }

    public WoodenChestMenu(final int pContainerId, final Inventory inv, final BlockEntity blockEntity) {
        super(ModMenuTypes.WOODEN_CHEST_MENU.get(), pContainerId);
        this.woodenChestBe = ((WoodenChestBE) blockEntity);

        this.addPlayerInventory(inv);
        this.addPlayerHotbar(inv);

        woodenChestBe.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(itemHandler -> {
            addSlot(new SlotItemHandler(itemHandler, 0, 8, 18));
            addSlot(new SlotItemHandler(itemHandler, 1, 26, 18));
            addSlot(new SlotItemHandler(itemHandler, 2, 44, 18));
            addSlot(new SlotItemHandler(itemHandler, 3, 62, 18));
            addSlot(new SlotItemHandler(itemHandler, 4, 80, 18));
            addSlot(new SlotItemHandler(itemHandler, 5, 98, 18));
            addSlot(new SlotItemHandler(itemHandler, 6, 116, 18));
            addSlot(new SlotItemHandler(itemHandler, 7, 134, 18));
            addSlot(new SlotItemHandler(itemHandler, 8, 152, 18));
            addSlot(new SlotItemHandler(itemHandler, 9, 8, 36));
            addSlot(new SlotItemHandler(itemHandler, 10, 26, 36));
            addSlot(new SlotItemHandler(itemHandler, 11, 44, 36));
            addSlot(new SlotItemHandler(itemHandler, 12, 62, 36));
            addSlot(new SlotItemHandler(itemHandler, 13, 80, 36));
            addSlot(new SlotItemHandler(itemHandler, 14, 98, 36));
            addSlot(new SlotItemHandler(itemHandler, 15, 116, 36));
        });
    }

    // CREDIT GOES TO: diesieben07 | https://github.com/diesieben07/SevenCommons
    // must assign a slot number to each of the slots used by the GUI.
    // For this container, we can see both the tile inventory's slots as well as the player inventory slots and the hotbar.
    // Each time we add a Slot to the container, it automatically increases the slotIndex, which means
    //  0 - 8 = hotbar slots (which will map to the InventoryPlayer slot numbers 0 - 8)
    //  9 - 35 = player inventory slots (which map to the InventoryPlayer slot numbers 9 - 35)
    //  36 - 44 = TileInventory slots, which map to our TileEntity slot numbers 0 - 8)
    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = WoodenChestMenu.PLAYER_INVENTORY_COLUMN_COUNT * WoodenChestMenu.PLAYER_INVENTORY_ROW_COUNT;
    private static final int VANILLA_SLOT_COUNT = WoodenChestMenu.HOTBAR_SLOT_COUNT + WoodenChestMenu.PLAYER_INVENTORY_SLOT_COUNT;
    private static final int VANILLA_FIRST_SLOT_INDEX = 0;
    private static final int TE_INVENTORY_FIRST_SLOT_INDEX = WoodenChestMenu.VANILLA_FIRST_SLOT_INDEX + WoodenChestMenu.VANILLA_SLOT_COUNT;

    // THIS YOU HAVE TO DEFINE!
    private static final int TE_INVENTORY_SLOT_COUNT = 16;
    @Override
    public ItemStack quickMoveStack(final Player playerIn, final int pIndex) {
        final Slot sourceSlot = this.slots.get(pIndex);
        if (null == sourceSlot || !sourceSlot.hasItem()) return ItemStack.EMPTY;  //EMPTY_ITEM
        final ItemStack sourceStack = sourceSlot.getItem();
        final ItemStack copyOfSourceStack = sourceStack.copy();

        // Check if the slot clicked is one of the vanilla container slots
        if (VANILLA_FIRST_SLOT_INDEX + WoodenChestMenu.VANILLA_SLOT_COUNT > pIndex) {
            // This is a vanilla container slot so merge the stack into the tile inventory
            if (!this.moveItemStackTo(sourceStack, WoodenChestMenu.TE_INVENTORY_FIRST_SLOT_INDEX, WoodenChestMenu.TE_INVENTORY_FIRST_SLOT_INDEX
                    + WoodenChestMenu.TE_INVENTORY_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;  // EMPTY_ITEM
            }
        } else if (TE_INVENTORY_FIRST_SLOT_INDEX + WoodenChestMenu.TE_INVENTORY_SLOT_COUNT > pIndex) {
            // This is a TE slot so merge the stack into the players inventory
            if (!this.moveItemStackTo(sourceStack, WoodenChestMenu.VANILLA_FIRST_SLOT_INDEX, WoodenChestMenu.VANILLA_FIRST_SLOT_INDEX + WoodenChestMenu.VANILLA_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            System.out.println("Invalid slotIndex:" + pIndex);
            return ItemStack.EMPTY;
        }
        // If stack size == 0 (the entire stack was moved) set slot contents to null
        if (0 == sourceStack.getCount()) {
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }
        sourceSlot.onTake(playerIn, sourceStack);
        return copyOfSourceStack;
    }

    @Override
    public boolean stillValid(final Player player) {
        return true;
    }

    private void addPlayerInventory(final Inventory playerInventory) {
        for (int i = 0; 3 > i; ++i) {
            for (int l = 0; 9 > l; ++l) {
                addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 84 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(final Inventory playerInventory) {
        for (int i = 0; 9 > i; ++i) {
            addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }
}
