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
		
		
		Course d0009e = new Course("Introduktion till programmering", "D0009E", Credits.SEVEN, "Basic programming stuff osvosv", "Bengtsson", "TOPIC.compsci", new CourseDate(2019, LP.ONE));
		KC arrays = new KC("Arrays", "How to program with arrays", "Basic level", 1);
		
		
		neo.createCourse(d0009e);
		neo.createKC(arrays);
		neo.createRelation(d0009e, arrays, Relations.DEVELOPED);
		
		print("Done");
		
	}

}
