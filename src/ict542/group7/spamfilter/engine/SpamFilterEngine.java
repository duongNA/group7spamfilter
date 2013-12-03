package ict542.group7.spamfilter.engine;

import ict542.group7.spamfilter.engine.common.Constants;
import ict542.group7.spamfilter.engine.common.DataClearable;
import ict542.group7.spamfilter.engine.common.Feature;
import ict542.group7.spamfilter.engine.common.ReportObject;
import ict542.group7.spamfilter.engine.components.AbstractDataStorage;
import ict542.group7.spamfilter.engine.components.AnalysisEngine;
import ict542.group7.spamfilter.engine.components.FeatureExtractor;
import ict542.group7.spamfilter.engine.components.MemoryDataStorage;
import ict542.group7.spamfilter.engine.components.SqlDataStorage;
import ict542.group7.spamfilter.engine.utils.FileUtils;

import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.List;

import org.apache.derby.iapi.services.io.FileUtil;
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
	private MeasureEngineListener measureListener;
	
	public SpamFilterEngine() {
		//dataStorage = new SqlDataStorage();
		dataStorage = new MemoryDataStorage();
		analysisEngine = new AnalysisEngine(dataStorage);
		featureExtractor = new FeatureExtractor(dataStorage, analysisEngine);
		
		clearData();
	}
	
	public void train(String spamDirPath, String hamDirPath) {
		File spamDir = new File(spamDirPath);
		File hamDir = new File(hamDirPath);

		File[] spamEmailFiles = spamDir.listFiles();
		File[] hamEmailFiles = hamDir.listFiles();
		
		// extract features from each emails and pass to data storage
		dataStorage.clearData();
		logger.debug("start training...");
		processFileList(spamEmailFiles, Constants.SPAM_EMAIL);
		processFileList(hamEmailFiles, Constants.HAM_EMAIL);
		
		// compute feature probabilties
		dataStorage.computeFeatureProbabilities(spamEmailFiles.length, hamEmailFiles.length);
	}
	
	private void processFileList(File[] fileList, int emailType) {
		logger.debug("process spam files...");
		int counter = 0;
		for (File file : fileList) {
			if (file.isFile()) {
				logger.debug("extract feature from file: " + file.getName());
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
	
	public AnalysisEngine getAnalysisEngine() {
		return analysisEngine;
	}
	
	public boolean analysisIsSpam(String emailFilePath) {
		analysisEngine.clearData();
		featureExtractor.extractFeatures(emailFilePath, null, FeatureExtractor.FOR_ANALYSIS);
		return analysisEngine.isSpamEmail();
	}
	
	public ReportObject measureEngine(String inboxPath) {
		ReportObject report = new ReportObject();
		
		File file = new File(inboxPath);
		if (!file.isDirectory()) {
			return null;
		}
		
		int numOfCompleted = 0;
		for (File email : file.listFiles()) {
			boolean isActualSpam = false;
			if (email.getName().startsWith("spam")) {
				report.numOfSpam++;
				isActualSpam = true;
			} else {
				report.numOfHam++;
			}
			
			boolean isSpam = analysisIsSpam(email.getAbsolutePath());
			if (isSpam) {
				report.numOfEngineSpam++;
				
				if (!isActualSpam) {
					// ham misclassified as spam
					report.hamMisAsSpam++;
				}
			} else {
				report.numOfEngineHam++;
				
				if (isActualSpam) {
					// spam misclassified as ham
					report.spamMisAsHam++;
				}
			}
			
			numOfCompleted++;
			if (measureListener != null) {
				measureListener.onCompleteNewEmail(numOfCompleted);
			}
		}
		
		return report;
	}

	@Override
	public void clearData() {
		dataStorage.clearData();
		analysisEngine.clearData();
	}

	public void setEngineExtractListener(EngineExtractListener listener) {
		engineExtractListener = listener;
	}
	
	public void setMeasureListener(MeasureEngineListener listener) {
		measureListener = listener;
	}
	
	public void setComputeFeatureProbabilityListener(ComputeFeatureProbListener listener) {
		dataStorage.setComputeFeatureProbListener(listener);
	}
	
	public Long getTrainedFeatures(long lastPosition, int setSize, List<Feature> featureList) {
		RandomAccessFile raf = null;
		try {
			int linesRead = 0;
			raf = new RandomAccessFile(FileUtils.FILE_NAME, "rw");
			raf.seek(lastPosition);
			char c;
			StringBuffer k = new StringBuffer();

			while (linesRead < setSize) {
				while ((c = raf.readChar()) != '\n') {
					k.append(c);
					lastPosition += 2;
				}
				lastPosition += 2;
				featureList.add(FileUtils.parseFeatureLine(k.toString()));
				k.setLength(0);
				linesRead++;
			}
			
			return lastPosition;
		} catch (EOFException eof) {
			logger.debug("end of file", eof);
			return null;
		} catch (IOException e) {
			logger.error(null, e);
			return null;
		} finally {
			if (raf != null) {
				try {
					raf.close();
				} catch (IOException e) {}
			}
		}
	}
}
