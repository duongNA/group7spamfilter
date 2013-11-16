package ict542.group7.spamfilter.unittest;

import org.junit.Test;

import ict542.group7.spamfilter.engine.components.AbstractDataStorage;
import ict542.group7.spamfilter.engine.components.AnalysisEngine;
import ict542.group7.spamfilter.engine.components.FeatureExtractor;

public class testAnalysisEngine {
	
	@Test
	public void testIsSpamEmail() {
		AbstractDataStorage storage = new MockDataStorage();
		AnalysisEngine analysisEngine = new AnalysisEngine(storage);
		FeatureExtractor extractor = new FeatureExtractor(storage, analysisEngine);
		
		String emailFilePath = "test/data/email-test";
		extractor.extractFeatures(emailFilePath, null, FeatureExtractor.FOR_ANALYSIS);
		
		System.out.println("is spam email: " +  analysisEngine.isSpamEmail());
	}
}
