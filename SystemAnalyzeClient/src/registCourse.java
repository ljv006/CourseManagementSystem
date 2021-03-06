import java.awt.EventQueue;
import java.awt.Toolkit;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.List;
import java.awt.event.ActionEvent;

public class registCourse extends JFrame {
	private static final long serialVersionUID = 1L;
	public static CourseList cl = new CourseList();
	public static String registerCourseStatus;
	public Thread t = null;
	/**
	 * Launch the application.
	 */
	class registCourseThread implements Runnable{
		JList<String> courselist;
		public registCourseThread(JList<String> list) {
			courselist = list;
		}
		public void run() {
			while (true) {
				cl.CourseList.clear();
				try {
					Client.getAllCourseList();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				DefaultListModel<String> model = new DefaultListModel<String>();
				for (Course c:cl.CourseList) {
					model.addElement(c.name);
				}
				courselist.setModel(model);
			}
		}
	}
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					@SuppressWarnings("unused")
					registCourse frame = new registCourse();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	private void close() {
        WindowEvent closeWin = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(closeWin);
    }
	/**
	 * Create the frame.
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	public registCourse() throws IOException, InterruptedException {
		JFrame f = new JFrame();
		f.setBounds(100, 100, 450, 300);
		f.getContentPane().setLayout(null);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JButton gobackButton = new JButton("\u8FD4\u56DE");
		gobackButton.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				close();
				try {
					if (!Client.usr.identity.equals("Teacher")) {
						@SuppressWarnings("unused")
						mainWindow newMainWindow = new mainWindow();
					} else {
						@SuppressWarnings("unused")
						mainWindowForTeacher newMainWindow = new mainWindowForTeacher();
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
		gobackButton.setBounds(332,-1,102,41);
		f.getContentPane().add(gobackButton);
		
		JLabel lblNewLabel = new JLabel("\u6240\u6709\u8BFE\u7A0B\u5217\u8868");
		lblNewLabel.setBounds(10, 6, 77, 26);
		f.getContentPane().add(lblNewLabel);
		JList<String> courselist = new JList<String>();
		cl.CourseList.clear();
		Client.getAllCourseList();
		Thread.sleep(500);
		DefaultListModel<String> model = new DefaultListModel<String>();
		for (Course c:cl.CourseList) {
			model.addElement(c.name);
		}
		courselist.setModel(model);
		JScrollPane ps = new JScrollPane(courselist);  
        ps.setBounds(0, 30, 180, 230);
		f.getContentPane().add(ps);
		
		JButton registerButton = new JButton("\u6CE8\u518C");
		registerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				List<String> courseName;
				if ((courseName = courselist.getSelectedValuesList()) != null) {
						try {
							Client.registerCourse(courseName);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
				}
				System.out.println(courseName.size());
				while (true) {
					System.out.println("registerCourseStatus");
					if (registerCourseStatus != null && registerCourseStatus.equals("REGISTERCOURSESUCCESS")) {
						JOptionPane.showMessageDialog(getContentPane(),
								"注册课程成功!", "注册课程成功", JOptionPane.INFORMATION_MESSAGE);
						break;
					}
					else if (registerCourseStatus != null && registerCourseStatus.equals("REGISTERCOURSEFAIL")) {
						
					}
				}
			}
		});
		registerButton.setBounds(216, 82, 102, 41);
		f.getContentPane().add(registerButton);
		
		t = new Thread(new registCourseThread(courselist));
		t.start();
		f.setResizable(false);
		f.setVisible(true);
	}

}
