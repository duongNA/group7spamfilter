package ict542.group7.spamfilter.engine;

import ict542.group7.spamfilter.engine.components.AbstractDataStorage;
import ict542.group7.spamfilter.engine.components.AnalysisEngine;
import ict542.group7.spamfilter.engine.components.FeatureExtractor;
import ict542.group7.spamfilter.engine.components.MemoryDataStorage;

/**
 * Spam filter engine 
 *
 */
public class SpamFilterEngine {
	
	private FeatureExtractor featureExtractor;
	private AnalysisEngine analysisEngine;
	private AbstractDataStorage dataStorage;
	
	public SpamFilterEngine() {
		dataStorage = new MemoryDataStorage();
		analysisEngine = new AnalysisEngine(dataStorage);
		featureExtractor = new FeatureExtractor(dataStorage, analysisEngine);
	}
	
	public void train(String spamDir, String hamDir) {
		
	}
	
//	public boolean analysis(String emailFilePath) {
//		
//	}

}
