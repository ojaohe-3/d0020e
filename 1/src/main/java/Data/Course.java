package Data;

public class Course {
	
	private String name;
	private String code;
	private Credits credits;
	private String description;
	private String examiner;
	private String topic;
	private CourseDate startPeriod;
	
	public Course(String name, String code, Credits credits, String description, String examiner, String topic, CourseDate startPeriod) {
		this.name = name;
		this.code = code;
		this.credits = credits;
		this.description = description;
		this.examiner = examiner;
		this.topic = topic;
		this.startPeriod = startPeriod;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Credits getCredits() {
		return credits;
	}

	public void setCredits(Credits credits) {
		this.credits = credits;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getExaminer() {
		return examiner;
	}

	public void setExaminer(String examiner) {
		this.examiner = examiner;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public CourseDate getStartPeriod() {
		return startPeriod;
	}

	public void setStartPeriod(CourseDate startPeriod) {
		this.startPeriod = startPeriod;
	}
	

}
