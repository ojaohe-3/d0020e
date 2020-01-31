package Data;

public class CourseProgram {

	private CourseOrder courseOrder;
	private String code;
	private String name;
	private String description;
	private CourseDate startDate;
	private Credits credits;
	private ProgramType programType;

	enum ProgramType {
		PROGRAM("program"),
		SPECIALIZATION("specialization");
		
		private String programType;
		private ProgramType(String programType) {
			this.programType = programType;
		}
	}
	
	public static final String courseProgram = "CourseProgram";
	
	
	public CourseProgram(CourseOrder courseOrder, ProgramType programType) {
		this.courseOrder = courseOrder;
		this.programType = programType;
	}
	
	/**
	 * 
	 * @param courseOrder
	 * @param code
	 * @param name
	 * @param description
	 * @param startDate
	 * @param credits
	 */
	public CourseProgram(CourseOrder courseOrder, String code, String name, String description, CourseDate startDate, Credits credits, ProgramType programType) {
		this.courseOrder = courseOrder;
		this.code = code;
		this.name = name;
		this.description = description;
		this.startDate = startDate;
		this.credits = credits;
		this.programType = programType;
		
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
	
	public ProgramType getProgramType() {
		return programType;
	}

	public void setProgramType(ProgramType programType) {
		this.programType = programType;
	}


	public static enum ProgramLabels {
		NAME("name"), CODE("code"), DESCRIPTION("description"), YEAR("year"), LP("lp"), READING_PERIODS("readingPeriods"), CREDITS("credits");
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
