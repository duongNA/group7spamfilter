package ict542.group7.spamfilter.engine.common;

/**
 * Class represents for a feature in spam filter engine
 */
public class Feature {
	
	private String tokenString;
	
	private int occurInSpam = 0; // number of occurrences in SPAM emails
	private int occurInHam = 0; // number of occurrences in HAM emails
	private double probabily = 0; // probability of event: this feature occurs a SPAM email
	
	public Feature(String tokenString) {
		this.tokenString = tokenString;
	}
	
	public Feature(String tokenString, int occurInSpam, int occurInHam) {
		this(tokenString);
		this.occurInSpam = occurInSpam;
		this.occurInHam = occurInHam;
	}
	
	public Feature(String tokenString, double probability) {
		this(tokenString);
		this.probabily = probability;
	}
	
	public String getTokenString() {
		return tokenString;
	}
	
	public int getOccurInSpam() {
		return occurInSpam;
	}
	
	/**
	 * Increases the number of occurrences of this feature in SPAM
	 * emails by 1
	 */
	public void increaseOccurInSpamByOne() {
		occurInSpam++;
	}
	
	public void setOccurInSpam(int occurInSpam) {
		this.occurInSpam = occurInSpam;
	}
	
	public int getOccurInHam() {
		return occurInHam;
	}
	
	/**
	 * Increases the number of occurrences of this feature in HAM
	 * emails by 1
	 */
	public void increaseOccurInHamByOne() {
		occurInHam++;
	}
	
	public double getPropability() {
		return probabily;
	}
	
	public void setProbability(double probability) {
		this.probabily = probability;
	}
	
	/**
	 * Compute probability P(SPAM|f) based on Graham formula
	 */
	public void computeProbability(int totalOfSpam, int totalOfHam) {
		// TODO: Code to compute the probability of 
		// this feature occurs in an SPAM email here
		if (isHapax()) {
			probabily = Constants.HAPAXIAL_VALUE;
		} else if (isSingleCorpusOfSpam()) {
			probabily = Constants.SINGLE_CORPUS_PROB;
		} else if (isSingleCorpusOfHam()) {
			probabily = Constants.OPPOSITE_SINGLE_CORPUS_PROB;
		} else {
			/* formula 
			 * P = (SH/TS) / ((SH/TS) + 2*(IH/TI))
			 * SH: occurrence in spam emails
			 * TS: total of spam emails
			 * IH: occurrence in ham emails
			 * TI: total of ham emails
			 * */
			probabily = (occurInSpam/(double)totalOfSpam) / ((occurInSpam/(double)totalOfSpam) + 2*(occurInHam/(double)totalOfHam));
		}
	}
	
	public double getInterestingness() {
		return Math.abs(0.5 - probabily);
	}
	
	/**
	 * Checks if feature only occurs in spam emails
	 */
	private boolean isSingleCorpusOfSpam() {
		return occurInHam == 0;
	}
	
	/**
	 * Check if feature only occurs in ham emails 
	 */
	private boolean isSingleCorpusOfHam() {
		return occurInSpam == 0;
	}
	
	/**
	 * Check if occurInSpam + 2*occurInHam < hapaxes threshold
	 * @return
	 */
	private boolean isHapax() {
		return (occurInSpam + 2*occurInHam) < Constants.HAPAXES_THRESHOLD;
	}
}
