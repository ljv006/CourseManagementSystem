import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CourseList implements Serializable{
	private static final long serialVersionUID = 1L;
	List<Course> CourseList;
	CourseList(List<Course> cl) {
		CourseList = cl; 
	}
	CourseList() {
		CourseList = new ArrayList<Course>();
	}
	public int getSize() {
		return CourseList.size();
	}
}