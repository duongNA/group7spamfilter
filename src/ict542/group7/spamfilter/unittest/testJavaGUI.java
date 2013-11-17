package ict542.group7.spamfilter.unittest;

import javax.swing.JFrame;

import org.junit.Test;

public class testJavaGUI {

	@Test
	public void testFrame() {
		JFrame frame = new TestFrame();
		frame.setVisible(true);
	}
	
	class TestFrame extends JFrame {
		public TestFrame() {
			setTitle("test frame");
			setBounds(100, 100, 400, 320);
		}
	}
}
