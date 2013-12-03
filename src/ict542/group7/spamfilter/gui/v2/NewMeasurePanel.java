package ict542.group7.spamfilter.gui.v2;

import ict542.group7.spamfilter.engine.MeasureEngineListener;
import ict542.group7.spamfilter.engine.SpamFilterEngine;
import ict542.group7.spamfilter.engine.common.ReportObject;
import ict542.group7.spamfilter.gui.Global;
import ict542.group7.spamfilter.gui.utils.SwingUtils;

import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.RowSpec;

public class NewMeasurePanel extends JPanel implements ActionListener, PropertyChangeListener {
	
	private int numOfEmails;
	
	private JFileChooser fileChooser;
	
	private JLabel lbThreadShold;
	private JTextField tfEmailDir;
	private JLabel lbAccuracy;
	private JButton btnBrowse;
	private JLabel lbNumOfEmails;
	private JProgressBar prbClassify;
	private JLabel lbNumOfClassifiedEmails;
	private JButton btnStartMeasurement;
	private JLabel lbNumOfEngineSpam;
	private JLabel lbNumOfMisAsSpam;
	private JLabel lbNumOfEngineHam;
	private JLabel lbNumOfMisAsHam;
	private JLabel lbNumOfHam;
	private JLabel lbNumOfSpam;

