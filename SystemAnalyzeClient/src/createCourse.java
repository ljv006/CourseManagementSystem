import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class createCourse extends JFrame {

	private JPanel contentPane;
	public static String createCourseStatus;
	private JTextField courseName;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					createCourse frame = new createCourse();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	private void close() {
        WindowEvent closeWin = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(closeWin);
    }
	/**
	 * Create the frame.
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	public createCourse() throws IOException, InterruptedException {
		JFrame f = new JFrame();
		f.setBounds(100, 100, 450, 300);
		f.getContentPane().setLayout(null);
		JButton gobackButton = new JButton("\u8FD4\u56DE");
		gobackButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				close();
				try {
					mainWindow newMainWindow = new mainWindow();
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		gobackButton.setBounds(322,10,102,41);
		f.getContentPane().add(gobackButton);
		
		courseName = new JTextField();
		courseName.setBounds(144, 34, 148, 41);
		f.getContentPane().add(courseName);
		courseName.setColumns(10);
		
		JLabel courseNamelabel = new JLabel("\u8BFE\u7A0B\u540D\u79F0");
		courseNamelabel.setHorizontalAlignment(SwingConstants.CENTER);
		courseNamelabel.setBounds(43, 34, 80, 41);
		f.getContentPane().add(courseNamelabel);
		
		JButton createCourseButton = new JButton("\u521B\u5EFA");
		createCourseButton.setBounds(182, 83, 110, 41);
		f.getContentPane().add(createCourseButton);
		f.setResizable(false);
		f.setVisible(true);
	}
}
