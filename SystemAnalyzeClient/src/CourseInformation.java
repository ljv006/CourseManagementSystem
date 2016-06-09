import java.io.Serializable;
public class CourseInformation implements Serializable{
	private static final long serialVersionUID = 1L;
	public int ID;
	public int CID;
	public String time;
	public String content;
	CourseInformation(int ID, int CID, String time, String content) {
		this.ID = ID;
		this.CID = CID;
		this.time = time;
		this.content = content;
	}
}
