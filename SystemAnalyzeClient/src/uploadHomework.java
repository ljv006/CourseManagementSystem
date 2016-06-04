import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class uploadHomework extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					uploadHomework frame = new uploadHomework();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public uploadHomework() {
		JFrame f = new JFrame();
		f.setBounds(100, 100, 450, 300);
		f.getContentPane().setLayout(null);
		f.setVisible(true);
	}

}
