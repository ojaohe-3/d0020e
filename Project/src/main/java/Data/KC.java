package Data;

public class KC {
	private int taxonomyLevel;
	private String name;
	private String generalDescription;
	private String taxonomyDescription;
	
	/**
	 * Constructor
	 * 
	 * @param name
	 * @param id
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
}
