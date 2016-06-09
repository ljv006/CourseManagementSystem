import java.io.*;
/*
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
*/
public class UserDatabase {
	static String fileName = System.getProperty("user.dir") + "\\src\\Student.txt";
	static int ID = 0;
	static int name =1;
	static int password = 2;
	static int identity = 3;
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
	public static boolean isfind(String _name) throws IOException {
		FileReader fileReader = new FileReader(fileName);
		@SuppressWarnings("resource")
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String out = "";
		for (out = bufferedReader.readLine(); out != null; out = bufferedReader.readLine()) {
			String studentInfo[] = out.split(" ");
			if (_name.equals(studentInfo[name])) {
				return true;
			}
		}
		bufferedReader.close();
		fileReader.close();
		return false;
	}
	public static User getUser(int SID) throws IOException {
		FileReader fileReader = new FileReader(fileName);
		@SuppressWarnings("resource")
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String out = "";
		for (out = bufferedReader.readLine(); out != null; out = bufferedReader.readLine()) {
			String studentInfo[] = out.split(" ");
			if (SID == Integer.parseInt(studentInfo[ID])) {
				User usr = new User(SID, studentInfo[name], studentInfo[password], studentInfo[identity]);
				return usr;
			}
		}
		bufferedReader.close();
		fileReader.close();
		return null;
	}
	public static boolean check(User user) throws NumberFormatException, IOException {
		FileReader fileReader = new FileReader(fileName);
		@SuppressWarnings("resource")
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String out = "";
		for (out = bufferedReader.readLine(); out != null; out = bufferedReader.readLine()) {
			String studentInfo[] = out.split(" ");
			if (user.ID == Integer.parseInt(studentInfo[ID]) && user.password.equals(studentInfo[password])) {
				return true;
			}
		}
		bufferedReader.close();
		fileReader.close();
		return false;
	}
	public static User getUser(String _Sname) throws NumberFormatException, IOException {
		FileReader fileReader = new FileReader(fileName);
		@SuppressWarnings("resource")
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String out = "";
		for (out = bufferedReader.readLine(); out != null; out = bufferedReader.readLine()) {
			String studentInfo[] = out.split(" ");
			if (_Sname.equals(studentInfo[name])) {
				User usr = new User(Integer.parseInt(studentInfo[ID]), studentInfo[name], studentInfo[password], studentInfo[identity]);
				return usr;
			}
		}
		bufferedReader.close();
		fileReader.close();
		return null;
	}
	public static boolean insert(User user) throws IOException {
		if (isfind(user.name)) {
			return false;
		}
		FileWriter fileWriter = new FileWriter(fileName, true);
		String input = getID() + "";
		input += " ";
		input += user.name;
		input += " ";
		input += user.password;
		input += " ";
		input += user.identity;
		input += "\r\n";
		fileWriter.write(input);
		fileWriter.close();
		return true;
	}

}