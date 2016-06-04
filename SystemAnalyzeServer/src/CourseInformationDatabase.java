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
public class CourseInformationDatabase {
	static String fileName = System.getProperty("user.dir") + "\\src\\CourseInformationList.txt";
	static int ID = 0;
	static int CID = 1;
	static int time = 2;
	static int content = 3;
	public static boolean isfind(CourseInformation courseInformation) throws IOException {
		FileReader fileReader = new FileReader(fileName);
		@SuppressWarnings("resource")
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String out = "";
		for (out = bufferedReader.readLine(); out != null; out = bufferedReader.readLine()) {
			String courseInfoInfo[] = out.split(" ");
			if (courseInformation.ID.equals(courseInfoInfo[ID])) {
				return true;
			}
		}
		bufferedReader.close();
		fileReader.close();
		return false;
	}

	public static List<CourseInformation> getAllCourseInformationList(String _CID) throws IOException {
		FileReader fileReader = new FileReader(fileName);
		@SuppressWarnings("resource")
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String out = "";
		List<CourseInformation> courseInfoList = new ArrayList<CourseInformation>();
		for (out = bufferedReader.readLine(); out != null; out = bufferedReader.readLine()) {
			String courseInfoInfo[] = out.split(" ");
			if (courseInfoInfo[CID].equals(_CID)) {
				CourseInformation courseInfo = new CourseInformation(courseInfoInfo[ID], 
						courseInfoInfo[CID], courseInfoInfo[time], courseInfoInfo[content]);
				courseInfoList.add(courseInfo);
			}
		}
		bufferedReader.close();
		fileReader.close();
		return courseInfoList;
	}
	public static boolean insert(CourseInformation courseInformation) throws IOException {
		if (isfind(courseInformation)) {
			return false;
		}
		FileWriter fileWriter = new FileWriter(fileName, true);
		String input = courseInformation.ID;
		input += " ";
		input += courseInformation.CID;
		input += " ";
		input += courseInformation.time;
		input += " ";
		input += courseInformation.content;
		input += "\r\n";
		fileWriter.write(input);
		fileWriter.close();
		return true;
	}
	public static  boolean delete(CourseInformation courseInformation) throws IOException{
		if (!isfind(courseInformation)) {
			return false;
		}
		FileWriter fileWriter = new FileWriter("temp.txt");
		FileReader fileReader = new FileReader(fileName);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String out = "";
		for (out = bufferedReader.readLine(); out != null; out = bufferedReader.readLine()) {
			String courseInfoInfo[] = out.split(" ");
			if (!courseInformation.ID.equals(courseInfoInfo[ID])) {
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