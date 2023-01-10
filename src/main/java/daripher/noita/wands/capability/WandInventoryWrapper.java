package daripher.noita.wands.capability;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import daripher.noita.wands.item.SpellItem;
import daripher.noita.wands.item.WandItem;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemHandlerHelper;

public class WandInventoryWrapper implements IItemHandlerModifiable, ICapabilityProvider {
	private final LazyOptional<IItemHandler> itemHandlerHolder = LazyOptional.of(() -> this);
	private final ItemStack wandStack;
	private final int slotsCount;

	public WandInventoryWrapper(ItemStack wandStack) {
		this.wandStack = wandStack;
		this.slotsCount = WandItem.getCapacity(wandStack);
	}

	@Override
	public int getSlots() {
		return slotsCount;
	}

	@Override
	@NotNull
	public ItemStack getStackInSlot(int slot) {
		validateSlotIndex(slot);
		return getItemList().get(slot);
	}

	@Override
	@NotNull
	public ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
		if (stack.isEmpty())
			return ItemStack.EMPTY;

		if (!isItemValid(slot, stack))
			return stack;

		validateSlotIndex(slot);
		NonNullList<ItemStack> itemStacks = getItemList();
		ItemStack existing = itemStacks.get(slot);
		int limit = Math.min(getSlotLimit(slot), stack.getMaxStackSize());

		if (!existing.isEmpty()) {
			if (!ItemHandlerHelper.canItemStacksStack(stack, existing))
				return stack;

			limit -= existing.getCount();
		}

		if (limit <= 0)
			return stack;

		boolean reachedLimit = stack.getCount() > limit;

		if (!simulate) {
			if (existing.isEmpty()) {
				itemStacks.set(slot, reachedLimit ? ItemHandlerHelper.copyStackWithSize(stack, limit) : stack);
			} else {
				existing.grow(reachedLimit ? limit : stack.getCount());
			}
			setItemList(itemStacks);
		}

		return reachedLimit ? ItemHandlerHelper.copyStackWithSize(stack, stack.getCount() - limit) : ItemStack.EMPTY;
	}

	@Override
	@NotNull
	public ItemStack extractItem(int slot, int amount, boolean simulate) {
		NonNullList<ItemStack> itemStacks = getItemList();
		if (amount == 0)
			return ItemStack.EMPTY;

		validateSlotIndex(slot);
		ItemStack existing = itemStacks.get(slot);

		if (existing.isEmpty())
			return ItemStack.EMPTY;

		int toExtract = Math.min(amount, existing.getMaxStackSize());

		if (existing.getCount() <= toExtract) {
			if (!simulate) {
				itemStacks.set(slot, ItemStack.EMPTY);
				setItemList(itemStacks);
				return existing;
			} else {
				return existing.copy();
			}
		} else {
			if (!simulate) {
				itemStacks.set(slot, ItemHandlerHelper.copyStackWithSize(existing, existing.getCount() - toExtract));
				setItemList(itemStacks);
			}

			return ItemHandlerHelper.copyStackWithSize(existing, toExtract);
		}
	}

	private void validateSlotIndex(int slot) {
		if (slot < 0 || slot >= getSlots())
			throw new RuntimeException("Slot " + slot + " not in valid range - [0," + getSlots() + ")");
	}

	@Override
	public int getSlotLimit(int slot) {
		return 1;
	}

	@Override
	public boolean isItemValid(int slot, @NotNull ItemStack stack) {
		return stack.getItem() instanceof SpellItem || stack.isEmpty();
	}

	@Override
	public void setStackInSlot(int slot, @NotNull ItemStack stack) {
		validateSlotIndex(slot);

		if (!isItemValid(slot, stack))
			throw new RuntimeException("Invalid stack " + stack + " for slot " + slot + ")");

		NonNullList<ItemStack> itemStacks = getItemList();
		itemStacks.set(slot, stack);
		setItemList(itemStacks);
	}

	private NonNullList<ItemStack> getItemList() {
		NonNullList<ItemStack> itemStacks = NonNullList.withSize(getSlots(), ItemStack.EMPTY);
		ContainerHelper.loadAllItems(wandStack.getOrCreateTagElement("Inventory"), itemStacks);
		return itemStacks;
	}

	private void setItemList(NonNullList<ItemStack> itemStacks) {
		CompoundTag inventoryTag = wandStack.getOrCreateTagElement("Inventory");
		ContainerHelper.saveAllItems(inventoryTag, itemStacks);
		wandStack.addTagElement("Inventory", inventoryTag);
	}

	@Override
	@NotNull
	public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
		return ForgeCapabilities.ITEM_HANDLER.orEmpty(cap, itemHandlerHolder);
	}
}
