package daripher.noita.wands.entity;

import daripher.noita.wands.init.EntityInit;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class Arrow extends AbstractArrow {
	public Arrow(Level level) {
		this(EntityInit.ARROW.get(), level);
	}

	private Arrow(EntityType<? extends Arrow> type, Level level) {
		super(type, level);
		pickup = Pickup.DISALLOWED;
	}

	@Override
	public void tick() {
		super.tick();
		Vec3 prevMovement = this.getDeltaMovement();
		setDeltaMovement(prevMovement.x, prevMovement.y - getMass(), prevMovement.z);
	}

	@Override
	protected ItemStack getPickupItem() {
		return ItemStack.EMPTY;
	}

	public void shoot(Vec3 direction, float speed, float spread) {
		super.shoot(direction.x, direction.y, direction.z, speed, spread);
	}

	@Override
	public boolean isNoGravity() {
		return true;
	}

	private float getMass() {
		return 0.03F;
	}

	public static EntityType<Arrow> createEntityType() {
		return EntityType.Builder.<Arrow>of(Arrow::new, MobCategory.MISC).sized(0.3F, 0.3F).clientTrackingRange(64).setUpdateInterval(1).build("arrow");
	}
}
