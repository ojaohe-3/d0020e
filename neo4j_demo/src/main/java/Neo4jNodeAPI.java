import Data.*;

public class Neo4jNodeAPI {

	private Neo4jCommunicator communicator;
	
	
	public Neo4jNodeAPI(String url, String username, String password) {
		this.communicator = new Neo4jCommunicator(url, username, password);
	}
	
	public void createCourse(Course course) {
		createCourse(course.getName(), course.getCode(), course.getCredits(), course.getDescription(), course.getExaminer(), course.getTopic(), course.getStartPeriod());
	}
	
	
	public void createCourse(String name, String code, Credits credits, String description, String examiner, String topic, CourseDate startPeriod) {
		String query = "CREATE(node: Course { ";
		
		query += " name:\"" + name + "\", ";
		query += "code:\"" + code + "\", ";
		query += "credits:\"" + credits.toString() + "\", ";
		query += "description:\"" + description + "\", ";
		query += "examiner:\"" + examiner + "\", ";
		query += "topic:\"" + topic + "\", ";
		query += "startPeriod:\"" + startPeriod.getPeriod().toString() + " " + startPeriod.getYear() + "\"})";
		query += "RETURN \"\" + id(node)";
		
		Main.print(query);
		
		this.communicator.writeToNeo(query);
	}
	
	
	public void createKC(KC kc) {
		createKC(kc.getName(), kc.getGeneralDesc(), kc.getTaxonomyDesc(), kc.getTaxonomyLevel());
	}
	
	public void createKC(String name, String generalDesc, String taxonomyDesc, int taxonomyLevel) {
		String query = "CREATE(node: KC { ";
		
		query += " name:\"" + name + "\", ";
		query += "generalDescription:\"" + generalDesc + "\", ";
		query += "taxonomyDescription:\"" + taxonomyDesc + "\", ";
		query += "taxonomyLevel:\"" + taxonomyLevel + "\"}) ";	
		query += "RETURN \"\" + id(node)";
		
		Main.print(query);
		
		this.communicator.writeToNeo(query);
		
	}
	
	public void createRelation(Course course, KC kc, Relations relation) {
		
		String query = "MATCH (course: Course {code: \"" + course.getCode() +  "\"}) ";
		query += "MATCH (kc: KC {name: \"" + kc.getName() + "\", taxonomyLevel: \"" + kc.getTaxonomyLevel() + "\"})";
		query += "CREATE (course) -[rel:" + relation.toString() +"]->(kc)";
		query += "RETURN \"\" + id(rel)";
		
		
		Main.print(query);
		
		this.communicator.writeToNeo(query);
	}
	
	
}