	/**
	 * Create the panel.
	 */
	public NewMeasurePanel() {
		setLayout(new GridLayout(2, 0, 0, 0));
		
		fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
		JPanel panel_1 = new JPanel();
		add(panel_1);
		panel_1.setLayout(new MigLayout("", "[][grow][]", "[][][][][][][][][]"));
		
		btnStartMeasurement = new JButton("Start measurement");
		panel_1.add(btnStartMeasurement, "cell 0 0");
		btnStartMeasurement.addActionListener(this);
		
		JSeparator separator = new JSeparator();
		panel_1.add(separator, "cell 0 1");
		
		JLabel lblThreadshold = new JLabel("Threadshold");
		panel_1.add(lblThreadshold, "cell 0 2");
		
		lbThreadShold = new JLabel("0.9");
		panel_1.add(lbThreadShold, "cell 1 2");
		
		JLabel lblPathToEmail = new JLabel("Path to email folder");
		panel_1.add(lblPathToEmail, "cell 0 3,alignx left");
		
		tfEmailDir = new JTextField();
		panel_1.add(tfEmailDir, "cell 1 3,growx");
		tfEmailDir.setColumns(10);
		
		btnBrowse = new JButton("Browse");
		panel_1.add(btnBrowse, "cell 2 3");
		btnBrowse.addActionListener(this);
		
		JLabel lblNumberOfEmails = new JLabel("Number of test emails");
		panel_1.add(lblNumberOfEmails, "cell 0 4");
		
		lbNumOfEmails = new JLabel("0");
		panel_1.add(lbNumOfEmails, "cell 1 4");
		
		JSeparator separator_1 = new JSeparator();
		panel_1.add(separator_1, "cell 0 5");
		
		JLabel lblClassifyingEmails = new JLabel("Classifying emails");
		panel_1.add(lblClassifyingEmails, "cell 0 6");
		
		prbClassify = new JProgressBar();
		prbClassify.setStringPainted(true);
		panel_1.add(prbClassify, "flowx,cell 1 6,growy");
		
		lbNumOfClassifiedEmails = new JLabel("");
		panel_1.add(lbNumOfClassifiedEmails, "cell 1 6");
		
		JSeparator separator_2 = new JSeparator();
		panel_1.add(separator_2, "cell 0 7");
		
		JLabel lblAccuracy = new JLabel("Accuracy");
		panel_1.add(lblAccuracy, "cell 0 8");
		
		lbAccuracy = new JLabel("");
		panel_1.add(lbAccuracy, "cell 1 8");
		
		JPanel panel = new JPanel();
		add(panel);
		panel.setLayout(new GridLayout(0, 2, 0, 0));
		
		JPanel panel_2 = new JPanel();
		panel.add(panel_2);
		panel_2.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("249px"),
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("8px"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("15px"),
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("15px"),
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("15px"),}));
		
		JLabel lblNumberOfSpam = new JLabel("Spam emails (classified by engine):");
		panel_2.add(lblNumberOfSpam, "2, 2");
		
		lbNumOfEngineSpam = new JLabel("0");
		panel_2.add(lbNumOfEngineSpam, "4, 2, 7, 1");
		
		JLabel lblNumberOf = new JLabel("Non-spam misclassified as Spam:");
		panel_2.add(lblNumberOf, "2, 4");
		
		lbNumOfMisAsSpam = new JLabel("0");
		panel_2.add(lbNumOfMisAsSpam, "4, 4, 8, 1, left, top");
		
		JLabel lblActualSpamEmails = new JLabel("Actual spam emails:");
		panel_2.add(lblActualSpamEmails, "2, 6");
		
		lbNumOfSpam = new JLabel("0");
		lbNumOfSpam.setHorizontalAlignment(SwingConstants.TRAILING);
		panel_2.add(lbNumOfSpam, "4, 6, 7, 1, left, top");
		
		JPanel panel_3 = new JPanel();
		panel.add(panel_3);
		panel_3.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		JLabel lblNonspamEmailsclassified = new JLabel("Non-spam emails (classified by engine):");
		panel_3.add(lblNonspamEmailsclassified, "2, 2");
		
		lbNumOfEngineHam = new JLabel("0");
		panel_3.add(lbNumOfEngineHam, "4, 2");
		
		JLabel lblSpamMisclassifiedAs = new JLabel("Spam misclassified as Non-spam:");
		panel_3.add(lblSpamMisclassifiedAs, "2, 4");
		
		lbNumOfMisAsHam = new JLabel("0");
		panel_3.add(lbNumOfMisAsHam, "4, 4");
		
		JLabel lblActualNonspamEmails = new JLabel("Actual non-spam emails:");
		panel_3.add(lblActualNonspamEmails, "2, 6");
		
		lbNumOfHam = new JLabel("0");
		panel_3.add(lbNumOfHam, "4, 6");

	}

	@Override
	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();
		if (source == btnBrowse) {
			int returnVal = fileChooser.showOpenDialog(SwingUtilities.getWindowAncestor(this));
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				String dirPath = fileChooser.getSelectedFile().getAbsolutePath();
				tfEmailDir.setText(dirPath);
			}
		} else if (source == btnStartMeasurement) {
			if (tfEmailDir.getText().trim().isEmpty()) {
				JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this), "Please choose email folder to test");
			} else {

				btnStartMeasurement.setEnabled(false);

				numOfEmails = SwingUtils.getNumOfFiles(tfEmailDir.getText());
				lbNumOfEmails.setText(String.valueOf(numOfEmails));

				// progress
				prbClassify.setValue(0);
				lbNumOfClassifiedEmails.setText("0");

				// accuracy
				lbAccuracy.setText("");

				// spam info
				lbNumOfEngineSpam.setText("");
				lbNumOfMisAsSpam.setText("");
				lbNumOfSpam.setText("");

				// ham info
				lbNumOfEngineHam.setText("");
				lbNumOfMisAsHam.setText("");
				lbNumOfHam.setText("");

				MeasureTask task = new MeasureTask(this, tfEmailDir.getText());
				task.addPropertyChangeListener(this);
				task.execute();
			}
		}
	}
	
	public void measureDone(ReportObject report) {
		btnStartMeasurement.setEnabled(true);
		
		lbAccuracy.setText(report.computeAccuracy() + "%");
		
		lbNumOfEngineSpam.setText(String.valueOf(report.numOfEngineSpam));
		lbNumOfMisAsSpam.setText(String.valueOf(report.hamMisAsSpam));
		lbNumOfSpam.setText(String.valueOf(report.numOfSpam));
		
		lbNumOfEngineHam.setText(String.valueOf(report.numOfEngineHam));
		lbNumOfMisAsHam.setText(String.valueOf(report.spamMisAsHam));
		lbNumOfHam.setText(String.valueOf(report.numOfHam));
		
		JOptionPane.showMessageDialog(SwingUtils.getRootWindow(this), "Measure completed!");
	}

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		if (MeasureTask.MEASURE_PROPERY.equals(event.getPropertyName())) {
			int newValue = (Integer) event.getNewValue();
			lbNumOfClassifiedEmails.setText(String.valueOf(newValue));
			
			int progress = newValue * 100 / numOfEmails;
			prbClassify.setValue(progress);
		}
	}
}

class MeasureTask extends SwingWorker<Void, Void> implements MeasureEngineListener {
	
	public static final String MEASURE_PROPERY = "measure-property";
	
	private NewMeasurePanel mPanel;
	private String mEmailDirPath;
	private ReportObject mReport;
	private int mNumOfCompletedEmails;
	
	public MeasureTask(NewMeasurePanel panel, String emailDirPath) {
		mPanel = panel;
		mEmailDirPath = emailDirPath;
	}

	@Override
	protected Void doInBackground() throws Exception {
		SpamFilterEngine engine = Global.engine;
		engine.setMeasureListener(this);
		mReport = engine.measureEngine(mEmailDirPath);
		return null;
	}

	@Override
	protected void done() {
		mPanel.measureDone(mReport);
	}

	@Override
	public void onCompleteNewEmail(int numOfCompletedEmails) {
		firePropertyChange(MEASURE_PROPERY, mNumOfCompletedEmails, numOfCompletedEmails);
		mNumOfCompletedEmails = numOfCompletedEmails;
	}
}
