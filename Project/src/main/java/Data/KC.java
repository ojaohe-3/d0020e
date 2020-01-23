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


	public static enum KCLabel {
		NAME("name"),  GENERAL_DESCRIPTION("generaldescription"), TAXONOMY_DESCRIPTION("taxonomydescription"), TAXONOMYLEVEL("taxonomylevel");
		private static String[] asStrings;
		private String name;

		static
		{
			asStrings = new String[KC.KCLabel.values().length];
			for (int i = 0; i < KC.KCLabel.values().length; i++) {
				asStrings[i] = KC.KCLabel.values()[i].toString();
			}
		}
		private KCLabel(String name) {
			this.name = name;
		}


		/**
		 * <b>The items of the returned array can be altered, but that is highly prohibited and
		 * will mess up your code.</b>
		 * @return Returns all the possible values as an array in the following order:
		 * <ul>
		 * <li>name</li>
		 * <li>credit</li>
		 * <li>description</li>
		 * <li>examiner</li>
		 * <li>year</li>
		 * <li>lp</li>
		 * <li>courseCode</li>
		 * </ul>
		 */
		public static String[] asStringArray() {
			return asStrings;
		}

		@Override
		public String toString() {
			return this.name;
		}
	}
}
