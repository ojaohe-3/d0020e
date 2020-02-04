package Data;

/**
 * This represents all possible relations between courses, KCs and course programs.
 * @author <b>UNKNOWN</b> and Robin
 *
 */
public enum Relations {
	DEVELOPED("DEVELOPED"),
	REQUIRED("REQUIRED"),
	BELONGS_TO("BELONGS_TO"),
	IN_PROGRAM("IN_PROGRAM"),
	SPECIALIZATION("SPECIALIZATION");
	
	private String relation;
	private Relations(String relation) {
		this.relation = relation;
	}
	
	@Override
	public String toString() {
		return this.relation;
	}
}
