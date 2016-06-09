import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
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
	private static final long serialVersionUID = 1L;
	private JTable courseResourceTable;
	public static CourseResourceList cl = new CourseResourceList();
	public static String courseName;
	public static String uploadCourseResourceStatus;
	public static String downloadCourseResourceStatus;
	public Thread t = null;
	/**
	 * Launch the application.
	 */
	class uploaddownloadThread implements Runnable{
		JTable courseResourceList;
		public uploaddownloadThread(JTable table) {
			courseResourceList = table;
		}
		public void run() {
			while (true) {
				cl.CourseResourceList.clear();
				try {
					Client.getCourseResource(courseName);
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				try {
					Thread.sleep(3000);
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
		}
	}
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					@SuppressWarnings("unused")
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
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JButton gobackButton = new JButton("\u8FD4\u56DE");
		gobackButton.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				try {
					if (Client.usr.identity.equals("Teacher")) {
						@SuppressWarnings("unused")
						mainWindowForTeacher newMainWindow = new mainWindowForTeacher();
					} else {
						@SuppressWarnings("unused")
						mainWindow newMainWindow = new mainWindow();
					}
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
		gobackButton.setBounds(342,3,102,41);
		f.getContentPane().add(gobackButton);
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
						break;
					}
					else if (uploadCourseResourceStatus != null && uploadCourseResourceStatus.equals("UPLOADCOURSERESOURCEFAIL")) {
						
					}
				}
			}
		});
		uploadButton.setBounds(219,44,116,36);
		f.getContentPane().add(uploadButton);
		
		
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
		JScrollPane ps = new JScrollPane(courseResourceTable);  
        ps.setBounds(0, 0, 200, 262);
		f.getContentPane().add(ps);
		JButton downloadButton = new JButton("\u4E0B\u8F7D\u8D44\u6E90");
		downloadButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String fileName;
				int row = courseResourceTable.getSelectedRow();
				if ((fileName = (String)courseResourceTable.getValueAt(row, 0)) != null) {
					try {
						Client.downloadCourseResource(fileName);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					while (true) {
						System.out.println("downloadCourseResourceStatus");
						if (downloadCourseResourceStatus != null && downloadCourseResourceStatus.equals("DOWNLOADCOURSERESOURCESUCCESS")) {
							JOptionPane.showMessageDialog(getContentPane(),
									"下载资源成功!", "下载资源成功", JOptionPane.INFORMATION_MESSAGE);
							break;
						}
						else if (downloadCourseResourceStatus != null && downloadCourseResourceStatus.equals("DOWNLOADCOURSERESOURCEFAIL")) {
							
						}
					}
				}
			}
		});
		downloadButton.setBounds(219, 80, 116, 36);
		f.getContentPane().add(downloadButton);
		JLabel courseResource = new JLabel("\u8BFE\u7A0B\u8D44\u6E90\u5217\u8868");
		courseResource.setHorizontalAlignment(SwingConstants.CENTER);
		courseResource.setBounds(10, 10, 177, 27);
		f.getContentPane().add(courseResource);
		t = new Thread(new uploaddownloadThread(courseResourceTable));
		t.start();
		f.setResizable(false);
		f.setVisible(true);
	}
}
