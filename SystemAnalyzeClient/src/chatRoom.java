import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.event.ActionEvent;

public class chatRoom extends JFrame {

	private JPanel contentPane;
	private JTextField contentField;
	public static String courseName;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					chatRoom frame = new chatRoom(courseName);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public chatRoom(String _courseName) {
		courseName = _courseName;
		JFrame f = new JFrame();
		f.setBounds(100, 100, 450, 300);
		JButton sendButton = new JButton("\u53D1\u9001");
		sendButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String content = contentField.getText();
				String speaker = Client.usr.name;
				System.out.println("this is speaker: " + speaker);
				Long t = new Date().getTime();
				DateFormat df2 = DateFormat.getDateTimeInstance();//可以精确到时分
				String time = df2.format(t);
				Chat c = new Chat(null, null, content, time, speaker);
				try {
					Client.sendMessage(courseName, c);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				contentField.setText("");
			}
		});
		sendButton.setBounds(338,124,85,45);
		f.getContentPane().add(sendButton);
		f.getContentPane().setLayout(null);
		
		JList chatContentList = new JList();
		chatContentList.setBounds(0, 0, 233, 262);
		f.getContentPane().add(chatContentList);
		
		contentField = new JTextField();
		contentField.setBounds(243, 62, 181, 52);
		f.getContentPane().add(contentField);
		contentField.setColumns(10);
		
		JButton refreshButton = new JButton("\u5237\u65B0\u804A\u5929\u7A97\u53E3");
		refreshButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
		refreshButton.setBounds(243, 124, 85, 45);
		f.getContentPane().add(refreshButton);
		f.setVisible(true);
	}
}
