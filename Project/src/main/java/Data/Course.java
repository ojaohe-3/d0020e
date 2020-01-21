package Data;

import java.util.ArrayList;

public class Course {
	private String[] name;
	private String[] courseCode;
	private String[] description;
	private String[] examiner;
	private CourseDate startPeriod;
	private Credits credit;
	private ArrayList<KC> requiredKC = new ArrayList<KC>();
	private ArrayList<KC> developedKC = new ArrayList<KC>();
	
	/**
	 * Constructor
	 * 
	 * @param name
	 * @param courseCode 
	 * @param credit enum 
	 * @param description
	 * @param examiner
	 * @param startPeriod type CourseDate to get same format
	 */
	public Course(String[] name, String[] courseCode, Credits credit, String[] description, String[] examiner, CourseDate startPeriod) {
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
	
	public String[] getName() {
		return name;
	}

	public void setName(String[] name) {
		this.name = name;
	}

	public String[] getCourseCode() {
		return courseCode;
	}

	public void setCourseCode(String[] courseCode) {
		this.courseCode = courseCode;
	}

	public String[] getDescription() {
		return description;
	}

	public void setDescription(String[] description) {
		this.description = description;
	}

	public String[] getExaminer() {
		return examiner;
	}

	public void setExaminer(String[] examiner) {
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

}
