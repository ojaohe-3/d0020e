package Data;

/**
 * Representation of a program specialization. This can be used in the same way as a course,
 * since it inherits all the methods and stuff from it. The biggest difference, however,
 * is the fact that a course specialization will become a different node in Neo4J.
 * @author Robin
 *
 */
public class ProgramSpecialization extends CourseProgram {
	
	public ProgramSpecialization(CourseOrder courseOrder, String code, String name, String description, CourseDate startDate, Credits credits) {
		super(courseOrder, code, name, description, startDate, credits, CourseProgram.ProgramType.SPECIALIZATION);
	}
}
