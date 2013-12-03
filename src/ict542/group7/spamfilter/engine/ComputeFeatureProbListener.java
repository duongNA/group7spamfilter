package ict542.group7.spamfilter.engine;

public interface ComputeFeatureProbListener {
	public void onCompleteCompute(int numOfCompletedFeature, int totalFeatures);
}
