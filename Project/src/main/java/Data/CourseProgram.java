package Data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Representation of a program with course
 * 
 * @author JSPr
 */
public class CourseProgram extends ProgramInformation{
	
	public static String program = "";
	private ArrayList<Course> courseOrder;
	
	/*
	 * Enum to know if it is a program or a specialization to a program
	 */
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

	public CourseProgram() {
		super( null, null,null,null,0,null);
		this.courseOrder = new ArrayList<>();
	}
	
	public CourseProgram(Collection<Course> courses) {
		super(null, null, null, null, 0, null);
		this.courseOrder = (ArrayList<Course>) courses;
	}
	
	/**
	 * Constructor for program with given course order
	 * 
	 * @param courseOrder - Arraylist with courses
	 * @param code
	 * @param name
	 * @param description
	 * @param startDate
	 * @param credits
	 */
	public CourseProgram(ArrayList<Course> courseOrder, String code, String name, String description, CourseDate startDate, float credits) {
		super(code, name, description, startDate, credits, ProgramType.PROGRAM);
		this.courseOrder = courseOrder;
		
	}
	
	/**
	 * Constructor for program without course order
	 * 
	 * @param code
	 * @param name
	 * @param description
	 * @param startDate
	 * @param credits
	 */
	public CourseProgram(String code, String name, String description, CourseDate startDate, float credits) {

		super(code, name, description, startDate, credits, ProgramType.PROGRAM);
		this.courseOrder = new ArrayList<>();
		
	}

	/**
	 * Constructor for program with given course order
	 * 
	 * @param courseOrder - Arraylist with courses
	 * @param code
	 * @param name
	 * @param description
	 * @param startDate
	 * @param credits
	 */
	public CourseProgram(ArrayList<Course> courseOrder, String code, String name, String description, CourseDate startDate, float credits, ProgramType type) {
		super( code, name, description, startDate, credits, type);
		this.courseOrder = (ArrayList<Course>) courseOrder;
	}
	
	public ArrayList<Course> getCourseOrder() {
		return courseOrder;
	}


	public void setCourseOrder(ArrayList<Course> courseOrder) {
		this.courseOrder =  courseOrder;
	}

	/**
	 * Get program as JSONObject
	 */
	public JSONObject getAsJson() throws JSONException {
		JSONObject obj = new JSONObject();

		obj.put(CourseProgram.ProgramLabels.NAME.toString(),name.replaceAll("\"",""));
		obj.put(CourseProgram.ProgramLabels.CODE.toString(),code.replaceAll("\"",""));
		obj.put(CourseProgram.ProgramLabels.DESCRIPTION.toString(),description.replaceAll("\"",""));
		obj.put(CourseProgram.ProgramLabels.CREDITS.toString(),credits);
		obj.put(CourseProgram.ProgramLabels.LP.toString(),startDate.getPeriod().name().replaceAll("\"",""));
		obj.put(ProgramLabels.YEAR.toString(),startDate.getYear());
		obj.put("Courses",this.courseOrder);

		return obj;
	}
	
	/**
	 * Enums for parameters to be used in neo API
	 */
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
