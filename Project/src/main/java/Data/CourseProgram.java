package Data;

public class CourseProgram extends ProgramInformation{
	private CourseOrder courseOrder;

	public static enum ProgramType {
		PROGRAM("CourseProgram"),
		SPECIALIZATION("ProgramSpecialization");
		
		private String programType;
		private ProgramType(String programType) {
			this.programType = programType;
		}
		
		@Override
		public String toString() {
			return this.programType;
		}
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
	public CourseProgram(CourseOrder courseOrder, String code, String name, String description, CourseDate startDate, Credits credits) {
		super( code, name, description, startDate, credits, ProgramType.PROGRAM);
		this.courseOrder = courseOrder;
		
	}
	
	public CourseProgram( String code, String name, String description, CourseDate startDate, Credits credits) {
		super( code, name, description, startDate, credits, ProgramType.PROGRAM);
		
	}
	
	protected CourseProgram(CourseOrder courseOrder, String code, String name, String description, CourseDate startDate, Credits credits, ProgramType type) {
		super( code, name, description, startDate, credits, type);
		this.courseOrder = courseOrder;
	}
	
	public CourseOrder getCourseOrder() {
		return courseOrder;
	}


	public void setCourseOrder(CourseOrder courseOrder) {
		this.courseOrder = courseOrder;
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
