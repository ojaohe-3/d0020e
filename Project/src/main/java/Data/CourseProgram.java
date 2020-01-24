package Data;

public class CourseProgram {

	private CourseOrder courseOrder;
	private String code;
	private String name;
	private String description;
	private int year;
	private Credits credits;
	
	
	public CourseProgram(CourseOrder courseOrder) {
		this.courseOrder = courseOrder;
	}
	
	public CourseOrder getCourseOrder() {
		return courseOrder;
	}


	public void setCourseOrder(CourseOrder courseOrder) {
		this.courseOrder = courseOrder;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}
	
	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public int getYear() {
		return year;
	}


	public void setYear(int year) {
		this.year = year;
	}


	public Credits getCredits() {
		return credits;
	}


	public void setCredits(Credits credits) {
		this.credits = credits;
	}



	

}
