package Data;


/**
 * 
 *  Keeps track of courses using a 2D array
 * 
 * @author Seagate_os
 *
 */
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
			
			this.courses[pos][readingPeriods] = course;
			
		}
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
