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
public class chatRoomDatabase {
	static String fileName = System.getProperty("user.dir") + "\\src\\chatRoomRecord.txt";
	public static int count = 0;
	static int ID= 0;
	static int CID = 1;
	static int content = 2;
	static int time = 3;
	static int speaker = 4;
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
	public static boolean isfind(String _content) throws IOException {
		FileReader fileReader = new FileReader(fileName);
		@SuppressWarnings("resource")
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String out = "";
		for (out = bufferedReader.readLine(); out != null; out = bufferedReader.readLine()) {
			String chatInfo[] = out.split("  ");
			if (_content.equals(chatInfo[content])) {
				return true;
			}
		}
		bufferedReader.close();
		fileReader.close();
		return false;
	}
	public static List<Chat> getChatRecordList(int _CID) throws IOException {
		FileReader fileReader = new FileReader(fileName);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String out = "";
		List<Chat> chatRecordList = new ArrayList<Chat>();
		for (out = bufferedReader.readLine(); out != null; out = bufferedReader.readLine()) {
			String chatInfo[] = out.split("  ");
			if (Integer.parseInt(chatInfo[CID]) == _CID) {
				Chat c = new Chat(Integer.parseInt(chatInfo[ID]), 
						_CID, chatInfo[content], chatInfo[time], chatInfo[speaker]);
				chatRecordList.add(c);
			}
		}
		bufferedReader.close();
		fileReader.close();
		return chatRecordList;
	}
	public static boolean insert(Chat c) throws IOException {
		if (isfind(c.content)) {
			return false;
		}
		FileWriter fileWriter = new FileWriter(fileName, true);
		String input = getID() + "";
		input += "  ";
		input += c.CID;
		input += "  ";
		input += c.content;
		input += "  ";
		input += c.time;
		input += "  ";
		input += c.speaker;
		input += "\r\n";
		fileWriter.write(input);
		fileWriter.close();
		return true;
	}
}
