package daripher.noita.wands.item;

import java.util.Optional;

import daripher.noita.wands.client.tooltip.SpellTooltip;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class SpellItem extends Item {
	public SpellItem() {
		super(new Properties().tab(CreativeModeTab.TAB_MISC).stacksTo(1));
	}

	@Override
	public Optional<TooltipComponent> getTooltipImage(ItemStack itemStack) {
		String type = "Projectile";
		int manaDrain = 15;
		int damage = 5;
		float spread = 0.6F;
		int speed = 600;
		float castDelay = 0.17F;
		float spreadBonus = -20F;
		return Optional.of(new SpellTooltip(type, manaDrain, damage, spread, speed, castDelay, spreadBonus));
	}
}
