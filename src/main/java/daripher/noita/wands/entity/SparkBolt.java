package daripher.noita.wands.entity;

import daripher.noita.wands.init.EntityInit;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;

public class SparkBolt extends AbstractHurtingProjectile {
	public SparkBolt(Level level) {
		super(EntityInit.SPARK_BOLT.get(), level);
	}

	private SparkBolt(EntityType<? extends SparkBolt> type, Level level) {
		super(type, level);
	}

	public static EntityType<SparkBolt> createEntityType() {
		return EntityType.Builder.<SparkBolt>of(SparkBolt::new, MobCategory.MISC).sized(0.3F, 0.3F).build("spark_bolt");
	}
}
