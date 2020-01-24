package Data;

public class CourseOrder {

	private Course[][] courses;
	private int readingPeriods;
	
	// How many courses can run in parallel?
	private final int COURSES_PER_PERIOD = 4;
	
	/**
	 *
	 * @param readingPeriods Amount of reading periods
	 */
	public CourseOrder(int readingPeriods) {
		this.courses = new Course[COURSES_PER_PERIOD][readingPeriods];
		this.readingPeriods = readingPeriods;
	}
	
	
	/**
	 * 	Sets course at the given position
	 * 
	 * @param course
	 * @param period
	 * @param pos
	 */
	public void setCourseAt(Course course, int period, int pos) {
		if(period < this.readingPeriods && pos < COURSES_PER_PERIOD) {
			
			this.courses[pos][period] = course;
			
		}
	}
	
	/**
	 * Help function to find first empty position in 2d array
	 * 
	 * @param readingPeriods
	 * @return
	 */
	private int findFirstPos(int readingPeriods) {
		for(int i = 0; i < this.COURSES_PER_PERIOD; i++) {
			if(this.courses[i][readingPeriods] == null) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * Set course in course order
	 * 
	 * @param course
	 * @param period
	 * @return true if success else false
	 */
	public boolean setCourseAtLp(Course course, int period) {
		if(period < this.readingPeriods) {
			int pos = findFirstPos(period);
			if(pos != -1) {
				this.courses[pos][period] = course;
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Removes the course from the course array
	 * @param course
	 */
	public void removeCourse(Course course) {
		
		for(int i = 0; i < COURSES_PER_PERIOD; i++) {
			for(int j = 0; j < this.readingPeriods; j++) {
				if(this.courses[i][j].getCourseCode() == course.getCourseCode()) {
					this.courses[i][j] = null;
					
					// Break the loops
					i = COURSES_PER_PERIOD;
					j = this.readingPeriods;
				}
			}
		}
	}
	
	
	/**
	 * Deletes the course from and replaces it with the course to
	 * 
	 * @param from
	 * @param to
	 */
	public void changeCourse(Course from, Course to) {
		for(int i = 0; i < COURSES_PER_PERIOD; i++) {
			for(int j = 0; j < this.readingPeriods; j++) {
				if(this.courses[i][j].getCourseCode() == from.getCourseCode()) {
					this.courses[i][j] = to;
					
					// Break the loops
					i = COURSES_PER_PERIOD;
					j = this.readingPeriods;
				}
			}
		}
	}
	
	public Course[][] getCourseArray() {
		return this.courses;
	}
	
}
