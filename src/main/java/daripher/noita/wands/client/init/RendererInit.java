package daripher.noita.wands.client.init;

import daripher.noita.wands.NoitaWandsMod;
import daripher.noita.wands.client.render.ArrowRenderer;
import daripher.noita.wands.init.EntityTypeInit;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(modid = NoitaWandsMod.MOD_ID, bus = Bus.MOD)
public class RendererInit {
	@SubscribeEvent
	public static void registerEntityRenderers(FMLClientSetupEvent event) {
		EntityRenderers.register(EntityTypeInit.ARROW.get(), ArrowRenderer::new);
	}
}
