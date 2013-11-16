package ict542.group7.spamfilter.unittest;

import ict542.group7.spamfilter.engine.common.Constants;
import ict542.group7.spamfilter.engine.components.AbstractDataStorage;
import ict542.group7.spamfilter.engine.components.AnalysisEngine;
import ict542.group7.spamfilter.engine.components.FeatureExtractor;

import org.junit.Test;

public class testFeatureExtractor {
	
	@Test
	public void testExtractFeatureForTraining() {
		AbstractDataStorage storage = new MockDataStorage();
		AnalysisEngine analysisEngine = new AnalysisEngine(storage);
		FeatureExtractor extractor = new FeatureExtractor(storage, analysisEngine);
		
		String emailFilePath = "test/data/011";
		extractor.extractFeatures(emailFilePath, Constants.SPAM_EMAIL, FeatureExtractor.FOR_TRAINING);
		
		emailFilePath = "test/data/email-test";
		extractor.extractFeatures(emailFilePath, Constants.SPAM_EMAIL, FeatureExtractor.FOR_TRAINING);
	}
}
