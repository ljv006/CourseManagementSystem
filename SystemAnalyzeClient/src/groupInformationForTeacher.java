import java.awt.EventQueue;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.util.Date;
import java.awt.event.ActionEvent;

public class groupInformationForTeacher extends JFrame {
	private static final long serialVersionUID = 1L;
	private JTable courseInfoTable;
	private JTextField courseInfo;
	public static String courseName;
	public static CourseInformationList cl = new CourseInformationList();
	public static String groupInformationStatus;
	public Thread t = null;
	/**
	 * Launch the application.
	 */
	class groupInformationThread implements Runnable{
		JTable groupInformationList;
		public groupInformationThread(JTable table) {
			groupInformationList = table;
		}
		public void run() {
			while (true) {
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
				groupInformationList.setModel(new DefaultTableModel(data,columnNames));
				AbstractTableModel model = (AbstractTableModel) groupInformationList.getModel();
				model.fireTableDataChanged();
			}
		}
	}
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					@SuppressWarnings("unused")
					groupInformationForTeacher frame = new groupInformationForTeacher(courseName);
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
	public groupInformationForTeacher(String _courseName) throws InterruptedException, UnknownHostException, IOException {
		courseName = _courseName;
		JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setBounds(100, 100, 450, 300);
		JButton gobackButton = new JButton("\u8FD4\u56DE");
		gobackButton.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				try {
					@SuppressWarnings("unused")
					mainWindowForTeacher newMainWindow = new mainWindowForTeacher();
					t.stop();
					f.dispose();
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		gobackButton.setBounds(332,-1,102,41);
		f.getContentPane().add(gobackButton);
		JButton createInfoButton = new JButton("\u521B\u5EFA\u8BFE\u7A0B\u4FE1\u606F");
		createInfoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Long t = new Date().getTime();
				DateFormat df2 = DateFormat.getDateInstance();//可以精确到时分
				String time = df2.format(t);
				try {
					Client.setCourseInfo(courseName, courseInfo.getText(), time);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				while (true) {
					System.out.println("groupInformationStatus");
					if (groupInformationStatus != null && groupInformationStatus.equals("SETCOURSEINFORMATIONSUCCESS")) {
						JOptionPane.showMessageDialog(getContentPane(),
								"创建课程信息成功!", "创建课程信息成功", JOptionPane.INFORMATION_MESSAGE);
						courseInfo.setText(" ");
						break;
					}
					else if (groupInformationStatus != null && groupInformationStatus.equals("SETCOURSEINFORMATIONFAILED")) {
						
					}
				}
			}
		});
		createInfoButton.setBounds(198, 89, 111, 36);
		f.getContentPane().add(createInfoButton);
		f.getContentPane().setLayout(null);
		
		//JTable显示
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
		JScrollPane ps = new JScrollPane(courseInfoTable);  
        ps.setBounds(0, 0, 180, 262);
		f.getContentPane().add(ps);
		
		JLabel lblNewLabel = new JLabel("\u8BFE\u7A0B\u4FE1\u606F");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(10, 10, 181, 27);
		f.getContentPane().add(lblNewLabel);

		courseInfo = new JTextField();
		courseInfo.setHorizontalAlignment(SwingConstants.CENTER);
		courseInfo.setBounds(198, 44, 111, 35);
		f.getContentPane().add(courseInfo);
		courseInfo.setColumns(10);
		t = new Thread(new groupInformationThread(courseInfoTable));
		t.start();
		f.setResizable(false);
		f.setVisible(true);
	}
}
