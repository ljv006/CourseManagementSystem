import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ClientThread implements Runnable{
	@SuppressWarnings("unused")
	private Socket s = null;
	BufferedReader br = null;
	ObjectInputStream is = null;
	private Socket s2;
	public ClientThread(Socket s, Socket s2) throws IOException{
		this.s = s;
		this.s2 = s2;
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
				case Command.LoginSuccess:
					{
						User usr = (User)is.readObject();
						Client.usr = usr;
						login.loginStatus = Command.LoginSuccess;
						return;
					}
				case Command.LoginFailed:
					{
						login.loginStatus = Command.LoginFailed;
						return;
					}
				case Command.RegisterSuccess:
					{
						register.userID = br.readLine();
						register.registerStatus = Command.RegisterSuccess;
						return;
					}
				case Command.RegisterFailed:
					{
						register.registerStatus = Command.RegisterFailed;
						return;
					}
				case Command.getAllCourseListSuccess:
					{
						//用原来的socket
						CourseList cl = new CourseList();
						//有问题
						while ((cl = (CourseList)is.readObject()) != null) {
							registCourse.cl = cl;
						}
						return;
					}
				case Command.getUserCourseListSuccess:
					{
						//用原来的socket
						CourseList cl = new CourseList();
						//有问题
						while ((cl = (CourseList)is.readObject()) != null) {
							for (Course c:cl.CourseList)
								System.out.println(c.name);
							mainWindow.cl = cl;
						}
						return;
					}
				case Command.getUserCourseListSuccessForTeacher:
					{
						//用原来的socket
						CourseList cl = new CourseList();
						//有问题
						while ((cl = (CourseList)is.readObject()) != null) {
							mainWindowForTeacher.cl = cl;
						}
						return;
					}
				case Command.registerCourseSuccess:
					{
						registCourse.registerCourseStatus = Command.registerCourseSuccess;
						return;
					}
				case Command.getCourseInformationSuccess:
					{
						CourseInformationList cl = new CourseInformationList();
						//有问题
						while ((cl = (CourseInformationList)is.readObject()) != null) {
							groupInformation.cl = cl;
						}
						return;
					}
				case Command.getCourseInformationSuccessForTeacher:
					{
						CourseInformationList cl = new CourseInformationList();
						//有问题
						while ((cl = (CourseInformationList)is.readObject()) != null) {
							groupInformationForTeacher.cl = cl;
						}
						return;
					}
				case Command.uploadHomeworkSuccess:
					{
						uploadHomework.uploadHomeworkStatus = Command.uploadHomeworkSuccess;
						return;
					}
				case Command.getCourseResourceSuccess:
					{
						CourseResourceList cl = new CourseResourceList();
						//有问题
						while ((cl = (CourseResourceList)is.readObject()) != null) {
							uploaddownload.cl = cl;
						}
						return;
					}
				case Command.uploadCourseResourceSuccess:
					{
						uploaddownload.uploadCourseResourceStatus = Command.uploadCourseResourceSuccess;
						return;
					}
				case Command.downloadCourseResourceSuccess:
					{
						byte[] inputByte = null;  
				        int length = 0;  
				        DataInputStream dis = null;  
				        FileOutputStream fos = null;  
				        String fileName = br.readLine();
				        //服务器存储文件路径
				        String filePath = "D:/DownloadCourseResource/" + fileName;
				        try {  
				            try {  
				                dis = new DataInputStream(s2.getInputStream());  
				                File f = new File("D:/DownloadCourseResource");  
				                if(!f.exists()){  
				                    f.mkdir();    
				                }  
				                /*   
				                 * 文件存储位置   
				                 */  
				                fos = new FileOutputStream(new File(filePath));      
				                inputByte = new byte[1024];     
				                System.out.println("开始接收数据...");    
				                while ((length = dis.read(inputByte, 0, inputByte.length)) > 0) {  
				                    fos.write(inputByte, 0, length);  
				                    fos.flush();      
				                }  
				                System.out.println("完成接收："+filePath);
				            } finally {  
				                if (fos != null)  
				                    fos.close();  
				                if (dis != null)  
				                    dis.close();  
				            }  
				        } catch (Exception e) {  
				            e.printStackTrace();  
				        }
						uploaddownload.downloadCourseResourceStatus = Command.downloadCourseResourceSuccess;
						return;
					}
				case Command.getMessageSuccess:
					{
						ChatList cl = new ChatList();
						//有问题
						while ((cl = (ChatList)is.readObject()) != null) {
							chatRoom.cl = cl;
						}
						return;
					}
				case Command.createCourseSuccess:
					{
						createCourse.createCourseStatus = Command.createCourseSuccess;
						return;
					}
				case Command.getGroupMemberSuccess:
					{
						groupMemberList gl = new groupMemberList();
						//有问题
						while ((gl = (groupMemberList)is.readObject()) != null) {
							courseGroupForTeacher.gl = gl;
						}
						return;
					}
				case Command.setCourseInformationSuccess:
					{
						groupInformationForTeacher.groupInformationStatus = Command.setCourseInformationSuccess;
						return;
					}
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
