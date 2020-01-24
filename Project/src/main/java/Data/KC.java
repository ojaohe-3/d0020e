package Data;

/**
 * KC = Knowledge component. Knowledge components can either be
 * created or required by a course, i.e. KCs are the result of every course.
 * @author Wilma
 *
 */
public class KC {
	private int taxonomyLevel;
	private String name;
	private String generalDescription;
	private String taxonomyDescription;
	
	/**
	 * Database name for a KC.
	 */
	public static final String kc = "KC";

	/**
	 * Constructor
	 *
	 * @param name
	 * @param generalDescription
	 * @param taxonomyLevel
	 * @param taxonomyDescription
	 */
	public KC(String name, String generalDescription, int taxonomyLevel, String taxonomyDescription) {
		this.name = name;
		this.generalDescription = generalDescription;
		this.taxonomyLevel = taxonomyLevel;
		this.taxonomyDescription = taxonomyDescription;
	}

	public int getTaxonomyLevel() {
		return taxonomyLevel;
	}

	public void setTaxonomyLevel(int taxonomyLevel) {
		this.taxonomyLevel = taxonomyLevel;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGeneralDescription() {
		return generalDescription;
	}

	public void setGeneralDescription(String generalDescription) {
		this.generalDescription = generalDescription;
	}

	public String getTaxonomyDescription() {
		return taxonomyDescription;
	}

	public void setTaxonomyDescription(String taxonomyDescription) {
		this.taxonomyDescription = taxonomyDescription;
	}


	public static enum KCLabel {
		NAME("name"),  GENERAL_DESCRIPTION("generaldescription"), TAXONOMY_DESCRIPTION("taxonomydescription"), TAXONOMYLEVEL("taxonomylevel");
		private String name;


		private KCLabel(String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return this.name;
		}
	}
}
