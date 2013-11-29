package ict542.group7.spamfilter.gui;

import ict542.group7.spamfilter.engine.common.Feature;
import ict542.group7.spamfilter.gui.tableunit.FeatureDataModel;
import ict542.group7.spamfilter.gui.tasks.AnalysisTask;
import ict542.group7.spamfilter.gui.utils.SwingUtils;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class AnalysisPanel extends JPanel {

	private static final long serialVersionUID = -6785940776386909435L;
	
	public AnalysisPanel() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		JPanel topPanel = new TopAnalysisPanel();
		JPanel bottomPanel = new BottomAnalysisPanel();
		add(topPanel);
		add(bottomPanel);
	}
	
}

class TopAnalysisPanel extends JPanel implements ActionListener {
	private JButton btnStartAnalysis;
	
	private JButton btnEmailBrowser;
	private JLabel lbPathToEmail;
	private JTextField tfEmailPath;
	
	private JLabel lbIntroThreshold;
	private JLabel lbThreshold;
	private JLabel lbIntroProbability;
	private JLabel lbProbability;
	
	private JLabel lbIntroEmailType;
	private JLabel lbEmailType;
	
	private JFileChooser fileChooser;
	
	public TopAnalysisPanel() {
		setLayout(new GridBagLayout());
		
		initComponents();
		
		add(btnStartAnalysis, SwingUtils.createConstraints(0, 0, 1, 1));
		
		// path to email
		add(lbPathToEmail, SwingUtils.createConstraints(0, 1, 2, 1));
		add(tfEmailPath, SwingUtils.createConstraints(2, 1, 2, 1, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 0, 0)));
		add(btnEmailBrowser, SwingUtils.createConstraints(4, 1, 1, 1));
		
		// thresold
		add(lbIntroThreshold, SwingUtils.createConstraints(0, 2, 2, 1));
		add(lbThreshold, SwingUtils.createConstraints(2, 2, 2, 1));
		
		// probability
		add(lbIntroProbability, SwingUtils.createConstraints(0, 3, 2, 1));
		add(lbProbability, SwingUtils.createConstraints(2, 3, 2, 1));
		
		// email type
		add(lbIntroEmailType, SwingUtils.createConstraints(0, 4, 2, 1));
		add(lbEmailType, SwingUtils.createConstraints(2, 4, 2, 1));
	}
	
	public void initComponents() {
		btnStartAnalysis = new JButton("Start analysis");
		
		btnEmailBrowser = new JButton("Browser");
		lbPathToEmail = new JLabel("Path to email: ");
		tfEmailPath = new JTextField();
		
		lbIntroThreshold = new JLabel("Threshold: ");
		lbThreshold = new JLabel();
		
		lbIntroProbability = new JLabel("Combination probability: ");
		lbProbability = new JLabel();
		
		lbIntroEmailType = new JLabel("Email type:");
		lbEmailType = new JLabel();
		
		fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		
		btnEmailBrowser.addActionListener(this);
		btnStartAnalysis.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();

		if (source == btnEmailBrowser) {
			int returnVal = fileChooser.showOpenDialog(SwingUtilities.getWindowAncestor(this));
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				String emailPath = fileChooser.getSelectedFile().getAbsolutePath();
				tfEmailPath.setText(emailPath);
			}
		} else if (source == btnStartAnalysis) {
			if (tfEmailPath.getText().trim().isEmpty()) {
				JOptionPane.showMessageDialog(SwingUtils.getRootWindow(this), "Please choose email first!");
			} else {
				btnStartAnalysis.setEnabled(false);
				AnalysisTask task = new AnalysisTask(tfEmailPath.getText(), btnStartAnalysis);
				task.execute();
			}
		}
		
	}
}

class BottomAnalysisPanel extends JPanel {
	
	private FeatureDataModel model;
	private JTable table;
	private JScrollPane scrollPane;
	
	private String[] cols = {"Feature", "Occur in Spam", "Occur in Ham", "Probability"}; 
	
	public BottomAnalysisPanel() {
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

