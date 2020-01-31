package Data;

import java.util.ArrayList;
import java.util.Objects;

/**
 * 
 * 	Java representation of a course. 
 * 
 * @author Jesper
 *
 */
public class Course {
	private CourseInformation courseInfo;
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
		this.courseInfo = new CourseInformation(name,courseCode,credit,description,examiner,startPeriod);
	}
	
	/**
	 * Create a full course from course information.
	 * @param information
	 * @see 
	 */
	public Course(CourseInformation information) {
		this.courseInfo = information;
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
		return this.courseInfo.getName();
	}

	public void setName(String name) {
		this.courseInfo.setName(name);
	}

	public String getCourseCode() {
		return this.courseInfo.getCourseCode();
	}

	public void setCourseCode(String courseCode) {
		this.courseInfo.setCourseCode(courseCode);
	}

	public String getDescription() {
		return this.courseInfo.getDescription();
	}

	public void setDescription(String description) {
		this.courseInfo.setDescription(description);
	}

	public String getExaminer() {
		return this.courseInfo.getExaminer();
	}

	public void setExaminer(String examiner) {
		this.courseInfo.setExaminer(examiner);
	}

	public CourseDate getStartPeriod() {
		return this.courseInfo.getStartPeriod();
	}

	public void setStartPeriod(CourseDate startPeriod) {
		this.courseInfo.setStartPeriod(startPeriod);
	}

	public Credits getCredit() {
		return this.courseInfo.getCredit();
	}

	public void setCredit(Credits credit) {
		this.courseInfo.setCredit(credit);
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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Course course = (Course) o;
		return name.equals(course.name) &&
				courseCode.equals(course.courseCode) &&
				description.equals(course.description) &&
				examiner.equals(course.examiner) &&
				startPeriod.equals(course.startPeriod) &&
				credit == course.credit;
	}

}
