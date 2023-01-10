package daripher.noita.wands.init;

import daripher.noita.wands.NoitaWandsMod;
import daripher.noita.wands.menu.WandMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MenuInit {
	public static final DeferredRegister<MenuType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.MENU_TYPES, NoitaWandsMod.MOD_ID);

	public static final RegistryObject<MenuType<WandMenu>> WAND = REGISTRY.register("wand", () -> new MenuType<WandMenu>(WandMenu::new));
}
