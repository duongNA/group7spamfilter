package ict542.group7.spamfilter.engine.components;

import ict542.group7.spamfilter.engine.common.Constants;
import ict542.group7.spamfilter.engine.common.Feature;
import ict542.group7.spamfilter.engine.utils.SqlUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * Manages feature information
 */
public class MemoryDataStorage extends AbstractDataStorage {
	private static final Logger logger = Logger.getLogger(MemoryDataStorage.class);
	
	@Override
	public void addFeature(String tokenString, int emailType) {
		//logger.debug("Add feature: " + tokenString);
		
		Feature feature = featureMap.get(tokenString);
		if (feature == null) {
			feature = new Feature(tokenString);
			featureMap.put(tokenString, feature);
		}
	
		if (emailType == Constants.SPAM_EMAIL) {
			feature.increaseOccurInSpamByOne();
		}
		if (emailType == Constants.HAM_EMAIL) {
			feature.increaseOccurInHamByOne();
		}
	}

	@Override
	public void computeFeatureProbabilities(int totalOfSpam, int totalOfHam) {
		// total of feature
		int totalFeatures = featureMap.keySet().size();
		int numOfCompletedFeatures = 0; 
		
		// create file data
		RandomAccessFile raf = null;
		
		int numOfFeatures = featureMap.keySet().size();
		try {
			File file = new File("feature.data");

			if (file.exists()) {
				file.delete();
			}

			raf = new RandomAccessFile(file, "rw");
			
//			String message = totalOfSpam + "%" + totalOfHam + "%" + numOfFeatures + "\n";
//			logger.debug("message");
//			out.write(message);

			for (Map.Entry<String, Feature> entry : featureMap.entrySet()) {
				Feature feature = entry.getValue();
				feature.computeProbability(totalOfSpam, totalOfHam);
				
				// save feature to file
				String message = feature.getTokenString() + "%" + feature.getOccurInSpam() + "%" + 
							feature.getOccurInHam() + "%" + feature.getPropability() + "\n";
				logger.debug(message);
				raf.writeChars(message);
				
				numOfCompletedFeatures++;
				if (computeListener != null) {
					computeListener.onCompleteCompute(numOfCompletedFeatures, totalFeatures);
				}
			}
		} catch (IOException ex) {
			logger.error(null, ex);
		} finally {
			if (raf != null) {
				try {
					raf.close();
				} catch (IOException e) {}
			}
		}
		
		//featureMap.clear();
	}
	
	
}
