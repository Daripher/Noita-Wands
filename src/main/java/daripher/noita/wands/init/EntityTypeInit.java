package daripher.noita.wands.init;

import daripher.noita.wands.NoitaWandsMod;
import daripher.noita.wands.entity.projectile.*;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EntityTypeInit {
	public static final DeferredRegister<EntityType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, NoitaWandsMod.MOD_ID);

	public static final RegistryObject<EntityType<SparkBolt>> SPARK_BOLT = REGISTRY.register("spark_bolt", SparkBolt::createEntityType);
	public static final RegistryObject<EntityType<Arrow>> ARROW = REGISTRY.register("arrow", Arrow::createEntityType);
}
