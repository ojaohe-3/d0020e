package neo4j_JVM_API;

import java.util.ArrayList;

import Data.*;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.StatementResult;
import Data.Course;
import Data.CourseDate;
import Data.CourseOrder;
import Data.CourseProgram;
import Data.Credits;
import Data.KC;
import Data.LP;
import Data.Relations;
import Data.Topic;
import Data.Course.CourseLabels;
import neoCommunicator.Neo4jCommunicator;


/**
 * Internal class for create functions.
 * @author Jesper, Robin
 *
 */
public class CreateMethods {

private final Neo4jCommunicator communicator;



	/**
	 * 
	 * @param communicator  used when calling the database.
	 * @see neoCommunicator.Neo4jCommunicator
	 */
	public CreateMethods(Neo4jCommunicator communicator){
		this.communicator = communicator;
	}
	
	/**
	 * Add a topic to the database.
	 */
	public void addTopic(String topic) {
		String query = "CREATE (n: " + Topic.TopicLabels.TOPIC.toString() +" { " +Topic.TopicLabels.TITLE.toString()+ ":\""+ topic.toString() +"\"})";
		this.communicator.writeToNeo(query);
	}
	
	/**
	 * 
	 * Create a relationship between a KC and a topic
	 * 
	 * @param kc
	 * @param topic
	 */
	public void createTopicToKCRelation(KC kc, String topic) {
		String query = "MATCH(kc : KC { "+ KC.KCLabel.NAME + ": \"" + kc.getName()  + "\"})" ;
		query += "MATCH (topic: Topic {title : \"" + topic + "\"}) ";
		query += "CREATE (kc)-[r:" + Relations.BELONGS_TO + "]->(topic)";
		
		this.communicator.writeToNeo(query);
	}
	
	
	/**
	 * Create a relationship between a course and a topic
	 * @param course
	 * @param topic
	 */
	public void createTopicToCourseRelation(Course course, String topic) {
		
		String query = "MATCH (course: Course {" + Course.CourseLabels.CODE +" : \"" + course.getCourseCode() + "\", "+ Course.CourseLabels.LP + ": \"" + course.getStartPeriod().getPeriod() + "\", " + Course.CourseLabels.YEAR + ": \"" + course.getStartPeriod().getYear() + "\"  }) ";
		query += "MATCH (topic: Topic {title : \"" + topic + "\"}) ";
		query += "CREATE (course)-[r:"+ Relations.BELONGS_TO +"]->(topic)";
		
		this.communicator.writeToNeo(query);

	}
	
	
	
	/**
	 * Add a new course to the server. This is still a work in progress, so don't use it yet.
	 * @param course - The selected course to create.
	 * @author Robin
	 * @see Data.Course
	 */
	public void createCourse(Course course) {
		String query = "MATCH (course: Course {courseCode: \"" + course.getCourseCode() + "\", "+ CourseLabels.YEAR + " : \"" + course.getStartPeriod().getYear() + "\" , " + CourseLabels.LP + " : \"" + course.getStartPeriod().getPeriod() + "\" }) RETURN course";
		StatementResult result = this.communicator.readFromNeo(query);
		String StartStatment = "CREATE";

		/* Check if a course exist already. */
		if (result.hasNext()) {
			StartStatment="MERGE";
		}

		query = StartStatment+"(n:" + Course.course+"{"+
		Course.CourseLabels.NAME.toString() + ":\"" + course.getName().toString() + "\", " +
		Course.CourseLabels.CREDIT.toString() + ":\"" + course.getCredit().toString() + "\", " +
		Course.CourseLabels.DESCRIPTION.toString() + ":\"" + course.getDescription().toString() + "\", " +
		Course.CourseLabels.EXAMINER.toString() + ":\"" + course.getExaminer().toString() + "\", " +
		Course.CourseLabels.YEAR.toString() + ":\"" + Integer.toString(course.getStartPeriod().getYear()) + "\", " +
		Course.CourseLabels.LP.toString() + ":\"" + course.getStartPeriod().getPeriod().toString() + "\", " +
		Course.CourseLabels.CODE.toString()+ ":\"" + course.getCourseCode() + "\"" +
		"})";		
		
		communicator.writeToNeo(query);
	}
	
