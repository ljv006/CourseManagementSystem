import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import javax.swing.JTextPane;

public class login extends JFrame {
	private static final long serialVersionUID = 1L;
	public static String loginStatus;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					@SuppressWarnings("unused")
					login frame = new login();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public login() {
		JFrame f = new JFrame();
		f.setBounds(100, 100, 450, 300);
		f.getContentPane().setLayout(null);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JLabel lblNewLabel_1 = new JLabel("Login");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(175, 10, 84, 45);
		f.getContentPane().add(lblNewLabel_1);
		lblNewLabel_1.setFont(new java.awt.Font("å®‹ä½“", 1, 24));
		
		JLabel lblNewLabel = new JLabel("ID");
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel.setBounds(86, 75, 75, 26);
		f.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_2 = new JLabel("Password");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_2.setBounds(86, 110, 74, 26);
		f.getContentPane().add(lblNewLabel_2);
		
		JTextPane IDtext = new JTextPane();
		IDtext.setBounds(168, 75, 95, 25);
		f.getContentPane().add(IDtext);
		
		JPasswordField Passwordtext=new JPasswordField();
		Passwordtext.setEchoChar('*');
		Passwordtext.setBounds(168, 115, 95, 25);
		f.getContentPane().add(Passwordtext);
		
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				@SuppressWarnings("deprecation")
				User user = new User(Integer.parseInt(IDtext.getText()), null, Passwordtext.getText(), null);
				try {
					Client.login(user);
					while (true) {
						System.out.println("loginStatus");
						if (loginStatus != null && loginStatus.equals(Command.LoginSuccess)) {
							JOptionPane.showMessageDialog(getContentPane(),
									"µÇÂ¼³É¹¦!", "µÇÂ¼³É¹¦", JOptionPane.INFORMATION_MESSAGE);
							if (Client.usr.identity.equals("Teacher")) {
								@SuppressWarnings("unused")
								mainWindowForTeacher mw = new mainWindowForTeacher();
								f.dispose();
								break;
							} else {
								@SuppressWarnings("unused")
								mainWindow mw = new mainWindow();
								f.dispose();
								break;
							}
						}
						else if (loginStatus != null && loginStatus.equals(Command.LoginFailed)) {
								JOptionPane.showMessageDialog(getContentPane(),
										"ÇëÖØÐÂµÇÂ¼!", "µÇÂ¼Ê§°Ü", JOptionPane.INFORMATION_MESSAGE);
								IDtext.setText("");
								Passwordtext.setText("");
								break;
						}
					}
					
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		okButton.setBounds(100, 170, 90, 35);
		f.getContentPane().add(okButton);
		
		JButton registerButton = new JButton("Register");
		registerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				@SuppressWarnings("unused")
				register RegisterWindow = new register();
			}
		});
		registerButton.setBounds(217, 170, 90, 35);
		f.getContentPane().add(registerButton);
		f.setResizable(false);
		f.setVisible(true);
	}
}
