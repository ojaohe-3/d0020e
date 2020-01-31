package Data;

public enum Relations {
	DEVELOPED("DEVELOPED"),
	REQUIRED("REQUIRED"),
	BELONGS_TO("BELONGS_TO"),
	IN_PROGRAM("IN_PROGRAM");
	SPECIALIZATION("SPECIALIZATION")
	
	private String relation;
	private Relations(String relation) {
		this.relation = relation;
	}
}
