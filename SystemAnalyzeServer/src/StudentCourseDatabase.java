import java.io.BufferedReader;
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
	static int ID = 0;
	static int SID = 1;
	static int Sname = 2;
	static int CID = 3;
	static int Cname = 4;
	public static int getID() throws NumberFormatException, IOException {
		FileReader fileReader = new FileReader(fileName);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String out = "";
		int maxID = 0;
		for (out = bufferedReader.readLine(); out != null; out = bufferedReader.readLine()) {
			String studentcourseInfo[] = out.split(" ");
			if (Integer.parseInt(studentcourseInfo[ID]) > maxID) {
				maxID = Integer.parseInt(studentcourseInfo[ID]);
			}
		}
		maxID = maxID + 1;
		bufferedReader.close();
		fileReader.close();
		return maxID;
	}
	public static boolean isfind(int _SID, int _CID) throws IOException {
		FileReader fileReader = new FileReader(fileName);
		@SuppressWarnings("resource")
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String out = "";
		for (out = bufferedReader.readLine(); out != null; out = bufferedReader.readLine()) {
			String studentcourseInfo[] = out.split(" ");
			if (_SID == Integer.parseInt(studentcourseInfo[SID]) && _CID == Integer.parseInt(studentcourseInfo[CID])) {
				return true;
			}
		}
		bufferedReader.close();
		fileReader.close();
		return false;
	}
	public static List<Course> getUserCourseList(int _SID) throws IOException {
		FileReader fileReader = new FileReader(fileName);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String out = "";
		List<Course> courseList = new ArrayList<Course>();
		for (out = bufferedReader.readLine(); out != null; out = bufferedReader.readLine()) {
			String studentcourseInfo[] = out.split(" ");
			if (Integer.parseInt(studentcourseInfo[SID]) == _SID){
				Course c = new Course(Integer.parseInt(studentcourseInfo[CID]), studentcourseInfo[Cname]);
				courseList.add(c);
			}
		}
		bufferedReader.close();
		fileReader.close();
		return courseList;
	}
	public static List<User> getCourseUserList(int _CID) throws IOException {
		FileReader fileReader = new FileReader(fileName);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String out = "";
		List<User> userList = new ArrayList<User>();
		for (out = bufferedReader.readLine(); out != null; out = bufferedReader.readLine()) {
			String studentcourseInfo[] = out.split(" ");
			if (Integer.parseInt(studentcourseInfo[CID]) == _CID){
				User s = UserDatabase.getUser(Integer.parseInt(studentcourseInfo[SID]));
				userList.add(s);
			}
		}
		bufferedReader.close();
		fileReader.close();
		return userList;
	}
	public static boolean insert(int _SID, int _CID) throws IOException {
		if (StudentCourseDatabase.isfind(_SID, _CID)) {
			return false;
		}
		FileWriter fileWriter = new FileWriter(fileName, true);
		String input = getID() + "";
		input += " ";
		input += SID;
		input += " ";
		input += UserDatabase.getUser(_SID).name;
		input += " ";
		input += CID;
		input += " ";
		input += CourseDatabase.getCourse(_CID).name;
		input += "\r\n";
		fileWriter.write(input);
		fileWriter.close();
		return true;
	}
}