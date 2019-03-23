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
		List<TemplateSaveFormat> templates = new ArrayList<TemplateSaveFormat>();
		for(SaveFormatComparator saves : (List<SaveFormatComparator>) super.getSaveList()) {
			templates.add(new TemplateSaveFormat(saves));
		}
		return templates;
	}
}