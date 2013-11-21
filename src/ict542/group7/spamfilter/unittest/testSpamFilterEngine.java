package ict542.group7.spamfilter.unittest;

import ict542.group7.spamfilter.engine.SpamFilterEngine;

import org.junit.Test;

public class testSpamFilterEngine {

	@Test
	public void testTrain() {
		try {
			SpamFilterEngine engine = new SpamFilterEngine();

			String spamDirPath = "/home/letmsee/Desktop/build_data_set/testSet/spam";
			String hamDirPath = "/home/letmsee/Desktop/build_data_set/testSet/ham";
			engine.train(spamDirPath, hamDirPath);

//			AbstractDataStorage dataStorage = engine.getDataStorage();
//			Map<String, Feature> map = dataStorage.getFeatureMap();
//
//			System.out.println("---- Training result ----");
//			for (Entry<String, Feature> entry : map.entrySet()) {
//				System.out.println(entry.getValue().toString());
//			}
			
			
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
