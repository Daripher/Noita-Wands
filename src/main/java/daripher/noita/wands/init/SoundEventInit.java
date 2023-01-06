package daripher.noita.wands.init;

import daripher.noita.wands.NoitaWandsMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SoundEventInit {
	public static final DeferredRegister<SoundEvent> REGISTRY = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, NoitaWandsMod.MOD_ID);

	public static final RegistryObject<SoundEvent> ARROW_FIRED = register("arrow_fired");

	private static RegistryObject<SoundEvent> register(String soundName) {
		return REGISTRY.register(soundName, () -> new SoundEvent(new ResourceLocation(NoitaWandsMod.MOD_ID, soundName)));
	}
}
