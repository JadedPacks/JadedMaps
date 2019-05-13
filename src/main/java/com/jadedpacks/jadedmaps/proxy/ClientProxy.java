package com.jadedpacks.jadedmaps.proxy;

import com.jadedpacks.jadedmaps.JadedMaps;
import com.jadedpacks.jadedmaps.gui.GuiMapList;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiCreateWorld;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.common.MinecraftForge;

import java.io.File;

public class ClientProxy extends CommonProxy {
	private GuiMapList mapList;

	public void preInit() {
		JadedMaps.mapsDir = new File(Minecraft.getMinecraft().mcDataDir, "maps");
		mapList = new GuiMapList();
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public void onOpenGui(final GuiOpenEvent event) {
		if(event.gui instanceof GuiCreateWorld) {
			event.gui = mapList;
		}
	}
}