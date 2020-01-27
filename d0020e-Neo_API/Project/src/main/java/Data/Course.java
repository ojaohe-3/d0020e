package Data;

import java.util.ArrayList;

public class Course {
	private String name;
	private String courseCode;
	private String description;
	private String examiner;
	private CourseDate startPeriod;
	private Credits credit;
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
		this.name = name;
		this.courseCode = courseCode;
		this.credit = credit;
		this.description = description;
		this.examiner = examiner;
		this.startPeriod = startPeriod;
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
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCourseCode() {
		return courseCode;
	}

	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getExaminer() {
		return examiner;
	}

	public void setExaminer(String examiner) {
		this.examiner = examiner;
	}

	public CourseDate getStartPeriod() {
		return startPeriod;
	}

	public void setStartPeriod(CourseDate startPeriod) {
		this.startPeriod = startPeriod;
	}

	public Credits getCredit() {
		return credit;
	}

	public void setCredit(Credits credit) {
		this.credit = credit;
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

}
