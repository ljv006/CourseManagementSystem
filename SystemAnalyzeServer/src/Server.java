import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Queue;
import java.util.Stack;
import java.*;
import java.util.List;
import java.io.*;
public class Server {
	public static List<Socket> socketList = Collections.synchronizedList(new ArrayList<>());
	public static String ip = "172.18.41.171";
	public static int Transfer_port = 10023;
	public static int Receive_port = 8888;
	public static int Receive_port2 = 8889;
	public static StudentDatabase db = new StudentDatabase();
	public static Queue<User> qq = new ArrayDeque<User>();
	public static void main(String[] args) throws IOException{
		ServerSocket ss = new ServerSocket(Receive_port);
		ServerSocket sss = new ServerSocket(Server.Receive_port2);
		while (true) {
			Socket s = ss.accept();
			Socket s2 = sss.accept();
			socketList.add(s);
			new Thread(new ServerThread(s, s2)).start();
		}
	}
}

