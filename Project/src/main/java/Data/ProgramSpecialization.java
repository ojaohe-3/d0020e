package Data;

public class ProgramSpecialization {

	private CourseProgram courseProgram;
	private String name;
	private String description;
	private CourseOrder courseOrder;
	private CourseDate startDate;
	private Credits credits;
	
	public static final String programSpecialization = "programSpecialization";
	
	public ProgramSpecialization(CourseProgram courseProgram) {
		this.courseProgram = courseProgram;
	}
	
	public ProgramSpecialization(CourseProgram courseProgram, String name, String description, CourseOrder courseOrder, CourseDate startDate, Credits credits) {
		this.courseProgram = courseProgram;
		this.name = name;
		this.description = description;
		this.courseOrder = courseOrder;
		this.startDate = startDate;
		this.credits = credits;
	}
	
	public CourseProgram getCourseProgram() {
		return courseProgram;
	}

	public void setCourseProgram(CourseProgram courseProgram) {
		this.courseProgram = courseProgram;
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
	
	public static enum ProgramLabels {
		NAME("name"), DESCRIPTION("description"), YEAR("year"), LP("lp"), CREDITS("credits"), COURSEPROGRAM("courseProgram");
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
