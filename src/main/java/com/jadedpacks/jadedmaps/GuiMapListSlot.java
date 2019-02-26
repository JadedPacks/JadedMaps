package com.jadedpacks.jadedmaps;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiSlot;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;

public class GuiMapListSlot extends GuiSlot {
	private GuiMapList list;

	GuiMapListSlot(final GuiMapList list) {
		super(Minecraft.getMinecraft(), list.width, list.height, 78, list.height - 32, 36);
		this.list = list;
	}

	@Override
	protected int getSize() {
		return list.saveList.size();
	}

	@Override
	protected void elementClicked(final int slot, final boolean doubleClicked) {
		list.selectedSlot = slot;
		list.createButton.enabled = true;
		if(doubleClicked) {
			list.createMap();
		}
	}

	@Override
	protected boolean isSelected(int slot) {
		return slot == list.selectedSlot;
	}

	@Override
	protected void drawBackground() {
		list.drawDefaultBackground();
	}

	@Override
	protected int getScrollBarX() {
		return (list.width / 2) + ((list.width - 40) / 2) - 6;
	}

	@Override
	protected void drawSlot(final int slot, final int x, final int y, final int slotHeight, final Tessellator tessellator) {
		final Minecraft mc = Minecraft.getMinecraft();
		final TemplateSaveFormat world = list.saveList.get(slot);
		list.drawString(mc.fontRenderer, world.getDisplayName(), x + 34, y + 5, 16777215);
		//list.drawString(mc.fontRenderer, world.getDescription(), x + 34, y + 17, 8421504);
		final ResourceLocation icon = world.getIcon();
		if(icon != null){
			mc.getTextureManager().bindTexture(icon);
			tessellator.startDrawingQuads();
			tessellator.setColorOpaque(255, 255, 255);
			tessellator.addVertexWithUV(x, y + 32, 0, 0, 1);
			tessellator.addVertexWithUV(x + 32, y + 32, 0, 1, 1);
			tessellator.addVertexWithUV(x + 32, y, 0, 1, 0);
			tessellator.addVertexWithUV(x, y, 0, 0, 0);
			tessellator.draw();
		}
	}
}