	/**
	 * Create a KC in the database.
	 * @param kc- The KC to add.
	 * @author Robin
	 * @see Data.KC
	 */
	public void createKC (KC kc) {
		
		String query = "CREATE(n:" +KC.kc + "{" +
			KC.KCLabel.NAME.toString() + ":\"" + kc.getName()+"\", " + 
			KC.KCLabel.GENERAL_DESCRIPTION.toString()+ ":\"" + kc.getGeneralDescription() + "\", " + 
			KC.KCLabel.TAXONOMYLEVEL.toString() + ":\"" + kc.getTaxonomyLevel() + "\", " + 
			KC.KCLabel.TAXONOMY_DESCRIPTION.toString() + ":\"" + kc.getTaxonomyDescription() + "\"})";
		communicator.writeToNeo(query);
	}
	

	/**
	 * Add multiple variants of the same KC to the database. 
	 * Instead of the KC's internal taxonomy level, a selections of multiple levels can be used.
	 * @param kc - The KC source.
	 * @param taxonomyLevels - Every level you want to add.
	 * @see Data.KC
	 */
	public void createKCgroup(KC kc, int... taxonomyLevels) {
		String query = "";
		for (int lv : taxonomyLevels) {
			query += "CREATE(n:" +KC.kc + "{" +
					KC.KCLabel.NAME.toString() + ":\"" + kc.getName()+"\", " + 
					KC.KCLabel.GENERAL_DESCRIPTION.toString()+ ":\"" + kc.getGeneralDescription() + "\", " + 
					KC.KCLabel.TAXONOMYLEVEL.toString() + ":\"" + lv + "\", " + 
					KC.KCLabel.TAXONOMY_DESCRIPTION.toString() + ":\"" + kc.getTaxonomyDescription() + "\"})";
		}
		communicator.writeToNeo(query);
	}
	
	/**
	 * Add a new program in the database. This method can also be used for creating program specializations
	 * Just make sure to create a connections between the program and the specialization afterwards.
	 * @param program - The program you want to add.
	 * @see Data.CourseProgram
	 * @see #createProgramSpecializationRelation(String, CourseDate, ProgramSpecialization)
	 */
	public void createProgram(CourseProgram program) {
		String query = "CREATE(n:" + program.getProgramType() + " {" +
				CourseProgram.ProgramLabels.NAME.toString() + ":\"" + program.getName() + "\", " +
				CourseProgram.ProgramLabels.DESCRIPTION.toString() + ":\"" + program.getDescription() + "\", " +
				CourseProgram.ProgramLabels.CODE.toString() + ":\"" + program.getCode() + "\", " +
				CourseProgram.ProgramLabels.CREDITS.toString() + ":\"" + program.getCredits() + "\", " +
				CourseProgram.ProgramLabels.YEAR.toString() + ":\"" + program.getStartDate().getYear() + "\", " +
				CourseProgram.ProgramLabels.LP.toString() + ":\"" + program.getStartDate().getPeriod().toString() + "\", " +		
				CourseProgram.ProgramLabels.READING_PERIODS.toString() + ":\"" + program.getCourseOrder().getReadingPeriods() + "\"})";
		System.out.println(query);
		communicator.writeToNeo(query);
	}
	
