import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/*
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
*/
public class StudentCourseDatabase {
	static String fileName = System.getProperty("user.dir") + "\\src\\StudentCourseList.txt";
	static int SID = 0;
	static int Sname = 1;
	static int CID = 2;
	static int Cname = 3;
	public static boolean isfind(String _Sname, String _Cname) throws IOException {
		FileReader fileReader = new FileReader(fileName);
		@SuppressWarnings("resource")
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String out = "";
		for (out = bufferedReader.readLine(); out != null; out = bufferedReader.readLine()) {
			String studentcourseInfo[] = out.split(" ");
			if (_Sname.equals(studentcourseInfo[Sname]) && _Cname.equals(studentcourseInfo[Cname])) {
				return true;
			}
		}
		bufferedReader.close();
		fileReader.close();
		return false;
	}
	public static List<Course> getUserCourseList(String _SID) throws IOException {
		FileReader fileReader = new FileReader(fileName);
		@SuppressWarnings("resource")
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String out = "";
		List<Course> courseList = new ArrayList<Course>();
		for (out = bufferedReader.readLine(); out != null; out = bufferedReader.readLine()) {
			String studentcourseInfo[] = out.split(" ");
			if (studentcourseInfo[SID].equals(_SID)){
				Course c = new Course(null, null);
				c.ID = studentcourseInfo[CID];
				c.name = studentcourseInfo[Cname];
				courseList.add(c);
			}
		}
		bufferedReader.close();
		fileReader.close();
		return courseList;
	}
	public static List<User> getCourseUserList(String _CID) throws IOException {
		FileReader fileReader = new FileReader(fileName);
		@SuppressWarnings("resource")
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String out = "";
		List<User> userList = new ArrayList<User>();
		for (out = bufferedReader.readLine(); out != null; out = bufferedReader.readLine()) {
			String studentcourseInfo[] = out.split(" ");
			if (studentcourseInfo[CID].equals(_CID)){
				User c = new User(null, studentcourseInfo[Sname], null, null);
				userList.add(c);
			}
		}
		bufferedReader.close();
		fileReader.close();
		return userList;
	}
	public static boolean insert(String SID, String CID) throws IOException {
		if (StudentCourseDatabase.isfind(SID, CID)) {
			return false;
		}
		FileWriter fileWriter = new FileWriter(fileName, true);
		String input = SID;
		input += " ";
		input += StudentDatabase.getSname(SID);
		input += " ";
		input += CID;
		input += " ";
		input += CourseDatabase.getCname(CID);
		input += "\r\n";
		fileWriter.write(input);
		fileWriter.close();
		return true;
	}
	public static  boolean delete(StudentCourse studentcourse) throws IOException{
		if (!isfind(studentcourse.SID, studentcourse.CID)) {
			return false;
		}
		FileWriter fileWriter = new FileWriter("temp.txt");
		FileReader fileReader = new FileReader(fileName);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String out = "";
		for (out = bufferedReader.readLine(); out != null; out = bufferedReader.readLine()) {
			String studentcourseInfo[] = out.split(" ");
			if (!studentcourse.CID.equals(studentcourseInfo[CID])) {
				fileWriter.write(out + "\r\n");
			}
		}
		fileWriter.close();
		bufferedReader.close();
		
		fileWriter = new FileWriter(fileName);
		fileReader = new FileReader("temp.txt");
		bufferedReader = new BufferedReader(fileReader);
		for (out = bufferedReader.readLine(); out != null; out = bufferedReader.readLine()) {
			fileWriter.write(out + "\r\n");
		}
		fileWriter.close();
		fileReader.close();
		
		File file = new File("temp.txt");
		if (file.exists()) {
			file.delete();
		}
		return true;
	}
}