package ict542.group7.spamfilter.gui;

import ict542.group7.spamfilter.gui.utils.SwingUtils;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MeasureEnginePanel extends JPanel {
	
	private static final long serialVersionUID = -1354244571014559427L;
	
	private JButton btnStartMeasurement;
	
	private JButton btnInboxBrowse;
	private JLabel lbPathToInbox;
	private JTextField tfInboxPath;
	
	private JLabel lbIntroNumOfInboxEmails;
	private JLabel lbNumOfInboxEmails;
	
	private JLabel lbIntroSpamByEngine;
	private JLabel lbSpamByEngine;
	
	private JLabel lbIntroTotalOfSpam;
	private JLabel lbTotalOfSpam;
	
	private JLabel lbIntroHamByEngine;
	private JLabel lbHamByEngine;
	
	private JLabel lbIntroTotalOfHam;
	private JLabel lbTotalOfHam;
	
	private JLabel lbIntroAccuracy;
	private JLabel lbAccuracy;
	
	
	public MeasureEnginePanel() {
		setLayout(new GridBagLayout());
		
		initComponents();
		setPreferredSize(new Dimension(GUIConstants.FRAME_WIDTH, 200));
		
		add(btnStartMeasurement, SwingUtils.createConstraints(0, 0, 1, 1));
		
		// path to inbox
		add(lbPathToInbox, SwingUtils.createConstraints(0, 1, 2, 1));
		add(tfInboxPath, SwingUtils.createConstraints(2, 1, 2, 1, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 0, 0)));
		add(btnInboxBrowse, SwingUtils.createConstraints(4, 1, 1, 1));
		
		// number of emails
		add(lbIntroNumOfInboxEmails, SwingUtils.createConstraints(0, 2, 2, 1));
		add(lbNumOfInboxEmails, SwingUtils.createConstraints(2, 2, 2, 1));
		
		// (engine) number of spam
		add(lbIntroSpamByEngine, SwingUtils.createConstraints(0, 3, 2, 1));
		add(lbSpamByEngine, SwingUtils.createConstraints(2, 3, 2, 1));
		
		// total of spam
		add(lbIntroTotalOfSpam, SwingUtils.createConstraints(0, 4, 2, 1));
		add(lbTotalOfSpam, SwingUtils.createConstraints(2, 4, 2, 1));
		
		// (engine) number of ham
		add(lbIntroHamByEngine, SwingUtils.createConstraints(0, 5, 2, 1));
		add(lbHamByEngine, SwingUtils.createConstraints(2, 5, 2, 1));
		
		// total of ham
		add(lbIntroTotalOfHam, SwingUtils.createConstraints(0, 6, 2, 1));
		add(lbTotalOfHam, SwingUtils.createConstraints(2, 6, 2, 1));
		
		// accuracy
		add(lbIntroAccuracy, SwingUtils.createConstraints(0, 7, 2, 1));
		add(lbAccuracy, SwingUtils.createConstraints(2, 7, 2, 1));
	}
	
	private void initComponents() {
		btnStartMeasurement = new JButton("Start measurement");
		
		btnInboxBrowse = new JButton("Browse");
		lbPathToInbox = new JLabel("Path to inbox: ");
		tfInboxPath = new JTextField();
		
		lbIntroNumOfInboxEmails = new JLabel("Number of emails in inbox: ");
		lbNumOfInboxEmails = new JLabel();
		
		lbIntroSpamByEngine = new JLabel("Engine - Number of spam: ");
		lbSpamByEngine = new JLabel();
		
		lbIntroTotalOfSpam = new JLabel("Total of spam: ");
		lbTotalOfSpam = new JLabel();
		
		lbIntroHamByEngine = new JLabel("Engine - Number of ham: ");
		lbHamByEngine = new JLabel();
		
		lbIntroTotalOfHam = new JLabel("Total of ham: ");
		lbTotalOfHam = new JLabel();
				
		lbIntroAccuracy = new JLabel("Engine Accuracy: ");
		lbAccuracy = new JLabel();
	}
	
}

