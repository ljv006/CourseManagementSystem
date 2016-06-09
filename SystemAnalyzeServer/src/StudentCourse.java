import java.io.Serializable;

public class StudentCourse implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int ID;
	public int SID;
	public String Sname;
	public int CID;
	public String Cname;
	StudentCourse(int ID, int SID, String Sname, int CID, String Cname) {
		this.ID = ID;
		this.SID = SID;
		this.Sname = Sname;
		this.CID = CID;
		this.Cname = Cname;
	}
}