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
	public static boolean isfind(StudentCourse studentcourse) throws IOException {
		FileReader fileReader = new FileReader(fileName);
		@SuppressWarnings("resource")
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String out = "";
		for (out = bufferedReader.readLine(); out != null; out = bufferedReader.readLine()) {
			String studentcourseInfo[] = out.split(" ");
			if (studentcourse.SID.equals(studentcourseInfo[SID]) && studentcourse.CID.equals(studentcourseInfo[CID])) {
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
	public static boolean insert(StudentCourse studentcourse) throws IOException {
		if (isfind(studentcourse)) {
			return false;
		}
		FileWriter fileWriter = new FileWriter(fileName, true);
		String input = studentcourse.SID;
		input += " ";
		input += studentcourse.Sname;
		input += " ";
		input += studentcourse.CID;
		input += " ";
		input += studentcourse.Cname;
		input += "\r\n";
		fileWriter.write(input);
		fileWriter.close();
		return true;
	}
	public static  boolean delete(StudentCourse studentcourse) throws IOException{
		if (!isfind(studentcourse)) {
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