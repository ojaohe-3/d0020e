package Data;

import com.fasterxml.jackson.core.JsonFactory;

/**
 * 
 * @author Robin
 *
 */
public class CourseInformation extends JsonFactory {
	private String name;
	private String courseCode;
	private String description;
	private String examiner;
	private CourseDate startPeriod;
	private Credits credit;


	/**
	 * 
	 * @param name - the name of the course
	 * @param courseCode - The course code
	 * @param credit - The number of credits
	 * @param description - The description of the course
	 * @param examiner - The examiner
	 * @param startPeriod - The staring period in the form of {@link CourseDate}
	 */
	public CourseInformation(String name, String courseCode, Credits credit, String description, String examiner, CourseDate startPeriod) {
		this.name = name;
		this.courseCode = courseCode;
		this.credit = credit;
		this.description = description;
		this.examiner = examiner;
		this.startPeriod = startPeriod;
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



	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		CourseInformation that = (CourseInformation) o;
		return name.equals(that.name) &&
				courseCode.equals(that.courseCode) &&
				description.equals(that.description) &&
				examiner.equals(that.examiner) &&
				startPeriod.equals(that.startPeriod) &&
				credit == that.credit;
	}
}
