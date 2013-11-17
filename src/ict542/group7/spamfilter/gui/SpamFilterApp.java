package ict542.group7.spamfilter.gui;

import ict542.group7.spamfilter.engine.SpamFilterEngine;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.apache.log4j.Logger;

public class SpamFilterApp extends JFrame {
	
	private static final Logger logger = Logger.getLogger(SpamFilterApp.class);
	
	private static final long serialVersionUID = -7166589533466094676L;

	public SpamFilterApp() {
		setTitle("ICT542 - Group 7 - Spam filter");
		setSize(GUIConstants.FRAME_WIDTH, GUIConstants.FRAME_HEIGHT);
		centerDesktop();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(new MainPanel());
	}
	
	public static void main(String[] args) {
		// set up look and feel
//		try {
//			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//		} catch (Exception e) {
//			logger.error("Error when set look and feel", e);
//		} 
		
		// create engine
		Global.engine = new SpamFilterEngine();
		
		JFrame appFrame = new SpamFilterApp();
		//appFrame.pack();
		appFrame.setVisible(true);
	}
	
	private void centerDesktop() {
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(((int)dimension.getWidth() - getWidth()) / 2, ((int)dimension.getHeight() - getHeight()) / 2);		
	}
}
