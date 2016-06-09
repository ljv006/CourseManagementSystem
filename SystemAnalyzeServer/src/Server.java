import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Queue;
import java.util.List;
import java.io.*;
public class Server {
	public static List<Socket> socketList = Collections.synchronizedList(new ArrayList<>());
	public static String ip = "172.18.41.171";
	public static int Transfer_port = 10023;
	public static int Receive_port = 8888;
	public static int Receive_port2 = 8889;
	public static Queue<User> qq = new ArrayDeque<User>();
	public static void main(String[] args) throws IOException{
		@SuppressWarnings("resource")
		ServerSocket ss = new ServerSocket(Receive_port);
		@SuppressWarnings("resource")
		ServerSocket sss = new ServerSocket(Server.Receive_port2);
		String path = System.getProperty("user.dir");
		File f = new File(path);
		if(!f.exists()){
			f.mkdirs();
		} 
		String fileName[] = {"UserCourseList.txt", "chatRoomRecord.txt","CourseInformationList.txt",
				"CourseList.txt","CourseResourceList.txt","User.txt"};
		File file[] = new File[6];
		for (int i = 0; i < 6; i++) {
			file[i] = new File(fileName[i]);
			if(!file[i].exists()){
				try {
					file[i].createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}	
		}
		while (true) {
			Socket s = ss.accept();
			Socket s2 = sss.accept();
			socketList.add(s);
			new Thread(new ServerThread(s, s2)).start();
		}
	}
}

