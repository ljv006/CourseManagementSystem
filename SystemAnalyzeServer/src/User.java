import java.io.Serializable;

public class User implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String ID;
	public String name;
	public String password;
	public String identity;
	public String type;
	User(String ID, String name, String password, String identity) {
		this.ID = ID;
		this.name = name;
		this.identity = identity;
	}
}