import java.util.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerThread implements Runnable{
	Socket s = null;
	BufferedReader br = null;
	ObjectInputStream is = null; 
	ObjectOutputStream os = null;
	Socket s2 = null;
	public ServerThread(Socket s, Socket s2) throws IOException{
		this.s  = s;
		this.s2 = s2;
		br = new BufferedReader(new InputStreamReader(s.getInputStream()));
		os = new ObjectOutputStream(s2.getOutputStream());
	}
	public void run() {
		System.out.println("HH");
		try {
			String content = null;
			while ((content = readFromClient()) != null) {
				System.out.println(content);
				System.out.println("here");
				switch(content) {
				case "LOGIN":
					{
						PrintStream ps = new PrintStream(s.getOutputStream());
						is = new ObjectInputStream(s2.getInputStream());
						try {
							User user = (User)is.readObject();
							if (StudentDatabase.isfind(user)) {
								ps.println(Command.LoginSuccess);							
							}
						}
						catch (ClassNotFoundException e) {
							e.printStackTrace();
						} 
						catch (IOException e) {
							e.printStackTrace();
						} 
						
					}
					break;
				case "REGISTER":
					{
						PrintStream ps = new PrintStream(s.getOutputStream());
						is = new ObjectInputStream(s2.getInputStream());
						try {
							User user = (User)is.readObject();
							if (!StudentDatabase.isfind(user)) {
								StudentDatabase.insert(user);
								ps.println(Command.RegisterSuccess);
							}
						}
						catch (ClassNotFoundException e) {
							e.printStackTrace();
						} 
						catch (IOException e) {
							e.printStackTrace();
						} 
						
					}
					break;
				case "GETALLCOURSELIST":
					{
						PrintStream ps = new PrintStream(s.getOutputStream());
						ps.println(Command.getAllCourseListSuccess);
						
						try {
							List<Course> courseList = CourseDatabase.getAllCourseList();
							CourseList cl = new CourseList(courseList);
							os.writeObject(cl);
							os.flush();
						}
						catch (IOException e) {
							e.printStackTrace();
						} 
					}
					break;
				case "GETUSERCOURSELIST":
					{
						PrintStream ps = new PrintStream(s.getOutputStream());
						ps.println(Command.getUserCourseListSuccess);
						String ID = readFromClient();
						try {
							List<Course> courseList = StudentCourseDatabase.getUserCourseList(ID);
							CourseList cl = new CourseList(courseList);
							System.out.println("the size:" + cl.getSize());
							os.writeObject(cl);
							os.flush();
						}
						catch (IOException e) {
							e.printStackTrace();
						} 
					}
					break;
				}
			}
			System.out.println("end");
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	private String readFromClient() {
		try{
			return br.readLine();
		}
		catch (IOException e) {
			Server.socketList.remove(s);
		}
		return null;
	}
}
