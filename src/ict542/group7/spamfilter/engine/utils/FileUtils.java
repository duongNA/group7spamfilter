package ict542.group7.spamfilter.engine.utils;

import ict542.group7.spamfilter.engine.common.Feature;

import java.io.File;

public class FileUtils {
	
	public static final String FILE_NAME = "feature.data";

	public static File getFileData() {
		return new File(FILE_NAME);
	}
	
	public static Feature parseFeatureLine(String line) {
		String[] parts = line.split("%");
		Feature feature = new Feature(parts[0], Integer.valueOf(parts[1]), Integer.valueOf(parts[2]));
		feature.setProbability(Double.valueOf(parts[3]));
		return feature;
	}
}
