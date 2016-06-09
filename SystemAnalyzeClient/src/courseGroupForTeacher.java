import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

public class courseGroupForTeacher extends JFrame {
	private static final long serialVersionUID = 1L;
	public static String courseName;
	public static groupMemberList gl;
	public Thread t = null;
	/**
	 * Launch the application.
	 */
	class courseGroupForTeacherThread implements Runnable{
		JList<String> userList;
		public courseGroupForTeacherThread(JList<String> list) {
			userList = list;
		}
		public void run() {
			while (true) {
				try {
					Client.getCourseGroupMember(courseName);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				DefaultListModel<String> model = new DefaultListModel<String>();
				for (User c:gl.groupMemberList) {
					System.out.println(c.name);
					model.addElement(c.name);
				}
				userList.setModel(model);
			}
		}
	}
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					@SuppressWarnings("unused")
					courseGroupForTeacher frame = new courseGroupForTeacher(courseName);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Create the frame.
	 * @throws InterruptedException 
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	public courseGroupForTeacher(String _courseName) throws InterruptedException, UnknownHostException, IOException {
		courseName = _courseName;
		JFrame f = new JFrame();
		f.setBounds(100, 100, 450, 300);
		f.getContentPane().setLayout(null);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JButton groupInformationButton = new JButton("\u7FA4\u7EC4\u4FE1\u606F");
		groupInformationButton.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				try {
					@SuppressWarnings("unused")
					groupInformationForTeacher gi = new groupInformationForTeacher(courseName);
					t.stop();
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
		groupInformationButton.setBounds(204, 47, 102, 41);
		f.getContentPane().add(groupInformationButton);
		
		JButton chatButton = new JButton("\u804A\u5929");
		chatButton.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				@SuppressWarnings("unused")
				chatRoom ch = new chatRoom(courseName);
				t.stop();
				f.dispose();
			}
		});
		chatButton.setBounds(204, 90, 102, 41);
		f.getContentPane().add(chatButton);
		
		JButton uploaddownloadButton = new JButton("\u4E0A\u4F20/\u4E0B\u8F7D\u8D44\u6E90");
		uploaddownloadButton.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				try {
					@SuppressWarnings("unused")
					uploaddownload ud = new uploaddownload(_courseName);
					t.stop();
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
		uploaddownloadButton.setBounds(204, 133, 102, 41);
		f.getContentPane().add(uploaddownloadButton);
		
		JButton goBackButton = new JButton("\u8FD4\u56DE");
		goBackButton.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				try {
					@SuppressWarnings("unused")
					mainWindowForTeacher mw = new mainWindowForTeacher();
					t.stop();
					f.dispose();
				} catch (IOException | InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		});
		goBackButton.setBounds(332, 0, 102, 41);
		f.getContentPane().add(goBackButton);
		
		JLabel courseNameLabel = new JLabel(courseName);
		courseNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		courseNameLabel.setBounds(177, 10, 145, 27);
		f.getContentPane().add(courseNameLabel);
		
		Client.getCourseGroupMember(courseName);
		String[] str = new String[gl.getSize()];
		int count = 0;
		for (User c:gl.groupMemberList) {
			str[count++] = c.name;
		}
		JList<String> groupMemberlist = new JList<String>();
		JScrollPane ps = new JScrollPane(groupMemberlist);  
		ps.setBounds(0, 30, 180, 230);
		f.getContentPane().add(ps);

		JLabel groupMemberLabel = new JLabel("\u7FA4\u7EC4\u6210\u5458");
		groupMemberLabel.setHorizontalAlignment(SwingConstants.CENTER);
		groupMemberLabel.setBounds(44, 6, 92, 28);
		f.getContentPane().add(groupMemberLabel);
		//获取课程列表
		t = new Thread(new courseGroupForTeacherThread(groupMemberlist));
		t.start();
		f.setResizable(false);
		f.setVisible(true);
	}
}
