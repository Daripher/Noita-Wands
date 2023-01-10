package daripher.noita.wands.item;

import java.util.Optional;

import javax.annotation.Nullable;

import daripher.noita.wands.NoitaWandsMod;
import daripher.noita.wands.capability.WandInventoryWrapper;
import daripher.noita.wands.client.tooltip.WandTooltip;
import daripher.noita.wands.entity.Arrow;
import daripher.noita.wands.init.SoundInit;
import daripher.noita.wands.menu.WandMenu;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.network.NetworkHooks;

@EventBusSubscriber(modid = NoitaWandsMod.MOD_ID)
public class WandItem extends Item {
	public WandItem() {
		super(new Properties().tab(CreativeModeTab.TAB_COMBAT));
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		ItemStack wandStack = player.getItemInHand(hand);

		if (player.isCrouching()) {
			if (!level.isClientSide && hand == InteractionHand.MAIN_HAND) {
				SimpleMenuProvider menuProvider = new SimpleMenuProvider((id, inv, p) -> new WandMenu(id, inv), getDescription());
				NetworkHooks.openScreen((ServerPlayer) player, menuProvider);
				return InteractionResultHolder.success(wandStack);
			}
		} else {
			if (!level.isClientSide) {
				Arrow projectile = new Arrow(level);
				projectile.setOwner(player);
				projectile.setPos(player.getEyePosition().add(player.getLookAngle()));
				projectile.shoot(player.getLookAngle(), 2F, 0F);
				level.addFreshEntity(projectile);
			}

			level.playSound(null, player, SoundInit.ARROW_FIRED.get(), SoundSource.PLAYERS, 1F, 1F);
			player.getCooldowns().addCooldown(this, getCooldown(wandStack));
		}

		return super.use(level, player, hand);
	}

	@Override
	public Optional<TooltipComponent> getTooltipImage(ItemStack itemStack) {
		boolean shuffle = false;
		int spellsPerCast = 1;
		float castDelay = getCastDelay(itemStack);
		float rechargeTime = getRechargeTime(itemStack);
		int manaMax = 83;
		int manaRecharge = 25;
		int capacity = getCapacity(itemStack);
		float spread = 0F;
		LazyOptional<IItemHandler> inventory = itemStack.getCapability(ForgeCapabilities.ITEM_HANDLER);
		return Optional.of(new WandTooltip(shuffle, spellsPerCast, castDelay, rechargeTime, manaMax, manaRecharge, capacity, spread, inventory));
	}

	@Override
	public @Nullable ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
		return new WandInventoryWrapper(stack);
	}

	private static int getLastCastSpell(ItemStack wandStack) {
		return wandStack.getOrCreateTag().getInt("LastSpell");
	}

	private static void setLastCastSpell(ItemStack wandStack, int i) {
		wandStack.getOrCreateTag().putInt("LastSpell", i);
	}

	public static int getCooldown(ItemStack wandStack) {
		LazyOptional<IItemHandler> inventory = wandStack.getCapability(ForgeCapabilities.ITEM_HANDLER);
		int lastCastSpell = getLastCastSpell(wandStack);
		int cooldown = 0;

		if (inventory.isPresent()) {
			if (lastCastSpell >= inventory.orElseGet(null).getSlots() - 1) {
				cooldown = (int) (getRechargeTime(wandStack) * 20);
				setLastCastSpell(wandStack, 0);
			} else {
				cooldown = (int) (getCastDelay(wandStack) * 20);
				setLastCastSpell(wandStack, lastCastSpell + 1);
			}
		}

		return cooldown;
	}

	public static int getCapacity(ItemStack wandStack) {
		return 3;
	}

	public static float getCastDelay(ItemStack wandStack) {
		return 0.05F;
	}

	public static float getRechargeTime(ItemStack wandStack) {
		return 0.56F;
	}
}