	/**
	 * Create a relation between a program specialization and a course program.
	 * @param code
	 * @param startDate
	 * @param specialization - The specialization you just created.
	 */
	public void createProgramSpecializationRelation(String code, CourseDate startDate, ProgramSpecialization specialization) {
		String query = "MATCH(program: " + CourseProgram.ProgramType.PROGRAM.toString() + "{" + CourseProgram.ProgramLabels.CODE.toString() + ": \"" + code + "\", " + 
				CourseProgram.ProgramLabels.YEAR.toString()+ ": \"" + startDate.getYear() + "\", " + 
				CourseProgram.ProgramLabels.LP.toString() + ": \"" + startDate.getPeriod().toString() + "\"}) ";
		
		query += "MATCH (specialization: " + CourseProgram.ProgramType.SPECIALIZATION.toString() +" {code: \"" + specialization.getCode() + "\", "+ 
		CourseLabels.YEAR + " : \"" + specialization.getStartDate().getYear() + "\" , " + 
				CourseLabels.LP + " : \"" + specialization.getStartDate().getPeriod().toString() + "\" }) ";
		query += "CREATE (program) <- [r: " + Relations.SPECIALIZATION.toString() + "]-(specialization)";
		this.communicator.writeToNeo(query);
	}
	
	/**
	 * Add relations between a program and it's courses.
	 * The courses must be added to the program before the 
	 * connections are made.
	 * @param program - The course program.
	 * @see Data.CourseProgram
	 */
	public void createProgramCourseRelations(CourseProgram program) {
		CourseOrder courseOrder = program.getCourseOrder();
		Course[][] courses = courseOrder.getCourseArray();
		
		/* find the program */
		String query = "MATCH(program: " + program.getProgramType() + "{" + CourseProgram.ProgramLabels.CODE.toString() + ": \"" + program.getCode() + "\", " + 
				CourseProgram.ProgramLabels.YEAR.toString()+ ": \"" + program.getStartDate().getYear()+ "\", " + 
				CourseProgram.ProgramLabels.LP.toString() + ": \"" + program.getStartDate().getPeriod().toString()+ "\"}) ";
		
		/* Create a match for every course in the course order and add a relation for that course. */
		Course c = null;	// temporary pointer.
		boolean hasCourses = false;
		for (int pos=0; pos < courses.length; pos++) {
			for (int period = 0; period < courses[pos].length; period++) {
				c = courses[pos][period];
				if(c != null) {
					hasCourses = true;
					query += "MATCH (course" + pos + "" + period + ": " + Course.course +" {" +CourseLabels.CODE.toString() + ": \""+c.getCourseCode()+"\", "+
					Course.CourseLabels.YEAR.toString() +": \"" + c.getStartPeriod().getYear()+"\","+
					Course.CourseLabels.LP.toString() + ": \""+c.getStartPeriod().getPeriod().toString()+"\"}) ";
					
	
			
				}
				
			}
		}
		if(!hasCourses) {
			System.err.print("No courses : ");
			return;
		}
		for (int pos=0; pos < courses.length; pos++) {
			for (int period = 0; period < courses[pos].length; period++) {
				c = courses[pos][period];
				if(c != null) {
					

					query += "CREATE (program)<-[r"+ pos + "" + period +": "+ Relations.IN_PROGRAM.toString() + " { "+Relations.YEAR+": \"" + pos + "\", "+Relations.PERIOD+" :\"" + period + "\"}]" + "-(course" + pos + "" + period +") ";
			
				}
				
			}
		}
		
		
		this.communicator.writeToNeo(query);
		
		
	}


