import java.io.Serializable;

public class StudentCourse implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String SID;
	public String Sname;
	public String CID;
	public String Cname;
	StudentCourse(String SID, String Sname, String CID, String Cname) {
		this.SID = SID;
		this.Sname = Sname;
		this.CID = CID;
		this.Cname = Cname;
	}
}