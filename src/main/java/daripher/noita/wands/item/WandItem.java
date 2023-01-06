package daripher.noita.wands.item;

import java.util.Optional;

import daripher.noita.wands.NoitaWandsMod;
import daripher.noita.wands.client.tooltip.WandTooltip;
import daripher.noita.wands.entity.projectile.Arrow;
import daripher.noita.wands.init.SoundEventInit;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = NoitaWandsMod.MOD_ID)
public class WandItem extends Item {
	public WandItem() {
		super(new Properties().tab(CreativeModeTab.TAB_COMBAT));
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		ItemStack wandStack = player.getItemInHand(hand);

		if (!level.isClientSide) {
			Arrow projectile = new Arrow(level);
			projectile.setOwner(player);
			projectile.setPos(player.getEyePosition().add(player.getLookAngle()));
			projectile.shoot(player.getLookAngle(), 2F, 0F);
			level.addFreshEntity(projectile);
		}

		level.playSound(null, player, SoundEventInit.ARROW_FIRED.get(), SoundSource.PLAYERS, 1F, player.getRandom().nextFloat() * 0.2F + 0.8F);
		player.getCooldowns().addCooldown(this, getCooldown(wandStack));
		return super.use(level, player, hand);
	}

	private int getCooldown(ItemStack wandStack) {
		return (int) (Math.max(getCastDelay(wandStack), getRechargeTime(wandStack)) * 20);
	}

	@Override
	public Optional<TooltipComponent> getTooltipImage(ItemStack itemStack) {
		boolean shuffle = false;
		int spellsPerCast = 1;
		float castDelay = getCastDelay(itemStack);
		float rechargeTime = getRechargeTime(itemStack);
		int manaMax = 83;
		int manaRecharge = 25;
		int capacity = 2;
		float spread = 0F;
		return Optional.of(new WandTooltip(shuffle, spellsPerCast, castDelay, rechargeTime, manaMax, manaRecharge, capacity, spread));
	}

	private float getCastDelay(ItemStack itemStack) {
		return 0.22F;
	}

	private float getRechargeTime(ItemStack itemStack) {
		return 0.47F;
	}
}
