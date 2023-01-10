package daripher.noita.wands.menu;

import daripher.noita.wands.init.MenuInit;
import daripher.noita.wands.item.WandItem;
import daripher.noita.wands.menu.util.SlotZone;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class WandMenu extends AbstractContainerMenu {
	private final SlotZone wholeInventorySlotZone = new SlotZone(0, 36);
	private final SlotZone upperInventorySlotZone = new SlotZone(0, 27);
	private final SlotZone hotbarSlotZone = new SlotZone(27, 9);
	private final SlotZone wandSlotZone;
	public final int slotsCount;

	public WandMenu(int containerId, Inventory inventory) {
		super(MenuInit.WAND.get(), containerId);
		ItemStack wandStack = inventory.player.getItemInHand(InteractionHand.MAIN_HAND);
		LazyOptional<IItemHandler> wandInventory = wandStack.getCapability(ForgeCapabilities.ITEM_HANDLER);
		this.slotsCount = WandItem.getCapacity(wandStack);
		this.wandSlotZone = new SlotZone(36, slotsCount);
		addPlayerSlots(inventory);
		wandInventory.ifPresent(this::addWandSlots);
	}

	private void addWandSlots(IItemHandler wandInventory) {
		int slotsLeftToAdd = slotsCount;

		for (int y = 0; y < getSlotsRows(); y++) {
			for (int x = 0; x < 9 && slotsLeftToAdd > 0; x++, slotsLeftToAdd--) {
				addSlot(new SlotItemHandler(wandInventory, x + y * 9, 8 + x * 18, 18 + y * 18));
			}
		}
	}

	private void addPlayerSlots(Inventory inventory) {
		int yShift = (getSlotsRows() - 4) * 18;

		for (int y = 0; y < 3; ++y) {
			for (int x = 0; x < 9; ++x) {
				addSlot(new Slot(inventory, x + y * 9 + 9, 8 + x * 18, 103 + y * 18 + yShift));
			}
		}

		for (int x = 0; x < 9; ++x) {
			addSlot(new Slot(inventory, x, 8 + x * 18, 161 + yShift));
		}
	}

	@Override
	public boolean stillValid(Player player) {
		return true;
	}

	@Override
	public ItemStack quickMoveStack(Player player, int sourceSlotIndex) {
		Slot sourceSlot = slots.get(sourceSlotIndex);

		if (sourceSlot == null || !sourceSlot.hasItem()) {
			return ItemStack.EMPTY;
		}

		ItemStack sourceStack = sourceSlot.getItem();
		ItemStack sourceStackBeforeMerge = sourceStack.copy();
		boolean canMergeStack = tryMergingStack(sourceSlotIndex, sourceStack);

		if (!canMergeStack) {
			return ItemStack.EMPTY;
		}

		if (sourceStack.isEmpty()) {
			sourceSlot.set(ItemStack.EMPTY);
		} else {
			sourceSlot.setChanged();
		}

		if (sourceStack.getCount() == sourceStackBeforeMerge.getCount()) {
			return ItemStack.EMPTY;
		}

		sourceSlot.onTake(player, sourceStack);
		return sourceStackBeforeMerge;
	}

	private boolean tryMergingStack(int sourceSlotIndex, ItemStack sourceStack) {
		if (wandSlotZone.containsSlot(sourceSlotIndex)) {
			return mergeStack(wholeInventorySlotZone, sourceStack, false);
		}

		if (wholeInventorySlotZone.containsSlot(sourceSlotIndex)) {
			if (mergeStack(wandSlotZone, sourceStack, false)) {
				return true;
			}

			if (hotbarSlotZone.containsSlot(sourceSlotIndex)) {
				return mergeStack(upperInventorySlotZone, sourceStack, false);
			} else {
				return mergeStack(hotbarSlotZone, sourceStack, false);
			}
		}

		return false;
	}

	private boolean mergeStack(SlotZone destinationZone, ItemStack sourceItemStack, boolean fillFromEnd) {
		return moveItemStackTo(sourceItemStack, destinationZone.firstSlotIndex, destinationZone.lastSlotIndex, fillFromEnd);
	}

	public int getSlotsCount() {
		return slotsCount;
	}

	public int getSlotsRows() {
		return (int) Math.ceil(slotsCount / 9D);
	}
}
