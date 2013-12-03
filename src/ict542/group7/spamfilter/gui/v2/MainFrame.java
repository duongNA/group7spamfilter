package ict542.group7.spamfilter.gui.v2;

import ict542.group7.spamfilter.engine.SpamFilterEngine;
import ict542.group7.spamfilter.gui.Global;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;

public class MainFrame extends JFrame {
	
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					// set up look and feel
					try {
						boolean nimbuzzExists = false;
//						for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
//							if ("Nimbus".equals(info.getName())) {
//								UIManager.setLookAndFeel(info.getClassName());
//								nimbuzzExists = true;
//								break;
//							}
//						}
						if (!nimbuzzExists) {
							UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
						}
					} catch (Exception e) {
						e.printStackTrace();
					} 
					
					// create engine
					Global.engine = new SpamFilterEngine();
					Global.engine.clearData();

					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		setTitle("Group7 Spam Filter");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, BorderLayout.CENTER);
		
		NewTrainPanel newTrainPanel = new NewTrainPanel();
		tabbedPane.addTab("Train", null, newTrainPanel, null);
		
		NewAnalysisPanel newAnalysisPanel = new NewAnalysisPanel();
		tabbedPane.addTab("Check email", null, newAnalysisPanel, null);
		
		NewMeasurePanel newMeasurePanel = new NewMeasurePanel();
		tabbedPane.addTab("Measure engine", null, newMeasurePanel, null);
	}

}
