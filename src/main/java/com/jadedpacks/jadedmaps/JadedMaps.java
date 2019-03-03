package com.jadedpacks.jadedmaps;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.client.Minecraft;

import java.io.File;

@Mod(modid = "jadedmaps", name = "JadedMaps", version = "@VERSION@")
public class JadedMaps {
	static File mapsDir;

	@Mod.EventHandler
	public void preInit(final FMLPreInitializationEvent event) {
		mapsDir = new File(Minecraft.getMinecraft().mcDataDir, "maps");
		// TODO
	}
}