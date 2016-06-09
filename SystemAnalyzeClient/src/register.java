import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.JCheckBox;

public class register extends JFrame {
	private static final long serialVersionUID = 1L;
	public static String registerStatus;
	public static String userID;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					@SuppressWarnings("unused")
					register frame = new register();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void close() {
		dispose();
    }
	/**
	 * Create the frame.
	 */
	public register() {
		JFrame f = new JFrame();
		f.setBounds(100, 100, 450, 300);
		f.getContentPane().setLayout(null);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JLabel lblNewLabel_1 = new JLabel("Register");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(157, 10, 130, 35);
		f.getContentPane().add(lblNewLabel_1);
		lblNewLabel_1.setFont(new java.awt.Font("瀹嬩綋", 1, 24));
		
		JLabel lblNewLabel_2 = new JLabel("Password");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_2.setBounds(100, 146, 73, 15);
		f.getContentPane().add(lblNewLabel_2);
		
		JTextPane Passwordtext = new JTextPane();
		Passwordtext.setBounds(179, 136, 95, 25);
		f.getContentPane().add(Passwordtext);
		
		JButton okButton = new JButton("OK");
		okButton.setBounds(100, 200, 90, 35);
		f.getContentPane().add(okButton);
		JTextPane Nametext = new JTextPane();
		Nametext.setBounds(179, 98, 95, 25);
		
		f.getContentPane().add(Nametext);
		
		
		JLabel lblName = new JLabel("Name");
		lblName.setHorizontalAlignment(SwingConstants.LEFT);
		lblName.setBounds(100, 98, 54, 15);
		f.getContentPane().add(lblName);
		
		JCheckBox TeacherCheckbox = new JCheckBox("Teacher");
		TeacherCheckbox.setBounds(50, 50, 84, 23);
		f.getContentPane().add(TeacherCheckbox);
		
		JCheckBox TACheckbox = new JCheckBox("Teaching Assitant");
		TACheckbox.setBounds(140, 50, 130, 23);
		f.getContentPane().add(TACheckbox);
		
		JCheckBox StudentCheckbox = new JCheckBox("Student");
		StudentCheckbox.setBounds(281, 50, 103, 23);
		f.getContentPane().add(StudentCheckbox);
		
		

		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String identity = new String();
				if (TeacherCheckbox.isSelected()) {
					identity = "Teacher";
				} else if (TACheckbox.isSelected()) {
					identity = "TA";
				} else if (StudentCheckbox.isSelected()) {
					identity = "Student";
				}
				User user = new User(0, Nametext.getText(), Passwordtext.getText(), identity);
				try {
					Client.register(user);
					while (true) {
						System.out.println("registerStatus");
						if (registerStatus != null && registerStatus.equals("REGISTERSUCCESS")) {
							System.out.println(registerStatus);
							JOptionPane.showMessageDialog(getContentPane(),
									"你的用户ID为" + userID, "注册成功!", JOptionPane.INFORMATION_MESSAGE);
							@SuppressWarnings("unused")
							login lg = new login();
							f.dispose();
							break;
						}
						else if (registerStatus != null && registerStatus.equals("REGISTERFAILED")) {
							System.out.println(registerStatus);
							JOptionPane.showMessageDialog(getContentPane(),
									"用户已存在!", "注册失败", JOptionPane.INFORMATION_MESSAGE);
							close();
							@SuppressWarnings("unused")
							register r = new register();
							f.dispose();
							break;
						}
					}
					
				}
				catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		JButton resetButton = new JButton("Reset");
		resetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TeacherCheckbox.setSelected(false);
				TACheckbox.setSelected(false);
				StudentCheckbox.setSelected(false);
				Nametext.setText("");
				Passwordtext.setText("");
				
			}
		});
		resetButton.setBounds(215, 200, 90, 35);
		f.getContentPane().add(resetButton);
		f.setResizable(false);
		f.setVisible(true);
	}
}
