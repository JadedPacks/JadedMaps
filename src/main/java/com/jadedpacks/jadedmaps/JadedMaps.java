package com.jadedpacks.jadedmaps;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

import java.io.File;

@Mod(modid = "jadedmaps", name = "JadedMaps", version = "@VERSION@")
public class JadedMaps {
	static File mapsDir;
	@SidedProxy(clientSide = "com.jadedpacks.jadedmaps.ClientProxy", serverSide = "com.jadedpacks.jadedmaps.CommonProxy")
	public static CommonProxy proxy;

	@Mod.EventHandler
	public void preInit(final FMLPreInitializationEvent event) {
		proxy.preInit();
	}
}