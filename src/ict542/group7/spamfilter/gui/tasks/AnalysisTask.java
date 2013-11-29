package ict542.group7.spamfilter.gui.tasks;

import ict542.group7.spamfilter.engine.SpamFilterEngine;
import ict542.group7.spamfilter.gui.Global;

import javax.swing.JButton;
import javax.swing.SwingWorker;

import org.apache.log4j.Logger;

public class AnalysisTask extends SwingWorker<Void, Void>{
	private static final Logger logger = Logger.getLogger(AnalysisTask.class);
	
	private JButton btnStartAnalysis;
	private String emailFilePath;
	
	private boolean isSpam;
	
	public AnalysisTask(String emailFilePath, JButton btnStartAnalysis) {
		this.btnStartAnalysis = btnStartAnalysis;
		this.emailFilePath = emailFilePath;
	}

	@Override
	protected Void doInBackground() throws Exception {
		SpamFilterEngine engine = Global.engine;
		isSpam = engine.analysisIsSpam(emailFilePath);
		return null;
	}

	@Override
	protected void done() {
		logger.debug("is_spam: " + isSpam);
		btnStartAnalysis.setEnabled(true);
	}
}
