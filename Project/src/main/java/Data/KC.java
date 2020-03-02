package Data;

import org.json.JSONException;
import org.json.JSONObject;

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

	/**
	 * Convert KC object to JSON
	 * @return String
	 */
	public String getAsJSON(){
		JSONObject object = new JSONObject();
		try {
			object.put(KCLabel.NAME.toString(),name.replaceAll("\"",""));
			object.put(KCLabel.TAXONOMYLEVEL.toString(), taxonomyLevel);
			object.put(KCLabel.GENERAL_DESCRIPTION.toString(), generalDescription.replaceAll("\"",""));
			object.put(KCLabel.TAXONOMY_DESCRIPTION.toString(), taxonomyDescription.replaceAll("\"",""));
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return object.toString();
	}
	
	/**
	 * Enums to be used in NEO API
	 */
	public static enum KCLabel {
		NAME("name"),  GENERAL_DESCRIPTION("generalDescription"), TAXONOMY_DESCRIPTION("taxonomyDescription"), TAXONOMYLEVEL("taxonomyLevel");
		private String name;


		private KCLabel(String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return this.name;
		}
	}

	@Override
	public String toString() {

		return getAsJSON();//.replaceAll("\"","");
	}
}
