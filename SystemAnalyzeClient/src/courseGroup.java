import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class courseGroup extends JFrame {

	private JPanel contentPane;
	public static String courseName;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					courseGroup frame = new courseGroup(courseName);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Create the frame.
	 */
	public courseGroup(String _courseName) {
		courseName = _courseName;
		JFrame f = new JFrame();
		f.setBounds(100, 100, 450, 300);
		f.getContentPane().setLayout(null);
		
		JButton uploadHomeworkButton = new JButton("\u63D0\u4EA4\u4F5C\u4E1A");
		uploadHomeworkButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				uploadHomework uh = new uploadHomework();
			}
		});
		uploadHomeworkButton.setBounds(82, 47, 102, 41);
		f.getContentPane().add(uploadHomeworkButton);
		
		JButton groupInformationButton = new JButton("\u7FA4\u7EC4\u4FE1\u606F");
		groupInformationButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					groupInformation gi = new groupInformation(courseName);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (UnknownHostException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		groupInformationButton.setBounds(82, 90, 102, 41);
		f.getContentPane().add(groupInformationButton);
		
		JButton chatButton = new JButton("\u804A\u5929");
		chatButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chat ch = new chat();
			}
		});
		chatButton.setBounds(82, 133, 102, 41);
		f.getContentPane().add(chatButton);
		
		JButton uploaddownloadButton = new JButton("\u4E0A\u4F20/\u4E0B\u8F7D\u8D44\u6E90");
		uploaddownloadButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				uploaddownload ud = new uploaddownload();
			}
		});
		uploaddownloadButton.setBounds(82, 176, 102, 41);
		f.getContentPane().add(uploaddownloadButton);
		
		JButton goBackButton = new JButton("\u8FD4\u56DE");
		goBackButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					mainWindow mw = new mainWindow();
				} catch (IOException | InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		});
		goBackButton.setBounds(316, 47, 102, 41);
		f.getContentPane().add(goBackButton);
		
		JLabel courseNameLabel = new JLabel(courseName);
		courseNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		courseNameLabel.setBounds(143, 10, 145, 27);
		f.getContentPane().add(courseNameLabel);
		//获取课程列表
		f.setVisible(true);
	}
}
