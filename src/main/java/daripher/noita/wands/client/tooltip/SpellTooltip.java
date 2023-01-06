package daripher.noita.wands.client.tooltip;

import net.minecraft.world.inventory.tooltip.TooltipComponent;

public class SpellTooltip implements TooltipComponent {
	public final String spellType;
	public final int manaDrain;
	public final int damage;
	public final float spread;
	public final int speed;
	public final float castDelay;
	public final float spreadBonus;

	public SpellTooltip(String type, int manaDrain, int damage, float spread, int speed, float castDelay, float spreadBonus) {
		this.spellType = type;
		this.manaDrain = manaDrain;
		this.damage = damage;
		this.spread = spread;
		this.speed = speed;
		this.castDelay = castDelay;
		this.spreadBonus = spreadBonus;
	}
}
