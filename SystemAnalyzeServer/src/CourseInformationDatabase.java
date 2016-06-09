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
public class CourseInformationDatabase {
	static String fileName = System.getProperty("user.dir") + "\\CourseInformationList.txt";
	static int ID = 0;
	static int CID = 1;
	static int time = 2;
	static int content = 3;
	public static int getID() throws NumberFormatException, IOException{
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
	public static boolean isfind(String _content) throws IOException {
		FileReader fileReader = new FileReader(fileName);
		@SuppressWarnings("resource")
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String out = "";
		for (out = bufferedReader.readLine(); out != null; out = bufferedReader.readLine()) {
			String courseInfoInfo[] = out.split(" ");
			if (_content.equals(courseInfoInfo[content])) {
				return true;
			}
		}
		bufferedReader.close();
		fileReader.close();
		return false;
	}

	public static List<CourseInformation> getAllCourseInformationList(int _CID) throws IOException {
		FileReader fileReader = new FileReader(fileName);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String out = "";
		List<CourseInformation> courseInfoList = new ArrayList<CourseInformation>();
		for (out = bufferedReader.readLine(); out != null; out = bufferedReader.readLine()) {
			String courseInfoInfo[] = out.split(" ");
			if (Integer.parseInt(courseInfoInfo[CID]) == _CID) {
				CourseInformation courseInfo = new CourseInformation(Integer.parseInt(courseInfoInfo[ID]), 
						_CID, courseInfoInfo[time], courseInfoInfo[content]);
				courseInfoList.add(courseInfo);
			}
		}
		bufferedReader.close();
		fileReader.close();
		return courseInfoList;
	}
	public static boolean insert(CourseInformation courseInformation) throws IOException {
		if (isfind(courseInformation.content)) {
			return false;
		}
		FileWriter fileWriter = new FileWriter(fileName, true);
		String input = getID() + "";
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
}