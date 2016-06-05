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
public class chatRoomDatabase {
	static String fileName = System.getProperty("user.dir") + "\\src\\chatRoomRecord.txt";
	public static int count = 0;
	static int ID= 0;
	static int CID = 1;
	static int content = 2;
	static int time = 3;
	static int speaker = 4;
	public static int getID() {
		return count++;
	}
	public static boolean isfind(Chat c) throws IOException {
		FileReader fileReader = new FileReader(fileName);
		@SuppressWarnings("resource")
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String out = "";
		for (out = bufferedReader.readLine(); out != null; out = bufferedReader.readLine()) {
			String chatInfo[] = out.split(" ");
			if (c.content.equals(chatInfo[content])) {
				return true;
			}
		}
		bufferedReader.close();
		fileReader.close();
		return false;
	}
	public static List<Chat> getAllChatRecordList(String _CID) throws IOException {
		FileReader fileReader = new FileReader(fileName);
		@SuppressWarnings("resource")
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String out = "";
		List<Chat> chatRecordList = new ArrayList<Chat>();
		for (out = bufferedReader.readLine(); out != null; out = bufferedReader.readLine()) {
			String chatInfo[] = out.split(" ");
			if (chatInfo[CID].equals(_CID)) {
				Chat c = new Chat(chatInfo[ID], 
						chatInfo[CID], chatInfo[content], chatInfo[time], chatInfo[speaker]);
				chatRecordList.add(c);
			}
		}
		bufferedReader.close();
		fileReader.close();
		return chatRecordList;
	}
	public static boolean insert(Chat c) throws IOException {
		if (isfind(c)) {
			return false;
		}
		FileWriter fileWriter = new FileWriter(fileName, true);
		String input = getID() + "";
		input += " ";
		input += c.CID;
		input += " ";
		input += c.content;
		input += " ";
		input += c.time;
		input += " ";
		input += c.speaker;
		input += "\r\n";
		fileWriter.write(input);
		fileWriter.close();
		return true;
	}
	public static  boolean delete(Chat c) throws IOException{
		if (!isfind(c)) {
			return false;
		}
		FileWriter fileWriter = new FileWriter("temp.txt");
		FileReader fileReader = new FileReader(fileName);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String out = "";
		for (out = bufferedReader.readLine(); out != null; out = bufferedReader.readLine()) {
			String courseResourceInfo[] = out.split(" ");
			if (!c.content.equals(courseResourceInfo[content])) {
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
