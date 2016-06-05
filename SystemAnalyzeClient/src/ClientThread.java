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
		if (!s.isClosed())
			br = new BufferedReader(new InputStreamReader(s.getInputStream()));
		if (!s2.isClosed())
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
						//用原来的socket
						CourseList cl = new CourseList();
						//有问题
						while ((cl = (CourseList)is.readObject()) != null) {
							registCourse.cl = cl;
						}
					}
					break;
				case "GETUSERCOURSELISTSUCCESS":
					{
						//用原来的socket
						CourseList cl = new CourseList();
						//有问题
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
						//有问题

						while ((cl = (CourseInformationList)is.readObject()) != null) {
							groupInformation.cl = cl;
						}
					}
					break;
				case "UPLOADHOMEWORKSUCCESS":
					{
						uploadHomework.uploadHomeworkStatus = "UPLOADHOMEWORKSUCCESS";
					}
					break;
				case "GETCOURSERESOURCESUCCESS":
					{
						CourseResourceList cl = new CourseResourceList();
						//有问题
						while ((cl = (CourseResourceList)is.readObject()) != null) {
							uploaddownload.cl = cl;
						}
					}
					break;
				case "UPLOADCOURSERESOURCESUCCESS":
					{
						uploaddownload.uploadCourseResourceStatus = "UPLOADCOURSERESOURCESUCCESS";
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
