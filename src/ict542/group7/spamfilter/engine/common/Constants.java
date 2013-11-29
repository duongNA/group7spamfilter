package ict542.group7.spamfilter.engine.common;

public class Constants {

	public static final int SPAM_EMAIL = 0;
	
	public static final int HAM_EMAIL = 1;
	
	public static final String EMAIL_DELIMITERS = "\n\t\f\r !@#$%^&*()_+-={}:[];'<>,./`~)\"";
	
	public static final int MIN_TOKEN_LENG = 3;
	
	public static final int GRAHAM_BIAS_FACTOR = 2;
	
	public static final int HAPAXES_THRESHOLD = 2;
	
	public static final double HAPAXIAL_VALUE = 0.4;
	
	public static final double SINGLE_CORPUS_PROB = 0.99;
	
	public static final double OPPOSITE_SINGLE_CORPUS_PROB = 0.01;
	
	public static final int NUM_INTEREST_FEATURES = 15;
	
	public static final double SPAM_PROBABILITY_THRESHOLD = 0.9;
}
