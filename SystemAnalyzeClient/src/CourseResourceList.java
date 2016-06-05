import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CourseResourceList implements Serializable{
	private static final long serialVersionUID = 1L;
	List<CourseResource> CourseResourceList;
	CourseResourceList(List<CourseResource> cl) {
		CourseResourceList = cl; 
	}
	CourseResourceList() {
		CourseResourceList = new ArrayList<CourseResource>();
	}
	public int getSize() {
		return CourseResourceList.size();
	}
}
