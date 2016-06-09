import java.awt.EventQueue;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.UnknownHostException;
import java.awt.event.ActionEvent;

public class groupInformation extends JFrame {
	private static final long serialVersionUID = 1L;
	private JTable courseInfoTable;
	public static String courseName;
	public static CourseInformationList cl = new CourseInformationList();
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
				cl.CourseInfoList.clear();
				try {
					Client.getCourseInfo(courseName);
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				try {
					Thread.sleep(10000);
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
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JButton gobackButton = new JButton("\u8FD4\u56DE");
		gobackButton.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				try {
					@SuppressWarnings("unused")
					mainWindow newMainWindow = new mainWindow();
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
		JScrollPane ps = new JScrollPane(courseInfoTable);  
        ps.setBounds(0, 48, 324, 214);
		f.getContentPane().add(ps);
		
		JLabel lblNewLabel = new JLabel("\u8BFE\u7A0B\u4FE1\u606F");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(10, 10, 295, 27);
		f.getContentPane().add(lblNewLabel);
		t = new Thread(new groupInformationThread(courseInfoTable));
		t.start();
		f.setResizable(false);
		f.setVisible(true);
	}
}
