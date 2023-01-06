package daripher.noita.wands.client.init;

import daripher.noita.wands.NoitaWandsMod;
import daripher.noita.wands.client.tooltip.*;
import net.minecraftforge.client.event.RegisterClientTooltipComponentFactoriesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = NoitaWandsMod.MOD_ID, bus = Bus.MOD)
public class TooltipInit {
	@SubscribeEvent
	public static void registerClientTooltipComponentFactories(RegisterClientTooltipComponentFactoriesEvent event) {
		event.register(WandTooltip.class, ClientWandTooltip::new);
		event.register(SpellTooltip.class, ClientSpellTooltip::new);
	}
}
