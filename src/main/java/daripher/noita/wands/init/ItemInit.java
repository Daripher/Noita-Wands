package daripher.noita.wands.init;

import daripher.noita.wands.NoitaWandsMod;
import daripher.noita.wands.item.*;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemInit {
	public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, NoitaWandsMod.MOD_ID);

	public static final RegistryObject<Item> WAND = REGISTRY.register("wand", WandItem::new);

	public static final RegistryObject<Item> ARROW_SPELL = REGISTRY.register("arrow_spell", SpellItem::new);
}
