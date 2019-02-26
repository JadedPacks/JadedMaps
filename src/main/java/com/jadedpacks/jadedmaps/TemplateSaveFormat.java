package com.jadedpacks.jadedmaps;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.EnumGameType;
import net.minecraft.world.storage.SaveFormatComparator;
import net.minecraft.world.storage.WorldInfo;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

class TemplateSaveFormat extends SaveFormatComparator {
	TemplateSaveFormat(final SaveFormatComparator save) {
		super(save.getFileName(), save.getDisplayName(), save.getLastTimePlayed(), save.sizeOnDisk, EnumGameType.SURVIVAL, save.requiresConversion(), false, false);
	}

	ResourceLocation getIcon() {
		final File icon = new File(getPath(), "icon.png");
		if(!icon.exists()) {
			return null;
		}
		try {
			final BufferedImage bufferedImage = ImageIO.read(icon);
			DynamicTexture texture = new DynamicTexture(bufferedImage.getWidth(), bufferedImage.getHeight());
			bufferedImage.getRGB(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight(), texture.getTextureData(), 0, bufferedImage.getWidth());
			texture.updateDynamicTexture();
			Minecraft.getMinecraft().getTextureManager().loadTexture(new ResourceLocation("jadedmaps", "icon/" + getFileName() + "/icon"), texture);
		} catch(IOException e) {
			return null;
		}
		return null;
	}

	void copy(final String folder, final String name) {
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
			String[] children = sourceLocation.list();
			if(children != null) {
				for(String child : children) {
					copyDirectory(new File(sourceLocation, child), new File(targetLocation, child));
				}
			}
		} else {
			InputStream in = new FileInputStream(sourceLocation);
			OutputStream out = new FileOutputStream(targetLocation);
			byte[] buf = new byte[1024];
			int len;
			while((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			in.close();
			out.close();
		}
	}
}