import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
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
	@SuppressWarnings("unchecked")
	public void run() {
		try {
			String content = null;
			while ((content = readFromClient()) != null) {
				System.out.println(content);
				switch(content) {
				case Command.Login:
					{
						PrintStream ps = new PrintStream(s.getOutputStream());
						is = new ObjectInputStream(s2.getInputStream());
						try {
							User user = (User)is.readObject();
							User _user = UserDatabase.getUser(user.ID);
							if (UserDatabase.isfind(_user.name) && UserDatabase.check(user)) 
							{
								os.writeObject(UserDatabase.getUser(user.ID));
								ps.println(Command.LoginSuccess);							
							} else {
								ps.println(Command.LoginFailed);	
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
				case Command.Register:
					{
						PrintStream ps = new PrintStream(s.getOutputStream());
						is = new ObjectInputStream(s2.getInputStream());
						try {
							User user = (User)is.readObject();
							if (UserDatabase.insert(user)) {
								ps.println(Command.RegisterSuccess);
								Thread.sleep(1000);
								ps.println(UserDatabase.getUser(user.name).ID);
							} else {
								ps.println(Command.RegisterFailed);
							}
						}
						catch (ClassNotFoundException e) {
							e.printStackTrace();
						} 
						catch (IOException e) {
							e.printStackTrace();
						} catch (InterruptedException e) {
							e.printStackTrace();
						} 
						
					}
					break;
				case Command.getAllCourseList:
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
				case Command.getUserCourseList:
					{
						int ID = Integer.parseInt(readFromClient());
						PrintStream ps = new PrintStream(s.getOutputStream());
						User usr = UserDatabase.getUser(ID);
						if (usr.identity.equals("Teacher")) {
							ps.println(Command.getUserCourseListSuccessForTeacher);
						} else {
							ps.println(Command.getUserCourseListSuccess);
						}
						try {
							List<Course> courseList = UserCourseDatabase.getUserCourseList(ID);
							CourseList cl = new CourseList(courseList);
							os.writeObject(cl);
							os.flush();
						}
						catch (IOException e) {
							e.printStackTrace();
						} 
					}
					break;
				case Command.registerCourse:
					{
						PrintStream ps = new PrintStream(s.getOutputStream());
						ps.println(Command.registerCourseSuccess);
						int SID = Integer.parseInt(readFromClient());
						is = new ObjectInputStream(s2.getInputStream());
						List<String> courseName = new ArrayList<String>();
						while ((courseName = (List<String>) is.readObject()) != null) {
							try {
								for (String cname:courseName) {
									int CID = CourseDatabase.getCourse(cname).ID;
									UserCourseDatabase.insert(SID, CID);
								}
							}
							catch (IOException e) {
								e.printStackTrace();
							}
						}
						
					}
					break;
				case Command.getCourseInformation:
					{
						PrintStream ps = new PrintStream(s.getOutputStream());
						
						String identity = readFromClient();
						String Cname = readFromClient();
						if (identity.equals("Teacher")) {
							ps.println(Command.getCourseInformationSuccessForTeacher);
						} else {
							ps.println(Command.getCourseInformationSuccess);
						}
						try {
							int CID = CourseDatabase.getCourse(Cname).ID;
							
							List<CourseInformation> courseInfoList = CourseInformationDatabase.getAllCourseInformationList(CID);
							CourseInformationList cl = new CourseInformationList(courseInfoList);
							System.out.println("This is size:" + cl.getSize());
							os.writeObject(cl);
							os.flush();
						}
						catch (IOException e) {
							e.printStackTrace();
						} 
						
					}
					break;
				case Command.uploadHomework:
					{
						PrintStream ps = new PrintStream(s.getOutputStream());
						ps.println(Command.uploadHomeworkSuccess);
						byte[] inputByte = null;  
				        int length = 0;  
				        DataInputStream dis = null;  
				        FileOutputStream fos = null;  
				        String fileName = readFromClient();
				        //服务器存储文件路径
				        String filePath = System.getProperty("user.dir") + "//Homework//" + fileName;
				        try {  
				            try {  
				                dis = new DataInputStream(s2.getInputStream());  
				                File f = new File(System.getProperty("user.dir") + "//Homework");  
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
						
					}
					break;
				case Command.getCourseResource:
					{
						PrintStream ps = new PrintStream(s.getOutputStream());
						ps.println(Command.getCourseResourceSuccess);
						String Cname = readFromClient();
						try {
							int CID = CourseDatabase.getCourse(Cname).ID;
							CourseResourceDatabase.renew(CID);
							List<CourseResource> courseResourceList = CourseResourceDatabase.getAllCourseResourceList(CID);
							CourseResourceList cl = new CourseResourceList(courseResourceList);
							os.writeObject(cl);
							os.flush();
						}
						catch (IOException e) {
							e.printStackTrace();
						} 
						
					}
					break;
				case Command.uploadCourseResource:
					{
						PrintStream ps = new PrintStream(s.getOutputStream());
						ps.println(Command.uploadCourseResourceSuccess);
						byte[] inputByte = null;  
				        int length = 0;  
				        DataInputStream dis = null;  
				        FileOutputStream fos = null;  
				        String fileName = readFromClient();
				        //服务器存储文件路径
				        String filePath = System.getProperty("user.dir") + "//CourseResource//" + fileName;
				        try {  
				            try {  
				                dis = new DataInputStream(s2.getInputStream());  
				                File f = new File(System.getProperty("user.dir") + "//CourseResource");  
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
						
					}
					break;
				case Command.downloadCourseResource:
					{
						PrintStream ps = new PrintStream(s.getOutputStream());
						ps.println(Command.downloadCourseResourceSuccess);
						String fileName = readFromClient();
						ps.println(fileName);
						int length = 0;  
				        double sumL = 0 ;  
				        byte[] sendBytes = null;  
				        DataOutputStream dos = null;  
				        FileInputStream fis = null;
				        File file = new File(System.getProperty("user.dir") + "//CourseResource//" + fileName);
				        boolean bool = false;  
				        try {  
				            long l = file.length();
				            dos = new DataOutputStream(s2.getOutputStream());  
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
					}
					break;
				case Command.sendMessage:
					{
						PrintStream ps = new PrintStream(s.getOutputStream());
						ps.println(Command.sendMessageSuccess);
						String courseName = readFromClient();
						is = new ObjectInputStream(s2.getInputStream());
						Chat c;
						while ((c = (Chat) is.readObject()) != null) {
							try {
									int CID = CourseDatabase.getCourse(courseName).ID;
									c.CID = CID;
									chatRoomDatabase.insert(c);
							}
							catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
					break;
				case Command.getMessage:
					{
						PrintStream ps = new PrintStream(s.getOutputStream());
						ps.println(Command.getMessageSuccess);
						String courseName = readFromClient();
						try {
							int CID = CourseDatabase.getCourse(courseName).ID;
							List<Chat> chatRecordList = chatRoomDatabase.getChatRecordList(CID);;
							ChatList cl = new ChatList(chatRecordList);
							os.writeObject(cl);
							os.flush();
						}
						catch (IOException e) {
							e.printStackTrace();
						} 
					}
					break;
				case Command.createCourse:
					{
						PrintStream ps = new PrintStream(s.getOutputStream());
						ps.println(Command.createCourseSuccess);
						int SID = Integer.parseInt(readFromClient());
						String courseName = readFromClient();
						try {
							Course c = new Course(0, courseName);
							CourseDatabase.insert(c);
							UserCourseDatabase.insert(SID, CourseDatabase.getCourse(courseName).ID);
						}
						catch (IOException e) {
							e.printStackTrace();
						} 
					}
					break;
				case Command.getGroupMember:
					{
						PrintStream ps = new PrintStream(s.getOutputStream());
						ps.println(Command.getGroupMemberSuccess);
						String courseName = readFromClient();
						try {
							int CID = CourseDatabase.getCourse(courseName).ID;
							List<User> userList = UserCourseDatabase.getCourseUserList(CID);
							groupMemberList gl = new groupMemberList(userList);
							os.writeObject(gl);
							os.flush();
						}
						catch (IOException e) {
							e.printStackTrace();
						} 
					}
					break;
				case Command.setCourseInformation:
					{
						PrintStream ps = new PrintStream(s.getOutputStream());
						ps.println(Command.setCourseInformationSuccess);
						String courseName = readFromClient();
						String message = readFromClient();
						String time = readFromClient();
						try {
							CourseInformation ci = new CourseInformation(0, CourseDatabase.getCourse(courseName).ID, time, message);
							CourseInformationDatabase.insert(ci);
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
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
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
