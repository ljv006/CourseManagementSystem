import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ChatList implements Serializable{
	private static final long serialVersionUID = 1L;
	List<Chat> ChatList;
	ChatList(List<Chat> cl) {
		ChatList = cl; 
	}
	ChatList() {
		ChatList = new ArrayList<Chat>();
	}
	public int getSize() {
		return ChatList.size();
	}
}