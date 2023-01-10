package daripher.noita.wands.client.tooltip;

import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Matrix4f;

import daripher.noita.wands.NoitaWandsMod;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class ClientWandTooltip implements ClientTooltipComponent {
	private static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation(NoitaWandsMod.MOD_ID, "textures/gui/wand_tooltip.png");
	private static final int STAT_ICONS_WIDTH = 8;
	private static final int TEXT_SPACING = 7;
	private List<Component> statComponents = new ArrayList<>();
	private List<Component> statValuesComponents = new ArrayList<>();
	private final WandTooltip tooltip;

	public ClientWandTooltip(WandTooltip tooltip) {
		this.tooltip = tooltip;
		addStatsComponents();
		addStatsValuesComponents(tooltip);
	}

	private void addStatsComponents() {
		statComponents.add(Component.literal("Shuffle"));
		statComponents.add(Component.literal("Spells/Cast"));
		statComponents.add(Component.literal("Cast Delay"));
		statComponents.add(Component.literal("Recharge Time"));
		statComponents.add(Component.literal("Mana Max"));
		statComponents.add(Component.literal("Mana Recharge"));
		statComponents.add(Component.literal("Capacity"));
		statComponents.add(Component.literal("Spread"));
	}

	private void addStatsValuesComponents(WandTooltip tooltip) {
		statValuesComponents.add(Component.literal(tooltip.shuffle ? "Yes" : "No"));
		statValuesComponents.add(Component.literal("" + tooltip.spellsPerCast));
		statValuesComponents.add(Component.literal("" + tooltip.castDelay + " s"));
		statValuesComponents.add(Component.literal("" + tooltip.rechargeTime + " s"));
		statValuesComponents.add(Component.literal("" + tooltip.manaMax));
		statValuesComponents.add(Component.literal("" + tooltip.manaRecharge));
		statValuesComponents.add(Component.literal("" + tooltip.capacity));
		statValuesComponents.add(Component.literal("" + tooltip.spread + " DEG"));
	}

	@Override
	public void renderImage(Font font, int x, int y, PoseStack poseStack, ItemRenderer itemRenderer, int z) {
		renderStatIcons(x, y + 1, poseStack);
		renderSpellSlots(x, y + 1, poseStack);
		renderSpells(x, y + 1, poseStack, itemRenderer);
	}

	@Override
	public void renderText(Font font, int x, int y, Matrix4f matrix, BufferSource bufferSource) {
		renderStatNames(font, x + STAT_ICONS_WIDTH + TEXT_SPACING, y + 1, matrix, bufferSource);
		renderStatValues(font, x + getWidth(font) - getMaxStatValueWidth(font), y + 1, matrix, bufferSource);
	}

	private void renderSpellSlots(int x, int y, PoseStack poseStack) {
		RenderSystem.setShaderColor(1F, 1F, 1F, 1F);
		RenderSystem.setShaderTexture(0, TEXTURE_LOCATION);

		tooltip.inventory.ifPresent(inventory -> {
			for (int i = 0; i < tooltip.capacity; i++) {
				ItemStack stackInSlot = inventory.getStackInSlot(i);
				int slotTexture = 0;

				if (!stackInSlot.isEmpty()) {
					slotTexture = 1;
				}

				GuiComponent.blit(poseStack, x + i * 19, y + 84, 0, 18 * slotTexture, 7, 18, 18, 256, 256);
			}
		});
	}

	private void renderSpells(int x, int y, PoseStack poseStack, ItemRenderer itemRenderer) {
		RenderSystem.setShaderColor(1F, 1F, 1F, 1F);

		tooltip.inventory.ifPresent(inventory -> {
			for (int i = 0; i < tooltip.capacity; i++) {
				ItemStack stackInSlot = inventory.getStackInSlot(i);

				if (!stackInSlot.isEmpty()) {
					itemRenderer.renderAndDecorateItem(stackInSlot, x + i * 19 + 1, y + 84 + 1);
				}
			}
		});
	}

	private void renderStatIcons(int x, int y, PoseStack poseStack) {
		RenderSystem.setShaderColor(1F, 1F, 1F, 1F);
		RenderSystem.setShaderTexture(0, TEXTURE_LOCATION);

		for (int i = 0; i < 8; i++) {
			GuiComponent.blit(poseStack, x + 1, y + i * 10, i * 7, 0, 7, 7, 256, 256);
		}
	}

	@Override
	public int getHeight() {
		return 83 + 21;
	}

	@Override
	public int getWidth(Font font) {
		return STAT_ICONS_WIDTH + TEXT_SPACING + getMaxStatWidth(font) + TEXT_SPACING + getMaxStatValueWidth(font);
	}

	private void renderStatValues(Font font, int x, int y, Matrix4f matrix, BufferSource bufferSource) {
		for (var component : statValuesComponents) {
			font.drawInBatch(component, x, y, -1, true, matrix, bufferSource, false, 0, 15728880);
			y += 10;
		}
	}

	private void renderStatNames(Font font, int x, int y, Matrix4f matrix, BufferSource bufferSource) {
		for (var component : statComponents) {
			font.drawInBatch(component, x, y, -1, true, matrix, bufferSource, false, 0, 15728880);
			y += 10;
		}
	}

	private int getMaxStatWidth(Font font) {
		return statComponents.stream().map(font::width).max(Integer::compare).get();
	}

	private int getMaxStatValueWidth(Font font) {
		return statValuesComponents.stream().map(font::width).max(Integer::compare).get();
	}
}
