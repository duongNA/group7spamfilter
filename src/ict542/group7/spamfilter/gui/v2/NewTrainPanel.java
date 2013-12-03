package ict542.group7.spamfilter.gui.v2;

import ict542.group7.spamfilter.engine.ComputeFeatureProbListener;
import ict542.group7.spamfilter.engine.EngineExtractListener;
import ict542.group7.spamfilter.engine.SpamFilterEngine;
import ict542.group7.spamfilter.engine.common.Constants;
import ict542.group7.spamfilter.engine.common.Feature;
import ict542.group7.spamfilter.gui.Global;
import ict542.group7.spamfilter.gui.tableunit.FeatureDataModel;
import ict542.group7.spamfilter.gui.utils.SwingUtils;

import javax.swing.JPanel;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JSeparator;
import javax.swing.JProgressBar;

public class NewTrainPanel extends JPanel implements ActionListener, PropertyChangeListener {
	private static final int NUM_FEATURES = 100;
	
	private int numOfSpam;
	private int numOfHam;
	private int totalOfFeature;
	private Long lastPosition; 
	
	private JTable tbFeature;
	private JTextField tfSpamDir;
	private JTextField tfHamDir;
	private JButton btnHamBrowse;
	private JButton btnSpamBrowse;
	
	private JFileChooser fileChooser;
	private JButton btnStartTraining;
	private JLabel lbNumOfSpam;
	private JLabel lbNumOfHam;
	private JProgressBar prbSpamEmails;
	private JProgressBar prbHamEmails;
	private JLabel lbNumOfProcessedSpam;
	private JLabel lbNumOfProcessedFeature;
	private JLabel lbNumOfFeatures;
	private JLabel lbNumOfProcessedHam;
	
	
	
