import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JList;
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class mainWindowForTeacher extends JFrame {
	private static final long serialVersionUID = 1L;
	public static CourseList cl = new CourseList();
	public Thread t = null;
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
				DefaultListModel<String> model = new DefaultListModel<String>();
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
					@SuppressWarnings("unused")
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
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JButton logout = new JButton("ע��");
		//��ȡ�γ��б�
		
		logout.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(getContentPane(),
						"ע���ɹ�!", "ע���ɹ�", JOptionPane.INFORMATION_MESSAGE);
				@SuppressWarnings("unused")
				login lg = new login();
				t.stop();
				f.dispose();
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
		JList<String> courselist = new JList<String>(str);
		JScrollPane ps = new JScrollPane(courselist);  
		ps.setBounds(0, 30, 180, 230);
		f.getContentPane().add(ps);
		JButton createCourseButton = new JButton("\u521B\u5EFA\u8BFE\u7A0B");
		createCourseButton.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				try {
					@SuppressWarnings("unused")
					createCourse cc = new createCourse();
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
		createCourseButton.setBounds(190, 65, 118, 38);
		f.getContentPane().add(createCourseButton);
		
		JButton enterGroupButton = new JButton("\u8FDB\u5165\u7FA4\u7EC4");
		enterGroupButton.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				String courseName;
				if ((courseName = (String) courselist.getSelectedValue()) != null) {
						try {
							@SuppressWarnings("unused")
							courseGroupForTeacher cg = new courseGroupForTeacher(courseName);
							t.stop();
							f.dispose();
						} catch (InterruptedException | IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
				}
			}
		});
		enterGroupButton.setBounds(190, 106, 118, 38);
		f.getContentPane().add(enterGroupButton);
		
		JLabel identity = new JLabel();
		identity.setText("��ǰ�û���" + Client.usr.name);
		identity.setBounds(190, 10, 133, 20);
		f.getContentPane().add(identity);
		JLabel identityLabel = new JLabel();
		identityLabel.setText("�û�Ȩ�ޣ�" + Client.usr.identity);
		identityLabel.setBounds(190, 28, 133, 20);
		f.getContentPane().add(identityLabel);
		t = new Thread(new mainWindowThread(courselist));
		t.start();
		f.setResizable(false);
		f.setVisible(true);
	}
}
