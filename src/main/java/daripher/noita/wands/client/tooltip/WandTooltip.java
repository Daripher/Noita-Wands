package daripher.noita.wands.client.tooltip;

import org.jetbrains.annotations.NotNull;

import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;

public class WandTooltip implements TooltipComponent {
	public final boolean shuffle;
	public final int spellsPerCast;
	public final float castDelay;
	public final float rechargeTime;
	public final int manaMax;
	public final int manaRecharge;
	public final int capacity;
	public final float spread;
	public final LazyOptional<IItemHandler> inventory;

	public WandTooltip(boolean shuffle, int spellsPerCast, float castDelay, float rechargeTime, int manaMax, int manaRecharge, int capacity, float spread,
			@NotNull LazyOptional<IItemHandler> inventory) {
		this.shuffle = shuffle;
		this.spellsPerCast = spellsPerCast;
		this.castDelay = castDelay;
		this.rechargeTime = rechargeTime;
		this.manaMax = manaMax;
		this.manaRecharge = manaRecharge;
		this.capacity = capacity;
		this.spread = spread;
		this.inventory = inventory;
	}
}
