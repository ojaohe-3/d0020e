package Data;

public class CourseProgram {

	private CourseOrder courseOrder;
	private String code;
	private String name;
	private String description;
	private CourseDate startDate;
	private Credits credits;
	
	public static final String courseProgram = "courseProgram";
	
	
	public CourseProgram(CourseOrder courseOrder) {
		this.courseOrder = courseOrder;
	}
	
	public CourseProgram(CourseOrder courseOrder, String code, String name, String description, CourseDate startDate, Credits credits) {
		this.courseOrder = courseOrder;
		this.code = code;
		this.name = name;
		this.description = description;
		this.startDate = startDate;
		this.credits = credits;
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


	public Credits getCredits() {
		return credits;
	}


	public void setCredits(Credits credits) {
		this.credits = credits;
	}

	public CourseDate getStartDate() {
		return startDate;
	}

	public void setStartDate(CourseDate startDate) {
		this.startDate = startDate;
	}


	public static enum ProgramLabels {
		NAME("name"), CODE("code"), DESCRIPTION("description"), YEAR("year"), LP("lp"), READING_PERIODS("readingPeriods"), CREDITS("credits");
		private String label;
		private ProgramLabels (String label) {
			this.label = label;
		}
	}
	

}
