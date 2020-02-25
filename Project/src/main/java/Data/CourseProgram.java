package Data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;

public class CourseProgram extends ProgramInformation{
	public static String program = "";
	//private CourseOrder courseOrder;
	private ArrayList<Course> courses;
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
		super( null, null,null,null,null,null);
		this.courses = new ArrayList<>();
	}
	public CourseProgram(Collection<Course> courses) {
		super( null, null,null,null,null,null);
		this.courses = (ArrayList<Course>)courses;
	}
	
	/**
	 *
	 * @param code
	 * @param name
	 * @param description
	 * @param startDate
	 * @param credits
	 */
	public CourseProgram(String code, String name, String description, CourseDate startDate, Credits credits) {
		super( code, name, description, startDate, credits, ProgramType.PROGRAM);
		this.courses = new ArrayList<>();
		
	}

	
	public CourseProgram(ArrayList<Course> courseOrder, String code, String name, String description, CourseDate startDate, Credits credits, ProgramType type) {
		super( code, name, description, startDate, credits, type);
		this.courses = (ArrayList<Course>) courseOrder;
	}
	
	public ArrayList<Course> getCourseOrder() {
		return courses;
	}


	public void setCourseOrder(ArrayList<Course> courseOrder) {
		this.courses = (ArrayList<Course>) courseOrder;
	}

	public JSONObject getAsJson() throws JSONException {
		JSONObject obj = new JSONObject();

		obj.put(CourseProgram.ProgramLabels.NAME.toString(),name.replaceAll("\"",""));
		obj.put(CourseProgram.ProgramLabels.CODE.toString(),code.replaceAll("\"",""));
		obj.put(CourseProgram.ProgramLabels.DESCRIPTION.toString(),description.replaceAll("\"",""));
		obj.put(CourseProgram.ProgramLabels.CREDITS.toString(),credits.name().replaceAll("\"",""));
		obj.put(CourseProgram.ProgramLabels.LP.toString(),startDate.getPeriod().name().replaceAll("\"",""));
		obj.put(CourseProgram.ProgramLabels.NAME.toString(),startDate.getYear());
		obj.put("Courses",this.courses);

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