	public void createProgramCourseRelation(CourseProgram program,Course course) {
		program.getCourseOrder().setCourseAt(course);
		String query = "MATCH(program:"+program.getProgramType()+"{"+ CourseProgram.ProgramLabels.CODE +":\""+program.getCode()+"\","+CourseProgram.ProgramLabels.LP+":\""+
				program.getStartDate().getPeriod().toString()+"\","+ CourseProgram.ProgramLabels.YEAR+":\""+program.getStartDate().getYear()+"\"}),";

		query += "(course:"+Course.course+"{"+Course.CourseLabels.CODE+":\""+course.getCourseCode()+"\","+ CourseLabels.LP.toString()+":\""+course.getStartPeriod().getPeriod().toString()
				+"\","+CourseLabels.YEAR+":\""+course.getStartPeriod().getYear()+"\"}) CREATE (program)<-[r:"+Relations.IN_PROGRAM+"]-(course)";


		this.communicator.writeToNeo(query);


	}
	/**
	 * Add relations between a course and all it's KCs to the database. 
	 * The KCs must be added as required or developed to the desired 
	 * course first, and the course must have been added to the database before
	 * this method is called.
	 * @param course - The course used for the relations.
	 * @see Data.Course
	 */	
	public void createCourseKCrelation(Course course) {
		ArrayList<KC> developed = course.getDevelopedKC();
		ArrayList<KC> required = course.getRequiredKC();
		String query = "MATCH (course: " + Course.course +" {" +CourseLabels.CODE.toString() + ":\""+course.getCourseCode()+"\", "+
				Course.CourseLabels.YEAR.toString() +":\"" + course.getStartPeriod().getYear()+"\","+
				Course.CourseLabels.LP.toString() + ":\""+course.getStartPeriod().getPeriod().toString()+"\"})";
		
		/* the first two loops create a match for every required and developed KC.*/
		for (int i = 0; i < required.size(); i++) {
			query += " MATCH ( kc" + i + ": " + KC.kc + "{" + KC.KCLabel.NAME.toString() +":\"" + required.get(i).getName() + "\", " + KC.KCLabel.TAXONOMYLEVEL.toString() + ":\"" + required.get(i).getTaxonomyLevel()+ "\"})";
		}
		for (int i = 0; i < developed.size(); i++) {
			query += " MATCH ( kc" + (i+required.size()) + ": " + KC.kc + "{" + KC.KCLabel.NAME.toString() +":\"" + developed.get(i).getName() + "\", " + KC.KCLabel.TAXONOMYLEVEL.toString() + ":\"" + developed.get(i).getTaxonomyLevel()+ "\"})";
		}
		
		/* these loops create a relation between the matched KCs and the course. */
		for (int i = 0; i < required.size(); i++) {
			query += "CREATE (course)-[r" + i + ":" + Relations.REQUIRED.toString() + "]->(kc" + i + ")";
		}
		for (int i = 0; i < developed.size(); i++) {
			query += "CREATE (course)-[r" + (required.size()+i) + ":" + Relations.DEVELOPED.toString() + "]->(kc" + (required.size()+i) + ")";
		}
		communicator.writeToNeo(query);
	}	
	
	/**
	 * Create a program specialization in database
	 * 
	 * @param specialization
	 */
	@Deprecated
	public void createProgramSpecialization(ProgramSpecialization specialization) {
		String query = "CREATE(programSpecialization:" + ProgramSpecialization.ProgramType.SPECIALIZATION + " {" +
				ProgramSpecialization.ProgramLabels.NAME.toString() + ":\"" + specialization.getName() + "\", " +
				ProgramSpecialization.ProgramLabels.DESCRIPTION.toString() + ":\"" + specialization.getDescription() + "\", " +
				ProgramSpecialization.ProgramLabels.CODE.toString() + ":\"" + specialization.getCode() + "\", " +
				ProgramSpecialization.ProgramLabels.YEAR.toString() + ":\"" + specialization.getStartDate().getYear() + "\", " +
				ProgramSpecialization.ProgramLabels.LP.toString() + ":\"" + specialization.getStartDate().getPeriod().toString() + "\", " +
				ProgramSpecialization.ProgramLabels.CREDITS.toString() + ":\"" + specialization.getCredits() + "\", " +
				ProgramSpecialization.ProgramLabels.YEAR.toString() + ":\"" + specialization.getStartDate().getYear() + "\", " +
				ProgramSpecialization.ProgramLabels.LP.toString() + ":\"" + specialization.getStartDate().getPeriod().toString() + "\"})";
		System.out.println(query);
		communicator.writeToNeo(query);
	}
	
