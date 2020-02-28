package Data;


import org.json.JSONException;
import org.json.JSONObject;

/**
 * The course information is merely a data structure used for
 * storing many search results from the database. Do not use this as a
 * complete representation of a course.
 * @author Robin
 *
 */
public class CourseInformation {
	protected String name;
	protected String courseCode;
	protected String description;
	protected String examiner;
	protected CourseDate startPeriod;
	protected float credit;


	/**
	 * 
	 * @param name - the name of the course
	 * @param courseCode - The course code
	 * @param credit - The number of credits
	 * @param description - The description of the course
	 * @param examiner - The examiner
	 * @param startPeriod - The staring period in the form of {@link CourseDate}
	 */
public CourseInformation(String name, String courseCode, float credit, String description, String examiner, CourseDate startPeriod) {
		this.name = name.replaceAll("\"","");
		this.courseCode = courseCode.replaceAll("\"","");
		this.credit = credit;
		this.description = description.replaceAll("\"","");
		this.examiner = examiner.replaceAll("\"","");
		this.startPeriod = startPeriod;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name.replaceAll("\"","");
	}

	public String getCourseCode() {
		return courseCode;
	}

	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode.replaceAll("\"","");
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description.replaceAll("\"","");
	}

	public String getExaminer() {
		return examiner;
	}

	public void setExaminer(String examiner) {
		this.examiner = examiner.replaceAll("\"","");
	}

	public CourseDate getStartPeriod() {
		return startPeriod;
	}

	public void setStartPeriod(CourseDate startPeriod) {
		this.startPeriod = startPeriod;
	}

	public float getCredit() {
		return credit;
	}

	public void setCredit(float credit) {
		this.credit = credit;
	}


	public JSONObject getAsJson() throws JSONException {
		JSONObject obj = new JSONObject();

		try {
			obj.put(Course.CourseLabels.CODE.toString(),courseCode);
			obj.put(Course.CourseLabels.NAME.toString(),name);
			obj.put(Course.CourseLabels.DESCRIPTION.toString(),description);
			obj.put(Course.CourseLabels.EXAMINER.toString(),examiner);
			obj.put(Course.CourseLabels.CREDIT.toString(),credit);
			obj.put(Course.CourseLabels.LP.toString(),startPeriod.getPeriod());
			obj.put(Course.CourseLabels.YEAR.toString(), startPeriod.getYear());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return obj;

	}
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		CourseInformation that = (CourseInformation) o;
		return name.equals(that.name) &&
				courseCode.equals(that.courseCode) &&
				description.equals(that.description) &&
				examiner.equals(that.examiner) &&
				startPeriod.equals(that.startPeriod) &&
				credit == that.credit;
	}
}
