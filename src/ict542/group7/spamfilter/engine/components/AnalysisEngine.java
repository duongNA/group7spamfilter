package ict542.group7.spamfilter.engine.components;

import ict542.group7.spamfilter.engine.common.Constants;
import ict542.group7.spamfilter.engine.common.DataClearable;
import ict542.group7.spamfilter.engine.common.Feature;
import ict542.group7.spamfilter.engine.common.FeatureInterestingnessComparator;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Analysis engine: decides whether the email is spam or not
 *
 */
public class AnalysisEngine implements DataClearable {
	private AbstractDataStorage dataStorage;
	
	private Map<String, Feature> emailFeatureMap = new HashMap<String, Feature>();
	
	public AnalysisEngine(AbstractDataStorage dataStorage) {
		this.dataStorage = dataStorage;
	}
	
	public void addEmailFeature(String tokenString) {
		if (emailFeatureMap.keySet().contains(tokenString)) {
			return;
		}
		
		// lockup feature in storage
		Feature feature = dataStorage.findFeature(tokenString);
		if (feature == null) {
			feature = new Feature(tokenString, 0.5);
		}
		
		emailFeatureMap.put(tokenString, feature);
	}
	
	public boolean isSpamEmail() {
		List<Feature> interestList = getInterestFeatureList();
		double combinationProbability = computeBayesianCombination(interestList);
		if (combinationProbability >= Constants.SPAM_PROBABILITY_THRESHOLD) {
			return true;
		} else {
			return false;
		}
		
	}
	
	private List<Feature> getInterestFeatureList() {
		List<Feature> featureList = new LinkedList<Feature>(emailFeatureMap.values());
		// sort feature list by interestingness
		Collections.sort(featureList, new FeatureInterestingnessComparator());
		
		// get n most interest feature
		int n = Math.min(featureList.size(), Constants.NUM_INTEREST_FEATURES);
		return featureList.subList(0, n);
	}
	
	/*
	 * Compute bayesian probability combination by formula
	 * 
	 * (p1*p2*...*pn) / (p1*p2*...*pn + (1-p1)*(1-p2)*...(1-pn))
	 */
	private double computeBayesianCombination(List<Feature> interestList) {
		if (interestList.isEmpty()) {
			return 0;
		}
		
		double a = 1;
		double b = 1;
		for (Feature f : interestList) {
			a *= f.getPropability();
			b *= 1 - f.getPropability();
		}
		
		double res = a/(a+b);
		return res;
	}

	@Override
	public void clearData() {
		emailFeatureMap.clear();
	}
}
