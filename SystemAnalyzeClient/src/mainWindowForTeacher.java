import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;

public class mainWindowForTeacher extends JFrame {

	private JPanel contentPane;
	public static CourseList cl = new CourseList();
	/**
	 * Launch the application.
	 */
	class mainWindowThread implements Runnable{
		JList<String> courseList;
		public mainWindowThread(JList<String> list) {
			courseList = list;
		}
		public void run() {
			while (true) {
				cl.CourseList.clear();
				try {
					Client.getUserCourseList(Client.usr);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				DefaultListModel model = new DefaultListModel();
				for (Course c:cl.CourseList) {
					model.addElement(c.name);
				}
				courseList.setModel(model);
			}
		}
	}
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					mainWindowForTeacher frame = new mainWindowForTeacher();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	public mainWindowForTeacher() throws IOException, InterruptedException {
		JFrame f = new JFrame();
		f.setBounds(100, 100, 450, 300);
		f.getContentPane().setLayout(null);
		JButton logout = new JButton("注销");
		//获取课程列表
		
		logout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(getContentPane(),
						"注销成功!", "注销成功", JOptionPane.INFORMATION_MESSAGE);
				login lg = new login();
			}
		});
		logout.setBounds(343,10,91,38);
		f.getContentPane().add(logout);
		JLabel lblNewLabel = new JLabel("\u8BFE\u7A0B\u5217\u8868");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(0, 0, 80, 30);
		f.getContentPane().add(lblNewLabel);
		cl.CourseList.clear();
		Client.getUserCourseList(Client.usr);
		Thread.sleep(1000);
		String[] str = new String[cl.getSize()];
		int count = 0;
		for (Course c:cl.CourseList) {
			str[count++] = c.name;
		}
		JList courselist = new JList(str);
		JScrollPane ps = new JScrollPane(courselist);  
		ps.setBounds(0, 30, 180, 230);
		f.getContentPane().add(ps);
		JButton createCourseButton = new JButton("\u521B\u5EFA\u8BFE\u7A0B");
		createCourseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					createCourse cc = new createCourse();
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		createCourseButton.setBounds(190, 30, 118, 38);
		f.getContentPane().add(createCourseButton);
		
		JButton enterGroupButton = new JButton("\u8FDB\u5165\u7FA4\u7EC4");
		enterGroupButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String courseName;
				if ((courseName = (String) courselist.getSelectedValue()) != null) {
						try {
							courseGroupForTeacher cg = new courseGroupForTeacher(courseName);
						} catch (InterruptedException | IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
				}
			}
		});
		enterGroupButton.setBounds(190, 67, 118, 38);
		f.getContentPane().add(enterGroupButton);
		
		JLabel identity = new JLabel();
		identity.setText("当前用户：" + Client.usr.name);
		identity.setBounds(185, 8, 133, 20);
		f.getContentPane().add(identity);
		new Thread(new mainWindowThread(courselist)).start();
		f.setResizable(false);
		f.setVisible(true);
	}
}
