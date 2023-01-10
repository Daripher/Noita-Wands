package daripher.noita.wands.client.init;

import daripher.noita.wands.NoitaWandsMod;
import daripher.noita.wands.client.screen.WandScreen;
import daripher.noita.wands.init.MenuInit;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(modid = NoitaWandsMod.MOD_ID, bus = Bus.MOD)
public class ContainerScreenInit {
	@SubscribeEvent
	public static void clientSetup(FMLClientSetupEvent event) {
		event.enqueueWork(() -> MenuScreens.register(MenuInit.WAND.get(), WandScreen::new));
	}
}
