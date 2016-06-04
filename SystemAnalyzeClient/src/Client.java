/**
 * 问题1：如何在注销后关闭历史窗口
 */
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Queue;

import javax.imageio.IIOException;
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
	public static void registerCourse() {
		
	}
}