import java.util.ArrayList;

import Data.Course;
import Data.CourseDate;
import Data.CourseOrder;
import Data.CourseProgram;
import Data.Credits;
import Data.KC;
import Data.LP;
import neo4j_JVM_API.Neo4JAPI;

public class Main {
	
	final static int AMOUNT_OF_COURSE_CREATED = 24;
	final static int AMOUNT_OF_KCS_CREATED = 24;
	static Course[] courses;
	static KC[] kcs;
	static String[] topics = {"MATH", "PHYSICS", "COMPUTER_SCIENCE", "ECONOMY", "SPACE", "MATH_SOMETHING"};
	
	static Neo4JAPI neoapi;
	
	public static void main(String[] args) {
		
		// Setup database connection
		neoapi = new Neo4JAPI("bolt://130.240.200.254:7687", "neo4j", "neo4j-d0020e");
		
		createCourses();
		createTopics();
		createKCs();
		
		createRelationsBetweenCoursesAndKCs();
		
		createCourseProgram("courseprgroma 1", "TIDAG");
		
		//addTopicsToCourses();
		//addTopicsToKCs();
		
		readCourses();
		readKCs();
		
		readTopics();
		
		filterTest();
		
		deleteCourses();
		deleteKCs();
	}
	
	public static void print(String s ) {
		System.out.println(s);
	}
	
	public static void createCourses() {
		// -------- CREATE COURSES ------------ //
		// Genereate courses for writing to DB
		courses = new Course[AMOUNT_OF_COURSE_CREATED];
		
		int year = 2017;
		int lpcounter = 0;
		LP lp = LP.ONE;
		for(int i = 0; i < AMOUNT_OF_COURSE_CREATED; i++) {
			if(i % 8 == 0) {
				year ++;
			}
			CourseDate cd = new CourseDate(year, lp);
			courses[i] = new Course("CourseNameNum " + i, "D000" + i + "E", Credits.SEVEN, "DESCRIPTION FOR COURSE" + i, "HÅKAN J", cd);
			
			lpcounter ++;
			if(lpcounter % 2 == 0) {
				if(lp == LP.ONE) {
					lp = LP.TWO;
				} else if(lp == LP.TWO) {
					lp = LP.THREE;
				} else if (lp == LP.THREE) {
					lp = LP.FOUR;
				} else if(lp == LP.FOUR) {
					lp = LP.ONE;
				}
			}
		}
		
		for(Course c : courses) {
			neoapi.createMethods.createCourse(c);
		}
		print(AMOUNT_OF_COURSE_CREATED + " courses should have been created");
		// ------- CREATE COURSE COMPLETE -------- //
		
	}
	public static void createTopics() {
		// ------- CREATE TOPICS --------- // 
		
		for(String topic: topics) {
			neoapi.createMethods.addTopic(topic);
		}
		
		print(topics.length + " topics created ");
		// ----- CREATE TOPICS COMPLETE --- //
	}
	public static void createKCs() {
		// ------ CREATE KCs ---------- //
		
		
		kcs = new KC[AMOUNT_OF_KCS_CREATED * 3];
		
		for(int i = 0; i < AMOUNT_OF_KCS_CREATED; i += 3) {
			kcs[i] = new KC("KC num " + i, "GENERAL DESC"+ i, 1, "TAXONOMY DESC" + i);
			kcs[i+1] = new KC("KC num " + i, "GENERAL DESC"+ i, 2, "TAXONOMY DESC" + i+1);
			kcs[i+2] = new KC("KC num " + i, "GENERAL DESC"+ i, 3, "TAXONOMY DESC" + i+2);
		}
		
		for(KC kc: kcs) {
			if(kc != null) {
				neoapi.createMethods.createKC(kc);
			}
		}
	}
	
	public static void createRelationsBetweenCoursesAndKCs() {
		
		boolean toggle = true;
		for(KC kc : kcs) {
			if(kc != null) {
				for(Course course: courses) {
					if(toggle) {
						toggle = !toggle;
						course.setDevelopedKC(kc);
					} else {
						toggle = !toggle;
						course.setRequiredKC(kc);
					}
					
				}
			}

		}
		for(Course course: courses) {
			
			neoapi.createMethods.createCourseKCrelation(course);
		}
		
		print("created A lot of relations between courses and kcs");
		
	}
	
