package ict542.group7.spamfilter.gui.tableunit;

import ict542.group7.spamfilter.engine.common.Feature;

import java.util.LinkedList;
import java.util.List;

import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;

public class InterestFeatureDataModel extends AbstractTableModel {
	
	private List<Feature> featureList = new LinkedList<Feature>();
	private String[] colNames = {"Number", "Feature", "Occur in spam", "Occur in non-spam", "Probability given spam", "Interestingness"};

	public InterestFeatureDataModel(List featureList) {
		super();
		this.featureList = featureList;
	}
	
	@Override
	public int getColumnCount() {
		return colNames.length;
	}

	@Override
	public int getRowCount() {
		return featureList.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (rowIndex < 0 || rowIndex >= featureList.size()) {
			return null;
		}
		
		Feature feature = featureList.get(rowIndex);
		
		switch (columnIndex) {
		case 0:
			// feature number
			return rowIndex + 1;
		case 1:
			// feature name
			return feature.getTokenString();
		case 2:
			// occur in spam
			return feature.getOccurInSpam();
		case 3:
			// occur in ham
			return feature.getOccurInHam();
		case 4:
			// probability
			return feature.getPropability();
		case 5:
			// interestingness
			return feature.getInterestingness();
		default:
			return null;
		}
		
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		switch (columnIndex) {
		
		case 0:
			// feature number
			return Integer.class;
		case 1:
			// feature
			return String.class;
		case 2:
			// occur in spam
			return Integer.class;
		case 3:
			// occur in ham
			return Integer.class;
		case 4:
			// probability
			return Double.class;
		case 5:
			// interesting
			return Double.class;
			
		default:
			return null;
		}
	}

	@Override
	public String getColumnName(int column) {
		return colNames[column];
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	public void setFeatureList(List<Feature> featureList) {
		this.featureList = featureList;
		fireTableChanged(new TableModelEvent(this));
	}
	
	public void clearList() {
		this.featureList.clear();
		fireTableChanged(new TableModelEvent(this));
	}
}
