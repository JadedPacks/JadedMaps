package com.jadedpacks.jadedmaps;

import net.minecraft.world.chunk.storage.AnvilSaveConverter;
import net.minecraft.world.storage.SaveFormatComparator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TemplateSaveLoader extends AnvilSaveConverter {
	TemplateSaveLoader(final File folder) {
		super(folder);
	}

	@Override
	public List<TemplateSaveFormat> getSaveList() {
		List<TemplateSaveFormat> templates = new ArrayList<>();
		for(Object saves : super.getSaveList()) {
			templates.add(new TemplateSaveFormat((SaveFormatComparator) saves));
		}
		return templates;
	}
}