	public static void addTopicsToCourses() {
		
		int i = 0;
		for(Course course: courses) {
			//neoapi.createMethods.addTopicToCourse(course, topics[i%topics.length]);
			i ++;
		}
		
	}
	public static void addTopicsToKCs() {
		
		int i = 0;
		for(KC kc: kcs) {
			//neoapi.createMethods.addTopicToKC(kc, topics[i%topics.length]);
			i ++;
		}
		
	}
	
	public static void createCourseProgram(String name, String code) {
		CourseOrder courseOrder = new CourseOrder(12);
		CourseDate courseDate = new CourseDate(2018, LP.ONE);
		
		CourseProgram courseProgram = new CourseProgram(courseOrder);
		courseProgram.setStartDate(courseDate);
		courseProgram.setCode(code);
		courseProgram.setName(name);
		courseProgram.setDescription("descrtiption for course" + name);
		courseProgram.setCredits(Credits.ERROR);
		
		neoapi.createMethods.createProgram(courseProgram);
		neoapi.createMethods.createProgramCourseRelation(courseProgram);
		print("A program named " + name + "is created");
		
	}
	
	private static void printCourse(Course course) {
		
		print("--Print course--");
		print("name :" +  course.getName());
		print("courseCode :" + course.getCourseCode());
		print("LP : " + course.getStartPeriod().getPeriod());
		print("year : " + course.getStartPeriod().getYear());
		print("description : " + course.getDescription());
		print("examiner :" + course.getExaminer());
		print("credits :" + course.getCredit());
		print("**********************************");
	}
	
	private static void printKCs(ArrayList<KC> kc_s) {
		for(KC kc: kc_s) {
			printKC(kc);
		}
	}
	
	private static void printKC(KC kc) {
		print("name :" + kc.getName());
		print("general desc :" + kc.getGeneralDescription());
		print("taxonomy desc : "+ kc.getTaxonomyDescription());
		print("taxonomy level : "+ kc.getTaxonomyLevel());
		print("************");
	}
	
	public static void readCourses() {
		
		for(Course course : courses) {
			Course c = neoapi.getMethods.getCourse(course.getCourseCode(), course.getStartPeriod());
			
			printCourse(c);
			print("Developed KCs");
			printKCs(c.getDevelopedKC());
			print("Required KCs");
			printKCs(c.getRequiredKC());
		}
	}
	
	public static void readKCs() {
		for(KC kc: kcs) {
			if(kc != null) {
				KC k = neoapi.getMethods.getKCwithTaxonomyLevel(kc.getName(), kc.getTaxonomyLevel());
				printKC(k);
			}
		}
	}
	
	public static void readTopics() {
		String[] titles = neoapi.getMethods.getTopics();
		print("-- Topics in Neo --");
		for(String t: titles) {
			print(t);
		}
		print("*************");
	}
	
	public static void filterTest() {
		String[] res = neoapi.filterMethods.filterCourseByCode("D001");
		print("filter course returned  : " + res.length);
		res = neoapi.filterMethods.filterCourseByName("Cour");
		print("filter course returned  : " + res.length);
		
		res = neoapi.filterMethods.filterProgramByCode("TID");
		print("filter programs returned  : " + res.length);
		res = neoapi.filterMethods.filterProgramByName("cour");
		print("filer programs returned : " + res.length);
		
		res = neoapi.filterMethods.filterTopic("MAT");
		print("filer topic returned : " + res.length);
		
	}
	
	public static void deleteCourses() {
		for(Course course: courses) {
			neoapi.modifyMethods.removeCourse(course.getCourseCode(), course.getStartPeriod());
		}
		print("courses deleted");
	}
	public static void deleteKCs() {
		for(KC kc: kcs) {
			neoapi.modifyMethods.removeKC(kc.getName(), kc.getTaxonomyLevel());
		}
		print("kcs deleted");
	}
	
	
}
