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

public class ClientSpellTooltip implements ClientTooltipComponent {
	private static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation(NoitaWandsMod.MOD_ID, "textures/gui/wand_tooltip.png");
	private static final int STAT_ICONS_WIDTH = 8;
	private static final int TEXT_SPACING = 7;
	private static final int TEXT_VERTICAL_SPACING = 4;
	private List<Component> statComponents = new ArrayList<>();
	private List<Component> statValuesComponents = new ArrayList<>();
	private final Component description;

	public ClientSpellTooltip(SpellTooltip tooltip) {
		description = Component.literal("Summons an arrow");
		addStatsComponents();
		addStatsValuesComponents(tooltip);
	}

	private void addStatsComponents() {
		statComponents.add(Component.literal("Type"));
		statComponents.add(Component.literal("Mana Drain"));
		statComponents.add(Component.literal("Damage (Slice)"));
		statComponents.add(Component.literal("Spread"));
		statComponents.add(Component.literal("Speed"));
		statComponents.add(Component.literal("Cast Delay"));
		statComponents.add(Component.literal("Spread"));
	}

	private void addStatsValuesComponents(SpellTooltip tooltip) {
		statValuesComponents.add(Component.literal(tooltip.spellType));
		statValuesComponents.add(Component.literal("" + tooltip.manaDrain));
		statValuesComponents.add(Component.literal("" + tooltip.damage));
		statValuesComponents.add(Component.literal("" + tooltip.spread + " DEG"));
		statValuesComponents.add(Component.literal("" + tooltip.speed));
		statValuesComponents.add(Component.literal("" + tooltip.castDelay + " s"));
		statValuesComponents.add(Component.literal("" + tooltip.spreadBonus + " DEG"));
	}

	@Override
	public void renderImage(Font font, int x, int y, PoseStack poseStack, ItemRenderer itemRenderer, int z) {
		renderStatIcons(x, y + 1 + TEXT_VERTICAL_SPACING + 10, poseStack);
	}

	@Override
	public void renderText(Font font, int x, int y, Matrix4f matrix, BufferSource bufferSource) {
		font.drawInBatch(description, x, y + 1, -1, true, matrix, bufferSource, false, 0, 15728880);
		renderStatNames(font, x + STAT_ICONS_WIDTH + TEXT_SPACING, y + 1 + TEXT_VERTICAL_SPACING + 10, matrix, bufferSource);
		renderStatValues(font, x + getWidth(font) - getMaxStatValueWidth(font), y + 1 + TEXT_VERTICAL_SPACING + 10, matrix, bufferSource);
	}

	private void renderStatIcons(int x, int y, PoseStack poseStack) {
		RenderSystem.setShaderColor(1F, 1F, 1F, 1F);
		RenderSystem.setShaderTexture(0, TEXTURE_LOCATION);

		for (int i = 0; i < 7; i++) {
			if (i == 2 || i == 5) {
				y += TEXT_VERTICAL_SPACING;
			}

			int[] icons = { 8, 9, 10, 7, 11, 2, 7 };

			GuiComponent.blit(poseStack, x + 1, y + i * 10, icons[i] * 7, 0, 7, 7, 256, 256);
		}
	}

	@Override
	public int getHeight() {
		return 81 + TEXT_VERTICAL_SPACING * 3;
	}

	@Override
	public int getWidth(Font font) {
		return STAT_ICONS_WIDTH + TEXT_SPACING + getMaxStatWidth(font) + TEXT_SPACING + getMaxStatValueWidth(font);
	}

	private void renderStatValues(Font font, int x, int y, Matrix4f matrix, BufferSource bufferSource) {
		for (int i = 0; i < 7; i++) {
			var component = statValuesComponents.get(i);

			if (i == 2 || i == 5) {
				y += TEXT_VERTICAL_SPACING;
			}

			font.drawInBatch(component, x, y, -1, true, matrix, bufferSource, false, 0, 15728880);
			y += 10;
		}
	}

	private void renderStatNames(Font font, int x, int y, Matrix4f matrix, BufferSource bufferSource) {
		for (int i = 0; i < 7; i++) {
			var component = statComponents.get(i);

			if (i == 2 || i == 5) {
				y += TEXT_VERTICAL_SPACING;
			}

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
