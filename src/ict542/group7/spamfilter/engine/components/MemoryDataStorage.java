package ict542.group7.spamfilter.engine.components;

import ict542.group7.spamfilter.engine.common.Constants;
import ict542.group7.spamfilter.engine.common.Feature;

import java.util.HashMap;
import java.util.Map;

/**
 * Manages feature information
 */
public class MemoryDataStorage extends AbstractDataStorage {
	private Map<String, Feature> featureMap = new HashMap<>();
	
	@Override
	public void addFeature(String tokenString, int emailType) {
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
