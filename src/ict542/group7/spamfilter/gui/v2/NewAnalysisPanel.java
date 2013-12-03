package ict542.group7.spamfilter.gui.v2;

import ict542.group7.spamfilter.engine.SpamFilterEngine;
import ict542.group7.spamfilter.engine.common.Feature;
import ict542.group7.spamfilter.gui.Global;
import ict542.group7.spamfilter.gui.tableunit.InterestFeatureDataModel;
import ict542.group7.spamfilter.gui.utils.SwingUtils;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import net.miginfocom.swing.MigLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.LinkedList;
import java.util.List;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.RowSpec;
import javax.swing.JTextField;
import javax.swing.JScrollPane;

public class NewAnalysisPanel extends JPanel implements ActionListener {
	private JFileChooser fileChooser;
	
	private JTextField tfEmailPath;
	private JTable tbInterestFeature;
	private JButton btnStartAnalysis;
	private JButton btnBrowse;
	private JLabel lbCombinationProb;
	private JLabel lbEmailType;

	/**
	 * Create the panel.
	 */
	public NewAnalysisPanel() {
		setLayout(new BorderLayout(0, 0));
		
		fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		
		JPanel panel = new JPanel();
		add(panel, BorderLayout.NORTH);
		
		btnStartAnalysis = new JButton("Start checking");
		btnStartAnalysis.addActionListener(this);
		
		panel.setLayout(new MigLayout("", "[136px][grow]", "[25px][][15px][][][][44.00]"));
		panel.add(btnStartAnalysis, "cell 0 0,alignx left,aligny top");
		
		JSeparator separator = new JSeparator();
		panel.add(separator, "cell 0 1");
		
		JLabel lblPathToEmail = new JLabel("Path to email");
		panel.add(lblPathToEmail, "cell 0 2,alignx left,aligny center");
		
		tfEmailPath = new JTextField();
		panel.add(tfEmailPath, "flowx,cell 1 2,growx");
		tfEmailPath.setColumns(10);
		
		JSeparator separator_1 = new JSeparator();
		panel.add(separator_1, "cell 0 3");
		
		JLabel lblThreadshold = new JLabel("Threadshold");
		panel.add(lblThreadshold, "cell 0 4");
		
		JLabel label = new JLabel("0.9");
		panel.add(label, "cell 1 4");
		
		JLabel lblCombinationProbabilityOf = new JLabel("Combination probability");
		panel.add(lblCombinationProbabilityOf, "cell 0 5");
		
		lbCombinationProb = new JLabel("");
		panel.add(lbCombinationProb, "cell 1 5");
		
		JLabel lblEmailType = new JLabel("Email type");
		panel.add(lblEmailType, "cell 0 6,aligny top");
		
		lbEmailType = new JLabel("");
		panel.add(lbEmailType, "cell 1 6,aligny top");
		
		btnBrowse = new JButton("Browse");
		panel.add(btnBrowse, "cell 1 2");
		btnBrowse.addActionListener(this);
		
		JPanel panel_1 = new JPanel();
		add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		panel_1.add(scrollPane, BorderLayout.CENTER);
		
		JSeparator separator_2 = new JSeparator();
		scrollPane.setColumnHeaderView(separator_2);
		
		List<Feature> featureList = new LinkedList<Feature>();
		InterestFeatureDataModel model = new InterestFeatureDataModel(featureList);
		tbInterestFeature = new JTable(model);
		scrollPane.setViewportView(tbInterestFeature);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == btnBrowse) {
			int returnVal = fileChooser.showOpenDialog(SwingUtilities.getWindowAncestor(this));
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				String dirPath = fileChooser.getSelectedFile().getAbsolutePath();
				tfEmailPath.setText(dirPath);
			}
		} else if (source == btnStartAnalysis) {
			if (tfEmailPath.getText().trim().isEmpty()) {
				JOptionPane.showMessageDialog(SwingUtils.getRootWindow(this), "Please choose email to check");
			} else {
				btnStartAnalysis.setEnabled(false);
				
				lbCombinationProb.setText("");
				lbEmailType.setText("");
				
				InterestFeatureDataModel model = (InterestFeatureDataModel) tbInterestFeature.getModel();
				model.clearList();
				
				AnalysisTask task = new AnalysisTask(this, tfEmailPath.getText());
				task.execute();
			}
		}
		
	}
	
	public void analysisDone(boolean isSpam) {
		btnStartAnalysis.setEnabled(true);
		
		double comProbability = Global.engine.getAnalysisEngine().getCombinationProbability();
		List<Feature> featureList = Global.engine.getAnalysisEngine().getFeatureList();
		
		lbCombinationProb.setText(String.valueOf(comProbability));
		String type;
		if (isSpam) {
			lbEmailType.setText("Spam");
			type = "Spam";
		} else {
			lbEmailType.setText("Non-spam");
			type = "Non-spam";
		}
		
		InterestFeatureDataModel model = (InterestFeatureDataModel) tbInterestFeature.getModel();
		model.setFeatureList(featureList);
		
		JOptionPane.showMessageDialog(SwingUtils.getRootWindow(this), "This email is " + type + " email!");
	}
}

class AnalysisTask extends SwingWorker<Void, Void> {
	
	private NewAnalysisPanel mPanel;
	private String mEmailPath;
	private boolean isSpam;
	
	public AnalysisTask(NewAnalysisPanel panel, String emailPath) {
		mPanel = panel;
		mEmailPath = emailPath;
	}

	@Override
	protected Void doInBackground() throws Exception {
		SpamFilterEngine engine = Global.engine;
		
		isSpam = engine.analysisIsSpam(mEmailPath);
		return null;
	}

	@Override
	protected void done() {
		mPanel.analysisDone(isSpam);
	}
}
