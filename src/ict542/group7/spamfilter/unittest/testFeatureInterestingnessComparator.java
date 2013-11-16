package ict542.group7.spamfilter.unittest;

import ict542.group7.spamfilter.engine.common.Feature;
import ict542.group7.spamfilter.engine.common.FeatureInterestingnessComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

public class testFeatureInterestingnessComparator {
	
	@Test
	public void testComparator() {
		List<Feature> list = new ArrayList<Feature>();
		
		list.add(new Feature("f1", 0.6));
		list.add(new Feature("f2", 0.1));
		list.add(new Feature("f3", 0.8));
		
		Collections.sort(list, new FeatureInterestingnessComparator());
		
		list = list.subList(0, 2);
		for (Feature f : list) {
			System.out.println(f.getTokenString() + " probability: " + f.getPropability() + ", interestingness: " + f.getInterestingness());
		}
		
		
	}
}
