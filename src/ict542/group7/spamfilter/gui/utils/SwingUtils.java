package ict542.group7.spamfilter.gui.utils;

import ict542.group7.spamfilter.engine.common.Feature;
import ict542.group7.spamfilter.gui.tableunit.FeatureDataModel;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.io.File;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class SwingUtils {
	
	public static GridBagConstraints createConstraints(int gridx, int gridy, int gridwidth, int gridheight, int anchor, int fill, Insets insets) {
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = gridx;
		c.gridy = gridy;
		c.gridwidth = gridwidth;
		c.gridheight = gridheight;
		c.weightx = 0.5;
		c.weighty = 0.5;
		c.anchor = anchor;
		c.fill = fill;
		c.insets = insets;
		return c;
	}
	
	public static GridBagConstraints createConstraints(int gridx, int gridy, int gridwidth, int gridheight) {
		return createConstraints(gridx, gridy, gridwidth, gridheight, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 0, 0, 0));
	}
	
	public static GridBagConstraints createConstraints(int gridx, int gridy, int gridwidth, int gridheight, Insets insets) {
		return createConstraints(gridx, gridy, gridwidth, gridheight, GridBagConstraints.WEST, GridBagConstraints.NONE, insets);
	}
	
	public static int getNumOfFiles(String dirPath) {
		File dir = new File(dirPath);
		if (!dir.isDirectory()) {
			return 0;
		}
		
		File[] files = dir.listFiles();
		int numOfFiles = 0;
		for (File f : files) {
			if (f.isFile()) {
				numOfFiles++;
			}
		}
		
		return numOfFiles;
	}
	
	public static Component getRootWindow(Component c) {
		return SwingUtilities.getWindowAncestor(c);
	}
	
	public static void cleartTableData(JTable table) {
		TableModel model = table.getModel();
		if (model instanceof DefaultTableModel) {
			((DefaultTableModel) model).setRowCount(0);
		} else if (model instanceof FeatureDataModel) {
			((FeatureDataModel) model).setFeatureList(new LinkedList<Feature>());
		}
	}
}
