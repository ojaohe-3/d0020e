package Data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

/**
 * 
 * 	Java representation of a course. 
 * 
 * @author Jesper
 *
 */
public class Course extends CourseInformation {
	private ArrayList<KC> requiredKC = new ArrayList<KC>();
	private ArrayList<KC> developedKC = new ArrayList<KC>();
	
	/**
	 * Database name for a course.
	 */
	public static final String course = "Course";
	
	/**
	 * Constructor
	 * 
	 * @param name
	 * @param courseCode 
	 * @param credit enum 
	 * @param description
	 * @param examiner
	 * @param startPeriod an object of type {@link CourseOrder}
	 */
	public Course(String name, String courseCode, Credits credit, String description, String examiner, CourseDate startPeriod) {
		super(name,courseCode,credit,description,examiner,startPeriod);
	}
	
	/**
	 * Create a full course from course information.
	 * @param information
	 * @see 
	 */
	public Course(CourseInformation information) {
		super(information.getName(), information.getCourseCode(), information.getCredit(), information.getDescription(), information.getExaminer(), information.getStartPeriod());
	}
	
	public void setRequiredKC(KC kc) {
		requiredKC.add(kc);
	}
	
	public void setDevelopedKC(KC kc) {
		developedKC.add(kc);
	}
	
	public void deleteRequiredKC(KC kc) {
		requiredKC.remove(kc);
	}
	
	public void deleteDevelopedKC(KC kc) {
		developedKC.remove(kc);
	}
	
	public ArrayList<KC> getRequiredKC() {
		return requiredKC;
	}
	
	public ArrayList<KC> getDevelopedKC() {
		return developedKC;
	}

	/**
	 * Creates Json but does not return as string but object to help nested types
	 * @return
	 * @throws JSONException
	 */
	public JSONObject getJsonObject() throws JSONException {
		JSONObject obj = new JSONObject();
		obj.put(Course.CourseLabels.CODE.toString(),courseCode.replaceAll("\"",""));
		obj.put(Course.CourseLabels.NAME.toString(),name.replaceAll("\"",""));
		obj.put(Course.CourseLabels.DESCRIPTION.toString(),description.replaceAll("\"",""));
		obj.put(Course.CourseLabels.EXAMINER.toString(),examiner.replaceAll("\"",""));
		obj.put(Course.CourseLabels.CREDIT.toString(),credit.name().replaceAll("\"",""));
		obj.put(Course.CourseLabels.LP.toString(),startPeriod.getPeriod().name().replaceAll("\"",""));
		obj.put(Course.CourseLabels.YEAR.toString(), startPeriod.getYear());
		obj.put("Required",requiredKC);
		obj.put("Developed",developedKC);
		return obj;
	}

	/**
	 * I don't wanna use strings everywhere and keep track of how 
	 * how to spell stuff, so I made this. It's basically just a few
	 * values with the names of every label in a course.
	 * @author Robin
	 *
	 */
	public static enum CourseLabels {
		NAME("name"), CREDIT("credit"), DESCRIPTION("description"), EXAMINER("examiner"), YEAR("year"), LP("lp"), CODE("courseCode");
		private String name;
		
		private CourseLabels(String name) {
			this.name = name;
		}
		
		@Override
		public String toString() {
			return this.name;
		}
	}
	@Override
	public JSONObject getAsJson() throws JSONException {
		JSONObject obj = new JSONObject();
		obj.put(Course.CourseLabels.CODE.toString(),courseCode);
		obj.put(Course.CourseLabels.NAME.toString(),name);
		obj.put(Course.CourseLabels.DESCRIPTION.toString(),description);
		obj.put(Course.CourseLabels.EXAMINER.toString(),examiner);
		obj.put(Course.CourseLabels.CREDIT.toString(),credit);
		obj.put(Course.CourseLabels.LP.toString(),startPeriod.getPeriod());
		obj.put(Course.CourseLabels.YEAR.toString(), startPeriod.getYear());
		obj.put("Required",requiredKC);
		obj.put("Developed",developedKC);

		return obj;

	}

	@Override
	public String toString() {
		try {
			return getAsJson().toString();
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
}
