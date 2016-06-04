import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CourseInformationList implements Serializable{
	private static final long serialVersionUID = 1L;
	List<CourseInformation> CourseInfoList;
	CourseInformationList(List<CourseInformation> cl) {
		CourseInfoList = cl; 
	}
	CourseInformationList() {
		CourseInfoList = new ArrayList<CourseInformation>();
	}
	public int getSize() {
		return CourseInfoList.size();
	}
}