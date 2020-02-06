package Data;

import Data.CourseProgram.ProgramType;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * This holds all the information associated with a program. No courses or
 * specializations are included. this is just pure information used when searching the database.
 * @author Robin
 *
 */
public class ProgramInformation {
	
	protected String code;
	protected String name;
	protected String description;
	protected CourseDate startDate;
	protected Credits credits;
	protected ProgramType programType;
	
	/**
	 * 
	 * @param code
	 * @param name
	 * @param description
	 * @param startDate
	 * @param credits
	 * @param type
	 */
	public ProgramInformation( String code, String name, String description, CourseDate startDate, Credits credits, ProgramType type) {
		this.code = code;
		this.name = name;
		this.description = description;
		this.startDate = startDate;
		this.credits = credits;
		this.programType = type;
		
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

	public String getAsJson() throws JSONException {
		JSONObject obj = new JSONObject();
		obj.put(CourseProgram.ProgramLabels.NAME.toString(),name);
		obj.put(CourseProgram.ProgramLabels.CODE.toString(),code);
		obj.put(CourseProgram.ProgramLabels.DESCRIPTION.toString(),description);
		obj.put(CourseProgram.ProgramLabels.CREDITS.toString(),credits.name());
		obj.put(CourseProgram.ProgramLabels.LP.toString(),startDate.getPeriod().name());
		obj.put(CourseProgram.ProgramLabels.NAME.toString(),startDate.getYear());
		return obj.toString();
	}
}
