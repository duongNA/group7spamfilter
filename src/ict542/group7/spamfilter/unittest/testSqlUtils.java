package ict542.group7.spamfilter.unittest;

import org.junit.Test;

import ict542.group7.spamfilter.engine.common.Feature;
import ict542.group7.spamfilter.engine.utils.SqlUtils;

public class testSqlUtils {
	
	@Test
	public void testCreateDatabase() {
		SqlUtils.initializeDB();
		
		Feature feature = new Feature("word");
		SqlUtils.insertFeature(feature);
		SqlUtils.listFeatures();
		System.out.println("-----------------------");
		
		feature = SqlUtils.searchFeatureByTokenString("word");
		feature.increaseOccurInHamByOne();
		feature.increaseOccurInSpamByOne();
		feature.setProbability(99.7);
		SqlUtils.updateFeature(feature);
		SqlUtils.listFeatures();
	}
	
	@Test
	public void testClearTableFeature() {
		SqlUtils.listFeatures();
		System.out.println("---------------------");
		SqlUtils.clearTableFeature();
		SqlUtils.listFeatures();
	}
}
