import java.io.Serializable;

public class Course implements Serializable{
	private static final long serialVersionUID = 1L;
	public int ID;
	public String name;
	Course(int ID, String name) {
		this.ID = ID;
		this.name = name;
	}
}