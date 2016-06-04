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
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;

public class registCourse extends JFrame {

	private JPanel contentPane;
	public static CourseList cl = new CourseList();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					registCourse frame = new registCourse();
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
	public registCourse() throws IOException, InterruptedException {
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
		gobackButton.setBounds(217,123,101,38);
		f.getContentPane().add(gobackButton);
		
		JLabel lblNewLabel = new JLabel("\u6240\u6709\u8BFE\u7A0B\u5217\u8868");
		lblNewLabel.setBounds(10, 6, 77, 26);
		f.getContentPane().add(lblNewLabel);
		cl.CourseList.clear();
		Client.getAllCourseList();
		Thread.sleep(500);
		String[] str = new String[cl.getSize()];
		int count = 0;
		for (Course c:cl.CourseList) {
			str[count++] = c.name;
		}
		JList courselist = new JList(str);
		courselist.setBounds(10, 37, 197, 215);
		f.getContentPane().add(courselist);
		
		JButton registerButton = new JButton("\u6CE8\u518C");
		registerButton.setBounds(216, 82, 102, 41);
		f.getContentPane().add(registerButton);
		
		JButton refreshButton = new JButton("\u5237\u65B0\u5217\u8868");
		refreshButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.CourseList.clear();
				try {
					Client.getAllCourseList();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				try {
					Thread.sleep(500);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				String[] str = new String[cl.getSize()];
				int count = 0;
				for (Course c:cl.CourseList) {
					str[count++] = c.name;
				}
				JList courselist = new JList(str);
				courselist.setBounds(10, 37, 180, 215);
				f.getContentPane().add(registerButton);
			}
		});
		refreshButton.setBounds(217, 44, 101, 38);
		f.getContentPane().add(refreshButton);
		f.setVisible(true);
	}

}
