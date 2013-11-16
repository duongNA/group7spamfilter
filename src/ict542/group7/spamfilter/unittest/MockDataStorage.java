package ict542.group7.spamfilter.unittest;

import org.apache.log4j.Logger;

import ict542.group7.spamfilter.engine.components.AbstractDataStorage;

public class MockDataStorage extends AbstractDataStorage {
	private static final Logger logger = Logger.getLogger(MockDataStorage.class);

	@Override
	public void addFeature(String tokenString, int emailType) {
		logger.debug("addFeature called with param: token = " + tokenString + ", emailType = " + emailType);
	}
}
