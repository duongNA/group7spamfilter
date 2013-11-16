package ict542.group7.spamfilter.unittest;

import ict542.group7.spamfilter.engine.common.Feature;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class testFeature {

	@Test
	public void testComputeProbability() {
		List<Feature> list = new ArrayList<Feature>();
		
		list.add(new Feature("fun", 19, 9));
		list.add(new Feature("girlfriend", 4, 0));
		list.add(new Feature("mariners", 0, 7));
		list.add(new Feature("tell", 8, 30));
		list.add(new Feature("the", 96, 48));
		list.add(new Feature("vehicle", 11, 3));
		list.add(new Feature("viagra", 20, 1));
		
		for (Feature f : list) {
			f.computeProbability(224, 112);
			System.out.println(f.getTokenString() + " : " + f.getPropability());
		}
	}
}
