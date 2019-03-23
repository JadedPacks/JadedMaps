package com.jadedpacks.jadedmaps;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiCreateWorld;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeSubscribe;

import java.io.File;

@Mod(modid = "jadedmaps", name = "JadedMaps", version = "@VERSION@")
public class JadedMaps {
	static File mapsDir;
	private GuiMapList mapList;

	@Mod.EventHandler
	public void preInit(final FMLPreInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(this);
		mapsDir = new File(Minecraft.getMinecraft().mcDataDir, "maps");
		mapList = new GuiMapList();
	}

	@ForgeSubscribe
	public void onOpenGui(final GuiOpenEvent event) {
		if(event.gui instanceof GuiCreateWorld) {
			event.gui = mapList;
		}
	}
}