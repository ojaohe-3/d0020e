package Data;

public class ProgramSpecialization {

	private String courseCode;
	private CourseDate courseStartDate;
	private String name;
	private String description;
	private CourseOrder courseOrder;
	private CourseDate startDate;
	private Credits credits;
	
	public static final String programSpecialization = "programSpecialization";
	
	public ProgramSpecialization(String courseCode, CourseDate courseStartDate) {
		this.courseCode = courseCode;
		this.courseStartDate = courseStartDate;
	}
	
	public ProgramSpecialization(String courseCode, CourseDate courseStartDate, String name, String description, CourseOrder courseOrder, CourseDate startDate, Credits credits) {
		this.courseCode = courseCode;
		this.courseStartDate = courseStartDate;
		this.name = name;
		this.description = description;
		this.courseOrder = courseOrder;
		this.startDate = startDate;
		this.credits = credits;
	}
	
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public CourseOrder getCourseOrder() {
		return courseOrder;
	}

	public void setCourseOrder(CourseOrder courseOrder) {
		this.courseOrder = courseOrder;
	}

	public CourseDate getStartDate() {
		return startDate;
	}

	public void setStartDate(CourseDate startDate) {
		this.startDate = startDate;
	}

	public Credits getCredits() {
		return credits;
	}

	public void setCredits(Credits credits) {
		this.credits = credits;
	}
	
	public String getCourseCode() {
		return courseCode;
	}

	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}

	public void setCourseStartDate(CourseDate courseStartDate) {
		this.courseStartDate = courseStartDate;
	}
	
	public CourseDate getCourseStartDate() {
		return courseStartDate;
	}

	
	public static enum ProgramLabels {
		NAME("name"), DESCRIPTION("description"), YEAR("year"), LP("lp"), CREDITS("credits"), COURSECODE("courseCode"), COURSEYEAR("courseStartYear"), COURSELP("courseStartLp");
		private String label;
		private ProgramLabels (String label) {
			this.label = label;
		}
		
		@Override
		public String toString() {
			return this.label;
		}
	}
}