	private JProgressBar prbFeatures;
	private JButton btnNextFeatures;
	
	
	/**
	 * Create the panel.
	 */
	public NewTrainPanel() {
		setLayout(new GridLayout(2, 0, 0, 0));
		
		fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
		JPanel panel_1 = new JPanel();
		add(panel_1);
		panel_1.setLayout(new MigLayout("", "[][][grow]", "[24.00][][][][][][][][][][][]"));
		
		btnStartTraining = new JButton("Start training");
		panel_1.add(btnStartTraining, "cell 0 0");
		btnStartTraining.addActionListener(this);
		
		JSeparator separator_1 = new JSeparator();
		panel_1.add(separator_1, "cell 0 1");
		
		JLabel lblPathToSpam = new JLabel("Path to spam training set");
		panel_1.add(lblPathToSpam, "cell 0 2");
		
		tfSpamDir = new JTextField();
		panel_1.add(tfSpamDir, "flowx,cell 2 2,growx");
		tfSpamDir.setColumns(10);
		
		btnSpamBrowse = new JButton("Browse");
		panel_1.add(btnSpamBrowse, "cell 2 2");
		btnSpamBrowse.addActionListener(this);
		
		JLabel lblPathToNonspam = new JLabel("Path to non-spam training set");
		panel_1.add(lblPathToNonspam, "cell 0 3");
		
		tfHamDir = new JTextField();
		panel_1.add(tfHamDir, "flowx,cell 2 3,growx");
		tfHamDir.setColumns(10);
		
		btnHamBrowse = new JButton("Browse");
		panel_1.add(btnHamBrowse, "cell 2 3");
		btnHamBrowse.addActionListener(this);
		
		JSeparator separator = new JSeparator();
		panel_1.add(separator, "cell 0 4");
		
		JLabel lblNumberOfSpam = new JLabel("Total number of spam emails");
		panel_1.add(lblNumberOfSpam, "cell 0 5");
		
		lbNumOfSpam = new JLabel("0");
		panel_1.add(lbNumOfSpam, "cell 2 5");
		
		JLabel lblTotalNumberOf = new JLabel("Total number of non-spam emails");
		panel_1.add(lblTotalNumberOf, "cell 0 6");
		
		lbNumOfHam = new JLabel("0");
		panel_1.add(lbNumOfHam, "cell 2 6");
		
		JLabel lblProcessingSpamEmails = new JLabel("Processing spam emails");
		panel_1.add(lblProcessingSpamEmails, "cell 0 7");
		
		prbSpamEmails = new JProgressBar();
		prbSpamEmails.setStringPainted(true);
		panel_1.add(prbSpamEmails, "flowx,cell 2 7,alignx left,growy");
		
		lbNumOfProcessedSpam = new JLabel("");
		panel_1.add(lbNumOfProcessedSpam, "cell 2 7");
		
		JLabel lblProcessingNonspamEmails = new JLabel("Processing non-spam emails");
		panel_1.add(lblProcessingNonspamEmails, "cell 0 8");
		
		prbHamEmails = new JProgressBar();
		prbHamEmails.setStringPainted(true);
		panel_1.add(prbHamEmails, "flowx,cell 2 8,growy");
		
		lbNumOfProcessedHam = new JLabel("");
		panel_1.add(lbNumOfProcessedHam, "cell 2 8");
		
		JSeparator separator_2 = new JSeparator();
		panel_1.add(separator_2, "cell 0 9");
		
		JLabel lblNumberOfExtracted = new JLabel("Number of extracted features");
		panel_1.add(lblNumberOfExtracted, "cell 0 10");
		
		lbNumOfFeatures = new JLabel("");
		panel_1.add(lbNumOfFeatures, "cell 2 10");
		
		JLabel lblComputingProbabilityGiven = new JLabel("Computing probability of feature");
		panel_1.add(lblComputingProbabilityGiven, "cell 0 11");
		
		prbFeatures = new JProgressBar();
		prbFeatures.setStringPainted(true);
		panel_1.add(prbFeatures, "flowx,cell 2 11,growy");
		
		lbNumOfProcessedFeature = new JLabel("");
		panel_1.add(lbNumOfProcessedFeature, "cell 2 11");
		
		JPanel panel = new JPanel();
		add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_2 = new JPanel();
		panel.add(panel_2, BorderLayout.SOUTH);
		panel_2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		btnNextFeatures = new JButton("Display next 100 features");
		panel_2.add(btnNextFeatures);
		btnNextFeatures.addActionListener(this);
		
		JScrollPane scrollPane = new JScrollPane();
		panel.add(scrollPane, BorderLayout.CENTER);
		
		tbFeature = new JTable();
		
		LinkedList<Feature> featureList = new LinkedList<>();
		tbFeature.setModel(new FeatureDataModel(featureList));

		scrollPane.setViewportView(tbFeature);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		
		if (source == btnSpamBrowse || source == btnHamBrowse) {
			int returnVal = fileChooser.showOpenDialog(SwingUtilities.getWindowAncestor(this));
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				String dirPath = fileChooser.getSelectedFile().getAbsolutePath();
				if (source == btnSpamBrowse) {
					tfSpamDir.setText(dirPath);
				} else {
					tfHamDir.setText(dirPath);
				}
			}
		} else if (source == btnStartTraining) {
			if (tfSpamDir.getText().trim().isEmpty()) {
				JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this), "Please choose spam training set");
			} else if (tfHamDir.getText().trim().isEmpty()) {
				JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this), "Please choose non-spam training set");
			} else {
			
				numOfSpam = SwingUtils.getNumOfFiles(tfSpamDir.getText());
				numOfHam = SwingUtils.getNumOfFiles(tfHamDir.getText());
				lbNumOfSpam.setText(String.valueOf(numOfSpam));
				lbNumOfHam.setText(String.valueOf(numOfHam));
			
				prbSpamEmails.setValue(0);
				prbHamEmails.setValue(0);
			
				// start training
				btnStartTraining.setEnabled(false);
				
				numOfSpam = SwingUtils.getNumOfFiles(tfSpamDir.getText());
				numOfHam = SwingUtils.getNumOfFiles(tfHamDir.getText());
				lbNumOfSpam.setText(String.valueOf(numOfSpam));
				lbNumOfHam.setText(String.valueOf(numOfHam));
				
				// progres bar
				prbSpamEmails.setValue(0);
				lbNumOfProcessedSpam.setText("0");
				prbHamEmails.setValue(0);
				lbNumOfProcessedHam.setText("0");
				
				totalOfFeature = 0;
				lbNumOfFeatures.setText("");
				prbFeatures.setValue(0);
				lbNumOfProcessedFeature.setText("0");
				
				btnNextFeatures.setEnabled(false);
				
				FeatureDataModel model = (FeatureDataModel) tbFeature.getModel();
				model.clearList();
				
				TrainTask task = new TrainTask(this, numOfSpam, numOfHam, tfSpamDir.getText(), tfHamDir.getText());
				task.addPropertyChangeListener(this);
				task.execute();
			}
		} else if (source == btnNextFeatures) {
			List<Feature> featureList = new LinkedList<Feature>();
			lastPosition = Global.engine.getTrainedFeatures(lastPosition, NUM_FEATURES, featureList);
			if (lastPosition == null) {
				btnNextFeatures.setEnabled(false);
			}
			
			FeatureDataModel model = (FeatureDataModel) tbFeature.getModel();
			model.addFeatures(featureList);
		}
	}
	
	public void trainDone() {
		btnStartTraining.setEnabled(true);
		lastPosition = new Long(0);

		List<Feature> featureList = new LinkedList<Feature>();
		lastPosition = Global.engine.getTrainedFeatures(lastPosition, NUM_FEATURES, featureList);
		if (lastPosition == null) {
			btnNextFeatures.setEnabled(false);
		} else {
			btnNextFeatures.setEnabled(true);
		}
		
		FeatureDataModel model = (FeatureDataModel) tbFeature.getModel();
		model.addFeatures(featureList);
		
		JOptionPane.showMessageDialog(SwingUtils.getRootWindow(this), "Train completed!");
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (TrainTask.NUM_PROCESSED_SPAM.equals(evt.getPropertyName())) {
			int newValue = (Integer) evt.getNewValue();
			lbNumOfProcessedSpam.setText(String.valueOf(newValue));
			
			int progress = newValue * 100 / numOfSpam;
			prbSpamEmails.setValue(progress);
			prbSpamEmails.setString(progress + "%");
		} else if (TrainTask.NUM_PROCESSED_HAM.equals(evt.getPropertyName())) {
			int newValue = (Integer) evt.getNewValue();
			lbNumOfProcessedHam.setText(String.valueOf(newValue));
			
			int progress = newValue * 100 / numOfHam;
			prbHamEmails.setValue(progress);
			prbHamEmails.setString(progress + "%");
		} else if (TrainTask.FEATURE_INFO.equals(evt.getPropertyName())) {
			String newInfo = (String) evt.getNewValue();
			int dashPosition = newInfo.indexOf("_");
			
			int tmpTotal = Integer.valueOf(newInfo.substring(dashPosition + 1));
			if (totalOfFeature != tmpTotal) {
				totalOfFeature = tmpTotal;
				lbNumOfFeatures.setText(String.valueOf(totalOfFeature));
			}
			
			int completedFeatures = Integer.valueOf(newInfo.substring(0, dashPosition));
			lbNumOfProcessedFeature.setText(String.valueOf(completedFeatures));
			int progress = completedFeatures * 100 / totalOfFeature;
			prbFeatures.setValue(progress);
		}
	}
}

