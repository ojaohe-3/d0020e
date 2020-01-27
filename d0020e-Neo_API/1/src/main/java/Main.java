import Data.Course;
import Data.CourseDate;
import Data.Credits;
import Data.KC;
import Data.LP;
import Data.Relations;

public class Main {

	public static void print(String s) {
		System.out.println(s);
	}
	public static void main(String[] args) {
		
		Neo4jNodeAPI neo = new Neo4jNodeAPI("bolt://localhost:7687", "neo4j", "admin");
		
		// term 1
		Course d0009e = new Course("Introduktion till programmering", "D0009E", Credits.SEVEN, "Basic programming stuff osvosv", "Bengtsson", "TOPIC.compsci", new CourseDate(2017, LP.ONE));
		Course d0015e = new Course("Datateknik och ingenjörsvetenskap", "D0015E", Credits.SEVEN, "Introduction to stuff student", "Jonsson", "TOPIC.compsci", new CourseDate(2017, LP.ONE));
		Course f0004t = new Course("Fysik 1", "F0004T", Credits.SEVEN, "Physics for forces and Thermdynamics", "Gustavsson", "TOPIC.physics", new CourseDate(2017, LP.TWO));
		Course m0009m = new Course("Disret Matematik", "M0009M", Credits.SEVEN, "Introduction to discrete mathmatics", "Ericsson", "TOPIC.math", new CourseDate(2017, LP.TWO));
		
		// term 2
		Course r0005n = new Course("Grundkurs i projekt och industriell ekonomi", "R0005N", Credits.SEVEN, "Boring project course for economy", "Sandberg", "TOPIC.economy", new CourseDate(2018, LP.THREE));
		Course d0010e = new Course("Objekt orienterad programmering och design", "D0010E", Credits.SEVEN, "Introduction to object orientated programming", "Jonsson", "TOPIC.compsci", new CourseDate(2018, LP.THREE));
		Course d0011e = new Course("Digital teknik", "D0011E", Credits.SEVEN, "Introduction to digital design", "Bodin", "TOPIC.compsci", new CourseDate(2018, LP.FOUR));
		Course m0038m = new Course("Matematik 1", "M0038M", Credits.SEVEN, "First base course in math for engineers", "Edlund", "TOPIC.math", new CourseDate(2018, LP.FOUR));
		
		
		KC arrays = new KC("Arrays", "How to program with arrays", "Basic level", 1);
		KC derivatives = new KC("Derivatives", "Derivatives", "Learn how to get the Derivative of advanced functions", 2);
		KC thermodyn = new KC("Thermo dynamics", "How to make calculations and predictions using phyiscal laws of thermodynamics", "", 2);
		KC economy = new KC("Economy", "Basic concepts of economy", "How companies make their calculations for products", 1);
		KC latex = new KC("LaTeX", "Use LaTeX to create awesome looking reports", "Using basic tags for layout", 1);
		KC reports = new KC("Reports", "Making scientifcal reports", "Basic understanding of how the report should be written", 1);
		KC probsolv = new KC("Problem solving", "The understanding of how to solve a problem", "How to look at a problem and make a computer solve it", 1);
		KC statemachines = new KC("State machines", "Using state machines to design the solution of a task","",1);
		KC statemachines2 = new KC("State machines", "Using state machines to design the solution of a task", "", 2);
		KC oop = new KC("Object-oriented programming", "Concepts of object-oriented programming","How to use OOP",1);
		KC uml = new KC("UML", "Model a system design using UML", "how the notations is working", 1);
		
		
		neo.createCourse(d0009e);
		neo.createCourse(d0015e);
		neo.createCourse(f0004t);
		neo.createCourse(m0009m);
		neo.createCourse(r0005n);
		neo.createCourse(d0010e);
		neo.createCourse(d0011e);
		neo.createCourse(m0038m);
		
		
		neo.createKC(arrays);
		neo.createKC(derivatives);
		neo.createKC(thermodyn);
		neo.createKC(economy);
		neo.createKC(latex);
		neo.createKC(reports);
		neo.createKC(probsolv);
		neo.createKC(statemachines);
		neo.createKC(statemachines2);
		neo.createKC(oop);
		neo.createKC(uml);
		
		neo.createRelation(d0009e, arrays, Relations.DEVELOPED);
		neo.createRelation(d0009e, probsolv, Relations.DEVELOPED);
		neo.createRelation(d0015e, latex, Relations.DEVELOPED);
		neo.createRelation(d0015e, reports, Relations.DEVELOPED);
		neo.createRelation(f0004t, thermodyn, Relations.DEVELOPED);
		neo.createRelation(m0009m, statemachines, Relations.DEVELOPED);
		neo.createRelation(r0005n, economy, Relations.DEVELOPED);
		neo.createRelation(d0010e, oop, Relations.DEVELOPED);
		neo.createRelation(d0010e, uml, Relations.DEVELOPED);
		neo.createRelation(d0011e, statemachines2, Relations.DEVELOPED);
		neo.createRelation(m0038m, derivatives, Relations.DEVELOPED);
		
		neo.createRelation(d0011e, statemachines, Relations.REQUIRED);
		neo.createRelation(m0009m, probsolv, Relations.REQUIRED);
		neo.createRelation(f0004t, reports, Relations.REQUIRED);
		neo.createRelation(f0004t, latex, Relations.REQUIRED);
		neo.createRelation(d0010e, arrays, Relations.REQUIRED);
		
		
		print("Done");
		
	}

}
