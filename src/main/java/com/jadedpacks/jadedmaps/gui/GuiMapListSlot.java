package com.jadedpacks.jadedmaps.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiSlot;
import net.minecraft.client.renderer.Tessellator;

public class GuiMapListSlot extends GuiSlot {
	private final GuiMapList list;

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
	protected boolean isSelected(final int slot) {
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
		list.drawString(Minecraft.getMinecraft().fontRenderer, list.saveList.get(slot).getDisplayName(), x, y + 5, 16777215);
	}
}