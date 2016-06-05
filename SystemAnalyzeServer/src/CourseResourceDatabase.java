import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
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
	static String fileName = System.getProperty("user.dir") + "\\src\\CourseResourceList.txt";
	static int count = 0;
	static int ID = 0;
	static int CID = 1;
	static int filename = 2;
	static int size = 3;
	public static int getID() {
		return count++;
	}
	public static boolean isfind(CourseResource courseResource) throws IOException {
		FileReader fileReader = new FileReader(fileName);
		@SuppressWarnings("resource")
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String out = "";
		for (out = bufferedReader.readLine(); out != null; out = bufferedReader.readLine()) {
			String courseResourceInfo[] = out.split(" ");
			if (courseResource.fileName.equals(courseResourceInfo[filename])) {
				return true;
			}
		}
		bufferedReader.close();
		fileReader.close();
		return false;
	}
	public static void renew(String courseName) throws IOException {
		FileReader fileReader = new FileReader(fileName);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String out = "";
		@SuppressWarnings("resource")
		File file=new File("D:/CourseResource");
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
				CourseResource courseResource = new CourseResource(getID() + "", 
						CourseDatabase.getCID(courseName), file2.getName(), size + "" + "KB");
				insert(courseResource);
			}
		}
		
	}
	public static List<CourseResource> getAllCourseResourceList(String _CID) throws IOException {
		FileReader fileReader = new FileReader(fileName);
		@SuppressWarnings("resource")
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String out = "";
		List<CourseResource> courseResourceList = new ArrayList<CourseResource>();
		for (out = bufferedReader.readLine(); out != null; out = bufferedReader.readLine()) {
			String courseResourceInfo[] = out.split(" ");
			if (courseResourceInfo[CID].equals(_CID)) {
				CourseResource courseResource = new CourseResource(courseResourceInfo[ID], 
						courseResourceInfo[CID], courseResourceInfo[filename], courseResourceInfo[size]);
				courseResourceList.add(courseResource);
			}
		}
		bufferedReader.close();
		fileReader.close();
		return courseResourceList;
	}
	public static boolean insert(CourseResource courseResource) throws IOException {
		if (isfind(courseResource)) {
			return false;
		}
		FileWriter fileWriter = new FileWriter(fileName, true);
		String input = courseResource.ID;
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
	public static  boolean delete(CourseResource courseResource) throws IOException{
		if (!isfind(courseResource)) {
			return false;
		}
		FileWriter fileWriter = new FileWriter("temp.txt");
		FileReader fileReader = new FileReader(fileName);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String out = "";
		for (out = bufferedReader.readLine(); out != null; out = bufferedReader.readLine()) {
			String courseResourceInfo[] = out.split(" ");
			if (!courseResource.fileName.equals(courseResourceInfo[filename])) {
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