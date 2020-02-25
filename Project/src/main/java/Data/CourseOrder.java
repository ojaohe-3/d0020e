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
	private final int COURSES_PER_PERIOD = 12;
	
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
	 */
	public void setCourseAt(Course course) {
		int pos = -1;
		int period;
		switch (course.getStartPeriod().getPeriod()){
			case ONE:period=1;break;
			case TWO:period=2;break;
			case THREE:period=3;break;
			case FOUR:period=4;break;
			default:period =-1;//throws error
		}

		for (int i = 0; i < COURSES_PER_PERIOD; i++) {
			if(this.courses[i][period] == null){
				pos = i;
				break;
			}
		}
		this.courses[pos][period] = course;
			

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
	
	/**
	 * This sets all the courses in this course order and deletes the old one.
	 * The reference to the array is <b>NOT</b> copied, i.e. the reference is
	 * the same as the input argument.
	 * @param courses
	 */
	public void assignCourseOrder(Course[][] courses) {
		if (courses == null) {
			throw new IllegalArgumentException("The course array is non existent.");
		}
		this.courses = courses;
	}
	
	public int getReadingPeriods() {
		return this.readingPeriods;
	}
	
	public Course[][] getCourseArray() {
		return this.courses;
	}
	
}
