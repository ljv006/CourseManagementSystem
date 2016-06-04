import java.io.Serializable;

public class Course implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String ID;
	public String name;
	Course(String ID, String name) {
		this.ID = ID;
		this.name = name;
	}
}