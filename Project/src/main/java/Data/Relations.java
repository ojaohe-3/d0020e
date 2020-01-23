package Data;

public enum Relations {
	DEVELOPED("developed"),
	REQUIRED("required");
	private String relation;
	private Relations(String relation) {
		this.relation = relation;
	}
}
