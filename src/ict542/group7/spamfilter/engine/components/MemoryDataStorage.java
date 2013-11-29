package ict542.group7.spamfilter.engine.components;

import ict542.group7.spamfilter.engine.common.Constants;
import ict542.group7.spamfilter.engine.common.Feature;
import ict542.group7.spamfilter.engine.utils.SqlUtils;

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
}
