package Data;

public class CourseDate {

	private int year;
	private LP period;
	
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
