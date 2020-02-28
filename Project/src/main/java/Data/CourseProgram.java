package Data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CourseProgram extends ProgramInformation{
	public static String program = "";
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
	
	public CourseProgram(CourseOrder courseOrder) {
		super( null, null,null,null,0,null);
		this.courseOrder = courseOrder;
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
	public CourseProgram(CourseOrder courseOrder, String code, String name, String description, CourseDate startDate, float credits) {
		super( code, name, description, startDate, credits, ProgramType.PROGRAM);
		this.courseOrder = courseOrder;
		
	}
	
	public CourseProgram( String code, String name, String description, CourseDate startDate, float credits) {
		super( code, name, description, startDate, credits, ProgramType.PROGRAM);
		
	}
	
	protected CourseProgram(CourseOrder courseOrder, String code, String name, String description, CourseDate startDate, float credits, ProgramType type) {
		super( code, name, description, startDate, credits, type);
		this.courseOrder = courseOrder;
	}
	
	public CourseOrder getCourseOrder() {
		return courseOrder;
	}


	public void setCourseOrder(CourseOrder courseOrder) {
		this.courseOrder = courseOrder;
	}

	public JSONObject getAsJson() throws JSONException {
		JSONObject obj = new JSONObject();
		JSONArray courses = new JSONArray();
		Course[][] data = courseOrder.getCourseArray();
		for (int i = 0; i < data.length ; i++) {
			for (int j = 0; j < data[i].length; j++) {
				if(data[i][j] != null)
					courses.put(data[i][j].getJsonObject());
			}
		}
		obj.put(CourseProgram.ProgramLabels.NAME.toString(),name.replaceAll("\"",""));
		obj.put(CourseProgram.ProgramLabels.CODE.toString(),code.replaceAll("\"",""));
		obj.put(CourseProgram.ProgramLabels.DESCRIPTION.toString(),description.replaceAll("\"",""));
		obj.put(CourseProgram.ProgramLabels.CREDITS.toString(),credits);
		obj.put(CourseProgram.ProgramLabels.LP.toString(),startDate.getPeriod().name().replaceAll("\"",""));
		obj.put(CourseProgram.ProgramLabels.NAME.toString(),startDate.getYear());
		obj.put("Courses",courses);

		return obj;
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
