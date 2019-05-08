package com.jadedpacks.jadedmaps;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiCreateWorld;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeSubscribe;

import java.io.File;

public class ClientProxy extends CommonProxy {
	private GuiMapList mapList;

	public void preInit() {
		JadedMaps.mapsDir = new File(Minecraft.getMinecraft().mcDataDir, "maps");
		mapList = new GuiMapList();
		MinecraftForge.EVENT_BUS.register(this);
	}

	@ForgeSubscribe
	public void onOpenGui(final GuiOpenEvent event) {
		if(event.gui instanceof GuiCreateWorld) {
			event.gui = mapList;
		}
	}
}