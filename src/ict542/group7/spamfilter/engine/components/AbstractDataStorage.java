package ict542.group7.spamfilter.engine.components;

import ict542.group7.spamfilter.engine.common.Feature;

import java.util.HashMap;
import java.util.Map;

/**
 * Common interface for data storage which is used to store 
 * feature information
 *
 */
public abstract class AbstractDataStorage {
	protected Map<String, Feature> featureMap = new HashMap<String, Feature>();
	
	private int totalOfSpam;
	private int totalOfHam;
	
	public abstract void addFeature(String tokenString, int emailType);
	
	public void setTotalOfSpam(int totalOfSpam) {
		this.totalOfSpam = totalOfSpam;
	}
	
	public void setTotalOfHam(int totalOfHam) {
		this.totalOfHam = totalOfHam;
	}
	
	public void computeFeatureProbabilities() {
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
}
