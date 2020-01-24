package Data;

public enum Relations {
	DEVELOPED("developed"),
	REQUIRED("required"),
	BELONGS_TO("belongsTo");
	private String relation;
	private Relations(String relation) {
		this.relation = relation;
	}
}
