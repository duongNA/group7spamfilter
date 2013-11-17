package ict542.group7.spamfilter.engine;

import ict542.group7.spamfilter.engine.common.Constants;
import ict542.group7.spamfilter.engine.common.DataClearable;
import ict542.group7.spamfilter.engine.components.AbstractDataStorage;
import ict542.group7.spamfilter.engine.components.AnalysisEngine;
import ict542.group7.spamfilter.engine.components.FeatureExtractor;
import ict542.group7.spamfilter.engine.components.MemoryDataStorage;

import java.io.File;

import org.apache.log4j.Logger;

/**
 * Spam filter engine 
 *
 */
public class SpamFilterEngine implements DataClearable {
	
	private static final Logger logger = Logger.getLogger(SpamFilterEngine.class);
	
	private FeatureExtractor featureExtractor;
	private AnalysisEngine analysisEngine;
	private AbstractDataStorage dataStorage;
	
	private EngineExtractListener engineExtractListener;
	
	public SpamFilterEngine() {
		dataStorage = new MemoryDataStorage();
		analysisEngine = new AnalysisEngine(dataStorage);
		featureExtractor = new FeatureExtractor(dataStorage, analysisEngine);
	}
	
	public void train(String spamDirPath, String hamDirPath) {
		File spamDir = new File(spamDirPath);
		File hamDir = new File(hamDirPath);

		File[] spamEmailFiles = spamDir.listFiles();
		File[] hamEmailFiles = hamDir.listFiles();
		
		// extract features from each emails and pass to data storage
		dataStorage.clearData();
		processFileList(spamEmailFiles, Constants.SPAM_EMAIL);
		processFileList(hamEmailFiles, Constants.HAM_EMAIL);
		
		// compute feature probabilties
		dataStorage.computeFeatureProbabilities(spamEmailFiles.length, hamEmailFiles.length);
	}
	
	private void processFileList(File[] fileList, int emailType) {
		int counter = 0;
		for (File file : fileList) {
			if (file.isFile()) {
				featureExtractor.extractFeatures(file.getAbsolutePath(), emailType, FeatureExtractor.FOR_TRAINING);
				counter++;
				if (engineExtractListener != null) {
					engineExtractListener.handleReport(counter, emailType);
				}
			}
		}
	}
	
	public AbstractDataStorage getDataStorage() {
		return dataStorage;
	}
	
	public boolean analysisIsSpam(String emailFilePath) {
		analysisEngine.clearData();
		featureExtractor.extractFeatures(emailFilePath, null, FeatureExtractor.FOR_ANALYSIS);
		return analysisEngine.isSpamEmail();
	}

	@Override
	public void clearData() {
		dataStorage.clearData();
		analysisEngine.clearData();
	}

	public void setEngineExtractListener(EngineExtractListener listener) {
		engineExtractListener = listener;
	}
	
}
