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
public class CourseResourceDatabase {
	static String fileName = System.getProperty("user.dir") + "\\CourseResourceList.txt";
	static int ID = 0;
	static int CID = 1;
	static int filename = 2;
	static int size = 3;
	public static int getID() throws NumberFormatException, IOException {
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
	public static boolean isfind(String _fileName) throws IOException {
		FileReader fileReader = new FileReader(fileName);
		@SuppressWarnings("resource")
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String out = "";
		for (out = bufferedReader.readLine(); out != null; out = bufferedReader.readLine()) {
			String courseResourceInfo[] = out.split(" ");
			if (_fileName.equals(courseResourceInfo[filename])) {
				return true;
			}
		}
		bufferedReader.close();
		fileReader.close();
		return false;
	}
	public static void renew(int _CID) throws IOException {
		FileReader fileReader = new FileReader(fileName);
		@SuppressWarnings("resource")
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String out = "";
		File file=new File(System.getProperty("user.dir") + "//CourseResource");
		//如果是文件夹，声明一个数组放文件夹和他的子文件
		File[] f=file.listFiles();
		//遍历文件件下的文件，并获取路径
		for (File file2 : f) {
			boolean flag = false;
			for (out = bufferedReader.readLine(); out != null; out = bufferedReader.readLine()) {
				String courseResourceInfo[] = out.split(" ");
				if (courseResourceInfo[filename].equals(file2.getName())) {
					flag = true;
					break;
				}
			}
			
			if (!flag) {
				double size = (Math.round((file2.length()/1024.0)*100.0)/100.0);
				CourseResource courseResource = new CourseResource(getID(), 
						_CID, file2.getName(), size + "" + "KB");
				insert(courseResource);
			}
		}
		
	}
	public static List<CourseResource> getAllCourseResourceList(int _CID) throws IOException {
		FileReader fileReader = new FileReader(fileName);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String out = "";
		List<CourseResource> courseResourceList = new ArrayList<CourseResource>();
		for (out = bufferedReader.readLine(); out != null; out = bufferedReader.readLine()) {
			String courseResourceInfo[] = out.split(" ");
			if (Integer.parseInt(courseResourceInfo[CID]) == _CID) {
				CourseResource courseResource = new CourseResource(Integer.parseInt(courseResourceInfo[ID]), 
						Integer.parseInt(courseResourceInfo[CID]), courseResourceInfo[filename], courseResourceInfo[size]);
				courseResourceList.add(courseResource);
			}
		}
		bufferedReader.close();
		fileReader.close();
		return courseResourceList;
	}
	public static boolean insert(CourseResource courseResource) throws IOException {
		if (isfind(courseResource.fileName)) {
			return false;
		}
		FileWriter fileWriter = new FileWriter(fileName, true);
		String input = getID() + "";
		input += " ";
		input += courseResource.CID;
		input += " ";
		input += courseResource.fileName;
		input += " ";
		input += courseResource.size;
		input += "\r\n";
		fileWriter.write(input);
		fileWriter.close();
		return true;
	}
}