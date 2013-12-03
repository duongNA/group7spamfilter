package ict542.group7.spamfilter.engine.components;

import ict542.group7.spamfilter.engine.ComputeFeatureProbListener;
import ict542.group7.spamfilter.engine.common.DataClearable;
import ict542.group7.spamfilter.engine.common.Feature;

import java.util.HashMap;
import java.util.Map;

/**
 * Common interface for data storage which is used to store 
 * feature information
 *
 */
public abstract class AbstractDataStorage implements DataClearable {
	protected Map<String, Feature> featureMap = new HashMap<String, Feature>();
	
	protected ComputeFeatureProbListener computeListener;
	
	public abstract void addFeature(String tokenString, int emailType);
	
	public void computeFeatureProbabilities(int totalOfSpam, int totalOfHam) {
		for (Map.Entry<String, Feature> entry : featureMap.entrySet()) {
			Feature feature = entry.getValue();
			feature.computeProbability(totalOfSpam, totalOfHam);
		}
	}
	
	public Map<String, Feature> getFeatureMap() {
		return featureMap;
	}
	
	public Feature findFeature(String tokenString) {
		return featureMap.get(tokenString);
	}

	@Override
	public void clearData() {
		featureMap.clear();
	}
	
	public void setComputeFeatureProbListener(ComputeFeatureProbListener listener) {
		computeListener = listener;
	}
}
