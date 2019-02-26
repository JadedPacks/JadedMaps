package com.jadedpacks.jadedmaps;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.world.storage.ISaveFormat;

import java.util.List;

public class GuiMapList extends GuiScreen {
	private GuiTextField mapName;
	private GuiScreen parent;
	private GuiMapListSlot mapList;
	private String folderString;
	List<TemplateSaveFormat> saveList;
	GuiButton createButton;
	int selectedSlot;

	public GuiMapList(GuiScreen parent) {
		this.parent = parent;
	}

	@Override
	public void initGui() {
		mapName = new GuiTextField(fontRenderer, width / 2 - 100, 45, 200, 20);
		mapName.setFocused(true);
		mapName.setText(I18n.getString("selectWorld.newWorld"));
		createButton = new GuiButton(0, width / 2 - 155, height - 28, 150, 20, I18n.getString("selectWorld.create"));
		createButton.enabled = false;
		buttonList.add(createButton);
		buttonList.add(new GuiButton(1, width / 2 + 5, width - 28, 150, 20, I18n.getString("gui.cancel")));
		mapList = new GuiMapListSlot(this);
		selectedSlot = -1;
		saveList = new TemplateSaveLoader(JadedMaps.mapsDir).getSaveList();
	}

	@Override
	protected void actionPerformed(final GuiButton button) {
		super.actionPerformed(button);
		if(button.id == 0) {
			createMap();
		} else if(button.id == 1) {
			mc.displayGuiScreen(parent);
		}
		mapList.actionPerformed(button);
	}

	@Override
	protected void keyTyped(final char keyChar, final int keyCode) {
		if(mapName.isFocused()) {
			mapName.textboxKeyTyped(keyChar, keyCode);
		}
		sanitizeFolderName();
	}

	@Override
	public void drawScreen(final int x, final int y, final float f) {
		mapList.drawScreen(x, y, f);
		mapName.drawTextBox();
		drawCenteredString(fontRenderer, I18n.getString("Create Templated World"), width / 2, 20, -1);
		drawString(fontRenderer, I18n.getString("selectWorld.enterName"), width / 2 - 100, 35, -6250336);
		drawString(fontRenderer, I18n.getString("selectWorld.resultFolder") + " " + folderString, width / 2 - 100, 68, -6250336);
		super.drawScreen(x, y, f);
	}

	private void sanitizeFolderName() {
		folderString = mapName.getText().trim().replaceAll("[\\./\"]", "_");
		for(char ch : ChatAllowedCharacters.allowedCharacters.toCharArray()) {
			folderString = this.folderString.replace(ch, '_');
		}
		final ISaveFormat saveLoader = mc.getSaveLoader();
		while(saveLoader.getWorldInfo(this.folderString) != null) {
			folderString = this.folderString + "-";
		}
	}

	void createMap() {
		if(selectedSlot != -1) {
			saveList.get(selectedSlot).copy(folderString, mapName.getText().trim());
		}
	}
}