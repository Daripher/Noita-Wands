package daripher.noita.wands;

import daripher.noita.wands.init.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(NoitaWandsMod.MOD_ID)
public class NoitaWandsMod {
	public static final String MOD_ID = "noitawands";

	public NoitaWandsMod() {
		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		ItemInit.REGISTRY.register(modEventBus);
		EntityTypeInit.REGISTRY.register(modEventBus);
		SoundEventInit.REGISTRY.register(modEventBus);
	}
}
