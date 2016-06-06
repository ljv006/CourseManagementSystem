import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class uploadHomework extends JFrame {

	private JPanel contentPane;
	public static String uploadHomeworkStatus;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					uploadHomework frame = new uploadHomework();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public uploadHomework() {
		JFrame f = new JFrame();
		f.setBounds(100, 100, 450, 300);
		JButton gobackButton = new JButton("\u8FD4\u56DE");
		gobackButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					mainWindow newMainWindow = new mainWindow();
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
		JButton uploadButton = new JButton("\u4E0A\u4F20\u4F5C\u4E1A");
		uploadButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					JFileChooser chooser = new JFileChooser();
		        	chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		        	int ret = chooser.showOpenDialog(f);
		        	if (ret == JFileChooser.APPROVE_OPTION) {
		        	File dir = chooser.getSelectedFile();
		        	// dir is the selected directory
		        	Client.uploadHomework(dir);
		        	}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				while (true) {
					//System.out.println("uploadHomeworkStatus");
					if (uploadHomeworkStatus != null && uploadHomeworkStatus.equals("UPLOADHOMEWORKSUCCESS")) {
						JOptionPane.showMessageDialog(getContentPane(),
								"上传作业成功!", "上传作业成功", JOptionPane.INFORMATION_MESSAGE);
						try {
							mainWindow mw = new mainWindow();
						} catch (IOException | InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						break;
					}
					else if (uploadHomeworkStatus != null && uploadHomeworkStatus.equals("UPLOADHOMEWORKFAIL")) {
						
					}
				}
			}
		});
		
		uploadButton.setBounds(176, 40, 99, 36);
		f.getContentPane().add(uploadButton);
		f.getContentPane().setLayout(null);
		f.setResizable(false);
		f.setVisible(true);
	}

}
