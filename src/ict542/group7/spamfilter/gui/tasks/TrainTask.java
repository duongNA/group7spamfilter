package ict542.group7.spamfilter.gui.tasks;

import java.awt.Component;
import java.awt.Frame;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ict542.group7.spamfilter.engine.EngineExtractListener;
import ict542.group7.spamfilter.engine.SpamFilterEngine;
import ict542.group7.spamfilter.engine.common.Constants;
import ict542.group7.spamfilter.engine.common.Feature;
import ict542.group7.spamfilter.gui.Global;
import ict542.group7.spamfilter.gui.tableunit.FeatureDataModel;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingWorker;
import javax.swing.table.TableModel;

public class TrainTask extends SwingWorker<Void, Void> implements EngineExtractListener {
	public static final String SPAM_PROGRESS = "spam-progress";
	public static final String HAM_PROGRESS = "ham-progress";
	
	private JButton btnStart;
	private JLabel lbNumOfFeature;
	private JTable tbFeature;
	private String spamDir;
	private String hamDir;
	private int totalOfSpam;
	private int totalOfHam;
	private Component rootWindow;
	
	private int spamProgress = 0;
	private int hamProgress = 0;
	
	public TrainTask(Component rootWindow, JButton btnStart, JTable tbFeature, JLabel lbNumOfFeature, String spamDir, String hamDir, int totalOfSpam, int totalOfHam) {
		this.btnStart = btnStart;
		this.lbNumOfFeature = lbNumOfFeature;
		this.tbFeature = tbFeature;
		this.spamDir = spamDir;
		this.hamDir = hamDir;
		this.totalOfSpam = totalOfSpam;
		this.totalOfHam = totalOfHam;
		this.rootWindow = rootWindow;
	}
	
	@Override
	protected Void doInBackground() throws Exception {
		SpamFilterEngine engine = Global.engine;
		// clear data
		engine.clearData();
		
		engine.setEngineExtractListener(this);
		engine.train(spamDir, hamDir);
		return null;
	}

	@Override
	public void handleReport(int numOfFiles, int emailType) {
		if (emailType == Constants.SPAM_EMAIL) {
			int progress = numOfFiles*100/totalOfSpam;
			if (spamProgress != progress) {
				firePropertyChange(SPAM_PROGRESS, spamProgress, progress);
				spamProgress = progress;
			}
		} else {
			int progress = numOfFiles*100/totalOfHam;
			if (hamProgress != progress) {
				firePropertyChange(HAM_PROGRESS, hamProgress, progress);
				hamProgress = progress;
			}
		}
	}

	@Override
	protected void done() {
		Map<String, Feature> featureMap = Global.engine.getDataStorage().getFeatureMap();
		
		List<Feature> newFeatureList = new LinkedList<Feature>(featureMap.values());
		TableModel model = tbFeature.getModel();
		if (model instanceof FeatureDataModel) {
			((FeatureDataModel) model).setFeatureList(newFeatureList);
		}
		
		lbNumOfFeature.setText(String.valueOf(newFeatureList.size()));
		
		JOptionPane.showMessageDialog(rootWindow, "Training completed");
		btnStart.setEnabled(true);
	}
	
	private void updateFeatureTable() {
		
	}
}
