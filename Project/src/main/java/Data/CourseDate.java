package Data;

/**
 * This represents a starting date of a course or program.
 * @author <b>UNKNOWN</b>
 *
 */
public class CourseDate {

	private int year;
	private LP period;
	
	/**
	 * Create a date with year and study period.
	 * @param year
	 * @param period - The starting period in the form of {@link LP}
	 */
	public CourseDate(int year, LP period) {
		this.year = year;
		this.period = period;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public LP getPeriod() {
		return period;
	}

	public void setPeriod(LP period) {
		this.period = period;
	}
	
	
	
}
