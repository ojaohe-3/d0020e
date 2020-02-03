package Data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Objects;

/**
 * 
 * 	Java representation of a course. 
 * 
 * @author Jesper
 *
 */
public class Course extends CourseInformation {
	private ArrayList<KC> requiredKC = new ArrayList<KC>();
	private ArrayList<KC> developedKC = new ArrayList<KC>();
	
	/**
	 * Database name for a course.
	 */
	public static final String course = "Course";
	
	/**
	 * Constructor
	 * 
	 * @param name
	 * @param courseCode 
	 * @param credit enum 
	 * @param description
	 * @param examiner
	 * @param startPeriod an object of type {@link CourseOrder}
	 */
	public Course(String name, String courseCode, Credits credit, String description, String examiner, CourseDate startPeriod) {
		super(name,courseCode,credit,description,examiner,startPeriod);
	}
	
	/**
	 * Create a full course from course information.
	 * @param information
	 * @see 
	 */
	public Course(CourseInformation information) {
		super(information.getName(), information.getCourseCode(), information.getCredit(), information.getDescription(), information.getExaminer(), information.getStartPeriod());
	}
	
	public void setRequiredKC(KC kc) {
		requiredKC.add(kc);
	}
	
	public void setDevelopedKC(KC kc) {
		developedKC.add(kc);
	}
	
	public void deleteRequiredKC(KC kc) {
		requiredKC.remove(kc);
	}
	
	public void deleteDevelopedKC(KC kc) {
		developedKC.remove(kc);
	}
	
	public ArrayList<KC> getRequiredKC() {
		return requiredKC;
	}
	
	public ArrayList<KC> getDevelopedKC() {
		return developedKC;
	}
	
	/**
	 * I don't wanna use strings everywhere and keep track of how 
	 * how to spell stuff, so I made this. It's basically just a few
	 * values with the names of every label in a course.
	 * @author Robin
	 *
	 */
	public static enum CourseLabels {
		NAME("name"), CREDIT("credit"), DESCRIPTION("description"), EXAMINER("examiner"), YEAR("year"), LP("lp"), CODE("courseCode");
		private String name;
		
		private CourseLabels(String name) {
			this.name = name;
		}
		
		@Override
		public String toString() {
			return this.name;
		}
	}

	public String toJson() throws JsonProcessingException {
		ObjectMapper obj = new ObjectMapper();
		String temp = obj.writeValueAsString(this);
		System.out.println(temp);
		return obj.writeValueAsString(this);
	}

}
