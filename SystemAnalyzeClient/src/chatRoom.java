import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.awt.event.ActionEvent;

public class chatRoom extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField contentField;
	public static String courseName;
	public static ChatList cl;
	public Thread t = null;
	/**
	 * Launch the application.
	 */
	class ChatRoomThread implements Runnable{
		JList<String> chatContentList;
		public ChatRoomThread(JList<String> list) {
			chatContentList = list;
		}
		public void run() {
			while (true) {
				try {
					Client.getMessage(courseName);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				DefaultListModel<String> model = new DefaultListModel<String>();
				for (Chat c:cl.ChatList) {
					model.addElement(c.speaker + ':' + c.content);
				}
				chatContentList.setModel(model);
			}
		}
	}
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					@SuppressWarnings("unused")
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
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setBounds(100, 100, 450, 300);
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
		JButton sendButton = new JButton("\u53D1\u9001");
		sendButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String content = contentField.getText();
				String speaker = Client.usr.name;
				System.out.println("this is speaker: " + speaker);
				Long t = new Date().getTime();
				DateFormat df2 = DateFormat.getDateTimeInstance();//可以精确到时分
				String time = df2.format(t);
				Chat c = new Chat(0, 0, content, time, speaker);
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
		
		JList<String> chatContentList = new JList<String>();
		JScrollPane ps = new JScrollPane(chatContentList);  
        ps.setBounds(0, 0, 233, 262);
		f.getContentPane().add(ps);
		
		try {
			Client.getMessage(courseName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Thread.sleep(500);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		DefaultListModel<String> model = new DefaultListModel<String>();
		for (Chat c:cl.ChatList) {
			System.out.println(c.speaker);
			model.addElement(c.speaker + " " + c.time + " " + c.content);
		}
		chatContentList.setModel(model);
		contentField = new JTextField();
		contentField.setBounds(243, 62, 181, 52);
		f.getContentPane().add(contentField);
		contentField.setColumns(10);
		
		
		t = new Thread(new ChatRoomThread(chatContentList));
		t.start();
		f.setResizable(false);
		f.setVisible(true);
	}
}
