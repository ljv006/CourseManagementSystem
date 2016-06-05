import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientThread implements Runnable{
	private Socket s;
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
				case "LOGINSUCCESS":
					{
						User usr = (User)is.readObject();
						Client.usr = usr;
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
						//������
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
				case "DOWNLOADCOURSERESOURCESUCCESS":
					{
						byte[] inputByte = null;  
				        int length = 0;  
				        DataInputStream dis = null;  
				        FileOutputStream fos = null;  
				        String fileName = br.readLine();
				        //�������洢�ļ�·��
				        String filePath = "D:/DownloadCourseResource/" + fileName;
				        try {  
				            try {  
				                dis = new DataInputStream(s2.getInputStream());  
				                File f = new File("D:/DownloadCourseResource");  
				                if(!f.exists()){  
				                    f.mkdir();    
				                }  
				                /*   
				                 * �ļ��洢λ��   
				                 */  
				                fos = new FileOutputStream(new File(filePath));      
				                inputByte = new byte[1024];     
				                System.out.println("��ʼ��������...");    
				                while ((length = dis.read(inputByte, 0, inputByte.length)) > 0) {  
				                    fos.write(inputByte, 0, length);  
				                    fos.flush();      
				                }  
				                System.out.println("��ɽ��գ�"+filePath);
				            } finally {  
				                if (fos != null)  
				                    fos.close();  
				                if (dis != null)  
				                    dis.close();  
				            }  
				        } catch (Exception e) {  
				            e.printStackTrace();  
				        }
						uploaddownload.downloadCourseResourceStatus = "DOWNLOADCOURSERESOURCESUCCESS";
					}
					break;
				case "SENDMESSAGESUCCESS":
					{
						
					
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
