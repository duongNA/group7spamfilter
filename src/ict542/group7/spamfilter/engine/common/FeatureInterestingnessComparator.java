package ict542.group7.spamfilter.engine.common;

import java.util.Comparator;

public class FeatureInterestingnessComparator implements Comparator<Feature> {

	@Override
	public int compare(Feature f1, Feature f2) {
		double i1 = f1.getInterestingness();
		double i2 = f2.getInterestingness();
		if (i1 < i2) {
			return -1;
		} else if (i1 > i2) {
			return 1;
		} else {
			return 0;
		}
	}

}
