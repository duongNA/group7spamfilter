package ict542.group7.spamfilter.unittest;

import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;

import ict542.group7.spamfilter.engine.SpamFilterEngine;
import ict542.group7.spamfilter.engine.common.Feature;
import ict542.group7.spamfilter.engine.components.AbstractDataStorage;

public class testSpamFilterEngine {

	@Test
	public void testTrain() {
		try {
			SpamFilterEngine engine = new SpamFilterEngine();

			String spamDirPath = "test/data/spam";
			String hamDirPath = "test/data/ham";
			engine.train(spamDirPath, hamDirPath);

			AbstractDataStorage dataStorage = engine.getDataStorage();
			Map<String, Feature> map = dataStorage.getFeatureMap();

			System.out.println("---- Training result ----");
			for (Entry<String, Feature> entry : map.entrySet()) {
				System.out.println(entry.getValue().toString());
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testAnalysisIsSpam() {
		SpamFilterEngine engine = new SpamFilterEngine();

		String spamDirPath = "test/data/spam";
		String hamDirPath = "test/data/ham";
		engine.train(spamDirPath, hamDirPath);
		
		String emailPath = "test/data/email-test";
		boolean isSpam = engine.analysisIsSpam(emailPath);
		System.out.println("is spam: " + isSpam);
	}
}
