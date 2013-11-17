package ict542.group7.spamfilter.engine;

public interface EngineExtractListener {
	public void handleReport(int numOfFiles, int emailType);
}
