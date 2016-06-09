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
public class CourseDatabase {
	static String fileName = System.getProperty("user.dir") + "\\CourseList.txt";
	static int ID = 0;
	static int name =1;
	public static int getID() throws IOException {
		FileReader fileReader = new FileReader(fileName);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String out = "";
		int maxID = 0;
		for (out = bufferedReader.readLine(); out != null; out = bufferedReader.readLine()) {
			String studentInfo[] = out.split(" ");
			if (Integer.parseInt(studentInfo[ID]) > maxID) {
				maxID = Integer.parseInt(studentInfo[ID]);
			}
		}
		maxID = maxID + 1;
		bufferedReader.close();
		fileReader.close();
		return maxID;
	}
	public static boolean isfind(String courseName) throws IOException {
		FileReader fileReader = new FileReader(fileName);
		@SuppressWarnings("resource")
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String out = "";
		for (out = bufferedReader.readLine(); out != null; out = bufferedReader.readLine()) {
			String courseInfo[] = out.split(" ");
			if (courseName.equals(courseInfo[name])) {
				return true;
			}
		}
		bufferedReader.close();
		fileReader.close();
		return false;
	}
	public static boolean isfind(int CID) throws IOException {
		FileReader fileReader = new FileReader(fileName);
		@SuppressWarnings("resource")
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String out = "";
		for (out = bufferedReader.readLine(); out != null; out = bufferedReader.readLine()) {
			String courseInfo[] = out.split(" ");
			if (CID == Integer.parseInt(courseInfo[ID])) {
				return true;
			}
		}
		bufferedReader.close();
		fileReader.close();
		return false;
	}
	public static Course getCourse(int CID) throws IOException {
		FileReader fileReader = new FileReader(fileName);
		@SuppressWarnings("resource")
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String out = "";
		for (out = bufferedReader.readLine(); out != null; out = bufferedReader.readLine()) {
			String courseInfo[] = out.split(" ");
			if (Integer.parseInt(courseInfo[ID]) == CID) {
				Course c = new Course(CID, courseInfo[name]);
				return c;
			}
		}
		bufferedReader.close();
		fileReader.close();
		return null;
	}
	public static Course getCourse(String _courseName) throws IOException {
		FileReader fileReader = new FileReader(fileName);
		@SuppressWarnings("resource")
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String out = "";
		for (out = bufferedReader.readLine(); out != null; out = bufferedReader.readLine()) {
			String courseInfo[] = out.split(" ");
			if (courseInfo[name].equals(_courseName)) {
				Course c = new Course(Integer.parseInt(courseInfo[ID]), _courseName);
				return c;
			}
		}
		bufferedReader.close();
		fileReader.close();
		return null;
	}
	public static List<Course> getAllCourseList() throws IOException {
		FileReader fileReader = new FileReader(fileName);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String out = "";
		List<Course> courseList = new ArrayList<Course>();
		for (out = bufferedReader.readLine(); out != null; out = bufferedReader.readLine()) {
			String courseInfo[] = out.split(" ");
			Course c = new Course(Integer.parseInt(courseInfo[ID]), courseInfo[name]);
			courseList.add(c);
		}
		bufferedReader.close();
		fileReader.close();
		return courseList;
	}
	public static boolean insert(Course course) throws IOException {
		if (isfind(course.name)) {
			return false;
		}
		FileWriter fileWriter = new FileWriter(fileName, true);
		String input = getID() + "";
		input += " ";
		input += course.name;
		input += "\r\n";
		fileWriter.write(input);
		fileWriter.close();
		return true;
	}
}