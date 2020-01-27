package Data;

public class KC {

	private String name;
	private String generalDesc;
	private String taxonomyDesc;
	private int taxonomyLevel;
	
	public KC(String name, String generalDesc, String taxonomyDesc, int taxonomyLevel) {
		this.name = name;
		this.generalDesc = generalDesc;
		this.taxonomyDesc = taxonomyDesc;
		this.taxonomyLevel = taxonomyLevel;
	}
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGeneralDesc() {
		return generalDesc;
	}

	public void setGeneralDesc(String generalDesc) {
		this.generalDesc = generalDesc;
	}

	public String getTaxonomyDesc() {
		return taxonomyDesc;
	}

	public void setTaxonomyDesc(String taxonomyDesc) {
		this.taxonomyDesc = taxonomyDesc;
	}

	public int getTaxonomyLevel() {
		return taxonomyLevel;
	}

	public void setTaxonomyLevel(int taxonomyLevel) {
		this.taxonomyLevel = taxonomyLevel;
	}

	
	
}
