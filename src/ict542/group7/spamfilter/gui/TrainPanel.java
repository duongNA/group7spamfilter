package ict542.group7.spamfilter.gui;

import ict542.group7.spamfilter.engine.common.Feature;
import ict542.group7.spamfilter.gui.tableunit.FeatureDataModel;
import ict542.group7.spamfilter.gui.tasks.TrainTask;
import ict542.group7.spamfilter.gui.utils.SwingUtils;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.LinkedList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ProgressMonitor;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import org.apache.log4j.Logger;

public class TrainPanel extends JPanel {
	private static final Logger logger = Logger.getLogger(TrainPanel.class);
	
	private JPanel topPanel;
	private JPanel bottomPanel;

	private static final long serialVersionUID = -4719667549836143716L;

	public TrainPanel() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		
		bottomPanel = new BottomTrainPanel();
		JTable table = ((BottomTrainPanel)bottomPanel).getTable();
		topPanel = new TopTrainPanel(table);
		add(topPanel);
		add(bottomPanel);
	}
}

class TopTrainPanel extends JPanel implements ActionListener, PropertyChangeListener {
	private static final Logger logger = Logger.getLogger(TopTrainPanel.class);
	
	private JButton btnStartTraining;
	private JButton btnSpamBrowse;
	private JButton btnHamBrowse;
	private JTextField tfSpamDir;
	private JTextField tfHamDir;
	
	private JLabel lbPathToSpam;
	private JLabel lbPathToHam;
	
	private JLabel lbIntroTotalOfSpam;
	private JLabel lbNumOfSpam;
	
	private JLabel lbIntroTotalOfHam;
	private JLabel lbNumOfHam;
	
	private JLabel lbProcessSpam;
	private JLabel lbProcessHam;
	private JProgressBar pbSpam;
	private JProgressBar pbHam;
	private JLabel lbProcessSpamCounter;
	private JLabel lbProcessHamCounter;
	
	private JFileChooser fileChooser;
	private ProgressMonitor pgMonitor;
	
	private JTable tbFeature;
	private JLabel lbIntroNumOfFeature;
	private JLabel lbNumOfFeature;
	
