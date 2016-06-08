import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class groupMemberList implements Serializable{
	private static final long serialVersionUID = 1L;
	List<User> groupMemberList;
	groupMemberList(List<User> gl) {
		groupMemberList = gl; 
	}
	groupMemberList() {
		groupMemberList = new ArrayList<User>();
	}
	public int getSize() {
		return groupMemberList.size();
	}
}