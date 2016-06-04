import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class uploaddownload extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					uploaddownload frame = new uploaddownload();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public uploaddownload() {
		JFrame f = new JFrame();
		f.setBounds(100, 100, 450, 300);
		f.getContentPane().setLayout(null);
		f.setVisible(true);
	}

}
