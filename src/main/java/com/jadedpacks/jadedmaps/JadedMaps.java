package com.jadedpacks.jadedmaps;

import com.jadedpacks.jadedmaps.proxy.CommonProxy;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

import java.io.File;

@Mod(modid = "jadedmaps", name = "JadedMaps", version = "@VERSION@")
public class JadedMaps {
	public static File mapsDir;
	@SidedProxy(clientSide = "com.jadedpacks.jadedmaps.proxy.ClientProxy", serverSide = "com.jadedpacks.proxy.jadedmaps.CommonProxy")
	public static CommonProxy proxy;

	@Mod.EventHandler
	public void preInit(final FMLPreInitializationEvent event) {
		proxy.preInit();
	}
}