	/**
	 * Add relations between a program specialization and it's courses.
	 * The courses must be added to the program specialization before the 
	 * connections are made.
	 * @param specialization - The program specialization.
	 *
	 */
	@Deprecated
	public void createProgramSpecializatonCourseRelation(ProgramSpecialization specialization) {
		CourseOrder courseOrder = specialization.getCourseOrder();
		Course[][] courses = courseOrder.getCourseArray();
		
		/* find the program specialization*/
		String query = "MATCH(program: " + ProgramSpecialization.ProgramType.SPECIALIZATION + "{" + ProgramSpecialization.ProgramLabels.CODE.toString() + ":\"" + specialization.getCode() + "\", " + 
				ProgramSpecialization.ProgramLabels.YEAR.toString()+ ":\"" + specialization.getStartDate().getYear()+ "\", " + 
				ProgramSpecialization.ProgramLabels.LP.toString() + "\"" + specialization.getStartDate().getPeriod().toString() + "\", " +
				ProgramSpecialization.ProgramLabels.YEAR.toString()+ ":\"" + specialization.getStartDate().getYear()+ "\", " + 
				ProgramSpecialization.ProgramLabels.LP.toString() + "\"" + specialization.getStartDate().getPeriod().toString() + "\", " +
				ProgramSpecialization.ProgramLabels.NAME.toString() + "\"" + specialization.getName().toString() + "})";
		
		/* Create a match for every course in the course order and add a relation for that course. */
		Course c = null;	// temporary pointer.
		for (int pos=0; pos < courses.length; pos++) {
			for (int period = 0; period < courses[pos].length; period++) {
				c = courses[pos][period];
				query += "MATCH (course" + pos + "" + period + ":" + Course.course +" {" +CourseLabels.CODE.toString() + ":\""+c.getCourseCode()+"\", "+
				Course.CourseLabels.YEAR.toString() +":\"" + c.getStartPeriod().getYear()+"\","+
				Course.CourseLabels.LP.toString() + ":\""+c.getStartPeriod().getPeriod().toString()+"\"})";
				
				query += "CREATE (programSpecialization) - [r"+pos+""+period+ ":"+ pos + ", " + period + "]" + "->(course" + pos + "" + period +")";
			}
		}
		this.communicator.writeToNeo(query);
	}
	
	/**
	 * Create a complete copy of a program (or specialization) for another starting year. Every course and 
	 * KC will be replicated and moved relative to the new starting year.
	 * @param program - The program you want to copy.
	 * @param newYear - The year of the first study period. This is not relative to the old year.
	 */
	public void createCopyOfProgrambyYear(CourseProgram program, int newYear) {
		int yearDifference = newYear - program.getStartDate().getYear();
		Course[][] courses = program.getCourseOrder().getCourseArray();
		Course[][] newCourses = new Course[courses.length][program.getCourseOrder().getReadingPeriods()];
		Course courseRef = null;
		CourseDate courseStartDateRef = null;
		for (int x = 0; x < courses.length; x++) {
			for (int y = 0; y < courses[x].length; x++) {
				
				courseRef = courses[x][y];
				if (courseRef == null) {
					continue;
				}
				courseStartDateRef = new CourseDate(courseRef.getStartPeriod().getYear() + yearDifference, courseRef.getStartPeriod().getPeriod());
				newCourses[x][y] = new Course(courseRef.getName(), courseRef.getCourseCode(), courseRef.getCredit(),courseRef.getDescription(), courseRef.getExaminer(), courseStartDateRef);
				this.createCourse(newCourses[x][y]);
				for (KC kc : courseRef.getDevelopedKC()) {
					newCourses[x][y].setDevelopedKC(kc);
				}
				for (KC kc : courseRef.getRequiredKC()) {
					newCourses[x][y].setRequiredKC(kc);
				}
				this.createCourseKCrelation(newCourses[x][y]);
			}
		}
		
		CourseDate newCourseDate = new CourseDate(newYear, program.getStartDate().getPeriod());
		CourseProgram newProgram = new CourseProgram(null, program.getCode(), program.getName(), program.getDescription(),newCourseDate, program.getCredits());
		CourseOrder newCourseOrder = new CourseOrder(program.getCourseOrder().getReadingPeriods());
		newCourseOrder.assignCourseOrder(newCourses);
		this.createProgram(newProgram);
<<<<<<< HEAD
		this.createProgramCourseRelation(newProgram);

=======
		this.createProgramCourseRelations(newProgram);
		
		
>>>>>>> origin/CanvasJS
	}


}


