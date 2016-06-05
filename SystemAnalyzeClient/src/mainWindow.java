import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
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

public class mainWindow extends JFrame {

	private JPanel contentPane;
	public static CourseList cl = new CourseList();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					mainWindow frame = new mainWindow();
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
	public mainWindow() throws IOException, InterruptedException {
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
		Thread.sleep(500);
		String[] str = new String[cl.getSize()];
		int count = 0;
		for (Course c:cl.CourseList) {
			str[count++] = c.name;
		}
		JList courselist = new JList(str);
		courselist.setBounds(10, 37, 180, 215);
		f.getContentPane().add(courselist);
		JButton registerCourseButton = new JButton("\u6CE8\u518C/\u521B\u5EFA\u8BFE\u7A0B");
		registerCourseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					registCourse rc = new registCourse();
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		registerCourseButton.setBounds(200, 71, 118, 38);
		f.getContentPane().add(registerCourseButton);
		
		JButton enterGroupButton = new JButton("\u8FDB\u5165\u7FA4\u7EC4");
		enterGroupButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String courseName;
				if ((courseName = (String) courselist.getSelectedValue()) != null) {
						courseGroup cg = new courseGroup(courseName);
						
				}
			}
		});
		enterGroupButton.setBounds(200, 108, 118, 38);
		f.getContentPane().add(enterGroupButton);
		
		JButton refreshButton = new JButton("\u5237\u65B0\u5217\u8868");
		refreshButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.CourseList.clear();
				try {
					Client.getUserCourseList(Client.usr);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				try {
					Thread.sleep(500);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				DefaultListModel model = new DefaultListModel();
				for (Course c:cl.CourseList) {
					model.addElement(c.name);
				}
				courselist.setModel(model);
			}
		});
		refreshButton.setBounds(200, 34, 118, 38);
		f.getContentPane().add(refreshButton);
		f.setVisible(true);
	}
}
