import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.awt.event.ActionEvent;

public class uploaddownload extends JFrame {

	private JPanel contentPane;
	private JTable courseResourceTable;
	public static CourseResourceList cl = new CourseResourceList();
	public static String courseName;
	public static String uploadCourseResourceStatus;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					uploaddownload frame = new uploaddownload(courseName);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws IOException 
	 * @throws UnknownHostException 
	 * @throws InterruptedException 
	 */
	public uploaddownload(String _courseName) throws UnknownHostException, IOException, InterruptedException {
		courseName = _courseName;
		JFrame f = new JFrame();
		f.setBounds(100, 100, 450, 300);
		f.getContentPane().setLayout(null);
		JButton uploadButton = new JButton("\u4E0A\u4F20\u8D44\u6E90");
		uploadButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					JFileChooser chooser = new JFileChooser();
		        	chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		        	int ret = chooser.showOpenDialog(f);
		        	if (ret == JFileChooser.APPROVE_OPTION) {
		        	File dir = chooser.getSelectedFile();
		        	// dir is the selected directory
		        	Client.uploadCourseResource(dir, courseName);
		        	}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				while (true) {
					System.out.println("uploadCourseResourceStatus");
					if (uploadCourseResourceStatus != null && uploadCourseResourceStatus.equals("UPLOADCOURSERESOURCESUCCESS")) {
						JOptionPane.showMessageDialog(getContentPane(),
								"上传资源成功!", "上传资源成功", JOptionPane.INFORMATION_MESSAGE);
						try {
							mainWindow mw = new mainWindow();
						} catch (IOException | InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						break;
					}
					else if (uploadCourseResourceStatus != null && uploadCourseResourceStatus.equals("UPLOADCOURSERESOURCEFAIL")) {
						
					}
				}
			}
		});
		uploadButton.setBounds(252,71,116,36);
		f.getContentPane().add(uploadButton);
		
		JButton downloadButton = new JButton("\u4E0B\u8F7D\u8D44\u6E90");
		downloadButton.setBounds(252, 108, 116, 36);
		f.getContentPane().add(downloadButton);
		//JTable显示
		cl.CourseResourceList.clear();
		Client.getCourseResource(courseName);
		Thread.sleep(500);
		int count = 1;
		String[] columnNames = {"Filename","Size"};
		String[][] data = new String[100][2];
		data[0][0] = "Filename";
		data[0][1] = "Size";
		for (CourseResource cr:cl.CourseResourceList) {
			data[count][0] = cr.fileName;
			data[count][1] = cr.size;
			count++;
		}
		courseResourceTable = new JTable(data,columnNames);
		courseResourceTable.setBounds(10, 38, 199, 199);
		f.getContentPane().add(courseResourceTable);
		
		JLabel courseResource = new JLabel("\u8BFE\u7A0B\u8D44\u6E90\u5217\u8868");
		courseResource.setHorizontalAlignment(SwingConstants.CENTER);
		courseResource.setBounds(10, 10, 177, 27);
		f.getContentPane().add(courseResource);
		
		JButton refreshButton = new JButton("\u5237\u65B0\u5217\u8868");
		refreshButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.CourseResourceList.clear();
				try {
					Client.getCourseResource(courseName);
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
				String[] columnNames = {"Filename","Size"};
				String[][] data = new String[100][2];
				data[0][0] = "Filename";
				data[0][1] = "Size";
				for (CourseResource cr:cl.CourseResourceList) {
					data[count][0] = cr.fileName;
					data[count][1] = cr.size;
					count++;
				}
				courseResourceTable.setModel(new DefaultTableModel(data,columnNames));
				AbstractTableModel model = (AbstractTableModel) courseResourceTable.getModel();
				model.fireTableDataChanged();
			}
		});
		refreshButton.setBounds(252, 34, 116, 36);
		f.getContentPane().add(refreshButton);
		f.setVisible(true);
	}
}
