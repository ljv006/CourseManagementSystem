/**
 * 问题1：如何在注销后关闭历史窗口
 */

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;
//客户端
public class Client{
	//主机IP
	public static String ip = "127.0.0.1";
	//主机端口
	public static int Transfer_port = 8888;
	public static int Receive_port = 10023;
	public static int Transfer_port2 = 8889;
	public static User usr = null;
	public static ObjectInputStream is = null;
	public static ObjectOutputStream os = null;
	public static void main(String[] args) throws IOException {
		@SuppressWarnings("unused")
		login lg = new login();
	}
	//先给服务器端发一个命令请求，在确认login命令后，那边等待接收user
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
	public static void createCourse(String courseName) throws UnknownHostException, IOException {
		Socket socket = new Socket(ip, Transfer_port);
		Socket socket2 = new Socket(ip, Transfer_port2);
		PrintStream ps = new PrintStream(socket.getOutputStream());
		System.out.println(Command.createCourse);
		ps.println(Command.createCourse);
		ps.println(Client.usr.ID);
		ps.println(courseName);
        new Thread(new ClientThread(socket, socket2)).start();
	}
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
	public static void setCourseInfo(String courseName, String content, String time) throws UnknownHostException, IOException {
		Socket socket = new Socket(ip, Transfer_port);
		Socket socket2 = new Socket(ip, Transfer_port2);
		PrintStream ps = new PrintStream(socket.getOutputStream());
		System.out.println(Command.setCourseInformation);
		ps.println(Command.setCourseInformation);
		ps.println(courseName);
		ps.println(content);
		ps.println(time);
        new Thread(new ClientThread(socket, socket2)).start();
	}
	public static void getCourseInfo(String courseName) throws UnknownHostException, IOException {
		Socket socket = new Socket(ip, Transfer_port);
		Socket socket2 = new Socket(ip, Transfer_port2);
		PrintStream ps = new PrintStream(socket.getOutputStream());
		System.out.println(Command.getCourseInformation);
		ps.println(Command.getCourseInformation);
		ps.println(Client.usr.identity);
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
                System.out.println("已传输："+((sumL/l)*100)+"%");  
                dos.write(sendBytes, 0, length);  
                dos.flush();  
            }   
            //虽然数据类型不同，但JAVA会自动转换成相同数据类型后在做比较  
            if(sumL==l){  
                bool = true;  
            }  
        }catch (Exception e) {  
            System.out.println("客户端文件传输异常");  
            bool = false;  
            e.printStackTrace();    
        } finally{    
            if (dos != null)  
                dos.close();  
            if (fis != null)  
                fis.close();         
        }  
        System.out.println(bool?"成功":"失败");
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
                System.out.println("已传输："+((sumL/l)*100)+"%");  
                dos.write(sendBytes, 0, length);  
                dos.flush();  
            }   
            //虽然数据类型不同，但JAVA会自动转换成相同数据类型后在做比较  
            if(sumL==l){  
                bool = true;  
            }  
        }catch (Exception e) {  
            System.out.println("客户端文件传输异常");  
            bool = false;  
            e.printStackTrace();    
        } finally{    
            if (dos != null)  
                dos.close();  
            if (fis != null)  
                fis.close();         
        }  
        System.out.println(bool?"成功":"失败");
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
	public static void getMessage(String courseName) throws UnknownHostException, IOException {
		Socket socket = new Socket(ip, Transfer_port);
		Socket socket2 = new Socket(ip, Transfer_port2);
		PrintStream ps = new PrintStream(socket.getOutputStream());
		System.out.println(Command.getMessage);
		ps.println(Command.getMessage);
		ps.println(courseName);
        new Thread(new ClientThread(socket, socket2)).start();
	}
	public static void getCourseGroupMember(String courseName) throws UnknownHostException, IOException {
		Socket socket = new Socket(ip, Transfer_port);
		Socket socket2 = new Socket(ip, Transfer_port2);
		PrintStream ps = new PrintStream(socket.getOutputStream());
		System.out.println(Command.getGroupMember);
		ps.println(Command.getGroupMember);
		ps.println(courseName);
        new Thread(new ClientThread(socket, socket2)).start();
	}
}