	public TopTrainPanel(JTable tbFeature) {
		this.tbFeature = tbFeature;
		initComponents();
		
		setLayout(new GridBagLayout());
		
		GridBagConstraints c = null;
		
		add(btnStartTraining, SwingUtils.createConstraints(0, 0, 1, 1));
		
		// path to spam
		add(lbPathToSpam, SwingUtils.createConstraints(0, 1, 2, 1));
		add(tfSpamDir, SwingUtils.createConstraints(2, 1, 2, 1, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 0, 0)));
		add(btnSpamBrowse, SwingUtils.createConstraints(4, 1, 1, 1));
		// path to ham
		add(lbPathToHam, SwingUtils.createConstraints(0, 2, 2, 1));
		add(tfHamDir, SwingUtils.createConstraints(2, 2, 2, 1, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 0, 0)));
		add(btnHamBrowse, SwingUtils.createConstraints(4, 2, 1, 1));
		
		// total of spam
		add(lbIntroTotalOfSpam, SwingUtils.createConstraints(0, 3, 2, 1, new Insets(20, 0, 0, 0)));
		add(lbNumOfSpam, SwingUtils.createConstraints(2, 3, 1, 1, new Insets(20, 0, 0, 0)));
		// total of ham
		add(lbIntroTotalOfHam, SwingUtils.createConstraints(0, 4, 2, 1));
		add(lbNumOfHam, SwingUtils.createConstraints(2, 4, 1, 1));

		// process of spam
		add(lbProcessSpam, SwingUtils.createConstraints(0, 5, 2, 1));
		add(pbSpam, SwingUtils.createConstraints(2, 5, 2, 1, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 0, 0)));
		add(lbProcessSpamCounter, SwingUtils.createConstraints(4, 5, 1, 1));
		// process of ham
		add(lbProcessHam, SwingUtils.createConstraints(0, 6, 2, 1));
		add(pbHam, SwingUtils.createConstraints(2, 6, 2, 1, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 0, 0)));
		add(lbProcessHamCounter, SwingUtils.createConstraints(4, 6, 1, 1));
		
		// number of feature
		add(lbIntroNumOfFeature, SwingUtils.createConstraints(0, 7, 2, 1));
		add(lbNumOfFeature, SwingUtils.createConstraints(2, 7, 2, 1));
		
	}
	
	private void initComponents() {
		btnStartTraining = new JButton("Start training");
		btnSpamBrowse = new JButton("Browse");
		btnHamBrowse = new JButton("Browse");
		tfSpamDir = new JTextField(20);
		tfHamDir = new JTextField(20);
		lbPathToSpam = new JLabel("Path to the spam training set:");
		lbPathToHam = new JLabel("Path to the non-spam training set:");
		lbIntroTotalOfSpam = new JLabel("Total number of spam emails:");
		lbIntroTotalOfHam = new JLabel("Total number of non-spam emails:");
		lbNumOfSpam = new JLabel("0");
		lbNumOfHam = new JLabel("0");
		lbProcessSpam = new JLabel("Processing spam message: ");
		lbProcessHam = new JLabel("Processing ham message: ");
		pbSpam = new JProgressBar();
		pbHam = new JProgressBar();
		lbProcessSpamCounter = new JLabel();
		lbProcessHamCounter = new JLabel();
		lbIntroNumOfFeature = new JLabel("Number of extracted features:");
		lbNumOfFeature = new JLabel("0");
		
		fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
		// set listeners for button
		btnStartTraining.addActionListener(this);
		btnSpamBrowse.addActionListener(this);
		btnHamBrowse.addActionListener(this);
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
			}
			
			int numOfSpam = SwingUtils.getNumOfFiles(tfSpamDir.getText());
			int numOfHam = SwingUtils.getNumOfFiles(tfHamDir.getText());
			lbNumOfSpam.setText(String.valueOf(numOfSpam));
			lbNumOfHam.setText(String.valueOf(numOfHam));
			
			logger.debug("start training is pressed");
			pbSpam.setValue(0);
			pbHam.setValue(0);
			
			// start training
			btnStartTraining.setEnabled(false);
			lbProcessSpamCounter.setText("0%");
			lbProcessHamCounter.setText("0%");
			TrainTask task = new TrainTask(SwingUtils.getRootWindow(this), btnStartTraining, tbFeature, lbNumOfFeature, tfSpamDir.getText(), tfHamDir.getText(), numOfSpam, numOfHam);
			task.addPropertyChangeListener(this);
			task.execute();
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (TrainTask.SPAM_PROGRESS.equals(evt.getPropertyName())) {
			int progress = (Integer) evt.getNewValue();
			pbSpam.setValue(progress);
			lbProcessSpamCounter.setText(progress + "%");
		} else if (TrainTask.HAM_PROGRESS.equals(evt.getPropertyName())) {
			int progress = (Integer) evt.getNewValue();
			pbHam.setValue(progress);
			lbProcessHamCounter.setText(progress + "%");
		}
		
	}
	
	
}

class BottomTrainPanel extends JPanel {
	
	private FeatureDataModel model;
	private JTable table;
	private JScrollPane scrollPane;
	
	private String[] cols = {"Feature", "Occur in Spam", "Occur in Ham", "Probability"}; 
	
	public BottomTrainPanel() {
		setLayout(new GridLayout());
		model = new FeatureDataModel(new LinkedList<Feature>());
		table = new JTable(model);
	    table.setRowHeight(23);
		table.setFillsViewportHeight(true);
		
		scrollPane = new JScrollPane(table);
		
		add(scrollPane);
	}
	
	public JTable getTable() {
		return table;
	}
}
