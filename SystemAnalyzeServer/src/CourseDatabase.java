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
public class CourseDatabase {
	static String fileName = System.getProperty("user.dir") + "\\src\\CourseList.txt";
	static int ID = 0;
	static int name =1;
	public static boolean isfind(Course course) throws IOException {
		FileReader fileReader = new FileReader(fileName);
		@SuppressWarnings("resource")
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String out = "";
		for (out = bufferedReader.readLine(); out != null; out = bufferedReader.readLine()) {
			String courseInfo[] = out.split(" ");
			if (course.ID.equals(courseInfo[ID])) {
				return true;
			}
		}
		bufferedReader.close();
		fileReader.close();
		return false;
	}
	public static List<Course> getAllCourseList() throws IOException {
		FileReader fileReader = new FileReader(fileName);
		@SuppressWarnings("resource")
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String out = "";
		List<Course> courseList = new ArrayList<Course>();
		for (out = bufferedReader.readLine(); out != null; out = bufferedReader.readLine()) {
			String courseInfo[] = out.split(" ");
			Course c = new Course(null, null);
			c.ID = courseInfo[ID];
			c.name = courseInfo[name];
			courseList.add(c);
		}
		bufferedReader.close();
		fileReader.close();
		return courseList;
	}
	public static boolean insert(Course course) throws IOException {
		if (isfind(course)) {
			return false;
		}
		FileWriter fileWriter = new FileWriter(fileName, true);
		String input = course.ID;
		input += " ";
		input += course.name;
		input += "\r\n";
		fileWriter.write(input);
		fileWriter.close();
		return true;
	}
	public static  boolean delete(Course course) throws IOException{
		if (!isfind(course)) {
			return false;
		}
		FileWriter fileWriter = new FileWriter("temp.txt");
		FileReader fileReader = new FileReader(fileName);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String out = "";
		for (out = bufferedReader.readLine(); out != null; out = bufferedReader.readLine()) {
			String courseInfo[] = out.split(" ");
			if (!course.ID.equals(courseInfo[ID])) {
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