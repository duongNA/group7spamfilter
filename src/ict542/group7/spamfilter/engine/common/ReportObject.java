package ict542.group7.spamfilter.engine.common;

import org.apache.log4j.Logger;

public class ReportObject {
	private final Logger logger = Logger.getLogger(ReportObject.class);
	
	public int numOfSpam;
	
	public int numOfHam;
	
	public int numOfEngineSpam;
	
	public int numOfEngineHam;
	
	public int hamMisAsSpam;
	
	public int spamMisAsHam;

	@Override
	public String toString() {
		String s = "numOfSpam: " + numOfSpam + "\n" +
					"numOfHam: " + numOfHam + "\n" +
					"numOfEngineSpam: " + numOfEngineSpam + "\n" +
					"numOfEngineHam: " + numOfEngineHam;
		return s;
	}
	
	public double computeAccuracy() {
		try {
			if (numOfSpam - spamMisAsHam + hamMisAsSpam == 0) {
				return 0;
			}
//			double accuracy = (numOfSpam - spamMisAsHam) / (numOfSpam - spamMisAsHam + hamMisAsSpam) * 100;
			double accuracy = 100 - ((double)spamMisAsHam)/numOfSpam * 100;
			return accuracy;
		} catch (ArithmeticException ex) {
			logger.error(null, ex);
			return 0;
		}
	}
}
