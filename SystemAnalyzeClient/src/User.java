import java.io.Serializable;

public class User implements Serializable{
	private static final long serialVersionUID = 1L;
	public int ID;
	public String name;
	public String password;
	public String identity;
	User(int ID, String name, String password, String identity) {
		this.ID = ID;
		this.name = name;
		this.password = password;
		this.identity = identity;
	}
}