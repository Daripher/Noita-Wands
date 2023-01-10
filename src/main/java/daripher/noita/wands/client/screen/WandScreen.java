package daripher.noita.wands.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import daripher.noita.wands.NoitaWandsMod;
import daripher.noita.wands.menu.WandMenu;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class WandScreen extends AbstractContainerScreen<WandMenu> {
	private static final ResourceLocation BACKGROUND_TEXTURE_LOCATION = new ResourceLocation(NoitaWandsMod.MOD_ID, "textures/gui/wand_inventory.png");

	public WandScreen(WandMenu menu, Inventory inventory, Component component) {
		super(menu, inventory, component);
		imageHeight = menu.getSlotsRows() * 18 + 114;
		inventoryLabelY = imageHeight - 94;
	}

	@Override
	public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
		renderBackground(poseStack);
		super.render(poseStack, mouseX, mouseY, partialTicks);
		renderTooltip(poseStack, mouseX, mouseY);
	}

	@Override
	protected void renderBg(PoseStack poseStack, float partialTicks, int mouseX, int mouseY) {
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderColor(1F, 1F, 1F, 1F);
		RenderSystem.setShaderTexture(0, BACKGROUND_TEXTURE_LOCATION);
		int backgroundX = (width - imageWidth) / 2;
		int backgroundY = (height - imageHeight) / 2;
		blit(poseStack, backgroundX, backgroundY, 0, 0, imageWidth, menu.getSlotsRows() * 18 + 17);
		blit(poseStack, backgroundX, backgroundY + menu.getSlotsRows() * 18 + 17, 0, 126, imageWidth, 96);
		renderWandSlots(poseStack, backgroundX, backgroundY);
	}

	private void renderWandSlots(PoseStack poseStack, int backgroundX, int backgroundY) {
		int slotsLeftToRender = menu.slotsCount;

		for (int y = 0; y < menu.getSlotsRows(); y++) {
			for (int x = 0; x < 9 && slotsLeftToRender > 0; x++, slotsLeftToRender--) {
				blit(poseStack, backgroundX + 7 + x * 18, backgroundY + 17 + y * 18, 238, 0, 18, 18);
			}
		}
	}
}
