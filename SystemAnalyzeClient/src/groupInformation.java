import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.UnknownHostException;
import java.awt.event.ActionEvent;

public class groupInformation extends JFrame {

	private JPanel contentPane;
	private JTable courseInfoTable;
	private JTextField courseInfo;
	public static String courseName;
	public static CourseInformationList cl = new CourseInformationList();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					groupInformation frame = new groupInformation(courseName);
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
	public groupInformation(String _courseName) throws InterruptedException, UnknownHostException, IOException {
		courseName = _courseName;
		JFrame f = new JFrame();
		f.setBounds(100, 100, 450, 300);
		JButton createInfoButton = new JButton("\u521B\u5EFA\u8BFE\u7A0B\u4FE1\u606F");
		createInfoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		createInfoButton.setBounds(198, 89, 111, 36);
		f.getContentPane().add(createInfoButton);
		f.getContentPane().setLayout(null);
		
		//JTableœ‘ æ
		cl.CourseInfoList.clear();
		Client.getCourseInfo(courseName);
		Thread.sleep(500);
		int count = 1;
		String[] columnNames = {"Content","Time"};
		String[][] data = new String[100][2];
		data[0][0] = "Content";
		data[0][1] = "Time";
		for (CourseInformation cif:cl.CourseInfoList) {
			data[count][0] = cif.content;
			data[count][1] = cif.time;
			count++;
		}
		
		courseInfoTable = new JTable(data,columnNames);
		courseInfoTable.setBounds(10, 63, 181, 188);
		f.getContentPane().add(courseInfoTable);
		
		JLabel lblNewLabel = new JLabel("\u8BFE\u7A0B\u4FE1\u606F");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(10, 10, 181, 27);
		f.getContentPane().add(lblNewLabel);
		
		JButton refreshInfoButton = new JButton("\u5237\u65B0\u8BFE\u7A0B\u4FE1\u606F");
		refreshInfoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.CourseInfoList.clear();
				try {
					Client.getCourseInfo(courseName);
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				try {
					Thread.sleep(500);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				int count = 1;
				String[] columnNames = {"Content","Time"};
				String[][] data = new String[100][2];
				data[0][0] = "Content";
				data[0][1] = "Time";
				for (CourseInformation cif:cl.CourseInfoList) {
					data[count][0] = cif.content;
					data[count][1] = cif.time;
					count++;
				}
				courseInfoTable.setModel(new DefaultTableModel(data,columnNames));
				AbstractTableModel model = (AbstractTableModel) courseInfoTable.getModel();
				model.fireTableDataChanged();
			}
		});
		refreshInfoButton.setBounds(198, 135, 111, 36);
		f.getContentPane().add(refreshInfoButton);
		
		courseInfo = new JTextField();
		courseInfo.setText("\u8BF7\u8F93\u5165\u8BFE\u7A0B\u4FE1\u606F");
		courseInfo.setHorizontalAlignment(SwingConstants.CENTER);
		courseInfo.setBounds(198, 44, 111, 35);
		f.getContentPane().add(courseInfo);
		courseInfo.setColumns(10);
		
		
		f.setVisible(true);
	}
}
