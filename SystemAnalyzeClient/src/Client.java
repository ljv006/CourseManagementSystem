/**
 * ����1�������ע����ر���ʷ����
 */
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Queue;

import javax.imageio.IIOException;
//�ͻ���
public class Client{
	//����IP
	public static String ip = "127.0.0.1";
	//�����˿�
	public static int Transfer_port = 8888;
	public static int Receive_port = 10023;
	public static int Transfer_port2 = 8889;
	public static User usr = null;
	public static ObjectInputStream is = null;
	public static ObjectOutputStream os = null;
	public static void main(String[] args) throws IOException {
		login lg = new login();
	}
	//�ȸ��������˷�һ������������ȷ��login������Ǳߵȴ�����user
	public static void login(User user) throws IOException{
		Socket socket = new Socket(ip, Transfer_port);
		Socket socket2 = new Socket(ip, Transfer_port2);
		PrintStream ps = new PrintStream(socket.getOutputStream());
		ps.println(Command.Login);
		os = new ObjectOutputStream(socket2.getOutputStream());
		os.writeObject(user); 
        os.flush();
        new Thread(new ClientThread(socket, socket2)).start();
	}
	public static void register(User user) throws IOException{
		Socket socket = new Socket(ip, Transfer_port);
		Socket socket2 = new Socket(ip, Transfer_port2);
		PrintStream ps = new PrintStream(socket.getOutputStream());
		ps.println(Command.Register);
		os = new ObjectOutputStream(socket2.getOutputStream());
		os.writeObject(user); 
        os.flush();
        new Thread(new ClientThread(socket, socket2)).start();
	}
	public static void getAllCourseList() throws IOException {
		Socket socket = new Socket(ip, Transfer_port);
		Socket socket2 = new Socket(ip, Transfer_port2);
		PrintStream ps = new PrintStream(socket.getOutputStream());
		System.out.println(Command.getAllCourseList);
		ps.println(Command.getAllCourseList);
        new Thread(new ClientThread(socket, socket2)).start();
	}
	public static void getUserCourseList(User usr) throws IOException {
		Socket socket = new Socket(ip, Transfer_port);
		Socket socket2 = new Socket(ip, Transfer_port2);
		PrintStream ps = new PrintStream(socket.getOutputStream());
		System.out.println(Command.getUserCourseList);
		ps.println(Command.getUserCourseList);
		ps.println(usr.ID);
        new Thread(new ClientThread(socket, socket2)).start();
	}
	//��ʦ���Դ����γ̣�δ��ɣ�
	public static void registerCourse(List<String> courseName) throws UnknownHostException, IOException {
		Socket socket = new Socket(ip, Transfer_port);
		Socket socket2 = new Socket(ip, Transfer_port2);
		PrintStream ps = new PrintStream(socket.getOutputStream());
		System.out.println(Command.registerCourse);
		ps.println(Command.registerCourse);
		ps.println(Client.usr.ID);
		os = new ObjectOutputStream(socket2.getOutputStream());
		os.writeObject(courseName); 
        os.flush();
        new Thread(new ClientThread(socket, socket2)).start();
	}
	public static void setCourseInfo() {
		
	}
	public static void getCourseInfo(String courseName) throws UnknownHostException, IOException {
		Socket socket = new Socket(ip, Transfer_port);
		Socket socket2 = new Socket(ip, Transfer_port2);
		PrintStream ps = new PrintStream(socket.getOutputStream());
		System.out.println(Command.getCourseInformation);
		ps.println(Command.getCourseInformation);
		ps.println(courseName);
        new Thread(new ClientThread(socket, socket2)).start();
	}
	public static void uploadHomework(File file) throws IOException {
		Socket socket = new Socket(ip, Transfer_port);
		Socket socket2 = new Socket(ip, Transfer_port2);
		PrintStream ps = new PrintStream(socket.getOutputStream());
		System.out.println(Command.uploadHomework);
		ps.println(Command.uploadHomework);
		ps.println(file.getName());
		System.out.println(file.getName());
		int length = 0;  
        double sumL = 0 ;  
        byte[] sendBytes = null;  
        DataOutputStream dos = null;  
        FileInputStream fis = null;  
        boolean bool = false;  
        try {  
            long l = file.length();
            dos = new DataOutputStream(socket2.getOutputStream());  
            fis = new FileInputStream(file);        
            sendBytes = new byte[1024];    
            while ((length = fis.read(sendBytes, 0, sendBytes.length)) > 0) {  
                sumL += length;    
                System.out.println("�Ѵ��䣺"+((sumL/l)*100)+"%");  
                dos.write(sendBytes, 0, length);  
                dos.flush();  
            }   
            //��Ȼ�������Ͳ�ͬ����JAVA���Զ�ת������ͬ�������ͺ������Ƚ�  
            if(sumL==l){  
                bool = true;  
            }  
        }catch (Exception e) {  
            System.out.println("�ͻ����ļ������쳣");  
            bool = false;  
            e.printStackTrace();    
        } finally{    
            if (dos != null)  
                dos.close();  
            if (fis != null)  
                fis.close();         
        }  
        System.out.println(bool?"�ɹ�":"ʧ��");
        new Thread(new ClientThread(socket, socket2)).start();
	}
	public static void getCourseResource(String courseName) throws UnknownHostException, IOException {
		Socket socket = new Socket(ip, Transfer_port);
		Socket socket2 = new Socket(ip, Transfer_port2);
		PrintStream ps = new PrintStream(socket.getOutputStream());
		System.out.println(Command.getCourseResource);
		ps.println(Command.getCourseResource);
		ps.println(courseName);
        new Thread(new ClientThread(socket, socket2)).start();
	}
	public static void uploadCourseResource(File file, String courseName) throws UnknownHostException, IOException {
		Socket socket = new Socket(ip, Transfer_port);
		Socket socket2 = new Socket(ip, Transfer_port2);
		PrintStream ps = new PrintStream(socket.getOutputStream());
		System.out.println(Command.uploadCourseResource);
		ps.println(Command.uploadCourseResource);
		ps.println(file.getName());
		ps.println(courseName);
		System.out.println(file.getName());
		int length = 0;  
        double sumL = 0 ;  
        byte[] sendBytes = null;  
        DataOutputStream dos = null;  
        FileInputStream fis = null;  
        boolean bool = false;  
        try {  
            long l = file.length();
            dos = new DataOutputStream(socket2.getOutputStream());  
            fis = new FileInputStream(file);        
            sendBytes = new byte[1024];    
            while ((length = fis.read(sendBytes, 0, sendBytes.length)) > 0) {  
                sumL += length;    
                System.out.println("�Ѵ��䣺"+((sumL/l)*100)+"%");  
                dos.write(sendBytes, 0, length);  
                dos.flush();  
            }   
            //��Ȼ�������Ͳ�ͬ����JAVA���Զ�ת������ͬ�������ͺ������Ƚ�  
            if(sumL==l){  
                bool = true;  
            }  
        }catch (Exception e) {  
            System.out.println("�ͻ����ļ������쳣");  
            bool = false;  
            e.printStackTrace();    
        } finally{    
            if (dos != null)  
                dos.close();  
            if (fis != null)  
                fis.close();         
        }  
        System.out.println(bool?"�ɹ�":"ʧ��");
        new Thread(new ClientThread(socket, socket2)).start();
	}
	public static void downloadCourseResource(String fileName) throws UnknownHostException, IOException {
		Socket socket = new Socket(ip, Transfer_port);
		Socket socket2 = new Socket(ip, Transfer_port2);
		PrintStream ps = new PrintStream(socket.getOutputStream());
		System.out.println(Command.downloadCourseResource);
		ps.println(Command.downloadCourseResource);
		ps.println(fileName);
        new Thread(new ClientThread(socket, socket2)).start();
	}
	public static void sendMessage(String courseName, Chat c) throws UnknownHostException, IOException {
		Socket socket = new Socket(ip, Transfer_port);
		Socket socket2 = new Socket(ip, Transfer_port2);
		PrintStream ps = new PrintStream(socket.getOutputStream());
		System.out.println(Command.sendMessage);
		ps.println(Command.sendMessage);
		ps.println(courseName);
		os = new ObjectOutputStream(socket2.getOutputStream());
		os.writeObject(c); 
        os.flush();
        new Thread(new ClientThread(socket, socket2)).start();
	}
	public static void getMessage(String courseName) {
		
	}
}