class TrainTask extends SwingWorker<Void, Void> implements EngineExtractListener, ComputeFeatureProbListener {
	public static final String NUM_PROCESSED_SPAM = "num-processed-spam";
	public static final String NUM_PROCESSED_HAM = "num-processed-ham";
	public static final String FEATURE_INFO = "feature-info";
	
	private int mTotalOfSpam;
	private int mTotalOfHam;
	private String mSpamDir;
	private String mHamDir;
	
	private int mProcessedSpam;
	private int mProcessedHam;
	private String mFeatureInfo; 
	
	private NewTrainPanel mPanel;
	
	public TrainTask(NewTrainPanel panel, int totalOfSpam, int totalOfHam, String spamDir, String hamDir) {
		mPanel = panel;
		mTotalOfSpam = totalOfSpam;
		mTotalOfHam = totalOfHam;
		
		mSpamDir = spamDir;
		mHamDir = hamDir;
	}

	@Override
	public void handleReport(int numOfFiles, int emailType) {
		if (emailType == Constants.SPAM_EMAIL) {
			int oldValue = mProcessedSpam;
			mProcessedSpam = numOfFiles;
			firePropertyChange(NUM_PROCESSED_SPAM, oldValue, mProcessedSpam);
		} else {
			int oldValue = mProcessedHam;
			mProcessedHam = numOfFiles;
			firePropertyChange(NUM_PROCESSED_HAM, oldValue, mProcessedHam);
		}
	}

	@Override
	protected Void doInBackground() throws Exception {
		SpamFilterEngine engine = Global.engine;
		// clear data
		engine.clearData();
		
		engine.setEngineExtractListener(this);
		engine.setComputeFeatureProbabilityListener(this);
		engine.train(mSpamDir, mHamDir);
		return null;
	}

	@Override
	public void onCompleteCompute(int numOfCompletedFeature, int totalFeatures) {
		String info = numOfCompletedFeature + "_" + totalFeatures;

		firePropertyChange(FEATURE_INFO, mFeatureInfo, info);
		mFeatureInfo = info;
	}

	@Override
	protected void done() {
		mPanel.trainDone();		
	}
	
}
