package neo4j_JVM_API;

import Data.Course;
import Data.CourseDate;
import Data.Credits;
import Data.LP;

/**
 * Simple class for generating a query on-the-go.
 * Dunno if we need this or not, but I'm too lazy to write specific query code.
 * @author Rogin
 *
 */
@Deprecated
class QueryTranslator {

	@Deprecated
	static String getCreateStatement(String variable, String label, String[] options, String[] values) {
		if (options.length != values.length) {
			throw new IllegalArgumentException("The options and values have different length");
		}
		String query =  "CREATE(" + variable +":" + label + "{";
		for (int i = 0; i < options.length-1; i++) {
			query = query.concat(options[i] + ": \"" + values[i] + "\",");
		}
		query = query.concat(options[options.length-1] + ": \"" + values[options.length-1] + "\" })");
		return query;
	}

	/*
	public static void main(String[] args) {
		Course c = new Course("name", "code", Credits.SEVEN, "description", "examiner", new CourseDate(2019, LP.TWO));
		String[] values = new String[] {c.getName(), 
				c.getCredit().toString(),
				c.getDescription(), 
				c.getExaminer(),
				Integer.toString(c.getStartPeriod().getYear()),
				c.getStartPeriod().getPeriod().toString(),
				c.getCourseCode()};
		System.out.println(getCreateStatement("n", "Course", Course.CourseLabels.asStringArray(), values));
	}
	*/
	
	@Deprecated
	String MATCH() {
		return "";
	}
}
