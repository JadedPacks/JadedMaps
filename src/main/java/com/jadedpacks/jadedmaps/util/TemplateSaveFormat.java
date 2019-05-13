package com.jadedpacks.jadedmaps.util;

import com.jadedpacks.jadedmaps.JadedMaps;
import net.minecraft.client.Minecraft;
import net.minecraft.world.EnumGameType;
import net.minecraft.world.storage.SaveFormatComparator;

import java.io.*;

public class TemplateSaveFormat extends SaveFormatComparator {
	TemplateSaveFormat(final SaveFormatComparator save) {
		super(save.getFileName(), save.getDisplayName(), save.getLastTimePlayed(), save.sizeOnDisk, EnumGameType.SURVIVAL, save.requiresConversion(), false, false);
	}

	public void copy(final String folder, final String name) {
		final Minecraft mc = Minecraft.getMinecraft();
		final File target = new File(new File(mc.mcDataDir, "saves"), folder);
		if(target.exists()) {
			return;
		}
		target.mkdir();
		try {
			copyDirectory(getPath(), target);
		} catch(IOException e) {
			e.printStackTrace();
		}
		mc.getSaveLoader().renameWorld(folder, name);
	}

	private File getPath() {
		return new File(JadedMaps.mapsDir, getFileName());
	}

	private void copyDirectory(final File sourceLocation, final File targetLocation) throws IOException {
		if(sourceLocation.isDirectory()) {
			if(!targetLocation.exists()) {
				targetLocation.mkdir();
			}
			final String[] children = sourceLocation.list();
			if(children != null) {
				for(String child : children) {
					copyDirectory(new File(sourceLocation, child), new File(targetLocation, child));
				}
			}
		} else {
			final InputStream in = new FileInputStream(sourceLocation);
			final OutputStream out = new FileOutputStream(targetLocation);
			final byte[] buf = new byte[1024];
			int len;
			while((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			in.close();
			out.close();
		}
	}
}