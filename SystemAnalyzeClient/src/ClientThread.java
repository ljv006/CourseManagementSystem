import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientThread implements Runnable{
	private Socket s;
	BufferedReader br = null;
	ObjectInputStream is = null;
	public ClientThread(Socket s, Socket s2) throws IOException{
		this.s = s;
		br = new BufferedReader(new InputStreamReader(s.getInputStream()));
		is = new ObjectInputStream(s2.getInputStream());
	}
	public void run() {
		try {
			String content = null;
			while ((content = br.readLine()) != null) {
				System.out.println(content);
				switch(content) {
				case "LOGINSUCCESS":
					{
						login.loginStatus = "LOGINSUCCESS";
					}
					break;
				case "REGISTERSUCCESS":
					{
						register.registerStatus = "REGISTERSUCCESS";
					}
					break;
				case "GETALLCOURSELISTSUCCESS":
					{
						//��ԭ����socket
						CourseList cl = new CourseList();
						//������
						while ((cl = (CourseList)is.readObject()) != null) {
							registCourse.cl = cl;
						}
					}
					break;
				case "GETUSERCOURSELISTSUCCESS":
					{
						//��ԭ����socket
						CourseList cl = new CourseList();
						//������
						while ((cl = (CourseList)is.readObject()) != null) {
							for (Course c:cl.CourseList)
								System.out.println(c.name);
							mainWindow.cl = cl;
						}
					}
					break;
				case "REGISTERCOURSESUCCESS":
					{
						registCourse.registerCourseStatus = "REGISTERCOURSESUCCESS";
					}
					break;
				case "GETCOURSEINFORMATIONSUCCESS":
					{
						CourseInformationList cl = new CourseInformationList();
						//������
						System.out.println("the size of ci:" + cl.getSize());
						while ((cl = (CourseInformationList)is.readObject()) != null) {
							groupInformation.cl = cl;
						}
					}
					break;
			
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
