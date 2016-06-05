import java.io.Serializable;

public class CourseResource  implements Serializable{
	private static final long serialVersionUID = 1L;
	public String ID;
	public String CID;
	public String fileName;
	public String size;
	CourseResource(String ID, String CID, String fileName, String size) {
		this.ID = ID;
		this.CID = CID;
		this.fileName = fileName;
		this.size = size;
	}
}
