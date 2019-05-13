package com.jadedpacks.jadedmaps.gui;

import com.jadedpacks.jadedmaps.JadedMaps;
import com.jadedpacks.jadedmaps.util.TemplateSaveFormat;
import com.jadedpacks.jadedmaps.util.TemplateSaveLoader;
import net.minecraft.client.AnvilConverterException;
import net.minecraft.client.gui.*;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.WorldType;
import net.minecraft.world.storage.ISaveFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class GuiMapList extends GuiScreen {
	private GuiTextField mapName;
	private GuiMapListSlot mapList;
	private String folderString;
	List<TemplateSaveFormat> saveList;
	GuiButton createButton;
	int selectedSlot;

	@Override
	public void initGui() {
		mapName = new GuiTextField(fontRendererObj, width / 2 - 100, 45, 200, 20);
		mapName.setFocused(true);
		mapName.setText(I18n.format("selectWorld.newWorld"));
		createButton = new GuiButton(0, width / 2 - 155, height - 28, 150, 20, I18n.format("selectWorld.create"));
		createButton.enabled = false;
		buttonList.addAll(Arrays.asList(
			createButton,
			new GuiButton(1, width / 2 + 5, height - 28, 150, 20, I18n.format("gui.cancel"))
		));
		mapList = new GuiMapListSlot(this);
		selectedSlot = -1;
		try {
			saveList = new TemplateSaveLoader(JadedMaps.mapsDir).getSaveList();
		} catch(AnvilConverterException e) {
			saveList = new ArrayList<>();
			e.printStackTrace();
		}
		sanitizeFolderName();
	}

	@Override
	protected void actionPerformed(final GuiButton button) {
		super.actionPerformed(button);
		if(button.id == 0) {
			createMap();
			if(mc.getSaveLoader().canLoadWorld(folderString)) {
				WorldSettings settings = new WorldSettings((new Random()).nextLong(), WorldSettings.GameType.SURVIVAL, true, false, WorldType.DEFAULT);
				settings.commandsAllowed = false;
				mc.launchIntegratedServer(folderString, mapName.getText().trim(), settings);
			}
		} else if(button.id == 1) {
			mc.displayGuiScreen(new GuiSelectWorld(new GuiMainMenu()));
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
		drawCenteredString(fontRendererObj, I18n.format("Create Templated World"), width / 2, 20, -1);
		drawString(fontRendererObj, I18n.format("selectWorld.enterName"), width / 2 - 100, 35, -6250336);
		drawString(fontRendererObj, I18n.format("selectWorld.resultFolder") + " " + folderString, width / 2 - 100, 68, -6250336);
		super.drawScreen(x, y, f);
	}

	private void sanitizeFolderName() {
		folderString = mapName.getText().trim().replaceAll("[\\./\"]", "_");
		for(char ch : ChatAllowedCharacters.allowedCharactersArray) {
			folderString = folderString.replace(ch, '_');
		}
		final ISaveFormat saveLoader = mc.getSaveLoader();
		while(saveLoader.getWorldInfo(folderString) != null) {
			folderString = folderString + "-";
		}
	}

	void createMap() {
		if(selectedSlot != -1) {
			saveList.get(selectedSlot).copy(folderString, mapName.getText().trim());
		}
	}
}