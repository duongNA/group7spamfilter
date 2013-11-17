package ict542.group7.spamfilter.gui;

import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class MainPanel extends JPanel {

	public MainPanel() {
		setLayout(new GridLayout(1, 1));
		
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.addTab("Train", null, new TrainPanel(), "Train engine with data set");
		tabbedPane.addTab("Analysis", null, new AnalysisPanel(), "Check if an email is spam");
		tabbedPane.addTab("Measure engine", null, new MeasureEnginePanel(), "Measure engine");
		
		add(tabbedPane);
	}
}
