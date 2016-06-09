import java.io.Serializable;

public class CourseResource  implements Serializable{
	private static final long serialVersionUID = 1L;
	public int ID;
	public int CID;
	public String fileName;
	public String size;
	CourseResource(int ID, int CID, String fileName, String size) {
		this.ID = ID;
		this.CID = CID;
		this.fileName = fileName;
		this.size = size;
	}
}
