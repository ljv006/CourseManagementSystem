import java.io.Serializable;
public class CourseInformation implements Serializable{
	private static final long serialVersionUID = 1L;
	public String ID;
	public String CID;
	public String time;
	public String content;
	CourseInformation(String ID, String CID, String time, String content) {
		this.ID = ID;
		this.CID = CID;
		this.time = time;
		this.content = content;
	}
}
