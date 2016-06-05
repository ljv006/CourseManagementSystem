import java.io.Serializable;

public class Chat implements Serializable{
	private static final long serialVersionUID = 1L;
	public String ID;
	public String CID;
	public String content;
	public String time;
	public String speaker;
	Chat(String ID, String CID, String content, String time, String speaker) {
		this.ID = ID;
		this.CID = CID;
		this.content = content;
		this.time = time;
		this.speaker = speaker;
	}
}
