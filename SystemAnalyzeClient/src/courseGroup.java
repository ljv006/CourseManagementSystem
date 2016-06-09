import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class courseGroup extends JFrame {
	private static final long serialVersionUID = 1L;
	public static String courseName;
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					@SuppressWarnings("unused")
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
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.getContentPane().setLayout(null);
		
		JButton uploadHomeworkButton = new JButton("\u63D0\u4EA4\u4F5C\u4E1A");
		uploadHomeworkButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				@SuppressWarnings("unused")
				uploadHomework uh = new uploadHomework();
				f.dispose();
			}
		});
		uploadHomeworkButton.setBounds(82, 47, 102, 41);
		f.getContentPane().add(uploadHomeworkButton);
		
		JButton groupInformationButton = new JButton("\u7FA4\u7EC4\u4FE1\u606F");
		groupInformationButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					@SuppressWarnings("unused")
					groupInformation gi = new groupInformation(courseName);
					f.dispose();
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
				@SuppressWarnings("unused")
				chatRoom ch = new chatRoom(courseName);
				f.dispose();
			}
		});
		chatButton.setBounds(82, 133, 102, 41);
		f.getContentPane().add(chatButton);
		
		JButton uploaddownloadButton = new JButton("\u4E0A\u4F20/\u4E0B\u8F7D\u8D44\u6E90");
		uploaddownloadButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					@SuppressWarnings("unused")
					uploaddownload ud = new uploaddownload(_courseName);
					f.dispose();
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
		uploaddownloadButton.setBounds(82, 176, 102, 41);
		f.getContentPane().add(uploaddownloadButton);
		
		JButton goBackButton = new JButton("\u8FD4\u56DE");
		goBackButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					@SuppressWarnings("unused")
					mainWindow mw = new mainWindow();
					f.dispose();
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
		f.setResizable(false);
		f.setVisible(true);
	}
}
